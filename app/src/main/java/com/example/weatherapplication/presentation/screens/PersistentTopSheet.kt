package com.example.weatherapplication.presentation.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.weatherapplication.presentation.ui.theme.DarkBlue
import com.example.weatherapplication.presentation.ui.theme.DeepBlue
import kotlin.math.roundToInt

@Composable
fun PersistentTopSheet(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
    ){
    var position by remember { mutableStateOf(if (isOpen) 0f else 300f) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    ) {
        if (isOpen) {
            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(0, position.roundToInt())
                    }
                    .fillMaxWidth()
                    .align(Alignment.TopStart)
                    .background(DeepBlue)
                    .clip(MaterialTheme.shapes.medium)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            position += dragAmount.y
                        }
                    }
            ) {
                content()
            }
        }
    }
    LaunchedEffect(isOpen) {
        position = if (isOpen) {
            0f
        } else {
            300f
        }
    }
}