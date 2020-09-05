Feature: First feature file
  Scenario: add new todo
    Given todo task 'BDD Learning', targetDate '2020-09-03'
    When creating TODO
    Then TODOs list should contain newly crated TODO


  Scenario: search an todo
    Given todo id 1
    When search in application
    Then result should return 'BDD Learning' Todo

