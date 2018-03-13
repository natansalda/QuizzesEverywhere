package pl.nataliana.quizzeseverywhere.Model;

/**
 * Created by natalia.nazaruk on 13.03.2018.
 */

public class Quiz {

    String quizName;
    String quizCategory;
    String imageResourceUrl;
    int numberOfQuestions;
    int isFavorite;
    int lastScore;
    int lastProgress;

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

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public void setQuizCategory(String quizCategory) {
        this.quizCategory = quizCategory;
    }

    public void setImageResourceUrl(String imageResourceUrl) {
        this.imageResourceUrl = imageResourceUrl;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
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
