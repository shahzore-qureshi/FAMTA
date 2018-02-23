package com.shahzorequreshi.famta.database.entities

import java.io.Serializable

/**
 * Represents a subway line event (ex. DELAYS, SERVICE CHANGE).
 */
class SubwayLineEvent(val title: String, val body: String) : Serializable {
    override fun toString(): String {
        return title
    }
}