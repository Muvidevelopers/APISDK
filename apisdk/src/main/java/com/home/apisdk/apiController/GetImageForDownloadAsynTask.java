package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.CommonConstants;
import com.home.apisdk.apiModel.GetImageForDownloadInputModel;
import com.home.apisdk.apiModel.GetImageForDownloadOutputModel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by User on 12-12-2016.
 */
public class GetImageForDownloadAsynTask extends AsyncTask<GetImageForDownloadInputModel, Void, Void> {

    GetImageForDownloadInputModel getImageForDownloadInputModel;
    String responseStr;
    int status;
    String message, PACKAGE_NAME;

    public interface GetImageForDownload {
        void onGetImageForDownloadPreExecuteStarted();

        void onGetImageForDownloadPostExecuteCompleted(GetImageForDownloadOutputModel getImageForDownloadOutputModel, int status, String message);
    }

    private GetImageForDownload listener;
    private Context context;
    GetImageForDownloadOutputModel getImageForDownloadOutputModel = new GetImageForDownloadOutputModel();

    public GetImageForDownloadAsynTask(GetImageForDownloadInputModel getImageForDownloadInputModel, GetImageForDownload listener, Context context) {
        this.listener = listener;
        this.context=context;


        this.getImageForDownloadInputModel = getImageForDownloadInputModel;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "getFeatureContentAsynTask");

    }

    @Override
    protected Void doInBackground(GetImageForDownloadInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getGetImageForDownloadUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(CommonConstants.AUTH_TOKEN, this.getImageForDownloadInputModel.getAuthToken());


            // Execute HTTP Post Request
            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.v("MUVISDK", "RES" + responseStr);

            } catch (org.apache.http.conn.ConnectTimeoutException e) {
                status = 0;
                message = "";

            } catch (IOException e) {
                status = 0;
                message = "";
            }

            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                status = Integer.parseInt(myJson.optString("code"));
                message = myJson.optString("status");
            }else {
                status=0;
            }


            if (status == 200) {
                if ((myJson.has("image_url")) && myJson.optString("image_url").trim() != null && !myJson.optString("image_url").trim().isEmpty() && !myJson.optString("image_url").trim().equals("null") && !myJson.optString("image_url").trim().matches("")) {
                    getImageForDownloadOutputModel.setImageUrl(myJson.optString("image_url"));
                }

            }

        } catch (Exception e) {
            status = 0;
            message = "";
        }
        return null;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onGetImageForDownloadPreExecuteStarted();
        responseStr = "0";
        status = 0;
            if(!PACKAGE_NAME.equals(CommonConstants.user_Package_Name_At_Api))
            {
                this.cancel(true);
                message = "Packge Name Not Matched";
                listener.onGetImageForDownloadPostExecuteCompleted(getImageForDownloadOutputModel,status,message);
                return;
            }
            if(CommonConstants.hashKey.equals(""))
            {
                this.cancel(true);
                message = "Hash Key Is Not Available. Please Initialize The SDK";
                listener.onGetImageForDownloadPostExecuteCompleted(getImageForDownloadOutputModel,status,message);
            }


    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onGetImageForDownloadPostExecuteCompleted(getImageForDownloadOutputModel, status, message);

    }

    //}
}
