package com.example.todolist_project

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist_project.databinding.ActivityMainBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class MainActivity : AppCompatActivity(),BottomSheetDialog.BottomSheetDialogClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomSheetDialogClickListener: BottomSheetDialog.BottomSheetDialogClickListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.setDisplayShowTitleEnabled(false);
        liveChart()

        binding.fab.setOnClickListener() {


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
                horizontalAlignment = Legend.LegendHorizontalAlignment.valueOf(bottom.toString())
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

    }

}