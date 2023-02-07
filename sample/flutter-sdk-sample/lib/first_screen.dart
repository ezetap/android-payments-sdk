import 'package:custom_print_ezetap/helper/common_snackbar.dart';
import 'package:custom_print_ezetap/helper/styles.dart';
import 'package:custom_print_ezetap/settings.dart';
import 'package:flutter/material.dart';
import 'package:package_info_plus/package_info_plus.dart';
import 'package:permission_handler/permission_handler.dart';

import 'dashboard/new_dashboard.dart';


class FirstScreen extends StatefulWidget {
  const FirstScreen({Key? key}) : super(key: key);

  @override
  State<FirstScreen> createState() => _FirstScreenState();
}

class _FirstScreenState extends State<FirstScreen> {
  var mAppVersion = "1.0.0";

  @override
  void initState() {
    PackageInfo.fromPlatform().then((value) {
      setState(() {
        mAppVersion = value.version;
      });
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(
          "Flutter Sample App",
          style: textStyle,
        ),
      ),
      body: Builder(builder: (context) {
        return Padding(
          padding: const EdgeInsets.all(18.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              ElevatedButton(
                style: flatButtonStyle,
                onPressed: () {
                  Permission.requestInstallPackages.status.then((value) {
                    if (value == PermissionStatus.granted) {
                      Permission.storage.status.then((store) {
                        if (value == PermissionStatus.granted) {
                          Navigator.of(context).push(MaterialPageRoute(
                              builder: (BuildContext context) =>
                                  const NewDashboardScreen()));
                        } else {
                          var permission = Permission.storage;
                          permission.request().then((value) {
                            if (value == PermissionStatus.granted) {
                              Navigator.of(context).push(MaterialPageRoute(
                                  builder: (BuildContext context) =>
                                      const NewDashboardScreen()));
                            } else {
                              CommonSnackBar.showSnackBar(
                                  context, "storage permission needed");
                            }
                          });
                        }
                      });
                    } else {
                      var permission = Permission.requestInstallPackages;
                      permission.request().then((value) {
                        if (value == PermissionStatus.granted) {
                          Navigator.of(context).push(MaterialPageRoute(
                              builder: (BuildContext context) =>
                                  const NewDashboardScreen()));
                        } else {
                          CommonSnackBar.showSnackBar(
                              context, "Unknown source permission needed");
                        }
                      });
                    }
                  });
                },
                child: const Text(
                  "Flutter App",
                  style: textStyle,
                ),
              ),
              const SizedBox(
                height: 20,
              ),
              ElevatedButton(
                style: flatButtonStyle,
                onPressed: () {
                  Navigator.of(context).push(MaterialPageRoute(
                      builder: (BuildContext context) =>
                          const SettingsScreen()));
                },
                child: const Text(
                  "Settings",
                  style: textStyle,
                ),
              ),
              const SizedBox(
                height: 20,
              ),
              Center(
                  child:
                      Text("App Version $mAppVersion", style: textStyleResult))
            ],
          ),
        );
      }),
    );
  }
}
