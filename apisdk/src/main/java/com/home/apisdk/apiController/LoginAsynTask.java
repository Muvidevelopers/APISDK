package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.Login_input;
import com.home.apisdk.apiModel.Login_output;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Muvi on 12/16/2016.
 * Class to get Login details.
 */
public class LoginAsynTask extends AsyncTask<Login_input, Void, Void> {

    private Login_input login_input;
    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private LoinDetailsListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a LoginAsynTask to run some code when get
     * responses.
     */

    public interface LoinDetailsListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onLoginPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param login_output
         * @param status
         * @param message
         */

        void onLoginPostExecuteCompleted(Login_output login_output, int status, String message);
    }

    Login_output login_output = new Login_output();

    /**
     * Constructor to initialise the private data members.
     *
     * @param login_input
     * @param listener
     * @param context
     */

    public LoginAsynTask(Login_input login_input, LoinDetailsListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.login_input = login_input;
        Log.v("MUVISDK", "LoginAsynTask");
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);

    }

    @Override
    protected Void doInBackground(Login_input... params) {


        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getLoginUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.login_input.getAuthToken());
            httppost.addHeader(HeaderConstants.EMAIL, this.login_input.getEmail());
            httppost.addHeader(HeaderConstants.PASSWORD, this.login_input.getPassword());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.login_input.getLang_code());
            httppost.addHeader(HeaderConstants.DEVICE_ID, this.login_input.getDevice_id());
            httppost.addHeader(HeaderConstants.GOOGLE_ID, this.login_input.getGoogle_id());
            httppost.addHeader(HeaderConstants.DEVICE_TYPE, "1");

            // Execute HTTP Post Request
            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());


            } catch (org.apache.http.conn.ConnectTimeoutException e) {

                status = 0;
                message = "Error";


            } catch (IOException e) {
                status = 0;
                message = "Error";
            }

            JSONObject mainJson = null;
            if (responseStr != null) {
                mainJson = new JSONObject(responseStr);
                status = Integer.parseInt(mainJson.optString("code"));


                if ((mainJson.has("email")) && mainJson.optString("email").trim() != null && !mainJson.optString("email").trim().isEmpty() && !mainJson.optString("email").trim().equals("null") && !mainJson.optString("email").trim().matches("")) {
                    login_output.setEmail(mainJson.optString("email"));
                } else {
                    login_output.setEmail("");

                }
                if ((mainJson.has("display_name")) && mainJson.optString("display_name").trim() != null && !mainJson.optString("display_name").trim().isEmpty() && !mainJson.optString("display_name").trim().equals("null") && !mainJson.optString("display_name").trim().matches("")) {
                    String hh = mainJson.optString("display_name");
                    login_output.setDisplay_name(mainJson.optString("display_name"));


                } else {
                    login_output.setDisplay_name("");

                }
                if ((mainJson.has("profile_image")) && mainJson.optString("profile_image").trim() != null && !mainJson.optString("profile_image").trim().isEmpty() && !mainJson.optString("profile_image").trim().equals("null") && !mainJson.optString("profile_image").trim().matches("")) {
                    login_output.setProfile_image(mainJson.optString("profile_image"));


                } else {
                    login_output.setProfile_image("");

                }
                if ((mainJson.has("isSubscribed")) && mainJson.optString("isSubscribed").trim() != null && !mainJson.optString("isSubscribed").trim().isEmpty() && !mainJson.optString("isSubscribed").trim().equals("null") && !mainJson.optString("isSubscribed").trim().matches("")) {
                    login_output.setIsSubscribed(mainJson.optString("story"));
                } else {
                    login_output.setIsSubscribed("");

                }
                if ((mainJson.has("nick_name")) && mainJson.optString("nick_name").trim() != null && !mainJson.optString("nick_name").trim().isEmpty() && !mainJson.optString("nick_name").trim().equals("null") && !mainJson.optString("nick_name").trim().matches("")) {
                    login_output.setNick_name(mainJson.optString("nick_name"));
                } else {
                    login_output.setNick_name("");

                }

                if ((mainJson.has("studio_id")) && mainJson.optString("studio_id").trim() != null && !mainJson.optString("studio_id").trim().isEmpty() && !mainJson.optString("studio_id").trim().equals("null") && !mainJson.optString("studio_id").trim().matches("")) {
                    login_output.setStudio_id(mainJson.optString("studio_id"));

                } else {
                    login_output.setStudio_id("");

                }

                if ((mainJson.has("msg")) && mainJson.optString("msg").trim() != null && !mainJson.optString("msg").trim().isEmpty() && !mainJson.optString("msg").trim().equals("null") && !mainJson.optString("msg").trim().matches("")) {
                    login_output.setMsg(mainJson.optString("msg"));
                } else {
                    login_output.setMsg("");

                }
                if ((mainJson.has("login_history_id")) && mainJson.optString("login_history_id").trim() != null && !mainJson.optString("login_history_id").trim().isEmpty() && !mainJson.optString("login_history_id").trim().equals("null") && !mainJson.optString("login_history_id").trim().matches("")) {
                    login_output.setLogin_history_id(mainJson.optString("login_history_id"));
                } else {
                    login_output.setLogin_history_id("");

                }
                if ((mainJson.has("id")) && mainJson.optString("id").trim() != null && !mainJson.optString("id").trim().isEmpty() && !mainJson.optString("id").trim().equals("null") && !mainJson.optString("id").trim().matches("")) {
                    login_output.setId(mainJson.optString("id"));
                } else {
                    login_output.setId("");

                }

            } else {
                responseStr = "0";
                status = 0;
                message = "Error";
            }
        } catch (final JSONException e1) {

            responseStr = "0";
            status = 0;
            message = "Error";
        } catch (Exception e) {

            responseStr = "0";
            status = 0;
            message = "Error";
        }
        return null;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onLoginPreExecuteStarted();

        status = 0;
        if (!PACKAGE_NAME.equals(HeaderConstants.user_Package_Name_At_Api)) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onLoginPostExecuteCompleted(login_output, status, message);
            return;
        }
        if (HeaderConstants.hashKey.equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onLoginPostExecuteCompleted(login_output, status, message);
        }

    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onLoginPostExecuteCompleted(login_output, status, message);

    }

}
