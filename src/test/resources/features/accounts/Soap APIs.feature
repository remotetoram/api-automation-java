@ts7021
Feature: SOAP API Validation
  @regression  #@tc7022
   Scenario: Get DP Transactions
    Given user sends SOAP request with 'GetDPAcctTxns' endpoint with XML 'GetDPAcctTxns.xml'
    #Then API response status code is 200
    #And  SOAP contract is valid for endpoint 'GetDPAcctTxns'