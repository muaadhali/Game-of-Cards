Feature: Card Game

    Scenario: A1_scenario
        Given a new game with rigged hands from assignment one
        When player 1 draws a 4 stage quest
        And player 1 refuses
        And player 2 accepts
        And current sponsor is set
        And player 2 adds a "Foe" card with value 5
        And player 2 adds a "Horse" card with value 10
        And player finishes making choices
        And player 2 adds a "Foe" card with value 15
        And player 2 adds a "Sword" card with value 10
        And player finishes making choices
        And player 2 adds a "Foe" card with value 15
        And player 2 adds a "Dagger" card with value 5
        And player 2 adds a "Battle-Axe" card with value 15
        And player finishes making choices
        And player 2 adds a "Foe" card with value 40
        And player 2 adds a "Battle-Axe" card with value 15
        And player finishes making choices
        And quest building resolves
        And player 1 accepts
        And player 1 draws a "Foe" card with value 30
        And player 1 trims card 1
        And player 1 adds a "Dagger" card with value 5
        And player 1 adds a "Sword" card with value 10
        And player finishes making choices
        And player 3 accepts
        And player 3 draws a "Sword" card with value 10
        And player 3 trims card 1
        And player 3 adds a "Sword" card with value 10
        And player 3 adds a "Dagger" card with value 5
        And player finishes making choices
        And player 4 accepts
        And player 4 draws a "Battle-Axe" card with value 15
        And player 4 trims card 1
        And player 4 adds a "Dagger" card with value 5
        And player 4 adds a "Horse" card with value 10
        And player finishes making choices
        And stage 1 attacks resolve
        And player 1 accepts
        And player 1 draws a "Foe" card with value 10
        And player 1 adds a "Horse" card with value 10
        And player 1 adds a "Sword" card with value 10
        And player finishes making choices
        And player 3 accepts
        And player 3 draws a "Lance" card with value 20
        And player 3 adds a "Battle-Axe" card with value 15
        And player 3 adds a "Sword" card with value 10
        And player finishes making choices
        And player 4 accepts
        And player 4 draws a "Lance" card with value 20
        And player 4 adds a "Horse" card with value 10
        And player 4 adds a "Battle-Axe" card with value 15
        And player finishes making choices
        And stage 2 attacks resolve
        And player 1 has 0 shields
        And check player1's hand is correct
        And player 3 accepts
        And player 3 draws a "Battle-Axe" card with value 15
        And player 3 adds a "Lance" card with value 20
        And player 3 adds a "Sword" card with value 10
        And player 3 adds a "Battle-Axe" card with value 15
        And player finishes making choices
        And player 4 accepts
        And player 4 draws a "Sword" card with value 10
        And player 4 adds a "Battle-Axe" card with value 15
        And player 4 adds a "Sword" card with value 10
        And player 4 adds a "Lance" card with value 20
        And player finishes making choices
        And stage 3 attacks resolve
        And player 3 accepts
        And player 3 draws a "Foe" card with value 30
        And player 3 adds a "Battle-Axe" card with value 15
        And player 3 adds a "Horse" card with value 10
        And player 3 adds a "Lance" card with value 20
        And player finishes making choices
        And player 4 accepts
        And player 4 draws a "Lance" card with value 20
        And player 4 adds a "Dagger" card with value 5
        And player 4 adds a "Sword" card with value 10
        And player 4 adds a "Lance" card with value 20
        And player 4 adds a "Excalibur" card with value 30
        And player finishes making choices
        And stage 4 attacks resolve
        Then winners are rewarded
        And player 3 has 0 shields
        And player3 has the correct hand
        And player 4 has 4 shields
        And player4 has the correct hand
        And player 2 trims card 1
        And player 2 trims card 1
        And player 2 trims card 1
        And player 2 trims card 1
        And player 2 draws and trims and ends with 12 cards

    Scenario: 2winner_game_2winner_quest
        Given a new game with rigged hands and decks for A2 scenarios
        When player 1 draws a 4 stage quest
        And player 1 accepts
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
        And player 2 accepts
        And player 2 trims card 13
        And player 2 adds a "Horse" card with value 10
        And player finishes making choices
        And player 3 accepts
        And player 3 trims card 13
        And player 3 adds a "Dagger" card with value 5
        And player finishes making choices
        And player 4 accepts
        And player 4 trims card 13
        And player 4 adds a "Horse" card with value 10
        And player finishes making choices
        And stage 1 attacks resolve
        And player 2 accepts
        And player 2 adds a "Battle-Axe" card with value 15
        And player finishes making choices
        And player 4 accepts
        And player 4 adds a "Battle-Axe" card with value 15
        And player finishes making choices
        And stage 2 attacks resolve
        And player 2 accepts
        And player 2 adds a "Lance" card with value 20
        And player finishes making choices
        And player 4 accepts
        And player 4 adds a "Lance" card with value 20
        And player finishes making choices
        And stage 3 attacks resolve
        And player 2 accepts
        And player 2 adds a "Lance" card with value 20
        And player 2 adds a "Sword" card with value 10
        And player finishes making choices
        And player 4 accepts
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
        And player 2 refuses
        And player 3 accepts
        And current sponsor is set
        And player 3 adds a "Foe" card with value 10
        And player finishes making choices
        And player 3 adds a "Foe" card with value 15
        And player finishes making choices
        And player 3 adds a "Foe" card with value 20
        And player finishes making choices
        And quest building resolves
        And player 1 refuses
        And player 2 accepts
        And player 2 adds a "Sword" card with value 10
        And player finishes making choices
        And player 4 accepts
        And player 4 adds a "Sword" card with value 10
        And player finishes making choices
        And stage 1 attacks resolve
        And player 2 accepts
        And player 2 adds a "Battle-Axe" card with value 15
        And player finishes making choices
        And player 4 accepts
        And player 4 adds a "Battle-Axe" card with value 15
        And player finishes making choices
        And stage 2 attacks resolve
        And player 2 accepts
        And player 2 adds a "Battle-Axe" card with value 15
        And player 2 adds a "Horse" card with value 10
        And player finishes making choices
        And player 4 accepts
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
#    Scenario: 1winner_game_with_events
#        Given a new rigged game for 1winner_game_with_events scenario
#        When player 1 draws a 4 stage quest
#        And player 1 accepts
#        And current sponsor is set
#        And player 1 adds a "Foe" card with value 10
#        And player finishes making choices
#        And player 1 adds a "Foe" card with value 15
#        And player finishes making choices
#        And player 1 adds a "Foe" card with value 20
#        And player finishes making choices
#        And player 1 adds a "Foe" card with value 25
#        And player finishes making choices
#        And quest building resolves
