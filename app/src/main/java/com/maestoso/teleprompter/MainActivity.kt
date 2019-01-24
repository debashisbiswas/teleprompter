package com.maestoso.teleprompter

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
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar.inflateMenu(R.menu.menu_main)

        val theButton = findViewById<Button>(R.id.start_button)
        theButton?.setOnClickListener {
            val intent = Intent(this, TeleprompterActivity::class.java)
            val theUserString = text_input.text.toString()
            intent.putExtra(resources.getString(R.string.user_string_extra_key), theUserString)
            startActivity(intent)
        }

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
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

        if (theClipboardManager.hasPrimaryClip()) {
            val theItem = theClipboardManager.primaryClip.getItemAt(0)
            thePasteData = theItem.text.toString()
        }

        if (thePasteData.isNotEmpty()) {
            text_input.setText(thePasteData)
        }
    }

    fun onClickClear(@Suppress("UNUSED_PARAMETER") aView: View) {
        val theEditText: EditText = text_input
        if (theEditText.text.isNotEmpty()) {
            val theDialog = AlertDialog.Builder(this).create().apply {
                setTitle("Confirm")
                setMessage("Are you sure you want to clear the text?")
                setButton(AlertDialog.BUTTON_POSITIVE, "Yes") { _, _ -> theEditText.setText( "" ) }
                setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { _, _ -> }
            }

            theDialog.show()

        }
    }
}
