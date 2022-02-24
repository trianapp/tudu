package app.trian.tudu.domain

import app.trian.tudu.R

enum class ThemeData(var text:Int,var value:String) {
    DEFAULT(R.string.theme_default,"default"),
    DARK(R.string.theme_dark,"dark"),
    LIGHT(R.string.theme_light,"light")
}