package com.ismin.android

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), BookCreator {
    private val startActivity2RequestCode = 1
    private var bookshelf = Bookshelf()
    private lateinit var bookService: BookService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**bookshelf.addBook(Book(title = "Un livre", author = "de cet auteur", date = "2020/20/10"))
        bookshelf.addBook(Book(title = "Un livre2", author = "de cet auteur", date = "2020/20/10"))
        bookshelf.addBook(Book(title = "Un livre3", author = "de cet auteur", date = "2020/20/10"))
        */

        //maintenant on passe par le serveur:
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://boobshelf-app-gsa.cleverapps.io") //pour faire une erreur de reseau -> .com
            .build()

        bookService = retrofit.create(BookService::class.java)

        /**
        bookService.getAllBooks().enqueue(object : Callback<ArrayList<Book>> {
            override fun onResponse(
                call: Call<ArrayList<Book>>,
                response: Response<ArrayList<Book>>
            ) {
                val allBooks = response.body()
                allBooks?.forEach {
                    bookshelf.addBook(it)
                    displayBookList()
                }
            }

            override fun onFailure(call: Call<ArrayList<Book>>, t: Throwable) {
                displayToastError(t)
            }
        }) SIMPLIFIER EN ::::::*/
        bookService.getAllBooks().enqueue {
            onResponse = {
                val allBooks = it.body()
                allBooks?.forEach {
                    bookshelf.addBook(it)
                    displayBookList()
                }
            }
            onFailure = {
                if (it != null) {
                    displayToastError(it)
                }
            }
        }


    }

    private fun displayToastError(t: Throwable) {
        Toast.makeText(
            applicationContext,
            "Erreur de reseau / ${t.localizedMessage}",
            Toast.LENGTH_LONG
        ).show()
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
        displayBookCreation()
        //val intent = Intent(this, CreateBookActivity::class.java)   //move from this to ..
        //startActivityForResult(intent, startActivity2RequestCode)
    }


    //creer une transaction pour ajouter le fragment et l'afficher qlqpart
    private fun displayBookList() {
        //// ############################
        //corriger le pbl ici --> faire le caste d'un arraylist
        val bookListFragment = BookListFragment.newInstance(ArrayList(bookshelf.getAllBooks())) //appel de la fonction static
        val bookListFragmentTransaction = supportFragmentManager.beginTransaction()

        bookListFragmentTransaction.replace(R.id.a_main_layout_fragment, bookListFragment)
        bookListFragmentTransaction.commit()

        a_main_btn_addBook.visibility = View.VISIBLE
    }

    //creer une transaction pour ajouter le fragment et l'afficher qlqpart (fragmnt ver)
    private fun displayBookCreation() {
        val createBookFragment = CreateBookFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.a_main_layout_fragment, createBookFragment)
        fragmentTransaction.commit()

        a_main_btn_addBook.visibility = View.GONE //element n'existe pas != invisible
    }

    override fun onBookCreated(book: Book) {
        //ajouter le livre sur le serveur:
        bookService.createBook(book).enqueue {
            onResponse = {
                bookshelf.addBook(it.body()!!)
                closeBookCreation()
            }
            onFailure = {
                if (it != null) {
                    displayToastError(it)
                }
            }
        }



        (object : Callback<Book> {
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                bookshelf.addBook(response.body()!!)
                closeBookCreation()
            }

            override fun onFailure(call: Call<Book>, t: Throwable) {
                displayToastError(t)
            }
        })

        //bookshelf.addBook(book)
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


    override fun closeBookCreation (){
        displayBookList()
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
