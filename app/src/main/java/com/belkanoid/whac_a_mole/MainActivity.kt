package com.belkanoid.whac_a_mole

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.belkanoid.whac_a_mole.view.EntryMenuFragment
import com.belkanoid.whac_a_mole.view.GameFragment
import com.belkanoid.whac_a_mole.view.ResultFragment

class MainActivity
    : AppCompatActivity(), EntryMenuFragment.Callbacks, GameFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val entryMenu = EntryMenuFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, entryMenu)
                .commit()
        }
    }

    override fun onPlayButtonClicked() {
        val gameFragment = GameFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, gameFragment)
            .commit()
    }

    override fun onGameFinished() {
        //TODO
        val resultFragment = ResultFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, resultFragment)
            .commit()
    }
}