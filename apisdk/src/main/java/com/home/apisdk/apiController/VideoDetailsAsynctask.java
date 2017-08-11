package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.HeaderConstants;
import com.home.apisdk.apiModel.GetVideoDetailsInput;
import com.home.apisdk.apiModel.Get_Video_Details_Output;

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
 * Class to get Video details.
 */

public class VideoDetailsAsynctask extends AsyncTask<GetVideoDetailsInput, Void, Void> {

    private GetVideoDetailsInput getVideoDetailsInput;
    private ArrayList<String> SubTitleName = new ArrayList<>();
    private ArrayList<String> SubTitlePath = new ArrayList<>();
    private ArrayList<String> FakeSubTitlePath = new ArrayList<>();
    private ArrayList<String> ResolutionFormat = new ArrayList<>();
    private ArrayList<String> ResolutionUrl = new ArrayList<>();
    private String PACKAGE_NAME;
    private String message;
    private String responseStr;
    private String status;
    private JSONArray SubtitleJosnArray = null;
    private JSONArray ResolutionJosnArray = null;
    private int code;
    private Get_Video_Details_Output get_video_details_output;
    private VideoDetailsListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a VideoDetailsAsynctask to run some code when get
     * responses.
     */

    public interface VideoDetailsListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onVideoDetailsPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param get_video_details_output
         * @param code
         * @param status
         * @param message
         */

        void onVideoDetailsPostExecuteCompleted(Get_Video_Details_Output get_video_details_output, int code, String status, String message);
    }

    /**
     * Constructor to initialise the private data members.
     *
     * @param getVideoDetailsInput
     * @param listener
     * @param context
     */


    public VideoDetailsAsynctask(GetVideoDetailsInput getVideoDetailsInput, VideoDetailsListener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.getVideoDetailsInput = getVideoDetailsInput;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "VideoDetailsAsynctask");

    }

    @Override
    protected Void doInBackground(GetVideoDetailsInput... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getVideoDetailsUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.getVideoDetailsInput.getAuthToken());
            httppost.addHeader(HeaderConstants.CONTENT_UNIQ_ID, this.getVideoDetailsInput.getContent_uniq_id());
            httppost.addHeader(HeaderConstants.STREAM_UNIQ_ID, this.getVideoDetailsInput.getStream_uniq_id());
            httppost.addHeader(HeaderConstants.INTERNET_SPEED, this.getVideoDetailsInput.getInternetSpeed());
            httppost.addHeader(HeaderConstants.USER_ID, this.getVideoDetailsInput.getUser_id());

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
            JSONArray SubtitleJosnArray = null;
            JSONArray ResolutionJosnArray = null;
            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                code = Integer.parseInt(myJson.optString("code"));
                message = myJson.optString("msg");
                SubtitleJosnArray = myJson.optJSONArray("subTitle");
                ResolutionJosnArray = myJson.optJSONArray("videoDetails");
                status = myJson.optString("status");
            }

            if (code == 200) {
                try {
                    get_video_details_output = new Get_Video_Details_Output();
                    get_video_details_output.setVideoResolution(myJson.optString("videoResolution"));
                    get_video_details_output.setVideoUrl(myJson.optString("videoUrl"));
                    get_video_details_output.setEmed_url(myJson.optString("emed_url"));
                    get_video_details_output.setPlayed_length(myJson.optString("played_length"));
                    get_video_details_output.setThirdparty_url(myJson.optString("thirdparty_url"));
                    get_video_details_output.setStudio_approved_url(myJson.optString("studio_approved_url"));
                    get_video_details_output.setLicenseUrl(myJson.optString("licenseUrl"));

                } catch (Exception e) {
                    code = 0;
                    message = "";
                    status = "";
                }
                if (SubtitleJosnArray != null) {
                    if (SubtitleJosnArray.length() > 0) {
                        for (int i = 0; i < SubtitleJosnArray.length(); i++) {
                            SubTitleName.add(SubtitleJosnArray.getJSONObject(i).optString("language").trim());
                            FakeSubTitlePath.add(SubtitleJosnArray.getJSONObject(i).optString("url").trim());


                        }

                        get_video_details_output.setSubTitleName(SubTitleName);
                        get_video_details_output.setFakeSubTitlePath(FakeSubTitlePath);
                    }
                }

                /******Resolution****/

                if (ResolutionJosnArray != null) {
                    if (ResolutionJosnArray.length() > 0) {
                        for (int i = 0; i < ResolutionJosnArray.length(); i++) {
                            if ((ResolutionJosnArray.getJSONObject(i).optString("resolution").trim()).equals("BEST")) {
                                ResolutionFormat.add(ResolutionJosnArray.getJSONObject(i).optString("resolution").trim());
                            } else {
                                ResolutionFormat.add((ResolutionJosnArray.getJSONObject(i).optString("resolution").trim()) + "p");
                            }

                            ResolutionUrl.add(ResolutionJosnArray.getJSONObject(i).optString("url").trim());

                            Log.v("MUVISDK", "Resolution Format Name =" + ResolutionJosnArray.getJSONObject(i).optString("resolution").trim());
                            Log.v("MUVISDK", "Resolution url =" + ResolutionJosnArray.getJSONObject(i).optString("url").trim());
                        }

                        get_video_details_output.setResolutionFormat(ResolutionFormat);
                        get_video_details_output.setResolutionUrl(ResolutionUrl);
                    }

                }

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
        listener.onVideoDetailsPreExecuteStarted();
        code = 0;
        status = "";
        if (!PACKAGE_NAME.equals(HeaderConstants.user_Package_Name_At_Api)) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onVideoDetailsPostExecuteCompleted(get_video_details_output, code, status, message);
            return;
        }
        if (HeaderConstants.hashKey.equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onVideoDetailsPostExecuteCompleted(get_video_details_output, code, status, message);
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        listener.onVideoDetailsPostExecuteCompleted(get_video_details_output, code, status, message);
    }
}
