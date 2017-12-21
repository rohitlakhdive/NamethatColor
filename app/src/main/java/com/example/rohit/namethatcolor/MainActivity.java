package com.example.rohit.namethatcolor;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

public class MainActivity extends Activity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap bitmap;
    private String function;
    private HomeFrag homeFrag;
    private ResultFrag resultFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.homeFrag = new HomeFrag();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainLayout, homeFrag, "home");
        transaction.commit();

    }

    public void back(View view) {

        this.homeFrag = new HomeFrag();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainLayout, homeFrag, "home");
        transaction.commit();

    }

    public void gridNTC(View view) {

        this.function = "grid";
        dispatchTakePictureIntent();

    }

    public void targetedNTC(View view) {

        this.function = "target";
        dispatchTakePictureIntent();

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            this.bitmap = (Bitmap) extras.get("data");

            this.resultFrag = new ResultFrag();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.mainLayout, resultFrag, "home");
            transaction.commit();

            if (this.function.equals("target")) {

                TargetTask targetTask = new TargetTask();
                targetTask.execute();

            } else if (this.function.equals("grid")) {

                GridTask gridTask = new GridTask();
                gridTask.execute();

            }

        }

    }

    private class TargetTask extends AsyncTask<Void, Void, Void> {

        Result result;

        @Override
        protected Void doInBackground(Void... voids) {

            result = ProcessImage.targetedColor(bitmap);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            resultFrag.setResult(result);
            function = "";

        }

    }

    private class GridTask extends AsyncTask<Void, Void, Void> {

        Result result;

        @Override
        protected Void doInBackground(Void... voids) {

            result = ProcessImage.gridColor(bitmap);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            resultFrag.setResult(result);
            function = "";

        }

    }

}