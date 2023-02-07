# android-payments-sdk
Ezetap payments SDK can be integrated in both Android native and hybrid applications. Integrating the SDK will allow you to access Ezetap services in your application. SDK is available in `aar` format.

## How to include payments-sdk in your android application
* Copy the `aar` file, available in the latest version in <a href=https://github.com/ezetap/android-payments-sdk/tree/master/release>release</a> folder, into `libs` folder under your project directory.

* Add the below line in dependencies of your `build.gradle` file in your application.
```
  compile(name: 'ezetapandroidsdk_X', ext: 'aar') Note: 'X' in the 'ezetapandroidsdk_X' represents the version (ex: ezetapandroidsdk_2_0)
``` 

* Add the below line in repositories section of your `build.gradle`
```
  flatDir {
      dirs 'libs'
  }
```

* Add the below provider in your project's `manifest` file under application tag

```xml

    <provider
        android:name="com.ezetap.sdk.EzetapFileProvider"
	    android:authorities="${applicationId}.EzetapFileProvider"
	    android:exported="false"
	    android:grantUriPermissions="true" >
        <meta-data
            android:name="android.support.FILE_PROVIDER_PATHS"
            android:resource="@xml/provider_paths" />
	</provider>

```

* If you have set attribute "tools:node" in you manifest for your application node. Please set it as tools:node="merge" as shown below, it is needed to merge all the elements from our manifest.
```
 <application
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        tools:node="merge"
        android:supportsRtl="true" >
```

* If you intend to support Android Nougat API Level 24 & above, follow these steps: 

1. Add the below provider in your project's `manifest` file under application tag

```xml

    <provider
        android:name="com.ezetap.sdk.EzetapFileProvider"
	    android:authorities="${applicationId}.EzetapFileProvider"
	    android:exported="false"
	    android:grantUriPermissions="true" >
        <meta-data
            android:name="android.support.FILE_PROVIDER_PATHS"
            android:resource="@xml/provider_paths" />
	</provider>

```

2. Create a file with name `provider_paths.xml` in your `Project / res / xml` folder and paste the following code:

```xml
    <?xml version="1.0" encoding="utf-8"?>
    <paths xmlns:android="http://schemas.android.com/apk/res/android" >
        <files-path name="ezetap-download" />
        <external-path name="ezetap-download" />
    </paths>
```

# Native Android integration

To integrate this API, you need to have a good grasp of Android app development. The Ezetap integration part involves setting up a project, importing a library and then actual coding with just a few lines of code.

## What you need
1. Android development environment
2. Android phone that can connect to internet
3. This documentation
4. Ezetap app key or login credentials to Ezetap service
5. Ezetap device to test card payments

## Sample app
There is a sample Android App inside the sample folder of the repository. You can use this project as a reference to integrate Ezetap SDK.

##### Follow the steps below to get the sample app working:
1. Import the project as an Android Project in Android Studio.
2. Clean & build the project.
3. Run the `EzeNativeSampleActivity` on your Smartphone.
4. `EzeNativeSampleActivity.java` will be your point of reference for native Android SDK integration.

><b>Note:</b> The errors you may face while importing the project will most likely be for Android version mismatch which EclipseIDE would normally resolve itself. Changing the Android version or restarting the Eclipse can help u solve this problem



# Cordova integration

To integrate this API, you need to know how Cordova works & how to configure Cordova Plugin for Android. Ezetap provides a Cordova plugin which is embedded in the SDK which does all the hard work, all you need to do is to configure our plugin to your Android Cordova Project and write simple code snippet to invoke our Plugin.

## What you need
1. Android development environment
2. Cordova development setup
3. Android phone that can connect to internet
4. This documentation
5. Ezetap app key or login credentials to Ezetap service
6. Ezetap device to test card payments

## Sample app
There is a sample Android App inside the `sample` folder of this repository. You can use this project as a reference to integrate with the Ezetap SDK.

##### Follow the steps below to get the sample app working:
1. Import the project as an Android Project in Android Studio.
2. Clean & build the project.
3. Run the `EzeCordovaSampleActivity` on your smartphone.
4. `Ezehelper.js` will be your point of reference for Cordova Android SDK integration.

* <b>IMPORTANT:</b> If your project's `targetSdkVersion` is higher or equal to 23(Android 6.0 Marshmallow) please add Android support library v4 to your Android project from <a href="http://developer.android.com/tools/support-library/setup.html">here.</a> The Android support libraries are not required if your project's `targetSdkVersion` is lesser than 23.
* Good to go, please refer <a href="https://sandbox.ezetap.com/static/index.html"> Ezetap API Portal</a> for API usage.

><b>Note:</b> The EzeAPIActivity has to be configured with the same attributes as given above.

><b>Note:</b> The errors you may face while importing the project will most likely be for Android version mismatch which the EclipseIDE would normally resolve itself. Changing the Android version or restarting the Eclipse can help you fix this issue.

* You can find the documentation on how to create a Cordova plugin for Android <a href="https://github.com/ezetap/android-payments-sdk/tree/master/docs">here</a>.

><b>Note:</b> Refer to the <a href="https://github.com/ezetap/android-payments-sdk/blob/master/docs/Ezetap%20Cordova%20Integration.pdf" target="_blank">Ezetap Cordova Integration</a> document for Android Nougat support changes, these are highlighted in the guide.



# react-native-ezetap-sdk

## Getting started

`$ npm install react-native-ezetap-sdk --save`

### Mostly automatic installation

`$ react-native link react-native-ezetap-sdk`

### Manual installation

#### Android

1. Open  `node_modules`
    Add `react-native-ezetap-sdk@x.x.x` folder
2. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import com.reactlibrary.RNEzetapSdkPackage;` to the imports at the top of the file
  - Add `new RNEzetapSdkPackage()` to the list returned by the `getPackages()` method
3. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-ezetap-sdk'
  	project(':react-native-ezetap-sdk').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-ezetap-sdk/android')
  	```
4. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      implementation project(':react-native-ezetap-sdk')
  	```
5. Open up `android/app/src/main/AndroidManifest.xml`
  - Add `tools:replace="android:allowBackup"` in `application` tag
    ```
     <application
        android:name=".MainApplication"
        android:allowBackup="false"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:theme="@style/AppTheme"
        tools:replace="android:allowBackup">

## What you need
1. React Native development environment
2. Android phone that can connect to internet
3. This documentation
4. Ezetap app key or login credentials to Ezetap service
5. Ezetap device to test card payments

## Usage
```javascript
import RNEzetapSdk from 'react-native-ezetap-sdk';
```

    • Then use the methods exposed by React Native SDK
    • For example:
    
     var response = await RNEzetapSdk.initialize(json);
  

# Flutter Ezetap SDK


[![pub package](https://img.shields.io/pub/v/ezetap_sdk.svg)](https://pub.dev/packages/ezetap_sdk)
[![pub points](https://img.shields.io/pub/points/ezetap_sdk?color=2E8B57&label=pub%20points)](https://pub.dev/packages/ezetap_sdk/score)

Welcome to Ezetap Payments Flutter SDK integration! You can easily collect payments from your
existing android applications by integrating the SDK.

## How integration works

1. Include the SDK in your mobile application to collect payments.
2. SDK interfaces with a Service App, this App will be installed during run-time.
3. Service App interfaces with the Card Device and Ezetap Servers to finish payment processing and
   notifies the status to SDK.

## Prerequisites

Supported on Android API version 16(JellyBean) or above.

## Use this package as a library

This will add a line like this to your package's pubspec.yaml (and run an
implicit ```flutter pub get```)

```
dependencies:
  ezetap_sdk: ^0.1.1
```

## Import it

Now in your Dart code, you can use:

```import 'package:ezetap_sdk/ezetap_sdk.dart';```

## Methods Available

```
 var json = {
      "prodAppKey": "Your prod app key",
      "demoAppKey": "Your demo app key",
      "merchantName": "merchantName",
      "userName": "userName",
      "currencyCode": 'INR',
      "appMode": "SANDBOX",
      "captureSignature": 'true',
      "prepareDevice": 'false',
      "captureReceipt": 'false'
    };
  
  EzetapSdk.initialize(json);

  EzetapSdk.prepareDevice();

  var sendReceiptJson = {
      "customerName": "customerName",
      "mobileNo": "mobileNo",
      "email": "emailId",
      "txnId": "transactionID"
    };
    
  EzetapSdk.sendReceipt(sendReceiptJson);

    var json = {
      "issueType": "issueType",
      "issueInfo": "issueInfo",
      "tags": [
        "tag1","tag2"
      ]
    };

  EzetapSdk.serviceRequest(json);

    var json = {
      "agentName": "username",
      "saveLocally": false
    };

  EzetapSdk.searchTransaction(json);

   var json = {
      "amount": "100",
      "options": {
        "amountTip": 0.0,
        "references": {
          "reference1": "sffr",
          "additionalReferences": [
    
          ]
        },
        "customer": {
    
        },
        "serviceFee": -1.0,
        "addlData": {
          "addl1": "addl1",
          "addl2": "addl2",
          "addl3": "addl3"
        },
        "appData": {
          "app1": "app1",
          "app2": "app2",
          "app3": "app3"
        }
      }
    };

  EzetapSdk.getTransaction(json);

   var json = {};
   
  EzetapSdk.checkForIncompleteTransaction(json);

  EzetapSdk.voidTransaction(String transactionID);

   var json = {"txnId": "transactionID"};
  EzetapSdk.attachSignature(json);

var json = {
  "amount": "123",
  "options": {
    "serviceFee": 100,
    "paymentBy": "CREDIT OR DEBIT OR ANY (**To be used only if the service fee is being added.)",
    "paymentMode": "Card/Cash/Cheque/UPI/UPI_VOUCHER/RemotePay/BHARATQR/Brand_Offers/Brand_EMI/Normal_EMI/Wallet ",
    "providerName": "<NBFC> eg. ZestMoney. (**providerName and emiType are to be passed only if user wants to use ZestMoney. In this scenario, set \"paymentMode”:”EMI”)",
    "emiType": "NORMAL, BRAND, EMI",
    "references": {
      "reference1": "1234",
      "additionalReferences": [
        "addRef_xx1",
        "addRef_xx2"
      ]
    },
    "appData": {
      "walletAcquirer": "freecharge/ola_money/ etc.(**walletAcquirer are to be passed only if user sets \"paymentMode”:”Wallet”)"
    },
    "customer": {
      "name": "xyz",
      "mobileNo": "1234567890",
      "email": "abc@xyz.com"
    }
  }
};

  EzetapSdk.pay(json);

  EzetapSdk.printReceipt(String transactionID);

  EzetapSdk.printBitmap(String? base64Image);

  EzetapSdk.logout();

  var json = {"txnIds":[""]};
  
  EzetapSdk.stopPayment(json);

  EzetapSdk.scanBarcode();

var json = {
  "amount": "100",
  "txnId": "transactionID"
};
  EzetapSdk.refundTransaction(json);
  
  EzetapSdk.checkForUpdates();
  
```


```dart
Future<void> onPaymentClicked(json) async {
  String? result = await EzetapSdk.pay(json);
  if (!mounted) return;
  setState(() {
    _result = result;
  });
}
```

```dart
Future<void> onBarcodePressed() async {
  String? result = await EzetapSdk.scanBarcode();
  if (!mounted) return;
  setState(() {
    _result = result;
  });
}
```

## What you need

1. Flutter development environment
2. Android phone that can connect to internet
3. This documentation
4. Ezetap app key or login credentials to Ezetap service
5. Ezetap device to test card payments







