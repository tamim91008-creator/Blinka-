package com.example.ui

import android.media.AudioManager
import android.media.ToneGenerator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Emerald400
import kotlinx.coroutines.delay

@Composable
fun BreakScreen(
    onFinished: () -> Unit,
    onRecordBreak: () -> Unit
) {
    var timeLeft by remember { mutableIntStateOf(20) }
    var isFinished by remember { mutableStateOf(false) }
    
    val progress by animateFloatAsState(
        targetValue = timeLeft / 20f,
        label = "progress_anim"
    )

    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000)
            timeLeft--
        }
        isFinished = true
        onRecordBreak()
        
        try {
            val toneGen = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100)
            toneGen.startTone(ToneGenerator.TONE_PROP_PROMPT, 150)
            delay(200)
            toneGen.release()
        } catch (e: Exception) {
            // Ignore if audio manager fails
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(32.dp)
            ) {
                Text(
                    text = "CARE PROTOCOL",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 10.sp,
                        letterSpacing = 2.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Emerald400.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Distance Focus",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.5).sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Look 20 feet away to relax your eye muscles.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(64.dp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(240.dp)
                ) {
                    CircularProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.fillMaxSize(),
                        color = Emerald400,
                        trackColor = MaterialTheme.colorScheme.surface,
                        strokeWidth = 6.dp
                    )
                    
                    if (isFinished) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Done",
                                modifier = Modifier.size(64.dp),
                                tint = Emerald400
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "COMPLETE",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Medium,
                                    letterSpacing = 2.sp
                                ),
                                color = Emerald400
                            )
                        }
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "00:${timeLeft.toString().padStart(2, '0')}",
                                style = MaterialTheme.typography.displayLarge.copy(
                                    fontSize = 64.sp,
                                    fontWeight = FontWeight.Light,
                                    fontFeatureSettings = "tnum",
                                    letterSpacing = (-2).sp
                                ),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "RELAXING",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Medium,
                                    letterSpacing = 2.sp
                                ),
                                color = Emerald400.copy(alpha = 0.8f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(64.dp))

                Box(modifier = Modifier.height(64.dp)) {
                    Column {
                        AnimatedVisibility(visible = isFinished) {
                            Button(
                                onClick = onFinished,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Emerald400,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                ),
                                shape = CircleShape,
                                modifier = Modifier
                                    .fillMaxWidth(0.6f)
                                    .height(56.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "FINISH",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
