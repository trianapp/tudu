package app.trian.tudu.data.domain.user

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class CheckSessionUserUseCase @Inject constructor(
    private val auth: FirebaseAuth
) {
    operator fun invoke(): Boolean = auth.currentUser != null
}