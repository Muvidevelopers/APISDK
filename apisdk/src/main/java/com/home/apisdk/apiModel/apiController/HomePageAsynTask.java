package com.home.apisdk.apiModel.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.apiModel.APIUrlConstant;
import com.home.apisdk.apiModel.CommonConstants;
import com.home.apisdk.apiModel.apiModel.HomePageBannerModel;
import com.home.apisdk.apiModel.apiModel.HomePageInputModel;
import com.home.apisdk.apiModel.apiModel.HomePageOutputModel;
import com.home.apisdk.apiModel.apiModel.HomePageSectionModel;
import com.home.apisdk.apiModel.apiModel.HomePageTextModel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by User on 12-12-2016.
 */
public class HomePageAsynTask extends AsyncTask<HomePageInputModel, Void, Void> {

    HomePageInputModel homePageInputModel;
    String responseStr;
    int status;
    String message, PACKAGE_NAME;

    public interface HomePage {
        void onHomePagePreExecuteStarted();

        void onHomePagePostExecuteCompleted(HomePageOutputModel homePageOutputModel, int status, String message);
    }
   /* public class GetContentListAsync extends AsyncTask<Void, Void, Void> {*/

    private HomePage listener;
    private Context context;
    HomePageOutputModel homePageOutputModel = new HomePageOutputModel();
    ArrayList<HomePageSectionModel> homePageSectionModelArrayList = new ArrayList<HomePageSectionModel>();
    ArrayList<HomePageBannerModel> homePageBannerModelArrayList = new ArrayList<HomePageBannerModel>();

    public HomePageAsynTask(HomePageInputModel homePageInputModel, HomePage listener, Context context) {
        this.listener = listener;
        this.context=context;

        this.homePageInputModel = homePageInputModel;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK","getPlanListAsynctask");
        Log.v("MUVISDK","authToken = "+ this.homePageInputModel.getAuthToken());


    }

    @Override
    protected Void doInBackground(HomePageInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getHomepageUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.addHeader(CommonConstants.AUTH_TOKEN, this.homePageInputModel.getAuthToken());
            httppost.addHeader(CommonConstants.LANG_CODE,this.homePageInputModel.getLang_code());

            Log.v("MUVISDK","authToken = "+ this.homePageInputModel.getAuthToken());

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

            Log.v("MUVISDK","response = "+ responseStr);
            JSONObject myJson = null;
            if (responseStr != null) {
                JSONObject bannerJson;
                JSONObject sectionJson;

                myJson = new JSONObject(responseStr);
                HomePageTextModel homePageTextModel = new HomePageTextModel();

                if (myJson.has("BannerSectionList")) {
                    bannerJson = new JSONObject(myJson.optString("BannerSectionList"));
                    status = Integer.parseInt(bannerJson.optString("code"));
                    message = myJson.optString("status");

                    if (status == 200) {
                        homePageTextModel.setText(bannerJson.optString("text"));
                        homePageTextModel.setJoin_btn_txt(bannerJson.optString("join_btn_txt"));

                        JSONArray jsonBannerImageNode = null;
                        try {
                            jsonBannerImageNode = bannerJson.getJSONArray("banners");


                            int lengthBannerImagesArray = jsonBannerImageNode.length();
                            if (lengthBannerImagesArray > 0) {
                                for (int i = 0; i < lengthBannerImagesArray; i++) {
                                    HomePageBannerModel homePageBannerModel = new HomePageBannerModel();
                                    homePageBannerModel.setMobile_view(jsonBannerImageNode.getJSONObject(i).optString("mobile_view"));
                                    homePageBannerModel.setOriginal(jsonBannerImageNode.getJSONObject(i).optString("original"));
                                    homePageBannerModelArrayList.add(homePageBannerModel);

                                }
                            }


                        } catch (Exception e) {
                            responseStr = "0";
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                }
                if (myJson.has("SectionName")) {
                    sectionJson = new JSONObject(myJson.optString("SectionName"));
                    if (status == 200) {



                        JSONArray jsonMainNode = sectionJson.getJSONArray("section");
                        int lengthJsonArr = jsonMainNode.length();
                        for (int i = 0; i < lengthJsonArr; i++) {

                            JSONObject jsonChildNode;
                            try {
                                jsonChildNode = jsonMainNode.getJSONObject(i);

                                HomePageSectionModel sectionModel = new HomePageSectionModel();

                                 if((jsonChildNode.has("studio_id")) && jsonChildNode.optString("studio_id").trim() != null && !jsonChildNode.optString("studio_id").trim().isEmpty() && !jsonChildNode.optString("studio_id").trim().equals("null") && !jsonChildNode.optString("studio_id").trim().matches("")) {
                                    sectionModel.setStudio_id(jsonChildNode.optString("studio_id"));

                                }
                                if ((jsonChildNode.has("language_id")) && jsonChildNode.optString("language_id").trim() != null && !jsonChildNode.optString("language_id").trim().isEmpty() && !jsonChildNode.optString("language_id").trim().equals("null") && !jsonChildNode.optString("language_id").trim().matches("")) {
                                    sectionModel.setLanguage_id(jsonChildNode.optString("language_id"));

                                }
                                if ((jsonChildNode.has("title")) && jsonChildNode.optString("title").trim() != null && !jsonChildNode.optString("title").trim().isEmpty() && !jsonChildNode.optString("title").trim().equals("null") && !jsonChildNode.optString("title").trim().matches("")) {
                                    sectionModel.setTitle(jsonChildNode.optString("title"));

                                }
                                if ((jsonChildNode.has("section_id")) && jsonChildNode.optString("section_id").trim() != null && !jsonChildNode.optString("section_id").trim().isEmpty() && !jsonChildNode.optString("section_id").trim().equals("null") && !jsonChildNode.optString("section_id").trim().matches("")) {
                                    sectionModel.setSection_id(jsonChildNode.optString("section_id"));

                                }

                                Log.v("MUVISDK","section_id = "+ sectionModel.getSection_id());

                                homePageSectionModelArrayList.add(sectionModel);
                            }
                            catch (Exception e) {
                                responseStr = "0";
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                          Log.v("MUVISDK","response123 = "+ responseStr);

                    }


                }

                homePageOutputModel.setHomePageBannerModel(homePageBannerModelArrayList);
                homePageOutputModel.setHomePageSectionModel(homePageSectionModelArrayList);
                homePageOutputModel.setHomePageTextModel(homePageTextModel);


            }

    }

    catch(
    final JSONException e1
    )

    {

        responseStr = "0";
        status = 0;
        message = "Error";
    }

    catch(
    Exception e
    )

    {

        responseStr = "0";
        status = 0;
        message = "Error";
    }

    return null;


}

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onHomePagePreExecuteStarted();
        status = 0;
        if (!PACKAGE_NAME.equals(CommonConstants.user_Package_Name_At_Api)) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onHomePagePostExecuteCompleted(homePageOutputModel, status, message);
            return;
        }
        if (CommonConstants.hashKey.equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onHomePagePostExecuteCompleted(homePageOutputModel, status, message);
        }
        listener.onHomePagePostExecuteCompleted(homePageOutputModel,status,message);
    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onHomePagePostExecuteCompleted(homePageOutputModel, status, message);

    }

    //}
}