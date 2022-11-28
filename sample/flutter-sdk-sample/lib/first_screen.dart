import 'package:custom_print_ezetap/helper/styles.dart';
import 'package:custom_print_ezetap/settings.dart';
import 'package:flutter/material.dart';

import 'dashboard/new_dashboard.dart';

class FirstScreen extends StatefulWidget {
  const FirstScreen({Key? key}) : super(key: key);

  @override
  State<FirstScreen> createState() => _FirstScreenState();
}

class _FirstScreenState extends State<FirstScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(
          "Flutter Sample App",
          style: textStyle,
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(18.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            ElevatedButton(
              style: flatButtonStyle,
              onPressed: () {
                Navigator.of(context).push(MaterialPageRoute(
                    builder: (BuildContext context) =>
                        const NewDashboardScreen()));
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
                    builder: (BuildContext context) => const SettingsScreen()));
              },
              child: const Text(
                "Settings",
                style: textStyle,
              ),
            )
          ],
        ),
      ),
    );
  }
}
