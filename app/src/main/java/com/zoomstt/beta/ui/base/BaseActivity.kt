package com.zoomstt.beta.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.zoomstt.beta.ui.event.UniversalBus
import com.zoomstt.beta.utils.showSnackMessage
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding, M : BaseViewModel>(@LayoutRes val layoutRes: Int) :
    DaggerAppCompatActivity() {
    protected lateinit var binding: T
    protected lateinit var viewModel: M

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    protected abstract fun viewModelClass(): Class<M>

    protected abstract fun T.initView()

    protected abstract fun T.addEvent()

    protected abstract fun M.observeLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.lifecycleOwner = this
        binding.initView()
        binding.addEvent()
        viewModel = ViewModelProvider(this, viewModelFactory).get(viewModelClass())
        viewModel.observeLiveData()

        //EventBus register
        UniversalBus.register(this)
    }

    fun showLoading() {
        // TODO: 12/2/2021 showLoading
    }

    fun hideLoading() {
        // TODO: 12/2/2021 hideLoading
    }

    fun showSnackMessage(view: View, message: String) {
        view.showSnackMessage(message)
    }

    protected fun showSnackMessage(
        view: View,
        message: String,
        action: String,
        callbackAction: () -> Unit
    ) {
        view.showSnackMessage(message, action, callbackAction)
    }

    fun onBack() {
        finish()
    }

    override fun onDestroy() {
        UniversalBus.unregister(this)
        super.onDestroy()
    }
}
