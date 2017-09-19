/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
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
 * This Class shows the list of the cards that users had saved for the payment purpose.
 *
 * @author MUVI
 */
public class GetCardListForPPVAsynTask extends AsyncTask<GetCardListForPPVInputModel, Void, Void> {

    private GetCardListForPPVInputModel getCardListForPPVInput;
    private String responseStr;
    private int status;
    private int totalItems;
    private String message;
    private String PACKAGE_NAME;
    private CardListForPPVListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a GetCardListForPPVAsynTask to run some code when get
     * responses.
     */

    public interface CardListForPPVListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onCardListForPPVPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param getCardListForPPVOutput A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status                  Response Code From The Server
         * @param totalItems              Getting the total item
         * @param message                 On Success Message
         */

        void onCardListForPPVPostExecuteCompleted(ArrayList<GetCardListForPPVOutputModel> getCardListForPPVOutput, int status, int totalItems, String message);
    }

    ArrayList<GetCardListForPPVOutputModel> getCardListForPPVOutput = new ArrayList<GetCardListForPPVOutputModel>();

    /**
     * Constructor to initialise the private data members.
     *
     * @param getCardListForPPVInput A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                               For Example: to use this API we have to set following attributes:
     *                               setAuthToken(),setUser_id() etc.
     * @param listener               GetCardListForPPV Listener
     * @param context                android.content.Context
     */

    public GetCardListForPPVAsynTask(GetCardListForPPVInputModel getCardListForPPVInput, CardListForPPVListener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.getCardListForPPVInput = getCardListForPPVInput;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "GetContentListAsyn");

    }

    /**
     * Background thread to execute.
     *
     * @return  null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException
     */

    @Override
    protected Void doInBackground(GetCardListForPPVInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getGetCardListForPpvUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.getCardListForPPVInput.getAuthToken());
            httppost.addHeader(HeaderConstants.USER_ID, this.getCardListForPPVInput.getUser_id());


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

                        getCardListForPPVOutput.add(content);
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
        listener.onCardListForPPVPreExecuteStarted();
        responseStr = "0";
        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onCardListForPPVPostExecuteCompleted(getCardListForPPVOutput, status, totalItems, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onCardListForPPVPostExecuteCompleted(getCardListForPPVOutput, status, totalItems, message);
        }

    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onCardListForPPVPostExecuteCompleted(getCardListForPPVOutput, status, totalItems, message);

    }

    //}
}
