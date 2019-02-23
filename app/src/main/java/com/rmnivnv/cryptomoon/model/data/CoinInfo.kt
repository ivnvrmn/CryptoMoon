package com.rmnivnv.cryptomoon.model.data

import com.google.gson.annotations.SerializedName

data class CoinInfo(
    @SerializedName("Id")
    val id: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("FullName")
    val fullName: String,
    @SerializedName("Internal")
    val internal: String,
    @SerializedName("ImageUrl")
    val imageUrl: String,
    @SerializedName("Url")
    val url: String,
    @SerializedName("Algorithm")
    val algorithm: String,
    @SerializedName("ProofType")
    val proofType: String,
    @SerializedName("NetHashesPerSecond")
    val netHashesPerSecond: Float,
    @SerializedName("BlockNumber")
    val blockNumber: Int,
    @SerializedName("BlockTime")
    val blockTime: Int,
    @SerializedName("BlockReward")
    val blockReward: Double,
    @SerializedName("Type")
    val type: Int,
    @SerializedName("DocumentType")
    val documentType: String
)