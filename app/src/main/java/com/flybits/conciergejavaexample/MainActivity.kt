package com.flybits.conciergejavaexample

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flybits.conciergejavaexample.R
import android.app.Activity
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
        grantPermission(this)
        val fab = findViewById<FloatingActionButton>(R.id.fab);
        fab.setOnClickListener {
            val content = Content.getById(applicationContext,"0DBB4649-5E82-49D3-9538-94EF50036178", object :
                    ObjectResultCallback<Content> {
                override fun onException(exception: FlybitsException) {
                    println("get content onException")
                }

                override fun onSuccess(item: Content) {
                    println("get content onSuccess  ${item.toString()}")
                }
            },true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    fun grantPermission(activity: Activity?) {
        ActivityCompat.requestPermissions(
                activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION), 123
        )
    }

    val EXTRA_PUSH_NOTIFICATION = "com.flybits.android.push.services.push_notification"
}