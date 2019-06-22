package com.maestoso.teleprompter

import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar.inflateMenu(R.menu.menu_main)

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)

        start_button.setOnClickListener {
            val intent = Intent(this, TeleprompterActivity::class.java)
            val theUserString = text_input.text.toString()
            intent.putExtra(resources.getString(R.string.user_string_extra_key), theUserString)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val theSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        val theFontPreviewSetting = theSharedPreferences.getBoolean(
                getString(R.string.pref_key_font_preview), false)

        if( theFontPreviewSetting )
        {
            val theFontSizeSetting = theSharedPreferences.getString(
                    getString(R.string.pref_key_font_size),
                    getString(R.string.pref_default_font_size))!!
                    .toFloat()

            text_input.textSize = theFontSizeSetting
        }
        else
        {
            text_input.textSize = resources.getDimension( R.dimen.default_font_preview_size )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }


    // Button onClick functions

    fun onClickPasteFromClipboard(@Suppress("UNUSED_PARAMETER") aView: View) {
        val theClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var thePasteData = ""

        if ( theClipboardManager.primaryClip != null
                && theClipboardManager.primaryClipDescription != null
                && theClipboardManager.hasPrimaryClip()
                && theClipboardManager.primaryClipDescription!!.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN) )
        {
            val theItem = theClipboardManager.primaryClip?.getItemAt(0)
            thePasteData = theItem?.text.toString()
        }

        if (thePasteData.isNotEmpty())
        {
            text_input.setText(thePasteData)
        }
        else
        {
            Toast.makeText( this, R.string.clipboard_empty_toast_text, Toast.LENGTH_SHORT ).show()
        }
    }

    fun onClickClear(@Suppress("UNUSED_PARAMETER") aView: View) {
        val theEditText: EditText = text_input
        if (theEditText.text.isNotEmpty()) {
            val theDialog = AlertDialog.Builder(this).create().apply {
                setTitle(getString(R.string.dialog_clear_title))
                setMessage(getString(R.string.dialog_clear_confirmation))
                setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_clear_positive)) { _, _ -> theEditText.setText( "" ) }
                setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.dialog_clear_negative)) { _, _ -> }
            }

            theDialog.show()
        }
    }
}
