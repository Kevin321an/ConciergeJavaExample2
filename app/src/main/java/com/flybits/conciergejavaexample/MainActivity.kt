package com.flybits.conciergejavaexample

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flybits.conciergejavaexample.R
import android.app.Activity
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.flybits.android.kernel.models.Content
import com.flybits.commons.library.api.results.callbacks.ObjectResultCallback
import com.flybits.commons.library.exceptions.FlybitsException
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val fab = findViewById<FloatingActionButton>(R.id.fab);
        startActivity(Intent(this, RTFScreen::class.java))
    }
}