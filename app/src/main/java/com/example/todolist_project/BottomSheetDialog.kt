package com.example.todolist_project

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.todolist_project.Room.Main
import com.example.todolist_project.Room.MainDatabase
import com.example.todolist_project.databinding.BottomsheetdialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


@Suppress("CAST_NEVER_SUCCEEDS", "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")

class BottomSheetDialog : BottomSheetDialogFragment() {


    private var _binding: BottomsheetdialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomSheetDialogClickListener: BottomSheetDialogClickListener

    interface BottomSheetDialogClickListener {
        fun onButton()

    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):
            View? {

        _binding = BottomsheetdialogBinding.inflate(inflater, container, false)
        val view = binding.root





        return view


    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val db = Room.databaseBuilder(context!!, MainDatabase::class.java, "Main")
            .fallbackToDestructiveMigration()
            .build()

        timedialog()

        daydialog()

        binding.timeSave.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            lifecycleScope.launch(Dispatchers.IO) {

                val day = intent.getStringExtra("day_room")
                val time = intent.getStringExtra("time_room")
                db.mainDao().insert(
                    Main(
                        db_goal = binding.timeGoal.text.toString(),
                        db_time = time,
                        db_day = day
                    )
                )

                intent.putExtra("goal", binding.timeGoal.text)
                Log.d(TAG, "goal: ${binding.timeGoal.text} ")
                intent.putExtra("time",day)
                Log.d(TAG, "time: $day ")
                intent.putExtra("day", time)
                Log.d(TAG, "day: $time")
                save()
            }
            startActivity(intent)

        }



    }

    @SuppressLint("SetTextI18n")
    fun daydialog() {
        binding.dayButonText.setOnClickListener {

            var intent = Intent()

            var calendar = Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var day = calendar.get(Calendar.DAY_OF_MONTH)

            var listener = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                // i년 i2월 i3일
                var day_text = binding.dayButonText.text
                binding.dayButonText.text = " ${i2 + 1}월 ${i3}일"
                intent.putExtra("day_room", day_text)
                Log.d(TAG,  " ${i2 + 1}월 ${i3}일")
            }

            var picker = DatePickerDialog(context!!, listener, year, month, day)
            picker.show()

        }


    }





    fun timedialog() {


        binding.timeButonText.setOnClickListener {
            var intent = Intent()

            var calendar = Calendar.getInstance()
            var hour = calendar.get(Calendar.HOUR)
            var minute = calendar.get(Calendar.MINUTE)

            var listener = TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                var time_text =  binding.timeButonText.text
                binding.timeButonText.text = "${i}시 ${i2}분"
                intent.putExtra("time_room", time_text)
                Log.d(TAG, "${i}시 ${i2}분")
            }

            var picker = TimePickerDialog(context!!, listener, hour, minute, false) // true하면 24시간 제
            picker.show()

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            bottomSheetDialogClickListener = context as BottomSheetDialogClickListener
        } catch (e: Exception) {
            Log.d(TAG, "Error")
        }
    }

    fun save() {
        binding.timeModify.visibility = View.VISIBLE
        binding.timeDelete.visibility = View.GONE
        binding.timeGoal.isEnabled = isHidden
        binding.timeButonText.isEnabled = isHidden

        binding.timeButonText.text = binding.timeButonText.toString()
        binding.dayButonText.text = binding.dayButonText.toString()
        binding.timeGoal.text = binding.timeGoal.text

    }
}



