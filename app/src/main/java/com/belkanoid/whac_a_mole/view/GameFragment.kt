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
import com.belkanoid.whac_a_mole.common.Callbacks
import com.belkanoid.whac_a_mole.common.Constants
import com.belkanoid.whac_a_mole.viewModel.GameViewModel


class GameFragment : Fragment() {


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
        gameTime = view.findViewById(R.id.game_time)
        gameScore = view.findViewById(R.id.game_score)
        recyclerView = view.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 3)

        startCountDownTimer()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameViewModel.score.observe(viewLifecycleOwner, Observer { score ->
            gameScore.text = score.toString()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

    private inner class HoleHolder(view : View)
        :RecyclerView.ViewHolder(view) {

        private var visible = false

        private val holeImage: ImageView = itemView.findViewById(R.id.hole_mole)

        fun bind(visible: Boolean) {
            if (visible) {
                this.visible = visible
                holeImage.setBackgroundResource(R.drawable.mole)
            }
        }

        init {
            itemView.setOnClickListener {
                if (visible) {
                    visible = false
                    gameViewModel.inScore()
                }
            }
        }

    }


    private inner class HoleAdapter(private val holes : List<Boolean>)
        :RecyclerView.Adapter<HoleHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoleHolder {
            return HoleHolder(layoutInflater.inflate(R.layout.list_item_hole, parent, false))
        }

        override fun onBindViewHolder(holder: HoleHolder, position: Int) {
            holder.bind(holes[position])
        }

        override fun getItemCount(): Int = holes.size

    }




    private fun startCountDownTimer() {
        timer = object : CountDownTimer(Constants.time, 800)  {
            override fun onTick(currentTime: Long) {
                updateUI(currentTime)
            }
            override fun onFinish() {
                gameViewModel.score.value?.let { callbacks?.onGameFinished(it) }
            }

        }.start()
    }

    private fun updateUI(currentTime : Long) {
        gameTime.text = ((currentTime/1000).toString())
        recyclerView.adapter = HoleAdapter(newHoles())
    }

    private fun newHoles(): List<Boolean> {
        val mole = (Math.random() * 9).toInt()
        return List(9) {it == mole}
    }


    companion object {

        fun newInstance() = GameFragment()
    }
}