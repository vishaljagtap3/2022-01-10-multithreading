package in.bitcode.threadingdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import in.bitcode.threadingdemo.MainActivity;

class DownloadThreadNew extends AsyncTask<String, Integer, Float> {

    private ProgressDialog progressDialog;
    private Handler handler;
    private Context context;

    public DownloadThreadNew(Context context, Handler handler) {
        this.handler = handler;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Log.e("tag", "onPre: " + Thread.currentThread().getName());

        progressDialog = new ProgressDialog(context);
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
                    Thread.sleep(10);
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

    //You can send message to a handler only from the thread on which the handler was created

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Message message = new Message();
        message.arg1 = 2;
        message.obj = values[0]; //Auto boxing
        handler.sendMessage(message); //this is good
        //handler.handleMessage(message); //works, however it is not a good practice

    }

    @Override
    protected void onPostExecute(Float aFloat) {
        super.onPostExecute(aFloat);
        Log.e("tag", "onPost: " + Thread.currentThread().getName());

        Message message = new Message();
        message.arg1 = 1;
        message.obj = aFloat;

        handler.sendMessage(message);

        progressDialog.dismiss();
    }

}