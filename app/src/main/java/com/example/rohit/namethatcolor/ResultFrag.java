package com.example.rohit.namethatcolor;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Rohit on 12/4/2017.
 */

public class ResultFrag extends Fragment {

    ImageView imageView;
    TextView textView1;
    TextView textView2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.result_layout, container, false);

        imageView = view.findViewById(R.id.imageView);
        textView1 = view.findViewById(R.id.textView1);
        textView2 = view.findViewById(R.id.textView2);

        return view;
    }

    public void setResult(Result result) {

        imageView.setImageBitmap(result.getResultantBitmap());
        textView1.setText(result.getResultantText1());
        textView2.setText(result.getResultantText2());

    }
}
