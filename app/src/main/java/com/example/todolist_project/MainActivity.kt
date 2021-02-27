package com.example.todolist_project

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.todolist_project.Recy.ReMainViewAdapter
import com.example.todolist_project.Recy.ReMainViewModel
import com.example.todolist_project.Room.Main
import com.example.todolist_project.Room.MainDatabase
import com.example.todolist_project.databinding.ActivityMainBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainActivity : AppCompatActivity(), BottomSheetDialog.BottomSheetDialogClickListener {

    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"
    var mainList = ArrayList<ReMainViewModel>()
    private var maindb: MainDatabase? = null

    // 어뎁터
    lateinit var ReMainViewAdapter: ReMainViewAdapter

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        supportActionBar?.setDisplayShowTitleEnabled(false) //기존바 제거

        liveChart()

        ReMainViewAdapter = ReMainViewAdapter(this.mainList)
        ReMainViewAdapter.submitList(mainList)
        binding.recyclerViewMain.apply {
            this.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = ReMainViewAdapter
        }

        // 현재시간을 가져오기
        val now: Long = System.currentTimeMillis()

// 현재 시간을 Date 타입으로 변환
        val date = Date(now)

// 날짜, 시간을 가져오고 싶은 형태 선언
        val dateFormat = SimpleDateFormat("yyyy-MM-dd ", Locale("ko", "KR"))

// 현재 시간을 dateFormat 에 선언한 형태의 String 으로 변환
        binding.calendarText.text = dateFormat.format(date)


//        val r = Runnable {
//            try {
//                mainList = maindb?.mainDao()?.getAll()
//                ReMainViewAdapter = ReMainViewAdapter(this.mainList)
//                ReMainViewAdapter.submitList(mainList)
//                ReMainViewAdapter.notifyDataSetChanged()
//
//                binding.recyclerViewMain.apply {
//                    layoutManager =
//                        LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
//                    setHasFixedSize(true)
//                    adapter = ReMainViewAdapter
//                }
//            } catch (e: Exception) {
//                Log.d("tag", "Error - $e")
//            }
//        } // thread 사용

        dataRoom()


        /*  var started = false

          open class timer {

              var time: String? = null
              var day: String? = null
              fun start() {

                  started = true
                  val intent = Intent()
                  var timeStart = intent.getIntExtra("time_dialog", 0)
                  var dayStart = intent.getIntExtra("day_dialog", 0)
                  Log.d(TAG, "start: timeStart : $timeStart dayStart : $dayStart")
                  thread(start = true) {
                      while (started) {
                          Thread.sleep(1000)
                          timeStart -= 1
                          if (timeStart == 0) {
                              dayStart -= 1
                              if (timeStart == 0 && dayStart == 0) {
                                  Toast.makeText(
                                      applicationContext,
                                      "시간이 종료 되었습니다.",
                                      Toast.LENGTH_SHORT
                                  ).show()
                              }
                          }
                          runOnUiThread {
                              time = "$timeStart 분"
                              day = "$dayStart 분"
                          }
                      }

                  }
              }
          }

         */




        binding.fab.setOnClickListener() {
            val bottomSheetDialog: BottomSheetDialog = BottomSheetDialog()
            bottomSheetDialog.show(supportFragmentManager, "")


        }
    }


    fun dataRoom() {

        if (intent.hasExtra("goal") && intent.hasExtra("time") && intent.hasExtra("day")) {

            Log.d(TAG, "onCreate: recevie")
            var goal = intent.getStringExtra("goal")
            Log.d(TAG, "main_goal : $goal")
            var time_intent = intent.getStringExtra("time")
            Log.d(TAG, "main_time : $time_intent")
            var day_intent = intent.getStringExtra("day")
            Log.d(TAG, "day : $day_intent")

            //db 만듬
            val db = Room.databaseBuilder(
                applicationContext, MainDatabase::class.java, "Main"
            ).build()


            //테이블 만듬
            //아마 여기가 틀린 듯
            db.mainDao().getAll().observe(this, Observer { mains ->
                goal = mains.toString()
                time_intent = mains.toString()
                day_intent = mains.toString()
            })

            //추가함
            lifecycleScope.launch(Dispatchers.IO) {
                db.mainDao().insert(
                    Main(
                        null, goal, time_intent, day_intent
                    )
                )
            }


            //list에 넣음
            val mainModel = ReMainViewModel(
                time_intent, goal, day_intent
            )

            //list추가
            mainList.add(0, mainModel)
            //변동사항 알려야함
            ReMainViewAdapter.notifyDataSetChanged()
            Log.d(TAG, "intent_Data: $mainModel")
            Log.d(TAG, "intent_Data: $mainList")

        } else {
            Log.d(TAG, "onCreate: 값 전달 실페")
        }
    }

    private fun liveChart() {

        val xAxis = binding.lineChart.xAxis //x축 가져오기

        xAxis.apply { //x축 설정
            position = XAxis.XAxisPosition.BOTTOM
            textSize = 10f
            setDrawGridLines(true)
            granularity = 1f //x축 간격
            axisMinimum = 2f // 최소 표기값
            isGranularityEnabled = true
        }
        binding.lineChart.apply { //y축 설정

            axisRight.isEnabled = false
            axisLeft.axisMaximum = 50f
            axisLeft.axisMinimum = 1f
            legend.apply {
                textSize = 15f
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                orientation = Legend.LegendOrientation.HORIZONTAL
                setDrawInside(true)
            }
        }
        val lineData = LineData()
        binding.lineChart.data = lineData
        feedMultiple()
    }

    private fun feedMultiple() {
        var thread: Thread? = null
        if (thread != null) {
            thread!!.interrupt() //??
        }
        val runnable = Runnable {
            addEntry()
        }
        thread = Thread(Runnable {
            while (true) {
                runOnUiThread(runnable) //쓰레드가 도아감
                try {
                    Thread.sleep(1000) //1초동안 멈춤
                } catch (e: InterruptedException) {
                    e.printStackTrace() // ?? 뭐징
                }
            }
        })
        thread!!.start()
    }

    private fun addEntry() {
        val data: LineData = binding.lineChart.data

        //data가 null이 아니면 실행
        data?.let {
            //임의의 데이터셋 0부터 시작
            var set: ILineDataSet? = data.getDataSetByIndex(0)
            if (set == null) {
                set = createSet()
                data.addDataSet(set)
            }
            data.addEntry(Entry(set?.entryCount?.toFloat()!!, 0F, ""), 0) //data는 공부한 시간 저장해서 보냄
            data.notifyDataChanged() //데이터 변경 감지
            binding.lineChart.apply {
                notifyDataSetChanged() //라인차트 변경 감지
                moveViewToX(data.entryCount.toFloat())
                setVisibleXRangeMaximum(7f) //최대 x
                setVisibleXRangeMinimum(1f) //최소 x
                setPinchZoom(false) //zoom
                isDoubleTapToZoomEnabled = false //더블탭
                description.text = "시간" //text
                setBackgroundColor(resources.getColor(R.color.design_default_color_on_primary))
                description.textSize = 15f //textsize
                description.textColor = resources.getColor(R.color.design_default_color_secondary)
                setExtraOffsets(8f, 16f, 8f, 16f)
            }
        }
    }

    private fun createSet(): ILineDataSet? {
        val set = LineDataSet(null, "일주일동안 공부한 차트")
        set.apply {
            axisDependency = YAxis.AxisDependency.LEFT
            color = resources.getColor(R.color.black)//데이터 색
            setCircleColor(resources.getColor(R.color.design_default_color_secondary_variant))//데이터원형 색
            valueTextSize = 10f //값 글자 크기
            lineWidth = 2f //값 글자 크기
            lineWidth = 2f //라인 두께
            circleRadius = 3f
            fillAlpha = 0
            fillColor = resources.getColor(R.color.design_default_color_secondary_variant)
            setDrawValues(true)
        }
        return set
    }

    override fun onButton() {

        Log.d(TAG, "onButton: BottomSheetDialog")
    }

}

