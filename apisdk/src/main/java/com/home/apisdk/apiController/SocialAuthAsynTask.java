/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.SocialAuthInputModel;
import com.home.apisdk.apiModel.SocialAuthOutputModel;

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
 * This Class authenticate users facebook details and help them to login using Facebook.
 *
 * @author MUVI
 */

public class SocialAuthAsynTask extends AsyncTask<SocialAuthInputModel, Void, Void> {

    private SocialAuthInputModel socialAuthInput;
    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private SocialAuthListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a SocialAuthAsynTask to run some code when get
     * responses.
     */

    public interface SocialAuthListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onSocialAuthPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param socialAuthOutput A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status           Response Code from the server
         * @param message          On Success Message
         */

        void onSocialAuthPostExecuteCompleted(SocialAuthOutputModel socialAuthOutput, int status, String message);
    }

    SocialAuthOutputModel socialAuthOutput = new SocialAuthOutputModel();

    /**
     * Constructor to initialise the private data members.
     *
     * @param socialAuthInput A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                        For Example: to use this API we have to set following attributes:
     *                        setAuthToken(),setEmail() etc.
     * @param listener        SocialAuthListener
     * @param context         android.content.Context
     */

    public SocialAuthAsynTask(SocialAuthInputModel socialAuthInput, SocialAuthListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.socialAuthInput = socialAuthInput;
        Log.v("MUVISDK", "LoginAsynTask");
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);

    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException,JSONException
     */

    @Override
    protected Void doInBackground(SocialAuthInputModel... params) {


        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getSocialauthUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.socialAuthInput.getAuthToken());
            httppost.addHeader(HeaderConstants.EMAIL, this.socialAuthInput.getEmail());
            httppost.addHeader(HeaderConstants.PASSWORD, this.socialAuthInput.getPassword());
            httppost.addHeader(HeaderConstants.NAME, this.socialAuthInput.getName());
            httppost.addHeader(HeaderConstants.FB_USER_ID, this.socialAuthInput.getFb_userid());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.socialAuthInput.getLanguage());
            httppost.addHeader(HeaderConstants.DEVICE_ID, this.socialAuthInput.getDevice_id());
            httppost.addHeader(HeaderConstants.DEVICE_TYPE, this.socialAuthInput.getDevice_type());

            // Execute HTTP Post Request
            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());


            } catch (org.apache.http.conn.ConnectTimeoutException e) {

                status = 0;
                message = "Error";
                Log.v("MUVISDK", "ConnectTimeoutException" + e.toString());


            } catch (IOException e) {
                status = 0;
                message = "Error";
                Log.v("MUVISDK", "IOException" + e.toString());

            }

            JSONObject mainJson = null;
            if (responseStr != null) {
                mainJson = new JSONObject(responseStr);
                status = Integer.parseInt(mainJson.optString("code"));


                if ((mainJson.has("email")) && mainJson.optString("email").trim() != null && !mainJson.optString("email").trim().isEmpty() && !mainJson.optString("email").trim().equals("null") && !mainJson.optString("email").trim().matches("")) {
                    socialAuthOutput.setEmail(mainJson.optString("email"));
                } else {
                    socialAuthOutput.setEmail("");

                }
                if ((mainJson.has("display_name")) && mainJson.optString("display_name").trim() != null && !mainJson.optString("display_name").trim().isEmpty() && !mainJson.optString("display_name").trim().equals("null") && !mainJson.optString("display_name").trim().matches("")) {

                    socialAuthOutput.setDisplay_name(mainJson.optString("display_name"));


                } else {
                    socialAuthOutput.setDisplay_name("");

                }
                if ((mainJson.has("profile_image")) && mainJson.optString("profile_image").trim() != null && !mainJson.optString("profile_image").trim().isEmpty() && !mainJson.optString("profile_image").trim().equals("null") && !mainJson.optString("profile_image").trim().matches("")) {
                    socialAuthOutput.setProfile_image(mainJson.optString("profile_image"));


                } else {
                    socialAuthOutput.setProfile_image("");

                }
                if ((mainJson.has("isSubscribed")) && mainJson.optString("isSubscribed").trim() != null && !mainJson.optString("isSubscribed").trim().isEmpty() && !mainJson.optString("isSubscribed").trim().equals("null") && !mainJson.optString("isSubscribed").trim().matches("")) {
                    socialAuthOutput.setIsSubscribed(mainJson.optString("isSubscribed"));
                } else {
                    socialAuthOutput.setIsSubscribed("");

                }
                if ((mainJson.has("nick_name")) && mainJson.optString("nick_name").trim() != null && !mainJson.optString("nick_name").trim().isEmpty() && !mainJson.optString("nick_name").trim().equals("null") && !mainJson.optString("nick_name").trim().matches("")) {
                    socialAuthOutput.setNick_name(mainJson.optString("nick_name"));
                } else {
                    socialAuthOutput.setNick_name("");

                }

                if ((mainJson.has("studio_id")) && mainJson.optString("studio_id").trim() != null && !mainJson.optString("studio_id").trim().isEmpty() && !mainJson.optString("studio_id").trim().equals("null") && !mainJson.optString("studio_id").trim().matches("")) {
                    socialAuthOutput.setStudio_id(mainJson.optString("studio_id"));

                } else {
                    socialAuthOutput.setStudio_id("");

                }

                if ((mainJson.has("msg")) && mainJson.optString("msg").trim() != null && !mainJson.optString("msg").trim().isEmpty() && !mainJson.optString("msg").trim().equals("null") && !mainJson.optString("msg").trim().matches("")) {
                    socialAuthOutput.setMsg(mainJson.optString("msg"));
                } else {
                    socialAuthOutput.setMsg("");

                }
                if ((mainJson.has("login_history_id")) && mainJson.optString("login_history_id").trim() != null && !mainJson.optString("login_history_id").trim().isEmpty() && !mainJson.optString("login_history_id").trim().equals("null") && !mainJson.optString("login_history_id").trim().matches("")) {
                    socialAuthOutput.setLogin_history_id(mainJson.optString("login_history_id"));
                } else {
                    socialAuthOutput.setLogin_history_id("");

                }
                if ((mainJson.has("id")) && mainJson.optString("id").trim() != null && !mainJson.optString("id").trim().isEmpty() && !mainJson.optString("id").trim().equals("null") && !mainJson.optString("id").trim().matches("")) {
                    socialAuthOutput.setId(mainJson.optString("id"));
                } else {
                    socialAuthOutput.setId("");

                }


            } else {
                responseStr = "0";
                status = 0;
                message = "Error";
            }
        } catch (final JSONException e1) {

            Log.v("MUVISDK", "IOException" + e1.toString());

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
        listener.onSocialAuthPreExecuteStarted();

        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onSocialAuthPostExecuteCompleted(socialAuthOutput, status, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onSocialAuthPostExecuteCompleted(socialAuthOutput, status, message);
        }

    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onSocialAuthPostExecuteCompleted(socialAuthOutput, status, message);

    }

}
