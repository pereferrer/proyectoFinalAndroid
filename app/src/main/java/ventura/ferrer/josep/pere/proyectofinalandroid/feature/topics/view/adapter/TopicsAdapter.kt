package ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ventura.ferrer.josep.pere.proyectofinalandroid.R
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.Topic
import ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.view.viewholder.TopicHolder

class TopicsAdapter(
    private val topicClickListener: ((Topic) -> Unit)? = null
) : RecyclerView.Adapter<TopicHolder>() {

    private val topicList = mutableListOf<Topic>()
    private val listener: ((View) -> Unit) = {
        val topic = it.tag as Topic
        topicClickListener?.invoke(topic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_topic, parent, false)
        return TopicHolder(view)
    }

    override fun getItemCount(): Int = topicList.size

    override fun onBindViewHolder(holder: TopicHolder, position: Int) {
        val topic = topicList[position]
        holder.topic = topic
        holder.itemView.setOnClickListener(listener)
    }

    fun setTopics(topics: List<Topic>) {
        topicList.clear()
        topicList.addAll(topics)
        notifyDataSetChanged()
    }

}