package com.example.remotephonemanager.ui.components

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.widget.ImageView
import com.example.remotephonemanager.R

class ClickableImageView(context: Context, attrs: AttributeSet) : ImageView(context, attrs) {

    var pressedColor : ColorStateList?

    init {
        isClickable = true
        isFocusable = true
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClickableImageView)
        pressedColor = typedArray?.getColorStateList(R.styleable.ClickableImageView_pressedColor)
        typedArray.recycle()
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        val color = pressedColor?.getColorForState(drawableState, Color.TRANSPARENT)

        if (color != null) {
            setColorFilter(color)
        }
        invalidate()
    }
}