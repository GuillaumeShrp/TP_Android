package com.ismin.android

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.*
import com.ismin.android.Book as Book

/** on decrit seulement une interface, retrofit de genere la classe
 */

interface BookService {

    @GET("books")
    fun getAllBooks() : Call<ArrayList<Book>>

    @POST("books")
    fun createBook(@Body() book:Book): Call<Book>


}