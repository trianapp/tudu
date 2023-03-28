package app.trian.tudu.data.theme

import app.trian.tudu.R

enum class ThemeData(val value: String, val text: Int) {
    DEFAULT("default", R.string.system),
    DARK("dark", R.string.dark),
    LIGHT("light", R.string.light)
}