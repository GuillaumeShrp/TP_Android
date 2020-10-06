package com.ismin.android

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookViewHolder(rootView : View) : RecyclerView.ViewHolder(rootView) {
    var txViewTitle : TextView = rootView.findViewById(R.id.a_vhd_textViewTitle)
    var txViewAuthor : TextView = rootView.findViewById(R.id.a_vhd_textViewAuthor)
    var txViewDate : TextView = rootView.findViewById(R.id.a_vhd_textViewDate)
}