package ui.anwesome.com.imageclipslideview

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.hardware.display.DisplayManager

/**
 * Created by anweshmishra on 31/12/17.
 */
class DimensionUtil {
    companion object {
        fun getSize(activity:Activity):Point {
            val point = Point()
            val displayManager = activity.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
            val display = displayManager?.getDisplay(0)
            display?.getRealSize(point)
            return point
        }
    }
}