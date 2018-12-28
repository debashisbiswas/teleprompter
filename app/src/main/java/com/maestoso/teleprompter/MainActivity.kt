package com.maestoso.teleprompter

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
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
            val theTextField = findViewById<EditText>(R.id.text_input)
            val theUserString = theTextField.text.toString()
            intent.putExtra(resources.getString(R.string.user_string_extra_key), theUserString)
            startActivity(intent)
        }

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate( R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            val intent = Intent( this, SettingsActivity::class.java )
            startActivity( intent )
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}
