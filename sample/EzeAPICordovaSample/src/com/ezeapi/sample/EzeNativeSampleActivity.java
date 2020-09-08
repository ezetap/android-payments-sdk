package com.ezeapi.sample;

import static com.ezeapi.sample.Setting.API_KEY;
import static com.ezeapi.sample.Setting.APP_MODE;
import static com.ezeapi.sample.Setting.MERCHANT_NAME;
import static com.ezeapi.sample.Setting.USER_NAME;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.eze.api.EzeAPI;
import com.ezetap.sdk.EzeConstants;
import java.io.ByteArrayOutputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EzeNativeSampleActivity extends Activity implements OnClickListener {

    /**
     * The response is sent back to your activity with a result code and request code based
     */
    private final int REQUEST_CODE_INITIALIZE = 10001;
    private final int REQUEST_CODE_PREPARE = 10002;
    private final int REQUEST_CODE_WALLET_TXN = 10003;
    private final int REQUEST_CODE_CHEQUE_TXN = 10004;
    private final int REQUEST_CODE_SALE_TXN = 10005;
    private final int REQUEST_CODE_CASH_BACK_TXN = 10006;
    private final int REQUEST_CODE_CASH_AT_POS_TXN = 10007;
    private final int REQUEST_CODE_CASH_TXN = 10008;
    private final int REQUEST_CODE_SEARCH = 10009;
    private final int REQUEST_CODE_VOID = 10010;
    private final int REQUEST_CODE_ATTACH_SIGN = 10011;
    private final int REQUEST_CODE_UPDATE = 10012;
    private final int REQUEST_CODE_CLOSE = 10013;
    private final int REQUEST_CODE_GET_TXN_DETAIL = 10014;
    private final int REQUEST_CODE_GET_INCOMPLETE_TXN = 10015;
    private final int REQUEST_CODE_PAY = 10016;
    private final int REQUEST_CODE_UPI = 10017;
    private final int REQUEST_CODE_REMOTE_PAY = 10018;
    private final int REQUEST_CODE_QR_CODE_PAY = 10019;
    private final int REQUEST_CODE_NORMAL_EMI = 10020;
    private final int REQUEST_CODE_BRAND_EMI = 10021;
    private final int REQUEST_CODE_PRINT_RECEIPT = 10022;
    private final int REQUEST_CODE_PRINT_BITMAP = 10023;
    private final int REQUEST_CODE_SERVICE_REQUEST = 10024;

    /**
     * The Base64 Image bitmap string for attach e-signature
     */
    private ImageView img;
    /**
     * unique ID for a transaction in EzeTap EMI Id associated with the transaction
     */
    private String strTxnId = null, emiID = null;
    /**
     * Error message
     */
    private String mandatoryErrMsg = "Please fill up mandatory params.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nativesample);
        img = (ImageView) findViewById(R.id.imgSign);
        img.buildDrawingCache();
        loadConfigFromPrefs();
    }

    private void loadConfigFromPrefs() {
        String merchant = Setting.getPrefs(MERCHANT_NAME, this);
        String apikey = Setting.getPrefs(API_KEY, this);
        String username = Setting.getPrefs(USER_NAME, this);
        String mode = Setting.getPrefs(APP_MODE, this);
        EzeConstants.AppMode appMode = EzeConstants.AppMode.EZETAP_DEMO;
        if (mode != null && mode.trim().length() > 0) {

        }
        if (merchant != null && apikey != null && mode != null && username != null) {
            Setting.config = new Setting.ConfigHolder(apikey, mode, username, merchant);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnInitialize:
                doInitializeEzeTap();
                break;
            case R.id.btnPrepare:
                doPrepareDeviceEzeTap();
                break;
            case R.id.btnWalletTxn:
                openPaymentPayloadPopup(REQUEST_CODE_WALLET_TXN);
                break;
            case R.id.btnChequeTxn:
                openPaymentPayloadPopup(REQUEST_CODE_CHEQUE_TXN);
                break;
            case R.id.btnSale:
                openPaymentPayloadPopup(REQUEST_CODE_SALE_TXN);
                break;
            case R.id.btnCashback:
                openPaymentPayloadPopup(REQUEST_CODE_CASH_BACK_TXN);
                break;
            case R.id.btnCashAtPOS:
                openPaymentPayloadPopup(REQUEST_CODE_CASH_AT_POS_TXN);
                break;
            case R.id.btnCashTxn:
                openPaymentPayloadPopup(REQUEST_CODE_CASH_TXN);
                break;
            case R.id.btnPay:
                openPaymentPayloadPopup(REQUEST_CODE_PAY);
                break;
            case R.id.btnRemotePay:
                openPaymentPayloadPopup(REQUEST_CODE_REMOTE_PAY);
                break;
            case R.id.btnSearchTxn:
                doSearchTxn();
                break;
            case R.id.btnVoidTxn:
                openTXNIdEnterPopup(REQUEST_CODE_VOID);
                break;
            case R.id.btnAttachSignature:
                openTXNIdEnterPopup(REQUEST_CODE_ATTACH_SIGN);
                break;
            case R.id.btnUpdate:
                doCheckUpdate();
                break;
            case R.id.btnCheckIncompleteTxn:
                doCheckIncompleteTxn();
                break;
            case R.id.btnGetTxnDetails:
                openTXNIdEnterPopup(REQUEST_CODE_GET_TXN_DETAIL);
                break;
            case R.id.btnUPITxn:
                openPaymentPayloadPopup(REQUEST_CODE_UPI);
                break;
            case R.id.btnQRCodeTxn:
                openPaymentPayloadPopup(REQUEST_CODE_QR_CODE_PAY);
                break;
            case R.id.btnNormalEMI:
                openPaymentPayloadPopup(REQUEST_CODE_NORMAL_EMI);
                break;
            case R.id.btnBrandEMI:
                openPaymentPayloadPopup(REQUEST_CODE_BRAND_EMI);
                break;
            case R.id.printBitmap:
                printBitmap();
                break;
            case R.id.printReceipt:
                openTXNIdEnterPopup(REQUEST_CODE_PRINT_RECEIPT);
                break;
            case REQUEST_CODE_PRINT_RECEIPT:
                printReceipt();
                break;
            case R.id.btnServiceRequest:
                openPaymentPayloadPopup(REQUEST_CODE_SERVICE_REQUEST);
                break;
            case R.id.btnClose:
                doCloseEzetap();
                break;
            default:
                break;
        }
    }

    /**
     * invoke to initialize the SDK with the merchant key and the device (card reader) with bank
     * keys
     */
    private void doInitializeEzeTap() {
        /**********************************************
         {
         "demoAppKey": "your demo app key",
         "prodAppKey": "your prod app key",
         "merchantName": "your merchant name",
         "userName": "your user name",
         "currencyCode": "INR",
         "appMode": "DEMO/PROD",
         "captureSignature": "true/false",
         "prepareDevice": "true/false"
         }
         **********************************************/
        JSONObject jsonRequest = new JSONObject();
        if (Setting.config != null) {
            try {
                jsonRequest.put("demoAppKey", Setting.config.getAppKey());
                jsonRequest.put("prodAppKey", Setting.config.getAppKey());
                jsonRequest.put("merchantName", Setting.config.getMerchantName());
                jsonRequest.put("userName", Setting.config.getUsername());
                jsonRequest.put("currencyCode", "INR");
                jsonRequest.put("appMode", Setting.config.getAppMode());
                jsonRequest.put("captureSignature", "true");
                jsonRequest.put("prepareDevice", "false");
                jsonRequest.put("captureReceipt", "false");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                jsonRequest.put("demoAppKey", "your demo appkey");
                jsonRequest.put("prodAppKey", "your prod appkey");
                jsonRequest.put("merchantName", "your organization name");
                jsonRequest.put("userName", "unique identifier of your user");
                jsonRequest.put("currencyCode", "INR");
                jsonRequest.put("appMode", "Demo");
                jsonRequest.put("captureSignature", "true");
                jsonRequest.put("prepareDevice", "false");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        EzeAPI.initialize(this, REQUEST_CODE_INITIALIZE, jsonRequest);
    }

    /**
     * optional mechanism to prepare a device for card transactions
     */
    private void doPrepareDeviceEzeTap() {
        EzeAPI.prepareDevice(this, REQUEST_CODE_PREPARE);
    }

    /**
     * Ability to take wallet transaction for Mobiquick, Freecharge, Novopay etc.
     */
    private void doWalletTxn(JSONObject jsonRequest) {
        /*******************************************
         {
         "amount": "123",
         "options": {
         "references": {
         "reference1": "1234",
         "additionalReferences": [
         "addRef_xx1",
         "addRef_xx2"
         ]
         },
         "customer": {
         "name": "xyz",
         "mobileNo": "1234567890",
         "email": "abc@xyz.com"
         }
         }
         }
         *******************************************/
        EzeAPI
            .walletTransaction(EzeNativeSampleActivity.this, REQUEST_CODE_WALLET_TXN, jsonRequest);
    }

    /**
     * Records cheque transaction
     */
    private void doChequeTxn(JSONObject jsonRequest) {
        /*****************************************
         {
         "amount": "123",
         "options": {
         "references": {
         "reference1": "1234",
         "additionalReferences": [
         "addRef_xx1",
         "addRef_xx2"
         ]
         },
         "customer": {
         "name": "xyz",
         "mobileNo": "1234567890",
         "email": "abc@xyz.com"
         }
         },
         "cheque": {
         "chequeNumber": "1234",
         "bankCode": "1234",
         "bankName": "xyz",
         "bankAccountNo": "1234",
         "chequeDate": "YYYY-MM-DD"
         }
         }
         *****************************************/
        EzeAPI.chequeTransaction(this, REQUEST_CODE_CHEQUE_TXN, jsonRequest);
    }

    /**
     * Take credit card transactions for Visa, Mastercard and Rupay. Debit card transactions for
     * Indian banks. Ability to perform EMI option.
     */
    private void doSaleTxn(JSONObject jsonRequest) {
        /******************************************
         {
         "amount": "123",
         "options": {
         "amountCashback": 0,
         "amountTip": 0,
         "references": {
         "reference1": "1234",
         "additionalReferences": [
         "addRef_xx1",
         "addRef_xx2"
         ]
         },
         "customer": {
         "name": "xyz",
         "mobileNo": "1234567890",
         "email": "abc@xyz.com"
         }
         },
         "mode": "SALE"
         }
         ******************************************/
        EzeAPI.cardTransaction(this, REQUEST_CODE_SALE_TXN, jsonRequest);
    }

    private void doNormalEMITxn(JSONObject jsonRequest) {
        /******************************************
         {
         "amount": "123",
         "options": {
         "amountCashback": 0,
         "amountTip": 0,
         "references": {
         "reference1": "1234",
         "additionalReferences": [
         "addRef_xx1",
         "addRef_xx2"
         ]
         },
         "customer": {
         "name": "xyz",
         "mobileNo": "1234567890",
         "email": "abc@xyz.com"
         }
         }
         }
         ******************************************/
        EzeAPI.normalEMITransaction(this, REQUEST_CODE_NORMAL_EMI, jsonRequest);
    }

    private void doBrandEMITxn(JSONObject jsonRequest) {
        /******************************************
         {
         "amount": "123",
         "options": {
         "amountCashback": 0,
         "amountTip": 0,
         "references": {
         "reference1": "1234",
         "additionalReferences": [
         "addRef_xx1",
         "addRef_xx2"
         ]
         },
         "customer": {
         "name": "xyz",
         "mobileNo": "1234567890",
         "email": "abc@xyz.com"
         },
         "productDetails": {
         "brand": "NOKIA",
         "SKUCode": "Nokia6MB",
         "serial": "1233442323131331"
         }
         }
         }
         ******************************************/
        EzeAPI.brandEMITransaction(this, REQUEST_CODE_BRAND_EMI, jsonRequest);
    }


    /**
     * Take credit card transactions for Visa, Mastercard and Rupay. Debit card transactions for
     * Indian banks. Ability to perform cashback option.
     */
    private void doCashbackTxn(JSONObject jsonRequest) {
        /******************************************
         {
         "amount": "123",
         "options": {
         "amountCashback": 100,
         "amountTip": 0,
         "references": {
         "reference1": "1234",
         "additionalReferences": [
         "addRef_xx1",
         "addRef_xx2"
         ]
         },
         "customer": {
         "name": "xyz",
         "mobileNo": "1234567890",
         "email": "abc@xyz.com"
         }
         },
         "mode": "CASHBACK"
         }
         ******************************************/
        EzeAPI.cardTransaction(this, REQUEST_CODE_CASH_BACK_TXN, jsonRequest);
    }

    /**
     * Take credit card transactions for Visa, Mastercard and Rupay. Debit card transactions for
     * Indian banks. Ability to perform cash@pos option.
     */
    private void doCashAtPosTxn(JSONObject jsonRequest) {
        /******************************************
         {
         "amount": "0",
         "options": {
         "amountCashback": 100,
         "amountTip": 0,
         "references": {
         "reference1": "1234",
         "additionalReferences": [
         "addRef_xx1",
         "addRef_xx2"
         ]
         },
         "customer": {
         "name": "xyz",
         "mobileNo": "1234567890",
         "email": "abc@xyz.com"
         }
         },
         "mode": "CASH@POS"
         }
         ******************************************/
        EzeAPI.cardTransaction(this, REQUEST_CODE_CASH_AT_POS_TXN, jsonRequest);
    }

    /**
     * Ability to record cash transaction
     */
    private void doCashTxn(JSONObject jsonRequest) {
        /******************************************
         {
         "amount": "123",
         "options": {
         "references": {
         "reference1": "1234",
         "additionalReferences": [
         "addRef_xx1",
         "addRef_xx2"
         ]
         },
         "customer": {
         "name": "xyz",
         "mobileNo": "1234567890",
         "email": "abc@xyz.com"
         }
         }
         }
         ******************************************/
        EzeAPI.cashTransaction(this, REQUEST_CODE_CASH_TXN, jsonRequest);
    }

    /**
     * Ability to perform upi transaction
     */
    private void doUPITxn(JSONObject jsonRequest) {
        /******************************************
         {
         "amount": "123",
         "options": {
         "references": {
         "reference1": "1234",
         "additionalReferences": [
         "addRef_xx1",
         "addRef_xx2"
         ]
         },
         "customer": {
         "name": "xyz",
         "mobileNo": "1234567890",
         "email": "abc@xyz.com"
         }
         }
         }
         ******************************************/
        EzeAPI.upiTransaction(this, REQUEST_CODE_UPI, jsonRequest);
    }

    private void doPay(JSONObject jsonRequest) {
        /******************************************
         {
         "amount": "123",
         "options": {
         "references": {
         "reference1": "1234",
         "additionalReferences": [
         "addRef_xx1",
         "addRef_xx2"
         ]
         },
         "customer": {
         "name": "xyz",
         "mobileNo": "1234567890",
         "email": "abc@xyz.com"
         }
         }
         }
         ******************************************/
        EzeAPI.pay(this, REQUEST_CODE_PAY, jsonRequest);
    }

    /**
     * Ability to perform upi transaction
     */
    private void doRemotePayTxn(JSONObject jsonRequest) {
        /******************************************
         {
         "amount": "123",
         "options": {
         "references": {
         "reference1": "1234",
         "additionalReferences": [
         "addRef_xx1",
         "addRef_xx2"
         ]
         },
         "customer": {
         "name": "xyz",
         "mobileNo": "1234567890",
         "email": "abc@xyz.com"
         }
         }
         }
         ******************************************/
        EzeAPI.remotePayment(this, REQUEST_CODE_REMOTE_PAY, jsonRequest);
    }

    /**
     * Ability to perform upi transaction
     */
    private void doQrCodePayTxn(JSONObject jsonRequest) {
        /******************************************
         {
         "amount": "123",
         "options": {
         "references": {
         "reference1": "1234",
         "additionalReferences": [
         "addRef_xx1",
         "addRef_xx2"
         ]
         },
         "customer": {
         "name": "xyz",
         "mobileNo": "1234567890",
         "email": "abc@xyz.com"
         }
         }
         }
         ******************************************/
        EzeAPI.qrCodeTransaction(this, REQUEST_CODE_QR_CODE_PAY, jsonRequest);
    }


    /**
     * search transactions for a merchant based on certain search parameters
     */
    private void doSearchTxn() {
        /*********************************
         {
         "agentName": "Demo User"
         }
         *********************************/
        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("agentName", Setting.getPrefs(USER_NAME, this));
            EzeAPI.searchTransaction(this, REQUEST_CODE_SEARCH, jsonRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Void transaction method is invoked to void a payment transaction
     */
    private void doVoidTxn() {
		if (isTransactionIdValid()) {
			EzeAPI.voidTransaction(this, REQUEST_CODE_VOID,
				strTxnId);// pass your transaction id value here
		} else {
			displayToast("Inorrect txn Id, please make a Txn.");
		}

    }

    /**
     *
     */
    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        return Base64.encodeToString(byteFormat, Base64.NO_WRAP);
    }

    /**
     * Use this operation to attach an e-signature from the customer for payments
     */
    private void doAttachSignature() {
        /*******************************************
         {
         "tipAmount": 0,
         "image": {
         "imageData": "js9bsidvicbi3h",
         "imageType": "JPEG",
         "height": "",
         "weight": ""
         },
         "txnId": "12355356345"
         }
         *******************************************/
        JSONObject jsonRequest = new JSONObject();
        JSONObject jsonImageObj = new JSONObject();
        try {
            img.buildDrawingCache();
            Bitmap bmap = img.getDrawingCache();
            String encodedImageData = getEncoded64ImageStringFromBitmap(bmap);
            // Building Image Object
            jsonImageObj.put("imageData", encodedImageData);
            jsonImageObj.put("imageType", "JPEG");
            jsonImageObj.put("height", "");// optional
            jsonImageObj.put("weight", "");// optional
            // Building final request object
            // jsonRequest.put("emiId", emiID);// pass this field if you have an
            // EMI Id associated with the transaction
            jsonRequest.put("tipAmount", 0.00);// optional
            jsonRequest.put("image",
                jsonImageObj); // Pass this attribute when you have a valid captured signature image
            jsonRequest.put("txnId", strTxnId);// pass your transaction id value here
            if (strTxnId != null) {
                EzeAPI.attachSignature(this, REQUEST_CODE_ATTACH_SIGN, jsonRequest);
            } else {
                displayToast("Inorrect txn Id, please pass txnId");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Check For Updates method is only relevant in the Android platform. It is invoked to check for
     * any updates to the Android Service app.
     */
    private void doCheckUpdate() {
        EzeAPI.checkForUpdates(this, REQUEST_CODE_UPDATE);
    }

    /**
     * use this method to check the status of an incomplete transaction due to timeouts, network
     * errors etc.
     */
    private void doCheckIncompleteTxn() {
        EzeAPI.checkForIncompleteTransaction(this, REQUEST_CODE_GET_INCOMPLETE_TXN);
    }

    /**
     * Retrieve the details of a transaction given a transaction Id
     */
    private void doGetTxnDetails() {
        if (!strTxnId.equals(null)) {
            EzeAPI.getTransaction(this, REQUEST_CODE_GET_TXN_DETAIL,
                strTxnId);// pass your reference id value here
        } else {
            displayToast("Inorrect txn Id, please pass txnId");
        }
    }

    /**
     * closes the connection with Ezetap server and shut down gracefully
     */
    private void doCloseEzetap() {
        EzeAPI.close(this, REQUEST_CODE_CLOSE);
    }

    /**
     * @param message message for Toast
     */
    private void displayToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d("SampleAppLogs", "requestCode = " + requestCode + "resultCode = " + resultCode);
        try {
            if (intent != null && intent.hasExtra("response")) {
                Toast.makeText(this, intent.getStringExtra("response"), Toast.LENGTH_LONG).show();
                Log.d("SampleAppLogs", intent.getStringExtra("response"));
                TextView resultView = (TextView) findViewById(R.id.resultView);
                resultView.setVisibility(View.VISIBLE);
                resultView.setText(intent.getStringExtra("response"));
            }
            switch (requestCode) {
                case REQUEST_CODE_UPI:
                case REQUEST_CODE_CASH_TXN:
                case REQUEST_CODE_CASH_BACK_TXN:
                case REQUEST_CODE_CASH_AT_POS_TXN:
                case REQUEST_CODE_WALLET_TXN:
                case REQUEST_CODE_SALE_TXN:

                    if (resultCode == RESULT_OK) {
                        JSONObject response = new JSONObject(intent.getStringExtra("response"));
                        response = response.getJSONObject("result");
                        response = response.getJSONObject("txn");
                        strTxnId = response.getString("txnId");
                        emiID = response.getString("emiId");
                    } else if (resultCode == RESULT_CANCELED) {
                        JSONObject response = new JSONObject(intent.getStringExtra("response"));
                        response = response.getJSONObject("error");
                        String errorCode = response.getString("code");
                        String errorMessage = response.getString("message");
                    }

                    break;
                case REQUEST_CODE_PREPARE:
                    if (resultCode == RESULT_OK) {
                        JSONObject response = new JSONObject(intent.getStringExtra("response"));
                        response = response.getJSONObject("result");
                    } else if (resultCode == RESULT_CANCELED) {
                        JSONObject response = new JSONObject(intent.getStringExtra("response"));
                        response = response.getJSONObject("error");
                        String errorCode = response.getString("code");
                        String errorMessage = response.getString("message");
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @return transaction id is valid
     */
    private boolean isTransactionIdValid() {
		if (strTxnId == null) {
			return false;
		} else {
			return true;
		}
    }

    private void openTXNIdEnterPopup(final int requestCode) {
        try {
            LayoutInflater layoutInflater = LayoutInflater.from(EzeNativeSampleActivity.this);
            final View customView = layoutInflater.inflate(R.layout.txn_id_enter_popup, null);
            AlertDialog.Builder editCustomerPopup = new AlertDialog.Builder(
                EzeNativeSampleActivity.this);
            editCustomerPopup.setCancelable(false);
            editCustomerPopup.setView(customView);
            final AlertDialog alertDialog = editCustomerPopup.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();

            Button cancelButton = (Button) customView.findViewById(R.id.cancel_button);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();
                }
            });

            final EditText txnIdEditText = (EditText) customView.findViewById(R.id.txn_id_number);
            Button confirmButton = (Button) customView.findViewById(R.id.confirm_button);
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    strTxnId = txnIdEditText.getText().toString() + "";
                    switch (requestCode) {
                        case REQUEST_CODE_VOID:
                            doVoidTxn();
                            break;
                        case REQUEST_CODE_GET_TXN_DETAIL:
                            doGetTxnDetails();
                            break;
                        case REQUEST_CODE_ATTACH_SIGN:
                            doAttachSignature();
                            break;
                        case REQUEST_CODE_PRINT_RECEIPT:
                            printReceipt();
                            break;
                    }
                    alertDialog.cancel();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openPaymentPayloadPopup(final int REQUEST_CODE) {
        try {
            LayoutInflater layoutInflater = LayoutInflater.from(EzeNativeSampleActivity.this);
            final View customView = layoutInflater.inflate(R.layout.payment_payload_popup, null);
            AlertDialog.Builder editCustomerPopup = new AlertDialog.Builder(
                EzeNativeSampleActivity.this);
            editCustomerPopup.setCancelable(false);
            editCustomerPopup.setView(customView);
            final AlertDialog alertDialog = editCustomerPopup.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();

            Button cancelButton = (Button) customView.findViewById(R.id.cancel_button);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();
                }
            });

            final EditText customerNameEditText = (EditText) customView
                .findViewById(R.id.user_name);
            final EditText emailIdEditText = (EditText) customView.findViewById(R.id.user_email);
            final EditText mobileNumberEditText = (EditText) customView
                .findViewById(R.id.user_mobile);
            final EditText orderNumberEditText = (EditText) customView
                .findViewById(R.id.order_number);
            final EditText externalReference2 = (EditText) customView.findViewById(R.id.ref2);
            final EditText externalReference3 = (EditText) customView.findViewById(R.id.ref3);
            final EditText externalReferences = (EditText) customView
                .findViewById(R.id.text_external_refs);
            final EditText payableAmountEditText = (EditText) customView
                .findViewById(R.id.payable_amount);
            final EditText cashBackAmountEditText = (EditText) customView
                .findViewById(R.id.cashback_amount);
            final EditText productBrandEditText = (EditText) customView
                .findViewById(R.id.product_brand);
            final EditText productCodeEditText = (EditText) customView
                .findViewById(R.id.product_sku);
            final EditText productSerialEditText = (EditText) customView
                .findViewById(R.id.product_serial);
            final EditText accountLabelEditTet = (EditText) customView.findViewById(R.id.acc_lab);
            final EditText serviceFeeEditText = (EditText) customView.findViewById(R.id.serv_fee);
            final EditText paymentByEditText = (EditText) customView.findViewById(R.id.pay_by);
            final EditText merchantPhonenumberEditText = (EditText) customView
                .findViewById(R.id.merchant_phone_number);
            final EditText IssueType = (EditText) customView.findViewById(R.id.issueType);
            final EditText IssueInfo = (EditText) customView.findViewById(R.id.issueInfo);
            final EditText tags = (EditText) customView.findViewById(R.id.tags);
            final EditText merchantEmailId = (EditText) customView
                .findViewById(R.id.merchant_email);

            if (REQUEST_CODE == REQUEST_CODE_SERVICE_REQUEST) {
                customerNameEditText.setVisibility(View.GONE);
                emailIdEditText.setVisibility(View.GONE);
                IssueType.setVisibility(View.VISIBLE);
                IssueInfo.setVisibility(View.VISIBLE);
                tags.setVisibility(View.VISIBLE);
                mobileNumberEditText.setVisibility(View.GONE);
                orderNumberEditText.setVisibility(View.GONE);
                payableAmountEditText.setVisibility(View.GONE);
                cashBackAmountEditText.setVisibility(View.GONE);
                productBrandEditText.setVisibility(View.GONE);
                productSerialEditText.setVisibility(View.GONE);
                accountLabelEditTet.setVisibility(View.GONE);
                serviceFeeEditText.setVisibility(View.GONE);
                paymentByEditText.setVisibility(View.GONE);
                productCodeEditText.setVisibility(View.GONE);
                externalReference2.setVisibility(View.GONE);
                externalReference3.setVisibility(View.GONE);
                externalReferences.setVisibility(View.GONE);
            }
            if (REQUEST_CODE == REQUEST_CODE_CASH_BACK_TXN
                || REQUEST_CODE == REQUEST_CODE_CASH_AT_POS_TXN
                || REQUEST_CODE == REQUEST_CODE_BRAND_EMI
                || REQUEST_CODE == REQUEST_CODE_NORMAL_EMI) {
                serviceFeeEditText.setVisibility(View.GONE);
                paymentByEditText.setVisibility(View.GONE);
                accountLabelEditTet.setVisibility(View.GONE);
            }
            if (REQUEST_CODE == REQUEST_CODE_CASH_AT_POS_TXN) {
                payableAmountEditText.setVisibility(View.GONE);
            }

            if (REQUEST_CODE == REQUEST_CODE_BRAND_EMI || REQUEST_CODE == REQUEST_CODE_PAY) {
                productBrandEditText.setVisibility(View.VISIBLE);
                productCodeEditText.setVisibility(View.VISIBLE);
                productSerialEditText.setVisibility(View.VISIBLE);
                cashBackAmountEditText.setVisibility(View.GONE);
            }
            Button confirmButton = (Button) customView.findViewById(R.id.confirm_button);
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((REQUEST_CODE != REQUEST_CODE_SERVICE_REQUEST) && (
                        orderNumberEditText.getText().toString().equalsIgnoreCase("")
                            || payableAmountEditText.getText().toString().equalsIgnoreCase("")
                            && (REQUEST_CODE != REQUEST_CODE_CASH_AT_POS_TXN))) {
                        displayToast(mandatoryErrMsg);
                        return;
                    }
                    try {
                        JSONObject jsonRequest = new JSONObject();
                        JSONObject jsonOptionalParams = new JSONObject();
                        JSONObject jsonReferences = new JSONObject();
                        JSONObject jsonCustomer = new JSONObject();
                        jsonRequest.put("issueType", IssueType.getText().toString().trim());
                        jsonRequest.put("issueInfo", IssueInfo.getText().toString().trim());
                        JSONArray tagArray = new JSONArray();
                        String[] tag = tags.getText().toString().split("\n");
                        for (String t : tag) {
                            tagArray.put(t);
                        }
                        jsonRequest.put("tags", tagArray);
                        jsonRequest.put("merchant_phone_number",
                            merchantPhonenumberEditText.getText().toString().trim());
                        jsonRequest
                            .put("merchant_email", merchantEmailId.getText().toString().trim());
                        // Building Customer Object
                        jsonCustomer.put("name", customerNameEditText.getText().toString().trim());
                        jsonCustomer
                            .put("mobileNo", mobileNumberEditText.getText().toString().trim());
                        jsonCustomer.put("email", emailIdEditText.getText().toString().trim());

                        // Building References Object
                        jsonReferences
                            .put("reference1", orderNumberEditText.getText().toString().trim());
                        jsonReferences
                            .put("reference2", externalReference2.getText().toString().trim());
                        jsonReferences
                            .put("reference3", externalReference3.getText().toString().trim());

                        // Passing Additional References
                        JSONArray array = new JSONArray();
                        String[] externalRefs = externalReferences.getText().toString().split("\n");
                        for (String externalRef : externalRefs) {
                            array.put(externalRef);
                        }
                        jsonReferences.put("additionalReferences", array);

                        // Building Optional params Object
                        jsonOptionalParams.put("amountCashback",
                            cashBackAmountEditText.getText().toString() + "");// Cannot
                        // have
                        // amount cashback in SALE transaction.
                        jsonOptionalParams.put("amountTip", 0.00);
                        jsonOptionalParams.put("references", jsonReferences);
                        jsonOptionalParams.put("customer", jsonCustomer);

                        // Service Fee
                        double serviceFee = -1.0;
                        String paymentBy = null;
                        if (serviceFeeEditText.getText().toString().length() > 0) {
                            serviceFee = Double
                                .parseDouble(serviceFeeEditText.getText().toString());
                        }
                        if (paymentByEditText.getText().toString().length() > 0) {
                            paymentBy = paymentByEditText.getText().toString();
                        }
                        jsonOptionalParams.put("serviceFee", serviceFee);
                        jsonOptionalParams.put("paymentBy", paymentBy);

                        // Pay to Account
                        String accountLabel = null;
                        if (accountLabelEditTet.getText().toString().length() > 0) {
                            accountLabel = accountLabelEditTet.getText().toString();
                        }
                        jsonOptionalParams.put("payToAccount", accountLabel);

                        if (REQUEST_CODE == REQUEST_CODE_BRAND_EMI
                            || REQUEST_CODE == REQUEST_CODE_PAY) {
                            JSONObject brandDetails = new JSONObject();
                            brandDetails
                                .put("SKUCode", productCodeEditText.getText().toString().trim());
                            brandDetails
                                .put("brand", productBrandEditText.getText().toString().trim());
                            brandDetails
                                .put("serial", productSerialEditText.getText().toString().trim());
                            jsonOptionalParams.put("productDetails", brandDetails);
                        }

                        JSONObject addlData = new JSONObject();
                        addlData.put("addl1", "addl1");
                        addlData.put("addl2", "addl2");
                        addlData.put("addl3", "addl3");
                        jsonOptionalParams.put("addlData", addlData);

                        JSONObject appData = new JSONObject();
                        appData.put("app1", "app1");
                        appData.put("app2", "app2");
                        appData.put("app3", "app3");
                        jsonOptionalParams.put("appData", appData);

                        // Building final request object
                        jsonRequest
                            .put("amount", payableAmountEditText.getText().toString().trim());
                        jsonRequest.put("options", jsonOptionalParams);

                        InputMethodManager imm = (InputMethodManager) EzeNativeSampleActivity.this
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(emailIdEditText.getWindowToken(), 0);

                        switch (REQUEST_CODE) {
                            case REQUEST_CODE_WALLET_TXN:
                                doWalletTxn(jsonRequest);
                                break;
                            case REQUEST_CODE_CHEQUE_TXN:
                                // Building Cheque Object
                                JSONObject jsonCheque = new JSONObject();
                                jsonCheque.put("chequeNumber", "125441");
                                jsonCheque.put("bankCode", "TEST0001233");
                                jsonCheque.put("bankName", "TEST Bank");
                                jsonCheque.put("bankAccountNo", "1234567890");
                                jsonCheque.put("chequeDate", "2017-12-10");

                                jsonRequest.put("cheque", jsonCheque);

                                doChequeTxn(jsonRequest);
                                break;
                            case REQUEST_CODE_SALE_TXN:
                                jsonRequest.put("mode", "SALE");//Card payment Mode
                                doSaleTxn(jsonRequest);
                                break;
                            case REQUEST_CODE_CASH_BACK_TXN:
                                jsonRequest.put("mode", "CASHBACK");//Card payment Mode
                                doCashbackTxn(jsonRequest);
                                break;
                            case REQUEST_CODE_CASH_AT_POS_TXN:
                                jsonRequest.put("mode", "CASH@POS");//Card payment Mode
                                doCashAtPosTxn(jsonRequest);
                                break;
                            case REQUEST_CODE_CASH_TXN:
                                doCashTxn(jsonRequest);
                                break;
                            case REQUEST_CODE_PAY:
                                doPay(jsonRequest);
                                break;
                            case REQUEST_CODE_UPI:
                                doUPITxn(jsonRequest);
                                break;
                            case REQUEST_CODE_REMOTE_PAY:
                                doRemotePayTxn(jsonRequest);
                                break;
                            case REQUEST_CODE_QR_CODE_PAY:
                                doQrCodePayTxn(jsonRequest);
                                break;
                            case REQUEST_CODE_NORMAL_EMI:
                                doNormalEMITxn(jsonRequest);
                                break;
                            case REQUEST_CODE_BRAND_EMI:
                                doBrandEMITxn(jsonRequest);
                                break;
                            case REQUEST_CODE_SERVICE_REQUEST:
                                callServiceRequest(jsonRequest);
                                break;
                        }
                        alertDialog.cancel();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Print Receipt method is invoked to print receipt for a payment transaction
     */
    private void printReceipt() {

        if (isTransactionIdValid()) {

            EzeAPI.printReceipt(this, REQUEST_CODE_PRINT_RECEIPT,
                strTxnId);// pass your transaction id value here

        } else {
            displayToast("Inorrect txn Id, please make a Txn.");
        }

    }

    /**
     * Use this operation to print a bitmap image
     */

    private void printBitmap() {

        /*******************************************
         {
         "image": {
         "imageData": "js9bsidvicbi3h",
         "imageType": "JPEG",
         "height": "",
         "weight": ""
         }
         }
         ******************************************/

        JSONObject jsonRequest = new JSONObject();

        JSONObject jsonImageObj = new JSONObject();

        try {

            img.buildDrawingCache();

            Bitmap bmap = img.getDrawingCache();

            String encodedImageData = getEncoded64ImageStringFromBitmap(bmap);

            // Building Image Object

            jsonImageObj.put("imageData", encodedImageData);

            jsonImageObj.put("imageType", "JPEG");

            jsonImageObj.put("height", "");// optional

            jsonImageObj.put("weight", "");// optional

            jsonRequest.put("image",
                jsonImageObj); // Pass this attribute when you have a valid captured signature image

            EzeAPI.printBitmap(this, REQUEST_CODE_PRINT_BITMAP, jsonRequest);

        } catch (JSONException e) {

            e.printStackTrace();
        }
    }

    private void callServiceRequest(JSONObject requestObject) {
        System.out.println("CallServiceRequest " + requestObject.toString());
        EzeAPI.serviceRequest(this, REQUEST_CODE_SERVICE_REQUEST, requestObject);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
