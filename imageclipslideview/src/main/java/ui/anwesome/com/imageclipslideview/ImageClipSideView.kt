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

            canvas.restore()
        }
    }
    data class ImageClipSideState(var w:Float,var maxW:Float,var x:Float = -w,var scale:Float = 0f,var dir:Float = 0f,var prevDir:Float = 1f,var prevScale:Float = 0f) {
        fun startUpdating(startcb:()->Unit) {
            dir = prevDir
            startcb()
        }
        fun update(updatecb:(Float)->Unit,stopcb:(Float)->Unit) {
            scale += dir*0.1f
            updatecb(scale)
            x+= w*scale
            if(Math.abs(scale - prevScale) > 1) {
                prevScale = scale+dir
                dir = 0f
                stopcb(scale)
                if(x + w >= maxW) {
                    prevDir *= -1
                }

            }
        }
    }
}