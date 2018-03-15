package pl.nataliana.quizzeseverywhere.Data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by natalia.nazaruk on 15.03.2018.
 */

public class QuizDbContract {

    public static final String CONTENT_AUTHORITY = "pl.nataliana.quizzeseverywhere";
    public static final Uri BASE_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String QUIZZES_PATH = "quizzwes";

    public static final class QuizEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(QUIZZES_PATH).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + QUIZZES_PATH;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + QUIZZES_PATH;

        public static final String TABLE_NAME = "quizzes";
        public static final String QUIZ_NAME = "title";
        public static final String QUIZ_CATEGORY = "name";
        public static final String QUIZ_IMAGE = "url";
        public static final String QUIZ_NUMBER_OF_QUESTIONS = "questions";
        public static final String QUIZ_IS_FAV = "is_favorite";
        public static final String QUIZ_LAST_SCORE = "last_score";
        public static final String QUIZ_LAST_PROGRESS = "last_progress";
        public static final String QUIZ_ID = "id";

        public static final String[] PROJECTION =
                {QUIZ_NAME, QUIZ_CATEGORY, QUIZ_IMAGE, QUIZ_NUMBER_OF_QUESTIONS, QUIZ_IS_FAV,
                        QUIZ_LAST_SCORE, QUIZ_LAST_PROGRESS, QUIZ_ID};

        public static Uri buildQuizUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
