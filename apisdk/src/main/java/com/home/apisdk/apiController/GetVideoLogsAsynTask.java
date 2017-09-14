/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.VideoLogsInputModel;
import com.home.apisdk.apiModel.Video_Log_Output_Model;

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
 * Class to get Video Logs details.
 */
public class GetVideoLogsAsynTask extends AsyncTask<VideoLogsInputModel, Void, Void> {

    private VideoLogsInputModel videoLogsInput;
    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private String videoLogId = "";
    private GetVideoLogsListener listener;
    private Video_Log_Output_Model video_log_output;
    private Context context;

    /**
     * Interface used to allow the caller of a GetVideoLogsAsynTask to run some code when get
     * responses.
     */

    public interface GetVideoLogsListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onGetVideoLogsPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param video_log_output A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status           Response Code From The Server
         * @param message          On Success Message
         */

        void onGetVideoLogsPostExecuteCompleted(Video_Log_Output_Model video_log_output, int status, String message);
    }

    /**
     * Constructor to initialise the private data members.
     *
     * @param videoLogsInput A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                       For Example: to use this API we have to set following attributes:
     *                       setAuthToken(),setUserId() etc.
     * @param listener       GetVideoLogs Listener
     * @param context        android.content.Context
     */

    public GetVideoLogsAsynTask(VideoLogsInputModel videoLogsInput, GetVideoLogsListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.videoLogsInput = videoLogsInput;
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
    protected Void doInBackground(VideoLogsInputModel... params) {


        try {

            // Execute HTTP Post Request
            try {
                URL url = new URL(APIUrlConstant.getVideoLogsUrl());
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter(HeaderConstants.AUTH_TOKEN, this.videoLogsInput.getAuthToken())
                        .appendQueryParameter(HeaderConstants.USER_ID, this.videoLogsInput.getUserId())
                        .appendQueryParameter(HeaderConstants.IP_ADDRESS, this.videoLogsInput.getIpAddress())
                        .appendQueryParameter(HeaderConstants.MOVIE_ID, this.videoLogsInput.getMuviUniqueId())
                        .appendQueryParameter(HeaderConstants.EPISODE_ID, this.videoLogsInput.getEpisodeStreamUniqueId())
                        .appendQueryParameter(HeaderConstants.PLAYED_LENGTH, this.videoLogsInput.getPlayedLength())
                        .appendQueryParameter(HeaderConstants.WATCH_STATUS, this.videoLogsInput.getWatchStatus())
                        .appendQueryParameter(HeaderConstants.DEVICE_TYPE, this.videoLogsInput.getDeviceType())
                        .appendQueryParameter(HeaderConstants.LOG_ID, this.videoLogsInput.getVideoLogId())
                        .appendQueryParameter(HeaderConstants.IS_STREAMING_RESTRICTION, this.videoLogsInput.getIs_streaming_restriction())
                        .appendQueryParameter(HeaderConstants.RESTRICT_STREAM_ID, this.videoLogsInput.getRestrict_stream_id());
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

            if (responseStr != null) {
                JSONObject mainJson = new JSONObject(responseStr);
                status = Integer.parseInt(mainJson.optString("code"));

                if (status == 200) {

                    video_log_output.setRestrict_stream_id(mainJson.optString("restrict_stream_id"));
                    video_log_output.setVideoLogId(mainJson.optString("log_id"));

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
        listener.onGetVideoLogsPreExecuteStarted();

        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetVideoLogsPostExecuteCompleted(video_log_output, status, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetVideoLogsPostExecuteCompleted(video_log_output, status, message);
        }

    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onGetVideoLogsPostExecuteCompleted(video_log_output, status, message);

    }

}
