package com.ismin.android

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class CreateBookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creat_book)
        //Log.d('CreateBookActivity', '') //debug: display log
    }


    private fun stopActivityAndResult(addedBook: Book) {
        val returnIntent = Intent()
        returnIntent.putExtra("Added Book", addedBook)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    fun createBook(view: View) {
        val title = findViewById<EditText>(R.id.a_createBook_edt_title).text.toString() //user field input value
        val author = findViewById<EditText>(R.id.a_createBook_edt_name).text.toString()
        val date = findViewById<EditText>(R.id.a_createBook_edt_date).text.toString()

        val addedBook: Book = Book(title, author, date)
        stopActivityAndResult(addedBook)
    }
}