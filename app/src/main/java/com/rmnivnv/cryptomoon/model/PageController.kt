package com.rmnivnv.cryptomoon.model

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by ivanov_r on 17.08.2017.
 */
class PageController {

    private val subject = PublishSubject.create<Int>()

    fun pageSelected(position: Int) = subject.onNext(position)

    fun getPageObservable(): Observable<Int> = subject
}