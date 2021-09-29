package com.adesso.movee.scene.login

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adesso.movee.R
import com.adesso.movee.base.BaseTransparentStatusBarFragment
import com.adesso.movee.databinding.FragmentLoginBinding
import com.adesso.movee.internal.extension.observeNonNull
import com.adesso.movee.internal.util.Event

class LoginFragment : BaseTransparentStatusBarFragment<LoginViewModel, FragmentLoginBinding>() {

    override val layoutId = R.layout.fragment_login

    override fun initialize() {
        super.initialize()
        binder.composeView.setContent { LoginComposable(viewModel = viewModel) }

        viewModel.navigateUri.observeNonNull(viewLifecycleOwner, ::handleNavigateUriEvent)
    }

    private fun handleNavigateUriEvent(event: Event<Uri>) {
        event.getContentIfNotHandled()?.let { uri ->
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    @Composable
    fun LoginComposable(viewModel: LoginViewModel) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(R.drawable.ic_login_background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 32.dp, end = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                val username by viewModel.username.observeAsState("")
                val password by viewModel.password.observeAsState("")
                var passwordVisibility by remember { mutableStateOf(false) }

                Image(
                    painter = painterResource(id = R.drawable.ic_movee),
                    contentDescription = null,
                    modifier = Modifier.padding(top = 100.dp)
                )

                TextField(
                    value = username,
                    onValueChange = { username ->
                        viewModel.username.value = username
                    },
                    textStyle = TextStyle(color = Color.White),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    label = {
                        Text(
                            text = stringResource(id = R.string.login_hint_username),
                            color = Color.White
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.Gray,
                        backgroundColor = Color.Transparent,
                        textColor = Color.White,
                        cursorColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(id = R.dimen.margin_login_image_view_movee))
                )

                TextField(
                    value = password,
                    onValueChange = { password ->
                        viewModel.password.value = password
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.White),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation =
                    if (passwordVisibility) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        val icon = if (passwordVisibility) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff
                        IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                            Icon(imageVector = icon, contentDescription = null, tint = Color.White)
                        }
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.login_hint_password),
                            color = Color.White
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.Gray,
                        backgroundColor = Color.Transparent,
                        textColor = Color.White,
                        cursorColor = Color.White
                    )
                )

                Text(
                    text = stringResource(id = R.string.login_message_forgot_password),
                    Modifier
                        .clickable {
                            viewModel.onForgotPasswordClick()
                        }
                        .padding(top = 16.dp)
                        .align(alignment = Alignment.End),
                    color = Color.White,
                    textAlign = TextAlign.End
                )

                OutlinedButton(
                    onClick = { viewModel.onLoginClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    colors = buttonColors(Color.White)
                ) {
                    Text(
                        text = stringResource(id = R.string.login_message_login),
                        color = colorResource(id = R.color.vibrant_blue),
                        fontSize = 17.sp
                    )
                }

                Text(
                    text = stringResource(id = R.string.login_message_register),
                    Modifier
                        .clickable {
                            viewModel.onRegisterClick()
                        }
                        .padding(top = 32.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    color = Color.White,
                    textAlign = TextAlign.End
                )
            }
        }
    }

    @Preview
    @Composable
    fun LoginPreview() {
        LoginComposable(viewModel = viewModel)
    }
}
