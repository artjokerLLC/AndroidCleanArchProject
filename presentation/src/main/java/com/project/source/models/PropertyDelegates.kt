package com.project.source.models

import com.orhanobut.hawk.Hawk
import kotlin.reflect.KProperty

class EnumStoragePropertyDelegate<E : Enum<*>>(private val values: Array<E>) {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): E? =
            Hawk.get(property.name, -1)
                    ?.let {
                        when (it) {
                            -1 -> null
                            else -> values[it]
                        }
                    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: E?) =
            Hawk.put(property.name, value?.ordinal ?: -1)

    companion object {
        inline fun <reified E : Enum<E>> instantiate() = EnumStoragePropertyDelegate(enumValues<E>())
    }
}

class StoragePropertyDelegate<T>(private val defaultValue: T? = null) {

    operator fun getValue(thisRef: Any, property: KProperty<*>): T =
            Hawk.get<T>(property.name, defaultValue)

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        Hawk.put(property.name, value)
    }
}
