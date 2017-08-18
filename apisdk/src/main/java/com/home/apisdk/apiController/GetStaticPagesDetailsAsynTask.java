package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.GetStaticPageDetailsModelOutput;
import com.home.apisdk.apiModel.GetStaticPagesDeatilsModelInput;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by MUVI on 1/20/2017.
 * Class to get Static Page details.
 */

public class GetStaticPagesDetailsAsynTask extends AsyncTask<GetStaticPagesDeatilsModelInput, Void, Void> {

    private GetStaticPagesDeatilsModelInput getStaticPagesDeatilsModelInput;
    private String PACKAGE_NAME;
    private String message;
    private String responseStr;
    private String status;
    private int code;
    private GetStaticPageDetailsModelOutput getStaticPageDetailsModelOutput;
    private GetStaticPageDetailsListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a GetStaticPagesDetailsAsynTask to run some code when get
     * responses.
     */

    public interface GetStaticPageDetailsListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onGetStaticPageDetailsPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param getStaticPageDetailsModelOutput
         * @param code
         * @param message
         * @param status
         */
        void onGetStaticPageDetailsPostExecuteCompleted(GetStaticPageDetailsModelOutput getStaticPageDetailsModelOutput, int code, String message, String status);
    }

    /**
     * Constructor to initialise the private data members.
     *
     * @param getStaticPagesDeatilsModelInput
     * @param listener
     * @param context
     */

    public GetStaticPagesDetailsAsynTask(GetStaticPagesDeatilsModelInput getStaticPagesDeatilsModelInput, GetStaticPageDetailsListener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.getStaticPagesDeatilsModelInput = getStaticPagesDeatilsModelInput;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "GetUserProfileAsynctask");

    }

    @Override
    protected Void doInBackground(GetStaticPagesDeatilsModelInput... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getGetstaticpagesUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.getStaticPagesDeatilsModelInput.getAuthToken());
            httppost.addHeader(HeaderConstants.PERMALINK, this.getStaticPagesDeatilsModelInput.getPermalink());


            // Execute HTTP Post Request
            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.v("MUVISDK", "RES" + responseStr);

            } catch (org.apache.http.conn.ConnectTimeoutException e) {
                code = 0;
                message = "";
                status = "";

            } catch (IOException e) {
                code = 0;
                message = "";
                status = "";
            }
            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                code = Integer.parseInt(myJson.optString("code"));
                message = myJson.optString("msg");
                status = myJson.optString("status");
            }

            if (code == 200) {

                Log.v("MUVISDK", "code = " + status);
                JSONObject mainJson = myJson.getJSONObject("page_details");
                getStaticPageDetailsModelOutput = new GetStaticPageDetailsModelOutput();
                if ((mainJson.has("content")) && mainJson.optString("content").trim() != null && !mainJson.optString("content").trim().isEmpty() && !mainJson.optString("content").trim().equals("null") && !mainJson.optString("content").trim().matches("")) {
                    Log.v("MUVISDK", "mainJson.has(\"content\") = " + mainJson.optString("content"));

                    getStaticPageDetailsModelOutput.setContent(mainJson.optString("content"));

                } else {
                    getStaticPageDetailsModelOutput.setContent("");

                }
                if ((mainJson.has("title")) && mainJson.optString("title").trim() != null && !mainJson.optString("title").trim().isEmpty() && !mainJson.optString("title").trim().equals("null") && !mainJson.optString("title").trim().matches("")) {
                    getStaticPageDetailsModelOutput.setTitle(mainJson.optString("title"));
                } else {
                    getStaticPageDetailsModelOutput.setTitle("");

                }


            }

            Log.v("MUVISDK", "content = " + getStaticPageDetailsModelOutput.getContent());
        } catch (Exception e) {
            code = 0;
            message = "";
            status = "";
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onGetStaticPageDetailsPreExecuteStarted();
        code = 0;
        if (!PACKAGE_NAME.equals(HeaderConstants.user_Package_Name_At_Api)) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetStaticPageDetailsPostExecuteCompleted(getStaticPageDetailsModelOutput, code, message, status);
            return;
        }
        if (HeaderConstants.hashKey.equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetStaticPageDetailsPostExecuteCompleted(getStaticPageDetailsModelOutput, code, message, status);
        }
        listener.onGetStaticPageDetailsPostExecuteCompleted(getStaticPageDetailsModelOutput, code, message, status);

    }

    @Override
    protected void onPostExecute(Void result) {
        listener.onGetStaticPageDetailsPostExecuteCompleted(getStaticPageDetailsModelOutput, code, message, status);
    }
}
