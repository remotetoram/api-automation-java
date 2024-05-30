#@ts7021
Feature: Stop Payments related APIs     
   @regression  #@tc7022
   Scenario: Validating StopPaymentService API
   Given User has created payload with 'addStop.vt' template with data from 'stoppayments' file
    When user sends REST request with 'addStop' endpoint
    Then API response status code is 200
    Then Rest contract is valid for 'addStop' endpoint
    And  Response contains correct values for addStop API
    When User has created payload with 'getStop.vt' template with data from 'stoppayments' file
    When user sends REST request with 'getStop' endpoint
    Then API response status code is 200
    Then Rest contract is valid for 'getStop' endpoint
    And  Response contains newly added stop Payment
    When User has created payload with 'deleteStop.vt' template with data from 'stoppayments' file with data generated earlier
    When user sends REST request with 'deleteStop' endpoint
    Then API response status code is 200
    Then Rest contract is valid for 'deleteStop' endpoint