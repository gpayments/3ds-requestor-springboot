/*
 * GPayments 3DS Server API Reference
 * Welcome to the 3DS Server RESTful API. You can use our API to access 3DS Server functionalities.  The 3DS Server API is organised around REST. Our API has predictable, resource-oriented URLs, and uses HTTP response codes to indicate API errors. We use built-in HTTP features, such as HTTP authentication and HTTP verbs, which are understood by off-the-shelf HTTP clients. We support cross-origin resource sharing, allowing you to interact securely with our API from a client-side web application (though you should never expose your secret API key in any public website’s client-side code). JSON is returned by all API responses, including errors.
 *
 * OpenAPI spec version: 1.0.0
 * Contact: techsupport@gpayments.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.gpayments.requestor.dto.activeserver;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * InitAuthResponseBRW
 */

public class InitAuthResponseBRW {
    @JsonProperty("errorCode")
    private String errorCode = null;

    @JsonProperty("errorComponent")
    private String errorComponent = null;

    @JsonProperty("errorDescription")
    private String errorDescription = null;

    @JsonProperty("errorDetail")
    private String errorDetail = null;

    @JsonProperty("errorMessageType")
    private String errorMessageType = null;

    @JsonProperty("threeDSServerCallbackUrl")
    private String threeDSServerCallbackUrl = null;

    @JsonProperty("threeDSServerTransID")
    private String threeDSServerTransID = null;


    /**
     * Code indicating the type of problem identified in the message.
     *
     * @return errorCode
     **/
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Code indicating the 3-D Secure component that identified the error.
     *
     * @return errorComponent
     **/
    public String getErrorComponent() {
        return errorComponent;
    }

    public void setErrorComponent(String errorComponent) {
        this.errorComponent = errorComponent;
    }


    /**
     * Text describing the problem identified in the message. Length: Maximum 2048 characters
     *
     * @return errorDescription
     **/
    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    /**
     * Additional detail regarding the problem identified in the message. Length: Maximum 2048 characters
     *
     * @return errorDetail
     **/
    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }


    /**
     * Identifies the Message Type that was identified as erroneous.
     *
     * @return errorMessageType
     **/
    public String getErrorMessageType() {
        return errorMessageType;
    }

    public void setErrorMessageType(String errorMessageType) {
        this.errorMessageType = errorMessageType;
    }

    /**
     * This URL returned by 3DS Server where the 3DS Requestor can use it to collect browser information as well as
     * executing 3DS method if supported by ACS for the BIN range.
     *
     * @return threeDSServerCallbackUrl
     **/
    public String getThreeDSServerCallbackUrl() {
        return threeDSServerCallbackUrl;
    }

    public void setThreeDSServerCallbackUrl(String threeDSServerCallbackUrl) {
        this.threeDSServerCallbackUrl = threeDSServerCallbackUrl;
    }

    /**
     * &#39;Universal unique transaction identifier assigned by the 3DS Server to identify a single transaction.
     * Length: 36 characters
     *
     * @return threeDSServerTransID
     **/
    public String getThreeDSServerTransID() {
        return threeDSServerTransID;
    }

    public void setThreeDSServerTransID(String threeDSServerTransID) {
        this.threeDSServerTransID = threeDSServerTransID;
    }

    @Override
    public String toString() {
        return "\n{\n" +
                "\t\"errorCode\":\"" + errorCode + "\",\n" +
                "\t\"errorComponent\":\"" + errorComponent + "\",\n" +
                "\t\"errorDescription\":\"" + errorDescription + "\",\n" +
                "\t\"errorDetail\":\"" + errorDetail + "\",\n" +
                "\t\"errorMessageType\":\"" + errorMessageType + "\",\n" +
                "\t\"threeDSServerCallbackUrl\":\"" + threeDSServerCallbackUrl + "\",\n" +
                "\t\"threeDSServerTransID\":\"" + threeDSServerTransID + "\"\n" +
                '}';
    }
}

