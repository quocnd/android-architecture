package com.quoc.coroutine.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.quoc.coroutine.base.BaseFragment
import com.quoc.coroutine.databinding.FragmentHomeBinding
import com.quoc.coroutine.di.GlideApp
import com.quoc.coroutine.domain.entity.ImageEntity
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

    private var adapter by autoCleared<ImageAdapter>()

    override fun setupView() {
        val glide = GlideApp.with(this)
        adapter = ImageAdapter(glide) {
            findNavController().navigate(HomeFragmentDirections.detail(it.id))
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addOnScrollListener(RecyclerScrollMoreListener(layoutManager, this))
    }

    override fun bindViewEvents() {
        binding.swipeLayout.setOnRefreshListener {
            viewModel.getImagesFlow()
        }

    }

    override fun bindViewModel() {
        viewModel.imagesFlow bindTo ::displayImages
        viewModel.error bindTo ::toast
        viewModel.isLoading bindTo ::loading
    }

    private fun displayImages(images: List<ImageEntity>) {
        adapter.submitList(images)
    }

    private fun loading(isLoading: Boolean) {
        binding.swipeLayout.isRefreshing = isLoading
    }

    override fun initData() {
        viewModel.getImagesFlow()
    }

    override fun onLoadMore(page: Int, total: Int) {
        viewModel.getImagesFlow()
    }

}