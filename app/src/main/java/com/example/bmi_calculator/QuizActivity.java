package com.example.bmi_calculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import java.io.FileDescriptor;
import java.io.InputStream;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "FlagQuiz Activity";
    private TextView answerTextView;
    private TextView answerTextView2;
    private List<String> answersList;
    private List<String> questionNameList; // flag file names
    private List<String> quizQuestionList;
    private String correctAnswer;
    private TextView questionNumberTextView;
    private TextView questionTextView;
    private TextView score;
    public static final String CHOICES = "pref_numberOfChoices";
    private boolean preferencesChanged = true; // did preferences change?
    private int guessRows;
    private LinearLayout quizLinearLayout;
    private LinearLayout[] guessLinearLayouts;
    private static int totalGuesses; // number of guesses made
    private int correctAnswers; // number of correct guesses
    private static final int KORONA_IN_QUIZ = 6;
    static int result, point, total, questionNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        questionTextView = findViewById(R.id.QuestionText);
        questionNumberTextView = findViewById(R.id.questionNumberTextView);
        answersList = new ArrayList<>();
        questionNameList = new ArrayList<>();
        quizQuestionList = new ArrayList<>();
        score = findViewById(R.id.TV_points);
        answerTextView = (TextView) findViewById(R.id.answerTextView);
        answerTextView2 = (TextView) findViewById(R.id.answerTextView2);
        Button btn_setttings = findViewById(R.id.btn_settings);
        PreferenceManager.setDefaultValues(this, R.xml.preference, false);
        PreferenceManager.getDefaultSharedPreferences(this).
                registerOnSharedPreferenceChangeListener(
                        preferencesChangeListener);
        questionNumberTextView.setText(
                getString(R.string.question, 1, KORONA_IN_QUIZ));

        guessLinearLayouts = new LinearLayout[4];
        guessLinearLayouts[0] =
                (LinearLayout) findViewById(R.id.row1LinearLayout);
        guessLinearLayouts[1] =
                (LinearLayout) findViewById(R.id.row2LinearLayout);
        guessLinearLayouts[2] =
                (LinearLayout) findViewById(R.id.row3LinearLayout);
        guessLinearLayouts[3] =
                (LinearLayout) findViewById(R.id.row4LinearLayout);

        for (LinearLayout row : guessLinearLayouts) {
            for (int column = 0; column < row.getChildCount(); column++) {
                Button button = (Button) row.getChildAt(column);
                button.setOnClickListener(guessButtonListener);
            }
        }

        btn_setttings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPreferencesActivity();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (preferencesChanged) {
            updateGuessRows(
                PreferenceManager.getDefaultSharedPreferences(this));

            resetQuiz();
            preferencesChanged = false;
        }
    }

    private void startPreferencesActivity() {
        Intent intent = new Intent(QuizActivity.this, myPreference.class);
        startActivity(intent);
    }

    private SharedPreferences.OnSharedPreferenceChangeListener preferencesChangeListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                // called when the user changes the app's preferences
                @Override
                public void onSharedPreferenceChanged(
                        SharedPreferences sharedPreferences, String key) {
                    preferencesChanged = true; // user changed app setting

                    if (key.equals(CHOICES)) { // # of choices to display changed
                        updateGuessRows(sharedPreferences);
                        resetQuiz();
                    }
                }
            };

    public void updateGuessRows(SharedPreferences sharedPreferences) {
        // get the number of guess buttons that should be displayed
        String choices = sharedPreferences.getString(CHOICES,  "4");
        guessRows = Integer.parseInt(choices) / 2;

        // hide all quess button LinearLayouts
        for (LinearLayout layout : guessLinearLayouts)
            layout.setVisibility(View.GONE);

        // display appropriate guess button LinearLayouts
        for (int row = 0; row < guessRows; row++)
            guessLinearLayouts[row].setVisibility(View.VISIBLE);
    }

    public void resetQuiz() {
        score.setText("Score: 0");
        point = 100;
        result = 0;
        total = 0;
        questionNumber = 0;
        questionNameList.clear();
        String[] paths = getResources().getStringArray(R.array.PYTANIA);
        for (String path : paths){
            questionNameList.add(path);
        }
        correctAnswers = 0; // reset the number of correct answers made
        totalGuesses = 0; // reset the total number of guesses the user made
        quizQuestionList.clear(); // clear prior list of quiz countries

        int QuestionCounter = 1;
        int numberOfQuestions = questionNameList.size();

        // add FLAGS_IN_QUIZ random file names to the quizCountriesList
        while (QuestionCounter <= KORONA_IN_QUIZ) {
            int randomIndex = new Random().nextInt(numberOfQuestions);
            // get the random file name
            String pytanie = questionNameList.get(randomIndex);

            // if the region is enabled and it hasn't already been chosen
            if (!quizQuestionList.contains(pytanie)) {
                quizQuestionList.add(pytanie); // add the file to the list
                ++QuestionCounter;
            }
        }
        loadNextQuestion(); // start the quiz by loading the first flag
    }

    private void loadNextQuestion() {
        point = 100;
        answersList.clear();
        questionNumber = questionNumber +1;
        String nextQuestion = quizQuestionList.remove(0);
        //getResources().getIdentifier(nextQuestion, "array", this.getPackageName())
        String QuestionSets[] =  getResources().getStringArray(getResources().getIdentifier(nextQuestion, "array", this.getPackageName()));
        String Question = QuestionSets[0];
        correctAnswer = QuestionSets[1]; // update the correct answer

        questionTextView.setText(Question);
        answerTextView.setText(""); // clear answerTextView
        answerTextView2.setText(""); // clear answerTextView

        for (int i = 2; i < QuestionSets.length; i++){
            answersList.add(QuestionSets[i]);
            Log.e("TUAJ:", i+"");
        }
        Collections.shuffle(answersList);

//         display current question number
        questionNumberTextView.setText(getString(R.string.question, questionNumber, KORONA_IN_QUIZ));

        // put the correct answer at the end of fileNameList
        int correct = questionNameList.indexOf(nextQuestion);
        questionNameList.add(questionNameList.remove(correct));

        // add 2, 4, 6 or 8 guess Buttons based on the value of guessRows
        int ans_pos = 0;
        for (int row = 0; row < guessRows; row++) {
            // place Buttons in currentTableRow
            for (int column = 0;
                 column < guessLinearLayouts[row].getChildCount();
                 column++) {
                // get reference to Button to configure
                Button newGuessButton =
                        (Button) guessLinearLayouts[row].getChildAt(column);
                newGuessButton.setEnabled(true);

                // get country name and set it as newGuessButton's text
                String filename = answersList.get(ans_pos);
                newGuessButton.setText(filename);
                ans_pos++;
            }
        }

        // randomly replace one Button with the correct answer
        int row = new Random().nextInt(guessRows); // pick random row
        int column = new Random().nextInt(2); // pick random column
        LinearLayout randomRow = guessLinearLayouts[row]; // get the row
        String countryName = correctAnswer;
        ((Button) randomRow.getChildAt(column)).setText(countryName);
    }

    private void disableButtons() {
        for (int row = 0; row < guessRows; row++) {
            LinearLayout guessRow = guessLinearLayouts[row];
            for (int i = 0; i < guessRow.getChildCount(); i++)
                guessRow.getChildAt(i).setEnabled(false);
        }
    }

    private final View.OnClickListener guessButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button guessButton = ((Button) v);
            String guess = guessButton.getText().toString();
            String answer = correctAnswer;
            ++totalGuesses; // increment number of guesses the user has made

            if (guess.equals(answer)) { // if the guess is correct
                ++result; // increment the number of correct answers
                total = total + point;
                score.setText("Score: " + total);
                // display correct answer in green text
                answerTextView.setText(answer + "!");
                answerTextView.setTextColor(getColor(R.color.colorPrimary));

                answerTextView2.setText("Odpowiedź poprawna");
                answerTextView2.setTextColor(getColor(R.color.colorPrimary));

                disableButtons(); // disable all guess Buttons

                // if the user has correctly identified FLAGS_IN_QUIZ flags
                if (questionNumber == KORONA_IN_QUIZ) {
                    answerTextView.setText(answer + "!");
                    answerTextView2.setText("Gratulujemy");
                    questionTextView.setText("Koniec gry");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            resetQuiz();
                        }
                    }, 3000);



                }else {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            loadNextQuestion();
                        }
                    }, 2000);
                }
            } else { // answer was incorrect

                // display "Incorrect!" in red
                point = point - 10;
                answerTextView.setText(R.string.incorrect_answer);
                answerTextView2.setText(R.string.incorrect_answer);
                answerTextView2.setText("Spróbuj ponownie.");
                answerTextView.setTextColor(getResources().getColor(
                        R.color.colorAccent));
                guessButton.setEnabled(false);

            }
        }
    };

    public static class QuizResults extends DialogFragment {


            // create an AlertDialog and return it
            @Override
            public Dialog onCreateDialog(Bundle bundle) {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(getActivity());
            builder.setMessage(
                    getString(R.string.results,
                            totalGuesses,
                            point));

            // "Reset Quiz" Button
            builder.setPositiveButton(R.string.reset_quiz,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int id) {

                        }
                    }
            );

            return builder.create(); // return the AlertDialog
        }
    }
}
