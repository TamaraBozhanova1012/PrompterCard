package com.bozhanova.teleprompter

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView

/**
 * TODO: document your custom view class.
 */

public class MyMouseScroll : ScrollView  {


    constructor(context: Context,attributeSet: AttributeSet) : super(context,attributeSet){

    }


    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }


}
