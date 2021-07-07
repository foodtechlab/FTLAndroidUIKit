package com.foodtechlab.ftlandroiduikit.textfield.helper

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.LayoutRes

class FTLDropDownAdapter(
    context: Context,
    @LayoutRes private val layoutResource: Int,
    private val items: ArrayList<String>
) : ArrayAdapter<String>(context, layoutResource, items), Filterable {
    var menuCellsChangesListener: FTLMenuCellsChangesListener? = null

    private var filteredItems: ArrayList<String>

    init {
        filteredItems = items
    }

    override fun getCount(): Int = filteredItems.size

    override fun getItem(position: Int): String = filteredItems[position]

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                val oldCount = count
                filteredItems = filterResults.values as ArrayList<String>
                if (oldCount != count) {
                    menuCellsChangesListener?.onQuantityChanges(count)
                }
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.lowercase()

                val filterResults = FilterResults()
                filterResults.values = when {
                    queryString.isNullOrEmpty() -> items
                    else -> items.filter { it.lowercase().contains(queryString) }
                }
                return filterResults
            }
        }
    }

    interface FTLMenuCellsChangesListener {
        fun onQuantityChanges(size: Int)
    }
}

