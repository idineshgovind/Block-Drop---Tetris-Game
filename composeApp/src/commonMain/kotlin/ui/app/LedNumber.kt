package ui.app

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import logic.LedFontFamily
import ui.theme.BrickMatrix
import ui.theme.BrickSpirit
import kotlin.math.roundToInt

@Composable
fun LedClock(modifier: Modifier = Modifier) {

    val animateValue by rememberInfiniteTransition().animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
    )

    var clock by remember { mutableStateOf(0 to 0) }

    DisposableEffect(key1 = animateValue.roundToInt()) {
        val (curHou, curMin) = DateTime.getFormattedDate("2023-09-22T10:18:15.311Z").split(",")
        clock = curHou.toInt() to curMin.toInt()
        onDispose { }
    }

    Row(modifier) {
        LedNumber(num = clock.first, digits = 2, fillZero = true)

        val LedComma: @Composable (color: Color) -> Unit = remember {
            {
                Text(
                    ":",
                    fontFamily = LedFontFamily(),
                    textAlign = TextAlign.End,
                    color = it,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Box(
            modifier = Modifier
                .width(6.dp)
                .padding(end = 1.dp),
        ) {

            LedComma(BrickMatrix)
            if (animateValue.roundToInt() == 1) {
                LedComma(BrickSpirit)
            }

        }

        LedNumber(num = clock.second, digits = 2, fillZero = true)
    }

}

@Composable
fun LedNumber(
    modifier: Modifier = Modifier,
    num: Int,
    digits: Int,
    fillZero: Boolean = false
) {
    val LedFontFamily = LedFontFamily()
    val textSize = 16.sp
    val textWidth = 8.dp
    Box(modifier) {
        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
            repeat(digits) {
                Text(
                    "8",
                    fontSize = textSize,
                    color = BrickMatrix,
                    fontFamily = LedFontFamily,
                    modifier = Modifier.width(textWidth),
                    textAlign = TextAlign.End

                )
            }

        }
        Row(
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            val str = if (fillZero) num.toString().padStart(digits, '0') else num.toString()
            str.iterator().forEach {
                Text(
                    text = it.toString(),
                    fontSize = textSize,
                    color = BrickSpirit,
                    fontFamily = LedFontFamily,
                    modifier = Modifier.width(textWidth),
                    textAlign = TextAlign.End
                )
            }
        }

    }
}

expect object DateTime {
    fun getFormattedDate(
        timestamp: String,
    ): String
}
