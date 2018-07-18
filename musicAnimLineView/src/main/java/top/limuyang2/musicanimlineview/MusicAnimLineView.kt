package top.limuyang2.musicanimlineview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout


/*
 * 
 * Date: 2018/7/18
 * @author by limuyang
 */
class MusicAnimLineView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

    /**
     * attr参数
     */
    private var animDuration = 500L
    private var lineWidthDP = 0f //单位DP
    private var lineColor = Color.DKGRAY
    private var isAutoPlay = true

    //线条运动的比例
    private val movingDistance = 0.5f

    private var isLine1Out = false
    private var isLine2Out = true
    private var isLine3Out = false
    private var isLine4Out = true

    /**
     * 懒加载lineView
     */
    private val line1 by lazy {
        val view = VerticalLineView(context)
        view.color = this.lineColor
        view.lineWidth = dp2px(this.lineWidthDP, context)
        view
    }
    private val line2 by lazy {
        val view = VerticalLineView(context)
        view.color = this.lineColor
        view.lineWidth = dp2px(this.lineWidthDP, context)
        view
    }
    private val line3 by lazy {
        val view = VerticalLineView(context)
        view.color = this.lineColor
        view.lineWidth = dp2px(this.lineWidthDP, context)
        view
    }
    private val line4 by lazy {
        val view = VerticalLineView(context)
        view.color = this.lineColor
        view.lineWidth = dp2px(this.lineWidthDP, context)
        view
    }

    /**
     * 懒加载 线条高度
     */
    private val line1Height by lazy { measuredHeight * (2f / 5f) }
    private val line2Height by lazy { measuredHeight * (1f / 3f) }
    private val line3Height by lazy { measuredHeight / 2f }
    private val line4Height by lazy { measuredHeight * (1f / 5f) }

    /**
     * 懒加载 线条Params
     */
    private val params1 by lazy {
        val p = LinearLayout.LayoutParams(0, measuredHeight + 10, 1f)
        p.topMargin = measuredHeight - line1Height.toInt()
        p
    }
    private val params2 by lazy {
        val p = LinearLayout.LayoutParams(0, measuredHeight + 10, 1f)
        p.topMargin = measuredHeight - (line2Height + (measuredHeight) * movingDistance).toInt()
        p
    }
    private val params3 by lazy {
        val p = LinearLayout.LayoutParams(0, measuredHeight + 10, 1f)
        p.topMargin = measuredHeight - line3Height.toInt()
        p
    }
    private val params4 by lazy {
        val p = LinearLayout.LayoutParams(0, measuredHeight + 10, 1f)
        p.topMargin = measuredHeight - (line4Height + (measuredHeight) * movingDistance).toInt()
        p
    }

    init {
        initAttrs(context, attrs)
        initView()
    }

    private fun initView() {
        this.orientation = LinearLayout.HORIZONTAL
        this.addView(line1)
        this.addView(line2)
        this.addView(line3)
        this.addView(line4)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        //拿到设置的属性
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MusicAnimLineView)
        animDuration = typedArray.getInteger(R.styleable.MusicAnimLineView_animDuration, animDuration.toInt()).toLong()
        isAutoPlay = typedArray.getBoolean(R.styleable.MusicAnimLineView_isAutoPlay, isAutoPlay)
        lineWidthDP = typedArray.getDimension(R.styleable.MusicAnimLineView_line_width, lineWidthDP)
        lineColor = typedArray.getColor(R.styleable.MusicAnimLineView_line_color, lineColor)

        typedArray.recycle()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        //在此方法中更新 Params ，measuredHeight此时可以正确获取到高度
        line1.layoutParams = params1
        line2.layoutParams = params2
        line3.layoutParams = params3
        line4.layoutParams = params4


    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        if (isAutoPlay && visibility == View.VISIBLE) {
            startAnim()
        }
    }

    /**
     * 开始动画
     */
    fun startAnim() {
        line1Anim()
        line2Anim()
        line3Anim()
        line4Anim()
    }

    /*** 四个线条的动画  start ***/
    private fun line1Anim() {
        val animation = if (isLine1Out) {
            getDownAnim()
        } else {
            getUpAnim()
        }

        animation.duration = animDuration
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                //动画结束时，更新topMargin布局参数
                val p = line1.layoutParams as LinearLayout.LayoutParams
                p.topMargin = if (isLine1Out) {
                    measuredHeight - line1Height.toInt()
                } else {
                    (p.topMargin - (measuredHeight) * movingDistance).toInt()
                }
                //清除动画防止闪屏
                line1.clearAnimation()
                line1.layoutParams = p

                isLine1Out = !isLine1Out

                line1Anim()
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })

        line1.startAnimation(animation)
    }

    private fun line2Anim() {
        val animation = if (isLine2Out) {
            getDownAnim()
        } else {
            getUpAnim()
        }

        animation.duration = animDuration
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                val p = line2.layoutParams as LinearLayout.LayoutParams
                p.topMargin = if (isLine2Out) {
                    p.topMargin + (measuredHeight * movingDistance).toInt()
                } else {
                    measuredHeight - (line2Height + measuredHeight * movingDistance).toInt()
                }

                line2.clearAnimation()
                line2.layoutParams = p

                isLine2Out = !isLine2Out

                line2Anim()
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })

        line2.startAnimation(animation)
    }

    private fun line3Anim() {
        val animation = if (isLine3Out) {
            getDownAnim()
        } else {
            getUpAnim()
        }

        animation.duration = animDuration
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                val p = line3.layoutParams as LinearLayout.LayoutParams
                p.topMargin = if (isLine3Out) {
                    measuredHeight - line3Height.toInt()
                } else {
                    (p.topMargin - (measuredHeight) * movingDistance).toInt()
                }

                line3.clearAnimation()
                line3.layoutParams = p

                isLine3Out = !isLine3Out

                line3Anim()
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })

        line3.startAnimation(animation)
    }

    private fun line4Anim() {
        val animation = if (isLine4Out) {
            getDownAnim()
        } else {
            getUpAnim()
        }

        animation.duration = animDuration
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                val p = line4.layoutParams as LinearLayout.LayoutParams
                p.topMargin = if (isLine4Out) {
                    p.topMargin + (measuredHeight * movingDistance).toInt()
                } else {
                    measuredHeight - (line4Height + measuredHeight * movingDistance).toInt()
                }

                line4.clearAnimation()
                line4.layoutParams = p

                isLine4Out = !isLine4Out

                line4Anim()
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })

        line4.startAnimation(animation)
    }

    /*** 四个线条的动画  end ***/

    private fun getDownAnim(): Animation {
        return TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, movingDistance - 0.01f
        )
    }

    private fun getUpAnim(): Animation {
        return TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -movingDistance + 0.01f
        )
    }

    companion object {
        private fun dp2px(dpValue: Float, context: Context): Float {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f)
        }
    }
}