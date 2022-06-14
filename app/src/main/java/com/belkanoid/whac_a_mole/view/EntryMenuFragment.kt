package com.belkanoid.whac_a_mole.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.belkanoid.whac_a_mole.R


class EntryMenuFragment : Fragment() {

    interface Callbacks {
        fun onPlayButtonClicked()
    }

    private lateinit var playButton : Button
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
        val view = inflater.inflate(R.layout.fragment_entry_menu, container, false)
        playButton = view.findViewById(R.id.play_button)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playButton.setOnClickListener{
            callbacks?.onPlayButtonClicked()
        }
    }

    companion object {
        fun newInstance() = EntryMenuFragment()

    }
}