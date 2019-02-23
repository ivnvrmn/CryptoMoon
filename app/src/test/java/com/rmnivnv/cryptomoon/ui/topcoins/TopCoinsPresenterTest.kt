package com.rmnivnv.cryptomoon.ui.topcoins

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import com.rmnivnv.cryptomoon.model.data.TopCoinEntity
import com.rmnivnv.cryptomoon.model.network.Result
import com.rmnivnv.cryptomoon.model.network.data.TopCoinsResponse
import com.rmnivnv.cryptomoon.utils.Logger
import com.rmnivnv.cryptomoon.utils.Toaster
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TopCoinsPresenterTest {

    @Mock lateinit var view: TopCoinsContract.View
    @Mock lateinit var topCoinsObservables: TopCoinsObservables
    @Mock lateinit var topCoinsRepository: TopCoinsRepository
    @Mock lateinit var dbRepository: TopCoinsDatabaseRepository
    @Mock lateinit var resProvider: TopCoinsResourceProvider
    @Mock lateinit var toaster: Toaster
    @Mock lateinit var logger: Logger

    private lateinit var presenter: TopCoinsContract.Presenter

    @Before
    fun setUp() {
        presenter = TopCoinsPresenter(
            view,
            topCoinsObservables,
            topCoinsRepository,
            dbRepository,
            resProvider,
            toaster,
            logger
        )
    }

    @Test
    fun `Should subscribe to observables and update top coins When view created`() {
        val data = listOf<TopCoinEntity>()
        val response = TopCoinsResponse(
            "message",
            1,
            listOf(),
            data,
            false
        )

        runBlocking {
            whenever(topCoinsRepository.getTopCoins()).thenReturn(Result.Success(response))
        }

        presenter.onViewCreated()

        verify(topCoinsObservables.observeTopCoins {  })
        verify(topCoinsObservables.observeMainCoinsUpdating {  })
        verify(topCoinsObservables.observePageChanging {  })
        runBlocking { verify(topCoinsRepository.getTopCoins()) }
    }
}