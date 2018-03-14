package pl.nataliana.quizzeseverywhere.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by natalia.nazaruk on 13.03.2018.
 */

public class Quiz implements Parcelable {

    private String quizName;
    private String quizCategory;
    private String imageResourceUrl;
    private int numberOfQuestions;
    private int isFavorite;
    private int lastScore;
    private int lastProgress;
    private int quizId;

    public Quiz(String quizName, String quizCategory, String imageResourceUrl, int numberOfQuestions, int isFavorite, int lastScore, int lastProgress, int quizId) {
        this.quizName = quizName;
        this.quizCategory = quizCategory;
        this.imageResourceUrl = imageResourceUrl;
        this.numberOfQuestions = numberOfQuestions;
        this.isFavorite = isFavorite;
        this.lastScore = lastScore;
        this.lastProgress = lastProgress;
        this.quizId = quizId;
    }

    public Quiz(String quizName, String quizCategory, String imageResourceUrl, int numberOfQuestions, int quizId) {
        this.quizName = quizName;
        this.quizCategory = quizCategory;
        this.imageResourceUrl = imageResourceUrl;
        this.numberOfQuestions = numberOfQuestions;
        this.quizId = quizId;
    }

    private Quiz(Parcel in) {
        quizName = in.readString();
        quizCategory = in.readString();
        imageResourceUrl = in.readString();
        numberOfQuestions = in.readInt();
        isFavorite = in.readInt();
        lastScore = in.readInt();
        lastProgress = in.readInt();
        quizId = in.readInt();
    }

    public static final Creator<Quiz> CREATOR = new Creator<Quiz>() {
        @Override
        public Quiz createFromParcel(Parcel in) {
            return new Quiz(in);
        }

        @Override
        public Quiz[] newArray(int size) {
            return new Quiz[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(quizName);
        parcel.writeString(quizCategory);
        parcel.writeString(imageResourceUrl);
        parcel.writeInt(numberOfQuestions);
        parcel.writeInt(isFavorite);
        parcel.writeInt(lastScore);
        parcel.writeInt(lastProgress);
        parcel.writeInt(quizId);
    }

    public String getQuizName() {
        return quizName;
    }

    public String getQuizCategory() {
        return quizCategory;
    }

    public String getImageResourceUrl() {
        return imageResourceUrl;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public int getLastScore() {
        return lastScore;
    }

    public int getLastProgress() {
        return lastProgress;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    public void setLastScore(int lastScore) {
        this.lastScore = lastScore;
    }

    public void setLastProgress(int lastProgress) {
        this.lastProgress = lastProgress;
    }
}
