package app.trian.tudu.common

import android.content.Context
import app.trian.tudu.domain.ThemeData
import app.trian.tudu.ui.component.dialog.DialogItemModel
import java.util.regex.Pattern

fun String.isEmailValid() =
    Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    ).matcher(this).matches()


fun String?.getTheme():ThemeData{
    val theme = this
    if(theme.isNullOrBlank()) return ThemeData.DEFAULT
   return when(theme){
        ThemeData.DEFAULT.value -> ThemeData.DEFAULT
        ThemeData.DARK.value -> ThemeData.DARK
        ThemeData.LIGHT.value -> ThemeData.LIGHT
        else -> ThemeData.DEFAULT
    }
}

fun String?.toDialogSelect(context: Context):DialogItemModel{
    val theme = this
    if(theme.isNullOrBlank()) return DialogItemModel(
        ThemeData.DEFAULT.value,
        context.getString(ThemeData.DEFAULT.text)
    )

    return when(theme){
        ThemeData.DEFAULT.value -> DialogItemModel(
            ThemeData.DEFAULT.value,
            context.getString(ThemeData.DEFAULT.text)
        )
        ThemeData.DARK.value -> DialogItemModel(
            ThemeData.DARK.value,
            context.getString(ThemeData.DARK.text)
        )
        ThemeData.LIGHT.value -> DialogItemModel(
            ThemeData.LIGHT.value,
            context.getString(ThemeData.LIGHT.text)
        )
        else -> DialogItemModel(
            ThemeData.DEFAULT.value,
            context.getString(ThemeData.DEFAULT.text)
        )
    }
}