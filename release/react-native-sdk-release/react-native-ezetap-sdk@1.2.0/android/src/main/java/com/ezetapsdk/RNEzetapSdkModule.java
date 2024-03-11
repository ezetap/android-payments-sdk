package com.ezetapsdk;

/**
 * @author TIJO THOMAS
 * @since 19/04/23
 */

import static com.ezetapsdk.EzeConstants.ATTACH_SIGNATURE_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.BRAND_EMI_TRANSACTION_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.CARD_INFO_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.CARD_TRANSACTION;
import static com.ezetapsdk.EzeConstants.CASH_TRANSACTION;
import static com.ezetapsdk.EzeConstants.CHECK_FOR_UPDATES;
import static com.ezetapsdk.EzeConstants.CHECK_INCOMPLETE_TRANSACTION_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.CHEQUE_TRANSACTION;
import static com.ezetapsdk.EzeConstants.CLOSE_SESSION_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.CONFIRM_PRE_AUTH_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.DISPLAY_TRANSACTION_HISTORY;
import static com.ezetapsdk.EzeConstants.GENERIC_TRANSACTION_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.GET_CARD_INFO;
import static com.ezetapsdk.EzeConstants.GET_LOYALTY_CARD_INFO;
import static com.ezetapsdk.EzeConstants.GET_TRANSACTION_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.INITIALIZE_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.NORMAL_EMI_TRANSACTION_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.PREPARE_DEVICE_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.PRE_AUTH_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.PRINT_BITMAP_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.PRINT_RECEIPT_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.QR_CODE_TRANSACTION;
import static com.ezetapsdk.EzeConstants.REFUND_TRANSACTION;
import static com.ezetapsdk.EzeConstants.RELEASE_PRE_AUTH_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.REMOTE_TRANSACTION;
import static com.ezetapsdk.EzeConstants.SCAN_BARCODE;
import static com.ezetapsdk.EzeConstants.SEARCH_TRANSACTION_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.SEND_RECEIPT_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.SERVICE_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.STOP_PAYMENT_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.UPI_TRANSACTION;
import static com.ezetapsdk.EzeConstants.VOID_TRANSACTION_REQUEST_CODE;
import static com.ezetapsdk.EzeConstants.WALLET_TRANSACTION;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.eze.api.EzeAPI;
import com.eze.api.EzeAPIConstants.EzetapErrors;
import com.eze.api.EzetapUserConfig;
import com.ezetap.printer.EPrintStatus;
import com.ezetap.printer.EPrinterImplementation;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author TIJO THOMAS
 * @since 19/04/23
 */
public class RNEzetapSdkModule extends ReactContextBaseJavaModule implements ActivityEventListener {

    private Promise mPromise;

    public RNEzetapSdkModule(ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addActivityEventListener(this);
    }


    @NonNull
    @Override
    public String getName() {
        return "RNEzetapSdk";
    }


    /**
     * Check For Updates method is only relevant in the Android platform. It is invoked to check for
     * any updates to the Android Service app.
     */
    @ReactMethod
    private void checkUpdates(Promise promise) {
        mPromise = promise;
        EzeAPI.checkForUpdates(getCurrentActivity(), CHECK_FOR_UPDATES);
    }

    /**
     * Method to initialize the Ezetap transaction
     *
     * @param requestObject requestObject - The request object as defined in the API documentation
     */

    @ReactMethod
    private void initialize(String requestObject, Promise promise) {
        mPromise = promise;
        try {
            EzeAPI.initialize(getCurrentActivity(), INITIALIZE_REQUEST_CODE,
                new JSONObject(requestObject));
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(e.getCause());
        }
    }

    /**
     * Method to close prepare device before any transaction
     */

    @ReactMethod
    private void prepareDevice(Promise promise) {
        mPromise = promise;
        EzeAPI.prepareDevice(getCurrentActivity(), PREPARE_DEVICE_REQUEST_CODE);
    }

    /**
     * Method to scan barcode and retrieve the result
     */
    @ReactMethod
    private void scanBarcode(Promise promise) {
        mPromise = promise;
        EzeAPI.scanBarcode(getCurrentActivity(), SCAN_BARCODE, null);
    }

    @ReactMethod
    private void getCardInfo(Promise promise) {
        mPromise = promise;
        EzeAPI.getCardInfo(getCurrentActivity(), GET_CARD_INFO);
    }

    @ReactMethod
    private void getLoyaltyCardInfo(String requestObject, Promise promise) {
        mPromise = promise;
        try {
            EzeAPI.getLoyaltyCardInfo(getCurrentActivity(), GET_LOYALTY_CARD_INFO,
                new JSONObject(requestObject));
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(e.getCause());
        }
    }

    /**
     * Method to send transaction receipt to given mobile no/email id
     *
     * @param requestObject requestObject - The request object as defined in the API documentation
     */

    @ReactMethod
    private void sendReceipt(String requestObject, Promise promise) {
        mPromise = promise;
        try {
            EzeAPI.sendReceipt(getCurrentActivity(), SEND_RECEIPT_REQUEST_CODE,
                new JSONObject(requestObject));
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(e.getCause());
        }
    }


    @ReactMethod
    private void serviceRequest(String requestObject, Promise promise) {
        mPromise = promise;
        try {
            EzeAPI.serviceRequest(getCurrentActivity(), SERVICE_REQUEST_CODE,
                new JSONObject(requestObject));
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(e.getCause());
        }
    }


    /**
     * Method to search transaction(s)
     *
     * @param requestObject requestObject - The request object as defined in the API documentation
     */

    @ReactMethod
    private void searchTransaction(String requestObject, Promise promise) {
        mPromise = promise;
        try {
            EzeAPI.searchTransaction(getCurrentActivity(), SEARCH_TRANSACTION_REQUEST_CODE,
                new JSONObject(requestObject));
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(e.getCause());
        }
    }

    /**
     * Method to get transaction detail for a given External Reference
     *
     * @param externalReference externalReference - External reference number to look up
     */


    @ReactMethod
    private void getTransaction(String externalReference, Promise promise) {
        mPromise = promise;
        try {
            EzeAPI.getTransaction(getCurrentActivity(), GET_TRANSACTION_REQUEST_CODE,
                new JSONObject(externalReference));
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(e.getCause());
        }
    }

    /**
     * Method to if a previous card transaction is pending. A card transaction could be pending if
     * no response was received after the transaction was initiated or if there was an app crash
     * after the transaction was initiated. The Ezetap SDK checks for pending transactions at the
     * very next Ezetap API call, it is recommended that calling applications call this API at the
     * appropriate time for eg. - upon login or upon calling pay cash where the handling is not done
     * by the Ezetap system.
     */

    @ReactMethod
    private void checkForIncompleteTransaction(String json, Promise promise) {
        mPromise = promise;
        try {
            EzeAPI.checkForIncompleteTransaction(getCurrentActivity(),
                CHECK_INCOMPLETE_TRANSACTION_REQUEST_CODE, new JSONObject(json));
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(e.getCause());
        }
    }

    /**
     * Method to void transaction
     *
     * @param transactionID transactionID - The ID of the transaction which is to be voided
     */

    @ReactMethod
    private void voidTransaction(String transactionID, Promise promise) {
        mPromise = promise;
        EzeAPI.voidTransaction(getCurrentActivity(), VOID_TRANSACTION_REQUEST_CODE, transactionID);
    }

    /**
     * Method to attach Signature for a transaction
     *
     * @param requestObject requestObject - The request object as defined in the API documentation
     */

    @ReactMethod
    private void attachSignature(String requestObject, Promise promise) {
        mPromise = promise;
        try {
            EzeAPI.attachSignature(getCurrentActivity(), ATTACH_SIGNATURE_REQUEST_CODE,
                new JSONObject(requestObject));
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(e.getCause());
        }
    }


    /**
     * Method to start a generic pay
     *
     * @param requestObject requestObject - The request object as defined in the API documentation
     */

    @ReactMethod
    private void pay(String requestObject, Promise promise) {
        mPromise = promise;
        try {
            EzeAPI.pay(getCurrentActivity(), GENERIC_TRANSACTION_REQUEST_CODE,
                new JSONObject(requestObject));
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(e.getCause());
        }
    }


    /**
     * Method to start a Brand EMI transaction
     *
     * @param requestObject requestObject - The request object as defined in the API documentation
     */

    @ReactMethod
    private void brandEMITransaction(String requestObject, Promise promise) {
        mPromise = promise;
        try {
            EzeAPI.brandEMITransaction(getCurrentActivity(), BRAND_EMI_TRANSACTION_REQUEST_CODE,
                new JSONObject(requestObject));
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(e.getCause());
        }
    }

    /**
     * Method to start a Brand EMI transaction
     *
     * @param requestObject requestObject - The request object as defined in the API documentation
     */

    @ReactMethod
    private void normalEMITransaction(String requestObject, Promise promise) {
        mPromise = promise;
        try {
            EzeAPI.normalEMITransaction(getCurrentActivity(), NORMAL_EMI_TRANSACTION_REQUEST_CODE,
                new JSONObject(requestObject));
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(e.getCause());
        }
    }

    /**
     * Method to print a receipt for a particular transaction
     *
     * @param transactionID transactionID - The ID of the Ezetap txn
     */

    @ReactMethod
    private void printReceipt(String transactionID, Promise promise) {
        mPromise = promise;
        EzeAPI.printReceipt(getCurrentActivity(), PRINT_RECEIPT_REQUEST_CODE, transactionID);
    }

    /**
     * Method to print a bitmap image of the current view
     */

    @ReactMethod
    private void printBitmap(String arguments, Promise promise) {
        mPromise = promise;
        getCurrentActivity().runOnUiThread(() -> {
            String encodedImageData;
            if (arguments != null) {
                encodedImageData = arguments.toString();
            } else {
                encodedImageData = Utils.loadBitmapBase64EncodedFromView(getCurrentActivity());
            }
            if (!Utils.isDeviceX990()) {
                try {
                    if (EzetapUserConfig.getEzeUserConfig() != null) {
                        EPrinterImplementation ePrinter = EPrinterImplementation.getInstance();
                        byte[] imageBytes = Base64.decode(encodedImageData, Base64.DEFAULT);
                        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0,
                            imageBytes.length);
                        EPrintStatus status = ePrinter.init(getCurrentActivity());
                        if (status == EPrintStatus.SUCCESS) {
                            new Thread(() -> ePrinter.printBitmap(decodedImage,
                                (ePrintStatus, o) -> getCurrentActivity().runOnUiThread(
                                    () -> mPromise.resolve(o)))).start();
                        } else {
                            mPromise.reject("EZE_ERROR", status.getMessage());
                        }
                    } else {
                        mPromise.reject(EzetapErrors.ERROR_INIT_REQUIRED.getErrorCode(),
                            EzetapErrors.ERROR_INIT_REQUIRED.getErrorMessage());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    mPromise.reject("EZE_ERROR", ex.getMessage());
                }
            } else {
                JSONObject jsonRequest = new JSONObject();
                JSONObject jsonImageObj = new JSONObject();
                try {
                    jsonImageObj.put("imageData", encodedImageData);
                    jsonImageObj.put("imageType", "JPEG");
                    jsonImageObj.put("height", "");// optional
                    jsonImageObj.put("weight", "");// optional
                    jsonRequest.put("image", jsonImageObj);
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
    private void close(Promise promise) {
        mPromise = promise;
        EzeAPI.close(getCurrentActivity(), CLOSE_SESSION_REQUEST_CODE);
    }

    /**
     * Method to Initiate Pre Auth
     *
     * @param jsonObject jsonObject - All txnIds to be passed to stop the payment.
     */
    @ReactMethod
    private void preAuth(String jsonObject, Promise promise) {
        mPromise = promise;
        try {
            EzeAPI.preAuth(getCurrentActivity(), PRE_AUTH_REQUEST_CODE, new JSONObject(jsonObject));
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(e.getCause());
        }
    }

    /**
     * Method to Confirm Pre Auth
     *
     * @param jsonObject jsonObject - All txnIds to be passed to stop the payment.
     */
    @ReactMethod
    private void confirmPreAuth(String jsonObject, Promise promise) {
        mPromise = promise;
        try {
            EzeAPI.confirmPreAuth(getCurrentActivity(), CONFIRM_PRE_AUTH_REQUEST_CODE,
                new JSONObject(jsonObject));
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(e.getCause());
        }
    }

    /**
     * Method to Release Pre Auth
     *
     * @param jsonObject jsonObject - All txnIds to be passed to stop the payment.
     */
    @ReactMethod
    private void releasePreAuth(String jsonObject, Promise promise) {
        mPromise = promise;
        try {
            EzeAPI.releasePreAuth(getCurrentActivity(), RELEASE_PRE_AUTH_REQUEST_CODE,
                new JSONObject(jsonObject));
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(e.getCause());
        }
    }

    /**
     * Method to void transaction
     *
     * @param jsonObject jsonObject - All txnIds to be passed to stop the payment.
     */

    @ReactMethod
    private void stopPayment(String jsonObject, Promise promise) {
        mPromise = promise;
        try {
            EzeAPI.stopPayment(getCurrentActivity(), STOP_PAYMENT_REQUEST_CODE,
                new JSONObject(jsonObject));
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(e.getCause());
        }
    }

    @ReactMethod
    private void walletTransaction(String requestObject, Promise promise) {
        mPromise = promise;
        try {
            EzeAPI.walletTransaction(getCurrentActivity(), WALLET_TRANSACTION,
                new JSONObject(requestObject));
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(e.getCause());
        }
    }

    @ReactMethod
    private void upiTransaction(String requestObject, Promise promise) {
        mPromise = promise;
        try {
            EzeAPI.upiTransaction(getCurrentActivity(), UPI_TRANSACTION,
                new JSONObject(requestObject));
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(e.getCause());
        }
    }

    @ReactMethod
    private void qrCodeTransaction(String requestObject, Promise promise) {
        mPromise = promise;
        try {
            EzeAPI.qrCodeTransaction(getCurrentActivity(), QR_CODE_TRANSACTION,
                new JSONObject(requestObject));
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(e.getCause());
        }
    }

    @ReactMethod
    private void chequeTransaction(String requestObject, Promise promise) {
        mPromise = promise;
        try {
            EzeAPI.walletTransaction(getCurrentActivity(), CHEQUE_TRANSACTION,
                new JSONObject(requestObject));
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(e.getCause());
        }
    }

    @ReactMethod
    private void refundTransaction(String requestObject, Promise promise) {
        mPromise = promise;
        try {
            EzeAPI.refundTransaction(getCurrentActivity(), REFUND_TRANSACTION,
                new JSONObject(requestObject));
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(e.getCause());
        }
    }

    @ReactMethod
    private void cardTransaction(String requestObject, Promise promise) {
        mPromise = promise;
        try {
            EzeAPI.cardTransaction(getCurrentActivity(), CARD_TRANSACTION,
                new JSONObject(requestObject));
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(e.getCause());
        }
    }

    @ReactMethod
    private void cashTransaction(String requestObject, Promise promise) {
        mPromise = promise;
        try {
            EzeAPI.cashTransaction(getCurrentActivity(), CASH_TRANSACTION,
                new JSONObject(requestObject));
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(e.getCause());
        }
    }

    @ReactMethod
    private void remotePayment(String requestObject, Promise promise) {
        mPromise = promise;
        try {
            EzeAPI.remotePayment(getCurrentActivity(), REMOTE_TRANSACTION,
                new JSONObject(requestObject));
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(e.getCause());
        }
    }

    @ReactMethod
    private void displayTransactionHistory(String requestObject, Promise promise) {
        mPromise = promise;
        try {
            EzeAPI.displayTransactionHistory(getCurrentActivity(), DISPLAY_TRANSACTION_HISTORY,
                new JSONObject(requestObject));
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(e.getCause());
        }
    }

    /**
     * Do not remove this onActivityResult as it is an abstract method which calls the
     * ActivityListener onActivityResult Method onActivityResult will be called
     *
     * @param activity context of the Activity
     * @param requestCode Request code you wish to handle for the result in onActivityResult
     * method.
     */
    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode,
        @Nullable Intent intent) {
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
                case STOP_PAYMENT_REQUEST_CODE:
                case CARD_TRANSACTION:
                case CASH_TRANSACTION:
                case CHECK_FOR_UPDATES:
                case CHEQUE_TRANSACTION:
                case DISPLAY_TRANSACTION_HISTORY:
                case QR_CODE_TRANSACTION:
                case REFUND_TRANSACTION:
                case REMOTE_TRANSACTION:
                case SCAN_BARCODE:
                case UPI_TRANSACTION:
                case WALLET_TRANSACTION: {
                    if (intent.getExtras() != null) {
                        mPromise.resolve(Utils.createJsonObject(intent));
                    } else {
                        mPromise.reject(String.valueOf(requestCode),
                            new Exception("An Error has been occurred"));
                    }
                }
            }
        }
    }

    @Override
    public void onNewIntent(Intent intent) {

    }
}