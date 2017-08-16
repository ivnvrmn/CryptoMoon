package com.rmnivnv.cryptomoon.model.rxbus

/**
 * Created by rmnivnv on 25/07/2017.
 */

data class CoinsLoadingEvent(val isLoading: Boolean)

data class CoinsSelectedEvent(val isSelected: Boolean)

class OnDeleteCoinsMenuItemClickedEvent()