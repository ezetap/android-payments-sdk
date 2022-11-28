import 'package:custom_print_ezetap/helper/common_snackbar.dart';
import 'package:custom_print_ezetap/helper/constants.dart';
import 'package:custom_print_ezetap/helper/styles.dart';
import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

class SettingsScreen extends StatefulWidget {
  const SettingsScreen({Key? key}) : super(key: key);

  @override
  State<SettingsScreen> createState() => _SettingsScreenState();
}

class _SettingsScreenState extends State<SettingsScreen> {
  var apiKey = "";
  var merchantName = "";
  var userName = "";
  var appMode = "";

  var apiKeyController = TextEditingController();
  var merchantNameController = TextEditingController();
  var userNameController = TextEditingController();
  var appModeController = TextEditingController();

  @override
  void initState() {
    showSettingsIfStored();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(
          "Settings",
          style: textStyle,
        ),
      ),
      body: Builder(builder: (context) {
        return SingleChildScrollView(
          child: Padding(
            padding: const EdgeInsets.all(18.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: [
                const SizedBox(height: 20),
                TextField(
                  controller: apiKeyController,
                  onChanged: (val) {
                    apiKey = val;
                  },
                  obscureText: false,
                  decoration: const InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Enter API Key',
                  ),
                ),
                const SizedBox(height: 20),
                TextField(
                  controller: merchantNameController,
                  onChanged: (val) {
                    merchantName = val;
                  },
                  obscureText: false,
                  decoration: const InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Enter Merchant Name',
                  ),
                ),
                const SizedBox(height: 20),
                TextField(
                  controller: userNameController,
                  onChanged: (val) {
                    userName = val;
                  },
                  obscureText: false,
                  decoration: const InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Enter UserName',
                  ),
                ),
                const SizedBox(height: 20),
                TextField(
                  controller: appModeController,
                  onChanged: (val) {
                    appMode = val;
                  },
                  obscureText: false,
                  decoration: const InputDecoration(
                      border: OutlineInputBorder(),
                      labelText: 'Enter App Mode',
                      hintText: 'DEMO/DEMO1/PROD/QA/UAT....'),
                ),
                const SizedBox(
                  height: 20,
                ),
                ElevatedButton(
                  style: flatButtonStyle,
                  onPressed: () {
                    if (appMode.isEmpty ||
                        userName.isEmpty ||
                        apiKey.isEmpty ||
                        merchantName.isEmpty) {
                      CommonSnackBar.showSnackBar(
                          context, "All fields are mandatory");
                    } else {
                      saveSettings(context);
                    }
                  },
                  child: const Text(
                    "Set Merchant",
                    style: textStyle,
                  ),
                )
              ],
            ),
          ),
        );
      }),
    );
  }

  Future<void> saveSettings(context) async {
    var sharedPref = await SharedPreferences.getInstance();
    sharedPref.setString(API_KEY, apiKey);
    sharedPref.setString(MERCHANT_NAME, merchantName);
    sharedPref.setString(USERNAME, userName);
    sharedPref.setString(APP_MODE, appMode);
    CommonSnackBar.showSnackBar(context, "Settings Saved");
    FocusScope.of(context).requestFocus(FocusNode());
  }

  Future<void> showSettingsIfStored() async {
    var sharedPref = await SharedPreferences.getInstance();
    apiKeyController.text = sharedPref.getString(API_KEY)!;
    merchantNameController.text = sharedPref.getString(MERCHANT_NAME)!;
    userNameController.text = sharedPref.getString(USERNAME)!;
    appModeController.text = sharedPref.getString(APP_MODE)!;
    setState(() {
      apiKey = sharedPref.getString(API_KEY)!;
      merchantName = sharedPref.getString(MERCHANT_NAME)!;
      userName = sharedPref.getString(USERNAME)!;
      appMode = sharedPref.getString(APP_MODE)!;
    });
  }
}
