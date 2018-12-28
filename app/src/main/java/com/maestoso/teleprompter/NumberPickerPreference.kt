package com.maestoso.teleprompter

import android.content.Context
import android.content.res.TypedArray
import android.support.v7.preference.DialogPreference
import android.util.AttributeSet

class NumberPickerPreference : DialogPreference {
    var mPickerValue: Int = 0

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?) : super(context)
    fun setNumber(value: Int) {
        val wasBlocking = shouldDisableDependents()

        mPickerValue = value

        persistInt(value)

        val isBlocking = shouldDisableDependents()
        if (isBlocking != wasBlocking) {
            notifyDependencyChange(isBlocking)
        }
    }

    override fun onSetInitialValue(restoreValue: Boolean, defaultValue: Any?) {
        setNumber(if (restoreValue) getPersistedInt(mPickerValue) else defaultValue as Int)
    }

    companion object {
        const val MAX_VALUE = 5
        const val MIN_VALUE = 0
        const val WRAP_SELECTOR_WHEEL = false
    }
}