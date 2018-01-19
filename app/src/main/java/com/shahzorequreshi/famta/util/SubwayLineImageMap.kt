package com.shahzorequreshi.famta.util

import com.shahzorequreshi.famta.R

/**
 * Created by Shahzore Qureshi on 1/15/18.
 */
object SubwayLineImageMap {
    fun getDrawableIdForSubwayLine(subwayLine: String): Int {
        when (subwayLine) {
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
}