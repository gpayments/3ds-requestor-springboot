/**
 * GPayments 3DS Server API Reference Welcome to the 3DS Server RESTful API. You can use our API to
 * access 3DS Server functionalities. The 3DS Server API is organised around REST. Our API has
 * predictable, resource-oriented URLs, and uses HTTP response codes to indicate API errors. We use
 * built-in HTTP features, such as HTTP authentication and HTTP verbs, which are understood by
 * off-the-shelf HTTP clients. We support cross-origin resource sharing, allowing you to interact
 * securely with our API from a dto-side web application (though you should never expose your secret
 * API key in any public website’s dto-side code). JSON is returned by all API responses, including
 * errors.
 *
 * <p>
 *
 * <p>Contact: techsupport@gpayments.com
 *
 * <p>NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git Do not edit the class manually.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gpayments.requestor.dto.activeserver;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AuthResponseBRW
 */
public class AuthResponseBRW {

  @JsonProperty("acsChallengeMandated")
  private String acsChallengeMandated = null;

  @JsonProperty("authenticationType")
  private String authenticationType = null;

  @JsonProperty("authenticationValue")
  private String authenticationValue = null;

  @JsonProperty("cardholderInfo")
  private String cardholderInfo = null;

  @JsonProperty("challengeUrl")
  private String challengeUrl = null;

  @JsonProperty("eci")
  private String eci = null;

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

  @JsonProperty("threeDSServerTransID")
  private String threeDSServerTransID = null;

  @JsonProperty("transStatus")
  private String transStatus = null;

  @JsonProperty("transStatusReason")
  private String transStatusReason = null;

  /**
   * Indication of whether a challenge is required for the transaction to be authorised due to
   * local/regional mandates or other variable.
   *
   * @return acsChallengeMandated
   */
  public String getAcsChallengeMandated() {
    return acsChallengeMandated;
  }

  public void setAcsChallengeMandated(String acsChallengeMandated) {
    this.acsChallengeMandated = acsChallengeMandated;
  }

  /**
   * Indicates the type of authentication method the Issuer will use to challenge the Cardholder
   * whether in the ARes message or what was used by the ACSRenderingType when in the RReq message.
   * Values accepted: 01 = Static 02 = Dynamic 03 = OOB
   *
   * @return authenticationType
   */
  public String getAuthenticationType() {
    return authenticationType;
  }

  public void setAuthenticationType(String authenticationType) {
    this.authenticationType = authenticationType;
  }

  public AuthResponseBRW authenticationValue(String authenticationValue) {
    this.authenticationValue = authenticationValue;
    return this;
  }

  /**
   * Payment System specific value provided as part of the ACSRenderingType registration for each
   * supported DS. Length: 28 characters
   *
   * @return authenticationValue
   */
  public String getAuthenticationValue() {
    return authenticationValue;
  }

  public void setAuthenticationValue(String authenticationValue) {
    this.authenticationValue = authenticationValue;
  }

  /**
   * Text provided by the AcsRenderingType/Issuer to Cardholder during a Frictionless transaction
   * that was not authenticated by the AcsRenderingType. The Issuer can optionally provide
   * information to Cardholder.
   *
   * @return cardholderInfo
   */
  public String getCardholderInfo() {
    return cardholderInfo;
  }

  public void setCardholderInfo(String cardholderInfo) {
    this.cardholderInfo = cardholderInfo;
  }

  public AuthResponseBRW challengeUrl(String challengeUrl) {
    this.challengeUrl = challengeUrl;
    return this;
  }

  /**
   * The challenge URl returned by 3DS Server if challenge is required.
   *
   * @return challengeUrl
   */
  public String getChallengeUrl() {
    return challengeUrl;
  }

  public void setChallengeUrl(String challengeUrl) {
    this.challengeUrl = challengeUrl;
  }

  /**
   * Payment Systemspecific value provided by the ACSRenderingType to indicate the results of the
   * attempt to authenticate the Cardholder. Length: 2 characters
   *
   * @return eci
   */
  public String getEci() {
    return eci;
  }

  public void setEci(String eci) {
    this.eci = eci;
  }

  /**
   * Code indicating the type of problem identified in the message.
   *
   * @return errorCode
   */
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
   */
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
   */
  public String getErrorDescription() {
    return errorDescription;
  }

  public void setErrorDescription(String errorDescription) {
    this.errorDescription = errorDescription;
  }

  /**
   * Additional detail regarding the problem identified in the message. Length: Maximum 2048
   * characters
   *
   * @return errorDetail
   */
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
   */
  public String getErrorMessageType() {
    return errorMessageType;
  }

  public void setErrorMessageType(String errorMessageType) {
    this.errorMessageType = errorMessageType;
  }

  /**
   * 'Universal unique transaction identifier assigned by the 3DS Server to identify a single
   * transaction. Length: 36 characters
   *
   * @return threeDSServerTransID
   */
  public String getThreeDSServerTransID() {
    return threeDSServerTransID;
  }

  public void setThreeDSServerTransID(String threeDSServerTransID) {
    this.threeDSServerTransID = threeDSServerTransID;
  }

  /**
   * Indicates whether a transaction qualifies as an authenticated transaction.
   *
   * @return transStatus
   */
  public String getTransStatus() {
    return transStatus;
  }

  public void setTransStatus(String transStatus) {
    this.transStatus = transStatus;
  }

  /**
   * Provides information on why the Transaction Status field has the specified value.
   *
   * @return transStatusReason
   */
  public String getTransStatusReason() {
    return transStatusReason;
  }

  public void setTransStatusReason(String transStatusReason) {
    this.transStatusReason = transStatusReason;
  }

  @Override
  public String toString() {
    return "\n{\n"
        + "\t\"acsChallengeMandated\":\""
        + acsChallengeMandated
        + "\",\n"
        + "\t\"authenticationType\":\""
        + authenticationType
        + "\",\n"
        + "\t\"authenticationValue\":\""
        + authenticationValue
        + "\",\n"
        + "\t\"cardholderInfo\":\""
        + cardholderInfo
        + "\",\n"
        + "\t\"challengeUrl\":\""
        + challengeUrl
        + "\",\n"
        + "\t\"eci\":\""
        + eci
        + "\",\n"
        + "\t\"errorCode\":\""
        + errorCode
        + "\",\n"
        + "\t\"errorComponent\":\""
        + errorComponent
        + "\",\n"
        + "\t\"errorDescription\":\""
        + errorDescription
        + "\",\n"
        + "\t\"errorDetail\":\""
        + errorDetail
        + "\",\n"
        + "\t\"errorMessageType\":\""
        + errorMessageType
        + "\",\n"
        + "\t\"threeDSServerTransID\":\""
        + threeDSServerTransID
        + "\",\n"
        + "\t\"transStatus\":\""
        + transStatus
        + "\",\n"
        + "\t\"transStatusReason\":\""
        + transStatusReason
        + "\"\n"
        + '}';
  }
}
