package za.co.sainet.androidjokelib;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import za.co.sainet.androidjokelib.databinding.ActivityDisplayJokeBinding;

public class DisplayJokeActivity extends AppCompatActivity {

    public final static String KEY_JOKE = "joke";
    private ActivityDisplayJokeBinding mDisplayJokeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDisplayJokeBinding = DataBindingUtil.setContentView(this, R.layout.activity_display_joke);
        String jokeText = getString(R.string.joke_text_placeholder);
        if(savedInstanceState != null)  {
             jokeText = savedInstanceState.getString(KEY_JOKE);
        } else if(getIntent().hasExtra(KEY_JOKE)) {
            jokeText = getIntent().getStringExtra(KEY_JOKE);
        }
        mDisplayJokeBinding.tvJoke.setText(jokeText);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_JOKE, mDisplayJokeBinding.tvJoke.getText().toString());
    }
}
