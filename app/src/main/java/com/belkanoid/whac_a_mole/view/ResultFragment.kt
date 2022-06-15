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
import com.belkanoid.whac_a_mole.common.Constants
import com.belkanoid.whac_a_mole.common.SharedPreferences
import kotlin.properties.Delegates


class ResultFragment(private val app : Application) : Fragment() {

    private var score : Int = 0
    private lateinit var playButton: Button
    private lateinit var menuButton: Button
    private lateinit var gameScore: TextView
    private lateinit var gameMaxScore: TextView

    private var callbacks : Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            score = it.getInt(Constants.score)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)
        gameScore = view.findViewById(R.id.game_score)
        playButton = view.findViewById(R.id.play_button)
        menuButton = view.findViewById(R.id.home_button)
        gameMaxScore = view.findViewById(R.id.max_score)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playButton.setOnClickListener {
            callbacks?.onPlayButtonClicked()
        }
        menuButton.setOnClickListener{
            callbacks?.onMainMenuEntered()
        }
        gameScore.text = getString(R.string.score, score.toString())
        val maxScore = SharedPreferences.getStoredQuery(app)
        if (maxScore < score) SharedPreferences.setStoredQuery(app, score)
        gameMaxScore.text = getString(R.string.max_score, SharedPreferences.getStoredQuery(app).toString())
    }

    companion object {

        fun newInstance(score : Int, app: Application) =
            ResultFragment(app).apply {
                arguments = Bundle().apply {
                    putInt(Constants.score, score)
                }
            }
    }
}