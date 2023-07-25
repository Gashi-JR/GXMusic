package com.example.viewmodellist.ui.components.find

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viewmodellist.R
import java.util.concurrent.TimeUnit


class MediaPlayerViewModel : ViewModel() {
    private var mediaPlayer: MediaPlayer? = null
    private var currentUrl: String? = null
    var isPlaying by mutableStateOf(false)
    var currentPosition by mutableStateOf(0)
    var duration = 0
    var currentPositionText by mutableStateOf("00:00")
    var durationText by mutableStateOf("00:00")
    var isLooping by mutableStateOf(false)
    private var isFirstPlay = true

    fun play(url: String) {
        if (currentUrl == url) {
            resume()
            return
        }

        stop()

        if (currentUrl == url && isLooping) {
            isLooping = false // 取消循环播放
        }

        currentUrl = url
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener { mp ->
                mp.start()
                this@MediaPlayerViewModel.isPlaying = true
                this@MediaPlayerViewModel.duration = mp.duration
                if (isFirstPlay) {
                    val handler = Handler(Looper.getMainLooper())
                    durationText = formatTime(duration) // 格式化总时间
                    handler.postDelayed(object : Runnable {
                        override fun run() {
                            this@MediaPlayerViewModel.currentPosition = mp.currentPosition
                            currentPositionText = formatTime(currentPosition) // 格式化当前进度的时间
                            handler.postDelayed(this, 1000) // 每秒更新一次进度
                        }
                    }, 0)
                    isFirstPlay = false
                }
            }

            setOnCompletionListener { mp ->
                if (isLooping) {
                    mp.start() // 循环播放
                } else {
                    this@MediaPlayerViewModel.isPlaying = false
                }
            }
        }
    }

    private fun formatTime(timeInMillis: Int): String {
        val correctedTime = timeInMillis + if (mediaPlayer?.isPlaying == true) 1000 else 0
        val minutes = TimeUnit.MILLISECONDS.toMinutes(correctedTime.toLong()) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(correctedTime.toLong()) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    fun pause() {
        mediaPlayer?.pause()
        isPlaying = false
    }

    fun resume() {
        mediaPlayer?.start()
        isPlaying = true
    }

    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer = null
        isPlaying = false
    }

    fun seekTo(position: Int) {
        mediaPlayer?.seekTo(position)
    }

    override fun onCleared() {
        super.onCleared()
        stop()
    }
}


@Composable
fun MusicPlayer(url: String) {


    val mediaPlayer = viewModel<MediaPlayerViewModel>()




    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Slider(
            value = mediaPlayer.currentPosition.toFloat(),
            onValueChange = { newPosition ->
                mediaPlayer.seekTo(newPosition.toInt())
            },
            valueRange = 0f..mediaPlayer.duration.toFloat(),
            modifier = Modifier
                .width(200.dp)
                .height(25.dp)
        )
        Text(text = mediaPlayer.currentPositionText, color = Color.Black)
        Text(text = "/", color = Color.Black)
        Text(text = mediaPlayer.durationText, color = Color.Black)

        if (mediaPlayer.isPlaying)
            Icon(
                painter = painterResource(R.drawable.baseline_pause_24),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        if (mediaPlayer.isPlaying) {
                            mediaPlayer.pause()
                        } else {
                            mediaPlayer.play(url)
                        }
                    },
                tint = Color.Black
            )
        else
            Icon(
                painter = painterResource(R.drawable.baseline_play_arrow_15),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        if (mediaPlayer.isPlaying) {
                            mediaPlayer.pause()
                        } else {
                            mediaPlayer.play(url)
                        }
                    },
                tint = Color.Black
            )


    }
}

@Composable
fun MyApp() {
    MaterialTheme {
        MusicPlayer("https://example.com/song.mp3")
    }
}

@Preview
@Composable
fun PreviewApp() {
    MyApp()
}
