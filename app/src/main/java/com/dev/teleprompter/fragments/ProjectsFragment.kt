package com.dev.teleprompter.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.teleprompter.R
import com.dev.teleprompter.adapter.ProjectsListAdapter
import com.dev.teleprompter.interfaces.CellClickListener
import com.dev.teleprompter.models.Project
import kotlinx.android.synthetic.main.projects_fragment.*

class ProjectsFragment : Fragment(), CellClickListener {
    private val myProjects = listOf(
        Project("Сказка о мёртвой царевне и семи богатырях", "Oct.,23", "Царь с царицею простился,\n" +
                "В путь-дорогу снарядился,\n" +
                "И царица у окна\n" +
                "Села ждать его одна.\n" +
                "Ждёт-пождёт с утра до ночи,\n" +
                "Смотрит в поле, инда очи\n" +
                "Разболелись, глядючи\n" +
                "С белой зори до ночи.\n" +
                "Не видать милого друга!\n" +
                "Только видит: вьётся вьюга,\n" +
                "Снег валится на поля,\n" +
                "Вся белёшенька земля.\n" +
                "Девять месяцев проходит,\n" +
                "С поля глаз она не сводит.\n" +
                "Вот в сочельник в самый, в ночь\n" +
                "Бог даёт царице дочь.\n" +
                "Рано утром гость желанный,\n" +
                "День и ночь так долго жданный,\n" +
                "Издалеча наконец\n" +
                "Воротился царь-отец.\n" +
                "На него она взглянула,\n" +
                "Тяжелёшенько вздохнула,\n" +
                "Восхищенья не снесла\n" +
                "И к обедне умерла.\n" +
                "\n" +
                "Долго царь был неутешен,\n" +
                "Но как быть? и он был грешен;\n" +
                "Год прошёл, как сон пустой,\n" +
                "Царь женился на другой.\n" +
                "Правду молвить, молодица\n" +
                "Уж и впрямь была царица:\n" +
                "Высока, стройна, бела,\n" +
                "И умом и всем взяла;\n" +
                "Но зато горда, ломлива,\n" +
                "Своенравна и ревнива.\n" +
                "Ей в приданое дано\n" +
                "Было зеркальце одно;\n" +
                "Свойство зеркальце имело:\n" +
                "Говорить оно умело.\n" +
                "С ним одним она была\n" +
                "Добродушна, весела,\n" +
                "С ним приветливо шутила\n" +
                "И, красуясь, говорила:\n" +
                "“Свет мой, зеркальце! скажи,\n" +
                "Да всю правду доложи:\n" +
                "Я ль на свете всех милее,\n" +
                "Всех румяней и белее?”\n" +
                "И ей зеркальце в ответ:\n" +
                "“Ты, конечно, спору нет;\n" +
                "Ты, царица, всех милее,\n" +
                "Всех румяней и белее”.\n" +
                "И царица хохотать,\n" +
                "И плечами пожимать,\n" +
                "И подмигивать глазами,\n" +
                "И прищёлкивать перстами,\n" +
                "И вертеться подбочась,\n" +
                "Гордо в зеркальце глядясь.\n" +
                "\n" +
                "Но царевна молодая,\n" +
                "Тихомолком расцветая,\n" +
                "Между тем росла, росла,\n" +
                "Поднялась - и расцвела,\n" +
                "Белолица, черноброва,\n" +
                "Нраву кроткого такого.\n" +
                "И жених сыскался ей,\n" +
                "Королевич Елисей.\n" +
                "Сват приехал, царь дал слово,\n" +
                "А приданое готово:\n" +
                "Семь торговых городов\n" +
                "Да сто сорок теремов.\n" +
                "\n" +
                "На девичник собираясь,\n" +
                "Вот царица, наряжаясь\n" +
                "Перед зеркальцем своим,\n" +
                "Перемолвилася с ним:\n" +
                "“Я ль, скажи мне, всех милее,\n" +
                "Всех румяней и белее?”\n" +
                "Что же зеркальце в ответ?\n" +
                "“Ты прекрасна, спору нет;\n" +
                "Но царевна всех милее,\n" +
                "Всех румяней и белее”.\n" +
                "Как царица отпрыгнёт,\n" +
                "Да как ручку замахнёт,\n" +
                "Да по зеркальцу как хлопнет,\n" +
                "Каблучком-то как притопнет!..\n" +
                "“Ах ты, мерзкое стекло!\n" +
                "Это врёшь ты мне назло.\n" +
                "Как тягаться ей со мною?\n" +
                "Я в ней дурь-то успокою.\n" +
                "Вишь какая подросла!\n" +
                "И не диво, что бела:\n" +
                "Мать брюхатая сидела\n" +
                "Да на снег лишь и глядела!\n" +
                "Но скажи: как можно ей\n" +
                "Быть во всём меня милей?\n" +
                "Признавайся: всех я краше.\n" +
                "Обойди всё царство наше,\n" +
                "Хоть весь мир; мне ровной нет.\n" +
                "Так ли?” Зеркальце в ответ:\n" +
                "“А царевна всё ж милее,\n" +
                "Всё ж румяней и белее”.\n" +
                "Делать нечего. Она,\n" +
                "Чёрной зависти полна,\n" +
                "Бросив зеркальце под лавку,\n" +
                "Позвала к себе Чернавку\n" +
                "И наказывает ей,\n" +
                "Сенной девушке своей,\n" +
                "Весть царевну в глушь лесную\n" +
                "И, связав её, живую\n" +
                "Под сосной оставить там\n" +
                "На съедение волкам.\n" +
                "\n" +
                "Черт ли сладит с бабой гневной?\n" +
                "Спорить нечего. С царевной\n" +
                "Вот Чернавка в лес пошла\n" +
                "И в такую даль свела,\n" +
                "Что царевна догадалась\n" +
                "И до смерти испугалась\n" +
                "И взмолилась: “Жизнь моя!\n" +
                "В чём, скажи, виновна я?\n" +
                "Не губи меня, девица!\n" +
                "А как буду я царица,\n" +
                "Я пожалую тебя”.\n" +
                "Та, в душе её любя,\n" +
                "Не убила, не связала,\n" +
                "Отпустила и сказала:\n" +
                "“Не кручинься, бог с тобой”.\n" +
                "А сама пришла домой.\n" +
                "“Что? - сказала ей царица. -\n" +
                "Где красавица девица?” -\n" +
                "“Там, в лесу, стоит одна, -\n" +
                "Отвечает ей она.-\n" +
                "Крепко связаны ей локти;\n" +
                "Попадётся зверю в когти,\n" +
                "Меньше будет ей терпеть,\n" +
                "Легче будет умереть”.\n" +
                "\n" +
                "И молва трезвонить стала:\n" +
                "Дочка царская пропала!\n" +
                "Тужит бедный царь по ней.\n" +
                "Королевич Елисей,\n" +
                "Помолясь усердно богу,\n" +
                "Отправляется в дорогу\n" +
                "За красавицей душой,\n" +
                "За невестой молодой.\n" +
                "\n" +
                "Но невеста молодая,\n" +
                "До зари в лесу блуждая,\n" +
                "Между тем всё шла да шла\n" +
                "И на терем набрела.\n" +
                "Ей навстречу пёс, залая,\n" +
                "Прибежал и смолк, играя.\n" +
                "В ворота вошла она,\n" +
                "На подворье тишина.\n" +
                "Пёс бежит за ней, ласкаясь,\n" +
                "А царевна, подбираясь,\n" +
                "Поднялася на крыльцо\n" +
                "И взялася за кольцо;\n" +
                "Дверь тихонько отворилась,\n" +
                "И царевна очутилась\n" +
                "В светлой горнице; кругом\n" +
                "Лавки, крытые ковром,\n" +
                "Под святыми стол дубовый,\n" +
                "Печь с лежанкой изразцовой.\n" +
                "Видит девица, что тут\n" +
                "Люди добрые живут;\n" +
                "Знать, не будет ей обидно! -\n" +
                "Никого меж тем не видно.\n" +
                "Дом царевна обошла,\n" +
                "Всё порядком убрала,\n" +
                "Засветила богу свечку,\n" +
                "Затопила жарко печку,\n" +
                "На полати взобралась\n" +
                "И тихонько улеглась."),
        Project("Project 7", "Oct.,22", "Project 7 Project 7 Project 7  Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 "),
        Project("Project 6", "Oct.,21", "Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 "),
        Project("Project 5","Oct.,20", "Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 "),
        Project("Project 4", "Oct.,19", "Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 "),
        Project("Project 3", "Oct.,19", "Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 "),
        Project("Project 2", "Oct.,19", "Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 "),
        Project("Project 1", "Oct.,19", "Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1")

    )
    private lateinit var adapter: ProjectsListAdapter
    var listener: OnItemClickedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.projects_fragment, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        projects_recycle_view.layoutManager = LinearLayoutManager(activity)
        adapter = ProjectsListAdapter(myProjects, this)
        projects_recycle_view.adapter = adapter

        DividerItemDecoration(activity, LinearLayoutManager(activity).orientation).apply {
            projects_recycle_view.addItemDecoration(this) }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnItemClickedListener
        if (listener == null) {
            return
        }
    }

    override fun onItemClicked(data: Project) {
        val bundle = bundleOf("project_title" to data.title, "project_scenario" to data.scenario)
        this.findNavController().navigate(R.id.action_projects_fragment_des_to_detail_projects_fragment_des, bundle)
        listener?.onItemSelected(data.scenario)
    }

    companion object {
        fun newInstance(): ProjectsFragment = ProjectsFragment()
    }
    interface OnItemClickedListener {
        fun onItemSelected(scenario : String)
    }
}