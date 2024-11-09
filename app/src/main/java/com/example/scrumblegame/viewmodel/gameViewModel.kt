package com.example.scrumblegame.viewmodel


import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class GameViewModel : ViewModel() {

    var score = mutableIntStateOf(0)
    var currentWord = mutableStateOf("")
    var userInput = mutableStateOf("")
    var scrambledWord = mutableStateOf("")
    var message = mutableStateOf("")

    private val words = listOf("apple", "banana", "cherry", "ahmed", "tlili")

    // Scramble a word
    private fun scrambleWord(word: String): String {
        return word.toCharArray().apply { shuffle(Random) }.concatToString()
    }

    // Generate a new word
    fun newWord() {
        val word = words.random()
        currentWord.value = word
        scrambledWord.value = scrambleWord(word)
        userInput.value = ""
        message.value = ""
    }

    // Check the guess
    fun checkGuess() {
        if (userInput.value.equals(currentWord.value, ignoreCase = true)) {
            score.value += 1
            message.value = "Correct! Nice job!"
            newWord()
        } else {
            message.value = "Try again!"
            userInput.value = ""
        }
    }

    // Unscramble the word
    fun unscramble() {
        message.value = "The word is: ${currentWord.value}"
        userInput.value = ""
    }
}
