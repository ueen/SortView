package de.ueen.sortview

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.children
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fondesa.recyclerviewdivider.dividerBuilder
import slush.ItemListEditor
import slush.Slush
import java.util.*

class SortView<ITEM> @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {


    companion object {
        const val DRAG_VERTICAL = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        const val DRAG_HORIZONTAL = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    }

    private var editor: ItemListEditor<ITEM>? = null
    private var onSortListener: OnSortListener? = null

    fun setupAdapter(adapter: (Slush.SingleType<ITEM>) -> Slush.SingleType<ITEM>) = apply {
        editor = adapter(Slush.SingleType()).into(this).itemListEditor
    }

    fun sortDirection(dragDir: Int) = apply {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(dragDir, 0) {
            override fun onMove(
                recyclerView: RecyclerView,
                start: ViewHolder,
                target: ViewHolder
            ): Boolean {
                editor?.moveItem(start.bindingAdapterPosition, target.bindingAdapterPosition)
                onSortListener?.onSort(start.bindingAdapterPosition, target.bindingAdapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {}

        }).attachToRecyclerView(this)
    }

    fun onSort(listener: (startPosition: Int, target: Int) -> Unit) = onSort(
        object : OnSortListener {
            override fun onSort(startPosition: Int, target: Int) {
                listener(startPosition, target)
            }
        })

    fun onSort(list: List<ITEM>) = onSort(
        object : OnSortListener {
            override fun onSort(startPosition: Int, target: Int) {
                Collections.swap(list, startPosition, target)
            }
        })

    fun onSort(listener: OnSortListener) = apply {
        onSortListener = listener

        val isHorizontal =  (layoutManager as LinearLayoutManager).orientation == LinearLayoutManager.HORIZONTAL
        sortDirection( if (isHorizontal) DRAG_HORIZONTAL else DRAG_VERTICAL)
    }

    fun equalSpacing(showFirstDivider: Boolean = false, showLastDivider: Boolean = false) = apply {
        this.post {
            val rcV = this
            rcV.post {
                val isHorizontal =  (rcV.layoutManager as LinearLayoutManager).orientation == LinearLayoutManager.HORIZONTAL
                val size = if (isHorizontal) rcV.width - (rcV.paddingRight + rcV.paddingLeft)
                            else rcV.height - (rcV.paddingTop + rcV.paddingBottom)
                val contentSize = rcV.children.sumOf { v -> if (isHorizontal) v.width else v.height }
                var dividers = rcV.childCount - 1
                //account for first/last divider
                dividers += showFirstDivider.compareTo(false) + showLastDivider.compareTo(false)
                val equalSpacing = (size - contentSize) / dividers

                if (equalSpacing > 0) {
                    val builder = context.dividerBuilder()
                        .asSpace()
                        .size(equalSpacing)
                    if (showFirstDivider) {
                        builder.showFirstDivider()
                    }
                    if (showLastDivider) {
                        builder.showLastDivider()
                    }
                    builder.build().addTo(rcV)
                }
            }
        }
    }

    interface OnSortListener {
        fun onSort(startPosition: Int, target: Int)
    }

}