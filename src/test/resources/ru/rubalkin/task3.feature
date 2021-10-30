Feature: Count in deck is correct after drawing X cards from bottom

  Background: Create new deck
    Given New deck
    And Save remaining cards count as "count"

  Scenario: Draw cards from bottom
#    dont work
    When I draw 5 cards from deck bottom and save cards in "cards"
    Then Assert that remaining cards lesser than stored in "count"
    And Assert that all cards from "cards" dont contains in all remaining in deck cards