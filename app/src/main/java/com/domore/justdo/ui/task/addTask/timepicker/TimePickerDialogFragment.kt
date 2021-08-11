package com.domore.justdo.ui.task.addTask.timepicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.domore.justdo.databinding.FragmentTimePickerBinding

class TimePickerDialogFragment : DialogFragment() {
    private var callback: OnTimeSelectedListener? = null
    private var viewBinding: FragmentTimePickerBinding? = null

    interface OnTimeSelectedListener {
        fun onTimeSubmit(hours: Int, minutes: Int, seconds: Int)
    }

    internal fun setOnTimerClickListener(listener: OnTimeSelectedListener) {
        callback = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentTimePickerBinding
        .inflate(inflater, container, false)
        .apply {
            viewBinding = this
        }.root

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.window!!.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        )

        viewBinding!!.buttonAdd.setOnClickListener {
            viewBinding?.let {
                callback?.onTimeSubmit(
                    it.numberPickerHours.value,
                    it.numberPickerMinutes.value,
                    it.numberPickerSeconds.value
                )
                this.dismiss()
            }
        }
    }

    override fun onDestroyView() {
        callback = null
        super.onDestroyView()

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TimePickerDialogFragment()
    }
}
