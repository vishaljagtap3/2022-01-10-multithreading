package in.bitcode.threadingdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnDownload;
    private EditText edtPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        btnDownload = findViewById(R.id.btnDownload);
        edtPath = findViewById(R.id.edtPath);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new DownloadThread(edtPath.getText().toString()).start();

                String [] files = {
                    "https://bitcode.in/file1",
                    "https://google.com/data/data.txt",
                    "https://aavidsoft.com/new/somefile.mp3"
                };

                new DownloadThreadNew()
                        .execute(files);
            }
        });
    }


    class DownloadThreadNew extends AsyncTask<String, Integer, Float> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.e("tag", "onPre: " + Thread.currentThread().getName());

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Downloading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
        }

        @Override
        protected Float doInBackground(String... args) {

            Log.e("tag", "doInBg: " + Thread.currentThread().getName());

            for(String file : args) {

                progressDialog.setMessage(file);

                for (int i = 0; i <= 100; i++) {
                    //btnDownload.setText(i + " % "); //Should not have worked
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressDialog.setProgress(i);

                    Integer [] progress = new Integer[1];
                    progress[0] = i;
                    publishProgress(progress);
                }
            }
            return 12.12F;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            btnDownload.setText(values[0] + "%");
        }

        @Override
        protected void onPostExecute(Float aFloat) {
            super.onPostExecute(aFloat);
            Log.e("tag", "onPost: " + Thread.currentThread().getName());
            btnDownload.setText(aFloat  +" is final res");
            progressDialog.dismiss();
        }

    }

    class DownloadThread extends Thread {

        private String path;
        private ProgressDialog progressDialog;

        public DownloadThread(String path) {
            this.path = path;
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("BitCode");
            progressDialog.setMessage("Downloading " + path);
            progressDialog.setIcon(R.mipmap.ic_launcher);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            //progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        public void run() {
            super.run();
            for(int i= 0; i <= 100; i++) {
                progressDialog.setProgress(i);

                //btnDownload.setText(i + "%"); //will not work

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            progressDialog.dismiss();
        }
    }
}





