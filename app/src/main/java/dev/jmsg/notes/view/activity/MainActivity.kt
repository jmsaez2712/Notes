package dev.jmsg.notes.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.jmsg.notes.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        Thread.sleep(1000)

        setTheme(R.style.Theme_Notes)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}