# 3ds-requestor-springboot
3DS Requstor demo code implemented with SpringBoot for ActiveServer

## **3DS Requestor Setup Guide**

The **3DS Requestor** is the merchant side 3DS2 component and is used to pass 3D Secure data from the consumer device to the 3DS Server. For example, in payment authentication, the 3DS Requestor typically represents the existing merchant web server for online shopping. 

The 3DS Requestor has a relationship with the cardholder either via the 3DS Requestor App, or the 3DS Method/Browser on the consumer device. To process 3-D Secure for **Browser-based** transactions, the 3DS Requestor utilises the 3DS Method to gather browser information/device details and the ACS provides HTML to the browser to display the UI to the cardholder when a challenge is necessary.
 
This section provides an introductory guide on how to integrate your merchant web server with the 3DS Requestor code to connect with ActiveServer, utilising GPayments' sample code. For information regarding Merchant App integration, please refer to the [**ActiveSDK documentation**](https://docs.activesdk.cloud/en/). 

## **Prerequisites**

The following are prerequisites to using this guide:

* Core Java knowledge
* Core web-technologies knowledge (HTML, CSS, Javascript)
* JDK 1.8
* IDE of your choice
* Apache Maven, for installation, please refer to https://maven.apache.org/install.html
* A GIT client
* An [activated and running ActiveServer](https://docs.activeserver.cloud/en/guides/activate_instance/) instance
