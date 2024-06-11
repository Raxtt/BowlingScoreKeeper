package edu.lewisu.hartke.FinalProject

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import edu.lewisu.hartke.FinalProject.Model.Game
import edu.lewisu.hartke.FinalProject.repo.GameRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class GameFragment : Fragment() {
    private var gameId = 0
    private lateinit var selectedDate: Date


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { gameId = it.getInt("game_id") }
    }

    private fun showDatePickerDialog(dateTextView: TextView) {
        val calendar = Calendar.getInstance()
        calendar.time = selectedDate

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                selectedDate = calendar.time
                dateTextView.text = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(selectedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_game, container, false)

        val dateTextView: TextView = rootView.findViewById(R.id.textViewDate)
        val scoreTextView: TextView = rootView.findViewById(R.id.editTextScore)
        val strikeTextView: TextView = rootView.findViewById(R.id.editTextStrikes)
        val spareTextView: TextView = rootView.findViewById(R.id.editTextSpares)
        val openFrameTextView: TextView = rootView.findViewById(R.id.editTextOpenFrames)
        val notesTextView: TextView = rootView.findViewById(R.id.editTextNotes)
        val addButton: Button = rootView.findViewById(R.id.buttonSave)
        val deleteButton: Button = rootView.findViewById(R.id.buttonDelete)

        if (gameId == 0) {
            deleteButton.visibility = View.GONE

            val calendar = Calendar.getInstance()
            selectedDate = calendar.time

            dateTextView.setOnClickListener {
                showDatePickerDialog(dateTextView)

                addButton.setOnClickListener {
                    val newGame = Game(
                        date = selectedDate,
                        score = scoreTextView.text.toString(),
                        strikes = strikeTextView.text.toString(),
                        spares = spareTextView.text.toString(),
                        openFrames = openFrameTextView.text.toString(),
                        notes = notesTextView.text.toString()

                    )


                    GameRepository.getInstance(requireContext()).insertGame(newGame)
                    Navigation.findNavController(it).navigate(R.id.show_list)
                }
            }
        }   else {
                val game = GameRepository.getInstance(requireContext()).getGame(gameId)

                dateTextView.isClickable = false
                dateTextView.isFocusable = false
                scoreTextView.isClickable = false
                scoreTextView.isFocusable = false
                strikeTextView.isClickable = false
                strikeTextView.isFocusable = false
                spareTextView.isClickable = false
                spareTextView.isFocusable = false
                openFrameTextView.isClickable = false
                openFrameTextView.isFocusable = false
                notesTextView.isClickable = false
                notesTextView.isFocusable = false

                addButton.visibility = View.GONE
                dateTextView.text = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(game.date)
                scoreTextView.text = game.score
                strikeTextView.text = game.strikes
                spareTextView.text = game.spares
                openFrameTextView.text = game.openFrames
                notesTextView.text = game.notes


                deleteButton.setOnClickListener {
                    GameRepository.getInstance(requireContext()).deleteGame(game)
                    Navigation.findNavController(it).navigate(R.id.show_list)
                }

            }

            return rootView
        }
    }

