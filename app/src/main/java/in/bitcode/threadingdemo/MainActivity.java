package in.bitcode.threadingdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

                Handler handler = new MyHandler();

                new DownloadThreadNew(MainActivity.this, handler)
                        .execute(files);
            }
        });
    }


     class MyHandler extends Handler {
         @Override
         public void handleMessage(@NonNull Message msg) {
             super.handleMessage(msg);
             if(msg != null && msg.obj != null) {
                 if(msg.arg1 == 1) {
                     float res = (float) msg.obj;
                     btnDownload.setText("Res: " + msg.obj);
                 }
                 if(msg.arg1 == 2) {
                     int val = (Integer)msg.obj;
                     btnDownload.setText("Progress: " + msg.obj);
                 }
             }
         }
     }



    public class DownloadThread extends Thread {

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





