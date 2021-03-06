package pl.nataliana.quizzeseverywhere;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import pl.nataliana.quizzeseverywhere.Adapter.QuizAdapter;
import pl.nataliana.quizzeseverywhere.Data.QuizDbContract;
import pl.nataliana.quizzeseverywhere.Model.Quiz;

public class MainActivity extends AppCompatActivity {

    public static final String ALL_QUIZ_BASE_URL = "http://quiz.o2.pl/api/v1/quizzes/0/100";
    private static final String TAG = MainActivity.class.getSimpleName(); // For tagging purposes
    private QuizAdapter quizAdapter;
    private ListView listView;
    private Quiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quizAdapter = new QuizAdapter(this, new ArrayList<Quiz>());
        listView = findViewById(R.id.quiz_list_view);

        if (savedInstanceState == null) {
            if (isOnline()) {
                showQuizes();
            } else {
                showOffline();
            }
        } else {
            //Get data from local resources
            Parcelable[] parcelable = savedInstanceState.
                    getParcelableArray(getString(R.string.quiz_parcelable));

            if (parcelable != null) {
                int numQuizObjects = parcelable.length;
                Quiz[] quizzes = new Quiz[numQuizObjects];
                for (int i = 0; i < numQuizObjects; i++) {
                    quizzes[i] = (Quiz) parcelable[i];
                }

                for (Quiz quiz : quizzes) {
                    if (quiz != null) {
                        quizAdapter.add(quiz);
                    }
                }

                // Load movie objects into view
                listView.setAdapter(quizAdapter);
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Quiz quiz = quizAdapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                int QuizID = quiz.getQuizId();
                intent.putExtra(getString(R.string.quiz_id_extra), QuizID);
                intent.putExtra(getString(R.string.quiz_parcelable), quiz);
                addToFav(QuizID);
                startActivity(intent);
            }
        });
    }

    // Add quiz to the database
    private void addToFav(int QuizID) {

        Uri uri = QuizDbContract.QuizEntry.CONTENT_URI;
        ContentResolver resolver = this.getContentResolver();
        ContentValues values = new ContentValues();
        values.clear();

        values.put(QuizDbContract.QuizEntry.QUIZ_NAME, quiz.getQuizName());
        values.put(QuizDbContract.QuizEntry.QUIZ_CATEGORY, quiz.getQuizCategory());
        values.put(QuizDbContract.QuizEntry.QUIZ_IMAGE, quiz.getImageResourceUrl());
        values.put(QuizDbContract.QuizEntry.QUIZ_NUMBER_OF_QUESTIONS, quiz.getNumberOfQuestions());
        values.put(QuizDbContract.QuizEntry.QUIZ_IS_FAV, quiz.getIsFavorite());
        values.put(QuizDbContract.QuizEntry.QUIZ_LAST_SCORE, quiz.getLastScore());
        values.put(QuizDbContract.QuizEntry.QUIZ_LAST_PROGRESS, quiz.getLastProgress());
        values.put(QuizDbContract.QuizEntry.QUIZ_ID, QuizID);

        Uri check = resolver.insert(uri, values);
        Toast.makeText(getApplicationContext(), "Quiz added to favorites!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        int numQuizObjects = listView.getCount();
        if (numQuizObjects > 0) {
            // Get Quiz objects from listView
            Quiz[] quizzes = new Quiz[numQuizObjects];
            for (int i = 0; i < numQuizObjects; i++) {
                quizzes[i] = (Quiz) listView.getItemAtPosition(i);
            }

            // Save Quiz objects to bundle
            outState.putParcelableArray(getString(R.string.quiz_parcelable), quizzes);
        }
        super.onSaveInstanceState(outState);
    }

    private void showQuizes() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestHandle requestHandle = client.get(ALL_QUIZ_BASE_URL, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                try {
                    JSONObject jsonObj = new JSONObject(responseBody);
                    JSONArray quizzes = jsonObj.getJSONArray("items");

                    // looping through quizzes
                    Quiz[] quizList = new Quiz[quizzes.length()];
                    for (int i = 0; i < quizzes.length(); ++i) {
                        JSONObject quiz = quizzes.getJSONObject(i);
                        JSONArray category = quiz.getJSONArray("categories");
                        JSONObject catName = category.getJSONObject(0);
                        JSONObject photo = quiz.getJSONObject("mainPhoto");
                        quizList[i] = new Quiz(
                                quiz.getString("title"), //quiz title
                                catName.getString("name"), //category's name
                                photo.getString("url"), //picture url
                                quiz.getInt("questions"), //num of questions
                                quiz.getInt("id")); //quiz id
                    }
                    quizAdapter.clear();
                    for (Quiz quiz : quizList) {
                        if (quiz != null) {
                            Log.v(TAG, "URLS" + quiz);
                            quizAdapter.add(quiz);
                        }
                    }
                    quizAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    Log.e(TAG, "Error", e);
                }

                listView.setAdapter(quizAdapter);
                quizAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Failed: ", "" + statusCode);
                Log.d("Error : ", "" + throwable);
            }
        });
    }


    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void showOffline() {
        Uri uri = QuizDbContract.QuizEntry.CONTENT_URI;
        ContentResolver resolver = getContentResolver();
        Cursor cursor = null;

        try {
            cursor = resolver.query(
                    uri,
                    null,
                    null,
                    null,
                    null);

            quizAdapter.clear();

            if (cursor.moveToFirst()) {
                do {
                    Quiz quiz = new Quiz(cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6), cursor.getInt(7), cursor.getInt(8));
                    quizAdapter.add(quiz);
                } while (cursor.moveToNext());
            }

        } finally {
            if (cursor != null)
                cursor.close();
        }
    }
}
