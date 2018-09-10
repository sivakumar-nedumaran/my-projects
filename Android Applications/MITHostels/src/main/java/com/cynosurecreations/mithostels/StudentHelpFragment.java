package com.cynosurecreations.mithostels;

/**
 * Created by Shiva on 23/07/16.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//Our class extending fragment
public class StudentHelpFragment extends Fragment {

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.fragment_student_help in you classes
        return inflater.inflate(R.layout.fragment_student_help, container, false);
    }
}
