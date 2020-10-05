package com.ismin.android


class Bookshelf() {

    private val storage = mutableMapOf<String, Book>()
    //or with array list

    fun addBook(book: Book) {
        storage[book.title] = book
    }

    fun getBook(title: String) : Book? {
        return storage[title]
    }

    fun getBooksOf(author: String) : Collection<Book> {
        return storage.filter { it.value.author == author }.values.sortedBy { it.title }
    }

    fun getAllBooks() : List<Book> {
        return storage.values.sortedBy { it.title }
    }

    fun getTotalNumberOfBooks() : Int? {
        return storage.count()
    }

}