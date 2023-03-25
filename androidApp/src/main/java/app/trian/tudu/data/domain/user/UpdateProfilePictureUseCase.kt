package app.trian.tudu.data.domain.user

import android.graphics.Bitmap
import app.trian.tudu.data.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UpdateProfilePictureUseCase @Inject constructor(
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage
) {
    operator fun invoke(
        picture: Bitmap?
    ): Flow<Response<String>> = flow {
        emit(Response.Loading)
        if (picture == null) emit(Response.Error(""))
        else uploadPicture(picture)
    }.flowOn(Dispatchers.IO)

    private fun uploadPicture(picture: Bitmap): Flow<Response<String>> = flow {
        val updateProfileChangeRequest = userProfileChangeRequest {
            this.displayName = displayName
        }
        auth.currentUser?.updateProfile(updateProfileChangeRequest)
        emit(Response.Result(""))
    }
}