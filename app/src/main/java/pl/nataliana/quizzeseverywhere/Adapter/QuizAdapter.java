package pl.nataliana.quizzeseverywhere.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import pl.nataliana.quizzeseverywhere.MainActivity;
import pl.nataliana.quizzeseverywhere.Model.Quiz;
import pl.nataliana.quizzeseverywhere.R;

/**
 * Created by natalia.nazaruk on 14.03.2018.
 */

public class QuizAdapter extends ArrayAdapter {

    @BindView(R.id.list_item_quiz_image)
    ImageView quizImageView;
    private ArrayList<Quiz> quizes;
    private Context context;

    public QuizAdapter(Context context, ArrayList<Quiz> quizList) {
        super(context, 0, quizList);
        quizes = new ArrayList<>();

        if (quizList != null) {
            quizes = quizList;
        }
        this.context = context;
    }

    @Override
    public int getCount() {
        return quizes.size();
    }

    @Override
    public Quiz getItem(int position) {
        return quizes.get(position);
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
            output = inflater.inflate(R.layout.quiz_card_view_item, parent, false);
        }

        Quiz quiz = getItem(position);

        String url = quiz.getImageResourceUrl();
        Picasso.with(context).load(url).placeholder(R.drawable.placeholder)
                .error(R.drawable.nowifi).into(quizImageView);
        return output;
    }
}
