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
    data class ImageClipSide(var x:Float,var w:Float,var ox:Float = x) {
        fun draw(canvas:Canvas,paint:Paint,bitmap:Bitmap,scale:Float) {
            x = ox + w*scale
            canvas.save()
            val path = Path()
            path.addRect(RectF(x,0f,x+w,bitmap.width.toFloat()),Path.Direction.CW)
            canvas.clipPath(path)
            canvas.drawBitmap(bitmap,-w*scale,0f,paint)
            canvas.restore()
        }
    }
}