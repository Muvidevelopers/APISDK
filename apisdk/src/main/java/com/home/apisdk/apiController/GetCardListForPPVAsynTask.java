package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.HeaderConstants;
import com.home.apisdk.apiModel.GetCardListForPPVInputModel;
import com.home.apisdk.apiModel.GetCardListForPPVOutputModel;

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
 * Class to get Card List For PPV details.
 */
public class GetCardListForPPVAsynTask extends AsyncTask<GetCardListForPPVInputModel, Void, Void> {

    private GetCardListForPPVInputModel getCardListForPPVInputModel;
    private String responseStr;
    private int status;
    private int totalItems;
    private String message;
    private String PACKAGE_NAME;
    private GetCardListForPPVListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a GetCardListForPPVAsynTask to run some code when get
     * responses.
     */

    public interface GetCardListForPPVListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onGetCardListForPPVPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param getCardListForPPVOutputModel
         * @param status
         * @param totalItems
         * @param message
         */

        void onGetCardListForPPVPostExecuteCompleted(ArrayList<GetCardListForPPVOutputModel> getCardListForPPVOutputModel, int status, int totalItems, String message);
    }

    ArrayList<GetCardListForPPVOutputModel> getCardListForPPVOutputModel = new ArrayList<GetCardListForPPVOutputModel>();

    /**
     * Constructor to initialise the private data members.
     *
     * @param getCardListForPPVInputModel
     * @param listener
     * @param context
     */

    public GetCardListForPPVAsynTask(GetCardListForPPVInputModel getCardListForPPVInputModel, GetCardListForPPVListener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.getCardListForPPVInputModel = getCardListForPPVInputModel;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "GetContentListAsynTask");

    }

    @Override
    protected Void doInBackground(GetCardListForPPVInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getGetCardListForPpvUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.getCardListForPPVInputModel.getAuthToken());
            httppost.addHeader(HeaderConstants.USER_ID, this.getCardListForPPVInputModel.getUser_id());


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
                status = Integer.parseInt(myJson.optString("code"));

                message = myJson.optString("status");
            }


            if (status == 200) {

                JSONArray jsonMainNode = myJson.getJSONArray("cards");

                int lengthJsonArr = jsonMainNode.length();
                for (int i = 0; i < lengthJsonArr; i++) {
                    JSONObject jsonChildNode;
                    try {
                        jsonChildNode = jsonMainNode.getJSONObject(i);
                        GetCardListForPPVOutputModel content = new GetCardListForPPVOutputModel();

                        if ((jsonChildNode.has("card_last_fourdigit")) && jsonChildNode.optString("card_last_fourdigit").trim() != null && !jsonChildNode.optString("card_last_fourdigit").trim().isEmpty() && !jsonChildNode.optString("card_last_fourdigit").trim().equals("null") && !jsonChildNode.optString("card_last_fourdigit").trim().matches("")) {
                            content.setCard_last_fourdigit(jsonChildNode.optString("card_last_fourdigit"));

                        }
                        if ((jsonChildNode.has("card_id")) && jsonChildNode.optString("card_id").trim() != null && !jsonChildNode.optString("card_id").trim().isEmpty() && !jsonChildNode.optString("card_id").trim().equals("null") && !jsonChildNode.optString("card_id").trim().matches("")) {
                            content.setCard_id(jsonChildNode.optString("card_id"));
                        }

                        getCardListForPPVOutputModel.add(content);
                    } catch (Exception e) {
                        status = 0;

                        message = "";
                    }
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
        listener.onGetCardListForPPVPreExecuteStarted();
        responseStr = "0";
        status = 0;
        if (!PACKAGE_NAME.equals(HeaderConstants.user_Package_Name_At_Api)) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetCardListForPPVPostExecuteCompleted(getCardListForPPVOutputModel, status, totalItems, message);
            return;
        }
        if (HeaderConstants.hashKey.equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetCardListForPPVPostExecuteCompleted(getCardListForPPVOutputModel, status, totalItems, message);
        }

    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onGetCardListForPPVPostExecuteCompleted(getCardListForPPVOutputModel, status, totalItems, message);

    }

    //}
}
