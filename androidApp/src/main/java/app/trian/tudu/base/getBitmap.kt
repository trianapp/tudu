package app.trian.tudu.base

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri

/**
 * Main Activity
 * Author PT Cexup Telemedicine
 * Created by Trian Damai
 * 28/09/2021
 **/

fun Uri.getBitmap(c: ContentResolver): Bitmap?{
    return try {
        val input =  c.openInputStream(this)
       val btmp = BitmapFactory.decodeStream(input)

        Bitmap.createScaledBitmap(
            btmp,
            120,
            120,
            false
        )

    }catch (e:Exception){
        null
    }
}