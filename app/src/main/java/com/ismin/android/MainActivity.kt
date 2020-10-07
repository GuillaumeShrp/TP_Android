package com.ismin.android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val startActivity2RequestCode = 1
    var bookshelf = Bookshelf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bookshelf.addBook(Book(title = "Un livre", author = "de cet auteur", date = "2020/20/10"))
        bookshelf.addBook(Book(title = "Un livre2", author = "de cet auteur", date = "2020/20/10"))
        bookshelf.addBook(Book(title = "Un livre3", author = "de cet auteur", date = "2020/20/10"))

        displayBookList()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == startActivity2RequestCode) {

            val addedBook = data?.getSerializableExtra("Added Book") as Book
            bookshelf.addBook(addedBook)

            //display notification of the added book when comeback to mainActivity:
            Toast.makeText(this,addedBook.toString(), Toast.LENGTH_LONG).show()

            displayBookList()
        }
    }


    fun goToBookCreation(view: View) {
        val intent = Intent(this, CreateBookActivity::class.java)   //move from this to ..
        startActivityForResult(intent, startActivity2RequestCode)
    }


    //creer une transaction pour ajouter le fragment et l'afficher qlqpart
    fun displayBookList() {
        //// ############################
        //corriger le pbl ici --> faire le caste d'un arraylist
        val bookListFragment = BookListFragment.newInstance(ArrayList(bookshelf.getAllBooks())) //appel de la fonction static
        val bookListFragmentTransaction = supportFragmentManager.beginTransaction()
        bookListFragmentTransaction.replace(R.id.a_main_fragBL, bookListFragment)
        bookListFragmentTransaction.commit()
    }


    /**
     * tips:
     * val qlqch = obj!!.getQlqch()
     * !! a la place de ? qui ne permet pas de type nul
     * assure qu l'element est definit precedemment
     */
}