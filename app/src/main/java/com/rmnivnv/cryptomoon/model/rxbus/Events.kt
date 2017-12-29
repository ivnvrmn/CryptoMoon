package com.rmnivnv.cryptomoon.model.rxbus

/**
 * Created by rmnivnv on 25/07/2017.
 */

class CoinsLoadingEvent(val isLoading: Boolean)

class OnDeleteCoinsMenuItemClickedEvent

class MainCoinsListUpdatedEvent

class SearchHashTagUpdated

class CoinsSortMethodUpdated

class LanguageChanged(val language: String)