const { Builder, By, until } = require('selenium-webdriver');

async function runA3Scenario3Test() {
    let driver = await new Builder().forBrowser('chrome').build();

    try {
        await driver.get('http://127.0.0.1:8080');
        await driver.sleep(1000);

        let resetButton = await driver.findElement(By.id("reset-button"));
        await resetButton.click(); //reset game
        await driver.sleep(1000);
        
        let rigButton = await driver.findElement(By.id("rigtest-button4"));
        await rigButton.click(); //rig cards for scenario
        await driver.sleep(1000);

        let playButton = await driver.findElement(By.id("continue-button"));
        await playButton.click(); //start game and draw quest
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        let playerInput = await driver.findElement(By.id("trim-input"));
        await playerInput.sendKeys("Yes"); //player 1 accepts to sponsor
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("1,3,5,7,9,11"); //player 1 sets up the first stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("1,2,3,4,5,6"); //player 1 sets up the second stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        let gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        let substrings = ["QUEST: |Stages: 2, Shields: 2|	No. of Stages built: 2/2",
                            "Stage 1: 1. |Name: Foe, Value: 50| 2. |Name: Dagger, Value: 5| 3. |Name: Sword, Value: 10| 4. |Name: Horse, Value: 10| 5. |Name: Battle-Axe, Value: 15| 6. |Name: Lance, Value: 20|",
                                "Value = 110",
                            "Stage 2: 1. |Name: Foe, Value: 70| 2. |Name: Dagger, Value: 5| 3. |Name: Sword, Value: 10| 4. |Name: Horse, Value: 10| 5. |Name: Battle-Axe, Value: 15| 6. |Name: Lance, Value: 20|",
                                "Value = 130"];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "The first quest was built incorrectly");

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("Yes"); //player 2 accepts attacking the first stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("1"); //player 2 trims the first card (F5)
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("12"); //player 2 sets up attack for first stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Excalibur, Value: 30|"), "Player 2's attack is incorrect for stage 1");

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("Yes"); //player 3 accepts attacking the first stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("4"); //player 3 trims the first card (F10)
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["YOUR ATTACK:", "VALUE: 0", " "];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Player 3's attack is incorrect for stage 1");

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("Yes"); //player 4 accepts attacking the first stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("4"); //player 4 trims the first card (F10)
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["YOUR ATTACK:", "VALUE: 0", " "];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Player 4's attack is incorrect for stage 1");

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 2 fails their attack.",
                    "Hand: 1. |Name: Foe, Value: 5| 2. |Name: Foe, Value: 5| 3. |Name: Foe, Value: 10| 4. |Name: Foe, Value: 15| 5. |Name: Foe, Value: 15| 6. |Name: Foe, Value: 20| 7. |Name: Foe, Value: 20| 8. |Name: Foe, Value: 25| 9. |Name: Foe, Value: 30| 10. |Name: Foe, Value: 30| 11. |Name: Foe, Value: 40|",
                    "Player 3 fails their attack.",
                    "Hand: 1. |Name: Foe, Value: 5| 2. |Name: Foe, Value: 5| 3. |Name: Foe, Value: 10| 4. |Name: Foe, Value: 15| 5. |Name: Foe, Value: 15| 6. |Name: Foe, Value: 20| 7. |Name: Foe, Value: 20| 8. |Name: Foe, Value: 25| 9. |Name: Foe, Value: 25| 10. |Name: Foe, Value: 30| 11. |Name: Foe, Value: 40| 12. |Name: Lance, Value: 20|",
                    "Player 4 fails their attack.",
                    "Hand: 1. |Name: Foe, Value: 5| 2. |Name: Foe, Value: 5| 3. |Name: Foe, Value: 10| 4. |Name: Foe, Value: 15| 5. |Name: Foe, Value: 15| 6. |Name: Foe, Value: 20| 7. |Name: Foe, Value: 20| 8. |Name: Foe, Value: 25| 9. |Name: Foe, Value: 25| 10. |Name: Foe, Value: 30| 11. |Name: Foe, Value: 50| 12. |Name: Excalibur, Value: 30|"];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Player attacks resolution Player 2,3, and 4's final hands are incorrect for stage 1");

        await playButton.click();
        await driver.sleep(500);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("No attackers left."), "The fact that there are no attackers left is not displayed correctly");

        await playButton.click();
        await driver.sleep(500);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("There are no winners..."), "Quest having no winners isn't displayed correctly");

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("1,2"); //player 1 trims the 2 cards
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["PLAYER: 1	SHIELDS: 0	HAND SIZE: 12", "HAND: 1. |Name: Foe, Value: 15| 2. |Name: Dagger, Value: 5| 3. |Name: Dagger, Value: 5| 4. |Name: Dagger, Value: 5| 5. |Name: Dagger, Value: 5| 6. |Name: Sword, Value: 10| 7. |Name: Sword, Value: 10| 8. |Name: Sword, Value: 10| 9. |Name: Horse, Value: 10| 10. |Name: Horse, Value: 10| 11. |Name: Horse, Value: 10| 12. |Name: Horse, Value: 10|"];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Player 1's hand after trimming is incorrect");
    } catch (error) {
        console.error("A3 Scenario 3 (0winner_quest) Test encountered an error: ", error);
    } finally {
        await driver.quit();
    }
}

runA3Scenario3Test();