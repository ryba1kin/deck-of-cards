Feature: From deck of Aces can get only Aces

  Background: Create new deck of Aces
    Given New deck of Aces

  Scenario Outline: Draw <cards> card
    When I draw <cards> card from deck
    Then Assert all drawed cards Aces
    Examples:
      | cards |
      | 0     |
      | 1     |
      | 2     |
      | 3     |
      | 4     |