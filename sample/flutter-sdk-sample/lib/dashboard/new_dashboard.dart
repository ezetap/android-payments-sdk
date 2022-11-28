import 'dart:convert';

import 'package:custom_print_ezetap/dashboard/more_details.dart';
import 'package:custom_print_ezetap/dashboard/txn_details.dart';
import 'package:custom_print_ezetap/helper/constants.dart';
import 'package:custom_print_ezetap/helper/styles.dart';
import 'package:ezetap_sdk/ezetap_sdk.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:screenshot/screenshot.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../bill_widget.dart';

class NewDashboardScreen extends StatefulWidget {
  const NewDashboardScreen({Key? key}) : super(key: key);

  @override
  State<NewDashboardScreen> createState() => _NewDashboardScreenState();
}

class _NewDashboardScreenState extends State<NewDashboardScreen> {
  final methodArray = {
    "initialize",
    "prepareDevice",
    "sendReceipt",
    "serviceRequest",
    "searchTransaction",
    "getTransactionDetails",
    "checkForIncompleteTransaction",
    "voidTransaction",
    "attachSignature",
    "pay",
    "brandEMITransaction",
    "normalEMITransaction",
    "printReceipt",
    "printBitmap",
    "logout",
    "preAuth",
    "confirmPreAuth",
    "releasePreAuth",
    "stopPayment",
    "scanBarcode",
 /*   "getCardInfo",
    "getLoyaltyCardInfo",*/
    "walletTransaction",
    "chequeTransaction",
    "cardTransaction - sale",
    "cardTransaction - cashback",
    "cardTransaction - cash@POS",
    "cashTransaction",
    "remotePayment",
  /*  "displayTransactionHistory",*/
    "upiTransaction",
    "qrCodeTransaction",
    "refundTransaction"
  };
  var apiKey = "";
  var merchantName = "";
  var userName = "";
  var appMode = "";

  var mResult = "";

  @override
  void initState() {
    getSettings();
    super.initState();
  }

  Future<void> getSettings() async {
    var sharedPref = await SharedPreferences.getInstance();
    setState(() {
      apiKey = sharedPref.getString(API_KEY)!;
      merchantName = sharedPref.getString(MERCHANT_NAME)!;
      userName = sharedPref.getString(USERNAME)!;
      appMode = sharedPref.getString(APP_MODE)!;
      debugPrint(apiKey + " " + merchantName + " " + userName + " " + appMode);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(
          "Dashboard",
          style: textStyle,
        ),
      ),
      body: Builder(builder: (context) {
        return Padding(
          padding: const EdgeInsets.all(18.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              Flexible(
                flex: 1,
                child: ListView.builder(
                    itemCount: methodArray.length,
                    itemBuilder: (ctx, index) {
                      return Column(
                        crossAxisAlignment: CrossAxisAlignment.stretch,
                        children: [
                          ElevatedButton(
                            style: flatButtonStyle,
                            onPressed: () {
                              switch (index) {
                                case 0:
                                  _initializeSDK(context);
                                  break;
                                case 1:
                                  _prepareDevice(context);
                                  break;
                                case 2:
                                  openTXNIdEnterPopup(
                                      REQUEST_CODE_SEND_RECEIPT);
                                  break;
                                case 3:
                                  openPaymentPayloadPopup(
                                      REQUEST_CODE_SERVICE_REQUEST);
                                  break;
                                case 4:
                                  doSearchTxn();
                                  break;
                                case 5:
                                  openPaymentPayloadPopup(
                                      REQUEST_CODE_GET_TXN_DETAIL);
                                  break;
                                case 6:
                                  doCheckIncompleteTxn();
                                  break;
                                case 7:
                                  openTXNIdEnterPopup(REQUEST_CODE_VOID);
                                  break;
                                case 8:
                                  openTXNIdEnterPopup(REQUEST_CODE_ATTACH_SIGN);
                                  break;
                                case 9:
                                  openPaymentPayloadPopup(REQUEST_CODE_PAY);
                                  break;
                                case 10:
                                  openPaymentPayloadPopup(
                                      REQUEST_CODE_BRAND_EMI);
                                  break;
                                case 11:
                                  openPaymentPayloadPopup(
                                      REQUEST_CODE_NORMAL_EMI);
                                  break;
                                case 12:
                                  openTXNIdEnterPopup(
                                      REQUEST_CODE_PRINT_RECEIPT);
                                  break;
                                case 13:
                                  printBitmap();
                                  break;
                                case 14:
                                  doCloseEzetap();
                                  break;
                                case 15:
                                  openPaymentPayloadPopup(
                                      REQUEST_CODE_PRE_AUTH);
                                  break;
                                case 16:
                                  openPaymentPayloadPopup(
                                      REQUEST_CODE_CONFIRM_PRE_AUTH);
                                  break;
                                case 17 :
                                  openTXNIdEnterPopup(
                                      REQUEST_CODE_RELEASE_PRE_AUTH);
                                  break;
                                case 18:
                                  openPaymentPayloadPopup(
                                      REQUEST_CODE_STOP_PAYMENT);
                                  break;
                                case 19:
                                  scanBarCodeInApp();
                                  break;
                             /*   case 20:
                                  getCardInfoSwiped();
                                  break;*/
                                case 20:
                                  openPaymentPayloadPopup(
                                      REQUEST_CODE_WALLET_TXN);
                                  break;
                                case 21:
                                  openPaymentPayloadPopup(
                                      REQUEST_CODE_CHEQUE_TXN);
                                  break;
                                case 22:
                                  openPaymentPayloadPopup(
                                      REQUEST_CODE_SALE_TXN);
                                  break;
                                case 23:
                                  openPaymentPayloadPopup(
                                      REQUEST_CODE_CASH_BACK_TXN);
                                  break;
                                case 24:
                                  openPaymentPayloadPopup(
                                      REQUEST_CODE_CASH_AT_POS_TXN);
                                  break;
                                case 25:
                                  openPaymentPayloadPopup(
                                      REQUEST_CODE_CASH_TXN);
                                  break;
                                case 26:
                                  openPaymentPayloadPopup(
                                      REQUEST_CODE_REMOTE_PAY);
                                  break;
                                case 27:
                                  openPaymentPayloadPopup(REQUEST_CODE_UPI);
                                  break;
                                case 28:
                                  openPaymentPayloadPopup(
                                      REQUEST_CODE_QR_CODE_PAY);
                                  break;
                                case 29:
                                  openPaymentPayloadPopup(
                                      REQUEST_CODE_REFUND_TRANSACTION);
                                  break;
                              }
                            },
                            child: Text(
                              methodArray.elementAt(index),
                              style: textStyle,
                            ),
                          ),
                          const SizedBox(
                            height: 20,
                          ),
                        ],
                      );
                    }),
              ),
              const SizedBox(
                height: 20,
              ),
              SizedBox(
                height: 120,
                child: SingleChildScrollView(
                  child: Column(
                    children: [
                      const Text(
                        "Response :",
                        style: textStyleResult,
                      ),
                      Text(
                        mResult,
                        style: textStyleResult,
                      ),
                    ],
                  ),
                ),
              )
            ],
          ),
        );
      }),
    );
  }

  ///Method init
  Future<void> _initializeSDK(context) async {
    var json = {
      "prodAppKey": apiKey,
      "demoAppKey": apiKey,
      "merchantName": merchantName,
      "userName": userName,
      "currencyCode": 'INR',
      "appMode": appMode,
      "captureSignature": 'true',
      "prepareDevice": 'false',
      "captureReceipt": 'false'
    };
    debugPrint(jsonEncode(json));
    try {
      final String? result = await EzetapSdk.initialize(jsonEncode(json));
      setState(() {
        mResult = result!;
      });
    } on PlatformException catch (e) {
      setState(() {
        mResult = e.message!;
      });
    }
  }

  Future<void> _prepareDevice(context) async {
    try {
      final String? result = await EzetapSdk.prepareDevice();
      setState(() {
        mResult = result!;
      });
    } on PlatformException catch (e) {
      setState(() {
        mResult = e.message!;
      });
    }
  }

  Future<void> openPaymentPayloadPopup(requestCode) async {
    var result = await Navigator.of(context).push(MaterialPageRoute(
        builder: (context) => MoreDetailsScreen(requestCode: requestCode)));
    setState(() {
      mResult = result;
    });
  }

  Future<void> doCheckIncompleteTxn() async {
    var incompleteTxn = {};
    try {
      var result = await EzetapSdk.checkForIncompleteTransaction(incompleteTxn);
      setState(() {
        mResult = result;
      });
    } on PlatformException catch (e) {
      setState(() {
        mResult = e.message!;
      });
    }
  }

  Future<void> openTXNIdEnterPopup(requestCode) async {
    var result = await Navigator.of(context).push(MaterialPageRoute(
        builder: (context) => TxnDetailsScreen(requestCode: requestCode)));
    setState(() {
      mResult = result;
    });
  }

  Future<void> doCloseEzetap() async {
    try {
      var result = await EzetapSdk.logout();
      setState(() {
        mResult = result;
      });
      Navigator.of(context).pop();
    } on PlatformException catch (e) {
      setState(() {
        mResult = e.message!;
      });
    }
  }

  Future<void> doSearchTxn() async {
    var searchJson = {"agentName": userName, "saveLocally": false};
    debugPrint(jsonEncode(searchJson));
    try {
      final String? result =
      await EzetapSdk.searchTransaction(jsonEncode(searchJson));
      setState(() {
        mResult = result!;
      });
    } on PlatformException catch (e) {
      setState(() {
        mResult = e.message!;
      });
    }
  }

  Future<void> printBitmap() async {
    final controller = ScreenshotController();

    ///@author TIJO THOMAS
    ///BillWidget is a dummy receipt widget to create base64
    final bytes =
    await controller.captureFromWidget(const BillWidget(), pixelRatio: 2.0);
    var base64Image = base64Encode(bytes);
    try {
      final String? result = await EzetapSdk.printBitmap(base64Image);
      setState(() {
        mResult = result!;
      });
    } on PlatformException catch (e) {
      setState(() {
        mResult = e.message!;
      });
    }
  }

  Future<void> scanBarCodeInApp() async {
    try {
      final String? result = await EzetapSdk.scanBarcode();
      setState(() {
        mResult = result!;
      });
    } on PlatformException catch (e) {
      setState(() {
        mResult = e.message!;
      });
    }
  }

  Future<void> getCardInfoSwiped() async {
    var json ={};
    try {
      final String? result = await EzetapSdk.getCardInfo(jsonEncode(json));
      setState(() {
        mResult = result!;
      });
    } on PlatformException catch (e) {
      setState(() {
        mResult = e.message!;
      });
    }
  }

}
