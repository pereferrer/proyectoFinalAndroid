package ventura.ferrer.josep.pere.proyectofinalandroid.feature.topics.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.item_topic.view.*
import ventura.ferrer.josep.pere.proyectofinalandroid.R
import ventura.ferrer.josep.pere.proyectofinalandroid.domain.Topic
import java.util.*

class TopicHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var topic: Topic? = null

        set(value) {
            field = value

            with(itemView) {

                tag = field
                field?.let {
                    Glide.with(this.context)
                        .load(topic!!.avatar)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(profileImage);
                    labelTitle.text = topic?.title
                    labelPosts.text = topic?.posts.toString()
                    labelViews.text = topic?.views.toString()
                    setTimeOffset(topic!!.getTimeOffset())
                }
            }
        }

    private fun setTimeOffset(timeOffset: Topic.TimeOffset) {
        val quantityString = when (timeOffset.unit) {
            Calendar.YEAR -> R.plurals.years
            Calendar.MONTH -> R.plurals.months
            Calendar.DAY_OF_MONTH -> R.plurals.days
            Calendar.HOUR -> R.plurals.hours
            else -> R.plurals.minutes
        }

        itemView.labelDate.text =
            if (timeOffset.amount != 0) {
                itemView.context.resources.getQuantityString(
                    quantityString,
                    timeOffset.amount,
                    timeOffset.amount
                )
            } else {
                itemView.context.resources.getString(R.string.minutes_zero)
            }
    }

}