package com.zybooks.studyhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    public static final String EXTRA_SUBJECT = "com.zybooks.studyhelper.subject";

    private StudyDatabase mStudyDb;
    private String mSubject;
    private List<Question> mQuestionList;
    private TextView mAnswerLabel;
    private TextView mAnswerText;
    private Button mAnswerButton;
    private TextView mQuestionText;
    private int mCurrentQuestionIndex;
    private ViewGroup mShowQuestionsLayout;
    private ViewGroup mNoQuestionsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Hosting activity provides the subject of the questions to display
        Intent intent = getIntent();
        mSubject = intent.getStringExtra(EXTRA_SUBJECT);

        // Load all questions for this subject
        mStudyDb = StudyDatabase.getInstance(getApplicationContext());
        mQuestionList = mStudyDb.getQuestions(mSubject);

        mQuestionText = findViewById(R.id.questionText);
        mAnswerLabel = findViewById(R.id.answerLabel);
        mAnswerText = findViewById(R.id.answerText);
        mAnswerButton = findViewById(R.id.answerButton);
        mShowQuestionsLayout = findViewById(R.id.showQuestionsLayout);
        mNoQuestionsLayout = findViewById(R.id.noQuestionsLayout);

        // Show first question
        showQuestion(0);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Are there questions to display?
        if (mQuestionList.size() == 0) {
            updateAppBarTitle();
            displayQuestion(false);
        } else {
            displayQuestion(true);
            toggleAnswerVisibility();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate menu for the app bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.question_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Determine which app bar item was chosen
        switch (item.getItemId()) {
            case R.id.previous:
                showQuestion(mCurrentQuestionIndex - 1);
                return true;
            case R.id.next:
                showQuestion(mCurrentQuestionIndex + 1);
                return true;
            case R.id.add:
                addQuestion();
                return true;
            case R.id.edit:
                editQuestion();
                return true;
            case R.id.delete:
                deleteQuestion();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addQuestionButtonClick(View view) {
        addQuestion();
    }

    public void answerButtonClick(View view) {
        toggleAnswerVisibility();
    }

    private void displayQuestion(boolean display) {

        // Show or hide the appropriate screen
        if (display) {
            mShowQuestionsLayout.setVisibility(View.VISIBLE);
            mNoQuestionsLayout.setVisibility(View.GONE);
        } else {
            mShowQuestionsLayout.setVisibility(View.GONE);
            mNoQuestionsLayout.setVisibility(View.VISIBLE);
        }
    }

    private void updateAppBarTitle() {

        // Display subject and number of questions in app bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            String title = getResources().getString(R.string.question_number,
                    mSubject, mCurrentQuestionIndex + 1, mQuestionList.size());
            setTitle(title);
        }
    }

    private void addQuestion() {
        // TODO: Add question
    }

    private void editQuestion() {
        // TODO: Edit question
    }

    private void deleteQuestion() {
        // TODO: Delete question
    }

    private void showQuestion(int questionIndex) {

        // Show question at the given index
        if (mQuestionList.size() > 0) {
            if (questionIndex < 0) {
                questionIndex = mQuestionList.size() - 1;
            } else if (questionIndex >= mQuestionList.size()) {
                questionIndex = 0;
            }

            mCurrentQuestionIndex = questionIndex;
            updateAppBarTitle();

            Question question = mQuestionList.get(mCurrentQuestionIndex);
            mQuestionText.setText(question.getText());
            mAnswerText.setText(question.getAnswer());
        }
        else {
            // No questions yet
            mCurrentQuestionIndex = -1;
        }
    }

    private void toggleAnswerVisibility() {
        if (mAnswerText.getVisibility() == View.VISIBLE) {
            mAnswerButton.setText(R.string.show_answer);
            mAnswerText.setVisibility(View.INVISIBLE);
            mAnswerLabel.setVisibility(View.INVISIBLE);
        }
        else {
            mAnswerButton.setText(R.string.hide_answer);
            mAnswerText.setVisibility(View.VISIBLE);
            mAnswerLabel.setVisibility(View.VISIBLE);
        }
    }
}