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


