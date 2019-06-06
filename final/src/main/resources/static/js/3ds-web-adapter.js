/*
    Callback for Authentication Result
*/
var _authResultCallback = _onAuthResultReady;

/*
Main Entry
*/
function threeDSAuth(transId, cardHolderInfo, purchaseInfo) {

    var postData = {
        threeDSRequestorTransID: transId,
        purchaseAmount: purchaseInfo.purchaseAmount,
        acctNumber: purchaseInfo.acctNumber,
        cardHolderInfo: cardHolderInfo
    };

    if (purchaseInfo.expiryDate) {
        postData.cardExpiryDate = purchaseInfo.expiryDate;
    }
    if (purchaseInfo.purchaseCurrency) {
        postData.purchaseCurrency = purchaseInfo.purchaseCurrency;
    }

    console.log('init authentication');
    $.ajax({
        url: '/auth/init',
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(postData),
        success: function (data) {
            console.log('init auth returns:', data);
            $('<iframe id="threeds-container" width="0" height="0" style="visibility: hidden;" src="'
                + data.threeDSServerCallbackUrl + '"></iframe>')
            .appendTo('.challengeContainer');
        },
        error: function (request, status, error) {

            console.log('init auth error', status, error)

            alert(error);
        },
        dataType: 'json'

    });

}

function _doAuth(transId) {

    console.log('Do Authentication for transId', transId);

    //first remove any 3dsmethod iframe
    //TODO: make it dynamic
    $('#threeds-container').remove();

    $.post("/auth", {id: transId}).done(function (data) {

        console.log('auth returns:', data);

        if (!data) {
            //TODO: error handling
            alert('error');
            return;
        }

        switch (data.transStatus) {
            case "Y":
                authSuccess(data, transId);
                break;
            case "C":
                // 3D requestor can decide whether to proceed the challenge here
                startChallenge(data.challengeUrl);
                break;
            case "N":
                break;
            case "U":
                break;
            case "R":
                break;
        }

    })
    .fail(function (error) {
        alert('error');
    });

}

function authSuccess(result, transId) {
    //remove the spinnerX
    $(".spinner").remove();
    //call back.
    _onAuthResult(transId, result.transStatus);

}

function startChallenge(url) {
    //remove the spinner
    $(".spinner").remove();

    //create the iframe
    $('<iframe src="' + url
        + '" class="h-100 w-100 border-0" id="challengeWindow" name="challengeWindow"></iframe>')
    .appendTo('.challengeContainer');

}

/**
 Default callback method for Authentication Result Ready Event. This can be customized in MainController.
 TODO: documentation
 **/
function _onAuthResult(transId, authResult) {
    console.log('authentication result is ready: ', transId, authResult);

    //call back
    _authResultCallback(transId);

}

/**
 Default callback method for 3DSMethod Skipped Event. This can be customized in MainController.
 TODO: documentation
 **/
function _on3DSMethodSkipped(transId) {

    console.log('3DS Method is skipped or not presented, now calling doAuth.',
        transId);

    _doAuth(transId);
}

/**
 Default callback method for 3DSMethod Finished Event. This can be customized in MainController.
 TODO: documentation
 **/
function _on3DSMethodFinished(transId) {

    console.log('now calling doAuth. transId=', transId);

    _doAuth(transId);
}

function _onAuthResultReady(transId) {
    //redirect to result page
    window.location.href = "/auth/result?txid=" + transId;
}