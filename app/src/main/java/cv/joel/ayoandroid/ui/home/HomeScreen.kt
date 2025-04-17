package cv.joel.ayoandroid.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cv.joel.ayoandroid.R
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Composable
fun HomeScreen(onLessonClick: (String) -> Unit) {

    val viewModel = hiltViewModel<HomeViewModel>()

    val uiState = viewModel.state.collectAsStateWithLifecycle()
    Log.d("DEBUG_STATE", uiState.toString())

    val lessons = List(20) { index ->
        val id = "lesson${index + 1}"
        val title = "Lesson ${index + 1}"
        val isUnlocked = index <= 5
        Triple(id, title, isUnlocked)
    }

    val currentLessonIndex = lessons.indexOfFirst { it.third && lessons.indexOf(it) >= 5 }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val itemHeightPx = with(LocalDensity.current) { (200 + 48).dp.toPx().toInt() }

    val sectionTitle by derivedStateOf {
        val index = scrollState.value / itemHeightPx
        when (index) {
            in 0..4 -> "Section 1: Getting Started"
            in 5..9 -> "Section 2: Basics of Kotlin"
            in 10..14 -> "Section 3: Jetpack Compose"
            else -> "Section 4: Advanced Android"
        }
    }

    val unitTitle by derivedStateOf {
        val index = scrollState.value / itemHeightPx
        when (index) {
            in 0..4 -> "Unit: Introduction to Android"
            in 5..9 -> "Unit: Kotlin Fundamentals"
            in 10..14 -> "Unit: UI with Compose"
            else -> "Unit: Architecture & Patterns"
        }
    }

    val unitSummary by derivedStateOf {
        val index = scrollState.value / itemHeightPx
        when (index) {
            in 0..4 -> "Learn the basics of Android app structure and setup."
            in 5..9 -> "Understand Kotlin syntax and key programming principles."
            in 10..14 -> "Build beautiful UIs using Jetpack Compose."
            else -> "Dive into app architecture, patterns, and best practices."
        }
    }

    val viewportHeightPx = with(LocalDensity.current) { 600.dp.toPx() }.toInt()

    val currentLessonOffset =
        remember { derivedStateOf { if (currentLessonIndex >= 0) currentLessonIndex * itemHeightPx else 0 } }
    val showScrollToCurrentButton by derivedStateOf {
        val currentOffset = currentLessonOffset.value
        val visibleTop = scrollState.value
        val visibleBottom = scrollState.value + viewportHeightPx

        currentOffset < visibleTop || currentOffset > visibleBottom
    }

    // Instantly jump to the scroll position before UI renders
    LaunchedEffect(Unit) {
        scrollState.scrollTo(currentLessonOffset.value)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painterResource(id = R.drawable.space_background_3),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.banner_6_2),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 48.dp,
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 50.dp)
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        sectionTitle,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(unitTitle, color = Color.White.copy(alpha = 0.9f), fontSize = 16.sp)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(top = 48.dp),
                verticalArrangement = Arrangement.spacedBy(48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                lessons.forEachIndexed { index, (id, title, isUnlocked) ->
                    val isCompleted = index < 5
                    val alignment =
                        if (index % 2 == 0) Alignment.CenterStart else Alignment.CenterEnd

                    if (index == 0 || index == 5 || index == 10 || index == 15) {
                        Text(
                            text = unitSummary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Color.Black.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(all = 8.dp),
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        contentAlignment = alignment
                    ) {
                        LessonNodeFancy(
                            title = title,
                            isUnlocked = isUnlocked,
                            isCompleted = isCompleted,
                            onClick = { onLessonClick(id) }
                        )
                    }
                }
            }
        }

        if (showScrollToCurrentButton) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollTo(currentLessonOffset.value)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .zIndex(2f),
                shape = RoundedCornerShape(4.dp),
                border = BorderStroke(2.dp, Color.White)
            ) {
                if (scrollState.value > currentLessonOffset.value) {
                    Icon(Icons.Default.ArrowUpward, contentDescription = "Scroll Up")
                } else {
                    Icon(Icons.Default.ArrowDownward, contentDescription = "Scroll Down")
                }
            }
        }
    }
}

@Composable
fun LessonNodeFancy(
    title: String,
    isUnlocked: Boolean,
    isCompleted: Boolean,
    onClick: () -> Unit
) {
    val planetRes = when {
        isCompleted -> R.drawable.completed_planet_2
        isUnlocked -> R.drawable.current_planet
        else -> R.drawable.locked_planet_2
    }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.1f else 1f,
        animationSpec = tween(200),
        label = "scaleAnim"
    )

    val isCurrent = isUnlocked && !isCompleted

    val infiniteTransition = rememberInfiniteTransition(label = "glow-ring")
    val ringAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ringAlpha"
    )
    val ringScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ringScale"
    )

    Box(
        modifier = Modifier
            .size(200.dp)
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .then(
                if (isUnlocked) Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick
                ) else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isCurrent) {
            Box(
                modifier = Modifier
                    .size(220.dp * ringScale)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(Color.Cyan.copy(alpha = ringAlpha), Color.Transparent)
                        )
                    )
            )
        }

        Image(
            painter = painterResource(id = planetRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}
