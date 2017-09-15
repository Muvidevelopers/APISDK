/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.TransactionInputModel;
import com.home.apisdk.apiModel.TransactionOutputModel;

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
 * This Class shows all the transaction details of the user.
 *
 * @author MUVI
 */


public class TransactionDetailsAsynctask extends AsyncTask<TransactionInputModel, Void, Void> {

    private TransactionInputModel transactionInput;
    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private TransactionListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a TransactionDetailsAsynctask to run some code when get
     * responses.
     */

    public interface TransactionListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onTransactionPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param transactionOutput A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status            Response Code from the server
         * @param message           On Success Message
         */

        void onTransactionPostExecuteCompleted(TransactionOutputModel transactionOutput, int status, String message);
    }


    TransactionOutputModel transactionOutput = new TransactionOutputModel();

    /**
     * Constructor to initialise the private data members.
     *
     * @param transactionInput A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                         For Example: to use this API we have to set following attributes:
     *                         setAuthToken(),setId() etc.
     * @param listener         Transaction Listener
     * @param context          android.content.Context
     */

    public TransactionDetailsAsynctask(TransactionInputModel transactionInput, TransactionListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.transactionInput = transactionInput;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "transaction");


    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException,JSONException
     */

    @Override
    protected Void doInBackground(TransactionInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getTransactionUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.transactionInput.getAuthToken());
            httppost.addHeader(HeaderConstants.USER_ID, this.transactionInput.getUser_id());
            httppost.addHeader(HeaderConstants.ID, this.transactionInput.getId());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.transactionInput.getLanguage());

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

                    JSONObject mainJson = myJson.getJSONObject("section");
                    if ((mainJson.has("order_number")) && mainJson.optString("order_number").trim() != null && !mainJson.optString("order_number").trim().isEmpty() && !mainJson.optString("order_number").trim().equals("null") && !mainJson.optString("order_number").trim().matches("")) {
                        transactionOutput.setOrder_number(mainJson.optString("order_number"));
                    } else {
                        transactionOutput.setOrder_number("");

                    }
                    if ((mainJson.has("movie_id")) && mainJson.optString("movie_id").trim() != null && !mainJson.optString("movie_id").trim().isEmpty() && !mainJson.optString("movie_id").trim().equals("null") && !mainJson.optString("movie_id").trim().matches("")) {
                        transactionOutput.setMovie_id(mainJson.optString("movie_id"));


                    } else {
                        transactionOutput.setMovie_id("");

                    }
                    if ((mainJson.has("transaction_date")) && mainJson.optString("transaction_date").trim() != null && !mainJson.optString("transaction_date").trim().isEmpty() && !mainJson.optString("transaction_date").trim().equals("null") && !mainJson.optString("transaction_date").trim().matches("")) {
                        transactionOutput.setTransaction_date(mainJson.optString("transaction_date"));


                    } else {
                        transactionOutput.setTransaction_date("");

                    }
                    if ((mainJson.has("transaction_status")) && mainJson.optString("transaction_status").trim() != null && !mainJson.optString("transaction_status").trim().isEmpty() && !mainJson.optString("transaction_status").trim().equals("null") && !mainJson.optString("transaction_status").trim().matches("")) {
                        transactionOutput.setTransaction_status(mainJson.optString("transaction_status"));
                    } else {
                        transactionOutput.setTransaction_status("");

                    }
                    if ((mainJson.has("plan_name")) && mainJson.optString("plan_name").trim() != null && !mainJson.optString("plan_name").trim().isEmpty() && !mainJson.optString("plan_name").trim().equals("null") && !mainJson.optString("plan_name").trim().matches("")) {
                        transactionOutput.setPlan_name(mainJson.optString("plan_name"));
                    } else {
                        transactionOutput.setPlan_name("");

                    }

                    if ((mainJson.has("currency_symbol")) && mainJson.optString("currency_symbol").trim() != null && !mainJson.optString("currency_symbol").trim().isEmpty() && !mainJson.optString("currency_symbol").trim().equals("null") && !mainJson.optString("currency_symbol").trim().matches("")) {
                        transactionOutput.setCurrency_symbol(mainJson.optString("currency_symbol"));

                    } else {
                        transactionOutput.setCurrency_symbol("");

                    }

                    if ((mainJson.has("currency_code")) && mainJson.optString("currency_code").trim() != null && !mainJson.optString("currency_code").trim().isEmpty() && !mainJson.optString("currency_code").trim().equals("null") && !mainJson.optString("currency_code").trim().matches("")) {
                        transactionOutput.setCurrency_code(mainJson.optString("currency_code"));
                    } else {
                        transactionOutput.setCurrency_code("");

                    }

                    if ((mainJson.has("amount")) && mainJson.optString("amount").trim() != null && !mainJson.optString("amount").trim().isEmpty() && !mainJson.optString("amount").trim().equals("null") && !mainJson.optString("amount").trim().matches("")) {
                        transactionOutput.setAmount(mainJson.optString("amount"));

                    } else {
                        transactionOutput.setAmount("");

                    }


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
        listener.onTransactionPreExecuteStarted();

        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onTransactionPostExecuteCompleted(transactionOutput, status, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onTransactionPostExecuteCompleted(transactionOutput, status, message);
        }

    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onTransactionPostExecuteCompleted(transactionOutput, status, message);

    }

    //}
}
