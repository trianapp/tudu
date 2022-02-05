package app.trian.tudu.ui.theme

import androidx.compose.ui.graphics.Color


val Primary = Color(0xFF2934D0)
val OnPrimary = Color(0xFFFFFFFF)
val Background = Color(0xFFFFFFFF)
val OnBackground = Color(0xFF767780)
val Surface = Color(0xFFF8F9FA)
val OnSurface = Color(0xFF000000)


val Tertiary = Color(0xFFF78585)
val OnTertiary = Color(0xFFFF9797)

val PrimaryDark = Color(0xFF33E6F6)
val OnPrimaryDark = Color(0xFF191A32)
val BackgroundDark = Color(0xFF191A32)
val OnBackgroundDark = Color(0xFF999CAD)
val SurfaceDark = Color(0xFF151529)
val  OnSurfaceDark = Color(0xFFFFFFFF)

val RedGoogle = Color(0xFFE8453C)

val Inactivebackground = Color(0xFFEAEBFA)
val InactiveText = Color(0xFF000000).copy(alpha =0.5f )

val ScrimColor = Color.DarkGray.copy(alpha = 0.5f)

object HexToJetpackColor{
    fun getColor(colorString: String): Color {
        return Color(android.graphics.Color.parseColor("#" + colorString))
    }
    val Blue = "567DF4"
    val SecondBlue = "EEF7FE"
    val Yellow = "FFDE6C"
    val SecondYellow = "FFFBEC"
    val Red = "F45656"
    val SecondRed = "FEEEEE"
    val Green = "34DEDE"
    val SecondGreen = "F0FFFF"
}
