package io.github.zyrouge.symphony.ui.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import io.github.zyrouge.symphony.App
import io.github.zyrouge.symphony.services.interfaces.SystemCallback
import io.github.zyrouge.symphony.ui.helpers.Routes.Home
import io.github.zyrouge.symphony.ui.helpers.ViewContext
import io.github.zyrouge.symphony.utils.Preferences
import java.util.UUID

@Composable
fun LoginView(context: ViewContext) {
    LoginForm(context)
}

@Composable
fun UrlField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Url",
    placeHolder: String = "Enter your url",
) {
    val focusManager = LocalFocusManager.current
    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Person,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        placeholder = { Text(placeHolder) },
        label = { Text(label) },
        singleLine = true,
        visualTransformation = VisualTransformation.None
    )
}

@Composable
fun LoginField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Login",
    placeHolder: String = "Enter your login",
) {
    val focusManager = LocalFocusManager.current
    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Person,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        placeholder = { Text(placeHolder) },
        label = { Text(label) },
        singleLine = true,
        visualTransformation = VisualTransformation.None
    )
}

@Composable
fun PasswordField(
    value: String,
    onChange: (String) -> Unit,
    submit: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Password",
    placeholder: String = "Enter your Password",
) {

    var isPasswordVisible by remember { mutableStateOf(false) }

    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Key,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
    val trailingIcon = @Composable {
        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
            Icon(
                if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }


    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(
            onDone = { submit() }
        ),
        placeholder = { Text(placeholder) },
        label = { Text(label) },
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun LoginForm(context: ViewContext) {
    Surface {
        var credentials by remember { mutableStateOf(Credentials()) }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            UrlField(
                value = credentials.server,
                onValueChange = { data -> credentials = credentials.copy(server = data) },
                modifier = Modifier.fillMaxWidth()
            )
            LoginField(
                value = credentials.user,
                onValueChange = { data -> credentials = credentials.copy(user = data) },
                modifier = Modifier.fillMaxWidth()
            )
            PasswordField(
                value = credentials.password,
                onChange = { data -> credentials = credentials.copy(password = data) },
                submit = {
                    if (!checkCredentials(credentials, context)) credentials = Credentials()
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            LabeledCheckbox(
                label = "Remember Me",
                onCheckChanged = {
                    credentials = credentials.copy(remember = !credentials.remember)
                },
                isChecked = credentials.remember
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    if (!checkCredentials(credentials, context)) credentials = Credentials()
                },
                enabled = credentials.isNotEmpty(),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Login")
            }
        }
    }
}

@Composable
fun LabeledCheckbox(
    label: String,
    onCheckChanged: () -> Unit,
    isChecked: Boolean,
) {
    Row(
        Modifier
            .clickable(
                onClick = onCheckChanged
            )
            .padding(4.dp)
    ) {
        Checkbox(checked = isChecked, onCheckedChange = null)
        Spacer(Modifier.size(6.dp))
        Text(label)
    }
}

fun checkCredentials(creds: Credentials, context: ViewContext): Boolean {
    if (creds.isNotEmpty() && creds.user == "codefish") {

        //保存账号密码信息到Preference
        saveServerPreference(
            UUID.randomUUID().toString(),
            creds.server,
            creds.user,
            creds.password,
            creds.isLowSecurity
        )
        val systemRepository = context.symphony.groove.systemRepository

        systemRepository.checkUserCredential(object : SystemCallback {
            override fun onError(exception: Exception) {
                resetServerPreference()
                Log.d("Lee4", "onError: 登录失败:$exception")
            }

            override fun onSuccess(password: String?, token: String, salt: String) {
                context.navController.popBackStack()
                context.navController.navigate(Home.route) {
                    this.launchSingleTop = true
                }
                Log.d("Lee4", "onSuccess: 登录成功")
            }
        })

        return true
    } else {
        Toast.makeText(context.activity, "Wrong Credentials", Toast.LENGTH_SHORT).show()
        return false
    }
}

data class Credentials(
    var server: String = "https://music.15731573.xyz",
    var user: String = "codefish",
    var password: String = "10bsyr1000lblx@Zxj",
    var isLowSecurity: Boolean = false,
    var remember: Boolean = true,
) {
    fun isNotEmpty(): Boolean {
        return user.isNotEmpty() && password.isNotEmpty()
    }
}

private fun saveServerPreference(
    serverId: String,
    server: String,
    user: String,
    password: String,
    isLowSecurity: Boolean,
) {
    Preferences.setServerId(serverId)
    Preferences.setServer(server)
    Preferences.setUser(user)
    Preferences.setPassword(password)
    Preferences.setLowSecurity(isLowSecurity)
    App.getSubsonicClientInstance(true)
}

private fun resetServerPreference() {
    Preferences.setServerId(null)
    Preferences.setServer(null)
    Preferences.setUser(null)
    Preferences.setPassword(null)
    Preferences.setToken(null)
    Preferences.setSalt(null)
    Preferences.setLowSecurity(false)

    App.getSubsonicClientInstance(true)
}
