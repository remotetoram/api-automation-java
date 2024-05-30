#@ts7021
Feature: SafeBox Transactions related APIs  
   @regression  #@tc7022
   Scenario: Validating GetSafeBoxTransactions API
    Given User has created payload with 'getSafeBoxTxn.vt' template with data from 'safebox' file
    When user sends REST request with 'getSafeBoxTxn' endpoint
    Then API response status code is 200
    Then Rest contract is valid for 'getSafeBoxTxn' endpoint
    And  Response contains correct values for getSafeBoxTxn API

   @regression  #@tc7022
   Scenario: Validating Error message on GetSafeBoxTransactions API with wrong box number
    Given User has created payload with 'getSafeBoxTxn_wrongBoxNumber.vt' template with data from 'safebox' file
    When user sends REST request with 'getSafeBoxTxn' endpoint
    Then API response status code is 400
    Then Validate that API response has error message 'BRANCH, BOX PREFIX, OR BOX NUMBER WAS NOT FOUND'
    
    @regression  #@tc7022
   Scenario: Validating GetSafeBoxOwner API
   Given User has created payload with 'getSafeBoxOwner.vt' template with data from 'safebox' file
    When user sends REST request with 'getSafeBoxOwner' endpoint
    Then API response status code is 200
    Then Rest contract is valid for 'getSafeBoxOwner' endpoint
    And  Response contains correct values for getSafeBoxOwner API
    
   @regression  #@tc7022
   Scenario: Validating GetSafeBoxDeputy API
   Given User has created payload with 'getSafeBoxDeputy.vt' template with data from 'safebox' file
    When user sends REST request with 'getSafeBoxDeputy' endpoint
    Then API response status code is 200
    Then Rest contract is valid for 'getSafeBoxDeputy' endpoint
    And  Response contains correct values for getSafeBoxDeputy API
    
    @regression  #@tc7022
    Scenario: Validating getSBVacantBox API
    Given User has created payload with 'getSBVacantBox.vt' template with data from 'safebox' file
    When user sends REST request with 'getSBVacantBox' endpoint
    Then API response status code is 200
    Then Rest contract is valid for 'getSBVacantBox' endpoint
   