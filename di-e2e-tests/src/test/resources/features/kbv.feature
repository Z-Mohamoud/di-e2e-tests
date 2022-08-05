Feature: Kenneth Decerqueira KBV happy path test.
  Kenneth Decerqueira is an Experian test user with 14 KBV questions

  @Kenneth_KBV_happy
  Scenario Outline: <userName> KBV happy path test in StagingYY
    Given the user is on KBV CRI Staging as "<userName>"
    When the user answers their KBV Question "<answerType>" for "<userName>"
    When the user answers their KBV Question "<answerType>" for "<userName>"
    When the user answers their KBV Question "<answerType>" for "<userName>"
    Then the user should see a "Verification Score" of 2 in the KBV CRI Response
    Examples:
      | userName           | answerType |
      | KennethDecerqueira | Successful |


