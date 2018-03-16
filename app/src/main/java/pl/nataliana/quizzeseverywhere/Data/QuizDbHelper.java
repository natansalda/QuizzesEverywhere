package pl.nataliana.quizzeseverywhere.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by natalia.nazaruk on 15.03.2018.
 */

public class QuizDbHelper extends SQLiteOpenHelper {

    private  static  final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "quizzes.db";

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_QUIZ_TABLE = "CREATE TABLE " + QuizDbContract.QuizEntry.TABLE_NAME + " (" +
                QuizDbContract.QuizEntry.QUIZ_NAME + " TEXT UNIQUE NOT NULL," +
                QuizDbContract.QuizEntry.QUIZ_CATEGORY + " TEXT UNIQUE NOT NULL," +
                QuizDbContract.QuizEntry.QUIZ_IMAGE + " TEXT NOT NULL," +
                QuizDbContract.QuizEntry.QUIZ_NUMBER_OF_QUESTIONS + " TEXT NOT NULL," +
                QuizDbContract.QuizEntry.QUIZ_IS_FAV + " TEXT NOT NULL," +
                QuizDbContract.QuizEntry.QUIZ_LAST_SCORE + " TEXT NOT NULL," +
                QuizDbContract.QuizEntry.QUIZ_LAST_PROGRESS + " TEXT NOT NULL," +
                QuizDbContract.QuizEntry.QUIZ_ID + " TEXT NOT NULL," +
                "UNIQUE (" + QuizDbContract.QuizEntry.QUIZ_ID +") ON CONFLICT IGNORE"+
                " );";

        db.execSQL(SQL_CREATE_QUIZ_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE " + QuizDbContract.QuizEntry.TABLE_NAME);
        onCreate(db);
    }
}
