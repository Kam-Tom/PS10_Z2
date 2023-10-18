package com.example.ps10_z1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PromptActivity extends AppCompatActivity {

    public static final String KEY_EXTRA_ANSWER_SHOWN = "com.example.ps10_z1.answerShown";
    private boolean correctAnswer;
    private Button showCorrectAnswerButton;
    private Button closeActivityButton;
    private TextView answerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);

        correctAnswer = getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER,true);

        answerTextView = findViewById(R.id.answer_text_view);
        showCorrectAnswerButton = findViewById(R.id.showCorrectAnswer_button);
        closeActivityButton = findViewById(R.id.closeActivity_button);

        showCorrectAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int answer = correctAnswer ? R.string.button_true : R.string.button_false;
                answerTextView.setText(answer);
                setAnswerShowResult(true);
            }
        });
        closeActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private static final String QUIZ_TAG = "MainActivity";
    private void setAnswerShowResult(boolean answerWasShown){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOWN,answerWasShown);
        setResult(RESULT_OK,resultIntent);
    }
}