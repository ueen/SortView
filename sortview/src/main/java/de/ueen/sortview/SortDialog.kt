package de.ueen.sortview

import android.R
import android.content.Context
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager


class SortDialog() {

    companion object {
        fun <ITEM> show(context: Context, list: List<ITEM>, title: String? = null, itemLayout: Int = R.layout.simple_list_item_1, listener: (List<ITEM>) -> Unit) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle(title)

            val sortView = SortView<String>(context)
                .setupAdapter { adapter ->
                    adapter.setItemLayout(itemLayout)
                    adapter.setLayoutManager(
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false))
                    adapter.onBind { view, s ->
                        view.findViewById<TextView>(R.id.text1).text = s }
                    adapter.setItems(list.map { i -> i.toString() })
                }
                .onSort(list)

            builder.setView(sortView)

            builder.setPositiveButton(R.string.ok) { d, w -> listener(list) }
            builder.setOnDismissListener { listener(list) }

            builder.create().show()
        }
    }

}