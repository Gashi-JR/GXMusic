package com.example.viewmodellist.ui.components.find

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.dokar.amlv.LyricsViewState
import com.example.viewmodellist.R
import java.util.concurrent.TimeUnit


class MediaPlayerViewModel : ViewModel() {
    private var mediaPlayer: MediaPlayer? = null
    private var currentUrl: String? = null
    var isPlaying by mutableStateOf(false)
    var currentPosition by mutableStateOf(0)
    var duration by mutableStateOf(0)
    var currentPositionText by mutableStateOf("00:00")
    var durationText by mutableStateOf("00:00")
    var isLooping by mutableStateOf(false)
    private var isFirstPlay = true

    fun play(url: String) {
        if (url != "") {
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

                    // 在播放前检查是否已准备好并处于播放状态
                    if (mp.isPlaying) {
                        // 获取总时长
                        this@MediaPlayerViewModel.duration = mp.duration
                        durationText =
                            formatTime(this@MediaPlayerViewModel.duration) // 格式化总时间

                        if (isFirstPlay && mediaPlayer?.isPlaying == true) {
                            val handler = Handler(Looper.getMainLooper())
                            handler.postDelayed(object : Runnable {
                                override fun run() {
                                    // 在播放前检查是否已准备好并处于播放状态
                                    if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
                                        // 获取当前播放位置
                                        this@MediaPlayerViewModel.currentPosition =
                                            mediaPlayer!!.currentPosition
                                        currentPositionText =
                                            formatTime(this@MediaPlayerViewModel.currentPosition) // 格式化当前进度的时间
                                    }
                                    handler.postDelayed(this, 1000) // 每秒更新一次进度
                                }
                            }, 0)
                            isFirstPlay = false
                        }
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
fun MusicPlayer(
    url: String,
    name: String,
    artists: String,
    musicController: MediaPlayerViewModel,
    state: LyricsViewState,
) {


    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Slider(
                value = musicController.currentPosition.toFloat(),
                onValueChange = { newPosition ->
                    run {
                        state.seekTo(newPosition.toLong())
                        musicController.seekTo(newPosition.toInt())
                    }
                },
                valueRange = 0f..musicController.duration.toFloat(),
                modifier = Modifier
                    .width(200.dp)
                    .height(10.dp)
            )

            Text(
                text = "$name - $artists",
                color = Color.Black,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontSize = 13.sp,
                modifier = Modifier.width(200.dp)
            )
        }

        Text(text = musicController.currentPositionText, color = Color.Black)
        Text(text = "/", color = Color.Black)
        Text(text = musicController.durationText, color = Color.Black)

        if (musicController.isPlaying)
            Icon(
                painter = painterResource(R.drawable.baseline_pause_24),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        if (musicController.isPlaying) {
                            musicController.pause()
                        } else {
                            musicController.play(url)
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
                        if (musicController.isPlaying) {
                            musicController.pause()
                        } else {
                            musicController.play(url)
                        }
                    },
                tint = Color.Black
            )


    }


}


@Preview
@Composable
fun PreviewApp() {

    //MusicPlayer("https://example.com/song.mp3", "sfafaf", "asfasfe", MediaPlayerViewModel(),)

}
