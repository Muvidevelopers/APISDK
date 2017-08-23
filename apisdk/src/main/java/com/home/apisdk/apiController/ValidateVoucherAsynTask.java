package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.ValidateVoucherInputModel;
import com.home.apisdk.apiModel.ValidateVoucherOutputModel;

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
 * Class to get Validate Voucher details.
 */


public class ValidateVoucherAsynTask extends AsyncTask<ValidateVoucherInputModel, Void, Void> {

    private ValidateVoucherInputModel validateVoucherInputModel;
    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private ValidateVoucherListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a ValidateVoucherAsynTask to run some code when get
     * responses.
     */

    public interface ValidateVoucherListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onValidateVoucherPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param validateVoucherOutputModel
         * @param status
         * @param message
         */

        void onValidateVoucherPostExecuteCompleted(ValidateVoucherOutputModel validateVoucherOutputModel, int status, String message);
    }

    ValidateVoucherOutputModel validateVoucherOutputModel = new ValidateVoucherOutputModel();

    /**
     * Constructor to initialise the private data members.
     *
     * @param validateVoucherInputModel
     * @param listener
     * @param context
     */

    public ValidateVoucherAsynTask(ValidateVoucherInputModel validateVoucherInputModel, ValidateVoucherListener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.validateVoucherInputModel = validateVoucherInputModel;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "validate voucher");

    }

    @Override
    protected Void doInBackground(ValidateVoucherInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getValidateVoucherUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.validateVoucherInputModel.getAuthToken());
            httppost.addHeader(HeaderConstants.MOVIE_ID, this.validateVoucherInputModel.getMovie_id());
            httppost.addHeader(HeaderConstants.STREAM_ID, this.validateVoucherInputModel.getStream_id());
            httppost.addHeader(HeaderConstants.PURCHASE_TYPE, this.validateVoucherInputModel.getPurchase_type());
            httppost.addHeader(HeaderConstants.SEASON, this.validateVoucherInputModel.getSeason());
            httppost.addHeader(HeaderConstants.VOUCHER_CODE, this.validateVoucherInputModel.getVoucher_code());
            httppost.addHeader(HeaderConstants.USER_ID, this.validateVoucherInputModel.getUser_id());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.validateVoucherInputModel.getLanguage());

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
            }


            if (status == 200) {
                if ((myJson.has("msg")) && myJson.optString("msg").trim() != null && !myJson.optString("msg").trim().isEmpty() && !myJson.optString("msg").trim().equals("null") && !myJson.optString("msg").trim().matches("")) {
                    validateVoucherOutputModel.setMsg(myJson.optString("msg"));
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
        listener.onValidateVoucherPreExecuteStarted();
        responseStr = "0";
        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api())) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onValidateVoucherPostExecuteCompleted(validateVoucherOutputModel, status, message);
            return;
        }
        if (SDKInitializer.getHashKey().equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onValidateVoucherPostExecuteCompleted(validateVoucherOutputModel, status, message);
        }


    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onValidateVoucherPostExecuteCompleted(validateVoucherOutputModel, status, message);

    }

    //}
}
