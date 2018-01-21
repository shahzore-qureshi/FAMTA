package com.shahzorequreshi.famta.database.entities

object Subway {
    val Services: MutableMap<String, SubwayService> = HashMap()
    val Lines: MutableMap<String, SubwayLine> = HashMap()

    init {
        /*
        val sampleStation1 = SubwayStation("A23", "34 St", listOf(Date(), Date(), Date()))
        val sampleStation2 = SubwayStation("B45", "42 St", listOf(Date(), Date(), Date()))

        val sampleBound1 = SubwayBound("207 St, Manhattan", "N", listOf(sampleStation1, sampleStation2))
        val sampleBound2 = SubwayBound("Lefferts Blvd, Far Rockaway, Rockaway Park, Queens", "S", listOf(sampleStation1, sampleStation2))



        Services.put("a", SubwayService("a",
                R.drawable.ic_subway_service_a,
                listOf(sampleBound1, sampleBound2)))
        Services.put("c", SubwayService("c",
                R.drawable.ic_subway_service_c,
                listOf(sampleBound1, sampleBound2)))
        Services.put("e", SubwayService("e",
                R.drawable.ic_subway_service_e,
                listOf(sampleBound1, sampleBound2)))

        Lines.put("blue", SubwayLine("blue", listOf(Services["a"], Services["c"], Services["e"])))


        Services.put("b", SubwayService("b",
                R.drawable.ic_subway_service_b,
                "Bedford Park Blvd, Bronx",
                "Brighton Beach, Brooklyn"))
        Services.put("d", SubwayService("d",
                R.drawable.ic_subway_service_d,
                "205 St, Bronx",
                "Stillwell Ave, Brooklyn"))
        Services.put("f", SubwayService("f",
                R.drawable.ic_subway_service_f,
                "Jamaica-179 St, Queens",
                "Stillwell Ave, Brooklyn via 63 Street Connector"))
        Services.put("m", SubwayService("m",
                R.drawable.ic_subway_service_m,
                "Forest Hills-71 Ave, Queens",
                "Broadway Junction, Brooklyn"))

        Lines.put("orange", SubwayLine("orange", listOf(Services["b"], Services["d"], Services["f"], Services["m"])))

        Services.put("g", SubwayService("g",
                R.drawable.ic_subway_service_g,
                "Court Sq, Queens",
                "Church Ave, Brooklyn"))

        Lines.put("lime green", SubwayLine("lime green", listOf(Services["g"])))

        Services.put("l", SubwayService("l",
                R.drawable.ic_subway_service_l,
                "8 Ave, Manhattan",
                "Rockaway Pkwy, Brooklyn"))

        Lines.put("light gray", SubwayLine("light gray", listOf(Services["l"])))

        Services.put("j", SubwayService("j",
                R.drawable.ic_subway_service_j,
                "Jamaica Center, Queens",
                "Broad St, Manhattan"))

        Services.put("z", SubwayService("z",
                R.drawable.ic_subway_service_z,
                "Jamaica Center, Queens",
                "Broad St, Manhattan"))

        Lines.put("brown", SubwayLine("brown", listOf(Services["j"], Services["z"])))

        Services.put("n", SubwayService("n",
                R.drawable.ic_subway_service_n,
                "Ditmars Blvd, Queens",
                "Stillwell Ave, Brooklyn"))

        Services.put("q", SubwayService("q",
                R.drawable.ic_subway_service_q,
                "96 St-2 Ave, Manhattan",
                "Stillwell Ave, Brooklyn"))

        Services.put("r", SubwayService("r",
                R.drawable.ic_subway_service_r,
                "Forest Hills-71 Ave, Queens",
                "95 St, Brooklyn"))

        Services.put("w", SubwayService("w",
                R.drawable.ic_subway_service_w,
                "Ditmars Blvd, Queens",
                "Whitehall St-South Ferry, Manhattan"))

        Lines.put("yellow", SubwayLine("yellow", listOf(Services["n"], Services["q"], Services["r"], Services["w"])))

        Services.put("1", SubwayService("1",
                R.drawable.ic_subway_service_1,
                "Van Cortlandt Park-242 St, Bronx",
                "South Ferry, Manhattan"))

        Services.put("2", SubwayService("2",
                R.drawable.ic_subway_service_2,
                "Wakefield-241 St, Bronx",
                "Flatbush Ave, Brooklyn"))

        Services.put("3", SubwayService("3",
                R.drawable.ic_subway_service_3,
                "Harlem-148 St, 7 Ave, Manhattan",
                "New Lots Ave, Brooklyn"))

        Lines.put("red", SubwayLine("red", listOf(Services["1"], Services["2"], Services["3"])))

        Services.put("4", SubwayService("4",
                R.drawable.ic_subway_service_4,
                "Woodlawn, Bronx",
                "New Lots Ave, Brooklyn"))

        Services.put("5", SubwayService("5",
                R.drawable.ic_subway_service_5,
                "Eastchester-Dyre Ave, Nereid Ave-238 St, Bronx",
                "Flatbush Ave, Brooklyn"))

        Services.put("6", SubwayService("6",
                R.drawable.ic_subway_service_6,
                "Pelham Bay Park, Bronx",
                "Brooklyn Bridge, Manhattan"))

        Services.put("6e", SubwayService("6e",
                R.drawable.ic_subway_service_6e,
                "Pelham Bay Park, Bronx",
                "Brooklyn Bridge, Manhattan"))

        Lines.put("green", SubwayLine("green", listOf(Services["4"], Services["5"], Services["6"], Services["6e"])))

        Services.put("7", SubwayService("7",
                R.drawable.ic_subway_service_7,
                "Flushing-Main St, Queens",
                "34 St-Hudson Yards, Manhattan"))

        Services.put("7e", SubwayService("7e",
                R.drawable.ic_subway_service_7e,
                "Flushing-Main St, Queens",
                "34 St-Hudson Yards, Manhattan"))

        Lines.put("raspberry", SubwayLine("raspberry", listOf(Services["7"], Services["7e"])))

        Services.put("s manhattan", SubwayService("s manhattan",
                R.drawable.ic_subway_service_s,
                "Times Square, Manhattan",
                "Grand Central Station, Manhattan"))

        Lines.put("gray manhattan", SubwayLine("gray manhattan", listOf(Services["s manhattan"])))

        Services.put("s queens", SubwayService("s queens",
                R.drawable.ic_subway_service_s,
                "Broad Channel, Queens",
                "Rockaway Park, Queens"))

        Lines.put("gray queens", SubwayLine("gray queens", listOf(Services["s queens"])))

        Services.put("s brooklyn", SubwayService("s brooklyn",
                R.drawable.ic_subway_service_s,
                "Franklin Avenue, Brooklyn",
                "Prospect Park, Brooklyn"))

        Lines.put("gray brooklyn", SubwayLine("gray brooklyn", listOf(Services["s brooklyn"])))

        Services.put("sir", SubwayService("sir",
                R.drawable.ic_subway_service_sir,
                "St. George, Staten Island",
                "Tottenville, Staten Island"))

        Lines.put("dark blue", SubwayLine("dark blue", listOf(Services["sir"])))
        */
    }
}
