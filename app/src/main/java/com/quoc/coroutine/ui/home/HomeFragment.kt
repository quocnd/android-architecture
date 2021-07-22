package com.quoc.coroutine.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.quoc.coroutine.base.BaseFragment
import com.quoc.coroutine.data.data.ImageData
import com.quoc.coroutine.databinding.FragmentHomeBinding
import com.quoc.coroutine.di.GlideApp
import com.quoc.coroutine.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

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
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    override fun bindViewEvents() {
        binding.swipeLayout.setOnRefreshListener {
            viewModel.getImagesPaging()
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest {
                Timber.d("State: ${it.mediator?.refresh}")
                binding.swipeLayout.isRefreshing = it.mediator?.refresh is LoadState.Loading
            }
        }
    }

    override fun bindViewModel() {
        viewModel.images bindTo ::displayImages
        viewModel.error bindTo ::toast
    }

    private fun displayImages(images: PagingData<ImageData>) {
        lifecycleScope.launch {
            adapter.submitData(images)
        }
    }

    override fun initData() {
        viewModel.getImagesPaging()
    }

}