/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package app.trian.tudu.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import app.trian.tudu.sqldelight.Database


class DriverFactory(
    private val context: Context
) {
    fun createDriver(): SqlDriver = AndroidSqliteDriver(Database.Schema,context,"test.db")

}