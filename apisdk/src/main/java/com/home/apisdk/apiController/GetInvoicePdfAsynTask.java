package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.GetInvoicePdfInputModel;
import com.home.apisdk.apiModel.GetInvoicePdfOutputModel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by MUVI on 1/20/2017.
 * Class to get Invoice Pdf details.
 */

public class GetInvoicePdfAsynTask extends AsyncTask<GetInvoicePdfInputModel, Void, Void> {

    private GetInvoicePdfInputModel getInvoicePdfInputModel;
    private String PACKAGE_NAME;
    private String message;
    private String responseStr;
    private String status;
    private int code;
    private GetInvoicePdfOutputModel getInvoicePdfOutputModel;
    private GetInvoicePdfListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a GetInvoicePdfAsynTask to run some code when get
     * responses.
     */

    public interface GetInvoicePdfListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onGetInvoicePdfPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param getInvoicePdfOutputModel
         * @param code
         * @param message
         * @param status
         */

        void onGetInvoicePdfPostExecuteCompleted(GetInvoicePdfOutputModel getInvoicePdfOutputModel, int code, String message, String status);
    }

    /**
     * Constructor to initialise the private data members.
     *
     * @param getInvoicePdfInputModel
     * @param listener
     * @param context
     */

    public GetInvoicePdfAsynTask(GetInvoicePdfInputModel getInvoicePdfInputModel, GetInvoicePdfListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.getInvoicePdfInputModel = getInvoicePdfInputModel;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "getinvoicepdf");

    }

    @Override
    protected Void doInBackground(GetInvoicePdfInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getGetInvoicePdfUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.getInvoicePdfInputModel.getAuthToken());
            httppost.addHeader(HeaderConstants.ID, this.getInvoicePdfInputModel.getId());
            httppost.addHeader(HeaderConstants.USER_ID, this.getInvoicePdfInputModel.getUser_id());
            httppost.addHeader(HeaderConstants.DEVICE_TYPE, this.getInvoicePdfInputModel.getDevice_type());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.getInvoicePdfInputModel.getLang_code());
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
            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                code = Integer.parseInt(myJson.optString("code"));
                message = myJson.optString("msg");
                message = myJson.optString("status");
            }

            if (code == 200) {

                getInvoicePdfOutputModel = new GetInvoicePdfOutputModel();
                getInvoicePdfOutputModel.setSection(myJson.optString("section"));

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
        listener.onGetInvoicePdfPreExecuteStarted();
        code = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api())) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetInvoicePdfPostExecuteCompleted(getInvoicePdfOutputModel, code, message, status);
            return;
        }
        if (SDKInitializer.getHashKey().equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetInvoicePdfPostExecuteCompleted(getInvoicePdfOutputModel, code, message, status);
        }


    }

    @Override
    protected void onPostExecute(Void result) {
        listener.onGetInvoicePdfPostExecuteCompleted(getInvoicePdfOutputModel, code, message, status);
    }
}
