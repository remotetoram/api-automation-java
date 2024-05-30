#@ts7021
Feature: Loan Creations related APIs
    
    @regression  #@tc7022
   Scenario: Validating generateLNAccntNum API
   Given User has created payload with 'generateLNAccntNum.vt' template with data from 'loancreation' file
    When user sends REST request with 'generateLNAccntNum' endpoint
    Then API response status code is 200
    Then Rest contract is valid for 'generateLNAccntNum' endpoint
    And  Validate generateLNAccntNum API response


    @regression  #@tc7022
   Scenario: Validating openLNAccnt & createNewLNNote APIs
    Given User has created payload with 'generateLNAccntNum.vt' template with data from 'loancreation' file
    When user sends REST request with 'generateLNAccntNum' endpoint
    Then API response status code is 200
    Then Validate generateLNAccntNum API response
    When User has created payload with 'openLNAccnt.vt' template with data from 'loancreation' file with data generated earlier
    When user sends REST request with 'openLNAccnt' endpoint
    Then API response status code is 200
    Then Rest contract is valid for 'openLNAccnt' endpoint
    And  Validate openLNAccnt API response
    When User has created payload with 'createNewLNNote.vt' template with data from 'loancreation' file with data generated earlier
    When user sends REST request with 'createNewLNNote' endpoint
    Then API response status code is 200
    Then Rest contract is valid for 'createNewLNNote' endpoint