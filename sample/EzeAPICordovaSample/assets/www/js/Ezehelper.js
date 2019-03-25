var initBtn = document.getElementById('btnInitialize');
var btnPrepDevice = document.getElementById('btnPrepDevice');
var payBtnWallet = document.getElementById('btnWalletTxn');
var payBtnCheque = document.getElementById('btnChqTxn');
// var payBtnCNP = document.getElementById('btnCNPTxn');
var payBtn = document.getElementById('btnPaySale');
var payBtnCashback = document.getElementById('btnPayCashback');
var payBtnCashAtPOS = document.getElementById('btnPayCashAtPOS');
var payBtnCash = document.getElementById('btnPayByCash');

var genericPayBtn = document.getElementById('btnGenericPay');
var upiPayBtn = document.getElementById('btnUpiPay');
var remotePayBtn = document.getElementById('btnRemotePay');
var qrCodePayBtn = document.getElementById('btnQrCodePay');
var normalEMIPayBtn = document.getElementById('btnNormalEMIPay');
var brandEMIPayBtn = document.getElementById('btnBrandEMIPay');


var payBtnTransHistory = document.getElementById('btnTransactionHistory');
var btnVoidTransaction = document.getElementById('btnVoidTransaction');
var btnAttachSignature = document.getElementById('btnAttachSignature');
var btnUpdate = document.getElementById('btnUpdate');
var btnLogout = document.getElementById('btnLogout');
var msgDiv = document.getElementById('messageDesc');
var transactionID = "";


document.addEventListener('backbutton', 
						   function(e){
									e.preventDefault();
									navigator.app.exitApp();
									},
						   false);

initBtn.onclick = function(){
	var ezeTapSuccessCallBack = function(response){
		$("#formData").hide();
		$("#messageDiv").show();
		$("#messageTag").text("Transaction successful");
		$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
	};
	var ezeTapFailureCallBack = function(response){
		$("#formData").hide();
		$("#messageDiv").show();
		$("#messageTag").text("Transaction failed");
		$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));			
	};
	var EzetapConfig = {
		   "demoAppKey": "Enter your demo app key",
		   "prodAppKey": "Enter your prod app key",
		   "merchantName": "your merchant name", 
		   "userName": "Your User Name",
		   "currencyCode": "INR", 
		   "appMode": "DEMO/PROD",
		   "captureSignature": "true/false",
		   "prepareDevice": "true/false"
	};
	cordova.exec(ezeTapSuccessCallBack,ezeTapFailureCallBack,"EzeAPIPlugin","initialize",
			[EzetapConfig]);
}

btnPrepDevice.onclick = function(){
	var ezeTapSuccessCallBack = function(response){
		$("#formData").hide();
		$("#messageDiv").show();
		$("#messageTag").text("Transaction successful");
		$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
	};
	var ezeTapFailureCallBack = function(response){
		$("#formData").hide();
		$("#messageDiv").show();
		$("#messageTag").text("Transaction failed");
		$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));			
	};
	cordova.exec(ezeTapSuccessCallBack,ezeTapFailureCallBack,"EzeAPIPlugin","prepareDevice",[]);
}

msgDiv.onclick = function(){
	$("#formData").show();
	$("#messageDiv").hide();
	$("#referenceNumber").val("");
	$("#amount").val("");
	$("#chqNum").val("");
	$("#bankCode").val("");
	$("#bankName").val("");
	$("#bankAccNum").val("");
	$("#chqDate").val("");
	$("#comments").val("");
	$("#amountCashBack").val("");
}
payBtnCashAtPOS.onclick = function(){
	var refNum = $("#referenceNumber").val();
	var amount = $("#amountCashBack").val();
	if(refNum!="" && amount!=""){
		var ezeTapSuccessCallBack = function(response){
			$("#formData").hide();
			$("#messageDiv").show();
			$("#messageTag").text("Transaction successful");
			transactionID = JSON.parse(response).result.txn.txnId;
			$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
		};
		var ezeTapFailureCallBack = function(response){
			$("#formData").hide();
			$("#messageDiv").show();
			$("#messageTag").text("Transaction failed");
			$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));			
		};

		var Request = {
				"amount": 0.00,
				"mode": "CASH@POS",
				"options": {
					"amountCashback": amount,
					"amountTip": 0.0,
					"references": {
						"reference1":refNum
					},
					"customer": {
						"name":$("#name").val(),
						"mobileNo":$("#mobile").val(),
						"email":$("#email").val()
					}
				},
		};
		cordova.exec(ezeTapSuccessCallBack,ezeTapFailureCallBack,"EzeAPIPlugin","cardTransaction",[Request]);
	}else{
		alert("Reference number and Cash Back Amount are the mandatory fields for Cash@POS transaction.");
	}
}
payBtnCashback.onclick = function(){
	var refNum = $("#referenceNumber").val();
	var amount = $("#amount").val();
	var amountCashback = $("#amountCashBack").val();
	if(refNum!="" && amount!="" && amountCashback!=""){
		var ezeTapSuccessCallBack = function(response){
			$("#formData").hide();
			$("#messageDiv").show();
			$("#messageTag").text("Transaction successful");
			transactionID = JSON.parse(response).result.txn.txnId;
			$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
		};
		var ezeTapFailureCallBack = function(response){
			$("#formData").hide();
			$("#messageDiv").show();
			$("#messageTag").text("Transaction failed");
			$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));			
		};

		var Request = {
				"amount": amount,
				"mode": "CASHBACK",
				"options": {
					"amountCashback": amountCashback,
					"amountTip": 0.0,
					"references": {
						"reference1":refNum
					},
					"customer": {
						"name":$("#name").val(),
						"mobileNo":$("#mobile").val(),
						"email":$("#email").val()
					}
				},
		};
		cordova.exec(ezeTapSuccessCallBack,ezeTapFailureCallBack,"EzeAPIPlugin","cardTransaction",[Request]);
	}else{
		alert("Please fill up mandatory fields.");
	}
}

payBtn.onclick = function(){
	var refNum = $("#referenceNumber").val();
	var amount = $("#amount").val();
	if(refNum!="" && amount!=""){
		var ezeTapSuccessCallBack = function(response){
			$("#formData").hide();
			$("#messageDiv").show();
			$("#messageTag").text("Transaction successful");
			transactionID = JSON.parse(response).result.txn.txnId;
			$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
		};
		var ezeTapFailureCallBack = function(response){
			$("#formData").hide();
			$("#messageDiv").show();
			$("#messageTag").text("Transaction failed");
			$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));			
		};

		var Request = {
				"amount": amount,
				"mode": "SALE",
				"options": {
					"amountCashback": 0.0,
					"amountTip": 0.0,
					"references": {
						"reference1":refNum
					},
					"customer": {
						"name":$("#name").val(),
						"mobileNo":$("#mobile").val(),
						"email":$("#email").val()
					}
				},
		};
		cordova.exec(ezeTapSuccessCallBack,ezeTapFailureCallBack,"EzeAPIPlugin","cardTransaction",[Request]);
	}else{
		alert("Please fill up mandatory fields.");
	}
}

payBtnCash.onclick = function(){
	var refNum = $("#referenceNumber").val();
	var amount = $("#amount").val();
	if(refNum!="" && amount!=""){
		var ezeTapSuccessCallBack = function(response){
			$("#formData").hide();
			$("#messageDiv").show();
			$("#messageTag").text("Transaction successful");
			transactionID = JSON.parse(response).result.txn.txnId;
			$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
		};
		var ezeTapFailureCallBack = function(response){
			$("#formData").hide();
			$("#messageDiv").show();
			$("#messageTag").text("Transaction failed");
			$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));			
		};

		var Request = {
				"amount": amount,
				"options": {
					"references": {
						"reference1":refNum
					},
					"customer": {
						"name":$("#name").val(),
						"mobileNo":$("#mobile").val(),
						"email":$("#email").val()
					}
				},
		};
		cordova.exec(ezeTapSuccessCallBack,ezeTapFailureCallBack,"EzeAPIPlugin","cashTransaction",[Request]);
	}else{
		alert("Please fill up mandatory fields.");
	}
}
payBtnWallet.onclick = function(){
    var refNum = $("#referenceNumber").val();
	var amount = $("#amount").val();
	if(refNum!="" && amount!=""){
		var ezeTapSuccessCallBack = function(response){
			$("#formData").hide();
			$("#messageDiv").show();
			$("#messageTag").text("Transaction successful");
			transactionID = JSON.parse(response).result.txn.txnId;
			$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
		};
		var ezeTapFailureCallBack = function(response){
			$("#formData").hide();
			$("#messageDiv").show();
			$("#messageTag").text("Transaction failed");
			$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
		};
		var Request = {
        				"amount": amount,
        				"options": {
        					"references": {
        						"reference1":refNum
        					},
        					"customer": {
        						"name":$("#name").val(),
        						"mobileNo":$("#mobile").val(),
        						"email":$("#email").val()
        					}
        				},
        };
        cordova.exec(ezeTapSuccessCallBack,ezeTapFailureCallBack,"EzeAPIPlugin","walletTransaction",[Request]);
	}else{
	    alert("Please fill up mandatory fields.");
	}
}

payBtnCheque.onclick = function(){
    var refNum = $("#referenceNumber").val();
	var amount = $("#amount").val();
	var chqNum = $("#chqNum").val();
	var bankCode = $("#bankCode").val();
	var bankName = $("#bankName").val();
	var bankAcNum = $("#bankAccNum").val();
	var chqDate = $("#chqDate").val();
	if(refNum!="" && amount!="" && chqNum!="" && bankCode!="" && bankName!="" && bankAcNum!="" && chqDate!=""){
		var ezeTapSuccessCallBack = function(response){
			$("#formData").hide();
			$("#messageDiv").show();
			$("#messageTag").text("Transaction successful");
			transactionID = JSON.parse(response).result.txn.txnId;
			$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
		};
		var ezeTapFailureCallBack = function(response){
			$("#formData").hide();
			$("#messageDiv").show();
			$("#messageTag").text("Transaction failed");
			$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
		};
		var Request = {
        				"amount": amount,
        				"options": {
        					"references": {
        						"reference1":refNum
        					},
        					"customer": {
        						"name":$("#name").val(),
        						"mobileNo":$("#mobile").val(),
        						"email":$("#email").val()
        					}
        				},
        				"cheque": {
        				    "chequeNumber": chqNum,
        				    "bankCode": bankCode,
        				    "bankName": bankName,
        				    "bankAccountNo": bankAcNum,
        				    "chequeDate": chqDate
        				  },
        				  "userName": "Demo user"
        };
        cordova.exec(ezeTapSuccessCallBack,ezeTapFailureCallBack,"EzeAPIPlugin","chequeTransaction",[Request]);
	}else{
	    alert("Please fill up mandatory fields.");
	}
}

genericPayBtn.onclick = function(){
	var refNum = $("#referenceNumber").val();
	var amount = $("#amount").val();
	if(refNum!="" && amount!=""){
		var ezeTapSuccessCallBack = function(response){
			$("#formData").hide();
			$("#messageDiv").show();
			$("#messageTag").text("Transaction successful");
			transactionID = JSON.parse(response).result.txn.txnId;
			$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
		};
		var ezeTapFailureCallBack = function(response){
			$("#formData").hide();
			$("#messageDiv").show();
			$("#messageTag").text("Transaction failed");
			$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
		};

		var Request = {
				"amount": amount,
				"options": {
					"amountCashback": 0.0,
					"amountTip": 0.0,
					"references": {
						"reference1":refNum
					},
					"customer": {
						"name":$("#name").val(),
						"mobileNo":$("#mobile").val(),
						"email":$("#email").val()
					}
				},
		};
		cordova.exec(ezeTapSuccessCallBack,ezeTapFailureCallBack,"EzeAPIPlugin","pay",[Request]);
	}else{
		alert("Please fill up mandatory fields.");
	}
}

upiPayBtn.onclick = function(){
	var refNum = $("#referenceNumber").val();
	var amount = $("#amount").val();
	if(refNum!="" && amount!=""){
		var ezeTapSuccessCallBack = function(response){
			$("#formData").hide();
			$("#messageDiv").show();
			$("#messageTag").text("Transaction successful");
			transactionID = JSON.parse(response).result.txn.txnId;
			$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
		};
		var ezeTapFailureCallBack = function(response){
			$("#formData").hide();
			$("#messageDiv").show();
			$("#messageTag").text("Transaction failed");
			$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
		};

		var Request = {
				"amount": amount,
				"options": {
					"amountCashback": 0.0,
					"amountTip": 0.0,
					"references": {
						"reference1":refNum
					},
					"customer": {
						"name":$("#name").val(),
						"mobileNo":$("#mobile").val(),
						"email":$("#email").val()
					}
				},
		};
		cordova.exec(ezeTapSuccessCallBack,ezeTapFailureCallBack,"EzeAPIPlugin","upiTransaction",[Request]);
	}else{
		alert("Please fill up mandatory fields.");
	}
}
remotePayBtn.onclick = function(){
	var refNum = $("#referenceNumber").val();
	var amount = $("#amount").val();
	if(refNum!="" && amount!=""){
		var ezeTapSuccessCallBack = function(response){
			$("#formData").hide();
			$("#messageDiv").show();
			$("#messageTag").text("Transaction successful");
			transactionID = JSON.parse(response).result.txn.txnId;
			$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
		};
		var ezeTapFailureCallBack = function(response){
			$("#formData").hide();
			$("#messageDiv").show();
			$("#messageTag").text("Transaction failed");
			$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
		};

		var Request = {
				"amount": amount,
				"options": {
					"amountCashback": 0.0,
					"amountTip": 0.0,
					"references": {
						"reference1":refNum
					},
					"customer": {
						"name":$("#name").val(),
						"mobileNo":$("#mobile").val(),
						"email":$("#email").val()
					}
				},
		};
		cordova.exec(ezeTapSuccessCallBack,ezeTapFailureCallBack,"EzeAPIPlugin","remotePayment",[Request]);
	}else{
		alert("Please fill up mandatory fields.");
	}
}
payBtnTransHistory.onclick = function(){
	var ezeTapSuccessCallBack = function(response){
		$("#formData").hide();
		$("#messageDiv").show();
		$("#messageTag").text("Transaction successful");
		$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
	};
	var ezeTapFailureCallBack = function(response){
		$("#formData").hide();
		$("#messageDiv").show();
		$("#messageTag").text("Transaction failed");
		$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));			
	};
	var Request= {
			"agentName": "Demo User",
			"startDate": "1/1/2015",
			"endDate": "12/31/2015",
			"txnType": "cash",
			"txnStatus": "void"		
	};
	cordova.exec(ezeTapSuccessCallBack,ezeTapFailureCallBack,"EzeAPIPlugin","searchTransaction",[Request]);
}

qrCodePayBtn.onclick = function(){
	var refNum = $("#referenceNumber").val();
	var amount = $("#amount").val();
	if(refNum!="" && amount!=""){
		var ezeTapSuccessCallBack = function(response){
			$("#formData").hide();
			$("#messageDiv").show();
			$("#messageTag").text("Transaction successful");
			transactionID = JSON.parse(response).result.txn.txnId;
			$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
		};
		var ezeTapFailureCallBack = function(response){
			$("#formData").hide();
			$("#messageDiv").show();
			$("#messageTag").text("Transaction failed");
			$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
		};

		var Request = {
				"amount": amount,
				"options": {
					"amountCashback": 0.0,
					"amountTip": 0.0,
					"references": {
						"reference1":refNum
					},
					"customer": {
						"name":$("#name").val(),
						"mobileNo":$("#mobile").val(),
						"email":$("#email").val()
					}
				},
		};
		cordova.exec(ezeTapSuccessCallBack,ezeTapFailureCallBack,"EzeAPIPlugin","qrCodeTransaction",[Request]);
	}else{
		alert("Please fill up mandatory fields.");
	}
}

btnNormalEMIPay.onclick = function(){
	var refNum = $("#referenceNumber").val();
	var amount = $("#amount").val();
	if(refNum!="" && amount!=""){
		var ezeTapSuccessCallBack = function(response){
			$("#formData").hide();
			$("#messageDiv").show();
			$("#messageTag").text("Transaction successful");
			transactionID = JSON.parse(response).result.txn.txnId;
			$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
		};
		var ezeTapFailureCallBack = function(response){
			$("#formData").hide();
			$("#messageDiv").show();
			$("#messageTag").text("Transaction failed");
			$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
		};

		var Request = {
				"amount": amount,
				"options": {
					"amountCashback": 0.0,
					"amountTip": 0.0,
					"references": {
						"reference1":refNum
					},
					"customer": {
						"name":$("#name").val(),
						"mobileNo":$("#mobile").val(),
						"email":$("#email").val()
					}
				},
		};
		cordova.exec(ezeTapSuccessCallBack,ezeTapFailureCallBack,"EzeAPIPlugin","normalEMI",[Request]);
	}else{
		alert("Please fill up mandatory fields.");
	}
}

btnBrandEMIPay.onclick = function(){
	var refNum = $("#referenceNumber").val();
	var amount = $("#amount").val();
	if(refNum!="" && amount!=""){
		var ezeTapSuccessCallBack = function(response){
			$("#formData").hide();
			$("#messageDiv").show();
			$("#messageTag").text("Transaction successful");
			transactionID = JSON.parse(response).result.txn.txnId;
			$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
		};
		var ezeTapFailureCallBack = function(response){
			$("#formData").hide();
			$("#messageDiv").show();
			$("#messageTag").text("Transaction failed");
			$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
		};

		var Request = {
				"amount": amount,
				"options": {
					"amountCashback": 0.0,
					"amountTip": 0.0,
					"references": {
						"reference1":refNum
					},
					"customer": {
						"name":$("#name").val(),
						"mobileNo":$("#mobile").val(),
						"email":$("#email").val()
					},
					"productDetails":{
					  "brand":$("#brand").val(),
					  "SKUCode":$("#SKU").val(),
					  "serial":$("#serial").val()
					}
				},
		};
		cordova.exec(ezeTapSuccessCallBack,ezeTapFailureCallBack,"EzeAPIPlugin","brandEMI",[Request]);
	}else{
		alert("Please fill up mandatory fields.");
	}
}

btnVoidTransaction.onclick = function(){
	var ezeTapSuccessCallBack = function(response){
		$("#formData").hide();
		$("#messageDiv").show();
		$("#messageTag").text("Transaction successful");
		$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
	};
	var ezeTapFailureCallBack = function(response){
		$("#formData").hide();
		$("#messageDiv").show();
		$("#messageTag").text("Transaction failed");
		$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));			
	};
	if(transactionID == "" || transactionID == undefined || transactionID == null)
		alert("Unable to find transaction ID, please make a transaction.");
	else
		cordova.exec(ezeTapSuccessCallBack,ezeTapFailureCallBack,"EzeAPIPlugin","voidTransaction",
				[transactionID]);
}
btnAttachSignature.onclick = function(){
	var ezeTapSuccessCallBack = function(response){
		$("#formData").hide();
		$("#messageDiv").show();
		$("#messageTag").text("Transaction successful");
		$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
	};
	var ezeTapFailureCallBack = function(response){
		$("#formData").hide();
		$("#messageDiv").show();
		$("#messageTag").text("Transaction failed");
		$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));			
	};
	var imgElem = document.getElementById('sampleImg');
	var Request= {
			"txnId": transactionID,
			// "tipAmount": 0.00,
			// "emiID":"852134799",
			/*
			 * "image": { "imageData": getBase64Image(imgElem), "imageType":
			 * "PNG", "height": "", "weight": "" }
			 */
	};
	if(transactionID == "" || transactionID == undefined || transactionID == null)
		alert("Unable to find transaction ID, please make a transaction.");
	else{
		cordova.exec(ezeTapSuccessCallBack,ezeTapFailureCallBack,"EzeAPIPlugin","attachSignature",[Request]);
	}
}
var getBase64Image = function(imgElem) {
	var canvas = document.createElement("canvas");
	canvas.width = imgElem.clientWidth;
	canvas.height = imgElem.clientHeight;
	var ctx = canvas.getContext("2d");
	ctx.drawImage(imgElem, 0, 0);
	var dataURL = canvas.toDataURL("image/png");
	return dataURL.replace(/^data:image\/(png|jpg);base64,/, "");
}
btnLogout.onclick = function(){
	var ezeTapSuccessCallBack = function(response){
		$("#formData").hide();
		$("#messageDiv").show();
		$("#messageTag").text("Transaction successful");
		$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
	};
	var ezeTapFailureCallBack = function(response){
		$("#formData").hide();
		$("#messageDiv").show();
		$("#messageTag").text("Transaction failed");
		$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));			
	};
	cordova.exec(ezeTapSuccessCallBack,ezeTapFailureCallBack,"EzeAPIPlugin","close",[]);
}

btnUpdate.onclick = function(){
    var ezeTapSuccessCallBack = function(response){
    		$("#formData").hide();
    		$("#messageDiv").show();
    		$("#messageTag").text("Transaction successful");
    		$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
    	};
    var ezeTapFailureCallBack = function(response){
    		$("#formData").hide();
    		$("#messageDiv").show();
    		$("#messageTag").text("Transaction failed");
    		$("#messageDesc").text("Tap here to do another transaction.\n\n"+JSON.stringify(response));
    };
    cordova.exec(ezeTapSuccessCallBack,ezeTapFailureCallBack,"EzeAPIPlugin","update",[Request]);

}
