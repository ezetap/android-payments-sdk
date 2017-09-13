# android-sdk
This SDK will work for both Android Native and Hybrid Applications. The SDK API will help you integrate with the Ezetap services in to your applciation. This documentation contains the instructions to integrate-

1. Ezetap Native Android SDK
2. Ezetap Cordova SDK

## Before we go into details lets go through the steps to include sdk in your android application
#### If you choose aar:
* Copy the aar into libs folder under the project directory.
* Add the below line in dependencies of your build.gradle file in your application.
```
  compile(name: 'ezetapandroidsdk_X', ext: 'aar') Note: 'X' in the 'ezetapandroidsdk_X' represents the version
``` 
* Add the below line in repositories section of your build.gradle
```
  flatDir {
          dirs 'libs'
      }
```
#### If you choose jar:
* You can find sdk jar file in the releases folder of this repository, Add the jar file in <a href="https://github.com/ezetap/android-payments-sdk/tree/master/release">libs</a> folder of your Native Android Project.
* In the manifest file of your project add the permission WRITE_EXTERNAL_STORAGE, which looks like this-
```xml
  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
* In the manifest file of your Android Project add a new Activity, which looks like this-
```xml
  <activity android:name="com.eze.api.EzeAPIActivity"
        android:configChanges="orientation|keyboardHidden|keyboard|screenSize|locale"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Translucent.NoTitleBar.Fullscreen" />
```
* If you intend to support Android Nougat API Level 24 & above, follow the below 2 steps - 

1. Add the below provider in your project's manifest file inside application tag & replace your application ID in the place of <Your application ID>. Your app ID/package name can be found in your app's manifest file under package tag or in the applicationId tag of your gradle file.

```xml

	<provider
		android:name="com.ezetap.sdk.EzetapFileProvider"
		android:authorities="<Your application ID>.EzetapFileProvider"
		android:exported="false"
		android:grantUriPermissions="true" >
		<meta-data
			android:name="android.support.FILE_PROVIDER_PATHS"
			android:resource="@xml/provider_paths" />
	</provider>

```

2. Create a file with name provider_paths.xml in your Project > res > xml > folder and paste the below code-
```xml
	<?xml version="1.0" encoding="utf-8"?>
	<paths xmlns:android="http://schemas.android.com/apk/res/android" >
		<files-path name="ezetap-download" />
		<external-path name="ezetap-download" />
	</paths>
```

# 1. Native Android Integration

To integrate this API, you need to have a good grasp of Android app development, building APKs etc. The Ezetap integration part involves setting up a project, importing a library and then actual coding with just a few lines of code.

## Getting Started
1. Android development environment
2. Android phone that can connect to internet
3. This documentation
4. Ezetap app key or login credentials to Ezetap service
5. Ezetap device to test card payments

## Sample App
There is a sample Android App inside the sample folder of the repository. You can use this project as a reference to integrate Ezetap SDK.

##### Follow the steps below to get the demo app working-
1. Import the project as an Android Project in Android Studio.
2. Clean & Build the project.
3. Run the EzeNativeSampleActivity on your Smartphone.
4. EzeNativeSampleActivity.java will be your point of reference for Native Android SDK integration

>Note- The errors you may face while importing the project will most likely be for Android version mismatch which EclipseIDE would normally resolve itself. Changing the Android version or restarting the Eclipse can help u solve this problem


# 2. Cordova Integration

To integrate this API, you need to know how Cordova works & how to configure Cordova Plugin for Android. Ezetap provides a Cordova plugin which is embedded in the SDK which does all the hard work, all you need to do is to configure our plugin to your Android Cordova Project and write simple code snippet to invoke our Plugin.

## Getting Started
1. Android development environment
2. Cordova development setup
3. Android phone that can connect to internet
4. This documentation
5. Ezetap app key or login credentials to Ezetap service
6. Ezetap device to test card payments

## Sample App
There is a sample Android App inside the sample folder of the repository. You can use this project as a reference to integrate Ezetap SDK.

##### Follow the steps below to get the demo app working-
1. Import the project as an Android Project in Android Studio
2. Clean & Build the project.
3. Run the EzeCordovaSampleActivity on your Smartphone.
4. Ezehelper.js will be your point of reference for Cordova Android SDK integration.

* IMPORTANT- If your project's targetSdkVersion is higher or equal to 23(Android 6.0 Marshmallow) please add Android support library v4 to your Android project from <a href="http://developer.android.com/tools/support-library/setup.html">here.</a> The Android support libraries are not required if your project's targetSdkVersion is lesser than 23.
* Good to go, please refer <a href="http://developers.ezetap.com/api/"> Ezetap API Portal</a> for API usage

>Note- The EzeAPIActivity has to be configured with the same attributes as given above.


>Note- The errors you may face while importing the project will most likely be for Android version mismatch which EclipseIDE would normally resolve itself. Changing the Android version or restarting the Eclipse can help u solve this problem.

## Steps to follow-
* You can find the documentation on how to create a Cordova plugin for Android <a href="https://github.com/ezetap/android-payments-sdk/tree/master/docs">here</a>.

>Note Refer Ezetap Cordova Integration.pdf for Android Nougat support changes which are highlighted in orange.

