package com.maestoso.teleprompter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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


}