package edu.lewisu.hartke.FinalProject

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.lewisu.hartke.FinalProject.repo.GameRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ListFragment : Fragment() {
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var averageScoreTextView: TextView


    @SuppressLint("SetTextI18n")
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_list, container, false)

        val onClickListener = View.OnClickListener { itemView: View ->
            val selectedGame = itemView.tag as Int
            val args = Bundle()
            args.putInt("game_id", selectedGame)
            Navigation.findNavController(itemView).navigate(R.id.show_item_game, args)
        }

        val games = GameRepository.getInstance(requireContext()).getAllGames()

        val recyclerView : RecyclerView = rootView.findViewById(R.id.recyclerview)
        val adapter = GameAdapter(games, onClickListener)
        recyclerView.adapter = adapter

        floatingActionButton = rootView.findViewById(R.id.floating_add_button)
        floatingActionButton.setOnClickListener{view : View ->
            val args = Bundle()
            args.putInt("game_id", 0)
            Navigation.findNavController(view).navigate(R.id.show_item_game, args)
            Log.d("GameDao", "Number of games fetched from database: ${games.size}")
        }

        averageScoreTextView = rootView.findViewById(R.id.average_text_view)

        GlobalScope.launch {
            val averageScore = calculateAverageScore()
            withContext(Dispatchers.Main) {
                averageScoreTextView.text = "Average Score: $averageScore"
            }
        }

        return rootView
    }

    private suspend fun calculateAverageScore(): Double {
        val games = GameRepository.getInstance(requireContext()).getAllGames()
        var totalScore = 0.0
        var numGames = 0

        for (game in games) {
            totalScore += game.score.toDouble()
            numGames++
        }
        return if (numGames > 0) totalScore / numGames else 0.0
    }


}