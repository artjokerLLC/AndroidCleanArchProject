package com.project.source.data

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

abstract class Mapper<in T, out U> private constructor() : (T) -> U {

    fun asListMapper(): Mapper<List<T>?, List<U>> =
            object : Mapper<List<T>?, List<U>>() {
                override fun invoke(t: List<T>?) = t?.map(this@Mapper) ?: emptyList()
            }

    companion object {

        fun <T, U> build(action: T.() -> U): Mapper<T, U> =
                object : Mapper<T, U>() {

                    override fun invoke(t: T) = action(t)
                }
    }
}

@SuppressLint("SimpleDateFormat")
object DateMapper {
    private val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

    fun transform(dataStr: String): Date? =
            try {
                format.parse(dataStr)
            } catch (e: ParseException) {
                e.printStackTrace()
                null
            }
}

/**
 * Event
 */
val eventMapper = Mapper.build<EventResp, Event> {
    Event(name, id,
            DateMapper.transform(start), DateMapper.transform(end),
            priceId, active, limit, stadiumName,
            commissionPercentage, sell ?: false, Companion.MatchType.values()[matchType],
            Companion.Tournament.values()[tournament], rivalIcon, priorityMapper(priorities), 0)
}

private val priorityMapper = Mapper.build<PriorityResp, Priority> {
    eventPriorityMapper.asListMapper()
            .invoke(eventPriorities)
            .let(::Priority)
}

private val eventPriorityMapper = Mapper.build<EventPriorityResp, EventPriority> {
    EventPriority(name, type)
}




