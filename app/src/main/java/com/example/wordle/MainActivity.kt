package com.example.wordle

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.text.style.ForegroundColorSpan
import android.util.Log


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var guess = 0
        val guessInput = findViewById<EditText>(R.id.guessInput)
        val guessButton = findViewById<Button>(R.id.guessButton)
        var resetButton = findViewById<Button>(R.id.resetButton)

        val answerOne = findViewById<TextView>(R.id.answer1)
        val answerTwo = findViewById<TextView>(R.id.answer2)
        val answerThree = findViewById<TextView>(R.id.answer3)

        val answerOneCheck = findViewById<TextView>(R.id.answer1Check)
        val answerTwoCheck = findViewById<TextView>(R.id.answer2Check)
        val answerThreeCheck = findViewById<TextView>(R.id.answer3Check)

        val wordle = findViewById<TextView>(R.id.wordle)

        val wordToGuess = FourLetterWordList.getRandomFourLetterWord()
        Log.v("Wordle",wordToGuess)
        wordle.text = wordToGuess


        guessButton.setOnClickListener {
            var text = guessInput.text.toString()
            if (text.length !=4){
                this.currentFocus?.let { it1 -> hideKeyboard(it1) }
                Toast
                    .makeText(this, "Please enter a 4-letter word!", Toast.LENGTH_LONG)
                    .show()
                guessInput.text.clear()
                return@setOnClickListener
            }
            when (guess) {
                0 -> {
                    answerOne.text = text
                    answerOneCheck.text = checkGuess(text.uppercase(),wordToGuess)
                    guess++
                }
                1 -> {
                    answerTwo.text = text
                    answerTwoCheck.text = checkGuess(text.uppercase(),wordToGuess)
                    guess++
                }
                2 -> {
                    answerThree.text = text
                    answerThreeCheck.text = checkGuess(text.uppercase(),wordToGuess)
                    guess++
                }
            }
            guessInput.text.clear()
            this.currentFocus?.let { it1 -> hideKeyboard(it1) }

            if (text.uppercase() == wordToGuess|| guess >= 3) {
                wordle.visibility = View.VISIBLE
                guessInput.isEnabled = false
                guessInput.isClickable = false
                guessButton.visibility = View.INVISIBLE
                resetButton.visibility = View.VISIBLE

                if (text.uppercase() == wordToGuess) {
                    Toast.makeText(
                        this,
                        "Nice Job! You Won, Press reset to play again.",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "Better Luck Next time, Press reset to play again.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    }


    private fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    private fun checkGuess(guess: String, wordToGuess: String) : Spannable {
        val result = SpannableString(guess)
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result.setSpan(ForegroundColorSpan(Color.GREEN),i,i+1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            } else if (guess[i] in wordToGuess) {
                result.setSpan(ForegroundColorSpan(Color.GRAY),i,i+1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            } else {
                result.setSpan(ForegroundColorSpan(Color.RED),i,i+1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        return result
    }
}

