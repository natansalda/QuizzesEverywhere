package pl.nataliana.quizzeseverywhere.Model;

import android.media.Image;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by natalia.nazaruk on 13.03.2018.
 */

public class Answer {

    private String answerImage;
    private int answerOrder;
    private String answerText;
    private int answerIsCorrect;


    public Answer(JSONObject jsonObject) throws JSONException {

        if (jsonObject.has("image")) {
            this.answerImage = jsonObject.getJSONObject("image").toString();
        }

        if (jsonObject.has("order")) {
            this.answerOrder = jsonObject.getInt("order");
        }

        if (jsonObject.has("text")) {
            this.answerText = jsonObject.get("text").toString();
        }

        if (jsonObject.has("isCorrect")) {
            this.answerIsCorrect = jsonObject.getInt("isCorrect");
        }

    }

    public String getAnswerImage() {
        return answerImage;
    }

    public int getAnswerOrder() {
        return answerOrder;
    }

    public String getAnswerText() {
        return answerText;
    }

    public int answerIsCorrect() {
        return answerIsCorrect;
    }

}
