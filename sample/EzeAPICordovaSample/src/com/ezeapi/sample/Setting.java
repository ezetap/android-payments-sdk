package com.ezeapi.sample;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.ezetap.sdk.EzeConstants.AppMode;
import com.ezetap.sdk.EzetapApiConfig;

import java.util.Map;

public class Setting extends Activity {

	public static final String MERCHANT_NAME = "merchant";
	public static final String API_KEY = "api_key";
	public static final String USER_NAME = "username";
	public static final String APP_MODE = "appmode";
	static  ConfigHolder config;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		loadData();
	}

	public void setMerchant(View v) {
		String appKey = ((EditText) findViewById(R.id.api_key)).getText().toString();
		String merchantName = ((EditText) findViewById(R.id.merchant_name)).getText().toString();
		String mode = ((EditText) findViewById(R.id.app_mode)).getText().toString();
		String userName = ((EditText) findViewById(R.id.user_name)).getText().toString();

		String appMode = "DEMO";
		if(mode != null && mode.trim().length() > 0) {
			appMode = mode;
		}
		config = new ConfigHolder(appKey, appMode, userName, merchantName);
		if(appKey == null || merchantName == null)
			Toast.makeText(this, "Merchant name or app key cannot be null", Toast.LENGTH_SHORT).show();
		else if(appKey.length() == 0 || merchantName.length() == 0)
			Toast.makeText(this, "Merchant name or app key cannot be empty", Toast.LENGTH_SHORT).show();
		else if(userName == null|| userName.length() == 0)
			Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
		else {
			saveData();
			Toast.makeText(this, "Merchant Set to "+ merchantName, Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	private void loadData() {
		String merchant = getPrefs(MERCHANT_NAME, this);
		EditText name = (EditText) findViewById(R.id.merchant_name);
		name.setText(merchant);
		name.clearFocus();

		String apikey = getPrefs(API_KEY, this);
		EditText api_key = (EditText) findViewById(R.id.api_key);
		api_key.setText(apikey);
		api_key.clearFocus();

		String username = getPrefs(USER_NAME, this);
		EditText user_name = (EditText) findViewById(R.id.user_name);
		user_name.setText(username);
		user_name.clearFocus();

		String appMode = getPrefs(APP_MODE, this);
		EditText app_mode = (EditText) findViewById(R.id.app_mode);
		app_mode.setText(appMode);
		app_mode.clearFocus();
	}

	private void saveData() {
		String name = ((EditText) findViewById(R.id.merchant_name)).getText().toString();
		String api_key = ((EditText) findViewById(R.id.api_key)).getText().toString();
		String userName = ((EditText) findViewById(R.id.user_name)).getText().toString();
		String mode = ((EditText) findViewById(R.id.app_mode)).getText().toString();
		savePrefs(MERCHANT_NAME, name, this);
		savePrefs(API_KEY, api_key, this);
		savePrefs(APP_MODE, mode, this);
		if(userName != null && userName.trim().length() > 0) {
			savePrefs(USER_NAME, userName, this);
		}
		if(name!= null && api_key!= null&& mode!= null&& userName!= null)
		{
			config = new ConfigHolder(api_key, mode, userName, name);
		}
	}
	
	public static String getPrefs(String key, Activity activity) {
		SharedPreferences sharedPreferences = activity.getSharedPreferences("app.db", Context.MODE_PRIVATE);
		Map<String, ?> map = (Map<String, ?>) sharedPreferences.getAll();
		String value = null;
		try {
			value = map.get(key).toString();
		} catch (Exception e) {
		}
		return value;
	}
	
	public static void savePrefs(String key, String value, Activity activity) {
		if (value != null && value.length() > 0) {
			SharedPreferences sharedPreferences = activity.getSharedPreferences("app.db", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString(key, value);
			editor.commit();
		}
	}

	public static class ConfigHolder {
		String appKey;
		String appMode;
		String username;
		String merchantName;

		public ConfigHolder(String appKey, String appMode, String username, String merchantName) {
			this.appKey = appKey;
			this.appMode = appMode;
			this.username = username;
			this.merchantName = merchantName;
		}

		public String getAppKey() {
			return appKey;
		}

		public void setAppKey(String appKey) {
			this.appKey = appKey;
		}

		public String getAppMode() {
			return appMode;
		}

		public void setAppMode(String appMode) {
			this.appMode = appMode;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getMerchantName() {
			return merchantName;
		}

		public void setMerchantName(String merchantName) {
			this.merchantName = merchantName;
		}
	}
}