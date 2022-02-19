package app.trian.tudu.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import app.trian.tudu.data.local.AppSetting
import kotlinx.coroutines.flow.Flow


@Dao
interface AppSettingDao {
    @Query("SELECT * FROM tb_app_setting WHERE idSetting=:uid")
    fun getApplicationSetting(uid:String): Flow<AppSetting?>

    @Insert
    fun addNewSetting(setting: AppSetting)

    @Update
    fun updateSetting(setting: AppSetting)
}