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
