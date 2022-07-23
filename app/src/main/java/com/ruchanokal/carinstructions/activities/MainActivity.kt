package com.ruchanokal.carinstructions.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ruchanokal.carinstructions.R
import com.ruchanokal.carinstructions.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val prefences = getSharedPreferences("com.ruchanokal.carinstructions", Context.MODE_PRIVATE)

        val currentAppLocale = Locale.getDefault().getLanguage()
        Log.i(TAG,"currentAppLocale: " + currentAppLocale)

        prefences.edit().putString("lang",currentAppLocale).apply()

    }


}