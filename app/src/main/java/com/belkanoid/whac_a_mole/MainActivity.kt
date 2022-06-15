package com.belkanoid.whac_a_mole

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.belkanoid.whac_a_mole.common.Callbacks
import com.belkanoid.whac_a_mole.view.MainMenuFragment
import com.belkanoid.whac_a_mole.view.GameFragment
import com.belkanoid.whac_a_mole.view.ResultFragment

class MainActivity
    : AppCompatActivity(), Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onMainMenuEntered()

    }

    override fun onPlayButtonClicked() {
        val gameFragment = GameFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, gameFragment)
            .commit()
    }

    override fun onMainMenuEntered() {
        val entryMenu = MainMenuFragment.newInstance(application)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, entryMenu)
            .commit()
    }

    override fun onGameFinished(score : Int) {
        val resultFragment = ResultFragment.newInstance(score, application)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, resultFragment)
            .commit()
    }
}