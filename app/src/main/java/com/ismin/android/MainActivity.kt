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
    private lateinit var recyclerView: RecyclerView //pour ne pas dire RecyclerView?
    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById<RecyclerView>(R.id.a_rcv_case)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        bookAdapter = BookAdapter(ArrayList(bookshelf.getAllBooks()))
        recyclerView.adapter = bookAdapter

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == startActivity2RequestCode) {
            bookshelf.addBook(data?.getSerializableExtra("Added Book") as Book)

            //up date
            //val bookAdapter = BookAdapter(ArrayList(bookshelf.getAllBooks()))
            //recyclerView.adapter = bookAdapter
            //recyclerView.adapter?.notifyDataSetChanged()

        }
        //Toast.makeText(this.bookshelf.getAllBooks().toString(), Toast.LENGTH_LONG).show() //afficher petite notif en bas
        bookAdapter.refreshBooks(ArrayList(bookshelf.getAllBooks()))
    }

    fun goToBookCreation(view: View) {
        val intent = Intent(this, CreateBookActivity::class.java)   //move from this to ..
        startActivityForResult(intent, startActivity2RequestCode)
    }



}