package app.trian.tudu.data.domain.user

import app.trian.tudu.data.theme.ThemeData
import app.trian.tudu.data.utils.Response
import app.trian.tudu.sqldelight.Database
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import javax.inject.Inject

class ChangeThemeUseCase @Inject constructor(
    private val db: Database,
    private val auth: FirebaseAuth
) {
    operator fun invoke(theme: ThemeData): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        val user = auth.currentUser
        if (user == null) emit(Response.Error("User not loggedIn"))
        else {
            db.transaction {
                val isSettingExist = db.appSettingQueries.getAppSetting(user.uid).executeAsOneOrNull()

                if (isSettingExist == null) {
                    //insert
                    db.appSettingQueries.insertSetting(
                        settingId = user.uid,
                        theme = theme.value,
                        createdAt = LocalDateTime.now().toString(),
                        updatedAt = LocalDateTime.now().toString()
                    )
                } else {
                    db.appSettingQueries.updateTheme(
                        settingId = user.uid,
                        theme = theme.value,
                        updatedAt = LocalDateTime.now().toString()
                    )
                }
            }
        }
        emit(Response.Result(true))
    }.flowOn(Dispatchers.IO)
}