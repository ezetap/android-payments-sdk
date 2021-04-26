// Import React and Component
import React, { useState, createRef } from "react";
import {
  StyleSheet,
  TextInput,
  View,
  Text,
  ScrollView,
  Image,
  Keyboard,
  TouchableOpacity,
  KeyboardAvoidingView,
} from "react-native";

import RNEzetapSdk from "react-native-ezetap-sdk";
import Loader from "./Components/Loader";
import AsyncStorage from "@react-native-community/async-storage";

const LoginScreen = ({ navigation }) => {
  const [userEmail, setUserEmail] = useState("");
  const [userPassword, setUserPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [errortext, setErrortext] = useState("");

  const passwordInputRef = createRef();

  const handleSubmitPress = async () => {
    setErrortext("");
    if (!userEmail) {
      alert("Username Required");
      return;
    }
    if (!userPassword) {
      alert("Password Required");
      return;
    }
    setLoading(true);
    var initPayloads =
      '{"loginType":"USERID","merchantName":"HDFC_DEMO_MERCHANT","prodAppKey":"eee0c98-cf93-4af1-853e-c583fcdd98kj","userName":' +
      userEmail +
      ',"password":' +
      userPassword +
      ',"currencyCode":"INR","appMode":"DEV11","captureSignature":"true","prepareDevice":"false","appId":"ezetap_android","versionName":"8.6.59","versionCode":"279"}';
    var response = await RNEzetapSdk.initialize(initPayloads);
    console.log(response);
    var jsonData = JSON.parse(response);
    setLoading(false);
    if (jsonData.status == "success") {
      try {
        await AsyncStorage.setItem("user", userEmail);
        navigation.replace("DrawerNavigationRoutes", { rep: response });
      } catch (error) {
        setErrortext(error);
      }
    } else setErrortext(jsonData.error.message);
  };
  const handleAppKeyPress = async () => {
    if (!userEmail) {
      alert("Username Required");
      return;
    }
    setLoading(true);
    var withAppKey =
      '{"userName":' +
      userEmail +
      ',"demoAppKey":"00f27217-2c54-49da-9a20-3cd4fe87ca5d","prodAppKey":"00f27217-2c54-49da-9a20-3cd4fe87ca5d","merchantName":"HDFC_DEMO_MERCHANT","appMode":"DEMO","currencyCode":"INR","captureSignature":false,"prepareDevice":false}';
    var response = await RNEzetapSdk.initialize(withAppKey);
    console.log(response);
    var jsonData = JSON.parse(response);
    setLoading(false);
    if (jsonData.status == "success") {
      try {
        await AsyncStorage.setItem("user", userEmail);
        navigation.replace("DrawerNavigationRoutes", { rep: response });
      } catch (error) {
        setErrortext(error);
      }
    } else setErrortext(jsonData.error.message);
  };
  return (
    <View style={styles.mainBody}>
      <Loader loading={loading} />
      <ScrollView
        keyboardShouldPersistTaps="handled"
        contentContainerStyle={{
          flexGrow: 1,
          justifyContent: "center",
          alignContent: "center",
        }}
      >
        <View>
          <KeyboardAvoidingView enabled>
            <View style={{ alignItems: "center" }}>
              <Image
                source={require("../Image/unnamed.png")}
                style={{
                  width: "50%",
                  height: 100,
                  resizeMode: "contain",
                  margin: 10,
                }}
              />
            </View>
            {/* <Text style={styles.registerTextStyle}>
              Demo based on 9080706051 account
            </Text>
            <Text style={styles.registerTextStyle}>
              username: 9080706051 password : 12345678S
            </Text> */}
            <View style={styles.SectionStyle}>
              <TextInput
                style={styles.inputStyle}
                onChangeText={(UserEmail) => setUserEmail(UserEmail)}
                placeholder="Username"
                placeholderTextColor="#8b9cb5"
                autoCapitalize="none"
                returnKeyType="next"
                onSubmitEditing={() =>
                  passwordInputRef.current && passwordInputRef.current.focus()
                }
                underlineColorAndroid="#f000"
                blurOnSubmit={false}
              />
            </View>
            <View style={styles.SectionStyle}>
              <TextInput
                style={styles.inputStyle}
                onChangeText={(UserPassword) => setUserPassword(UserPassword)}
                placeholder="Enter Password" //12345
                placeholderTextColor="#8b9cb5"
                keyboardType="default"
                ref={passwordInputRef}
                onSubmitEditing={Keyboard.dismiss}
                blurOnSubmit={false}
                secureTextEntry={true}
                underlineColorAndroid="#f000"
                returnKeyType="next"
              />
            </View>
            {errortext != "" ? (
              <Text style={styles.errorTextStyle}>{errortext}</Text>
            ) : null}
            <TouchableOpacity
              style={styles.buttonStyle}
              activeOpacity={0.5}
              onPress={handleSubmitPress}
            >
              <Text style={styles.buttonTextStyle}>LOGIN</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.buttonStyle}
              activeOpacity={0.5}
              onPress={handleAppKeyPress}
            >
              <Text style={styles.buttonTextStyle}>LOGIN WITH APP KEY</Text>
              <Text style={styles.registerTextStyle}>Username Needed</Text>
            </TouchableOpacity>
            <Text style={styles.registerTextStyle}>
              prodAppKey : 00f27217-2c54-49da-9a20-3cd4fe87ca5d
            </Text>
            <Text style={styles.registerTextStyle}>
              demoAppKey : 00f27217-2c54-49da-9a20-3cd4fe87ca5d
            </Text>
          </KeyboardAvoidingView>
        </View>
      </ScrollView>
    </View>
  );
};
export default LoginScreen;

const styles = StyleSheet.create({
  mainBody: {
    flex: 1,
    justifyContent: "center",
    backgroundColor: "#003f5c",
    alignContent: "center",
  },
  SectionStyle: {
    flexDirection: "row",
    height: 40,
    marginTop: 20,
    marginLeft: 35,
    marginRight: 35,
    margin: 10,
  },
  buttonStyle: {
    backgroundColor: "#fb5b5a",
    borderWidth: 0,
    color: "#FFFFFF",
    borderColor: "#7DE24E",
    height: 40,
    alignItems: "center",
    borderRadius: 30,
    marginLeft: 35,
    marginRight: 35,
    marginTop: 20,
    marginBottom: 25,
  },
  buttonTextStyle: {
    color: "#FFFFFF",
    paddingVertical: 10,
    fontSize: 16,
  },
  inputStyle: {
    flex: 1,
    color: "white",
    paddingLeft: 15,
    paddingRight: 15,
    borderWidth: 1,
    borderRadius: 30,
    borderColor: "#dadae8",
  },
  registerTextStyle: {
    color: "#FFFFFF",
    textAlign: "center",
    fontWeight: "bold",
    fontSize: 14,
    alignSelf: "center",
    padding: 10,
  },
  errorTextStyle: {
    color: "red",
    textAlign: "center",
    fontSize: 14,
  },
});
