package com.belkanoid.whac_a_mole.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val _score = MutableLiveData(0)
    val score : LiveData<Int> = _score

    fun inScore() {
        _score.value = _score.value?.plus(1)
    }

}