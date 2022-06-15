package com.belkanoid.whac_a_mole.common

import android.app.Application

interface Callbacks {

    fun onGameFinished(score : Int)

    fun onPlayButtonClicked()

    fun onMainMenuEntered()
}