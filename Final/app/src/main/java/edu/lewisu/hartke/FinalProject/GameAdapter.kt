package edu.lewisu.hartke.FinalProject

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.lewisu.hartke.FinalProject.Model.Game
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class GameAdapter (private val gameList : List<Game>, private val onClickListener: View.OnClickListener)
    : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return GameViewHolder(layoutInflater, parent)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = gameList[position]
        holder.itemView.tag = game.id
        holder.bind(game)
        holder.itemView.setOnClickListener(onClickListener)

    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    inner class GameViewHolder(inflater: LayoutInflater, parent: ViewGroup?):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_game, parent, false)){
        private val gameDateView: TextView = itemView.findViewById(R.id.game_date)
        private val gameScoreView : TextView = itemView.findViewById(R.id.game_score)

        @SuppressLint("SetTextI18n")
        fun bind(game: Game) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            gameDateView.text = dateFormat.format(game.date)


            if(gameDateView.isClickable) {
                gameDateView.setOnClickListener {
                    val calendar = Calendar.getInstance()
                    calendar.time = game.date
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val day = calendar.get(Calendar.DAY_OF_MONTH)

                    val datePickerDialog = DatePickerDialog(
                        itemView.context,
                        { _, selectedYear, selectedMonth, selectedDay ->
                            val selectedCalendar = Calendar.getInstance()
                            selectedCalendar.set(selectedYear, selectedMonth, selectedDay)
                            game.date = selectedCalendar.time
                            gameDateView.text = dateFormat.format(game.date)
                        },
                        year,
                        month,
                        day
                    )
                    datePickerDialog.show()
                }
            }

            gameScoreView.text = "Score: ${game.score}"
        }
    }
}