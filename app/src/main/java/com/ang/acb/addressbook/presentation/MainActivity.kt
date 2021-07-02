package com.ang.acb.addressbook.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ang.acb.addressbook.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
