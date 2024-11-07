Feature: Card Game

    Scenario: Compulsory acceptance test from A1
        Given a new game with rigged hands from assignment one
        When player1 draws a four stage quest
        When player2 sponsors and sets up the quest
        When players draw the first time
        When players play the first stage
        When players draw the second time
        When players play the second stage
        Then check player1's hand is correct and has 0 shields

        When players draw the third time
        When players play the third stage
        When players draw the fourth time
        When players play the fourth stage and draw
        Then players are rewarded and have correct number of shields

    Scenario:
