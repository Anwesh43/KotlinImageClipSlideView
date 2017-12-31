package ui.anwesome.com.imageclipslideview

import android.app.Activity
import android.content.Context
import android.view.*
import android.graphics.*
/**
 * Created by anweshmishra on 31/12/17.
 */
class ImageClipSideView(ctx:Context,var bitmap:Bitmap):View(ctx) {
    val paint:Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val renderer = ImageClipSideRenderer(this)
    override fun onDraw(canvas:Canvas) {
        canvas.drawColor(Color.parseColor("#212121"))
        renderer.render(canvas,paint)
    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }
    data class ImageClipSide(var x:Float,var w:Float,var ox:Float = x) {
        fun draw(canvas:Canvas,paint:Paint,bitmap:Bitmap,x:Float,pw:Float) {
            this.x = x
            paint.color = Color.parseColor("#212121")
            canvas.drawRect(RectF(x,0f,x+w,bitmap.height.toFloat()),paint)
            canvas.save()
            val path = Path()
            path.addRect(RectF(x,0f,x+w,bitmap.height.toFloat()),Path.Direction.CW)
            canvas.clipPath(path)
            canvas.drawBitmap(bitmap,pw,0f,paint)
            canvas.restore()
        }
    }
    data class ImageClipSideContainer(var w:Float,var h:Float,var bitmap: Bitmap,var n:Int=4) {
        val state = ImageClipSideState(w/n,w)
        var curr:ImageClipSide?=null
        fun draw(canvas:Canvas,paint:Paint) {
            curr?.draw(canvas,paint,bitmap,state.x,state.x-state.prevX)
            canvas.save()
            val path = Path()
            path.addRect(RectF(0f,0f,state.x+state.w,h),Path.Direction.CW)
            canvas.clipPath(path)
            canvas.drawBitmap(bitmap,0f,0f,paint)
            canvas.restore()
        }
        fun update(updatecb:(Float)->Unit,stopcb:(Float)->Unit) {
            state.update(updatecb,{
                stopcb(it)
                curr = null
            })
        }
        fun startUpdating(startcb:()->Unit) {
            state.startUpdating({
                curr = ImageClipSide(state.x,state.w)
                startcb()

            })
        }
    }
    data class ImageClipSideRenderer(var view:ImageClipSideView,var time:Int = 0) {
        var container:ImageClipSideContainer?=null
        val animator = Animator(view)
        fun render(canvas:Canvas,paint:Paint) {
            if(time == 0) {
                val w = canvas.width.toFloat()
                val h = canvas.height.toFloat()
                val bitmap = Bitmap.createScaledBitmap(view.bitmap,w.toInt(),h.toInt(),true)
                container = ImageClipSideContainer(w,h,bitmap)
            }
            container?.draw(canvas,paint)
            time++
            animator.update({
                container?.update({},{
                    animator.stop()
                })
            })
        }
        fun handleTap() {
            container?.startUpdating{
                animator.start()
            }
        }
    }
    data class Animator(var view:ImageClipSideView,var animated:Boolean = false) {
        fun update(cb:()->Unit) {
            if(animated) {
                cb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch(ex:Exception) {

                }
            }
        }
        fun start() {
            if(!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop() {
            if(animated) {
                animated = false
            }
        }
    }
    data class ImageClipSideState(var w:Float,var maxW:Float,var x:Float = -w,var scale:Float = 0f,var dir:Float = 1f,var prevX:Float = x) {
        fun startUpdating(startcb:()->Unit) {
            startcb()
            scale = 0f
        }
        fun update(updatecb:(Float)->Unit,stopcb:(Float)->Unit) {
            scale += 0.1f
            updatecb(scale)
            x = prevX + w*scale*dir
            if(scale > 1f) {
                scale = 1f
                x = prevX + w*dir
                prevX = x
                stopcb(dir)
                if(x + w >= maxW || x<=-w) {
                    dir*=-1
                }

            }
        }
    }
    companion object {
        fun create(activity:Activity,bitmap:Bitmap):ImageClipSideView {
            val view = ImageClipSideView(activity,bitmap)
            val size = DimensionUtil.getSize(activity)
            activity.addContentView(view,ViewGroup.LayoutParams(size.x,size.x))
            return view
        }
    }
}