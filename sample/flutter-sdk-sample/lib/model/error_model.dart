// To parse this JSON data, do
//
//     final errorModel = errorModelFromJson(jsonString);

import 'dart:convert';

ErrorModel errorModelFromJson(String str) =>
    ErrorModel.fromJson(json.decode(str));

String errorModelToJson(ErrorModel data) => json.encode(data.toJson());

class ErrorModel {
  ErrorModel({
    this.error,
    this.status,
  });

  Error? error;
  String? status;

  factory ErrorModel.fromJson(Map<String, dynamic> json) => ErrorModel(
        error: (json["error"] == null || json["error"] == "")
            ? null
            : Error.fromJson(json["error"]),
        status: json["status"],
      );

  Map<String, dynamic> toJson() => {
        "error": error?.toJson(),
        "status": status,
      };
}

class Error {
  Error({
    this.code,
    this.message,
  });

  String? code;
  String? message;

  factory Error.fromJson(Map<String, dynamic> json) => Error(
        code: json["code"],
        message: json["message"],
      );

  Map<String, dynamic> toJson() => {
        "code": code,
        "message": message,
      };
}
