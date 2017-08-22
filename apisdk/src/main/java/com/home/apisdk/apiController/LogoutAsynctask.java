package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.LogoutInput;

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
 * Class to get Logout details.
 */

public class LogoutAsynctask extends AsyncTask<LogoutInput, Void, Void> {

    private LogoutInput logoutInput;
    private String PACKAGE_NAME;
    private String message;
    private String responseStr;
    private String status;
    private int code;
    private LogoutListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a LogoutAsynctask to run some code when get
     * responses.
     */

    public interface LogoutListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onLogoutPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param code
         * @param status
         * @param message
         */

        void onLogoutPostExecuteCompleted(int code, String status, String message);

    }

    /**
     * Constructor to initialise the private data members.
     *
     * @param logoutInput
     * @param listener
     * @param context
     */

    public LogoutAsynctask(LogoutInput logoutInput, LogoutListener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.logoutInput = logoutInput;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "LogoutAsynctask");

    }

    @Override
    protected Void doInBackground(LogoutInput... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getLogoutUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.logoutInput.getAuthToken());
            httppost.addHeader(HeaderConstants.LOGIN_HISTORY_ID, this.logoutInput.getLogin_history_id());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.logoutInput.getLang_code());

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
        listener.onLogoutPreExecuteStarted();
        code = 0;
        status = "";
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api())) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onLogoutPostExecuteCompleted(code, status, message);
            return;
        }
        if (SDKInitializer.getHashKey().equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onLogoutPostExecuteCompleted(code, status, message);
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        listener.onLogoutPostExecuteCompleted(code, status, message);
    }
}
