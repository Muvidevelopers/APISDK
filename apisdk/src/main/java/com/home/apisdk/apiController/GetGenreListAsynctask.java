package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.GenreListInput;
import com.home.apisdk.apiModel.GenreListOutput;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by MUVI on 1/20/2017.
 * Class to get Genre List details.
 */

public class GetGenreListAsynctask extends AsyncTask<GenreListInput, Void, Void> {

    private GenreListInput genreListInput;
    private String PACKAGE_NAME;
    private String status;
    private String responseStr;
    private int code;
    private GenreListListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a GetGenreListAsynctask to run some code when get
     * responses.
     */

    public interface GenreListListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onGetGenreListPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param genreListOutput
         * @param code
         * @param status
         */

        void onGetGenreListPostExecuteCompleted(ArrayList<GenreListOutput> genreListOutput, int code, String status);
    }


    ArrayList<GenreListOutput> genreListOutput = new ArrayList<GenreListOutput>();

    /**
     * Constructor to initialise the private data members.
     *
     * @param genreListInput
     * @param listener
     * @param context
     */

    public GetGenreListAsynctask(GenreListInput genreListInput, GenreListListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.genreListInput = genreListInput;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "GetGenreListAsynctask");

    }

    @Override
    protected Void doInBackground(GenreListInput... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getGenreListUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.genreListInput.getAuthToken());

            // Execute HTTP Post Request
            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.v("MUVISDK", "RES" + responseStr);

            } catch (org.apache.http.conn.ConnectTimeoutException e) {
                code = 0;
                status = "";

            } catch (IOException e) {
                code = 0;
                status = "";
            }
            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                code = Integer.parseInt(myJson.optString("code"));
                status = myJson.optString("status");
            }

            if (code == 200) {

                JSONArray jsonMainNode = myJson.getJSONArray("genre_list");

                int lengthJsonArr = jsonMainNode.length();
                for (int i = 0; i < lengthJsonArr; i++) {
                    try {
                        GenreListOutput content = new GenreListOutput();
                        content.setGenre_name(jsonMainNode.get(i).toString());

                        Log.v("MUVISDK", "setGenre_name====== " + jsonMainNode.get(i).toString());

                        genreListOutput.add(content);
                    } catch (Exception e) {
                        code = 0;
                        status = "";
                    }
                }
            }
        } catch (Exception e) {
            code = 0;
            status = "";
        }
        return null;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onGetGenreListPreExecuteStarted();
        code = 0;
        if (!PACKAGE_NAME.equals(HeaderConstants.user_Package_Name_At_Api)) {
            this.cancel(true);
            status = "Package Name Not Matched";
            listener.onGetGenreListPostExecuteCompleted(genreListOutput, code, status);
            return;
        }
        if (HeaderConstants.hashKey.equals("")) {
            this.cancel(true);
            status = "Please Initialize The SDK";
            listener.onGetGenreListPostExecuteCompleted(genreListOutput, code, status);
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        listener.onGetGenreListPostExecuteCompleted(genreListOutput, code, status);
    }
}
