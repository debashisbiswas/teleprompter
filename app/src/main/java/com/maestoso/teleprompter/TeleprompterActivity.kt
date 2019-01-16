package com.maestoso.teleprompter

import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_teleprompter.*

class TeleprompterActivity : AppCompatActivity() {

    val mHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        setContentView(R.layout.activity_teleprompter)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val theSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val thePaddingEnabledSetting = theSharedPreferences.getBoolean(
                getString(R.string.pref_key_padding_switch), false)
        var theText = intent.extras.getString(resources.getString(R.string.user_string_extra_key))
        if( thePaddingEnabledSetting )
        {
            val thePaddingAboveSetting = theSharedPreferences.getInt(
                    getString(R.string.pref_key_padding_above), 0)
            val thePaddingBelowSetting = theSharedPreferences.getInt(
                    getString(R.string.pref_key_padding_below), 0)
            val thePaddingCharacter = "\n"

            val thePaddingAbove = thePaddingCharacter.repeat( thePaddingAboveSetting )
            val thePaddingBelow = thePaddingCharacter.repeat( thePaddingBelowSetting )
            theText = thePaddingAbove + theText + thePaddingBelow
        }

        val theFontSizeSetting = theSharedPreferences.getString(
                getString(R.string.pref_key_font_size),
                getString(R.string.pref_default_font_size))
                .toFloat()

        text_view.textSize = theFontSizeSetting
        text_view.text = theText
    }

    fun beginScrolling(@Suppress("UNUSED_PARAMETER") aView: View) {
        overlay.visibility = View.INVISIBLE

        val theSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val theSpeedSetting = theSharedPreferences.getInt(getString(R.string.pref_key_scroll_speed), 0)

        // for future reference: here's how this works
        // theMaxDelay is the slowest delay. The scroll bar's speed setting is subtracted from that
        // higher speed setting -> less delay -> faster scroll
        // scrolling by 1 every call is unnecessarily demanding and causes skipping
        val theMaxDelay = 25
        val theScrollAmountPerCall = 3

        val ms: Long = (theMaxDelay - theSpeedSetting).toLong()
        mHandler.postDelayed(object : Runnable {
            override fun run() {
                scroll_view.smoothScrollBy(0, theScrollAmountPerCall)
                mHandler.postDelayed(this, ms)
            }

        }, ms)
    }

    fun pauseScrolling(@Suppress("UNUSED_PARAMETER") aView: View) {
        // removes all
        mHandler.removeCallbacksAndMessages(null)

        overlay.visibility = View.VISIBLE
    }

    private fun hideStatusBar() {
        // hide status bar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        // hide action bar just in case it is still visible
        // should not be visible because of the NoActionBar style
        actionBar?.hide()
    }
}
