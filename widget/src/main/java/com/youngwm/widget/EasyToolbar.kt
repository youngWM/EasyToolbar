package com.youngwm.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginTop

/**
 *
 *  author: coding_fd
 *  time  : 2018/4/12
 *  -----------------
 *  description:
 */
class EasyToolbar : Toolbar {

    private var statusBarHeight: Int = 0
    private var childViewHeight: Int = LayoutParams.WRAP_CONTENT
    private var titleTextView: TextView
    private var titleMaxWidth = 0

    private var tintColor = Color.BLACK
    private var toolbarBgColor = Color.WHITE
    
    var widgetAddMargin = 5
        set(value) {
            field = value
            requestLayout()
        }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        setContentInsetsAbsolute(0, 0)

        if (minimumHeight != 0) {
            childViewHeight = minimumHeight
        }

        val resources = context.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        statusBarHeight = measuredHeight + resources.getDimensionPixelSize(resourceId)
        if (statusBarHeight == 0) {
            statusBarHeight = (24 * context.resources.displayMetrics.density + 0.5).toInt()
        }
        minimumHeight += statusBarHeight
        setPadding(0, statusBarHeight/2, 0, statusBarHeight/2)
        titleTextView = TextView(context).apply {
            addView(this, LayoutParams(LayoutParams.WRAP_CONTENT, childViewHeight, Gravity.CENTER))
            gravity = Gravity.CENTER
            textSize = 18F
            ellipsize = TextUtils.TruncateAt.END
            setSingleLine()
            setTextColor(tintColor)
            addLeftMargin(this)
            addRightMargin(this)
        }
    }

    fun useLightStyle(){
        customUiStyle(toolbarBg = Color.WHITE, tint = Color.BLACK)
    }

    fun useLightTintStyle(){
        customUiStyle(null, tint = Color.BLACK)
    }

    fun useDarkTintStyle(){
        customUiStyle(null, Color.WHITE)
    }

    fun customUiStyle(toolbarBg: Int?, tint:Int){
        tintColor = tint
        setTitleTextColor(tintColor)
        toolbarBg?.let {
            toolbarBgColor = toolbarBg
            setBackgroundColor(toolbarBgColor)
        }

        requestLayout()
    }

    override fun setTitle(title: CharSequence?) {
        titleTextView.text = title
    }

    override fun setTitleTextColor(color: Int) {
        titleTextView.setTextColor(color)
    }

    fun <T : View> addBackView(view: T, block: (T.() -> Unit)?=null): T {
        return view.apply {
            addView(this, LayoutParams(LayoutParams.WRAP_CONTENT, childViewHeight, Gravity.START or Gravity.CENTER_VERTICAL))
            addLeftMargin(this)
            block?.let { it() }
        }
    }

    fun addBackTextView(content: String?=null, block: (TextView.() -> Unit) ?=null): TextView {
        return TextView(context).apply {
            addView(this, LayoutParams(LayoutParams.WRAP_CONTENT, childViewHeight, Gravity.START or Gravity.CENTER_VERTICAL))
            textSize = 16F
            setTextColor(tintColor)
            setSingleLine()
            addLeftMargin(this)
            content?.let { text = it }
            block?.let { it() }
        }
    }

    fun addBackImageView(@DrawableRes resId: Int?=null, block: (ImageView.() -> Unit) ?=null): ImageView {
        return ImageView(context).apply {
            addView(this, LayoutParams(LayoutParams.WRAP_CONTENT, childViewHeight, Gravity.START or Gravity.CENTER_VERTICAL))
            scaleType = ImageView.ScaleType.CENTER_INSIDE
            setColorFilter(tintColor)
            resId?.let {
                setBackgroundResource(it)
                tintDefaultColor()
            }
            addLeftMargin(this)
            block?.let { it() }
        }
    }

    fun addTextViewMenu(content: String?=null, block: (TextView.() -> Unit) ?=null): TextView {
        return TextView(context).apply {
            addView(this, LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, childViewHeight, Gravity.END or Gravity.CENTER_VERTICAL))
            textSize = 16F
            setSingleLine()
            setTextColor(tintColor)
            addRightMargin(this)
            content?.let { text = it }
            block?.let { it() }
        }
    }


    fun addImageViewMenu(@DrawableRes resId: Int?=null, block: (ImageView.() -> Unit) ?=null): ImageView {
        return ImageView(context).apply {
            addView(this, LayoutParams(LayoutParams.WRAP_CONTENT, childViewHeight, Gravity.END or Gravity.CENTER_VERTICAL))
            scaleType = ImageView.ScaleType.CENTER_INSIDE
            addRightMargin(this)
            setColorFilter(tintColor)
            resId?.let {
                setBackgroundResource(it)
                tintDefaultColor()
            }
            block?.let { it() }
        }
    }

    fun <T : View> addViewMenu(view: T, block: (T.() -> Unit) ?=null): T {
        return view.apply {
            addView(this, LayoutParams(LayoutParams.WRAP_CONTENT, childViewHeight, Gravity.END or Gravity.CENTER_VERTICAL))
            addRightMargin(this)
            block?.let { it() }
        }
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        resetTitleView()
    }

    /** 重新计算标题的宽度 */
    private fun resetTitleView() {
        var start = 0
        var end = 0
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            val lp = view.layoutParams as Toolbar.LayoutParams
            if (Gravity.START == lp.gravity or Gravity.START) {
                start += view.measuredWidth + lp.marginStart
            } else if (Gravity.END == lp.gravity or Gravity.END) {
                end += view.measuredWidth + lp.marginEnd
            }
        }

        val width= getScreenWidth() - Math.max(start, end) * 2 - dp2px(16F)
        if (width!=titleMaxWidth){
            titleTextView.maxWidth=width
            titleMaxWidth=width
        }
    }

    private fun dp2px(dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }


    private fun getScreenWidth(): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
            ?: return context.resources.displayMetrics.widthPixels
        val point = Point()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.defaultDisplay.getRealSize(point)
        } else {
            wm.defaultDisplay.getSize(point)
        }
        return point.x
    }

    private fun ImageView.tintDefaultColor(){
        tint(tintColor)
    }

    private fun ImageView.tint(color: Int){
        tintDrawable(background, ColorStateList.valueOf(color))
    }

    private fun tintDrawable(drawable: Drawable, colors: ColorStateList): Drawable {
        val wrappedDrawable = DrawableCompat.wrap(drawable)
        DrawableCompat.setTintList(wrappedDrawable, colors)
        return wrappedDrawable
    }

    private fun addLeftMargin(view: View){
        var params = MarginLayoutParams(0,0)
        if (view.layoutParams != null)
            params = view.layoutParams as MarginLayoutParams
        with(params){
            setMargins(marginStart + widgetAddMargin, marginTop, marginEnd, marginBottom)
            view.layoutParams = this
        }
    }

    private fun addRightMargin(view: View){
        var params = MarginLayoutParams(0,0)
        if (view.layoutParams != null)
            params = view.layoutParams as MarginLayoutParams
        with(params){
            setMargins(marginStart, marginTop, marginEnd + widgetAddMargin, marginBottom)
            view.layoutParams = this
        }
    }



    companion object {
        val TAG = "EasyToolbar"
    }

}
