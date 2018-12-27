package com.maestoso.teleprompter

import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
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

        text_view.text =
                "\n\n\n" +
               intent.extras.getString(resources.getString(R.string.user_string_extra_key)) +
                "\n\n\n"
        // TODO: add custom amount of padding
        // TODO: Add developer settings to show a placeholder
    }

    fun beginScrolling(aView: View)
    {
        overlay.visibility = View.INVISIBLE

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val theSpeedSetting: Long = prefs.getInt( getString( R.string.scroll_speed_key ), 0 ).toLong()

        Log.e( "Debashis", theSpeedSetting.toString())

        val ms : Long = 19 - theSpeedSetting
        mHandler.postDelayed( object:Runnable {
            override fun run() {
                scroll_view.smoothScrollBy(0, 3)
                mHandler.postDelayed(this, ms)
            }

        }, ms)
    }

    fun pauseScrolling(aView: View)
    {
        // removes all
        mHandler.removeCallbacksAndMessages( null )

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
