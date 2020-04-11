package com.learning.android101.interfaces

interface SyncScrollListener {
    fun scrollTo(y: Int)

    fun getScrollYPos() : Int
}