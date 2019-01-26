package com.rmnivnv.cryptomoon.ui.settings

import android.content.Context
import com.rmnivnv.cryptomoon.di.PerActivity
import com.rmnivnv.cryptomoon.model.Preferences
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import dagger.Module
import dagger.Provides

/**
 * Created by ivanov_r on 28.12.2017.
 */
@Module
class SettingsModule {

    @Provides @PerActivity
    fun provideView(settingsActivity: SettingsActivity): Settings.View = settingsActivity

    @Provides @PerActivity
    fun providePresenter(
        view: Settings.View,
        context: Context,
        resourceProvider: ResourceProvider,
        preferences: Preferences
    ): Settings.Presenter = SettingsPresenter(view, context, resourceProvider, preferences)
}