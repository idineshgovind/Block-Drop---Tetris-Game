package logic

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import blockdrop_tetrisgame.composeapp.generated.resources.Res
import blockdrop_tetrisgame.composeapp.generated.resources.unidream_led
import org.jetbrains.compose.resources.Font
import kotlin.reflect.KClass

fun Offset(x: Int, y: Int) = androidx.compose.ui.geometry.Offset(x.toFloat(), y.toFloat())

enum class Direction {
    Left, Up, Right, Down
}

fun Direction.toOffset() = when (this) {
    Direction.Left -> -1 to 0
    Direction.Up -> 0 to -1
    Direction.Right -> 1 to 0
    Direction.Down -> 0 to 1
}


@Composable
fun LedFontFamily(): FontFamily {
    return FontFamily(
        Font(Res.font.unidream_led, FontWeight.Light),
        Font(Res.font.unidream_led, FontWeight.Normal),
        Font(Res.font.unidream_led, FontWeight.Normal, FontStyle.Italic),
        Font(Res.font.unidream_led, FontWeight.Medium),
        Font(Res.font.unidream_led, FontWeight.Bold)
    )
}


val NextMatrix = 4 to 2
const val ScoreEverySpirit = 12

fun calculateScore(lines: Int) = when (lines) {
    1 -> 100
    2 -> 300
    3 -> 700
    4 -> 1500
    else -> 0
}
