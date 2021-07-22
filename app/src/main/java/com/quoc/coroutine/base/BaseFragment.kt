package com.quoc.coroutine.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.quoc.coroutine.R
import com.quoc.coroutine.ui.dialog.DialogManager
import com.quoc.coroutine.util.autoCleared
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding> : Fragment(), IBaseFragment {

    @Inject
    lateinit var dialogManager: DialogManager

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    var binding by autoCleared<VB>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindingInflater.invoke(inflater, container, false).apply {
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (this as? IBaseFragment)?.let {
            setupView()
            bindViewEvents()
            bindViewModel()
            bindBaseEvents()
            initData()
        }
    }

    private fun bindBaseEvents(){
        viewModel.isUnauthorized bindTo ::onUnauthorized
    }

    private fun onUnauthorized(isUnauthorized: Boolean){
        if(isUnauthorized) {
            dialogManager.showDialogTwoButton(
                getString(R.string.unauthorized_title),
                getString(R.string.unauthorized_message)
            ) { _, _ ->
                handleUnauthorized()
            }
        }
    }

    private fun handleUnauthorized(){
        // TODO clear cache
    }

    // Step 2
    protected open fun initData() = Unit


    protected fun toast(msg: String?) {
        dialogManager.toast(msg)
    }

    protected inline infix fun <T> Flow<T>.bindTo(crossinline action: (T) -> Unit){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                collect {
                    action.invoke(it)
                }
            }
        }
    }

}