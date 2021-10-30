Feature: Count in deck is correct after drawing X cards

  Background: Create new deck
    Given New deck

  Scenario: Draw 0 cards
    When I draw 0 cards from deck
    Then Assert in deck 52 cards

  Scenario: Draw max cards
    When I draw 52 cards from deck
    Then Assert in deck 0 cards

  Scenario: Draw 1 card
    When I draw 1 card from deck
    Then Assert in deck 51 cards
