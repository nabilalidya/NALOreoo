package com.example.nal_oreo.data

import retrofit2.Response
import retrofit2.http.*

interface SupabaseApiService {

    // === 1. REGISTER USER (POST) ===
    @POST("users")
    suspend fun registerUser(
        @Body user: UserRequest
    ): Response<Unit>

    // === 2. CRUD PRODUCTS ===
    @GET("products?select=*&order=id.desc")
    suspend fun getProducts(): List<ProductModel>

    @POST("products")
    suspend fun addProduct(
        @Body product: ProductModel
    ): Response<Unit>

    @PATCH("products")
    suspend fun updateProduct(
        @Query("id") idFilter: String,
        @Body product: ProductModel
    ): Response<Unit>

    @DELETE("products")
    suspend fun deleteProduct(
        @Query("id") idFilter: String
    ): Response<Unit>

    // === 3. CRUD TRANSACTIONS ===
    @GET("transactions?select=*&order=id.desc")
    suspend fun getTransactions(): List<TransaksiSupabaseModel>

    @POST("transactions")
    suspend fun addTransaction(
        @Body transaksi: TransaksiSupabaseModel
    ): Response<Unit>

    @PATCH("transactions")
    suspend fun updateTransaction(
        @Query("id") idFilter: String,
        @Body transaksi: TransaksiSupabaseModel
    ): Response<Unit>

    @DELETE("transactions")
    suspend fun deleteTransaction(
        @Query("id") idFilter: String
    ): Response<Unit>
}