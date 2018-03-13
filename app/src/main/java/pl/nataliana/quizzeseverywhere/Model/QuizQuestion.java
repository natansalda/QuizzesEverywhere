package pl.nataliana.quizzeseverywhere.Model;

import java.util.List;

import pl.nataliana.quizzeseverywhere.Enum.AnswerType;
import pl.nataliana.quizzeseverywhere.Enum.QuestionType;

/**
 * Created by natalia.nazaruk on 13.03.2018.
 */

public class QuizQuestion {

    private String resultComment;
    private String questionContent;
    private int quizResult;
    private List<Answer> questionAnswers;
    private AnswerType answerType;
    private QuestionType questionType;

    public String getResultComment() {
        return resultComment;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public int getQuizResult() {
        return quizResult;
    }

    public List<Answer> getQuestionAnswers() {
        return questionAnswers;
    }

    public AnswerType getAnswerType() {
        return answerType;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }
}
