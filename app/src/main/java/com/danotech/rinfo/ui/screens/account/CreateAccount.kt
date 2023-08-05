package com.danotech.rinfo.ui.screens.account

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.ClickableTextRow
import com.danotech.rinfo.ui.components.GoogleButton
import com.danotech.rinfo.ui.components.HeadingText
import com.danotech.rinfo.ui.components.RinfoButton
import com.danotech.rinfo.ui.components.SubHeadingText
import com.danotech.rinfo.ui.components.TextInput
import com.danotech.rinfo.ui.theme.AppTheme

@Composable
fun CreateAccount(
    modifier: Modifier = Modifier,
    onSignInTextClicked: () -> Unit = { },
    onBackHandler: () -> Unit = {}
) {
    BackHandler() {
        onBackHandler()
    }

    Surface {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.body_padding)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // page title
            HeadingText(
                text = R.string.create_account,
                modifier = Modifier.padding(5.dp)
            )

            // sub title
            SubHeadingText(text = R.string.have_an_account)

            Spacer(modifier = Modifier.height(20.dp))

            TextInput(
                labelText = "Name",
                leadingIcon = Icons.Default.Person,
            )

            TextInput(
                labelText = "Email",
                leadingIcon = Icons.Default.Email,
            )

            TextInput(
                labelText = "Password",
                leadingIcon = Icons.Default.Lock,
            )

            RinfoButton(
                name = R.string.create_account,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Divider()

            Spacer(modifier = Modifier.height(16.dp))

            GoogleButton(
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            ClickableTextRow(
                clickableText = R.string.sign_in,
                noneClickableText = R.string.have_an_account,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp),
                onSignUpTextClicked = onSignInTextClicked
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateAccountPreview() {
    AppTheme {
        CreateAccount()
    }
}

@Preview(showBackground = true)
@Composable
fun CreateAccountDarkPreview() {
    AppTheme(
        darkTheme = true,
    ) {
        CreateAccount()
    }
}