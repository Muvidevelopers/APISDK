package com.home.apisdk.apiModel.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.apiModel.APIUrlConstant;
import com.home.apisdk.apiModel.CommonConstants;
import com.home.apisdk.apiModel.apiModel.APVModel;
import com.home.apisdk.apiModel.apiModel.ContentDetailsInput;
import com.home.apisdk.apiModel.apiModel.ContentDetailsOutput;
import com.home.apisdk.apiModel.apiModel.CurrencyModel;
import com.home.apisdk.apiModel.apiModel.PPVModel;

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
 * Created by User on 12-12-2016.
 */
public class GetContentDetailsAsynTask extends AsyncTask<ContentDetailsInput, Void, Void> {

    ContentDetailsInput contentDetailsInput;
    String responseStr;
    int status;
    String message, PACKAGE_NAME;

    public interface GetContentDetails {
        void onGetContentDetailsPreExecuteStarted();

        void onGetContentDetailsPostExecuteCompleted(ContentDetailsOutput contentDetailsOutput, int status, String message);
    }
   /* public class GetContentListAsync extends AsyncTask<Void, Void, Void> {*/

    private GetContentDetails listener;
    private Context context;
    ContentDetailsOutput contentDetailsOutput = new ContentDetailsOutput();

    public GetContentDetailsAsynTask(ContentDetailsInput contentDetailsInput, GetContentDetails listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.contentDetailsInput = contentDetailsInput;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "GetContentListAsynTask");


    }

    @Override
    protected Void doInBackground(ContentDetailsInput... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getContentDetailsUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.addHeader(CommonConstants.AUTH_TOKEN, this.contentDetailsInput.getAuthToken());
            httppost.addHeader(CommonConstants.PERMALINK, this.contentDetailsInput.getPermalink());

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

            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                status = Integer.parseInt(myJson.optString("code"));
                message = myJson.optString("msg");
            }

            if (status > 0) {

                if (status == 200) {

                    JSONObject mainJson = myJson.getJSONObject("movie");
                    if ((mainJson.has("name")) && mainJson.optString("name").trim() != null && !mainJson.optString("name").trim().isEmpty() && !mainJson.optString("name").trim().equals("null") && !mainJson.optString("name").trim().matches("")) {
                        contentDetailsOutput.setName(mainJson.optString("name"));
                    } else {
                        contentDetailsOutput.setName("");

                    }
                    if ((mainJson.has("genre")) && mainJson.optString("genre").trim() != null && !mainJson.optString("genre").trim().isEmpty() && !mainJson.optString("genre").trim().equals("null") && !mainJson.optString("genre").trim().matches("")) {
                        contentDetailsOutput.setGenre(mainJson.optString("genre"));

                    } else {
                        contentDetailsOutput.setGenre("");

                    }
                    if ((mainJson.has("censor_rating")) && mainJson.optString("censor_rating").trim() != null && !mainJson.optString("censor_rating").trim().isEmpty() && !mainJson.optString("censor_rating").trim().equals("null") && !mainJson.optString("censor_rating").trim().matches("")) {
                        contentDetailsOutput.setCensorRating(mainJson.optString("censor_rating"));


                    } else {
                        contentDetailsOutput.setCensorRating("");

                    }
                    if ((mainJson.has("story")) && mainJson.optString("story").trim() != null && !mainJson.optString("story").trim().isEmpty() && !mainJson.optString("story").trim().equals("null") && !mainJson.optString("story").trim().matches("")) {
                        contentDetailsOutput.setStory(mainJson.optString("story"));
                    } else {
                        contentDetailsOutput.setStory("");

                    }
                    if ((mainJson.has("trailerUrl")) && mainJson.optString("trailerUrl").trim() != null && !mainJson.optString("trailerUrl").trim().isEmpty() && !mainJson.optString("trailerUrl").trim().equals("null") && !mainJson.optString("trailerUrl").trim().matches("")) {
                        contentDetailsOutput.setTrailerUrl(mainJson.optString("trailerUrl"));
                    } else {
                        contentDetailsOutput.setTrailerUrl("");

                    }

                    if ((mainJson.has("movie_stream_uniq_id")) && mainJson.optString("movie_stream_uniq_id").trim() != null && !mainJson.optString("movie_stream_uniq_id").trim().isEmpty() && !mainJson.optString("movie_stream_uniq_id").trim().equals("null") && !mainJson.optString("movie_stream_uniq_id").trim().matches("")) {
                        contentDetailsOutput.setMovieStreamUniqId(mainJson.optString("movie_stream_uniq_id"));

                    } else {
                        contentDetailsOutput.setMovieStreamUniqId("");

                    }

                    if ((mainJson.has("muvi_uniq_id")) && mainJson.optString("muvi_uniq_id").trim() != null && !mainJson.optString("muvi_uniq_id").trim().isEmpty() && !mainJson.optString("muvi_uniq_id").trim().equals("null") && !mainJson.optString("muvi_uniq_id").trim().matches("")) {
                        contentDetailsOutput.setMuviUniqId(mainJson.optString("movie_stream_uniq_id"));
                    } else {
                        contentDetailsOutput.setMuviUniqId("");

                    }
                    if ((mainJson.has("video_duration")) && mainJson.optString("video_duration").trim() != null && !mainJson.optString("video_duration").trim().isEmpty() && !mainJson.optString("video_duration").trim().equals("null") && !mainJson.optString("video_duration").trim().matches("")) {
                        contentDetailsOutput.setMuviUniqId(mainJson.optString("video_duration"));
                    } else {
                        contentDetailsOutput.setMuviUniqId("");

                    }

                    if ((mainJson.has("movieUrl")) && mainJson.optString("movieUrl").trim() != null && !mainJson.optString("movieUrl").trim().isEmpty() && !mainJson.optString("movieUrl").trim().equals("null") && !mainJson.optString("movieUrl").trim().matches("")) {
                        contentDetailsOutput.setMovieUrl(mainJson.optString("movieUrl"));

                    } else {
                        contentDetailsOutput.setMovieUrl("");

                    }

                    if ((mainJson.has("banner")) && mainJson.optString("banner").trim() != null && !mainJson.optString("banner").trim().isEmpty() && !mainJson.optString("banner").trim().equals("null") && !mainJson.optString("banner").trim().matches("")) {
                        contentDetailsOutput.setBanner(mainJson.optString("banner"));
                    } else {
                        contentDetailsOutput.setBanner("");

                    }

                    if ((mainJson.has("poster")) && mainJson.optString("poster").trim() != null && !mainJson.optString("poster").trim().isEmpty() && !mainJson.optString("poster").trim().equals("null") && !mainJson.optString("poster").trim().matches("")) {
                        contentDetailsOutput.setPoster(mainJson.optString("poster"));
                    } else {
                        contentDetailsOutput.setPoster("");

                    }
                    if ((mainJson.has("isFreeContent")) && mainJson.optString("isFreeContent").trim() != null && !mainJson.optString("isFreeContent").trim().isEmpty() && !mainJson.optString("isFreeContent").trim().equals("null") && !mainJson.optString("isFreeContent").trim().matches("")) {
                        contentDetailsOutput.setIsFreeContent(mainJson.optString("isFreeContent"));
                    } else {
                        contentDetailsOutput.setIsFreeContent(mainJson.optString("isFreeContent"));

                    }
                    if ((mainJson.has("release_date")) && mainJson.optString("release_date").trim() != null && !mainJson.optString("release_date").trim().isEmpty() && !mainJson.optString("release_date").trim().equals("null") && !mainJson.optString("release_date").trim().matches("")) {
                        contentDetailsOutput.setReleaseDate(mainJson.optString("release_date"));
                    } else {
                        contentDetailsOutput.setReleaseDate(mainJson.optString("isFreeContent"));

                    }
                    if ((mainJson.has("is_ppv")) && mainJson.optString("is_ppv").trim() != null && !mainJson.optString("is_ppv").trim().isEmpty() && !mainJson.optString("is_ppv").trim().equals("null") && !mainJson.optString("is_ppv").trim().matches("")) {
                        contentDetailsOutput.setIsPpv(Integer.parseInt(mainJson.optString("is_ppv")));
                    } else {
                        contentDetailsOutput.setIsPpv(0);

                    }

                    if ((mainJson.has("is_converted")) && mainJson.optString("is_converted").trim() != null && !mainJson.optString("is_converted").trim().isEmpty() && !mainJson.optString("is_converted").trim().equals("null") && !mainJson.optString("is_converted").trim().matches("")) {
                        contentDetailsOutput.setIsConverted(Integer.parseInt(mainJson.optString("is_converted")));
                    } else {
                        contentDetailsOutput.setIsConverted(0);

                    }
                    if ((mainJson.has("is_advance")) && mainJson.optString("is_advance").trim() != null && !mainJson.optString("is_advance").trim().isEmpty() && !mainJson.optString("is_advance").trim().equals("null") && !mainJson.optString("is_advance").trim().matches("")) {
                        contentDetailsOutput.setIsApv(Integer.parseInt(mainJson.optString("is_advance")));
                    } else {
                        contentDetailsOutput.setIsApv(0);

                    }
                    if (contentDetailsOutput.getIsPpv() == 1) {
                        JSONObject ppvJson = null;
                        if ((myJson.has("ppv_pricing"))) {

                            PPVModel ppvModel = new PPVModel();
                            ppvJson = myJson.getJSONObject("ppv_pricing");
                            if ((ppvJson.has("price_for_unsubscribed")) && ppvJson.optString("price_for_unsubscribed").trim() != null && !ppvJson.optString("price_for_unsubscribed").trim().isEmpty() && !ppvJson.optString("price_for_unsubscribed").trim().equals("null") && !ppvJson.optString("price_for_unsubscribed").trim().matches("")) {
                                ppvModel.setPPVPriceForUnsubscribedStr(ppvJson.optString("price_for_unsubscribed"));
                            } else {
                                ppvModel.setPPVPriceForUnsubscribedStr("0.0");

                            }
                            if ((ppvJson.has("price_for_subscribed")) && ppvJson.optString("price_for_subscribed").trim() != null && !ppvJson.optString("price_for_subscribed").trim().isEmpty() && !ppvJson.optString("price_for_subscribed").trim().equals("null") && !ppvJson.optString("price_for_subscribed").trim().matches("")) {
                                ppvModel.setPPVPriceForsubscribedStr(ppvJson.optString("price_for_subscribed"));
                            } else {
                                ppvModel.setPPVPriceForsubscribedStr("0.0");

                            }
                            contentDetailsOutput.setPpvDetails(ppvModel);
                        }

                    }
                    if (contentDetailsOutput.getIsApv() == 1) {
                        JSONObject advJson = null;
                        if ((myJson.has("adv_pricing"))) {
                            APVModel aPVModel = new APVModel();

                            advJson = myJson.getJSONObject("adv_pricing");
                            if ((advJson.has("price_for_unsubscribed")) && advJson.optString("price_for_unsubscribed").trim() != null && !advJson.optString("price_for_unsubscribed").trim().isEmpty() && !advJson.optString("price_for_unsubscribed").trim().equals("null") && !advJson.optString("price_for_unsubscribed").trim().matches("")) {
                                aPVModel.setAPVPriceForUnsubscribedStr(advJson.optString("price_for_unsubscribed"));

                            } else {
                                aPVModel.setAPVPriceForUnsubscribedStr("0.0");

                            }
                            if ((advJson.has("price_for_subscribed")) && advJson.optString("price_for_subscribed").trim() != null && !advJson.optString("price_for_subscribed").trim().isEmpty() && !advJson.optString("price_for_subscribed").trim().equals("null") && !advJson.optString("price_for_subscribed").trim().matches("")) {
                                aPVModel.setAPVPriceForsubscribedStr(advJson.optString("price_for_subscribed"));
                            } else {
                                aPVModel.setAPVPriceForsubscribedStr("0.0");

                            }
                            contentDetailsOutput.setApvDetails(aPVModel);

                        }

                    }

                    if (contentDetailsOutput.getIsPpv() == 1 || contentDetailsOutput.getIsApv() == 1) {

                        JSONObject currencyJson = null;
                        if (myJson.has("currency") && myJson.optString("currency") != null && !myJson.optString("currency").equals("null")) {
                            currencyJson = myJson.getJSONObject("currency");
                            CurrencyModel currencyModel = new CurrencyModel();

                            if (currencyJson.has("id") && currencyJson.optString("id").trim() != null && !currencyJson.optString("id").trim().isEmpty() && !currencyJson.optString("id").trim().equals("null") && !currencyJson.optString("id").trim().matches("")) {
                                currencyModel.setCurrencyId(currencyJson.optString("id"));
                            } else {
                                currencyModel.setCurrencyId("");

                            }
                            if (currencyJson.has("country_code") && currencyJson.optString("country_code").trim() != null && !currencyJson.optString("country_code").trim().isEmpty() && !currencyJson.optString("country_code").trim().equals("null") && !currencyJson.optString("country_code").trim().matches("")) {
                                currencyModel.setCurrencyCode(currencyJson.optString("country_code"));
                            } else {
                                currencyModel.setCurrencyCode("");
                            }
                            if (currencyJson.has("symbol") && currencyJson.optString("symbol").trim() != null && !currencyJson.optString("symbol").trim().isEmpty() && !currencyJson.optString("symbol").trim().equals("null") && !currencyJson.optString("symbol").trim().matches("")) {
                                currencyModel.setCurrencySymbol(currencyJson.optString("symbol"));
                            } else {
                                currencyModel.setCurrencySymbol("");
                            }
                            contentDetailsOutput.setCurrencyDetails(currencyModel);

                        }

                    }




                       /* if (mainJson.has("cast_detail") && mainJson.has("cast_detail")!= false && mainJson.optString("cast_detail").trim() != null && !mainJson.optString("cast_detail").trim().isEmpty() && !mainJson.optString("cast_detail").trim().equals("null") && !mainJson.optString("cast_detail").trim().equals("false")){
                            JSONArray castDetailArray = mainJson.getJSONArray("cast_detail");
                            int lengthJsonArr = castDetailArray.length();
                            for(int i=0; i < lengthJsonArr; i++) {
                                JSONObject jsonChildNode;
                                try {
                                    jsonChildNode = castDetailArray.getJSONObject(i);
                                    if (jsonChildNode.has("cast_type") && jsonChildNode.optString("cast_type").equalsIgnoreCase("actor")){
                                        tempStr.append( jsonChildNode.optString("celeb_name")+"\n");
                                        castStr = tempStr.toString();

                                    }else  if (jsonChildNode.has("cast_type") && jsonChildNode.optString("cast_type").equalsIgnoreCase("director")){
                                        crewtempStr.append( jsonChildNode.optString("celeb_name")+"\n");
                                        crewStr = crewtempStr.toString();

                                    }

                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }

                        }else{
                            castStr = "";
                            crewStr = "";
                        }
*/
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
        listener.onGetContentDetailsPreExecuteStarted();

        status = 0;
        if (!PACKAGE_NAME.equals(CommonConstants.user_Package_Name_At_Api)) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetContentDetailsPostExecuteCompleted(contentDetailsOutput, status, message);
            return;
        }
        if (CommonConstants.hashKey.equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetContentDetailsPostExecuteCompleted(contentDetailsOutput, status, message);
        }


    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onGetContentDetailsPostExecuteCompleted(contentDetailsOutput, status, message);

    }

    //}
}
