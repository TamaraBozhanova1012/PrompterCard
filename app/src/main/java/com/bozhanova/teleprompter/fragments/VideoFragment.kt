package com.bozhanova.teleprompter.fragments

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.camera.core.*
import androidx.core.animation.doOnCancel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.bozhanova.teleprompter.databinding.FragmentVideoBinding
import com.bozhanova.teleprompter.utils.SharedPrefsManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bozhanova.teleprompter.R
import com.bozhanova.teleprompter.utils.*
import kotlinx.android.synthetic.main.fragment_video.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import kotlin.properties.Delegates

@SuppressLint("RestrictedApi")
class VideoFragment : BaseFragment<FragmentVideoBinding>(R.layout.fragment_video){

    companion object {
        private const val TAG = "CameraXDemo"
        const val KEY_GRID = "sPrefGridVideo"
    }

    private lateinit var displayManager: DisplayManager
    private lateinit var prefs: SharedPrefsManager
    private lateinit var preview: Preview
    private lateinit var videoCapture: VideoCapture

    private var displayId = -1
    private var lensFacing = CameraX.LensFacing.BACK
    private var flashMode by Delegates.observable(FlashMode.OFF.ordinal) { _, _, new ->
        binding.buttonFlash.setImageResource(
            when (new) {
                FlashMode.ON.ordinal -> R.drawable.ic_flash_on
                else -> R.drawable.ic_flash_off
            }
        )
    }
    private var hasGrid = false
    private var isTorchOn = false
    private var isRecording = false
    private val animateRecord by lazy {
        ObjectAnimator.ofFloat(binding.fabRecordVideo, View.ALPHA, 1f, 0.5f).apply {
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = ObjectAnimator.INFINITE
            doOnCancel { binding.fabRecordVideo.alpha = 1f }
        }
    }

    private  var objectAnimator : ObjectAnimator? = null

    private var playSlower : Boolean = true
    private var playNormal : Boolean = true
    private var playFaster : Boolean = true

    /**
     * A display listener for orientation changes that do not trigger a configuration
     * change, for example if we choose to override config change in manifest or for 180-degree
     * orientation changes.
     */
    private val displayListener = object : DisplayManager.DisplayListener {
        override fun onDisplayAdded(displayId: Int) = Unit
        override fun onDisplayRemoved(displayId: Int) = Unit

        override fun onDisplayChanged(displayId: Int) = view?.let { view ->
            if (displayId == this@VideoFragment.displayId) {
                preview.setTargetRotation(view.display.rotation)
                videoCapture.setTargetRotation(view.display.rotation)
            }
        } ?: Unit
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = SharedPrefsManager.newInstance(requireContext())
        hasGrid = prefs.getBoolean(KEY_GRID, false)
        initViews()

        displayManager =
            requireContext().getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        displayManager.registerDisplayListener(displayListener, null)

        binding.fragment = this // setting the variable for XML
        binding.viewFinder.addOnAttachStateChangeListener(object :
            View.OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(v: View) =
                displayManager.registerDisplayListener(displayListener, null)

            override fun onViewAttachedToWindow(v: View) =
                displayManager.unregisterDisplayListener(displayListener)
        })

        // This swipe gesture adds a fun gesture to switch between video and photo
        val swipeGestures = SwipeGestureDetector().apply {
            setSwipeCallback(left = {
                Navigation.findNavController(view).navigate(R.id.action_video_to_camera)
            })
        }
        val gestureDetectorCompat = GestureDetector(requireContext(), swipeGestures)
        view.setOnTouchListener { _, motionEvent ->
            if (gestureDetectorCompat.onTouchEvent(motionEvent)) return@setOnTouchListener false
            return@setOnTouchListener true
        }
        val bundle = this.arguments
        if (bundle != null) {
            val receivedScenario = bundle.getString("project_scenario", "")// Key, default value
            textV_scenario2.text = receivedScenario
        }

        btnNormalSpeed.setOnClickListener{ onScrollAnimate(animationTime( textV_scenario2.text.toString(), 3),3) }
        btnSlowerSpeed.setOnClickListener { onScrollAnimate(animationTime( textV_scenario2.text.toString(), 2), 2) }
        btnFasterSpeed.setOnClickListener { onScrollAnimate(animationTime( textV_scenario2.text.toString(), 5), 5) }
    }

    /**
     * Create some initial states
     * */
    private fun initViews() {
        binding.buttonGrid.setImageResource(if (hasGrid) R.drawable.ic_grid_on else R.drawable.ic_grid_off)
        binding.groupGridLines.visibility = if (hasGrid) View.VISIBLE else View.GONE

       // adjustInsets()
    }

    /**
     * This methods adds all necessary margins to some views based on window insets and screen orientation
     * */
//    private fun adjustInsets() {
//        binding.viewFinder.fitSystemWindows()
//        binding.fabRecordVideo.onWindowInsets { view, windowInsets ->
//            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
//                view.bottomMargin = windowInsets.systemWindowInsetBottom
//            else view.endMargin = windowInsets.systemWindowInsetRight
//        }
//        binding.buttonFlash.onWindowInsets { view, windowInsets ->
//            view.topMargin = windowInsets.systemWindowInsetTop
//        }
//    }

    /**
     * Change the facing of camera
     *  toggleButton() function is an Extension function made to animate button rotation
     * */
    fun toggleCamera() = binding.buttonSwitchCamera.toggleButton(
        lensFacing == CameraX.LensFacing.BACK, 180f,
        R.drawable.ic_outline_camera_rear, R.drawable.ic_outline_camera_front
    ) {
        lensFacing = if (it) CameraX.LensFacing.BACK else CameraX.LensFacing.FRONT

        CameraX.getCameraWithLensFacing(lensFacing)
        recreateCamera()
    }

    /**
     * Unbinds all the lifecycles from CameraX, then creates new with new parameters
     * */
    private fun recreateCamera() {
        CameraX.unbindAll()
        startCamera()
    }

    private fun startCamera() {
        // This is the Texture View where the camera will be rendered
        val viewFinder = binding.viewFinder

        // The ratio for the output video and preview
        val ratio = AspectRatio.RATIO_16_9

        // The Configuration of how we want to preview the camera
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetAspectRatio(ratio) // setting the aspect ration
            setLensFacing(lensFacing) // setting the lens facing (front or back)
            setTargetRotation(viewFinder.display.rotation)// setting the rotation of the camera
        }.build()

        // Create an instance of Camera Preview
        preview = AutoFitPreviewBuilder.build(previewConfig, viewFinder)

        // The Configuration of how we want to capture the video
        val videoCaptureConfig = VideoCaptureConfig.Builder().apply {
            setTargetAspectRatio(ratio) // setting the aspect ration
            setLensFacing(lensFacing) // setting the lens facing (front or back)
            setVideoFrameRate(24) // setting the frame rate to 24 fps
            setTargetRotation(viewFinder.display.rotation) // setting the rotation of the camera
        }.build()

        videoCapture = VideoCapture(videoCaptureConfig)

        binding.fabRecordVideo.setOnClickListener { recordVideo(videoCapture) }

        // Add all the use cases to the CameraX
        CameraX.bindToLifecycle(viewLifecycleOwner, preview, videoCapture)
    }

    /**
     * Navigate to PreviewFragment
     * */
    fun openPreview() {
        if (!outputDirectory.listFiles().isNullOrEmpty())
            view?.let { Navigation.findNavController(it).navigate(R.id.action_video_to_preview) }
    }

    private fun recordVideo(videoCapture: VideoCapture) {
        // Create the output file
        val videoFile = File(outputDirectory, "${System.currentTimeMillis()}.mp4")
        if (!isRecording) {
            animateRecord.start()
            // Capture the video, first parameter is the file where the video should be stored, the second parameter is the callback after racording a video
            videoCapture.startRecording(
                videoFile,
                requireContext().mainExecutor(),
                object : VideoCapture.OnVideoSavedListener {
                    override fun onVideoSaved(file: File) {
                        // Create small preview
                        setGalleryThumbnail(file)
                        val msg = "Video saved in ${file.absolutePath}"
                        Log.d("CameraXDemo", msg)
                    }

                    override fun onError(
                        videoCaptureError: VideoCapture.VideoCaptureError,
                        message: String,
                        cause: Throwable?
                    ) {
                        // This function is called if there is some error during the video recording process
                        animateRecord.cancel()
                        val msg = "Video capture failed: $message"
                        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                        Log.e("CameraXApp", msg)
                        cause?.printStackTrace()
                    }
                })
        } else {
            animateRecord.cancel()
            videoCapture.stopRecording()
        }
        isRecording = !isRecording
    }

    /**
     * Turns on or off the grid on the screen
     * */
    fun toggleGrid() =
        binding.buttonGrid.toggleButton(
            hasGrid,
            180f,
            R.drawable.ic_grid_off,
            R.drawable.ic_grid_on
        ) { flag ->
            hasGrid = flag
            prefs.putBoolean(KEY_GRID, flag)
            binding.groupGridLines.visibility = if (flag) View.VISIBLE else View.GONE
        }

    /**
     * Turns on or off the flashlight
     * */
    fun toggleFlash() {
        binding.buttonFlash.toggleButton(
            flashMode == FlashMode.ON.ordinal,
            360f,
            R.drawable.ic_flash_off,
            R.drawable.ic_flash_on
        ) { flag ->
            isTorchOn = flag
            flashMode = if (flag) FlashMode.ON.ordinal else FlashMode.OFF.ordinal
            preview.enableTorch(flag)
        }
    }

    override fun onPermissionGranted() {
        // Each time apps is coming to foreground the need permission check is being processed
        binding.viewFinder.let { vf ->
            vf.post {
                // Setting current display ID
                displayId = vf.display.displayId
                recreateCamera()
                lifecycleScope.launch(Dispatchers.IO) {
                    // Do on IO Dispatcher
                    // Check if there are any photos or videos in the app directory and preview the last one
                    outputDirectory.listFiles()?.firstOrNull()?.let {
                        setGalleryThumbnail(it)
                    }
                        ?: binding.buttonGallery.setImageResource(R.drawable.ic_no_picture) // or the default placeholder
                }
                preview.enableTorch(isTorchOn)
            }
        }
    }

    private fun setGalleryThumbnail(file: File) = binding.buttonGallery.let {
        // Do the work on view's thread, this is needed, because the function is called in a Coroutine Scope's IO Dispatcher
        it.post {
            Glide.with(requireContext())
                .load(file)
                .apply(RequestOptions.circleCropTransform())
                .into(it)
        }
    }

    override fun onBackPressed() = requireActivity().finish()

    override fun onStop() {
        super.onStop()
        preview.enableTorch(false)
    }

    private fun onScrollAnimate(time : Long, speed: Int) {

        objectAnimator?.pause()
        val diff: Int = scrollView.getChildAt(0).height + scrollView.scrollY
        objectAnimator = ObjectAnimator.ofInt(
            scrollView,
            "scrollY",
            diff
        )
        objectAnimator?.interpolator = LinearInterpolator()

        when (speed) {
            2 -> {
                if (playSlower) {
                    playSlower = false
                    playNormal = true
                    playFaster = true
                    btnSlowerSpeed.setBackgroundColor(resources.getColor(R.color.blue))
                    btnFasterSpeed.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                    btnNormalSpeed.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                    objectAnimator?.duration = time
                    objectAnimator?.start()
                } else {
                    playSlower = true
                    btnSlowerSpeed.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                    objectAnimator?.pause()
                }
            }
            3 -> {
                if (playNormal) {
                    playNormal = false
                    playSlower = true
                    playFaster = true
                    btnNormalSpeed.setBackgroundColor(resources.getColor(R.color.blue))
                    btnFasterSpeed.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                    btnSlowerSpeed.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                    objectAnimator?.duration = time
                    objectAnimator?.start()
                } else {
                    playNormal = true
                    btnNormalSpeed.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                    objectAnimator?.pause()
                }
            }
            5 -> {
                if (playFaster) {
                    playFaster = false
                    playSlower = true
                    playNormal = true
                    btnFasterSpeed.setBackgroundColor(resources.getColor(R.color.blue))
                    btnSlowerSpeed.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                    btnNormalSpeed.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                    objectAnimator?.duration = time
                    objectAnimator?.start()
                } else {
                    playFaster = true
                    btnFasterSpeed.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                    objectAnimator?.pause()
                }
            }
        }
    }

    private fun animationTime(scenario : String, speed : Int) : Long{
        val wordList: List<String> = scenario.trim().split("\\s+".toRegex())
        val quantity = wordList.size;
        val timeInSeconds = (quantity / speed) * 1000;
        return timeInSeconds.toLong()
    }

}