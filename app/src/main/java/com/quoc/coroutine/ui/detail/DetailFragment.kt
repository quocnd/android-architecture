package com.quoc.coroutine.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.quoc.coroutine.base.BaseFragment
import com.quoc.coroutine.databinding.FragmentDetailBinding
import com.quoc.coroutine.domain.entity.ImageEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val params: DetailFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding
        get() = { layoutInflater, viewGroup, attachToParent ->
            FragmentDetailBinding.inflate(layoutInflater, viewGroup, attachToParent)
        }

    override val viewModel: DetailViewModel by viewModels()

    override fun setupView() {

    }

    override fun bindViewEvents() {

    }

    override fun bindViewModel() {
        viewModel.imageDetail bindTo ::displayImageDetail
        viewModel.error bindTo ::toast
    }

    private fun displayImageDetail(imageEntity: ImageEntity) {
        if (URLUtil.isNetworkUrl(imageEntity.downloadUrl)) {
            Glide.with(requireContext())
                .load(imageEntity.downloadUrl)
                .into(binding.ivImage)
        }
        binding.tvAuthor.text = imageEntity.author
    }

    override fun initData() {
        super.initData()
        if (params.imageId.isNotEmpty()) {
            viewModel.getDetail(params.imageId)
        }
    }

}