package com.example.nal_oreo.data

import com.google.gson.annotations.SerializedName

data class UserRequest(
    val nama: String,
    val email: String,
    @SerializedName("tgl_lahir") val tglLahir: String,
    @SerializedName("jenis_kelamin") val jenisKelamin: String,
    val username: String,
    val password: String
)

data class ProductModel(
    val id: Long? = null,
    val name: String,
    val stock: String,
    val price: String
)

data class TransaksiSupabaseModel(
    val id: Long? = null,
    @SerializedName("kode_order") val kodeOrder: String,
    val detail: String,
    val nominal: String
)