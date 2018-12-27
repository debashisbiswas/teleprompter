package com.maestoso.teleprompter

import android.os.Bundle
import android.preference.PreferenceFragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*
import java.util.prefs.PreferenceChangeEvent
import java.util.prefs.PreferenceChangeListener

class SettingsActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_settings )
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled( true )
        supportActionBar?.setDisplayHomeAsUpEnabled( true )

        if( fragment_container != null )
        {
            if( savedInstanceState != null )
            {
                return
            }

            fragmentManager.beginTransaction().add( R.id.fragment_container, SettingsFragment() ).commit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    class SettingsFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            addPreferencesFromResource(R.xml.preferences)
        }
    }
}