package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.jokesbackend.myJokeApi.MyJokeApi;
import com.udacity.gradle.builditbigger.network.GetJoke;

import java.io.IOException;

import za.co.sainet.androidjokelib.DisplayJokeActivity;


public class MainActivity extends AppCompatActivity {

    private GetJoke mGetJokeTask;

    private GetJoke.CallbackHandler mCallbackHandler = new GetJoke.CallbackHandler() {
        @Override
        public void jokeReceived(String joke) {
            Intent jokeTellingIntent = new Intent(MainActivity.this, DisplayJokeActivity.class);
            jokeTellingIntent.putExtra(DisplayJokeActivity.KEY_JOKE, joke);
            startActivity(jokeTellingIntent);
            if(mIdlingResource != null) {
                mIdlingResource.setIdleState(true);
            }
            mGetJokeTask = null;
        }
    };

    // For testing use only
    @Nullable
    private BasicIdlingResource mIdlingResource;

    // This is public for the test to grab the idling resource
    @VisibleForTesting
    @NonNull
    public BasicIdlingResource getIdlingResource()  {
        if(mIdlingResource == null) {
            mIdlingResource = new BasicIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        if(mGetJokeTask == null)    {
            if(mIdlingResource != null) {
                mIdlingResource.setIdleState(false);
            }
            mGetJokeTask = new GetJoke();
            mGetJokeTask.setHandler(mCallbackHandler);
            mGetJokeTask.execute();
        }
    }



}
