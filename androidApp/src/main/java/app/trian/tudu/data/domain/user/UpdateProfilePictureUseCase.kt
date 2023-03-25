package app.trian.tudu.data.domain.user

import android.graphics.Bitmap
import app.trian.tudu.data.utils.Response
import app.trian.tudu.data.utils.ResponseWithProgress
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
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
        else {
            try {
                val uuid = auth.currentUser?.uid
                val pictureRef = storage.reference
                    .child("PROFILE")
                    .child("${uuid.orEmpty()}.jpg")

                val baos = ByteArrayOutputStream()

                picture.compress(Bitmap.CompressFormat.JPEG, 70, baos)
                val uploadTask = pictureRef.putBytes(baos.toByteArray())

                uploadTask.await()
                val url = pictureRef.downloadUrl.await()

                val updateProfileChangeRequest = userProfileChangeRequest {
                    photoUri = url
                }
                auth.currentUser?.updateProfile(updateProfileChangeRequest)
                 emit(Response.Result(url.toString()))
            } catch (e: Exception) {
                emit(Response.Error(e.message.orEmpty()))
            }
        }
    }.flowOn(Dispatchers.IO)

}