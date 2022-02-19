package app.trian.tudu.data.local.dao

import androidx.room.*
import app.trian.tudu.data.local.AppSetting
import kotlinx.coroutines.flow.Flow


@Dao
interface AppSettingDao {
    @Query("SELECT * FROM tb_app_setting WHERE idSetting=:uid")
    fun getApplicationSetting(uid:String): Flow<AppSetting?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewSetting(setting: AppSetting)

    @Update
    fun updateSetting(setting: AppSetting)
}