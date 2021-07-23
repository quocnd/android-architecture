package com.quoc.coroutine.util

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

open class RecyclerScrollMoreListener(
    private val mLayoutManager: RecyclerView.LayoutManager,
    private val loadMoreListener: OnLoadMoreListener?
) : RecyclerView.OnScrollListener() {
    private var currentPage = 0
    private var previousTotalItemCount = 0
    private var loading = true
    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (loadMoreListener != null) {
            var lastVisibleItemPosition = 0
            val totalItemCount: Int = mLayoutManager.itemCount
            when (mLayoutManager) {
                is StaggeredGridLayoutManager -> {
                    val lastVisibleItemPositions: IntArray =
                        mLayoutManager.findLastVisibleItemPositions(null)
                    lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
                }
                is LinearLayoutManager -> {
                    lastVisibleItemPosition =
                        mLayoutManager.findLastVisibleItemPosition()
                }
                is GridLayoutManager -> {
                    lastVisibleItemPosition =
                        mLayoutManager.findLastVisibleItemPosition()
                }
            }
            if (totalItemCount < previousTotalItemCount) {
                currentPage = 0
                previousTotalItemCount = totalItemCount
                if (totalItemCount == 0) {
                    loading = true
                }
            }
            if (loading && totalItemCount > previousTotalItemCount) {
                loading = false
                previousTotalItemCount = totalItemCount
            }
            val visibleThreshold = 5
            if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
                currentPage++
                loadMoreListener.onLoadMore(totalItemCount, totalItemCount)
                loading = true
            }
        }
    }

    open fun resetState() {
        currentPage = 0
        previousTotalItemCount = 0
        loading = true
    }

    interface OnLoadMoreListener {
        fun onLoadMore(page: Int, total: Int)
    }

}