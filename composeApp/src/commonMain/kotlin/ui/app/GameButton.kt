package ui.app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ui.theme.Purple500

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GameButton(
    modifier: Modifier = Modifier,
    size: Dp,
    onClick: () -> Unit = {},
    autoInvokeWhenPressed: Boolean = false,
    buttonColor: Color = Purple500,
    content: @Composable (Modifier) -> Unit = {},
) {
    val backgroundShape = RoundedCornerShape(size / 2)
    lateinit var ticker: ReceiveChannel<Unit>

    val coroutineScope = rememberCoroutineScope()
    val pressedInteraction = remember { mutableStateOf<PressInteraction.Press?>(null) }
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Box(
        modifier = modifier
            .shadow(10.dp, shape = backgroundShape) // Outer shadow for 3D effect
            .size(size = size)
            .clip(backgroundShape)
            .background(
                buttonColor
            )
            .indication(interactionSource = interactionSource, indication = rememberRipple())
            .drawWithContent {
                // Inner shadow
                drawContent()
                drawRoundRect(
                    color = Color(0x33000000), // semi-transparent black for shadow
                    topLeft = Offset(4.dp.toPx(), 4.dp.toPx()), // Offset for shadow direction
                    cornerRadius = CornerRadius((size / 2).toPx(), (size / 2).toPx()),
                    blendMode = BlendMode.Multiply
                )
            }
            .run {
//                if (autoInvokeWhenPressed) {
//                    pointerInteropFilter {
//                        when (it.action) {
//                            ACTION_DOWN -> {
//                                coroutineScope.launch {
//                                    // Remove any old interactions if we didn't fire stop / cancel properly
//                                    pressedInteraction.value?.let { oldValue ->
//                                        val interaction = PressInteraction.Cancel(oldValue)
//                                        interactionSource.emit(interaction)
//                                        pressedInteraction.value = null
//                                    }
//                                    val interaction = PressInteraction.Press(Offset(50f, 50f))
//                                    interactionSource.emit(interaction)
//                                    pressedInteraction.value = interaction
//                                }
//
//                                ticker = ticker(initialDelayMillis = 300, delayMillis = 60)
//                                coroutineScope.launch {
//                                    ticker
//                                        .receiveAsFlow()
//                                        .collect { onClick() }
//                                }
//                            }
//
//                            ACTION_CANCEL, ACTION_UP -> {
//                                coroutineScope.launch {
//                                    pressedInteraction.value?.let {
//                                        val interaction = PressInteraction.Cancel(it)
//                                        interactionSource.emit(interaction)
//                                        pressedInteraction.value = null
//                                    }
//                                }
//                                ticker.cancel()
//                                if (it.action == ACTION_UP) {
//                                    onClick()
//                                }
//                            }
//
//                            else -> {
//                                if (it.action != ACTION_MOVE) {
//                                    ticker.cancel()
//                                }
//                                return@pointerInteropFilter false
//                            }
//                        }
//                        true
//                    }
//                } else {
                    clickable { onClick() }
//                }
            }

    ) {
        content(Modifier.align(Alignment.Center))
    }
}
