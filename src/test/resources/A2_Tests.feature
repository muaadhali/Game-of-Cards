Feature: Card Game

    Scenario: A1_scenario
        Given a new game with rigged hands from assignment one
        When player 1 draws a 4 stage quest and player 2 decides to sponsor it
        And player2 sets up the quest
        And players draw the first time
        And players play the first stage
        And players draw the second time
        And players play the second stage
        And check player1's hand is correct and has 0 shields

        And players draw the third time
        And players play the third stage
        And players draw the fourth time
        And players play the fourth stage and draw
        Then players are rewarded and have correct number of shields

    Scenario: 2winner_game_2winner_quest
        Given a new game with rigged hands and decks for A2 scenarios
        When player 1 draws a 4 stage quest
        And player accepts
        And current sponsor is set
        And player 1 adds a "Foe" card with value 10
        And player finishes making choices
        And player 1 adds a "Foe" card with value 15
        And player finishes making choices
        And player 1 adds a "Foe" card with value 20
        And player finishes making choices
        And player 1 adds a "Foe" card with value 25
        And player finishes making choices
        And quest building resolves
        And player accepts
        And player trims card 13
        And player 2 adds a "Horse" card with value 10
        And player finishes making choices
        And player accepts
        And player trims card 13
        And player 3 adds a "Dagger" card with value 5
        And player finishes making choices
        And player accepts
        And player trims card 13
        And player 4 adds a "Horse" card with value 10
        And player finishes making choices
        And stage 1 attacks resolve
        And player accepts
        And player 2 adds a "Battle-Axe" card with value 15
        And player finishes making choices
        And player accepts
        And player 4 adds a "Battle-Axe" card with value 15
        And player finishes making choices
        And stage 2 attacks resolve
        And player accepts
        And player 2 adds a "Lance" card with value 20
        And player finishes making choices
        And player accepts
        And player 4 adds a "Lance" card with value 20
        And player finishes making choices
        And stage 3 attacks resolve
        And player accepts
        And player 2 adds a "Lance" card with value 20
        And player 2 adds a "Sword" card with value 10
        And player finishes making choices
        And player accepts
        And player 4 adds a "Lance" card with value 20
        And player 4 adds a "Sword" card with value 10
        And player finishes making choices
        And stage 4 attacks resolve
        And winners are rewarded
        And player 1 has 0 shields
        And player 2 has 4 shields
        And player 3 has 0 shields
        And player 4 has 4 shields
        And player 2 draws a 3 stage quest
        And player refuses
        And player accepts
        And current sponsor is set
        And player 3 adds a "Foe" card with value 10
        And player finishes making choices
        And player 3 adds a "Foe" card with value 15
        And player finishes making choices
        And player 3 adds a "Foe" card with value 20
        And player finishes making choices
        And quest building resolves
        And player refuses
        And player accepts
        And player 2 adds a "Sword" card with value 10
        And player finishes making choices
        And player accepts
        And player 4 adds a "Sword" card with value 10
        And player finishes making choices
        And stage 1 attacks resolve
        And player accepts
        And player 2 adds a "Battle-Axe" card with value 15
        And player finishes making choices
        And player accepts
        And player 4 adds a "Battle-Axe" card with value 15
        And player finishes making choices
        And stage 2 attacks resolve
        And player accepts
        And player 2 adds a "Battle-Axe" card with value 15
        And player 2 adds a "Horse" card with value 10
        And player finishes making choices
        And player accepts
        And player 4 adds a "Battle-Axe" card with value 15
        And player 4 adds a "Lance" card with value 20
        And player finishes making choices
        And stage 3 attacks resolve
        And winners are rewarded
        Then player 1 has 0 shields
        And player 2 has 7 shields
        And player 3 has 0 shields
        And player 4 has 7 shields
        And players "2,4" are winners

#
#        When player 2 draws a 3 stage quest and player 3 decides to sponsor it
#        When player3 builds the quest scenario2
#        When player1 declines and others build their attacks for first stage scenario2
#        When players draw and build their attacks for second stage and quest2 scenario2
#        When players draw and build their attacks for third stage and quest2 scenario2
#        Then player 2 has 7 shields
#        And player 4 has 7 shields
#        Then player 2 is a winner
#        And player 4 is a winner


    #ask about difference between then and when in implementation
    #ask about using little functions instead of big functions

