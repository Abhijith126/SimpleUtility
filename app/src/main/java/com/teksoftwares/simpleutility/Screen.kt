package com.teksoftwares.simpleutility

sealed class Screen(val route: String, val title: String) {
    object Clock : Screen("clock", "Clock")
    object Timer : Screen("timer", "Timer")
    object Stopwatch : Screen("stopwatch", "Stopwatch")
}