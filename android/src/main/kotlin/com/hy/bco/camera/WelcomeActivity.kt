package com.hy.bco.camera

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.hello.R
import kotlinx.android.synthetic.main.activity_welcome.*


class WelcomeActivity : Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        bgIV.setOnClickListener(View.OnClickListener {
            var intent = Intent();
            intent.putExtra("close","close");
            setResult(18,intent)
            finish()
        }
        )
    }


}