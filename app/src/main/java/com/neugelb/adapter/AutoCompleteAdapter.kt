package com.neugelb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.neugelb.data.model.Movie
import timber.log.Timber
import java.util.*

class AutoCompleteAdapter(
    context: Context,
    @LayoutRes private val layoutResource: Int,
    @IdRes private val textViewResourceId: Int = 0,
    private val movieRsp: List<Movie>
) :
    ArrayAdapter<Movie>(context, layoutResource, movieRsp) {
    val suggestions = ArrayList<Movie>()

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = createViewFromResource(
            convertView,
            parent,
            android.R.layout.simple_spinner_dropdown_item
        )
        return bindData(getItem(position)!!, view)
    }

    private fun bindData(value: Movie, view: TextView): TextView {
        view.text = value.original_title
        return view
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = createViewFromResource(convertView, parent, layoutResource)

        return bindData(getItem(position)!!, view)
    }

    private fun createViewFromResource(
        convertView: View?,
        parent: ViewGroup,
        layoutResource: Int
    ): TextView {
        val context = parent.context
        val view =
            convertView ?: LayoutInflater.from(context).inflate(layoutResource, parent, false)
        return try {
            if (textViewResourceId == 0) view as TextView
            else {
                view.findViewById(textViewResourceId) ?: throw RuntimeException(
                    "Failed to find view with ID " +
                            "${context.resources.getResourceName(textViewResourceId)} in item layout"
                )
            }
        } catch (ex: ClassCastException) {
            Timber.e("You must supply a resource ID for a TextView")
            throw IllegalStateException(
                "ArrayAdapter requires the resource ID to be a TextView",
                ex
            )
        }
    }

    override fun getFilter(): Filter {
        return nameFilter
    }

    fun update(list: List<Movie>) {
        clear()
        addAll(list)
    }

    private var nameFilter: Filter = object : Filter() {
        override fun convertResultToString(resultValue: Any): String {
            return (resultValue as Movie).original_title
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            return if (constraint != null) {
                suggestions.clear()
                for (rsp in movieRsp) {
                    if (rsp.original_title.lowercase(Locale.getDefault())
                            .startsWith(constraint.toString().lowercase(Locale.getDefault()))
                    ) {
                        suggestions.add(rsp)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = suggestions
                filterResults.count = suggestions.size
                filterResults
            } else {
                FilterResults()
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results != null && results.count > 0) {
                val filteredList: ArrayList<Movie> = results.values as ArrayList<Movie>
                clear()
                for (c in filteredList) {
                    add(c)
                }
                notifyDataSetChanged()
            }
        }
    }

}