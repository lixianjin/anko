package com.lxj.ankosqlit.commons.misc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import org.jetbrains.anko.setContentView

class MiscActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MiscActivityUI().setContentView(this)
    }
}
