package com.ismin.android

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), BookCreator {
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

    /**plus besoin avec les ffragment car dans le onBookCreated
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
    */

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
        bookListFragmentTransaction.replace(R.id.a_main_layout_fragment, bookListFragment)
        bookListFragmentTransaction.commit()
    }

    //creer une transaction pour ajouter le fragment et l'afficher qlqpart (fragmnt ver)
    fun displayBookCreation() {
        val createBookFragment = CreateBookFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.a_main_layout_fragment, createBookFragment)
        fragmentTransaction.commit()
    }

    override fun onBookCreated(book: Book) {
        bookshelf.addBook(book)
        displayBookList()
    }



    /** POUR LE MENU DANS LA TOOLBAR */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) { //switch case
            R.id.menu_action_clear -> {
                bookshelf.clear()
                displayBookList()
                Toast.makeText(this, "Bibliothèque effacée", Toast.LENGTH_SHORT).show()
                true //equal to return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    /**
     * tips:
     * val qlqch = obj!!.getQlqch()
     * !! a la place de ? qui ne permet pas de type nul
     * assure qu l'element est definit precedemment
     *
     * model mvc:
     * m=book
     * v=xml
     * c=activity
     */
}
