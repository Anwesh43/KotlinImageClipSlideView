package ui.anwesome.com.kotlinimageclipslideview

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.imageclipslideview.ImageClipSideView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImageClipSideView.create(this,BitmapFactory.decodeResource(resources,R.drawable.trphy))
    }
}
