package com.learning.android101.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView

class SyncScrollView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ScrollView(context, attrs, defStyleAttr) {

    private var scrollViewListener: ScrollViewListener? = null

    fun setOnScrollviewListener(scrollViewListener: ScrollViewListener) {
        this.scrollViewListener = scrollViewListener
    }

    override fun onScrollChanged(x: Int, y: Int, oldx: Int, oldy: Int) {
        super.onScrollChanged(x, y, oldx, oldy)
        scrollViewListener?.onScrollChanged(this, x, y, oldx, oldy)
    }

    interface ScrollViewListener {
        fun onScrollChanged(scrollView: SyncScrollView, x: Int, y: Int, oldx: Int, oldy: Int)
    }
}