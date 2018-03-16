package pl.nataliana.quizzeseverywhere.Data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by natalia.nazaruk on 15.03.2018.
 */

public class QuizDbProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private QuizDbHelper mDbHelper;

    private static final int QUIZZES = 100;
    private static final int QUIZZES_ID = 101;

    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = QuizDbContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, QuizDbContract.QUIZZES_PATH, QUIZZES);
        matcher.addURI(authority, QuizDbContract.QUIZZES_PATH + "/#", QUIZZES_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new QuizDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {

        switch (sUriMatcher.match(uri)) {
            case QUIZZES:
                return QuizDbContract.QuizEntry.CONTENT_TYPE;
            case QUIZZES_ID:
                return QuizDbContract.QuizEntry.CONTENT_ITEM_TYPE;
            default:
                return null;
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor quizCursor;
        final int match = sUriMatcher.match(uri);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        switch (match) {
            case QUIZZES: {
                builder.setTables(QuizDbContract.QuizEntry.TABLE_NAME);
                break;
            }

            case QUIZZES_ID: {
                builder.setTables(QuizDbContract.QuizEntry.TABLE_NAME);
                builder.appendWhere(QuizDbContract.QuizEntry.QUIZ_ID + " = " +
                        uri.getLastPathSegment());
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        quizCursor = builder.query(
                db,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        quizCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return quizCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case QUIZZES: {
                long _id = db.insert(QuizDbContract.QuizEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = QuizDbContract.QuizEntry.buildQuizUri(_id);
                else
                    throw new android.database.SQLException("Failed when inserting the row into" + uri);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case QUIZZES:
                rowsDeleted = db.delete(
                        QuizDbContract.QuizEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case QUIZZES:
                rowsUpdated = db.update(
                        QuizDbContract.QuizEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
