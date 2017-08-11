package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.HeaderConstants;
import com.home.apisdk.apiModel.DeleteInvoicePdfInputModel;
import com.home.apisdk.apiModel.DeleteInvoicePdfOutputModel;

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
 * Class to Delete Invoice Pdf details
 */

public class DeleteInvoicePdfAsynTask extends AsyncTask<DeleteInvoicePdfInputModel, Void, Void> {

    private DeleteInvoicePdfInputModel deleteInvoicePdfInputModel;
    private String PACKAGE_NAME;
    private String message;
    private String responseStr;
    private String status;
    private int code;
    private DeleteInvoicePdfOutputModel deleteInvoicePdfOutputModel;
    private DeleteInvoicePdfListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a DeleteInvoicePdfAsynTask to run some code when get
     * responses.
     */

    public interface DeleteInvoicePdfListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onDeleteInvoicePdfPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param deleteInvoicePdfOutputModel
         * @param code
         * @param message
         * @param status
         */

        void onDeleteInvoicePdfPostExecuteCompleted(DeleteInvoicePdfOutputModel deleteInvoicePdfOutputModel, int code, String message, String status);
    }

    /**
     * Constructor to initialise the private data members.
     *
     * @param deleteInvoicePdfInputModel
     * @param listener
     * @param context
     */

    public DeleteInvoicePdfAsynTask(DeleteInvoicePdfInputModel deleteInvoicePdfInputModel, DeleteInvoicePdfListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.deleteInvoicePdfInputModel = deleteInvoicePdfInputModel;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "deleteinvoicepdf");

    }

    @Override
    protected Void doInBackground(DeleteInvoicePdfInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getDeleteInvoicePdfUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.deleteInvoicePdfInputModel.getAuthToken());
            httppost.addHeader(HeaderConstants.FILE_PATH, this.deleteInvoicePdfInputModel.getFilepath());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.deleteInvoicePdfInputModel.getLanguage_code());


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

                message = myJson.optString("status");
            }

            if (code == 200) {

                deleteInvoicePdfOutputModel = new DeleteInvoicePdfOutputModel();
                deleteInvoicePdfOutputModel.setMsg(myJson.optString("msg"));

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
        listener.onDeleteInvoicePdfPreExecuteStarted();
        code = 0;
        if (!PACKAGE_NAME.equals(HeaderConstants.user_Package_Name_At_Api)) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onDeleteInvoicePdfPostExecuteCompleted(deleteInvoicePdfOutputModel, code, message, status);
            return;
        }
        if (HeaderConstants.hashKey.equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onDeleteInvoicePdfPostExecuteCompleted(deleteInvoicePdfOutputModel, code, message, status);
        }


    }

    @Override
    protected void onPostExecute(Void result) {
        listener.onDeleteInvoicePdfPostExecuteCompleted(deleteInvoicePdfOutputModel, code, message, status);
    }
}
