package com.skillw.rpgmaker.core.set

import java.lang.ref.WeakReference

open class WeakHashSet<T> : MutableSet<T> {

    private val map = mutableMapOf<Int, WeakReference<T>>()
    var nextIndex = 0
        private set

    override val size: Int
        get() = map.size

    override fun addAll(elements: Collection<T>): Boolean {
        var modified = false
        elements.forEach { element ->
            if (add(element)) {
                modified = true
            }
        }
        return modified
    }

    override fun contains(element: T): Boolean {
        return map.values.any { it.get() == element }
    }

    override fun add(element: T): Boolean {
        val index = nextIndex++
        map[index] = WeakReference(element)
        return true
    }

    override fun remove(element: T): Boolean {
        val iterator = map.entries.iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            val reference = entry.value.get()
            if (reference == null || reference == element) {
                iterator.remove()
                return true
            }
        }
        return false
    }

    override fun iterator(): MutableIterator<T> {
        return object : MutableIterator<T> {
            private val mapIterator = map.values.iterator()

            override fun hasNext(): Boolean {
                return mapIterator.hasNext()
            }

            override fun next(): T {
                return mapIterator.next().get() ?: throw NoSuchElementException()
            }

            override fun remove() {
                mapIterator.remove()
            }
        }
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        var modified = false
        elements.forEach { element ->
            if (remove(element)) {
                modified = true
            }
        }
        return modified
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        val retainSet = elements.toSet()
        val iterator = map.entries.iterator()
        var modified = false
        while (iterator.hasNext()) {
            val entry = iterator.next()
            val reference = entry.value.get()
            if (reference == null || !retainSet.contains(reference)) {
                iterator.remove()
                modified = true
            }
        }
        return modified
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return elements.all { contains(it) }
    }

    override fun clear() {
        map.clear()
    }

    override fun isEmpty(): Boolean {
        return map.isEmpty()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Set<*>) return false
        if (size != other.size) return false
        return containsAll(other)
    }

    override fun hashCode(): Int {
        return map.hashCode()
    }

    override fun toString(): String {
        val elements = map.values.mapNotNull { it.get() }.joinToString(", ")
        return "WeakHashSet(size=$size, nextIndex=$nextIndex, elements=[$elements])"
    }

}
