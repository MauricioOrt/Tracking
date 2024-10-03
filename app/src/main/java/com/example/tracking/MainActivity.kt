package com.example.tracking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tracking.ui.theme.TrackingTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrackingTheme {
                HabitTrackerApp()
            }
        }
    }
}

@Composable
fun HabitTrackerApp() {

    var habitList by remember { mutableStateOf(listOf<Habit>()) }
    var newHabit by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            OutlinedTextField(
                value = newHabit,
                onValueChange = { newHabit = it },
                label = { Text("Nuevo Hábito") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))


            Button(
                onClick = {
                    if (newHabit.isNotEmpty()) {
                        habitList = habitList + Habit(newHabit, false)
                        newHabit = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar Hábito")
            }

            Spacer(modifier = Modifier.height(16.dp))


            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(habitList.size) { index ->
                    HabitItem(habit = habitList[index]) { completed ->

                        habitList = habitList.toMutableList().also {
                            it[index] = it[index].copy(isCompleted = completed)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HabitItem(habit: Habit, onCompletedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(habit.name)

        Checkbox(
            checked = habit.isCompleted,
            onCheckedChange = onCompletedChange
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HabitTrackerPreview() {
    TrackingTheme {
        HabitTrackerApp()
    }
}


