package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.CommonConstants;
import com.home.apisdk.apiModel.ViewFavouriteInputModel;
import com.home.apisdk.apiModel.ViewFavouriteOutputModel;

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
 * Created by User on 12-12-2016.
 */
public class ViewFavouriteAsynTask extends AsyncTask<ViewFavouriteInputModel, Void, Void> {

    ViewFavouriteInputModel viewFavouriteInputModel;
    String responseStr;
    int status;
    int totalItems;
    String message, PACKAGE_NAME;

    public interface ViewFavouriteListener {
        void onViewFavouritePreExecuteStarted();

        void onViewFavouritePostExecuteCompleted(ArrayList<ViewFavouriteOutputModel> viewFavouriteOutputModelArray, int status, int totalItems, String message);
    }
   /* public class GetContentListAsync extends AsyncTask<Void, Void, Void> {*/

    private ViewFavouriteListener listener;
    private Context context;
    ArrayList<ViewFavouriteOutputModel> viewFavouriteOutputModel = new ArrayList<ViewFavouriteOutputModel>();

    public ViewFavouriteAsynTask(ViewFavouriteInputModel viewFavouriteInputModel, ViewFavouriteListener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.viewFavouriteInputModel = viewFavouriteInputModel;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "GetContentListAsynTask");

    }

    @Override
    protected Void doInBackground(ViewFavouriteInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getViewFavorite());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(CommonConstants.AUTH_TOKEN, this.viewFavouriteInputModel.getAuthToken());

            httppost.addHeader(CommonConstants.USER_ID,this.viewFavouriteInputModel.getUser_id());

            // Execute HTTP Post Request
            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.v("MUVISDK", "RES" + responseStr);

            } catch (org.apache.http.conn.ConnectTimeoutException e) {
                status = 0;
                totalItems = 0;
                message = "";

            } catch (IOException e) {
                status = 0;
                totalItems = 0;
                message = "";
            }

            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                status = Integer.parseInt(myJson.optString("status"));
                totalItems = Integer.parseInt(myJson.optString("item_count"));
                message = myJson.optString("msg");
            }


            if (status == 200) {

                JSONArray jsonMainNode = myJson.getJSONArray("movieList");

                int lengthJsonArr = jsonMainNode.length();
                for (int i = 0; i < lengthJsonArr; i++) {
                    JSONObject jsonChildNode;
                    try {
                        jsonChildNode = jsonMainNode.getJSONObject(i);
                        ViewFavouriteOutputModel content = new ViewFavouriteOutputModel();

                        if ((jsonChildNode.has("movie_uniq_id")) && jsonChildNode.optString("movie_uniq_id").trim() != null && !jsonChildNode.optString("movie_uniq_id").trim().isEmpty() && !jsonChildNode.optString("movie_uniq_id").trim().equals("null") && !jsonChildNode.optString("movie_uniq_id").trim().matches("")) {
                            content.setMovieId(jsonChildNode.optString("movie_uniq_id"));
                        }

                        if ((jsonChildNode.has("title")) && jsonChildNode.optString("title").trim() != null && !jsonChildNode.optString("title").trim().isEmpty() && !jsonChildNode.optString("title").trim().equals("null") && !jsonChildNode.optString("title").trim().matches("")) {
                            content.setTitle(jsonChildNode.optString("title"));
                        }
                        if ((jsonChildNode.has("poster")) && jsonChildNode.optString("poster").trim() != null && !jsonChildNode.optString("poster").trim().isEmpty() && !jsonChildNode.optString("poster").trim().equals("null") && !jsonChildNode.optString("poster").trim().matches("")) {
                            content.setPoster(jsonChildNode.optString("poster"));

                        }
                        if ((jsonChildNode.has("permalink")) && jsonChildNode.optString("permalink").trim() != null && !jsonChildNode.optString("permalink").trim().isEmpty() && !jsonChildNode.optString("permalink").trim().equals("null") && !jsonChildNode.optString("permalink").trim().matches("")) {
                            content.setPermalink(jsonChildNode.optString("permalink"));
                        }
                        if ((jsonChildNode.has("content_types_id")) && jsonChildNode.optString("content_types_id").trim() != null && !jsonChildNode.optString("content_types_id").trim().isEmpty() && !jsonChildNode.optString("content_types_id").trim().equals("null") && !jsonChildNode.optString("content_types_id").trim().matches("")) {
                            content.setContentTypesId(jsonChildNode.optString("content_types_id"));

                        }
                        if ((jsonChildNode.has("is_episode")) && jsonChildNode.optString("is_episode").trim() != null && !jsonChildNode.optString("is_episode").trim().isEmpty() && !jsonChildNode.optString("is_episode").trim().equals("null") && !jsonChildNode.optString("is_episode").trim().matches("")) {
                            content.setIsEpisodeStr(jsonChildNode.optString("is_episode"));

                        }
                        viewFavouriteOutputModel.add(content);
                    } catch (Exception e) {
                        status = 0;
                        totalItems = 0;
                        message = "";
                    }
                }
            }

        } catch (Exception e) {
            status = 0;
            totalItems = 0;
            message = "";
        }
        return null;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onViewFavouritePreExecuteStarted();
        responseStr = "0";
        status = 0;
        if (!PACKAGE_NAME.equals(CommonConstants.user_Package_Name_At_Api)) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onViewFavouritePostExecuteCompleted(viewFavouriteOutputModel, status, totalItems, message);
            return;
        }
        if (CommonConstants.hashKey.equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onViewFavouritePostExecuteCompleted(viewFavouriteOutputModel, status, totalItems, message);
        }

    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onViewFavouritePostExecuteCompleted(viewFavouriteOutputModel, status, totalItems, message);

    }

    //}
}
