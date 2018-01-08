package com.shahzorequreshi.famta.fragments.dummy

import java.util.ArrayList

object Subway {
    val LINES: MutableList<SubwayLine> = ArrayList<SubwayLine>()

    init {
        addItem(SubwayLine("blue", arrayOf("A", "C", "E")))
        addItem(SubwayLine("orange", arrayOf("B", "D", "F", "M")))
        addItem(SubwayLine("lime_green", arrayOf("G")))
        addItem(SubwayLine("light_gray", arrayOf("L")))
        addItem(SubwayLine("brown", arrayOf("J", "M", "Z")))
        addItem(SubwayLine("yellow", arrayOf("N", "Q", "R", "W")))
        addItem(SubwayLine("red", arrayOf("1", "2", "3")))
        addItem(SubwayLine("green", arrayOf("4", "5", "6", "6E")))
        addItem(SubwayLine("raspberry", arrayOf("7", "7E")))
        addItem(SubwayLine("dark_gray", arrayOf("S")))
    }

    private fun addItem(item: SubwayLine) {
        LINES.add(item)
    }

    class SubwayLine(val subwayLineName: String, val subwayServices: Array<String>) {
        override fun toString(): String {
            return subwayLineName
        }
    }
}
