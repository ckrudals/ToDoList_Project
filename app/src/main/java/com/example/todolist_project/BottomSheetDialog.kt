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
    private var maindb: MainDatabase? = null
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


        maindb = MainDatabase.getInstance(context!!)

        val db = Room.databaseBuilder(context!!, MainDatabase::class.java, "Main")
            .fallbackToDestructiveMigration()
            .build()
        val intent = Intent(context, MainActivity::class.java)

        timedialog()
        daydialog()

//        val addRunnable = Runnable {
//            val newCat = Main()
//            newCat.db_goal = binding.timeGoal.text.toString()
//            newCat.db_time = binding.timeButonText.text.toString()
//            newCat.db_day = binding.dayButonText.text.toString()
//            maindb?.mainDao()?.insert(newCat)
//        }

        binding.timeSave.setOnClickListener {

            lifecycleScope.launch(Dispatchers.IO) {

                db.mainDao().insert(
                    Main(
                        null,
                        binding.timeGoal.text.toString(),
                        binding.timeButonText.text.toString(),
                        binding.dayButonText.text.toString()
                    )
                )

            } //비동기 처리


            intent.putExtra("goal", binding.timeGoal.text.toString())
            Log.d(TAG, "goal: ${binding.timeGoal.text} ")
            intent.putExtra("time", binding.timeButonText.text.toString())
            Log.d(TAG, "time: ${binding.timeButonText.text} ")
            intent.putExtra("day", binding.dayButonText.text.toString())
            Log.d(TAG, "day: ${binding.dayButonText.text}")
//            val addThread = Thread(addRunnable)
//            addThread.start()
            startActivity(intent)


        }

        binding.timeDelete.setOnClickListener {

            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.remove(this)
                ?.commit()
        }


    }

    @SuppressLint("SetTextI18n")
    fun daydialog() {
        binding.dayButonText.setOnClickListener {

            val intent = Intent()
            var calendar = Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var day = calendar.get(Calendar.DAY_OF_MONTH)

            var listener = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                // i년 i2월 i3일
                binding.dayButonText.text = " ${i2 + 1}월 ${i3}일"

                Log.d(TAG, " ${i2 + 1}월 ${i3}일")
                intent.putExtra("time_dialog", day)
            }

            var picker = DatePickerDialog(context!!, listener, year, month, day)

            picker.show()

        }


    }


    fun timedialog() {

        binding.timeButonText.setOnClickListener {

            val intent = Intent()
            var calendar = Calendar.getInstance()
            var hour = calendar.get(Calendar.HOUR)
            var minute = calendar.get(Calendar.MINUTE)


            var listener = TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                binding.timeButonText.text = "${i}시 ${i2}분"

                Log.d(TAG, "${i}시 ${i2}분")
                intent.putExtra("day_dialog", i2)
            }

            var picker = TimePickerDialog(context!!, listener, hour, minute, true) // true하면 24시간 제
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





