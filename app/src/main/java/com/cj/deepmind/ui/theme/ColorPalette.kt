package com.cj.deepmind.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ColorPalette(
    val background : Color = Color.Unspecified,
    val txtColor : Color = Color.Unspecified,
    val btnColor : Color = Color.Unspecified,
    val txtFieldColor : Color = Color.Unspecified
)

val DeepMindColorPalette = staticCompositionLocalOf {
    ColorPalette()
}