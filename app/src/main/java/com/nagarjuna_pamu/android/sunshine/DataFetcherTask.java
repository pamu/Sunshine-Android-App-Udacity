package com.nagarjuna_pamu.android.sunshine;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by android on 26/1/15.
 */
public class DataFetcherTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    private ProgressDialog dialog;
    private Task task;

    public DataFetcherTask(Context context) {
        super();
        this.mContext = context;
    }

    public DataFetcherTask(Context context, Task task) {
        super();
        this.mContext = context;
        this.task = task;
    }

    @Override
    protected String doInBackground(String... params) {
        String json = null;
        HttpGet get = new HttpGet(params[0]);
        HttpClient client = new DefaultHttpClient();
        try {
            HttpResponse response = client.execute(get);

            if(response == null) return null;

            HttpEntity entity = response.getEntity();

            if(entity == null) return null;

            if(response.getStatusLine().getStatusCode() == 200)
                json = EntityUtils.toString(entity);

            Log.i("Data", json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(mContext);
        dialog.setIndeterminate(true);
        dialog.setTitle("Fetching Data");
        dialog.setMessage("Please wait ...");
        dialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s == null) return;
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
        if(dialog != null) dialog.dismiss();
        if(task != null) task.run(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if(dialog != null) dialog.dismiss();
        if(task != null) task.cancel    ();
    }
}
