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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.scrumblegame.viewmodel.GameViewModel



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
    // Use the ViewModel
    val gameViewModel: GameViewModel = viewModel()

    // Initialize a new word when the composable is first launched
    LaunchedEffect(Unit) {
        gameViewModel.newWord()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Score: ${gameViewModel.score.intValue}",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.scrumble),
            contentDescription = "Scrumble Game Logo",
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(2.dp, Color.Gray, RoundedCornerShape(12.dp))
                .shadow(4.dp)
        )

        if (gameViewModel.message.value.isNotEmpty()) {
            Text(
                text = gameViewModel.message.value,
                color = if (gameViewModel.message.value.contains("Correct")) Color.Green else Color.Red,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Text(
            text = "Scrambled Word: ${gameViewModel.scrambledWord.value}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = { gameViewModel.unscramble() },
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(text = "Unscramble")
        }

        BasicTextField(
            value = gameViewModel.userInput.value,
            onValueChange = { gameViewModel.userInput.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .border(BorderStroke(1.dp, Color.Black))
                .padding(8.dp)
        )

        Button(
            onClick = { gameViewModel.checkGuess() },
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(text = "Guess")
        }

        Button(
            onClick = { gameViewModel.newWord() },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(text = "Skip")
        }
    }
}