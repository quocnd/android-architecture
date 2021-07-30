package com.quoc.coroutine.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.quoc.coroutine.base.BaseFragment
import com.quoc.coroutine.databinding.FragmentDetailBinding
import com.quoc.coroutine.domain.model.ImageModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    @Inject
    lateinit var baseUrl: String
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

    private fun displayImageDetail(imageModel: ImageModel) {
        if (URLUtil.isNetworkUrl(imageModel.downloadUrl)) {
            Glide.with(requireContext())

                .load(imageModel.getThumbnailUrl(baseUrl))
                .into(binding.ivImage)
        }
        binding.tvAuthor.text = imageModel.author
    }

    override fun initData() {
        super.initData()
        if (params.imageId.isNotEmpty()) {
            viewModel.getDetail(params.imageId)
        }
    }

}