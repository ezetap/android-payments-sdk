import React, { useState, createRef } from "react";
import {
  SafeAreaView,
  StyleSheet,
  Text,
  View,
  TextInput,
  TouchableOpacity,
  Alert,
  ScrollView,
} from "react-native";
import RNEzetapSdk from "react-native-ezetap-sdk";
import AsyncStorage from "@react-native-community/async-storage";

const HomeScreen = ({ route, navigation }) => {
  const [username, setUsername] = useState("");
  const [user, setUser] = useState("");
  const [userAmount, setAmount] = useState("");
  const [userAgent, setUserAgent] = useState("");
  const [referenceID, setReferenceID] = useState("");
  const [transactionID, setTransactionID] = useState("");
  const [mobile, setMobile] = useState("");
  const [email, setEmail] = useState("");
  const [taxationID, setTaxationID] = useState("");
  return (
    <ScrollView
      keyboardShouldPersistTaps="handled"
      contentContainerStyle={{
        flexGrow: 1,
        justifyContent: "center",
        alignContent: "center",
      }}
    >
      <View style={styles.container}>
        <View style={styles.space}></View>
        {/* <Text style={styles.loginText}>Demo based on 9080706051 account</Text> */}
        <View style={styles.space}></View>
        <View style={styles.inputView}>
          <TextInput
            style={styles.inputText}
            placeholder="Enter Customer Name"
            placeholderTextColor="#fff"
            onChangeText={(username) => setUsername(username)}
          />
        </View>
        <View style={styles.inputView}>
          <TextInput
            style={styles.inputText}
            placeholder="Enter Amount"
            placeholderTextColor="#fff"
            onChangeText={(userAmount) => setAmount(userAmount)}
          />
        </View>
        <TouchableOpacity
          style={styles.loginBtn}
          onPress={async () => {
            // if (!user) {
            //   alert("Username Required for testing");
            //   return;
            // }
            if (!userAmount) {
              alert("Amount Required");
              return;
            }
            if (!username) {
              alert("Name Required");
              return;
            }

            var userName = await AsyncStorage.getItem("user");

            var genericPay =
              '{"userName":' +
              userName +
              ',"amount":' +
              userAmount +
              ',"options":{"references":{"reference2":" ","reference3":" "},"customer":{"name":' +
              username +
              ',"email":""},"amountTip":"0"}}';
            var res = await RNEzetapSdk.pay(genericPay);
            console.log(res);
            var json = JSON.parse(res);
            if (json.status == "success") {
              Alert.alert("", json.result.references.reference1);
            } else {
              Alert.alert("", json.error.message);
            }
          }}
        >
          <Text style={styles.loginText}> PAY </Text>
        </TouchableOpacity>
        <Text style={styles.loginText}> -- -- OTHER METHODS -- -- </Text>
        <TouchableOpacity
          style={styles.otherBtn}
          onPress={async () => {
            var res = await RNEzetapSdk.prepareDevice();
            console.log(res);
            var json = JSON.parse(res);
            if (json.status == "success") {
              Alert.alert(
                "",
                json.result.message +
                  " SerialNumber:" +
                  json.result.device.serialNumber
              );
            } else {
              Alert.alert("", json.error.message);
            }
          }}
        >
          <Text style={styles.loginText}> PREPARE DEVICE </Text>
        </TouchableOpacity>
        <View style={styles.logo}></View>
        <View style={styles.inputView}>
          <TextInput
            style={styles.inputText}
            placeholder="Enter Mobile"
            placeholderTextColor="#fff"
            onChangeText={(mobile) => setMobile(mobile)}
          />
        </View>
        <View style={styles.inputView}>
          <TextInput
            style={styles.inputText}
            placeholder="Enter Email"
            placeholderTextColor="#fff"
            onChangeText={(email) => setEmail(email)}
          />
        </View>
        <View style={styles.inputView}>
          <TextInput
            style={styles.inputText}
            placeholder="Enter Taxation ID"
            placeholderTextColor="#fff"
            onChangeText={(taxationID) => setTaxationID(taxationID)}
          />
        </View>
        <TouchableOpacity
          style={styles.otherBtn}
          onPress={async () => {
            if (!mobile) {
              alert("Mobile Required");
              return;
            }
            if (!email) {
              alert("email Required");
              return;
            }
            if (!taxationID) {
              alert("taxationID Required");
              return;
            }

            var sendReceiptJson =
              '{"customerName":"9080706051","mobileNo":' +
              mobile +
              ',"email":' +
              email +
              ',"txnId":' +
              taxationID +
              "}";
            var res = await RNEzetapSdk.sendReceipt(sendReceiptJson);
            console.log(res);
            var json = JSON.parse(res);
            if (json.status == "success") {
              Alert.alert("", json.result.message);
            } else {
              Alert.alert("", json.error.message);
            }
          }}
        >
          <Text style={styles.loginText}> SEND RECEIPT </Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={styles.otherBtn}
          onPress={async () => {
            var serviceRequestJson =
              '{"merchant_phone_number":"9080706051","issueType":"Connectivity Issue","tags":["Connectivity Issue"],"issueInfo":"nothing","merchant_email":"tt@tt.com"}';
            var res = await RNEzetapSdk.serviceRequest(serviceRequestJson);
            console.log(res);
            var json = JSON.parse(res);
            if (json.status == "success") {
              Alert.alert("", json.result.message);
            } else {
              Alert.alert("", json.error.message);
            }
          }}
        >
          <Text style={styles.loginText}> SERVICE REQUEST </Text>
        </TouchableOpacity>
        <View style={styles.space}></View>
        <View style={styles.inputView}>
          <TextInput
            style={styles.inputText}
            placeholder="Enter Agent Name"
            placeholderTextColor="#fff"
            onChangeText={(userAgent) => setUserAgent(userAgent)}
          />
        </View>
        <TouchableOpacity
          style={styles.otherBtn}
          onPress={async () => {
            if (!userAgent) {
              alert("Enter Agent Name");
              return;
            }

            var date = new Date().getDate();
            var month = new Date().getMonth() + 1;
            var year = new Date().getFullYear();
            var d = year + "-" + month + "-" + date;
            var searchJson =
              '{"agentName":' +
              userAgent +
              ',"startDate":' +
              d +
              ',"endDate":' +
              d +
              ',"saveLocally":true}';
            console.log(searchJson);
            var res = await RNEzetapSdk.searchTransaction(searchJson);
            console.log(res);
            var json = JSON.parse(res);
            if (json.status == "success") {
              var dataArray = json.result.data;
              console.log(dataArray);
              var referenceIds = "";
              for (let i = 0; i < dataArray.length; i++) {
                referenceIds =
                  referenceIds + "\n" + dataArray[i].references.reference1;
              }
              console.log(referenceIds);
              if (referenceIds) Alert.alert("Reference ID", referenceIds);
              else
                Alert.alert(
                  "Reference ID",
                  "Not Found.Try some transactions today"
                );
            } else {
              Alert.alert("", json.error.message);
            }
          }}
        >
          <Text style={styles.loginText}> SEARCH TRANSACTION </Text>
        </TouchableOpacity>
        <View style={styles.space}></View>
        <View style={styles.inputView}>
          <TextInput
            style={styles.inputText}
            placeholder="Enter ReferenceId"
            placeholderTextColor="#fff"
            onChangeText={(referenceID) => setReferenceID(referenceID)}
          />
        </View>
        <TouchableOpacity
          style={styles.otherBtn}
          onPress={async () => {
            if (!referenceID) {
              alert("Reference ID Needed");
              return;
            }
            var getTransactionJson =
              '{"references":{"reference1":' +
              referenceID +
              ',"reference2":" ","reference3":" "}}';
            console.log(getTransactionJson);
            var res = await RNEzetapSdk.getTransaction(getTransactionJson);
            console.log(res);
            var json = JSON.parse(res);
            if (json.status == "success") {
              var dataArray = json.result.data;
              console.log(dataArray);
              var referenceIds = "";
              for (let i = 0; i < dataArray.length; i++) {
                referenceIds =
                  referenceIds + "\n" + dataArray[i].references.reference1;
              }
              console.log(referenceIds);
              if (referenceIds) Alert.alert("Reference ID", referenceIds);
              else
                Alert.alert(
                  "Reference ID",
                  "Not Found.Try some transactions today"
                );
            } else {
              Alert.alert("", json.error.message);
            }
          }}
        >
          <Text style={styles.loginText}> GET TRANSACTION </Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={styles.otherBtn}
          onPress={async () => {
            var res = await RNEzetapSdk.checkForIncompleteTransaction(null);
            console.log(res);
            var json = JSON.parse(res);
            if (json.status == "success") {
              Alert.alert("", json.result.message);
            } else {
              Alert.alert("", json.error.message);
            }
          }}
        >
          <Text style={styles.loginText}> CHECK INCOMPLETE TRANSACTION </Text>
        </TouchableOpacity>
        <View style={styles.space}></View>
        <View style={styles.inputView}>
          <TextInput
            style={styles.inputText}
            placeholder="Enter Transaction ID"
            placeholderTextColor="#fff"
            onChangeText={(transactionID) => setTransactionID(transactionID)}
          />
        </View>
        <TouchableOpacity
          style={styles.otherBtn}
          onPress={async () => {
            if (!transactionID) {
              alert("Transaction ID required");
              return;
            }
            var res = await RNEzetapSdk.voidTransaction(transactionID);
            console.log(res);
            var json = JSON.parse(res);
            if (json.status == "success") {
              Alert.alert("", json.result.message);
            } else {
              Alert.alert("", json.error.message);
            }
          }}
        >
          <Text style={styles.loginText}> VOID TRANSACTION </Text>
        </TouchableOpacity>
        <View style={styles.space}></View>
        <View style={styles.inputView}>
          <TextInput
            style={styles.inputText}
            placeholder="Enter Taxation ID"
            placeholderTextColor="#fff"
            onChangeText={(taxationID) => setTaxationID(taxationID)}
          />
        </View>
        <TouchableOpacity
          style={styles.otherBtn}
          onPress={async () => {
            if (!taxationID) {
              alert("taxation ID required");
              return;
            }
            var json = '{"txnId":' + taxationID + "}";
            console.log(json);
            var res = await RNEzetapSdk.attachSignature(json);
            console.log(res);
            var json = JSON.parse(res);
            if (json.status == "success") {
              Alert.alert("", json.result.message);
            } else {
              Alert.alert("", json.error.message);
            }
          }}
        >
          <Text style={styles.loginText}> ATTACH SIGNATURE </Text>
        </TouchableOpacity>
        <View style={styles.space}></View>
        <View style={styles.inputView}>
          <TextInput
            style={styles.inputText}
            placeholder="Enter Customer Name"
            placeholderTextColor="#fff"
            onChangeText={(username) => setUsername(username)}
          />
        </View>
        <View style={styles.inputView}>
          <TextInput
            style={styles.inputText}
            placeholder="Enter Amount"
            placeholderTextColor="#fff"
            onChangeText={(userAmount) => setAmount(userAmount)}
          />
        </View>
        <TouchableOpacity
          style={styles.loginBtn}
          onPress={async () => {
            // if (!user) {
            //   alert("Username Required for testing");
            //   return;
            // }
            if (!userAmount) {
              alert("Amount Required");
              return;
            }
            if (!username) {
              alert("Name Required");
              return;
            }
            var userName = await AsyncStorage.getItem("user");
            var genericPay =
              '{"userName":' +
              userName +
              ',"amount":' +
              userAmount +
              ',"options":{"references":{"reference2":" ","reference3":" "},"customer":{"name":' +
              username +
              ',"email":""},"amountTip":"0"}}';
            var res = await RNEzetapSdk.brandEMITransaction(genericPay);
            console.log(res);
            var json = JSON.parse(res);
            if (json.status == "success") {
              Alert.alert("", json.result.message);
            } else {
              Alert.alert("", json.error.message);
            }
          }}
        >
          <Text style={styles.loginText}> BRAND EMI TRANSACTION </Text>
        </TouchableOpacity>
        <View style={styles.space}></View>
        <View style={styles.inputView}>
          <TextInput
            style={styles.inputText}
            placeholder="Enter Customer Name"
            placeholderTextColor="#fff"
            onChangeText={(username) => setUsername(username)}
          />
        </View>
        <View style={styles.inputView}>
          <TextInput
            style={styles.inputText}
            placeholder="Enter Amount"
            placeholderTextColor="#fff"
            onChangeText={(userAmount) => setAmount(userAmount)}
          />
        </View>
        <TouchableOpacity
          style={styles.loginBtn}
          onPress={async () => {
            // if (!user) {
            //   alert("Username Required for testing");
            //   return;
            // }
            if (!userAmount) {
              alert("Amount Required");
              return;
            }
            if (!username) {
              alert("Name Required");
              return;
            }
            var userName = await AsyncStorage.getItem("user");
            var genericPay =
              '{"userName":' +
              userName +
              ',"amount":' +
              userAmount +
              ',"options":{"references":{"reference2":" ","reference3":" "},"customer":{"name":' +
              username +
              ',"email":""},"amountTip":"0"}}';
            var res = await RNEzetapSdk.normalEMITransaction(genericPay);
            console.log(res);
            var json = JSON.parse(res);
            if (json.status == "success") {
              Alert.alert("", json.result.message);
            } else {
              Alert.alert("", json.error.message);
            }
          }}
        >
          <Text style={styles.loginText}> NORMAL EMI TRANSACTION </Text>
        </TouchableOpacity>
        <View style={styles.space}></View>
        <View style={styles.inputView}>
          <TextInput
            style={styles.inputText}
            placeholder="Enter Transaction ID"
            placeholderTextColor="#fff"
            onChangeText={(transactionID) => setTransactionID(transactionID)}
          />
        </View>
        <TouchableOpacity
          style={styles.otherBtn}
          onPress={async () => {
            if (!transactionID) {
              alert("Transaction ID required");
              return;
            }
            var res = await RNEzetapSdk.printReceipt(transactionID);
            console.log(res);
            var json = JSON.parse(res);
            if (json.status == "success") {
              Alert.alert("", json.result.message);
            } else {
              Alert.alert("", json.error.message);
            }
          }}
        >
          <Text style={styles.loginText}> PRINT RECEIPT </Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={styles.otherBtn}
          onPress={async () => {
            var res = await RNEzetapSdk.printBitmap();
            console.log(res);
            var json = JSON.parse(res);
            if (json.status == "success") {
              Alert.alert("", "Printing completed");
            } else {
              Alert.alert("", json.error.message);
            }
          }}
        >
          <Text style={styles.loginText}> PRINT BITMAP </Text>
        </TouchableOpacity>
        <View style={styles.space}></View>
        <View style={styles.inputView}>
          <TextInput
            style={styles.inputText}
            placeholder="Enter Customer Name"
            placeholderTextColor="#fff"
            onChangeText={(username) => setUsername(username)}
          />
        </View>
        <View style={styles.inputView}>
          <TextInput
            style={styles.inputText}
            placeholder="Enter Amount"
            placeholderTextColor="#fff"
            onChangeText={(userAmount) => setAmount(userAmount)}
          />
        </View>
        <TouchableOpacity
          style={styles.otherBtn}
          onPress={async () => {
            if (!userAmount) {
              alert("Amount Required");
              return;
            }
            if (!username) {
              alert("Name Required");
              return;
            }
            var userName = await AsyncStorage.getItem("user");
            var json =
              '{"userName":' +
              userName +
              ',"amount":' +
              userAmount +
              ',"options":{"references":{"reference2":" ","reference3":" "},"customer":{"name":' +
              username +
              ',"email":""},"amountTip":"0"}}';
            console.log(json);
            var res = await RNEzetapSdk.preAuth(json);
            console.log(res);
            var json = JSON.parse(res);
            if (json.status == "success") {
              Alert.alert("", json.result.message);
            } else {
              Alert.alert("", json.error.message);
            }
          }}
        >
          <Text style={styles.loginText}> PRE AUTH </Text>
        </TouchableOpacity>
        <View style={styles.space}></View>
        <View style={styles.inputView}>
          <TextInput
            style={styles.inputText}
            placeholder="Enter Taxation ID"
            placeholderTextColor="#fff"
            onChangeText={(taxationID) => setTaxationID(taxationID)}
          />
        </View>
        <View style={styles.inputView}>
          <TextInput
            style={styles.inputText}
            placeholder="Enter Amount"
            placeholderTextColor="#fff"
            onChangeText={(userAmount) => setAmount(userAmount)}
          />
        </View>
        <TouchableOpacity
          style={styles.otherBtn}
          onPress={async () => {
            if (!userAmount) {
              alert("Amount Required");
              return;
            }
            if (!taxationID) {
              alert("taxationID Required");
              return;
            }
            var json =
              '{"txnId":' + taxationID + ',"amount":' + userAmount + "}";
            console.log(json);
            var res = await RNEzetapSdk.confirmPreAuth(json);
            console.log(res);
            var json = JSON.parse(res);
            if (json.status == "success") {
              Alert.alert("", json.result.message);
            } else {
              Alert.alert("", json.error.message);
            }
          }}
        >
          <Text style={styles.loginText}> CONFIRM PRE AUTH </Text>
        </TouchableOpacity>
        <View style={styles.space}></View>
        <View style={styles.inputView}>
          <TextInput
            style={styles.inputText}
            placeholder="Enter Taxation ID"
            placeholderTextColor="#fff"
            onChangeText={(taxationID) => setTaxationID(taxationID)}
          />
        </View>
        <TouchableOpacity
          style={styles.otherBtn}
          onPress={async () => {
            if (!taxationID) {
              alert("taxationID Required");
              return;
            }
            var json = '{"txnId":' + taxationID + "}";
            console.log(json);
            var res = await RNEzetapSdk.releasePreAuth(json);
            console.log(res);
            var json = JSON.parse(res);
            if (json.status == "success") {
              Alert.alert("", json.result.message);
            } else {
              Alert.alert("", json.error.message);
            }
          }}
        >
          <Text style={styles.loginText}> RELEASE PRE AUTH </Text>
        </TouchableOpacity>
        <View style={styles.space}></View>
        <View style={styles.inputView}>
          <TextInput
            style={styles.inputText}
            placeholder="Enter Taxation IDs by trailing comma"
            placeholderTextColor="#fff"
            onChangeText={(taxationID) => setTaxationID(taxationID)}
          />
        </View>
        <TouchableOpacity
          style={styles.otherBtn}
          onPress={async () => {
            if (!taxationID) {
              alert("Taxation IDs required");
              return;
            }
            var json = '{"txnIds":[' + taxationID.toString().split(",") + "]}";
            console.log(json);
            var res = await RNEzetapSdk.stopPayment(json);
            console.log(res);
            var json = JSON.parse(res);
            if (json.status == "success") {
              Alert.alert("", json.result.message);
            } else {
              Alert.alert("", json.error.message);
            }
          }}
        >
          <Text style={styles.loginText}> STOP PAYMENT </Text>
        </TouchableOpacity>
      </View>
    </ScrollView>
  );
};

export default HomeScreen;
const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#003f5c",
    alignItems: "center",
    justifyContent: "center",
  },
  logo: {
    fontWeight: "bold",
    fontSize: 50,
    textAlign: "center",
    color: "#fb5b5a",
    marginBottom: 40,
  },
  inputView: {
    width: "80%",
    backgroundColor: "#465881",
    borderRadius: 25,
    height: 50,
    marginBottom: 20,
    justifyContent: "center",
    padding: 20,
  },
  inputText: {
    height: 50,
    color: "white",
  },
  space: {
    height: 10,
  },
  loginBtn: {
    width: "80%",
    backgroundColor: "#fb5b5a",
    borderRadius: 25,
    height: 40,
    alignItems: "center",
    justifyContent: "center",
    marginTop: 10,
    marginBottom: 10,
  },
  otherBtn: {
    width: "80%",
    backgroundColor: "#fb5b5a",
    borderRadius: 25,
    height: 40,
    alignItems: "center",
    justifyContent: "center",
    marginTop: 10,
    marginBottom: 5,
  },
  loginText: {
    color: "white",
  },
});
