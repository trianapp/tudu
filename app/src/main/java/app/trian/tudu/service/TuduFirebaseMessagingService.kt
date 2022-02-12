package app.trian.tudu.service

import android.annotation.SuppressLint
import app.trian.tudu.data.repository.design.UserRepository
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
@AndroidEntryPoint
class TuduFirebaseMessagingService: FirebaseMessagingService(){
    @Inject
    lateinit var userRepository: UserRepository

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        GlobalScope.launch {
            userRepository.registerFCMTokenAndSubscribeTopic()
        }



    }

}