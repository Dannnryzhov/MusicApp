package com.example.musicapp.presentation.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.musicapp.R
import com.example.musicapp.presentation.ui.fragments.MainTracksFragment

class MainTracksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_tracks)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_main_container, MainTracksFragment())
                .commit()
        }
    }
}
