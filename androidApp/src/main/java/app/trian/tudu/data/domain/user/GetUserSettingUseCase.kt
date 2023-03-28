package app.trian.tudu.data.domain.user

import app.trian.tudu.data.utils.Response
import app.trian.tudu.sqldelight.Database
import app.trian.tudu.table.appSetting.AppSetting
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetUserSettingUseCase @Inject constructor(
    private val db: Database,
    private val auth: FirebaseAuth
) {
    operator fun invoke(): Flow<Response<AppSetting>> = flow {
        emit(Response.Loading)
        val user = auth.currentUser
        if (user != null) {
            val setting = db.appSettingQueries.getAppSetting(user.uid).executeAsOneOrNull()
            if (setting == null) emit(Response.Error(""))
            else emit(Response.Result(setting))
        } else emit(Response.Error(""))
    }.flowOn(Dispatchers.IO)
}