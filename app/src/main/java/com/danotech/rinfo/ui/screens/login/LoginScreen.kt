package com.danotech.rinfo.ui.screens.login

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.presentation.sign_in.GoogleAuthUiClient
import com.danotech.rinfo.presentation.sign_in.SignInState
import com.danotech.rinfo.presentation.sign_in.SignInViewModel
import com.danotech.rinfo.ui.ThemeViewModel
import com.danotech.rinfo.ui.components.ClickableTextRow
import com.danotech.rinfo.ui.components.EmailField
import com.danotech.rinfo.ui.components.GoogleButton
import com.danotech.rinfo.ui.components.HeadingText
import com.danotech.rinfo.ui.components.OrFormDiver
import com.danotech.rinfo.ui.components.PasswordField
import com.danotech.rinfo.ui.components.SignInButton
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.UserViewModel
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.GoogleAuthProvider
import com.stevdzasan.onetap.OneTapSignInWithGoogle
import com.stevdzasan.onetap.rememberOneTapSignInState
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    openAndPopUp: (String, String) -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    onSignUpTextClicked: () -> Unit = {},
    onBackHandler: () -> Unit = {},
    onResetPassword: () -> Unit = {},
    window: Window
) {
    val context = LocalContext.current as Activity
    val coroutineScope = rememberCoroutineScope()
    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }

    val viewModel = viewModel<SignInViewModel>()
    val state by viewModel.state.collectAsState()
    LaunchedEffect(key1 = Unit) {
        if (googleAuthUiClient.getSignedInUser() != null) {
            openAndPopUp(RInfoScreen.Login.name, RInfoScreen.Home.name)
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == RESULT_OK) {
                coroutineScope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    viewModel.onSignInResult(signInResult)
                }
            }
        }
    )

    LaunchedEffect(key1 = state.isSignInSuccessful) {
        if (state.isSignInSuccessful) {
            Toast.makeText(
                context,
                "Sign in successful",
                Toast.LENGTH_LONG
            ).show()

            openAndPopUp(RInfoScreen.Login.name, RInfoScreen.Home.name)
            viewModel.resetState()
        }
    }

    val signInViewModel = viewModel<SignInViewModel>()
    val signInState by signInViewModel.state.collectAsState()
    val uiState = loginViewModel.uiState.collectAsState().value

    BackHandler {
        onBackHandler()
    }

    Scaffold { innerPadding ->
        // User is not logged in, show login form
        // User is logged in, handle this case
        LoginForm(
            email = uiState.email,
            password = uiState.password,
            openAndPopUp = openAndPopUp,
            innerPadding = innerPadding,
            onResetPassword = onResetPassword,
            onSignUpTextClicked = onSignUpTextClicked,
            viewModel = loginViewModel,
            userViewModel = userViewModel,
            signInState = signInState,
            onSignInClick = {
                coroutineScope.launch {
                    val signInIntentSender = googleAuthUiClient.signIn()

                    launcher.launch(
                        IntentSenderRequest.Builder(
                            signInIntentSender ?: return@launch
                        ).build()
                    )
                }
            }
        )
    }
}

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    innerPadding: PaddingValues,
    viewModel: LoginViewModel,
    userViewModel: UserViewModel,
    openAndPopUp: (String, String) -> Unit,
    onResetPassword: () -> Unit = { },
    onSignUpTextClicked: () -> Unit = { },
    signInState: SignInState,
    onSignInClick: () -> Unit = {}
) {
    val context = LocalContext.current

    var isRememberMeChecked by remember {
        mutableStateOf(false)
    }

    val uiState = viewModel.uiState.collectAsState().value

    val spaceLarge = 16.dp

    LaunchedEffect(key1 = signInState.signInError) {
        signInState.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.body_padding))
            .padding(paddingValues = innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(spaceLarge))
        // page title
        HeadingText(text = R.string.sign_in)

        Spacer(modifier = Modifier.height(spaceLarge))

        AnimatedVisibility(visible = uiState.hasMessage) {
            Text(
                text = uiState.message,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(0.44f),
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }

        EmailField(
            value = email,
            onValueChanged = viewModel::onEmailChange,
        )

        PasswordField(
            value = password,
            onValueChanged = viewModel::onPasswordChange,
        )

        SignInButton(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            viewModel.signInClick(openAndPopUp)
            userViewModel.login(uiState.email, uiState.password)
        }

        Spacer(modifier = Modifier.height(spaceLarge))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box() {

            }
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(3.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Checkbox(
//                    checked = isRememberMeChecked,
//                    onCheckedChange = {
//                        isRememberMeChecked = true
//                    },
//                    modifier = Modifier.size(10.dp)
//                )
//
//                Text(text = stringResource(id = R.string.remember_me))
//            }

            ClickableText(
                text = AnnotatedString(text = stringResource(id = R.string.reset_password)),
                onClick = {
                    onResetPassword()
                },
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                ),
            )
        }

        Spacer(modifier = Modifier.height(spaceLarge))

        OrFormDiver()

        Spacer(modifier = Modifier.height(spaceLarge))

        val state = rememberOneTapSignInState()

        OneTapSignInWithGoogle(
            state = state,
            clientId = stringResource(id = R.string.web_client_id),
            onTokenIdReceived = { tokenId ->
                try {
                    val credentials = GoogleAuthProvider.getCredential(tokenId, null)

                    viewModel.signInWithCredentials(credentials, openAndPopUp)
                    Log.d("LOG", tokenId)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("LOG", e.message.toString())
                }
            },
            onDialogDismissed = { message ->
                Log.d("LOG", message)
            }
        )

        GoogleButton(
            onClick = onSignInClick,
            text = "SignIn with google",
            modifier = modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(spaceLarge))

        ClickableTextRow(
            clickableText = R.string.sign_up,
            noneClickableText = R.string.dont_have_an_account,

            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            onSignUpTextClicked = onSignUpTextClicked
        )
    }
}
