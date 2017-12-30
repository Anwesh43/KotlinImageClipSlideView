package ui.anwesome.com.imageclipslideview

import android.content.Context
import android.view.*
import android.graphics.*
/**
 * Created by anweshmishra on 31/12/17.
 */
class ImageClipSideView(ctx:Context,var bitmap:Bitmap):View(ctx) {
    val paint:Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas:Canvas) {

    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}