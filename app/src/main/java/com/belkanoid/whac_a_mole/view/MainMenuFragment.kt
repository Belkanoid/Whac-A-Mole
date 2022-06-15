package com.belkanoid.whac_a_mole.view

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.belkanoid.whac_a_mole.R
import com.belkanoid.whac_a_mole.common.Callbacks
import com.belkanoid.whac_a_mole.common.SharedPreferences


class MainMenuFragment(private val app : Application) : Fragment() {


    private lateinit var playButton : Button
    private lateinit var gameMaxScore: TextView
    private var callbacks : Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_menu, container, false)
        playButton = view.findViewById(R.id.play_button)
        gameMaxScore = view.findViewById(R.id.score)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playButton.setOnClickListener{
            callbacks?.onPlayButtonClicked()
        }

        gameMaxScore.text = getString(R.string.max_score, SharedPreferences.getStoredQuery(app).toString())
    }

    companion object {
        fun newInstance(app: Application) = MainMenuFragment(app)

    }
}