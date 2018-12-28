package com.maestoso.teleprompter

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_settings )
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled( true )
        supportActionBar?.setDisplayHomeAsUpEnabled( true )

        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, SettingsFragment())
                .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)
        }

        override fun onResume() {
            super.onResume()
            preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener( this )
        }

        override fun onPause() {
            super.onPause()
            preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener( this )
        }

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
            when(key){
                "pref_scroll_speed_key" -> {
                    // val thePreference = preferenceManager.findPreference(key) as EditTextPreference
                    // thePreference.summary = thePreference.text
                }
            }
        }

        override fun onDisplayPreferenceDialog(preference: Preference?) {
            val theFragment: DialogFragment
            if( preference is NumberPickerPreference )
            {
                theFragment = NumberPickerDialogFragmentCompat.newInstance(preference)
                theFragment.setTargetFragment( this, 0 )
                theFragment.show( fragmentManager,
                        "android.support.v7.preference.PreferenceFragment.DIALOG" )
            }
            else
            {
                super.onDisplayPreferenceDialog(preference)
            }
        }
    }
}