
package com.ezetapsdk;

import android.app.Activity;
import android.content.Intent;

import com.eze.api.EzeAPI;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import org.json.JSONException;
import org.json.JSONObject;

import static com.ezetapsdk.EzeConstants.ATTACH_SIGNATURE_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.BRAND_EMI_TRANSACTION_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.CARD_INFO_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.CHECK_INCOMPLETE_TRANSACTION_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.CLOSE_SESSION_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.CONFIRM_PRE_AUTH_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.GENERIC_TRANSACTION_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.GET_TRANSACTION_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.INITIALIZE_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.NORMAL_EMI_TRANSACTION_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.PREPARE_DEVICE_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.PRE_AUTH_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.PRINT_BITMAP_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.PRINT_RECEIPT_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.RELEASE_PRE_AUTH_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.SEARCH_TRANSACTION_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.SEND_RECEIPT_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.SERVICE_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.STOP_PAYMENT_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.VOID_TRANSACTION_REQUEST_CODE;

public class RNEzetapSdkModule extends ReactContextBaseJavaModule implements ActivityEventListener {

    private Promise mPromise;

    public RNEzetapSdkModule(ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addActivityEventListener(this);
    }

    @Override
    public String getName() {
        return "RNEzetapSdk";
    }

    /**
     * Method to initialize the Ezetap transaction
     *
     * @param requestObject requestObject - The request object as defined in the API
     *                      documentation
     */
    @ReactMethod
    public void initialize(String requestObject, final Promise promise) throws JSONException {
        mPromise = promise;
        EzeAPI.initialize(getCurrentActivity(), INITIALIZE_REQUEST_CODE, new JSONObject(requestObject));
    }

    /**
     * Method to close prepare device before any transaction
     */
    @ReactMethod
    public void prepareDevice(final Promise promise) {
        mPromise = promise;
        EzeAPI.prepareDevice(getCurrentActivity(), PREPARE_DEVICE_REQUEST_CODE);
    }

    /**
     * Method to send transaction receipt to given mobile no/email id
     *
     * @param requestObject requestObject - The request object as defined in the API
     *                      documentation
     */
    @ReactMethod
    public void sendReceipt(String requestObject, final Promise promise) throws JSONException {
        mPromise = promise;
        EzeAPI.sendReceipt(getCurrentActivity(), SEND_RECEIPT_REQUEST_CODE, new JSONObject(requestObject));
    }


    @ReactMethod
    public void serviceRequest(String requestObject, final Promise promise) throws JSONException {
        mPromise = promise;
        EzeAPI.serviceRequest(getCurrentActivity(), SERVICE_REQUEST_CODE, new JSONObject(requestObject));
    }


    /**
     * Method to search transaction(s)
     *
     * @param requestObject requestObject - The request object as defined in the API
     *                      documentation
     */
    @ReactMethod
    public void searchTransaction(String requestObject, final Promise promise) throws JSONException {
        mPromise = promise;
        EzeAPI.searchTransaction(getCurrentActivity(), SEARCH_TRANSACTION_REQUEST_CODE, new JSONObject(requestObject));
    }

    /**
     * Method to get transaction detail for a given External Reference
     *
     * @param externalReference externalReference - External reference number to look up
     */
    @ReactMethod
    public void getTransaction(String externalReference, final Promise promise) throws JSONException {
        mPromise = promise;
        EzeAPI.getTransaction(getCurrentActivity(), GET_TRANSACTION_REQUEST_CODE, new JSONObject(externalReference));
    }

    /**
     * Method to if a previous card transaction is pending. A card transaction
     * could be pending if no response was received after the transaction was
     * initiated or if there was an app crash after the transaction was
     * initiated. The Ezetap SDK checks for pending transactions at the very
     * next Ezetap API call, it is recommended that calling applications call
     * this API at the appropriate time for eg. - upon login or upon calling pay
     * cash where the handling is not done by the Ezetap system.
     */

    @ReactMethod
    public void checkForIncompleteTransaction(String json, final Promise promise) throws JSONException {
        mPromise = promise;
        EzeAPI.checkForIncompleteTransaction(getCurrentActivity(), CHECK_INCOMPLETE_TRANSACTION_REQUEST_CODE, json != null ? new JSONObject(json) : (JSONObject) null);
    }

    /**
     * Method to void transaction
     *
     * @param transactionID transactionID - The ID of the transaction which is to be
     *                      voided
     */
    @ReactMethod
    public void voidTransaction(String transactionID, final Promise promise) {
        mPromise = promise;
        EzeAPI.voidTransaction(getCurrentActivity(), VOID_TRANSACTION_REQUEST_CODE, transactionID);
    }

    /**
     * Method to attach Signature for a transaction
     *
     * @param requestObject requestObject - The request object as defined in the API
     *                      documentation
     */
    @ReactMethod
    public void attachSignature(String requestObject, final Promise promise) throws JSONException {
        mPromise = promise;
        EzeAPI.attachSignature(getCurrentActivity(), ATTACH_SIGNATURE_REQUEST_CODE, new JSONObject(requestObject));
    }


    /**
     * Method to start a generic pay
     *
     * @param requestObject requestObject - The request object as defined in the API
     *                      documentation
     */
    @ReactMethod
    public void pay(String requestObject, final Promise promise) throws JSONException {
        mPromise = promise;
        EzeAPI.pay(getCurrentActivity(), GENERIC_TRANSACTION_REQUEST_CODE, new JSONObject(requestObject));
    }


    /**
     * Method to start a Brand EMI transaction
     *
     * @param requestObject requestObject - The request object as defined in the API
     *                      documentation
     */
    @ReactMethod
    public void brandEMITransaction(String requestObject, final Promise promise) throws JSONException {
        mPromise = promise;
        EzeAPI.brandEMITransaction(getCurrentActivity(), BRAND_EMI_TRANSACTION_REQUEST_CODE, new JSONObject(requestObject));
    }

    /**
     * Method to start a Brand EMI transaction
     *
     * @param requestObject requestObject - The request object as defined in the API
     *                      documentation
     */
    @ReactMethod
    public void normalEMITransaction(String requestObject, final Promise promise) throws JSONException {
        mPromise = promise;
        EzeAPI.normalEMITransaction(getCurrentActivity(), NORMAL_EMI_TRANSACTION_REQUEST_CODE, new JSONObject(requestObject));
    }

    /**
     * Method to print a receipt for a particular transaction
     *
     * @param transactionID transactionID - The ID of the Ezetap txn
     */
    @ReactMethod
    public void printReceipt(String transactionID, final Promise promise) {
        mPromise = promise;
        EzeAPI.printReceipt(getCurrentActivity(), PRINT_RECEIPT_REQUEST_CODE, transactionID);
    }

    /**
     * Method to print a bitmap image of the current view
     */
    @ReactMethod
    public void printBitmap(final Promise promise) {
        mPromise = promise;
        getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String encodedImageData = Utils.loadBitmapBase64EncodedFromView(getCurrentActivity());
                JSONObject jsonRequest = new JSONObject();
                JSONObject jsonImageObj = new JSONObject();
                try {
                    jsonImageObj.put("imageData", encodedImageData);
                    jsonImageObj.put("imageType", "JPEG");
                    jsonImageObj.put("height", "");// optional
                    jsonImageObj.put("weight", "");// optional
                    jsonRequest.put("image",
                            jsonImageObj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                EzeAPI.printBitmap(getCurrentActivity(), PRINT_BITMAP_REQUEST_CODE, jsonRequest);
            }
        });
    }

    /**
     * Method to close Ezetap sessions
     */
    @ReactMethod
    public void close(final Promise promise) {
        mPromise = promise;
        EzeAPI.close(getCurrentActivity(), CLOSE_SESSION_REQUEST_CODE);
    }

    /* Initiate Pre Auth */
    @ReactMethod
    public void preAuth(String jsonObject, final Promise promise) throws JSONException {
        mPromise = promise;
        EzeAPI.preAuth(getCurrentActivity(), PRE_AUTH_REQUEST_CODE, new JSONObject(jsonObject));

    }

    /* Confirm Pre Auth */
    @ReactMethod
    public void confirmPreAuth(String jsonObject, final Promise promise) throws JSONException {
        mPromise = promise;
        EzeAPI.confirmPreAuth(getCurrentActivity(), CONFIRM_PRE_AUTH_REQUEST_CODE, new JSONObject(jsonObject));

    }

    /* Release Pre Auth */
    @ReactMethod
    public void releasePreAuth(String jsonObject, final Promise promise) throws JSONException {
        mPromise = promise;
        EzeAPI.releasePreAuth(getCurrentActivity(), RELEASE_PRE_AUTH_REQUEST_CODE, new JSONObject(jsonObject));

    }

    /**
     * Method to void transaction
     *
     * @param jsonObject jsonObject - All txnIds to be passed to stop the payment.
     */
    @ReactMethod
    public void stopPayment(String jsonObject, final Promise promise) throws JSONException {
        mPromise = promise;
        EzeAPI.stopPayment(getCurrentActivity(), STOP_PAYMENT_REQUEST_CODE, new JSONObject(jsonObject));
    }

    /**
     * Do not remove this onActivityResult as it is an abstract method which calls the ActivityListener
     * onActivityResult Method
     * onActivityResult will be called
     *
     * @param activity    context of the Activity
     * @param requestCode Request code you wish to handle for the result in onActivityResult method.
     */
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        onActivityResult(requestCode, resultCode, data);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mPromise != null) {

            switch (requestCode) {
                case INITIALIZE_REQUEST_CODE:
                case PREPARE_DEVICE_REQUEST_CODE:
                case CARD_INFO_REQUEST_CODE:
                case SEND_RECEIPT_REQUEST_CODE:
                case SERVICE_REQUEST_CODE:
                case SEARCH_TRANSACTION_REQUEST_CODE:
                case GET_TRANSACTION_REQUEST_CODE:
                case CHECK_INCOMPLETE_TRANSACTION_REQUEST_CODE:
                case VOID_TRANSACTION_REQUEST_CODE:
                case ATTACH_SIGNATURE_REQUEST_CODE:
                case GENERIC_TRANSACTION_REQUEST_CODE:
                case BRAND_EMI_TRANSACTION_REQUEST_CODE:
                case NORMAL_EMI_TRANSACTION_REQUEST_CODE:
                case PRINT_RECEIPT_REQUEST_CODE:
                case PRINT_BITMAP_REQUEST_CODE:
                case CLOSE_SESSION_REQUEST_CODE:
                case PRE_AUTH_REQUEST_CODE:
                case CONFIRM_PRE_AUTH_REQUEST_CODE:
                case RELEASE_PRE_AUTH_REQUEST_CODE:
                case STOP_PAYMENT_REQUEST_CODE: {
                    if (data.getExtras() != null) {
                        mPromise.resolve(Utils.createJsonObject(data));
                    } else
                        mPromise.reject(String.valueOf(requestCode), new Exception("An Error has been occurred"));
                }
                default:
                    break;
            }
        }
    }
}