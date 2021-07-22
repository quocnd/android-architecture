package com.quoc.coroutine.di

import com.quoc.coroutine.ui.dialog.DialogManager
import com.quoc.coroutine.ui.dialog.DialogManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
abstract class DialogModule {

    @Binds
    internal abstract fun bindDialogManager(dialogManagerImpl: DialogManagerImpl): DialogManager
}