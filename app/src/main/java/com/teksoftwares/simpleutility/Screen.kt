package com.teksoftwares.simpleutility

sealed class Screen(val route: String, val title: String) {
    data object Clock : Screen("clock", "Clock")
    data object Timer : Screen("timer", "Timer")
    data object Stopwatch : Screen("stopwatch", "Stopwatch")
}