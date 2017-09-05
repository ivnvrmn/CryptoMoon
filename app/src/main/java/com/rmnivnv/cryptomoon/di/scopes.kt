package com.rmnivnv.cryptomoon.di

import javax.inject.Scope

/**
 * Created by ivanov_r on 05.09.2017.
 */

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class PerActivity

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class PerFragment