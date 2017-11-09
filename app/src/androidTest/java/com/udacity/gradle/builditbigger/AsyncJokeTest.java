package com.udacity.gradle.builditbigger;

import android.support.test.runner.AndroidJUnit4;

import com.udacity.gradle.builditbigger.network.GetJoke;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;

/**
 * Created by Kendon Kyle on 2017/11/09.
 */
@RunWith(AndroidJUnit4.class)
public class AsyncJokeTest {

    private GetJoke.CallbackHandler mCallbackHandler = new GetJoke.CallbackHandler() {
        @Override
        public void jokeReceived(String joke) {
            assertFalse(joke.contains("Failed"));
            assertNotSame(joke, "");
        }
    };

    private GetJoke mGetJokeTask;

    @Before
    public void createGetJokeTask() {
        mGetJokeTask = new GetJoke();
    }

    @Test
    public void testGetJokeAsyncTaskReturnsJoke() {
        mGetJokeTask.setHandler(mCallbackHandler);
        mGetJokeTask.execute();
    }
}
