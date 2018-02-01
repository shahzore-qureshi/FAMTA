package com.shahzorequreshi.famta.util

import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.database.entities.SubwayService

/**
 * Provides maps between subway objects and images.
 */
object SubwayMaps {
    fun getDrawableIdForSubwayService(subwayService: SubwayService): Int {
        when(subwayService.name) {
            "1" -> return R.drawable.ic_subway_service_1
            "2" -> return R.drawable.ic_subway_service_2
            "3" -> return R.drawable.ic_subway_service_3
            "4" -> return R.drawable.ic_subway_service_4
            "5" -> return R.drawable.ic_subway_service_5
            "6" -> return R.drawable.ic_subway_service_6
            "6X" -> return R.drawable.ic_subway_service_6e
            "7" -> return R.drawable.ic_subway_service_7
            "7X" -> return R.drawable.ic_subway_service_7e
            "A" -> return R.drawable.ic_subway_service_a
            "B" -> return R.drawable.ic_subway_service_b
            "C" -> return R.drawable.ic_subway_service_c
            "D" -> return R.drawable.ic_subway_service_d
            "E" -> return R.drawable.ic_subway_service_e
            "F" -> return R.drawable.ic_subway_service_f
            "G" -> return R.drawable.ic_subway_service_g
            "J" -> return R.drawable.ic_subway_service_j
            "L" -> return R.drawable.ic_subway_service_l
            "M" -> return R.drawable.ic_subway_service_m
            "N" -> return R.drawable.ic_subway_service_n
            "Q" -> return R.drawable.ic_subway_service_q
            "R" -> return R.drawable.ic_subway_service_r
            "S" -> return R.drawable.ic_subway_service_s
            "SIR" -> return R.drawable.ic_subway_service_sir
            "W" -> return R.drawable.ic_subway_service_w
            "Z" -> return R.drawable.ic_subway_service_z
        }
        return 0
    }

    fun getDrawableIdForSubwayService(subwayService: String): Int {
        when(subwayService) {
            "1" -> return R.drawable.ic_subway_service_1
            "2" -> return R.drawable.ic_subway_service_2
            "3" -> return R.drawable.ic_subway_service_3
            "4" -> return R.drawable.ic_subway_service_4
            "5" -> return R.drawable.ic_subway_service_5
            "6" -> return R.drawable.ic_subway_service_6
            "6X" -> return R.drawable.ic_subway_service_6e
            "7" -> return R.drawable.ic_subway_service_7
            "7X" -> return R.drawable.ic_subway_service_7e
            "A" -> return R.drawable.ic_subway_service_a
            "B" -> return R.drawable.ic_subway_service_b
            "C" -> return R.drawable.ic_subway_service_c
            "D" -> return R.drawable.ic_subway_service_d
            "E" -> return R.drawable.ic_subway_service_e
            "F" -> return R.drawable.ic_subway_service_f
            "G" -> return R.drawable.ic_subway_service_g
            "J" -> return R.drawable.ic_subway_service_j
            "L" -> return R.drawable.ic_subway_service_l
            "M" -> return R.drawable.ic_subway_service_m
            "N" -> return R.drawable.ic_subway_service_n
            "Q" -> return R.drawable.ic_subway_service_q
            "R" -> return R.drawable.ic_subway_service_r
            "S" -> return R.drawable.ic_subway_service_s
            "SIR" -> return R.drawable.ic_subway_service_sir
            "W" -> return R.drawable.ic_subway_service_w
            "Z" -> return R.drawable.ic_subway_service_z
        }
        return 0
    }
}