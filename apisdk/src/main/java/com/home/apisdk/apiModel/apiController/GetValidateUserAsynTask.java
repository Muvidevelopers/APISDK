package com.home.apisdk.apiModel.apiController;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.apiModel.APIUrlConstant;
import com.home.apisdk.apiModel.CommonConstants;
import com.home.apisdk.apiModel.apiModel.ValidateUserInput;
import com.home.apisdk.apiModel.apiModel.ValidateUserOutput;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Muvi on 12/16/2016.
 */
public class GetValidateUserAsynTask extends AsyncTask<ValidateUserInput, Void, Void> {
    ValidateUserInput validateUserInput;

    String responseStr;
    int status;
    String message,PACKAGE_NAME;
    String validuser_str,isSubscribed;
    public interface GetValidateUser{
        void onGetValidateUserPreExecuteStarted();
        void onGetValidateUserPostExecuteCompleted(ValidateUserOutput validateUserOutput, int status, String message);
    }
   /* public class GetContentListAsync extends AsyncTask<Void, Void, Void> {*/

    private GetValidateUser listener;
    private Context context;
    ValidateUserOutput validateUserOutput=new ValidateUserOutput();

    public GetValidateUserAsynTask(ValidateUserInput validateUserInput, GetValidateUser listener, Context context) {
        this.listener = listener;
        this.context=context;

        this.validateUserInput = validateUserInput;
        PACKAGE_NAME=context.getPackageName();

    }
    @Override
    protected Void doInBackground(ValidateUserInput... params) {


        try {

            // Execute HTTP Post Request
            try {
                URL url = new URL(APIUrlConstant.getValidateUserForContentUrl());
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(20000);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                Log.v("MUVISDK", "this.validateUserInput.getUserId()" + this.validateUserInput.getUserId());
                Log.v("MUVISDK","this.validateUserInput.getUserId()"+this.validateUserInput.getMuviUniqueId());
                Log.v("MUVISDK","this.validateUserInput.getUserId()"+this.validateUserInput.getAuthToken());

                Log.v("MUVISDK","this.validateUserInput.getUserId()"+this.validateUserInput.getEpisodeStreamUniqueId());
                Log.v("MUVISDK","this.validateUserInput.getUserId()"+this.validateUserInput.getSeasonId());
                Log.v("MUVISDK","this.validateUserInput.getUserId()"+this.validateUserInput.getLanguageCode());
                Log.v("MUVISDK","this.validateUserInput.getUserId()"+this.validateUserInput.getPurchaseType());

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter(CommonConstants.AUTH_TOKEN, this.validateUserInput.getAuthToken())
                        .appendQueryParameter(CommonConstants.USER_ID, this.validateUserInput.getUserId())
                        .appendQueryParameter(CommonConstants.MOVIE_ID, this.validateUserInput.getMuviUniqueId())
                        .appendQueryParameter(CommonConstants.EPISODE_ID, this.validateUserInput.getEpisodeStreamUniqueId())
                        .appendQueryParameter(CommonConstants.SEASON_ID, this.validateUserInput.getSeasonId())
                        .appendQueryParameter(CommonConstants.LANG_CODE, this.validateUserInput.getLanguageCode())
                        .appendQueryParameter(CommonConstants.PURCHASE_TYPE, this.validateUserInput.getPurchaseType());
                String query = builder.build().getEncodedQuery();

                Log.v("MUVISDK", "authToken" +this.validateUserInput.getAuthToken());
                Log.v("MUVISDK", "user_id" +this.validateUserInput.getUserId());
                Log.v("MUVISDK", "movie_id" +this.validateUserInput.getMuviUniqueId());
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();



                int responseCode = conn.getResponseCode();
                if (responseCode != HttpsURLConnection.HTTP_OK) {
                    final InputStream err = conn.getErrorStream();
                    try {
                    } finally {

                        InputStreamReader isr = new InputStreamReader(err);
                        BufferedReader in = new BufferedReader(isr);

                        String inputLine;

                        while ((inputLine = in.readLine()) != null) {
                            System.out.println(inputLine);
                            responseStr = inputLine;
                            Log.v("MUVISDK", "responseStr" +responseStr);

                        }
                        in.close();
                        err.close();
                    }
                }else{
                    InputStream ins = conn.getInputStream();

                    InputStreamReader isr = new InputStreamReader(ins);
                    BufferedReader in = new BufferedReader(isr);

                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        System.out.println(inputLine);
                        responseStr = inputLine;
                        Log.v("MUVISDK", "responseStr" +responseStr);

                    }
                    in.close();
                }



            } catch (org.apache.http.conn.ConnectTimeoutException e){

                status = 0;
                message = "Error";
                Log.v("MUVISDK", "ConnectTimeoutException" +e);



            }catch (IOException e) {
                status = 0;
                message = "Error";
                Log.v("MUVISDK", "IOException" +e);

            }

            JSONObject mainJson =null;
            if(responseStr!=null) {
                mainJson = new JSONObject(responseStr);
                status = Integer.parseInt(mainJson.optString("code"));
                if ((mainJson.has("msg")) && mainJson.optString("msg").trim() != null && !mainJson.optString("msg").trim().isEmpty() && !mainJson.optString("msg").trim().equals("null") && !mainJson.optString("msg").trim().matches("")) {
                    validateUserOutput.setMessage(mainJson.optString("msg"));
                    message = mainJson.optString("msg");

                }
                if ((mainJson.has("member_subscribed")) && mainJson.optString("member_subscribed").trim() != null && !mainJson.optString("member_subscribed").trim().isEmpty() && !mainJson.optString("member_subscribed").trim().equals("null") && !mainJson.optString("member_subscribed").trim().matches("")) {
                    validateUserOutput.setIsMemberSubscribed(mainJson.optString("member_subscribed"));
                    isSubscribed=mainJson.optString("member_subscribed");

                }

                if ((mainJson.has("status")) && mainJson.optString("status").trim() != null && !mainJson.optString("status").trim().isEmpty() && !mainJson.optString("status").trim().equals("null") && !mainJson.optString("status").trim().matches("")) {
                    validateUserOutput.setValiduser_str(mainJson.optString("status"));
                    validuser_str = mainJson.optString("status");

                }
            }

            else{
                responseStr = "0";
                status = 0;
                message = "Error";
            }
        } catch (final JSONException e1) {
            Log.v("MUVISDK", "JSONException" +e1);

            responseStr = "0";
            status = 0;
            message = "Error";            }

        catch (Exception e)
        {
            Log.v("MUVISDK", "Exception" +e);

            responseStr = "0";
            status = 0;
            message = "Error";
        }
        return null;


    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onGetValidateUserPreExecuteStarted();

        status = 0;
        if(!PACKAGE_NAME.equals(CommonConstants.user_Package_Name_At_Api))
        {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetValidateUserPostExecuteCompleted(validateUserOutput, status, message);
            return;
        }
        if(CommonConstants.hashKey.equals(""))
        {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetValidateUserPostExecuteCompleted(validateUserOutput, status, message);
        }

    }



    @Override
    protected void onPostExecute(Void result) {
        listener.onGetValidateUserPostExecuteCompleted(validateUserOutput, status, message);

    }

}
