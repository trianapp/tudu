package app.trian.tudu.common

import android.content.Context
import android.content.res.Configuration
import android.view.ContextThemeWrapper
import java.util.*


class LocaleUtils() {

    fun updateLocale(lang:String,context: ContextThemeWrapper){
            val languange = Locale(lang)
            val config = Configuration()

            Locale.setDefault(languange)
            config.setLocale(languange)
            context.applyOverrideConfiguration(config)
    }
}