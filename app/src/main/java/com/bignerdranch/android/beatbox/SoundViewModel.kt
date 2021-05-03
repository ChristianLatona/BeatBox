package com.bignerdranch.android.beatbox

import androidx.databinding.*

private const val TAG = "SoundViewModel"
// private const val BINDING_PROGRESS_CHANGED = "android:onProgressChanged"

class SoundViewModel(private val beatBox: BeatBox): BaseObservable() {

    var sound: Sound? = null
        set(sound) {
            field = sound
            notifyChange()
        }

    @Bindable
    var rate: Int = 50
        set(value) {
            field = value
            BeatBox.soundRate = value/100f+0.5f
            notifyPropertyChanged(BR.rate)
        }

    @get:Bindable
    val title: String?
        get() = sound?.name

    fun onButtonClicked() {
        sound?.let {
            beatBox.play(it)
        }
    }

}
