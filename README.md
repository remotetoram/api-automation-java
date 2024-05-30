# API Test Automation Framework

## Overview

This Test Framework is designed using Java Spring Boot and leverages Rest Assured for API testing. It generates dynamic payloads using Apache Velocity and supports Behavior-Driven Development (BDD) format for writing test cases. The framework produces comprehensive execution reports in both HTML and PDF formats.

## Usage:
- **Dynamic Payload Generation**: Utilizes Apache Velocity to create dynamic payloads for testing various API scenarios. Framework uses Test Data from specified properties data file and if required it can use generated Test Data in previous steps.

- **Many Reusable StepDefs/Methods**: Wherever possible,use Common step definitions, Reusuable Validation Steps/methods to maintain consistency and reduce code duplication.

- **Azure DevOps Integration**: Integrated with Azure DevOps (ADO), allowing for seamless management of Test Suites, Test Plans, and Test Cases directly within ADO.

- **Mulesoft API via APIM Gateway**: Generates OAuth 2.0 tokens and adds access tokens to API requests when using Mulesoft API via APIM gateway.

- **Continuous Integration Pipeline**: A CI pipeline is configured, enabling scheduled test executions and automatic triggering of tests upon code changes.

- **Comprehensive Reporting**: Generates detailed HTML and PDF reports for test execution, providing a clear view of test results.

The framework is deployed in an Azure repository and is fully integrated with Azure DevOps (ADO) for test management and execution.

## Getting Started

### Prerequisites

- Java 17
- Maven
- Azure DevOps account
- Access to Azure Repositories
- ClientID/Secret setup as enviornment variables for APIM Gateway

### Installation

1. **Clone the repository**:
    ```sh
    git clone https://SynovusFinancial@dev.azure.com/SynovusFinancial/Integration%20Hub/_git/mulesoft-api-test-automation
    cd <repository-directory>
    ```

2. **Build the project**:
    ```sh
    mvn clean install
    
    ```

3. **Run tests**:
    ```sh
    mvn clean test  
   - It will run all the tests with default configuration 
   OR
   mvn clean test -D cucumber.filter.tags="@regression"
   - It will run @regression annotated tests with default configuration 
   OR
   mvn clean test -D cucumber.filter.tags="@functional"
   - It will run @functional annotated tests with default configuration  
   OR
    mvn clean test -D spring.profiles.active=QAPAIM -D cucumber.filter.tags="@apim"
   - It will run @apim annotated tests with QAPAIM profile configuration  
    ```

## Automate API Test in easy way

Follow these steps to quickly create and automate your test scenarios:

1. **Create a short and meaningful test scenario in the feature file**:
    - Write test scenarios using BDD format in your `.feature` files.

2. **Use reusable step definitions**:
    - Leverage existing step definitions from `commonSteps.java` and others to avoid redundancy.

3. **Generate JSON Schema file/Velocity file as per API request**:
    - Ensure the names of the schema/velocity files match the API requests they correspond to.

4. **Keep test data in respective properties files**:
    - Follow the naming conventions used in the velocity file for consistency.
    - To use test data generated in previous steps, utilize the step definition with Additional Test data

5. **Keep API endpoint in `endpoint`/`endpointAPIM` properties file**:
    - Define your API endpoints in the appropriate properties files for easy access and management.

6. **Add specific validation if required**:
    - Implement custom validation logic as necessary to meet your testing requirements.

7. **Run tests using Maven command or from IDE**:

    For Git Pull Request Guidelines and Reusable components details, please see documentation under resource/docs folder.


## Follow Programming Standards in code

    Some common good practices for having clean code in the framework.
1. **Use JAVA coding convensions**
    - All convensions listed - http://pragmatictestlabs.com/2018/03/05/coding-convention-selenium-java/
2.  **Try to avoid using static Methods, rather use springboot beans**
3. **Keep code Seggregated, use Sprintboot standards**
