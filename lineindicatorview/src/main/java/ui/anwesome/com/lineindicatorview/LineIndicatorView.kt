package ui.anwesome.com.lineindicatorview

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.hardware.display.DisplayManager
import android.view.View
import android.view.ViewGroup

/**
 * Created by anweshmishra on 01/01/18.
 */
class LineIndicatorView(ctx:Context):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val line = Line()
    override fun onDraw(canvas:Canvas) {
        line.draw(canvas, paint)
    }
    fun update(scale:Float) {
        line.update(scale)
        invalidate()
    }
    data class Line(var scale:Float = 0f) {
        fun draw(canvas:Canvas,paint:Paint) {
            val w = canvas.width.toFloat()
            val h = canvas.height.toFloat()
            paint.strokeWidth = h/2
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = Color.parseColor("#00C853")
            if(scale != 0f) {
                canvas.drawLine(w / 20, h / 2, (19 * w / 20) * scale, h / 2, paint)
            }
        }
        fun update(scale:Float) {
            this.scale = scale
        }
    }
    companion object {
        fun create(activity: Activity):LineIndicatorView {
            val view = LineIndicatorView(activity)
            val displayManager:DisplayManager = activity.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
            val display = displayManager?.getDisplay(0)
            val size = Point()
            display.getRealSize(size)
            var w = size.x
            var h = size.y
            activity.addContentView(view, ViewGroup.LayoutParams(w,h/15))
            view.x = 0f
            view.y = 0.7f*h
            return view
        }
    }
}