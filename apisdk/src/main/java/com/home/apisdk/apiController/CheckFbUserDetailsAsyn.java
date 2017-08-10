package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.CommonConstants;
import com.home.apisdk.apiModel.CheckFbUserDetailsInput;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by MUVI on 7/4/2017.
 * Class to Get Facebook User details
 */

public class CheckFbUserDetailsAsyn extends AsyncTask<CheckFbUserDetailsInput, Void, Void> {

    private CheckFbUserDetailsInput checkFbUserDetailsInput;
    private String PACKAGE_NAME;
    private String responseStr;
    private String message;
    private int isNewUserStr = 1;
    private int code;
    private JSONObject myJson = null;
    private CheckFbUserDetailsListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a CheckFbUserDetailsAsyn to run some code when get
     * responses.
     */

    public interface CheckFbUserDetailsListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onCheckFbUserDetailsAsynPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param code
         */

        void onCheckFbUserDetailsAsynPostExecuteCompleted(int code);
    }

    /**
     * Constructor to initialise the private data members.
     *
     * @param checkFbUserDetailsInput
     * @param listener
     * @param context
     */

    public CheckFbUserDetailsAsyn(CheckFbUserDetailsInput checkFbUserDetailsInput, CheckFbUserDetailsListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.checkFbUserDetailsInput = checkFbUserDetailsInput;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "register user payment");
    }

    @Override
    protected Void doInBackground(CheckFbUserDetailsInput... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getFbUserExistsUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(CommonConstants.AUTH_TOKEN, this.checkFbUserDetailsInput.getAuthToken());
            httppost.addHeader(CommonConstants.FB_USER_ID, this.checkFbUserDetailsInput.getFb_userid());


            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.v("MUVISDK", "RES" + responseStr);


            } catch (ClientProtocolException e) {
                code = 0;
                e.printStackTrace();
            } catch (IOException e) {
                code = 0;
                e.printStackTrace();
            }

            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                code = Integer.parseInt(myJson.optString("code"));
                isNewUserStr = Integer.parseInt(myJson.optString("is_newuser"));
            }

        } catch (JSONException e) {
            code = 0;
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onCheckFbUserDetailsAsynPreExecuteStarted();

        code = 0;
        if (!PACKAGE_NAME.equals(CommonConstants.user_Package_Name_At_Api)) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onCheckFbUserDetailsAsynPostExecuteCompleted(code);
            return;
        }
        if (CommonConstants.hashKey.equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onCheckFbUserDetailsAsynPostExecuteCompleted(code);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onCheckFbUserDetailsAsynPostExecuteCompleted(code);
    }
}
