Feature: Customer details related APIs

   @apim @regression 
  Scenario: Validate token generation for APIM(oAuth2)
    Given user gets the oauth token
    Then validate that oauth token is generated
    
   @apim  @regression
   Scenario: Validating GetNameAddress API
    Given User has created payload with 'getNameAddress.vt' template with data from 'customerdetail' file
    When user sends REST request with 'getNameAddress' endpoint
    Then API response status code is 200
    Then Rest contract is valid for 'getNameAddress' endpoint