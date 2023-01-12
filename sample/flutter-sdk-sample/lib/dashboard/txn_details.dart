import 'dart:convert';

import 'package:custom_print_ezetap/helper/common_snackbar.dart';
import 'package:custom_print_ezetap/helper/constants.dart';
import 'package:custom_print_ezetap/helper/styles.dart';
import 'package:custom_print_ezetap/model/error_model.dart';
import 'package:ezetap_sdk/ezetap_sdk.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:shared_preferences/shared_preferences.dart';

class TxnDetailsScreen extends StatefulWidget {
  final int requestCode;

  const TxnDetailsScreen({Key? key, required this.requestCode})
      : super(key: key);

  @override
  State<TxnDetailsScreen> createState() => _TxnDetailsScreenState();
}

class _TxnDetailsScreenState extends State<TxnDetailsScreen> {
  var apiKey = "";
  var merchantName = "";
  var userName = "";
  var appMode = "";

  var txnID = "";
  var mobileNo = "";
  var emailId = "";

  var emailMobileVisible = false;

  @override
  void initState() {
    getSettings();
    if (widget.requestCode == REQUEST_CODE_SEND_RECEIPT) {
      setState(() {
        emailMobileVisible = true;
      });
    }
    super.initState();
  }

  Future<void> getSettings() async {
    var sharedPref = await SharedPreferences.getInstance();
    apiKey = sharedPref.getString(API_KEY)!;
    merchantName = sharedPref.getString(MERCHANT_NAME)!;
    userName = sharedPref.getString(USERNAME)!;
    appMode = sharedPref.getString(APP_MODE)!;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(
          "Txn Details",
          style: textStyle,
        ),
      ),
      body: _buildOverlayContent(context),
    );
  }

  Widget _buildOverlayContent(context) {
    return Builder(builder: (context) {
      return Padding(
        padding: const EdgeInsets.all(18.0),
        child: Column(
          children: [
            Expanded(
                child: SingleChildScrollView(
              physics: const AlwaysScrollableScrollPhysics(),
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  const SizedBox(height: 20),
                  TextField(
                    onChanged: (val) {
                      txnID = val;
                    },
                    obscureText: false,
                    decoration: const InputDecoration(
                      border: OutlineInputBorder(),
                      labelText: '*Enter Transaction ID',
                    ),
                  ),
                  const SizedBox(height: 20),
                  Visibility(
                    visible: emailMobileVisible,
                    child: TextField(
                      keyboardType: TextInputType.phone,
                      onChanged: (val) {
                        mobileNo = val;
                      },
                      obscureText: false,
                      maxLength: 14,
                      decoration: const InputDecoration(
                        border: OutlineInputBorder(),
                        labelText: '*Enter Mobile Number',
                      ),
                    ),
                  ),
                  Visibility(
                      visible: emailMobileVisible,
                      child: const SizedBox(height: 20)),
                  Visibility(
                    visible: emailMobileVisible,
                    child: TextField(
                      keyboardType: TextInputType.emailAddress,
                      onChanged: (val) {
                        emailId = val;
                      },
                      obscureText: false,
                      decoration: const InputDecoration(
                        border: OutlineInputBorder(),
                        labelText: 'Enter Email ID',
                      ),
                    ),
                  )
                ],
              ),
            )),
            Visibility(
                visible: emailMobileVisible, child: const SizedBox(height: 20)),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                Flexible(
                  child: SizedBox(
                    width: double.infinity,
                    child: ElevatedButton(
                      style: flatButtonStyle,
                      onPressed: () {
                        Navigator.of(context).pop("Aborted");
                      },
                      child: const Text(
                        "CANCEL",
                        style: textStyle,
                      ),
                    ),
                  ),
                ),
                const SizedBox(
                  width: 20,
                ),
                Flexible(
                  child: SizedBox(
                    width: double.infinity,
                    child: ElevatedButton(
                      style: flatButtonStyle,
                      onPressed: () {
                        if (txnID.isEmpty) {
                          CommonSnackBar.showSnackBar(
                              context, "Txn ID mandatory");
                          return;
                        }
                        if (widget.requestCode == REQUEST_CODE_SEND_RECEIPT) {
                          if (mobileNo.isEmpty) {
                            CommonSnackBar.showSnackBar(
                                context, "mobile number is mandatory");
                            return;
                          }
                        }
                        switch (widget.requestCode) {
                          case REQUEST_CODE_VOID:
                            doVoidTxn(txnID, context);
                            break;
                          case REQUEST_CODE_ATTACH_SIGN:
                            doAttachSignature(txnID, context);
                            break;
                          case REQUEST_CODE_PRINT_RECEIPT:
                            doPrintReceipt(txnID, context);
                            break;
                          case REQUEST_CODE_SEND_RECEIPT:
                            doSendReceipt(txnID, context);
                            break;
                          case REQUEST_CODE_RELEASE_PRE_AUTH:
                            doReleasePreAuth(txnID, context);
                        }
                      },
                      child: const Text(
                        "OK",
                        style: textStyle,
                      ),
                    ),
                  ),
                )
              ],
            )
          ],
        ),
      );
    });
  }

  Future<void> doVoidTxn(String txnID,context) async {
    try {
      final String? result = await EzetapSdk.voidTransaction(txnID);
       findIfErrorInResponse(result, context);
    } on PlatformException catch (e) {
      Navigator.of(context).pop(e.message!);
    }
  }

  Future<void> doAttachSignature(String txnID,context) async {
    var json = {"txnId": txnID};
    try {
      final String? result = await EzetapSdk.attachSignature(jsonEncode(json));
       findIfErrorInResponse(result, context);
    } on PlatformException catch (e) {
      Navigator.of(context).pop(e.message!);
    }
  }

  Future<void> doPrintReceipt(String txnID,context) async {
    try {
      final String? result = await EzetapSdk.printReceipt(txnID);
       findIfErrorInResponse(result, context);
    } on PlatformException catch (e) {
      Navigator.of(context).pop(e.message!);
    }
  }

  Future<void> doSendReceipt(String txnID,context) async {
    var sendReceiptJson = {
      "customerName": userName,
      "mobileNo": mobileNo,
      "email": emailId,
      "txnId": txnID
    };
    try {
      final String? result =
          await EzetapSdk.sendReceipt(jsonEncode(sendReceiptJson));
       findIfErrorInResponse(result, context);
    } on PlatformException catch (e) {
      Navigator.of(context).pop(e.message!);
    }
  }

  Future<void> doReleasePreAuth(String txnID,context) async {
    var json = {"txnId": txnID};
    try {
      final String? result = await EzetapSdk.releasePreAuth(jsonEncode(json));
       findIfErrorInResponse(result, context);
    } on PlatformException catch (e) {
      Navigator.of(context).pop(e.message!);
    }
  }

  void findIfErrorInResponse(String? result, context) {
    final errorModel = errorModelFromJson(result!);
    if (errorModel.status == "fail") {
      CommonSnackBar.showSnackBar(
          context, "${errorModel.error?.code} \n${errorModel.error?.message}");
      Navigator.of(context).pop(result);
    } else {
      Navigator.of(context).pop(result);
    }
  }
}
