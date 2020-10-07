package com.ismin.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


private const val ID_BOOKS = "id_books" //clé pour l'argument

/**
 * A simple [Fragment] subclass.
 * Use the [BookListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookListFragment : Fragment() {
    private var myBooks: ArrayList<Book> = arrayListOf() //init vide
    private lateinit var recyclerView: RecyclerView //lateinit pour ne pas dire RecyclerView?
    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            myBooks = it.getSerializable(ID_BOOKS) as ArrayList<Book>

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                            savedInstanceState: Bundle?): View? {

        val framgentView = inflater.inflate(R.layout.fragment_book_list, container, false)

        recyclerView = framgentView.findViewById<RecyclerView>(R.id.a_booklist_rcv_case)
        val layoutManager = LinearLayoutManager(context) //fragment doesnt have context -> recupere l'activité
        recyclerView.layoutManager = layoutManager
        bookAdapter = BookAdapter(myBooks)
        recyclerView.adapter = bookAdapter


        return framgentView
    }

    companion object { //element commun a la classe : equal to static
        @JvmStatic
        fun newInstance(books: ArrayList<Book>) =
            BookListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ID_BOOKS, books)
                }
            }
    }
}