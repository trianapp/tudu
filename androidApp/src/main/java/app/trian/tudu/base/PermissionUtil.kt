package app.trian.tudu.base

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat


fun Array<String>.checkGrantedPermissionFrom(ctx: Context): Boolean =
    map {
        ContextCompat.checkSelfPermission(ctx, it) == PackageManager.PERMISSION_GRANTED
    }.filter { it }.size >= this.size

