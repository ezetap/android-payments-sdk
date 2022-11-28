// To parse this JSON data, do
//
//     final loginModel = loginModelFromJson(jsonString);

import 'dart:convert';

LoginModel loginModelFromJson(String str) => LoginModel.fromJson(json.decode(str));

String loginModelToJson(LoginModel data) => json.encode(data.toJson());

class LoginModel {
  LoginModel({
    this.error,
    this.status,
    this.result,
  });

  String? error;
  String? status;
  Result? result;

  factory LoginModel.fromJson(Map<String, dynamic> json) => LoginModel(
    error: json["error"],
    status: json["status"],
    result: Result.fromJson(json["result"]),
  );

  Map<String, dynamic> toJson() => {
    "error": error,
    "status": status,
    "result": result!.toJson(),
  };
}

class Result {
  Result({
    this.message,
  });

  String? message;

  factory Result.fromJson(Map<String, dynamic> json) => Result(
    message: json["message"],
  );

  Map<String, dynamic> toJson() => {
    "message": message,
  };
}
