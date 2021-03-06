package pl.nataliana.quizzeseverywhere.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pl.nataliana.quizzeseverywhere.Model.Quiz;
import pl.nataliana.quizzeseverywhere.R;

/**
 * Created by natalia.nazaruk on 14.03.2018.
 */

public class QuizAdapter extends ArrayAdapter {

    private ArrayList<Quiz> quizzes;
    private Context context;

    public QuizAdapter(Context context, ArrayList<Quiz> quizList) {
        super(context, 0, quizList);
        quizzes = new ArrayList<>();

        if (quizList != null) {
            quizzes = quizList;
        }
        this.context = context;
    }

    @Override
    public int getCount() {
        return quizzes.size();
    }

    @Override
    public Quiz getItem(int position) {
        return quizzes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getQuizId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View output = convertView;
        if (output == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            output = inflater.inflate(R.layout.quiz_item_list, parent, false);
        }

        TextView quizTitleTextView = output.findViewById(R.id.list_item_quiz_title);
        ImageView quizImageImageView = output.findViewById(R.id.list_item_quiz_image);
        TextView categoryNameTextView = output.findViewById(R.id.list_item_quiz_category_name);
        TextView categoryValueTextView = output.findViewById(R.id.list_item_quiz_category_value);
        TextView quizScoreNameTextView = output.findViewById(R.id.list_item_quiz_score_name);
        TextView quizScoreValueTextView = output.findViewById(R.id.list_item_quiz_score_value);
        TextView quizProgressNameTextView = output.findViewById(R.id.list_item_quiz_progress_name);
        TextView quizProgressValueTextView = output.findViewById(R.id.list_item_quiz_progress_value);

        Quiz quiz = getItem(position);

        quizTitleTextView.setText(quizzes.get(position).getQuizName());
        categoryNameTextView.setText(R.string.category);
        String url = quiz.getImageResourceUrl();
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.nowifi)
                .into(quizImageImageView);
        categoryValueTextView.setText(quizzes.get(position).getQuizCategory());
        quizScoreNameTextView.setText(R.string.last_score);
        if (quizzes.get(position).getLastScore() == 0) {
            quizScoreValueTextView.setText("0");
        } else {
            quizScoreValueTextView.setText(quizzes.get(position).getLastScore());
        }
        quizProgressNameTextView.setText(R.string.progress_in_quiz);
        if (quizzes.get(position).getLastProgress() == 0) {
            quizProgressValueTextView.setText("0");
        } else {
            quizProgressValueTextView.setText(quizzes.get(position).getLastProgress());
        }
        return output;
    }
}
