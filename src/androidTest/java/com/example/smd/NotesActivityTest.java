package com.example.smd;

import android.test.ActivityInstrumentationTestCase2;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.example.smd.NotesActivityTest \
 * com.example.smd.tests/android.test.InstrumentationTestRunner
 */
public class NotesActivityTest extends ActivityInstrumentationTestCase2<NotesActivity> {

    public NotesActivityTest() {
        super("com.example.smd", NotesActivity.class);
    }

}
