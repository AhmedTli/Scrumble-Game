package com.example.scrumblegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlin.random.Random



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScrumbleGame()
        }
    }
}
@Composable
fun ScrumbleGame() {
    var score by remember { mutableIntStateOf(0) }
    var currentWord by remember { mutableStateOf("") }
    var userInput by remember { mutableStateOf("") }
    var scrambledWord by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    // Define your word list
    val words = listOf("apple", "banana", "cherry","ahmed","tlili")

    // Function to scramble the word
    fun scrambleWord(word: String): String {
        return word.toCharArray().apply { shuffle(Random) }.concatToString()
    }

    // Function to pick a new word
    fun newWord() {
        val word = words.random()
        currentWord = word
        scrambledWord = scrambleWord(word)
        userInput = ""
        message = ""
    }

    // Call newWord when the composable is first loaded
    LaunchedEffect(Unit) {
        newWord()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Score: $score",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.scrumble), // Corrected image reference
            contentDescription = "Scrumble Game Logo", // Content description for accessibility
            modifier = Modifier
                .size(200.dp) // Adjust the size as needed
                .padding(16.dp) // Add some padding
                .clip(RoundedCornerShape(12.dp)) // Rounded corners for a smoother look
                .border(2.dp, Color.Gray, RoundedCornerShape(12.dp)) // Adding a border with rounded corners
                .shadow(4.dp) // Adding a slight shadow for cool effect
        )
        // Feedback message
        if (message.isNotEmpty()) {
            Text(
                text = message,
                color = if (message.contains("Correct")) Color.Green else Color.Red,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        // Display scrambled word
        Text(
            text = "Scrambled Word: $scrambledWord",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        // Unscramble button
        Button(
            onClick = {
                message = "The word is: $currentWord" // Display the correct word
                userInput = "" // Clear input
            },
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(text = "Unscramble")
        }

        // User input field
        BasicTextField(
            value = userInput,
            onValueChange = { userInput = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .border(BorderStroke(1.dp, Color.Black))
                .padding(8.dp)
        )

        // Guess button
        Button(
            onClick = {
                // Check the user's guess
                if (userInput.equals(currentWord, ignoreCase = true)) {
                    score += 1 // Increase score for correct guess
                    message = "Correct! Nice job!" // Feedback message
                    newWord() // Get a new word after a correct guess
                } else {
                    message = "Try again!" // Feedback message for incorrect guess
                    userInput = "" // Clear input on incorrect guess
                }
            },
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(text = "Guess")
        }



        // Skip button
        Button(
            onClick = {
                newWord() // Get a new word
            },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(text = "Skip")
        }


    }
}