###
# WARNING: This file is written by the Codebots Platform.
# Any changes to this file will be overwritten.
#
# @author Codebots
###

@CodebotsPlatform
Feature: Scenario12 - New scenario

@scenario12
Scenario: New scenario
Given I navigate to url http://localhost:8080/login
When I type fishnatic@example.com into the element with XPath of //*[@id="username-field"]
  And I type password into the element with XPath of //*[@id="password-field"]
  And I click on the element with XPath of //button[text()='Login']
Then I wait for the url to be http://localhost:8080/
  And I wait for the element with XPath of //h2[text()=' Welcome to Fishnatics '] to be present
