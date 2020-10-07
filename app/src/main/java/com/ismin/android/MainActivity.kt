package com.ismin.android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val startActivity2RequestCode = 1
    var bookshelf = Bookshelf()
    private lateinit var recyclerView: RecyclerView //lateinit pour ne pas dire RecyclerView?
    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bookshelf.addBook(Book(title = "Un livre", author = "de cet auteur", date = "2020/20/10"))

        recyclerView = findViewById<RecyclerView>(R.id.a_rcv_case)
        val layoutManager = LinearLayoutManager(this) //come directly from android
        recyclerView.layoutManager = layoutManager
        bookAdapter = BookAdapter(ArrayList(bookshelf.getAllBooks()))
        recyclerView.adapter = bookAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == startActivity2RequestCode) {

            val addedBook = data?.getSerializableExtra("Added Book") as Book
            bookshelf.addBook(addedBook)

            //display notification of the added book when comeback to mainActivity:
            Toast.makeText(this,addedBook.toString(), Toast.LENGTH_LONG).show()

            bookAdapter.refreshBooks(ArrayList(bookshelf.getAllBooks()))
            bookAdapter.notifyDataSetChanged()
        }
    }

    fun goToBookCreation(view: View) {
        val intent = Intent(this, CreateBookActivity::class.java)   //move from this to ..
        startActivityForResult(intent, startActivity2RequestCode)
    }



}