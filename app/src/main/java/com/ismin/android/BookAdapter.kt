package com.ismin.android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class BookAdapter(private var books: ArrayList<Book>) : RecyclerView.Adapter<BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.row_book, parent, false)
        //false pour ne pas attacher à la vue globale (refresh ne sont pas en commun)
        return BookViewHolder(row)
    }

    override fun onBindViewHolder(viewholder: BookViewHolder, position: Int) {
        val (title, author, date) = this.books[position]
        viewholder.txViewTitle.text = title
        viewholder.txViewAuthor.text = author
        viewholder.txViewDate.text = date
    }

    override fun getItemCount(): Int {
        return this.books.size
    }

    //update book list in adapter
    fun refreshBooks(myBooks: ArrayList<Book>){
        books.clear()
        books.addAll(myBooks)
    }
}
