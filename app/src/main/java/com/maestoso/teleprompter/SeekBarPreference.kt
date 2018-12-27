package com.maestoso.teleprompter

import android.content.Context
import android.content.res.TypedArray
import android.preference.DialogPreference
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView

class SeekBarPreference : DialogPreference, SeekBar.OnSeekBarChangeListener {
    // TODO: Clean this up for Kotlin and add attrs.xml for constants (min, max, etc.)
    private var seekbar: SeekBar? = null
    private var textview: TextView? = null
    var value: Int = 0
        set(value) {
            field = value
            persistInt(this.value)
        }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onCreateDialogView(): View {
        val seekbarLayoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val textviewLayoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        textviewLayoutParams.gravity = Gravity.CENTER

        seekbar = SeekBar(context)
        seekbar!!.layoutParams = seekbarLayoutParams
        seekbar!!.setOnSeekBarChangeListener( this )

        textview = TextView(context)
        textview!!.layoutParams = textviewLayoutParams
        textview!!.gravity = Gravity.CENTER
        textview!!.text = seekbar!!.progress.toString()
        textview!!.textSize = 20F

        val layout = LinearLayout( context )
        layout.orientation = LinearLayout.VERTICAL

        layout.addView( textview )
        layout.addView( seekbar )

        return layout
    }

    override fun onBindDialogView(view: View) {
        super.onBindDialogView(view)
        seekbar!!.left = MIN_VALUE
        seekbar!!.max = MAX_VALUE
        seekbar!!.progress = value
    }

    override fun onDialogClosed(positiveResult: Boolean) {
        if (positiveResult) {
            seekbar!!.clearFocus()
            val newValue = seekbar!!.progress
            if (callChangeListener(newValue)) {
                value = newValue
            }
        }
    }

    override fun onGetDefaultValue(a: TypedArray, index: Int): Any {
        return a.getInt(index, MIN_VALUE)
    }

    override fun onSetInitialValue(restorePersistedValue: Boolean, defaultValue: Any) {
        value = if (restorePersistedValue) getPersistedInt(MIN_VALUE) else defaultValue as Int
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        textview!!.text = seekbar!!.progress.toString()
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {

    }

    override fun onStopTrackingTouch(p0: SeekBar?) {

    }

    companion object {
        // allowed range
        val MAX_VALUE = 10
        val MIN_VALUE = 0
    }
}