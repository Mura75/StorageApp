package kz.mobile.storageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;

public class FileActivity extends AppCompatActivity {

    private EditText editText2;
    private Button buttonSaveToFile;

    private MyAsyncTask asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        editText2 = findViewById(R.id.editText2);
        buttonSaveToFile = findViewById(R.id.buttonSaveToFile);

        asyncTask = new MyAsyncTask();

        buttonSaveToFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText2.getText().toString();
                asyncTask.execute(text);
            }
        });
    }

    class MyAsyncTask extends AsyncTask<String, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("my_async", "start write to file");
        }

        @Override
        protected Void doInBackground(String... strings) {
            Log.d("my_async", "write in background");
            File file = new File(FileActivity.this.getCacheDir(), "my_file");
            FileUtils.saveToFile(file,FileActivity.this, strings[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("my_async", "finish write to file");
        }
    }
}
