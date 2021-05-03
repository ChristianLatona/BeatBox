package com.bignerdranch.android.beatbox

import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import androidx.lifecycle.ViewModel

private const val MAX_SOUNDS = 5
private const val TAG = "BeatBox"

class BeatBoxViewModel(private val assets: AssetManager): ViewModel(){

    // this ViewModel was very hard to figure out, because in my mind we re not referring directly to a Fragment or Activity,
    // when in the reality we are. So, the only thing that needs to persist is the SoundPool, which is responsible for
    // playing the sound, nobody cares if we reload the sounds, as long as the SoundPool player doesn't stop.
    // remove the beatbox.release from MainActivity.onDestroy(), since we don't want that, instead we do it in onCleared()
    // that's all, this way to expose an instance of BeatBox is very curious, i found it very useful

    // but i never faced this usage of ViewModel, this made me very confused, but it's very simple

    // what if I put all into ViewModel? I did it and worked out, for this purpose (I was very reluctant about this)
    // I will just keep the only-soundPool version

    private val soundPool: SoundPool = SoundPool.Builder()
            .setMaxStreams(MAX_SOUNDS)
            .build()

    val beatBox = BeatBox(assets, soundPool)

    override fun onCleared() {
        super.onCleared()
        soundPool.release()
    }
}