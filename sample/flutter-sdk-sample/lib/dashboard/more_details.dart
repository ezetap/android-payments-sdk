import 'dart:convert';

import 'package:custom_print_ezetap/helper/common_snackbar.dart';
import 'package:custom_print_ezetap/helper/constants.dart';
import 'package:custom_print_ezetap/helper/styles.dart';
import 'package:custom_print_ezetap/model/error_model.dart';
import 'package:ezetap_sdk/ezetap_sdk.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:shared_preferences/shared_preferences.dart';

class MoreDetailsScreen extends StatefulWidget {
  final int requestCode;

  const MoreDetailsScreen({Key? key, required this.requestCode})
      : super(key: key);

  @override
  State<MoreDetailsScreen> createState() => _MoreDetailsScreenState();
}

class _MoreDetailsScreenState extends State<MoreDetailsScreen> {
  var apiKey = "";
  var merchantName = "";
  var userName = "";
  var appMode = "";

  var serviceRequestVisible = false;
  var txnRequestVisible = true;
  var cashPosRequestVisible = true;
  var cashbackRequestVisible = true;
  var productRequestVisible = false;

  var customerNameEditText = "";
  var emailIdEditText = "";
  var mobileNumberEditText = "";
  var orderNumberEditText = "";
  var externalReference2 = "";
  var externalReference3 = "";
  var externalReferences = "";
  var payableAmountEditText = "";
  var cashBackAmountEditText = "";
  var productBrandEditText = "";
  var productCodeEditText = "";
  var productSerialEditText = "";
  var accountLabelEditTet = "";
  var serviceFeeEditText = "";
  var paymentByEditText = "";
  var merchantPhoneNumberEditText = "";
  var issueType = "";
  var issueInfo = "";
  var tags = "";
  var merchantEmailId = "";
  var stopPayList = "";
  String nullPaymentMode = "select payment mode";
  String? dropDownPaymentMode = "";

  var dropDownPaymentModeVisible = true;

  var walletAcquirer = "";
  var walletAcquirerVisible = false;

  var txnID = "";
  var refundTxnVisible = false;

  var amountLabel = "*Enter payable amount";
  var cashbackLabel = "Enter cashback amount";

  @override
  void initState() {
    dropDownPaymentMode = nullPaymentMode;
    getSettings();
    if (widget.requestCode == REQUEST_CODE_REFUND_TRANSACTION ||
        widget.requestCode == REQUEST_CODE_CONFIRM_PRE_AUTH) {
      setState(() {
        refundTxnVisible = true;
      });
    }
    if (widget.requestCode == REQUEST_CODE_SERVICE_REQUEST) {
      setState(() {
        serviceRequestVisible = true;
      });
    }
    if (widget.requestCode == REQUEST_CODE_CASH_BACK_TXN ||
        widget.requestCode == REQUEST_CODE_CASH_AT_POS_TXN ||
        widget.requestCode == REQUEST_CODE_BRAND_EMI ||
        widget.requestCode == REQUEST_CODE_NORMAL_EMI) {
      setState(() {
        txnRequestVisible = false;
      });
    }

    if (widget.requestCode == REQUEST_CODE_CASH_AT_POS_TXN) {
      setState(() {
        cashPosRequestVisible = false;
      });
    }

    if (widget.requestCode == REQUEST_CODE_BRAND_EMI ||
        widget.requestCode == REQUEST_CODE_PAY) {
      setState(() {
        cashbackRequestVisible = false;
        productRequestVisible = true;
      });
    }

    if (widget.requestCode == REQUEST_CODE_GET_TXN_DETAIL ||
        widget.requestCode == REQUEST_CODE_PRE_AUTH ||
        widget.requestCode == REQUEST_CODE_CONFIRM_PRE_AUTH ||
        widget.requestCode == REQUEST_CODE_RELEASE_PRE_AUTH ||
        widget.requestCode == REQUEST_CODE_GET_TXN_DETAIL_WITHOUT_STOP ||
        widget.requestCode == REQUEST_CODE_STOP_PAYMENT ||
        widget.requestCode == REQUEST_CODE_CASH_AT_POS_TXN ||
        widget.requestCode == REQUEST_CODE_CASH_BACK_TXN ||
        widget.requestCode == REQUEST_CODE_REFUND_TRANSACTION ||
        widget.requestCode == REQUEST_CODE_SERVICE_REQUEST) {
      setState(() {
        dropDownPaymentModeVisible = false;
      });
    }

    if (widget.requestCode == REQUEST_CODE_REFUND_TRANSACTION) {
      setState(() {
        amountLabel = "*Enter amount";
      });
    }
    if (widget.requestCode == REQUEST_CODE_CASH_BACK_TXN ||
        widget.requestCode == REQUEST_CODE_CASH_AT_POS_TXN) {
      setState(() {
        cashbackLabel = "*Enter cashback amount";
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
          "More Details",
          style: textStyle,
        ),
      ),
      body: _buildOverlayContent(context),
    );
  }

  Widget _buildOverlayContent(BuildContext context) {
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
                  children: <Widget>[
                    const SizedBox(height: 20),
                    Visibility(
                      child: TextField(
                        onChanged: (val) {
                          orderNumberEditText = val;
                        },
                        obscureText: false,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: '*Enter Order Number',
                        ),
                      ),
                      visible: !serviceRequestVisible && !refundTxnVisible,
                    ),
                    Visibility(
                      child: const SizedBox(height: 20),
                      visible: !serviceRequestVisible && !refundTxnVisible,
                    ),
                    Visibility(
                      visible: !serviceRequestVisible && cashPosRequestVisible,
                      child: TextField(
                        keyboardType: const TextInputType.numberWithOptions(
                            decimal: true),
                        onChanged: (val) {
                          payableAmountEditText = val;
                        },
                        obscureText: false,
                        decoration: InputDecoration(
                          border: const OutlineInputBorder(),
                          labelText: amountLabel,
                        ),
                      ),
                    ),
                    Visibility(
                      child: const SizedBox(height: 20),
                      visible: dropDownPaymentModeVisible,
                    ),
                    Visibility(
                        visible: dropDownPaymentModeVisible,
                        child: SizedBox(
                          height: 60,
                          child: InputDecorator(
                            decoration: const InputDecoration(
                              border: OutlineInputBorder(
                                borderRadius:
                                    BorderRadius.all(Radius.circular(4.0)),
                              ),
                            ),
                            child: DropdownButtonHideUnderline(
                              child: DropdownButton<String>(
                                isExpanded: true,
                                value: dropDownPaymentMode,
                                items: [
                                  nullPaymentMode,
                                  'Card',
                                  'Cash',
                                  'Cheque',
                                  'UPI',
                                  'UPI_VOUCHER',
                                  'RemotePay',
                                  'BHARATQR',
                                  'Brand_Offers',
                                  'Brand_EMI',
                                  'Normal_EMI',
                                  'Wallet'
                                ].map((e) {
                                  return DropdownMenuItem<String>(
                                    value: e,
                                    child: Text(
                                      e,
                                      style: textStyleResult,
                                    ),
                                  );
                                }).toList(),
                                onChanged: (val) {
                                  setState(() {
                                    if (val == "Wallet") {
                                      walletAcquirerVisible = true;
                                    } else {
                                      walletAcquirerVisible = false;
                                    }
                                    dropDownPaymentMode = val!;
                                  });
                                },
                              ),
                            ),
                          ),
                        )),
                    Visibility(
                      child: const SizedBox(height: 20),
                      visible: walletAcquirerVisible,
                    ),
                    Visibility(
                      visible: walletAcquirerVisible,
                      child: TextField(
                        keyboardType: TextInputType.text,
                        onChanged: (val) {
                          walletAcquirer = val;
                        },
                        obscureText: false,
                        decoration: const InputDecoration(
                            border: OutlineInputBorder(),
                            labelText: '*Wallet Acquirer',
                            hintText: "freecharge/ola_money/ etc."),
                      ),
                    ),
                    Visibility(
                      child: const SizedBox(height: 20),
                      visible: refundTxnVisible,
                    ),
                    Visibility(
                      visible: refundTxnVisible,
                      child: TextField(
                        onChanged: (val) {
                          txnID = val;
                        },
                        obscureText: false,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: '*Enter Transaction ID',
                        ),
                      ),
                    ),
                    Visibility(
                      child: const SizedBox(height: 20),
                      visible: !serviceRequestVisible && cashPosRequestVisible,
                    ),
                    Visibility(
                      visible: !serviceRequestVisible && cashbackRequestVisible,
                      child: TextField(
                        keyboardType: const TextInputType.numberWithOptions(
                            decimal: true),
                        onChanged: (val) {
                          cashBackAmountEditText = val;
                        },
                        obscureText: false,
                        decoration: InputDecoration(
                          border: const OutlineInputBorder(),
                          labelText: cashbackLabel,
                        ),
                      ),
                    ),
                    Visibility(
                      child: const SizedBox(height: 20),
                      visible: !serviceRequestVisible && cashbackRequestVisible,
                    ),
                    Visibility(
                      visible: !serviceRequestVisible,
                      child: TextField(
                        onChanged: (val) {
                          customerNameEditText = val;
                        },
                        obscureText: false,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: 'Enter customer name',
                        ),
                      ),
                    ),
                    Visibility(
                      child: const SizedBox(height: 20),
                      visible: !serviceRequestVisible,
                    ),
                    Visibility(
                      visible: !serviceRequestVisible,
                      child: TextField(
                        onChanged: (val) {
                          externalReference2 = val;
                        },
                        obscureText: false,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: 'External reference 2',
                        ),
                      ),
                    ),
                    Visibility(
                      child: const SizedBox(height: 20),
                      visible: !serviceRequestVisible,
                    ),
                    Visibility(
                      visible: !serviceRequestVisible,
                      child: TextField(
                        onChanged: (val) {
                          externalReference3 = val;
                        },
                        obscureText: false,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: 'External reference 3',
                        ),
                      ),
                    ),
                    Visibility(
                      child: const SizedBox(height: 20),
                      visible: !serviceRequestVisible,
                    ),
                    Visibility(
                      visible: !serviceRequestVisible,
                      child: TextField(
                        onChanged: (val) {
                          externalReferences = val;
                        },
                        obscureText: false,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: 'External refs',
                        ),
                      ),
                    ),
                    Visibility(
                      child: const SizedBox(height: 20),
                      visible: !serviceRequestVisible,
                    ),
                    Visibility(
                      visible: !serviceRequestVisible,
                      child: TextField(
                        onChanged: (val) {
                          stopPayList = val;
                        },
                        obscureText: false,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: 'Txn list',
                        ),
                      ),
                    ),
                    Visibility(
                      child: const SizedBox(height: 20),
                      visible: !serviceRequestVisible,
                    ),
                    Visibility(
                      visible: !serviceRequestVisible,
                      child: TextField(
                        keyboardType: TextInputType.phone,
                        onChanged: (val) {
                          mobileNumberEditText = val;
                        },
                        obscureText: false,
                        maxLength: 14,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: 'Enter customer mobile number',
                        ),
                      ),
                    ),
                    Visibility(
                      child: const SizedBox(height: 20),
                      visible: !serviceRequestVisible,
                    ),
                    Visibility(
                      visible: !serviceRequestVisible,
                      child: TextField(
                        keyboardType: TextInputType.emailAddress,
                        onChanged: (val) {
                          emailIdEditText = val;
                        },
                        obscureText: false,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: 'Enter customer email id',
                        ),
                      ),
                    ),
                    Visibility(
                      child: const SizedBox(height: 20),
                      visible: !serviceRequestVisible,
                    ),
                    Visibility(
                      visible: !serviceRequestVisible && productRequestVisible,
                      child: TextField(
                        onChanged: (val) {
                          productBrandEditText = val;
                        },
                        obscureText: false,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: 'Enter product brand',
                        ),
                      ),
                    ),
                    Visibility(
                      child: const SizedBox(height: 20),
                      visible: !serviceRequestVisible && productRequestVisible,
                    ),
                    Visibility(
                      visible: !serviceRequestVisible && productRequestVisible,
                      child: TextField(
                        onChanged: (val) {
                          productCodeEditText = val;
                        },
                        obscureText: false,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: 'Enter product sku',
                        ),
                      ),
                    ),
                    Visibility(
                      child: const SizedBox(height: 20),
                      visible: !serviceRequestVisible && productRequestVisible,
                    ),
                    Visibility(
                      visible: !serviceRequestVisible && productRequestVisible,
                      child: TextField(
                        onChanged: (val) {
                          productSerialEditText = val;
                        },
                        obscureText: false,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: 'Enter product serial',
                        ),
                      ),
                    ),
                    Visibility(
                      child: const SizedBox(height: 20),
                      visible: !serviceRequestVisible && productRequestVisible,
                    ),
                    Visibility(
                      visible: !serviceRequestVisible && txnRequestVisible,
                      child: TextField(
                        onChanged: (val) {
                          accountLabelEditTet = val;
                        },
                        obscureText: false,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: 'Enter account label',
                        ),
                      ),
                    ),
                    Visibility(
                      child: const SizedBox(height: 20),
                      visible: !serviceRequestVisible && txnRequestVisible,
                    ),
                    Visibility(
                      visible: !serviceRequestVisible && txnRequestVisible,
                      child: TextField(
                        keyboardType: const TextInputType.numberWithOptions(
                            decimal: true),
                        onChanged: (val) {
                          serviceFeeEditText = val;
                        },
                        obscureText: false,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: 'Enter service fee amount',
                        ),
                      ),
                    ),
                    Visibility(
                      child: const SizedBox(height: 20),
                      visible: !serviceRequestVisible && txnRequestVisible,
                    ),
                    Visibility(
                      visible: !serviceRequestVisible && txnRequestVisible,
                      child: TextField(
                        onChanged: (val) {
                          paymentByEditText = val;
                        },
                        obscureText: false,
                        decoration: const InputDecoration(
                            border: OutlineInputBorder(),
                            labelText: 'Enter payment by',
                            hintText: "CREDIT OR DEBIT OR ANY"),
                      ),
                    ),
                    Visibility(
                      child: const SizedBox(height: 20),
                      visible: !serviceRequestVisible && txnRequestVisible,
                    ),
                    Visibility(
                      visible: !serviceRequestVisible,
                      child: TextField(
                        keyboardType: TextInputType.phone,
                        onChanged: (val) {
                          merchantPhoneNumberEditText = val;
                        },
                        obscureText: false,
                        maxLength: 14,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: 'Enter merchant mobile number',
                        ),
                      ),
                    ),
                    Visibility(
                      child: const SizedBox(height: 20),
                      visible: !serviceRequestVisible,
                    ),
                    Visibility(
                      visible: !serviceRequestVisible,
                      child: TextField(
                        keyboardType: TextInputType.emailAddress,
                        onChanged: (val) {
                          merchantEmailId = val;
                        },
                        obscureText: false,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: 'Enter merchant email id',
                        ),
                      ),
                    ),
                    Visibility(
                      child: const SizedBox(height: 20),
                      visible: !serviceRequestVisible,
                    ),
                    Visibility(
                      visible: serviceRequestVisible,
                      child: TextField(
                        onChanged: (val) {
                          issueType = val;
                        },
                        obscureText: false,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: 'Enter issue type',
                        ),
                      ),
                    ),
                    Visibility(
                      child: const SizedBox(height: 20),
                      visible: serviceRequestVisible,
                    ),
                    Visibility(
                      visible: serviceRequestVisible,
                      child: TextField(
                        onChanged: (val) {
                          issueInfo = val;
                        },
                        obscureText: false,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: 'Enter issue info / comments',
                        ),
                      ),
                    ),
                    Visibility(
                      child: const SizedBox(height: 20),
                      visible: serviceRequestVisible,
                    ),
                    Visibility(
                      visible: serviceRequestVisible,
                      child: TextField(
                        onChanged: (val) {
                          tags = val;
                        },
                        obscureText: false,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: 'Enter issue tags',
                        ),
                      ),
                    ),
                    Visibility(
                      child: const SizedBox(height: 20),
                      visible: serviceRequestVisible,
                    ),
                  ],
                ),
              ),
            ),
            const SizedBox(height: 20),
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
                        if (!serviceRequestVisible && !refundTxnVisible) {
                          if (orderNumberEditText.trim().isEmpty) {
                            CommonSnackBar.showSnackBar(
                                context, "Order number is mandatory");
                            return;
                          }
                        }
                        if (!serviceRequestVisible && cashPosRequestVisible) {
                          if (payableAmountEditText.trim().isEmpty) {
                            CommonSnackBar.showSnackBar(
                                context, "amount is mandatory");
                            return;
                          }
                        }

                        if (widget.requestCode == REQUEST_CODE_CASH_BACK_TXN ||
                            widget.requestCode ==
                                REQUEST_CODE_CASH_AT_POS_TXN) {
                          if (cashBackAmountEditText.trim().isEmpty) {
                            CommonSnackBar.showSnackBar(
                                context, "cashback amount is mandatory");
                            return;
                          }
                        }

                        if (refundTxnVisible) {
                          if (txnID.trim().isEmpty) {
                            CommonSnackBar.showSnackBar(
                                context, "transaction ID is mandatory");
                            return;
                          }
                        }
                        if (walletAcquirerVisible) {
                          if (walletAcquirer.trim().isEmpty) {
                            CommonSnackBar.showSnackBar(
                                context, "walletAcquirer is mandatory");
                            return;
                          }
                        }
                        createJsonRequest(context);
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

  Future<void> createJsonRequest(context) async {
    var mode = "";
    if (widget.requestCode == REQUEST_CODE_CASH_BACK_TXN) {
      mode = "CASHBACK";
    } else if (widget.requestCode == REQUEST_CODE_CASH_AT_POS_TXN) {
      mode = "CASH@POS";
    }
    var cheque = {};
    if (dropDownPaymentMode == "Cheque") {
      cheque = {
        "chequeNumber": "125441",
        "bankCode": "TEST0001233",
        "bankName": "TEST Bank",
        "bankAccountNo": "1234567890",
        "chequeDate": "2017-12-10"
      };
    }
    String? payMode = !dropDownPaymentModeVisible ? null : dropDownPaymentMode;
    debugPrint(dropDownPaymentMode);
    if (dropDownPaymentMode == nullPaymentMode && dropDownPaymentModeVisible) {
      payMode = null;
      mode = "SALE";
    }
    var json = {
      "mode": mode,
      "cheque": cheque,
      "issueType": issueType,
      "issueInfo": issueInfo,
      "tags": tags.split("\n").toList(),
      "merchant_phone_number": merchantPhoneNumberEditText,
      "merchant_email": merchantEmailId,
      "txnIdArray": stopPayList.split("\n").toList(),
      "amount": payableAmountEditText,
      "txnId": txnID,
      "options": {
        "paymentMode": payMode,
        "amountCashback": cashBackAmountEditText,
        "amountTip": 0.00,
        "references": {
          "reference1": orderNumberEditText,
          "reference2": externalReference2,
          "reference3": externalReference3,
          "additionalReferences": externalReferences.split("\n").toList()
        },
        "customer": {
          "name": customerNameEditText,
          "email": emailIdEditText,
          "mobileNo": mobileNumberEditText
        },
        "serviceFee": serviceFeeEditText.isEmpty
            ? -1.00
            : double.parse(serviceFeeEditText),
        "paymentBy": paymentByEditText,
        "payToAccount": accountLabelEditTet,
        "productDetails": {
          "SKUCode": productCodeEditText,
          "brand": productBrandEditText,
          "serial": productSerialEditText
        },
        "addlData": {"addl1": "addl1", "addl2": "addl2", "addl3": "addl3"},
        "appData": {
          "app1": "app1",
          "app2": "app2",
          "app3": "app3",
          "walletAcquirer": walletAcquirer
        }
      }
    };

    var jsonData = json.removeNulls();

    debugPrint(jsonEncode(jsonData));

    switch (widget.requestCode) {
      case REQUEST_CODE_SERVICE_REQUEST:
        try {
          final String? result =
              await EzetapSdk.serviceRequest(jsonEncode(jsonData));
          findIfErrorInResponse(result, context);
        } on PlatformException catch (e) {
          Navigator.of(context).pop(e.message!);
        }
        break;
      case REQUEST_CODE_GET_TXN_DETAIL:
        try {
          final String? result =
              await EzetapSdk.getTransaction(jsonEncode(jsonData));
          findIfErrorInResponse(result, context);
        } on PlatformException catch (e) {
          Navigator.of(context).pop(e.message!);
        }
        break;
      case REQUEST_CODE_PAY:
        try {
          final String? result = await EzetapSdk.pay(jsonEncode(jsonData));
          findIfErrorInResponse(result, context);
        } on PlatformException catch (e) {
          Navigator.of(context).pop(e.message!);
        }
        break;
      case REQUEST_CODE_BRAND_EMI:
        try {
          final String? result =
              await EzetapSdk.brandEMITransaction(jsonEncode(jsonData));
          findIfErrorInResponse(result, context);
        } on PlatformException catch (e) {
          Navigator.of(context).pop(e.message!);
        }
        break;
      case REQUEST_CODE_NORMAL_EMI:
        try {
          final String? result =
              await EzetapSdk.normalEMITransaction(jsonEncode(jsonData));
          findIfErrorInResponse(result, context);
        } on PlatformException catch (e) {
          Navigator.of(context).pop(e.message!);
        }
        break;
      case REQUEST_CODE_STOP_PAYMENT:
        try {
          var txnIds = {"txnIds": stopPayList.split("\n").toList()};
          debugPrint(jsonEncode(txnIds));
          final String? result =
              await EzetapSdk.stopPayment(jsonEncode(txnIds));
          findIfErrorInResponse(result, context);
        } on PlatformException catch (e) {
          Navigator.of(context).pop(e.message!);
        }
        break;
      case REQUEST_CODE_WALLET_TXN:
        try {
          final String? result =
              await EzetapSdk.walletTransaction(jsonEncode(jsonData));
          findIfErrorInResponse(result, context);
        } on PlatformException catch (e) {
          Navigator.of(context).pop(e.message!);
        }
        break;
      case REQUEST_CODE_CHEQUE_TXN:
        try {
          final String? result =
              await EzetapSdk.chequeTransaction(jsonEncode(jsonData));
          findIfErrorInResponse(result, context);
        } on PlatformException catch (e) {
          Navigator.of(context).pop(e.message!);
        }
        break;
      case REQUEST_CODE_SALE_TXN:
        try {
          final String? result =
              await EzetapSdk.cardTransaction(jsonEncode(jsonData));
          findIfErrorInResponse(result, context);
        } on PlatformException catch (e) {
          Navigator.of(context).pop(e.message!);
        }
        break;
      case REQUEST_CODE_CASH_BACK_TXN:
        try {
          final String? result =
              await EzetapSdk.cardTransaction(jsonEncode(jsonData));
          findIfErrorInResponse(result, context);
        } on PlatformException catch (e) {
          Navigator.of(context).pop(e.message!);
        }
        break;
      case REQUEST_CODE_CASH_AT_POS_TXN:
        try {
          final String? result =
              await EzetapSdk.cardTransaction(jsonEncode(jsonData));
          findIfErrorInResponse(result, context);
        } on PlatformException catch (e) {
          Navigator.of(context).pop(e.message!);
        }
        break;
      case REQUEST_CODE_CASH_TXN:
        try {
          final String? result =
              await EzetapSdk.cashTransaction(jsonEncode(jsonData));
          findIfErrorInResponse(result, context);
        } on PlatformException catch (e) {
          Navigator.of(context).pop(e.message!);
        }
        break;
      case REQUEST_CODE_REMOTE_PAY:
        try {
          final String? result =
              await EzetapSdk.remotePayment(jsonEncode(jsonData));
          findIfErrorInResponse(result, context);
        } on PlatformException catch (e) {
          Navigator.of(context).pop(e.message!);
        }
        break;
      case REQUEST_CODE_UPI:
        try {
          final String? result =
              await EzetapSdk.upiTransaction(jsonEncode(jsonData));
          findIfErrorInResponse(result, context);
        } on PlatformException catch (e) {
          Navigator.of(context).pop(e.message!);
        }
        break;
      case REQUEST_CODE_QR_CODE_PAY:
        try {
          final String? result =
              await EzetapSdk.qrCodeTransaction(jsonEncode(jsonData));
          findIfErrorInResponse(result, context);
        } on PlatformException catch (e) {
          Navigator.of(context).pop(e.message!);
        }
        break;
      case REQUEST_CODE_REFUND_TRANSACTION:
        try {
          final String? result =
              await EzetapSdk.refundTransaction(jsonEncode(jsonData));
          findIfErrorInResponse(result, context);
        } on PlatformException catch (e) {
          Navigator.of(context).pop(e.message!);
        }
        break;
      case REQUEST_CODE_PRE_AUTH:
        try {
          final String? result = await EzetapSdk.preAuth(jsonEncode(jsonData));
          findIfErrorInResponse(result, context);
        } on PlatformException catch (e) {
          Navigator.of(context).pop(e.message!);
        }
        break;
      case REQUEST_CODE_CONFIRM_PRE_AUTH:
        var jsonPre = {
          "txnId": txnID,
          "amount": double.parse(payableAmountEditText)
        };
        debugPrint(jsonEncode(jsonPre));
        try {
          final String? result =
              await EzetapSdk.confirmPreAuth(jsonEncode(jsonPre));
          findIfErrorInResponse(result, context);
        } on PlatformException catch (e) {
          Navigator.of(context).pop(e.message!);
        }
        break;
    }
  }

  void findIfErrorInResponse(String? result, context) {
    debugPrint(result);
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

Map<String, dynamic> removeNullsFromMap(Map<String, dynamic> json) => json
  ..removeWhere((String key, dynamic value) => value == null || value == "")
  ..map<String, dynamic>((key, value) => MapEntry(key, removeNulls(value)));

List removeNullsFromList(List list) => list
  ..removeWhere((value) => value == null || value == "")
  ..map((e) => removeNulls(e)).toList();

removeNulls(e) => (e is List)
    ? removeNullsFromList(e)
    : (e is Map<String, dynamic> ? removeNullsFromMap(e) : e);

extension ListExtension on List {
  List removeNulls() => removeNullsFromList(this);
}

extension MapExtension on Map<String, dynamic> {
  Map<String, dynamic> removeNulls() => removeNullsFromMap(this);
}
