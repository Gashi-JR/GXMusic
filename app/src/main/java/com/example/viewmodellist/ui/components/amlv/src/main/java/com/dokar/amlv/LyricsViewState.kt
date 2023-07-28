package com.dokar.amlv

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.dokar.amlv.parser.LrcLyricsParser
import com.example.viewmodellist.ui.components.find.MediaPlayerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

@Composable
fun rememberLyricsViewState(
    lrcContent: String,
    mediaPlayerViewModel: MediaPlayerViewModel,
): LyricsViewState {
    val scope = rememberCoroutineScope()
    val parser = remember { LrcLyricsParser() }
    return remember(scope, parser, lrcContent, mediaPlayerViewModel) {
        val lyrics = parser.parse(lrcContent)
        LyricsViewState(lyrics, mediaPlayerViewModel, scope)
    }
}

@Composable
fun rememberLyricsViewState(
    lyrics: Lyrics?,
    mediaPlayerViewModel: MediaPlayerViewModel,
): LyricsViewState {
    val scope = rememberCoroutineScope()
    return remember(scope, lyrics) { LyricsViewState(lyrics, mediaPlayerViewModel, scope) }
}

@Stable
class LyricsViewState(
    lyrics: Lyrics?,
    val mediaPlayerViewModel: MediaPlayerViewModel,
    private val scope: CoroutineScope,
    private val tickMillis: Long = 50L,
) {
    val lyrics: Lyrics?

    private val lineCount = lyrics?.lines?.size ?: 0


    internal var currentLineIndex by mutableStateOf(-1)
        private set


    private var playbackJob: Job? = null

    init {
        require(tickMillis > 0) { "tickMillis must > 0" }
        this.lyrics = lyrics?.run {
            // Make sure all lines are sorted by the start time
            copy(lines = lines.sortedBy { it.startAt })
        }
    }

    fun play() {
        Log.d(TAG, "play: 第一次播放$lyrics")
        if (lyrics == null) return

        val lines = lyrics.lines
        if (lines.isEmpty()) return

        if (mediaPlayerViewModel.currentPosition !in 0..lyrics.optimalDurationMillis) {
            return
        }

        playbackJob?.cancel()
        playbackJob = scope.launch {
            var currLineIdx = findLineIndexAt(mediaPlayerViewModel.currentPosition.toLong())
            currentLineIndex = currLineIdx

            mediaPlayerViewModel.isPlaying = true

            fun checkFinished(): Boolean {
                return !isActive || !mediaPlayerViewModel.isPlaying || mediaPlayerViewModel.currentPosition >= lyrics.optimalDurationMillis
            }

            /**
             * @return deviation millis
             */
            suspend fun startTicking(duration: Long): Long {
                if (duration <= 0) return 0L

                val loops = duration / tickMillis
                val extraDelay = duration % tickMillis
                var i = 0L
                var millis = measureTimeMillis {
                    while (i < loops && !checkFinished()) {
                        delay(tickMillis)
                        mediaPlayerViewModel.currentPosition =
                            (mediaPlayerViewModel.currentPosition.toLong() + tickMillis).toInt()
                        i++
                    }
                }
                val deviation = i * tickMillis - millis

                if (checkFinished()) {
                    return deviation
                }

                millis = measureTimeMillis {
                    delay(extraDelay)
                    mediaPlayerViewModel.currentPosition =
                        (mediaPlayerViewModel.currentPosition.toLong() + extraDelay).toInt()
                }
                val extraDeviation = extraDelay - millis

                return extraDeviation + deviation
            }

            var deviationMillis = 0L

            while (currLineIdx < lineCount && !checkFinished()) {
                currentLineIndex = currLineIdx

                val duration = if (currLineIdx < 0) {
                    lines.first().startAt - mediaPlayerViewModel.currentPosition
                } else {
                    lines[currLineIdx].durationMillis
                }
                deviationMillis = startTicking(duration + deviationMillis)

                if (checkFinished()) {
                    return@launch
                }

                if (currLineIdx in 0 until lineCount - 1) {
                    val lineStopAt = lines[currLineIdx].let { it.startAt + it.durationMillis }
                    val nextLineStartAt = lines[currLineIdx + 1].startAt
                    val inactiveDuration = nextLineStartAt - lineStopAt
                    if (inactiveDuration > 0) {
                        currentLineIndex = -1
                        val actualDuration = inactiveDuration + deviationMillis
                        if (actualDuration > 0L) {
                            deviationMillis = startTicking(actualDuration)
                        } else {
                            deviationMillis -= inactiveDuration
                        }
                    }
                }

                if (checkFinished()) {
                    return@launch
                }

                currLineIdx++
            }
        }
        playbackJob!!.invokeOnCompletion { cause ->
            if (cause == null) {
                mediaPlayerViewModel.isPlaying = false
            }
        }
    }





    fun pause() {
        mediaPlayerViewModel.isPlaying = false
        playbackJob?.cancel()
    }

    fun seekToLine(index: Int) {
        val lines = lyrics?.lines ?: return
        val idx = index.coerceIn(-1, lineCount - 1)
        val position = if (idx >= 0) lines[idx].startAt else 0L
        seekTo(position)
        mediaPlayerViewModel.seekTo(position.toInt())
    }

    fun seekTo(position: Long) {
        val playAfterSeeking = mediaPlayerViewModel.isPlaying
        if (mediaPlayerViewModel.isPlaying) {
            playbackJob?.cancel()
        }
        this.mediaPlayerViewModel.currentPosition = position.toInt()
        if (playAfterSeeking) {
            play()
        } else {
            currentLineIndex = findLineIndexAt(position)
        }
    }

    private fun findLineIndexAt(position: Long): Int {
        if (position < 0 || lyrics == null) return -1
        val lines = lyrics.lines
        for (i in lines.lastIndex downTo 0) {
            if (position >= lines[i].startAt) {
                return i
            }
        }
        return -1
    }
}