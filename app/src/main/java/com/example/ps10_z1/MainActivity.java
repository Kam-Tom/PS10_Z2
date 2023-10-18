package com.example.ps10_z1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ps10_z1.R;

public class MainActivity extends AppCompatActivity {
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button promptButton;
    private TextView questionTextView;
    private int currentIndex = 0;
    private boolean questionAnswered = false;
    private boolean answerWasShown = false;
    private int correctAnswers = 0;
    private static final int REQUEST_CODE_PROMPT = 0;
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    private static final String KEY_CORRECT_ANSWERS = "correctAnswers";
    private static final String KEY_QUESTION_ANSWERED = "questionAnswered";
    private static final String QUIZ_TAG = "MainActivity";
    public static final String KEY_EXTRA_ANSWER = "com.example.ps10_z1.correctAnswer";
    private Question[] questions = new Question[]{
      new Question(R.string.q_activity,true),
      new Question(R.string.q_find_resources,false),
      new Question(R.string.q_listener,true),
      new Question(R.string.q_resources,true),
      new Question(R.string.q_version,false),
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(QUIZ_TAG,"Wywołana została metoda cyklu życia: onActivityResult");
        if(resultCode != RESULT_OK) { return; }
        if(requestCode == REQUEST_CODE_PROMPT) {
            if(data == null) { return; }
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(QUIZ_TAG,"Wywołana została metoda cyklu życia: onCreate");
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null)
        {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
            correctAnswers = savedInstanceState.getInt(KEY_CORRECT_ANSWERS);
            questionAnswered = savedInstanceState.getBoolean(KEY_QUESTION_ANSWERED);
        }
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        promptButton = findViewById(R.id.prompt_button);
        questionTextView = findViewById(R.id.question_text_view);

        setNextQuestion();

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(true);
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex++;
                answerWasShown = false;
                if(currentIndex == questions.length){
                    showCorrectAnswersAmount();
                    currentIndex = 0;
                } else {
                    questionAnswered = false;
                    setNextQuestion();
                }


            }
        });

        promptButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PromptActivity.class);
                boolean correctAnswer = questions[currentIndex].isTrueAnswer();
                intent.putExtra(KEY_EXTRA_ANSWER,correctAnswer);
                startActivityForResult(intent, REQUEST_CODE_PROMPT);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(QUIZ_TAG,"Wywołana została metoda: onSaveInstaceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
        outState.putInt(KEY_CORRECT_ANSWERS, correctAnswers);
        outState.putBoolean(KEY_QUESTION_ANSWERED, questionAnswered);
    }

    private void checkAnswerCorrectness(boolean userAnswer){
        if(questionAnswered){
            Toast.makeText(this, R.string.question_answered, Toast.LENGTH_SHORT).show();
            return;
        }
        if(answerWasShown)
        {

            Toast.makeText(this, R.string.answer_was_shown, Toast.LENGTH_SHORT).show();
            return;
        }
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if(userAnswer == correctAnswer){
            correctAnswers++;
            resultMessageId = R.string.correct_answer;
        } else {
            correctAnswers--;
            resultMessageId = R.string.incorrect_answer;
        }

        questionAnswered = true;
        Toast.makeText(this,resultMessageId,Toast.LENGTH_SHORT).show();
    }
    private void showCorrectAnswersAmount(){

        Toast.makeText(this,"Twój wynik : "+correctAnswers +"/"+questions.length,Toast.LENGTH_SHORT).show();
    }
    private void setNextQuestion(){
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(QUIZ_TAG,"Wywołana została metoda cyklu życia: onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(QUIZ_TAG,"Wywołana została metoda cyklu życia: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(QUIZ_TAG,"Wywołana została metoda cyklu życia: onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(QUIZ_TAG,"Wywołana została metoda cyklu życia: onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(QUIZ_TAG,"Wywołana została metoda cyklu życia: onResume");
    }
}