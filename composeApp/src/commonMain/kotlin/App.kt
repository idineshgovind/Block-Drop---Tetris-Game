import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import blockdrop_tetrisgame.composeapp.generated.resources.Res
import blockdrop_tetrisgame.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import logic.Action
import logic.Direction
import logic.GameViewModel
import ui.app.GameBody
import ui.app.GameScreen
import ui.app.combinedClickable
import ui.theme.ComposetetrisTheme

@OptIn(ObsoleteCoroutinesApi::class)
@Composable
@Preview
fun App() {
    ComposetetrisTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {

            val viewModel = viewModel { GameViewModel() }
            val viewState = viewModel.viewState.value

            LaunchedEffect(key1 = Unit) {
                while (isActive) {
                    delay(650L - 55 * (viewState.level - 1))
                    viewModel.dispatch(Action.GameTick)
                }
            }

            val lifecycleOwner = LocalLifecycleOwner.current
            DisposableEffect(key1 = Unit) {
                val observer = object : DefaultLifecycleObserver {
                    override fun onResume(owner: LifecycleOwner) {
                        viewModel.dispatch(Action.Resume)
                    }

                    override fun onPause(owner: LifecycleOwner) {
                        viewModel.dispatch(Action.Pause)
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            }


            GameBody(combinedClickable(
                onMove = { direction: Direction ->
                    if (direction == Direction.Up) viewModel.dispatch(Action.Drop)
                    else viewModel.dispatch(Action.Move(direction))
                },
                onRotate = {
                    viewModel.dispatch(Action.Rotate)
                },
                onRestart = {
                    viewModel.dispatch(Action.Reset)
                },
                onPause = {
                    if (viewModel.viewState.value.isRuning) {
                        viewModel.dispatch(Action.Pause)
                    } else {
                        viewModel.dispatch(Action.Resume)
                    }
                },
                onMute = {
                    viewModel.dispatch(Action.Mute)
                }
            )) {
                GameScreen(
                    Modifier.fillMaxSize()
                )
            }
        }
    }
}