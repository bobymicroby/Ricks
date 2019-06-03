package dev.boby.ricks.util

import android.app.Activity
import android.os.Build
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.ricks.R

fun Activity.lightStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.decorView.systemUiVisibility = android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = ContextCompat.getColor(this, R.color.white);
    }
}


fun TextView.setChar(text: Char?) {
    if (text == null) {
        this.text = null
    } else {
        this.text = text.toString()
    }
}