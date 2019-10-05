package kz.mobile.storageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;

public class ExternalFileActivity extends AppCompatActivity {

    private EditText editText3;
    private Button buttonSaveToExternal;

    private AsyncTaskExternal asyncTaskExternal;

    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_file);

        editText3 = findViewById(R.id.editText3);
        buttonSaveToExternal = findViewById(R.id.buttonSaveToExternal);

        buttonSaveToExternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = editText3.getText().toString();
                asyncTaskExternal = new AsyncTaskExternal();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (writeToExternalFileGranted()) {
                        asyncTaskExternal.execute(text);
                    } else {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                    }
                } else {
                    asyncTaskExternal.execute(text);
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                asyncTaskExternal.execute(text);
            }
        }
    }

    private boolean writeToExternalFileGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    class AsyncTaskExternal extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            if (strings[0] != null) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "external file");
                if (!file.exists()) {
                    file.mkdirs();
                }
                FileUtils.saveToFile(file, ExternalFileActivity.this, strings[0]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
