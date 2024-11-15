package com.amaurypm.audiocontroller

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.MediaController
import android.widget.MediaController.MediaPlayerControl
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.amaurypm.audiocontroller.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Colocamos propiedades para el MediaPlayer y el MediaController
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var mediaController: MediaController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Instanciamos el mediaplayer con un archivo cargado
        mediaPlayer = MediaPlayer.create(this, R.raw.zelda)

        //Instanciamos el mediacontroller
        mediaController = MediaController(this)

        mediaController.setAnchorView(binding.audioPlayer)

        mediaController.setMediaPlayer(object: MediaPlayerControl{
            override fun start() {
                mediaPlayer.start()
            }

            override fun pause() {
                mediaPlayer.pause()
            }

            override fun getDuration(): Int = mediaPlayer.duration

            override fun getCurrentPosition(): Int = mediaPlayer.currentPosition

            override fun seekTo(p0: Int) {
                mediaPlayer.seekTo(p0)
            }

            override fun isPlaying(): Boolean = mediaPlayer.isPlaying

            override fun getBufferPercentage(): Int = 0

            override fun canPause(): Boolean = true

            override fun canSeekBackward(): Boolean = true

            override fun canSeekForward(): Boolean = true

            override fun getAudioSessionId(): Int = mediaPlayer.audioSessionId

        })


        mediaPlayer.setOnPreparedListener {
            mediaController.show(3000)
            mediaPlayer.start()
        }


        binding.audioPlayer.setOnClickListener {
            if(!mediaController.isShowing)
                mediaController.show(3000)
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.pause()
    }

    override fun onRestart() {
        super.onRestart()
        mediaPlayer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}