import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.composescreenshottesting.ui.login.LoginScreen
import com.example.composescreenshottesting.ui.login.LoginState
import com.example.composescreenshottesting.ui.theme.ComposeScreenshotTestingTheme


@Preview
@Composable
private fun LoginScreenPreview() {
    ComposeScreenshotTestingTheme {
        LoginScreen(
            state = LoginState(),
            onAction = {}
        )
    }
}