package com.shahzorequreshi.famta.objects

import com.shahzorequreshi.famta.R

object Subway {
    class SubwayService(val name: String,
                        val drawableId: Int,
                        val northboundStation: String,
                        val southboundStation: String) {
        override fun toString(): String {
            return name
        }
    }

    class SubwayLine(val name: String, val services: List<SubwayService?>) {
        override fun toString(): String {
            return name
        }
    }

    val Services: MutableMap<String, SubwayService> = HashMap()
    val Lines: MutableMap<String, SubwayLine> = HashMap()

    init {
        Services.put("a", SubwayService("a",
                R.drawable.ic_subway_service_a,
                "207 St, Manhattan",
                "Lefferts Blvd, Far Rockaway, Rockaway Park, Queens"))
        Services.put("c", SubwayService("c",
                R.drawable.ic_subway_service_c,
                "168 St, Manhattan",
                "Euclid Av, Brooklyn"))
        Services.put("e", SubwayService("e",
                R.drawable.ic_subway_service_e,
                "Jamaica Ctr, Queens",
                "World Trade Ctr, Manhattan"))

        Lines.put("blue", SubwayLine("blue", listOf(Services["a"], Services["c"], Services["e"])))

        Services.put("b", SubwayService("b",
                R.drawable.ic_subway_service_b,
                "Bedford Park Blvd, Bronx",
                "Brighton Beach, Brooklyn"))
        Services.put("d", SubwayService("d",
                R.drawable.ic_subway_service_d,
                "205 St, Bronx",
                "Stillwell Av, Brooklyn"))
        Services.put("f", SubwayService("f",
                R.drawable.ic_subway_service_f,
                "Jamaica-179 St, Queens",
                "Stillwell Av, Brooklyn via 63 Street Connector"))
        Services.put("m", SubwayService("m",
                R.drawable.ic_subway_service_m,
                "Forest Hills-71 Av, Queens",
                "Broadway Junction, Brooklyn"))

        Lines.put("orange", SubwayLine("orange", listOf(Services["b"], Services["d"], Services["f"], Services["m"])))

        /*
        addItem(SubwayLine("blue", arrayOf("A", "C", "E")))
        addItem(SubwayLine("orange", arrayOf("B", "D", "F", "M")))
        addItem(SubwayLine("lime_green", arrayOf("G")))
        addItem(SubwayLine("light_gray", arrayOf("L")))
        addItem(SubwayLine("brown", arrayOf("J", "M", "Z")))
        addItem(SubwayLine("yellow", arrayOf("N", "Q", "R", "W")))
        addItem(SubwayLine("red", arrayOf("1", "2", "3")))
        addItem(SubwayLine("green", arrayOf("4", "5", "6", "6E")))
        addItem(SubwayLine("raspberry", arrayOf("7", "7E")))
        addItem(SubwayLine("gray", arrayOf("S")))
        addItem(SubwayLine("dark_blue", arrayOf("SIR")))
        */
    }
}
