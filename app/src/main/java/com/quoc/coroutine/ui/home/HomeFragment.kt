package com.quoc.coroutine.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.quoc.coroutine.base.BaseFragment
import com.quoc.coroutine.databinding.FragmentHomeBinding
import com.quoc.coroutine.di.GlideApp
import com.quoc.coroutine.domain.entity.ImageEntity
import com.quoc.coroutine.domain.param.LoadType
import com.quoc.coroutine.util.RecyclerScrollMoreListener
import com.quoc.coroutine.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(),
    RecyclerScrollMoreListener.OnLoadMoreListener {

    override val viewModel: HomeViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = { inflater, parent, attachToParent ->
            FragmentHomeBinding.inflate(inflater, parent, attachToParent)
        }

    private var imageAdapter by autoCleared<ImageAdapter>()
    private var loadingAdapter by autoCleared<LoadingAdapter>()

    override fun setupView() {
        val glide = GlideApp.with(this)
        imageAdapter = ImageAdapter(glide) {
            findNavController().navigate(HomeFragmentDirections.detail(it.id))
        }
        loadingAdapter = LoadingAdapter()

        val linearLayoutManager = LinearLayoutManager(requireContext())
        with(binding.recyclerView) {
            layoutManager = linearLayoutManager
            adapter = ConcatAdapter(imageAdapter, loadingAdapter)
            isNestedScrollingEnabled = false
            addOnScrollListener(
                RecyclerScrollMoreListener(
                    imageAdapter,
                    linearLayoutManager,
                    this@HomeFragment
                )
            )
        }
    }

    override fun bindViewEvents() {
        binding.swipeLayout.setOnRefreshListener {
            viewModel.getImages(LoadType.REFRESH)
        }
    }

    override fun bindViewModel() {
        viewModel.images bindTo ::displayImages
        viewModel.error bindTo ::toast
        viewModel.isLoading bindTo ::loading
    }

    private fun displayImages(images: List<ImageEntity>) {
        imageAdapter.submitList(images)
    }

    private fun loading(isLoading: Boolean) {
        if (!isLoading && binding.swipeLayout.isRefreshing) {
            binding.swipeLayout.isRefreshing = false
        }

        val list = if (isLoading) listOf(isLoading) else emptyList()
        loadingAdapter.submitList(list)
    }

    override fun initData() {
        viewModel.getImages(LoadType.INITIAL)
    }

    override fun onLoadMore(page: Int, total: Int) {
        viewModel.getImages(LoadType.APPEND)
    }

}