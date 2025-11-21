package com.example.quizz;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class result extends AppCompatActivity {

    TextView totalText, correctText, wrongText, skippedText, scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        totalText = findViewById(R.id.totalQuesText);
        correctText = findViewById(R.id.correctText);
        wrongText = findViewById(R.id.wrongText);
        skippedText = findViewById(R.id.skippedText);
        scoreText = findViewById(R.id.scoreText);

        // Get actual values from Intent
        int total = getIntent().getIntExtra("totalQuestions", 0);
        int correct = getIntent().getIntExtra("correctAnswers", 0);
        int skipped = getIntent().getIntExtra("skippedAnswers", 0);

        // Calculate wrong answers dynamically
        int wrong = total - correct - skipped;

        // Set values dynamically
        totalText.setText("Total Questions: " + total);
        correctText.setText("Correct Answers: " + correct);
        wrongText.setText("Wrong Answers: " + wrong);
        skippedText.setText("Skipped Answers: " + skipped);

        int score = total > 0 ? (int) ((correct * 100.0) / total) : 0;
        scoreText.setText("Final Score: " + score + "%");
    }
}
