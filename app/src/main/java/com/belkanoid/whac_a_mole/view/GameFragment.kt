package com.belkanoid.whac_a_mole.view

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.os.CountDownTimer

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.belkanoid.whac_a_mole.R
import com.belkanoid.whac_a_mole.common.Constants
import com.belkanoid.whac_a_mole.viewModel.GameViewModel


class GameFragment : Fragment() {

    interface Callbacks {
        fun onGameFinished()
    }

    private val gameViewModel: GameViewModel by lazy {
        ViewModelProvider(this).get(GameViewModel::class.java)
    }

    private var callbacks : Callbacks? = null
    private var timer : CountDownTimer? = null
    private lateinit var gameTime : TextView
    private lateinit var gameScore : TextView
    private lateinit var recyclerView: RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        findView(view)
        recyclerView = view.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameViewModel.score.observe(viewLifecycleOwner, Observer {
            gameScore.text = gameViewModel.getScore().toString()
        })
    }


    override fun onStart() {
        super.onStart()
        startCountDownTimer()
    }

    private fun findView(view : View) {
        gameTime = view.findViewById(R.id.game_time)
        gameScore = view.findViewById(R.id.game_score)
    }

    private inner class HoleHolder(view : View)
        :RecyclerView.ViewHolder(view), View.OnClickListener {

        private var vis = false

        private val holeImage: ImageView = itemView.findViewById(R.id.hole_mole)

        fun bind(_vis: Boolean) {
            vis = _vis
            holeImage.setBackgroundResource(when {
                vis -> R.drawable.mole
                else -> R.drawable.hole
            })
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (vis) {
                Toast.makeText(context, "Hit!", Toast.LENGTH_SHORT).show()
                gameViewModel.inScore()
            }
        }

    }


    private inner class HoleAdapter(private val holes : List<Boolean>)
        :RecyclerView.Adapter<HoleHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoleHolder {
            val view = layoutInflater.inflate(R.layout.list_item_hole, parent, false)

            return HoleHolder(view)
        }

        override fun onBindViewHolder(holder: HoleHolder, position: Int) {
            holder.bind(holes[position])
        }

        override fun getItemCount(): Int = holes.size

    }




    private fun startCountDownTimer() {
        timer = object : CountDownTimer(Constants.time, 500)  {
            override fun onTick(currentTime: Long) {
                updateUI(currentTime)
            }
            override fun onFinish() {
                callbacks?.onGameFinished()
            }

        }.start()
    }

    private fun updateUI(currentTime : Long) {
        gameTime.text = currentTime.toString()
        recyclerView.adapter = HoleAdapter(newHoles())
        recyclerView.invalidate()
    }

    private fun newHoles(): List<Boolean> {
        val newHoles = MutableList(9) {false}
        val mole = (Math.random() * 9).toInt()
        newHoles[mole] = true
        return newHoles
    }


    companion object {

        fun newInstance() = GameFragment()
    }
}