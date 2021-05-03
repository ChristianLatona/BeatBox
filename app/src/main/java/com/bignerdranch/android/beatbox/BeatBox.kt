package com.bignerdranch.android.beatbox

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import java.io.IOException
import java.lang.Exception

private const val TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"


class BeatBox(private val assets: AssetManager, private val soundPool: SoundPool){

    val sounds: List<Sound>

    init {
        sounds = loadSounds()
    }

    private fun loadSounds(): List<Sound> {
        val soundNames: Array<String>
        try {
            soundNames = assets.list(SOUNDS_FOLDER)!!
        } catch (e: Exception){
            Log.e(TAG, "could not list assets", e)
            return emptyList()
        }
        val sounds = mutableListOf<Sound>()
        soundNames.forEach { filename ->
            val assetPath = "$SOUNDS_FOLDER/$filename"
            val sound = Sound(assetPath)
            try {
                sounds.add(sound)
                load(sound)
            }catch (ioe: IOException){
                Log.e(TAG, "Could not load sound $filename", ioe)
            }

        }
        return sounds
    }

    private fun load(sound: Sound){
        val afd: AssetFileDescriptor = assets.openFd(sound.assetPath) // we re working with afd instead of InputStream...
        // ... because SoundPool.load() function works with afd.
        val soundId = soundPool.load(afd, 1) // this fun returns a soundId
        sound.soundId = soundId
    }

    fun play(sound: Sound) {
        sound.soundId?.let {
            soundPool.play(it, 1f, 1f, 1, 0, soundRate)
            // Log.d(TAG, "played: $it")
        }
    }

    companion object{
        var soundRate = 1f
    }

}
