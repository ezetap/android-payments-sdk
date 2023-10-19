## Getting started

`$ npm i react-native-ezetap-sdk`


### Manual installation

#### Android

1. Open  `node_modules`
    Add `react-native-ezetap-sdk@x.x.x` folder
2. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import com.ezetapsdk.RNEzetapSdkPackage;` to the imports at the top of the file
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

# After manual or automatic installation


Open up `android/app/src/main/AndroidManifest.xml`
- Add `tools:replace="android:allowBackup"` in `application` tag
```javascript
     <application
        android:name=".MainApplication"
        android:allowBackup="false"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:theme="@style/AppTheme"
        tools:replace="android:allowBackup">
```

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

### Methods

```javascript
      var json = {
      "prodAppKey": "Your prod app key",
      "demoAppKey": "Your demo app key",
      "merchantName": "merchantName",
      "userName": "userName",
      "currencyCode": 'INR',
      "appMode": "Your environment",
      "captureSignature": 'true',
      "prepareDevice": 'false',
      "captureReceipt": 'false'
    };
    var response = await RNEzetapSdk.initialize(JSON.stringify(json));
    console.log(response);
```

```javascript
    var response = await RNEzetapSdk.prepareDevice();
    console.log(response);
```

```javascript
    var sendReceiptJson = {
        "customerName": "customerName",
        "mobileNo": "mobileNo",
        "email": "emailId",
        "txnId": "transactionID"
      };
      
    var response = await RNEzetapSdk.sendReceipt(JSON.stringify(sendReceiptJson));
    console.log(response);
```


```javascript
      var json = {
        "issueType": "issueType",
        "issueInfo": "issueInfo",
        "tags": [
          "tag1","tag2"
        ]
      };
  
    var response = await RNEzetapSdk.serviceRequest(JSON.stringify(json));
   console.log(response);
```


```javascript
      var json = {
        "agentName": "username",
        "saveLocally": false
      };
  
    var response = await RNEzetapSdk.searchTransaction(JSON.stringify(json));
   console.log(response);
```



```javascript
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
  
    var response = await RNEzetapSdk.getTransaction(JSON.stringify(json));
   console.log(response);
```


```javascript
     var json = {};
    var response = await RNEzetapSdk.checkForIncompleteTransaction(JSON.stringify(json));
   console.log(response);
```


```javascript
    //Pass the transactionId to this function
    var transactionID = "";
    var response = await RNEzetapSdk.voidTransaction(transactionID);
   console.log(response);
```



```javascript
    var json = {"txnId": "transactionID"};
    var response = await RNEzetapSdk.attachSignature(JSON.stringify(json));
   console.log(response);
```


```javascript
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
  
   var response = await RNEzetapSdk.pay(JSON.stringify(json));
   console.log(response);
```


```javascript
    //Pass the transactionId to this function
    var transactionID = "";
    var response = await RNEzetapSdk.printReceipt(transactionID);
     console.log(response);
```


```javascript
    //Pass the image as base64 string to this function
    var base64ImageString = "";
    var response = await RNEzetapSdk.printBitmap(base64ImageString);
   console.log(response);
```

```javascript
    var response = await RNEzetapSdk.logout();
   console.log(response);
```

```javascript
    var json = {"txnIds":[""]};
    var response = await RNEzetapSdk.stopPayment(JSON.stringify(json));
   console.log(response);
```


```javascript
    var response = await RNEzetapSdk.scanBarcode();
   console.log(response);
```

```javascript
     var json = {
       "amount": "100",
       "txnId": "transactionID"
     };
    var response = await RNEzetapSdk.refundTransaction(JSON.stringify(json));
     console.log(response);
```

```javascript
    var response = await RNEzetapSdk.checkForUpdates();
    console.log(response);
```
