package com.rmnivnv.cryptomoon.model.rxbus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by rmnivnv on 25/07/2017.
 */

object RxBus {

    private val publisher = PublishSubject.create<Any>()

    fun publish(event: Any) = publisher.onNext(event)

    // Listen should return an Observable and not the publisher
    // Using ofType we filter only events that match that class type
    fun <T> listen(eventType: Class<T>): Observable<T> = publisher.ofType(eventType)
}