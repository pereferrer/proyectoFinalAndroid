package ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import ventura.ferrer.josep.pere.proyectofinalandroid.R
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.Topic
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.view.viewholder.TopicHolder
import java.util.*
import kotlin.collections.ArrayList

class TopicsAdapter(private val topicClickListener: ((Topic) -> Unit)? = null) :
    RecyclerView.Adapter<TopicHolder>(), Filterable {

    private var topicList = mutableListOf<Topic>()
    private var topicListFilter = mutableListOf<Topic>()

    private val listener: ((View) -> Unit) = {
        val topic = it.tag as Topic
        topicClickListener?.invoke(topic)
    }

    init {
        topicListFilter = topicList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_topic, parent, false)
        return TopicHolder(view)
    }

    override fun getItemCount(): Int = topicListFilter.size

    override fun onBindViewHolder(holder: TopicHolder, position: Int) {
        val topic = topicListFilter[position]
        holder.topic = topic
        holder.itemView.setOnClickListener(listener)
    }

    fun setTopics(topics: List<Topic>) {
        topicList.clear()
        topicList.addAll(topics)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    topicListFilter = topicList
                } else {
                    val resultList = mutableListOf<Topic>()
                    for (row in topicList) {
                        if (row.title.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    topicListFilter = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = topicListFilter
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                topicListFilter = results?.values as MutableList<Topic>
                notifyDataSetChanged()
            }

        }
    }
}

