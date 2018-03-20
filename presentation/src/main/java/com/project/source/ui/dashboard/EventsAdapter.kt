package com.project.source.ui.dashboard

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup
import com.project.source.ui.dashboard.EventsAdapter.Item.DATE
import com.project.source.ui.dashboard.EventsAdapter.Item.EVENT
import com.project.source.ui.inflate

abstract class EventsAdapter(private val itemClick: (Event) -> Unit) : RecyclerView.Adapter<ViewHolder>() {

    protected var items = ArrayList<Any>()

    override fun getItemViewType(pos: Int) =
            when (items[pos]) {
                is String -> DATE.ordinal
                else -> EVENT.ordinal
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            when (viewType) {
                DATE.ordinal -> parent.inflate(R.layout.item_event_date)
                        .let(EventsAdapter::DateViewHolder)
                else -> parent.inflate(R.layout.item_event)
                        .let { getEventViewHolder(it, itemClick) }
            }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        val item = items[pos]
        when (item) {
            is Event -> (holder as EventViewHolder).bind(item)
            else -> (holder as DateViewHolder).bind(item as String)
        }
    }

    override fun getItemCount() = items.size

    fun reset(items: List<Any>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    open fun getEventViewHolder(itemView: View, itemClick: (Event) -> Unit) = EventViewHolder(itemView, itemClick)

    open class EventViewHolder(itemView: View, private val itemClick: (Event) -> Unit) : ViewHolder(itemView) {
        open fun bind(event: Event) {
            with(itemView) {
                opponentLogo.loadImage(event.rivalIcon)
                type.text = context.resources.getStringArray(R.array.tournament)[event.tournament.ordinal]
                name.text = event.name
                event.start?.uiDate.let { startDate.text = it }
                setOnClickListener { itemClick(event) }
            }
        }
    }

    class DateViewHolder(itemView: View) : ViewHolder(itemView) {
        fun bind(date: String) {
            itemView.eventsDate.text = date
        }
    }

    enum class Item {
        EVENT_MAIN, DATE, EVENT
    }
}
