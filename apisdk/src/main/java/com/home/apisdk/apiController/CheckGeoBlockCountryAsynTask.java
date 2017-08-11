package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.HeaderConstants;
import com.home.apisdk.apiModel.CheckGeoBlockInputModel;
import com.home.apisdk.apiModel.CheckGeoBlockOutputModel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

/**
 * Created by User on 12-12-2016.
 * Class to Get Country details
 */
public class CheckGeoBlockCountryAsynTask extends AsyncTask<CheckGeoBlockInputModel, Void, Void> {

    private CheckGeoBlockInputModel checkGeoBlockInputModel;
    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private String countryCode;
    private CheckGeoBlockForCountryListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a CheckGeoBlockCountryAsynTask to run some code when get
     * responses.
     */

    public interface CheckGeoBlockForCountryListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */


        void onCheckGeoBlockCountryPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param checkGeoBlockOutputModel
         * @param status
         * @param message
         */

        void onCheckGeoBlockCountryPostExecuteCompleted(CheckGeoBlockOutputModel checkGeoBlockOutputModel, int status, String message);
    }


    CheckGeoBlockOutputModel checkGeoBlockOutputModel = new CheckGeoBlockOutputModel();

    /**
     * Constructor to initialise the private data members.
     *
     * @param checkGeoBlockInputModel
     * @param listener
     * @param context
     */

    public CheckGeoBlockCountryAsynTask(CheckGeoBlockInputModel checkGeoBlockInputModel, CheckGeoBlockForCountryListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.checkGeoBlockInputModel = checkGeoBlockInputModel;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "getFeatureContentAsynTask");

    }

    @Override
    protected Void doInBackground(CheckGeoBlockInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            String url = APIUrlConstant.getCheckGeoBlockUrl();
            HttpPost httppost = new HttpPost(url);
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.checkGeoBlockInputModel.getAuthToken());
            httppost.addHeader(HeaderConstants.IP, this.checkGeoBlockInputModel.getIp());


            // Execute HTTP Post Request
            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.v("MUVISDK", "RES" + responseStr);

            } catch (org.apache.http.conn.ConnectTimeoutException e) {
                status = 0;
                countryCode = "";
                message = "";

            } catch (IOException e) {
                status = 0;
                countryCode = "";
                message = "";
            }

            JSONObject myJson = null;
            if (responseStr != null) {
                Object json = new JSONTokener(responseStr).nextValue();
                if (json instanceof JSONObject) {
                    String statusStr = ((JSONObject) json).optString("code");
                    status = Integer.parseInt(statusStr);
                    if (status == 200) {
                        countryCode = ((JSONObject) json).optString("country");
                        checkGeoBlockOutputModel.setCountrycode(countryCode);
                    }

                }
            }


        } catch (Exception e) {
            status = 0;
            message = "";
            countryCode = "";
        }
        return null;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onCheckGeoBlockCountryPreExecuteStarted();
        responseStr = "0";
           /* status = 0;
            if(!PACKAGE_NAME.equals(HeaderConstants.user_Package_Name_At_Api))
            {
                this.cancel(true);
                message = "Packge Name Not Matched";
                listener.onCheckGeoBlockCountryPostExecuteCompleted(checkGeoBlockOutputModel,status,message);
                return;
            }
            if(HeaderConstants.hashKey.equals(""))
            {
                this.cancel(true);
                message = "Hash Key Is Not Available. Please Initialize The SDK";
                listener.onCheckGeoBlockCountryPostExecuteCompleted(checkGeoBlockOutputModel,status,totalItems,message);
            }*/


    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onCheckGeoBlockCountryPostExecuteCompleted(checkGeoBlockOutputModel, status, message);

    }

    //}
}
