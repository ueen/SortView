package de.ueen.sortviewexample

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import de.ueen.sortview.SortDialog
import de.ueen.sortview.SortView

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val list = listOf<String>("hi","whats","going","on")

        val sortView = findViewById<SortView<String>>(R.id.sortView)
            .setupAdapter { adapter ->
                //documentation https://github.com/minseunghyun/slush
                adapter.setItemLayout(R.layout.string_item)
                adapter.setLayoutManager(
                    LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false))
                adapter.onBind { view, s ->
                    view.findViewById<TextView>(R.id.textView).text = s }
                adapter.setItems(list)
            }
            .onSort(list) //infers direction
            //.sortDirection(SortView.DRAG_HORIZONTAL)
            .equalSpacing() //optional: showFirstDivider: Boolean, showLastDivider: Boolean

        val intList = listOf(1,2,3,4,5)
        SortDialog.show(this, intList, "SortDialog") { newList ->
            //intList reordered
            Log.d("SortDialog", "intList reordered: "+newList.toString())
        }

    }
}