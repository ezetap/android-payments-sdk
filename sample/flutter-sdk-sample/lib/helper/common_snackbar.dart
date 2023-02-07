import 'package:custom_print_ezetap/helper/styles.dart';
import 'package:flutter/material.dart';

class CommonSnackBar {
  static Future<void> showSnackBar(BuildContext context, String text) async {
    debugPrint(text);
    ScaffoldMessenger.of(context).showSnackBar(SnackBar(
      behavior: SnackBarBehavior.floating,
      content: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Text(
          text,
          style: textStyle,
        ),
      ),
    ));
  }
}
