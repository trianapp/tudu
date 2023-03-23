/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package app.trian.tudu.data.sdk.auth


import app.trian.tudu.base.UnAuthorizedException
import app.trian.tudu.data.DriverFactory
import app.trian.tudu.data.createDatabase
import app.trian.tudu.data.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await


class AuthSDK(
    driverFactory: DriverFactory,
    private val firebaseAuth: FirebaseAuth
) {
    val db = createDatabase(driverFactory)

    fun isLoggedIn(): Boolean = firebaseAuth.currentUser != null

    fun getCurrentUser(): Flow<Response<FirebaseUser?>> = flow<Response<FirebaseUser?>> {
        emit(Response.Loading)
        val user = firebaseAuth.currentUser
        emit(Response.Result(user))
    }.flowOn(Dispatchers.IO)

    suspend fun signInWithGoogle(
        idToken: String?,
    ): Flow<Response<FirebaseUser?>> = flow {
        emit(Response.Loading)

        val user = firebaseAuth.signInWithCredential(
            GoogleAuthProvider.getCredential(idToken, null)
        ).await().user ?: throw UnAuthorizedException("User null")

        emit(Response.Result(user))
    }.flowOn(Dispatchers.IO)


    suspend fun signInWithEmail(
        email: String,
        password: String,
    ): Flow<Response<FirebaseUser?>> = flow {
        emit(Response.Loading)

        val user = firebaseAuth.signInWithEmailAndPassword(
            email, password
        ).await().user ?: throw UnAuthorizedException("User null")
        if (!user.isEmailVerified) {
            emit(Response.Error("Email Not verified!"))
            firebaseAuth.signOut()
        } else {
            emit(Response.Result(user))
        }
    }.flowOn(Dispatchers.IO)


    suspend fun registerWithEmail(
        displayName: String,
        email: String,
        password: String
    ): Flow<Response<FirebaseUser?>> = flow {
        emit(Response.Loading)
        val authenticate = firebaseAuth.createUserWithEmailAndPassword(
            email,
            password
        ).await()

        val user = firebaseAuth.currentUser
        val profileChangeRequest = userProfileChangeRequest {
            this.displayName = displayName
        }
        user?.updateProfile(profileChangeRequest)
        user?.sendEmailVerification()
        emit(Response.Result(authenticate.user))
    }.flowOn(Dispatchers.IO)

    suspend fun changePassword(newPassword: String): Flow<Response<Boolean>> = flow<Response<Boolean>> {
        emit(Response.Loading)
        val user = firebaseAuth.currentUser
        user?.updatePassword(newPassword)
            ?.await()
        emit(Response.Result(true))
    }.flowOn(Dispatchers.IO)

    suspend fun resetPasswordByEmail(email: String): Flow<Response<Boolean>> = flow<Response<Boolean>> {
        emit(Response.Loading)
        firebaseAuth.sendPasswordResetEmail(email)
        emit(Response.Result(true))
    }.flowOn(Dispatchers.IO)

}