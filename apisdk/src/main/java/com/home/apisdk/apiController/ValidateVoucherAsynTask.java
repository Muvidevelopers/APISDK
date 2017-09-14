/**
 * SDK initialization, platform and device information classes.
 */


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
 * This Class checks whether voucher entered by the user is valid or invalid.
 *
 * @author MUVI
 */


public class ValidateVoucherAsynTask extends AsyncTask<ValidateVoucherInputModel, Void, Void> {

    private ValidateVoucherInputModel validateVoucherInput;
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
         * @param validateVoucherOutput A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status                Response Code from the server
         * @param message               On Success Message
         */

        void onValidateVoucherPostExecuteCompleted(ValidateVoucherOutputModel validateVoucherOutput, int status, String message);
    }

    ValidateVoucherOutputModel validateVoucherOutput = new ValidateVoucherOutputModel();

    /**
     * Constructor to initialise the private data members.
     *
     * @param validateVoucherInput A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                             For Example: to use this API we have to set following attributes:
     *                             setAuthToken(),setMovie_id() etc.
     * @param listener             ValidateVoucher Listener
     * @param context              android.content.Context
     */

    public ValidateVoucherAsynTask(ValidateVoucherInputModel validateVoucherInput, ValidateVoucherListener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.validateVoucherInput = validateVoucherInput;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "validate voucher");

    }

    /**
     * Background thread to execute.
     *
     * @return null
     */

    @Override
    protected Void doInBackground(ValidateVoucherInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getValidateVoucherUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.validateVoucherInput.getAuthToken());
            httppost.addHeader(HeaderConstants.MOVIE_ID, this.validateVoucherInput.getMovie_id());
            httppost.addHeader(HeaderConstants.STREAM_ID, this.validateVoucherInput.getStream_id());
            httppost.addHeader(HeaderConstants.PURCHASE_TYPE, this.validateVoucherInput.getPurchase_type());
            httppost.addHeader(HeaderConstants.SEASON, this.validateVoucherInput.getSeason());
            httppost.addHeader(HeaderConstants.VOUCHER_CODE, this.validateVoucherInput.getVoucher_code());
            httppost.addHeader(HeaderConstants.USER_ID, this.validateVoucherInput.getUser_id());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.validateVoucherInput.getLanguage());

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
                    validateVoucherOutput.setMsg(myJson.optString("msg"));
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
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onValidateVoucherPostExecuteCompleted(validateVoucherOutput, status, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onValidateVoucherPostExecuteCompleted(validateVoucherOutput, status, message);
        }

    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onValidateVoucherPostExecuteCompleted(validateVoucherOutput, status, message);

    }

    //}
}
