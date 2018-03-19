package pl.nataliana.quizzeseverywhere;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by natalia.nazaruk on 14.03.2018.
 */

public class QuizActivity extends AppCompatActivity{

    public static final String SINGLE_QUIZ_BASE_URL = "http://quiz.o2.pl/api/v1/quiz/%s/0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_detail);
    }
}
