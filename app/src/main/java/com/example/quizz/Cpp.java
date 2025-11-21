package com.example.quizz;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.quizz.model.pythonques;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cpp extends AppCompatActivity {

    TextView q1, qnoText;
    RadioGroup radioGroup;
    RadioButton r1, r2, r3, r4;
    Button submitBtn, skipBtn;

    List<pythonques> questionList = new ArrayList<>();
    int currentIndex = 0;
    int correctAnswersCount = 0;
    int skippedCount = 0;

    MediaPlayer correctSound, wrongSound, skipSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_python);

        // Initialize views
        qnoText = findViewById(R.id.qnoText);
        q1 = findViewById(R.id.q1);
        radioGroup = findViewById(R.id.radioGroup);
        r1 = findViewById(R.id.r1);
        r2 = findViewById(R.id.r2);
        r3 = findViewById(R.id.r3);
        r4 = findViewById(R.id.r4);
        submitBtn = findViewById(R.id.b);
        skipBtn = findViewById(R.id.skipBtn);

        // Optional sounds
//        correctSound = MediaPlayer.create(this, R.raw.correct);
//        wrongSound = MediaPlayer.create(this, R.raw.wrong);
//        skipSound = MediaPlayer.create(this, R.raw.skip);

        // Load questions
        loadQuestionsFromJsonFile();

        // Submit button
        submitBtn.setOnClickListener(v -> handleSubmit());

        // Skip button
        skipBtn.setOnClickListener(v -> handleSkip());
    }

    /** Load JSON questions from assets */
    private void loadQuestionsFromJsonFile() {
        try {
            InputStream is = getAssets().open("Cppmini.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<pythonques>>() {}.getType();
            questionList = gson.fromJson(json, listType);

            if (questionList != null && !questionList.isEmpty()) {
                Collections.shuffle(questionList); // Shuffle questions
                showQuestion(currentIndex);
            } else {
                showToastFor1Second("No questions found!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showToastFor1Second("Error loading questions!");
        }
    }

    /** Display current question and shuffled options */
    private void showQuestion(int index) {
        pythonques q = questionList.get(index);

        List<String> shuffledOptions = new ArrayList<>(q.getOptions());
        Collections.shuffle(shuffledOptions);

        qnoText.setText("Q" + (index + 1) + " of " + questionList.size());
        q1.setText(q.getQuestion());
        r1.setText(shuffledOptions.get(0));
        r2.setText(shuffledOptions.get(1));
        r3.setText(shuffledOptions.get(2));
        r4.setText(shuffledOptions.get(3));

        radioGroup.clearCheck();
    }

    /** Handle Submit button click */
    private void handleSubmit() {
        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == -1) {
            showToastFor1Second("Please select an answer!");
            return;
        }

        RadioButton selectedRadio = findViewById(selectedId);
        String selectedAnswer = selectedRadio.getText().toString();
        String correctAnswer = questionList.get(currentIndex).getAnswer();

        Log.d("QuizDebug", "Selected: '" + selectedAnswer + "' | Correct: '" + correctAnswer + "'");

        if (selectedAnswer.trim().equalsIgnoreCase(correctAnswer.trim())) {
            correctAnswersCount++;
            showToastFor1Second("✅ Correct!");
            if (correctSound != null) correctSound.start();
        } else {
            showToastFor1Second("❌ Wrong!");
            if (wrongSound != null) wrongSound.start();
        }

        nextQuestionOrResult();
    }

    /** Handle Skip button click */
    private void handleSkip() {
        skippedCount++;
        showToastFor1Second("⏭ Question Skipped!");
        if (skipSound != null) skipSound.start();
        nextQuestionOrResult();
    }

    /** Move to next question or show result */
    private void nextQuestionOrResult() {
        currentIndex++;
        if (currentIndex < questionList.size()) {
            showQuestion(currentIndex);
        } else {
            Intent intent = new Intent(Cpp.this, result.class);
            intent.putExtra("totalQuestions", questionList.size());
            intent.putExtra("correctAnswers", correctAnswersCount);
            intent.putExtra("skippedAnswers", skippedCount);
            startActivity(intent);
            finish();
        }
    }

    /** Short toast message */
    private void showToastFor1Second(String message) {
        final Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
        new Handler().postDelayed(toast::cancel, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (correctSound != null) correctSound.release();
        if (wrongSound != null) wrongSound.release();
        if (skipSound != null) skipSound.release();
    }
}
