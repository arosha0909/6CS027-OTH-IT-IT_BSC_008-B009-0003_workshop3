package com.example.workshop3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private TextView mQuestionTextView;
    private TextView successTextView;

    private static int SuccessPoints=0;
    private static double SuccessPercentage=0;

    private static int QuizCount=0;
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),

            new Question(R.string.question_expectancy, false),
            new Question(R.string.question_standing, true),
            new Question(R.string.question_speakers, true),
            new Question(R.string.question_surname, false),
            new Question(R.string.question_disease, true),
    };
    private int mCurrentIndex = 0;
    private int arrayListLength = 0;
//    private Button mNextButton;
    private ImageButton mNextButton;

    private ImageButton mPreviousButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SuccessPoints=0;
        QuizCount=0;
        SuccessPercentage=0;

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
//        int question = mQuestionBank[mCurrentIndex].getTextResId();
//        mQuestionTextView.setText(question);
        updateQuestion();


        mTrueButton.setOnClickListener((v) -> {
//            Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
            checkAnswer(true);

            mTrueButton.setBackgroundColor(Color.YELLOW);
            mFalseButton.setBackgroundColor(Color.DKGRAY);
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        });

        mFalseButton.setOnClickListener((v) -> {
//            Toast.makeText(MainActivity.this, "Incorrect!", Toast.LENGTH_SHORT).show();
            checkAnswer(false);

            mTrueButton.setBackgroundColor(Color.DKGRAY);
            mFalseButton.setBackgroundColor(Color.YELLOW);

            mFalseButton.setEnabled(false);
            mTrueButton.setEnabled(false);
        });

//        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mCurrentIndex==9){
                    Toast.makeText(MainActivity.this, "Completed Quiz List!", Toast.LENGTH_SHORT).show();
                }else {
                    QuizCount++;
                    mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                    updateQuestion();
                    calculationSuccess(QuizCount,SuccessPoints);
                    mTrueButton.setEnabled(true);
                    mFalseButton.setEnabled(true);
                    mTrueButton.setBackgroundColor(Color.DKGRAY);
                    mFalseButton.setBackgroundColor(Color.DKGRAY);
                }

            }
        });

        mPreviousButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(mCurrentIndex==0){
                    Toast.makeText(MainActivity.this, "Completed Quiz List!", Toast.LENGTH_SHORT).show();
                }else {
                    QuizCount--;
                    SuccessPoints--;
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                    updateQuestion();
                    calculationSuccess(QuizCount,SuccessPoints);
                    mTrueButton.setEnabled(true);
                    mFalseButton.setEnabled(true);
                    mTrueButton.setBackgroundColor(Color.DKGRAY);
                    mFalseButton.setBackgroundColor(Color.DKGRAY);
                }

            }
        });

        successTextView=(TextView) findViewById(R.id.success_percentage);
        mTrueButton.setBackgroundColor(Color.DKGRAY);
        mFalseButton.setBackgroundColor(Color.DKGRAY);
    }

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            SuccessPoints++;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
    private void calculationSuccess(int quizCount,float succPoints){
        SuccessPercentage=0;
        if (quizCount > 0 && succPoints>=0) {
            SuccessPercentage = Math.round((double) succPoints / quizCount * 100.0);
            successTextView.setText(String.valueOf(SuccessPercentage));
        }else{
            quizCount=0;
            succPoints=0;
        }
        successTextView.setText(String.valueOf(SuccessPercentage));
    }
}
