package com.home.apisdk.apiController;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.CommonConstants;
import com.home.apisdk.apiModel.VideoBufferLogsInputModel;
import com.home.apisdk.apiModel.VideoBufferLogsOutputModel;

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
 * Created by MUVI on 7/5/2017.
 */

public class UpdateVideoBufferLogDetailsAsync extends AsyncTask<VideoBufferLogsInputModel, Void, Void> {
        VideoBufferLogsInputModel videoBufferLogsInputModel;
    String responseStr;
    int status;
    String message, PACKAGE_NAME;

    public interface UpdateVideoBufferLog {
        void onUpdateVideoBufferLogPreExecuteStarted();

        void onUpdateVideoBufferLogPostExecuteCompleted(VideoBufferLogsOutputModel videoBufferLogsOutputModel, int status, String message);
    }

    public UpdateVideoBufferLogDetailsAsync(VideoBufferLogsInputModel videoBufferLogsInputModel, UpdateVideoBufferLog listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.videoBufferLogsInputModel = videoBufferLogsInputModel;
        Log.v("MUVISDK", "LoginAsynTask");
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);

    }

    private UpdateVideoBufferLog listener;
    private Context context;
    VideoBufferLogsOutputModel videoBufferLogsOutputModel = new VideoBufferLogsOutputModel();
    @Override
    protected Void doInBackground(VideoBufferLogsInputModel... params) {
        try {

            // Execute HTTP Post Request
            try {
                URL url = new URL(APIUrlConstant.getUpdateBufferLogUrl());
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter(CommonConstants.AUTH_TOKEN, this.videoBufferLogsInputModel.getAuthToken())
                        .appendQueryParameter(CommonConstants.USER_ID, this.videoBufferLogsInputModel.getUserId())
                        .appendQueryParameter(CommonConstants.IP_ADDRESS, this.videoBufferLogsInputModel.getIpAddress())
                        .appendQueryParameter(CommonConstants.MOVIE_ID, this.videoBufferLogsInputModel.getMuviUniqueId())
                        .appendQueryParameter(CommonConstants.EPISODE_ID, this.videoBufferLogsInputModel.getEpisodeStreamUniqueId())
                        .appendQueryParameter(CommonConstants.LOG_ID, this.videoBufferLogsInputModel.getBufferLogId())
                        .appendQueryParameter(CommonConstants.RESOLUTION, this.videoBufferLogsInputModel.getVideoResolution())
                        .appendQueryParameter(CommonConstants.DEVICE_TYPE, this.videoBufferLogsInputModel.getDeviceType())
                        .appendQueryParameter(CommonConstants.START_TIME, this.videoBufferLogsInputModel.getBufferStartTime())
                        .appendQueryParameter(CommonConstants.END_TIME, this.videoBufferLogsInputModel.getBufferEndTime())
                        .appendQueryParameter(CommonConstants.LOG_UNIQUE_ID, this.videoBufferLogsInputModel.getBufferLogUniqueId())
                        .appendQueryParameter(CommonConstants.LOCATION, this.videoBufferLogsInputModel.getLocation());

                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                InputStream ins = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(ins);
                BufferedReader in = new BufferedReader(isr);

                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                    responseStr = inputLine;
                    Log.v("MUVISDK", "responseStr" + responseStr);

                }
                in.close();


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

                if (status == 200) {


                    if ((mainJson.has("log_id")) && mainJson.optString("log_id").trim() != null && !mainJson.optString("log_id").trim().isEmpty() && !mainJson.optString("log_id").trim().equals("null") && !mainJson.optString("log_id").trim().matches("")) {
                        videoBufferLogsOutputModel.setBufferLogId(mainJson.optString("log_id"));

                    }
                    if ((mainJson.has("log_unique_id")) && mainJson.optString("log_unique_id").trim() != null && !mainJson.optString("log_unique_id").trim().isEmpty() && !mainJson.optString("log_unique_id").trim().equals("null") && !mainJson.optString("log_unique_id").trim().matches("")) {
                        videoBufferLogsOutputModel.setBufferLogUniqueId(mainJson.optString("log_unique_id"));

                    }
                    if ((mainJson.has("location")) && mainJson.optString("location").trim() != null && !mainJson.optString("location").trim().isEmpty() && !mainJson.optString("location").trim().equals("null") && !mainJson.optString("location").trim().matches("")) {
                        videoBufferLogsOutputModel.setBufferLocation(mainJson.optString("location"));

                    }

                } else {
                    videoBufferLogsInputModel.setBufferLogUniqueId("0");
                    videoBufferLogsOutputModel.setBufferLogId("0");
                    videoBufferLogsOutputModel.setBufferLocation("0");
                }
            }
        } catch (Exception e) {
            videoBufferLogsInputModel.setBufferLogUniqueId("0");
            videoBufferLogsOutputModel.setBufferLogId("0");
            videoBufferLogsOutputModel.setBufferLocation("0");
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onUpdateVideoBufferLogPreExecuteStarted();
        status = 0;
        if (!PACKAGE_NAME.equals(CommonConstants.user_Package_Name_At_Api)) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onUpdateVideoBufferLogPostExecuteCompleted(videoBufferLogsOutputModel, status, message);
            return;
        }
        if (CommonConstants.hashKey.equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onUpdateVideoBufferLogPostExecuteCompleted(videoBufferLogsOutputModel, status, message);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onUpdateVideoBufferLogPostExecuteCompleted(videoBufferLogsOutputModel,status,message);
    }
}
