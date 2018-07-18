package top.limuyang2.musicanimlineview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/* 
 * 
 * Date: 2018/7/18
 * @author by limuyang
 */
class VerticalLineView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val mPaint by lazy {
        val paint = Paint()
        //设置画笔颜色
        paint.color = this.color
        //设置画笔模式
        paint.style = Paint.Style.FILL
//        paint.strokeWidth = lineWidth

        paint
    }

    private val rectF by lazy {
        val left = (width - lineWidth) / 2f
        val right = left + lineWidth
        RectF(left, 0f, right, height.toFloat())
    }

    var color = Color.DKGRAY
        set(value) {
            field = value
            mPaint.color = value
        }

    var lineWidth = 0f //单位PX

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //未设置线宽时，则设置默认宽度
        if (lineWidth == 0f) lineWidth = (width / 4).toFloat()

        canvas?.drawRoundRect(rectF, lineWidth / 2, lineWidth / 2, mPaint)
    }
}