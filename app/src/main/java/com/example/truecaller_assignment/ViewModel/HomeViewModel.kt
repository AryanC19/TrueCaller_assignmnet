import android.app.Application
import android.graphics.Color
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.truecaller_assignment.R

import com.truecaller.android.sdk.oAuth.TcSdk
import com.truecaller.android.sdk.oAuth.TcOAuthCallback
import com.truecaller.android.sdk.oAuth.TcOAuthData
import com.truecaller.android.sdk.oAuth.TcOAuthError
import com.truecaller.android.sdk.oAuth.CodeVerifierUtil
import com.truecaller.android.sdk.oAuth.TcSdkOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigInteger
import java.security.SecureRandom


import kotlinx.coroutines.Dispatchers

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header

// API Interface for token exchange
interface TruecallerApi {
    @FormUrlEncoded
    @POST("token")
    suspend fun getAccessToken(
        @Field("code") code: String,
        @Field("grant_type") grantType: String = "authorization_code",
        @Field("code_verifier") codeVerifier: String,
        @Field("client_id") clientId: String
    ): TokenResponse

    @GET("userinfo")
    suspend fun getUserProfile(
        @Header("Authorization") authToken: String
    ): TruecallerProfile
}

data class TokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int
)

// The user profile JSON includes "given_name", "family_name", "phone_number", etc.
data class TruecallerProfile(
    val sub: String?,
    val given_name: String?,
    val family_name: String?,
    val phone_number: String?,
    val email: String?,
    val picture: String?,
    val gender: String?,
    val phone_number_country_code: String?,
    val phone_number_verified: Boolean?,
    val address: Address?
)

data class Address(
    val locality: String?,
    val postal_code: String?
)


data class Name(
    val first: String,
    val last: String
)

data class PhoneNumber(
    val number: String,
    val countryCode: String
)
class HomeViewModel(application: Application) : AndroidViewModel(application) {
    // Lazy initialize the SDK instance
    private val truecallerSdk by lazy { TcSdk.getInstance() }
    private var currentState: String = ""
    private var codeVerifier: String? = null

    // UI state
    private val _uiState = MutableStateFlow<RegistrationState>(RegistrationState.Initial)
    val uiState = _uiState.asStateFlow()

    // User data
    private val _userData = MutableStateFlow<UserData?>(null)
    val userData = _userData.asStateFlow()

    private val api = Retrofit.Builder()
        .baseUrl("https://oauth-account-noneu.truecaller.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TruecallerApi::class.java)




    private val tcOAuthCallback = object : TcOAuthCallback {
        override fun onSuccess(tcOAuthData: TcOAuthData) {
            if (tcOAuthData.state == currentState) {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val tokenResponse = api.getAccessToken(
                            code = tcOAuthData.authorizationCode,
                            codeVerifier = codeVerifier ?: "",
                            clientId = getApplication<Application>().getString(R.string.clientID)
                        )
                        val profile = api.getUserProfile("Bearer ${tokenResponse.access_token}")

                        // Map retrieved data to your own UserData
                        _userData.value = UserData(
                            firstName = profile.given_name ?: "",
                            lastName = profile.family_name ?: "",
                            phoneNumber = profile.phone_number ?: ""
                        )
                        _uiState.value = RegistrationState.Success
                    } catch (e: Exception) {
                        _uiState.value = RegistrationState.Error("Failed to get user data: ${e.message}")
                    }
                }
            } else {
                _uiState.value = RegistrationState.Error("State verification failed")
            }
        }

        override fun onFailure(tcOAuthError: TcOAuthError) {
            _uiState.value = RegistrationState.Error(tcOAuthError.errorMessage)
        }

        override fun onVerificationRequired(tcOAuthError: TcOAuthError?) {
            _uiState.value = RegistrationState.Error(tcOAuthError?.errorMessage ?: "Verification required")
        }
    }

    fun resetUiState() {
        _uiState.value = RegistrationState.Initial
    }
    // Call this method first before any other SDK operations
    fun initializeTruecaller(activity: FragmentActivity) {
        try {
            // Clear any existing instance
            TcSdk.clear()

            val tcSdkOptions = TcSdkOptions.Builder(activity, tcOAuthCallback)
                .buttonColor(Color.parseColor("#2196F3"))
                .buttonTextColor(Color.parseColor("#FFFFFF"))
                .loginTextPrefix(TcSdkOptions.LOGIN_TEXT_PREFIX_TO_GET_STARTED)
                .ctaText(TcSdkOptions.CTA_TEXT_CONTINUE)
                .buttonShapeOptions(TcSdkOptions.BUTTON_SHAPE_ROUNDED)
                .footerType(TcSdkOptions.FOOTER_TYPE_SKIP)
                .sdkOptions(TcSdkOptions.OPTION_VERIFY_ONLY_TC_USERS)
                .build()
            TcSdk.init(tcSdkOptions)

        } catch (e: Exception) {
            throw RuntimeException("Failed to initialize Truecaller SDK: ${e.message}")
        }
    }
    fun initiateRegistration(activity: FragmentActivity) {
        try {
            // Check OAuth flow
            if (!TcSdk.getInstance().isOAuthFlowUsable) {
                _uiState.value = RegistrationState.Error("Truecaller is not available")
                return
            }


            // Check OAuth flow
            if (!TcSdk.getInstance().isOAuthFlowUsable()) {  // Fixed: using correct method name with parentheses
                _uiState.value = RegistrationState.Error("Truecaller is not available")
                return
            }

            // Continue with OAuth flow
            currentState = BigInteger(130, SecureRandom()).toString(32)
            truecallerSdk.setOAuthState(currentState)

            codeVerifier = CodeVerifierUtil.generateRandomCodeVerifier()
            val codeChallenge = codeVerifier?.let { CodeVerifierUtil.getCodeChallenge(it) }
                ?: throw Exception("Failed to generate code challenge")

            truecallerSdk.setCodeChallenge(codeChallenge)
            truecallerSdk.setOAuthScopes(arrayOf("profile", "phone"))

            truecallerSdk.getAuthorizationCode(activity)
            _uiState.value = RegistrationState.InProgress
        } catch (e: Exception) {
            _uiState.value = RegistrationState.Error(e.message ?: "Registration failed")
        }
    }
}
// States for the registration flow
sealed class RegistrationState {
    object Initial : RegistrationState()
    object InProgress : RegistrationState()
    object Success : RegistrationState()
    data class Error(val message: String) : RegistrationState()
    data class Loaded(val userData: UserData) : RegistrationState() // Updated state
}


// Data class to hold user information
data class UserData(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String
)