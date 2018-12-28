package com.maestoso.teleprompter

import android.content.Context
import android.os.Bundle
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceDialogFragmentCompat
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.NumberPicker

class NumberPickerDialogFragmentCompat : PreferenceDialogFragmentCompat() {

    private var mPicker: NumberPicker? = null
    private var mPickerValue: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPickerValue = getNumberPickerPreference().mPickerValue
    }

    override fun onCreateDialogView(context: Context): View {
        val layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER

        mPicker = NumberPicker(context)
        mPicker!!.layoutParams = layoutParams

        val dialogView = FrameLayout(context)
        dialogView.addView(mPicker)

        mPicker!!.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        return dialogView
    }

    override fun onBindDialogView(view: View) {
        super.onBindDialogView(view)
        mPicker!!.minValue = NumberPickerPreference.MIN_VALUE
        mPicker!!.maxValue = NumberPickerPreference.MAX_VALUE
        mPicker!!.wrapSelectorWheel = NumberPickerPreference.WRAP_SELECTOR_WHEEL
        mPicker!!.value = mPickerValue
    }

    override fun onDialogClosed(positiveResult: Boolean) {
        if (positiveResult) {
            mPicker!!.clearFocus()
            val value = mPicker!!.value
            if (getNumberPickerPreference().callChangeListener(value)) {
                getNumberPickerPreference().setNumber(value)
            }
        }
    }


    private fun getNumberPickerPreference(): NumberPickerPreference {
        return preference as NumberPickerPreference
    }

    companion object {
        fun newInstance(preference: Preference): NumberPickerDialogFragmentCompat {
            val theFragmentCompat = NumberPickerDialogFragmentCompat()
            val theBundle = Bundle(1)
            theBundle.putString("key", preference.key)
            theFragmentCompat.arguments = theBundle
            return theFragmentCompat
        }
    }
}