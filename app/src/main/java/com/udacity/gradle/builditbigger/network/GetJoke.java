package com.udacity.gradle.builditbigger.network;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.jokesbackend.myJokeApi.MyJokeApi;

import java.io.IOException;

/**
 * Took this out of MainActivity so it could be tested in isolation
 * Created by Kendon Kyle on 2017/11/09.
 */

public class GetJoke extends AsyncTask<Void,Void,String> {

    public interface CallbackHandler {
        void jokeReceived(String joke);
    }

    CallbackHandler mCalbackHandler;

    public void setHandler(CallbackHandler handler)    {
        mCalbackHandler = handler;
    }

    @Override
    protected String doInBackground(Void... params) {
        MyJokeApi.Builder builder = new MyJokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                .setRootUrl("http://192.168.6.232:8080/_ah/api/")
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
        MyJokeApi jokeApi = builder.build();
        try {
            return jokeApi.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        mCalbackHandler.jokeReceived(joke);
    }
}