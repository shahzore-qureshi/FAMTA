package com.shahzorequreshi.famta.util

import com.shahzorequreshi.famta.R
import com.shahzorequreshi.famta.database.entities.SubwayLine
import com.shahzorequreshi.famta.database.entities.SubwayService

/**
 * Created by Shahzore Qureshi on 1/15/18.
 */
object SubwayMaps {
    fun getDrawableIdForSubwayLine(subwayLine: SubwayLine): Int {
        when (subwayLine.name) {
            "blue" -> return R.drawable.subway_line_blue
            "orange" -> return R.drawable.subway_line_orange
            "lime green" -> return R.drawable.ic_subway_service_g
            "light gray" -> return R.drawable.ic_subway_service_l
            "brown" -> return R.drawable.subway_line_brown
            "yellow" -> return R.drawable.subway_line_yellow
            "red" -> return R.drawable.subway_line_red
            "green" -> return R.drawable.subway_line_green
            "raspberry" -> return R.drawable.subway_line_raspberry
            "gray" -> return R.drawable.ic_subway_service_s
            "dark blue" -> return R.drawable.ic_subway_service_sir
        }
        return 0
    }

    fun getDrawableIdForSubwayService(subwayService: SubwayService): Int {
        when(subwayService.name) {
            "1" -> return R.drawable.ic_subway_service_1
            "2" -> return R.drawable.ic_subway_service_2
            "3" -> return R.drawable.ic_subway_service_3
            "4" -> return R.drawable.ic_subway_service_4
            "5" -> return R.drawable.ic_subway_service_5
            "6" -> return R.drawable.ic_subway_service_6
            "6E" -> return R.drawable.ic_subway_service_6e
            "7" -> return R.drawable.ic_subway_service_7
            "7E" -> return R.drawable.ic_subway_service_7e
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