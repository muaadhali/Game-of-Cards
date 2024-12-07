const { Builder, By, until } = require('selenium-webdriver');

async function runA1Test() {
    let driver = await new Builder().forBrowser('chrome').build();

    try {
        await driver.get('http://127.0.0.1:8080');
        await driver.sleep(1000);

        let resetButton = await driver.findElement(By.id("reset-button"));
        await resetButton.click(); //reset game
        await driver.sleep(1000);
        
        let rigButton = await driver.findElement(By.id("rigtest-button"));
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
        await playerInput.sendKeys("No"); //player 1 refuses to sponsor
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("Yes"); //player 2 accepts sponsoring the quest
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("1,8"); //player 2 sets up the first stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("2,6"); //player 2 sets up the second stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("2,4,6"); //player 2 sets up the third stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("2,4"); //player 2 sets up the fourth stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        let gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        let substrings = ["QUEST: |Stages: 4, Shields: 4|	No. of Stages built: 4/4",
                            "Stage 1: 1. |Name: Foe, Value: 5| 2. |Name: Horse, Value: 10|",
                                "Value = 15",
                            "Stage 2: 1. |Name: Foe, Value: 15| 2. |Name: Sword, Value: 10|",
                                "Value = 25",
                            "Stage 3: 1. |Name: Foe, Value: 15| 2. |Name: Dagger, Value: 5| 3. |Name: Battle-Axe, Value: 15|",
                                "Value = 35",
                            "Stage 4: 1. |Name: Foe, Value: 40| 2. |Name: Battle-Axe, Value: 15|",
                                "Value = 55"];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "The quest was built incorrectly");

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("Yes"); //player 1 accepts attacking the first stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("1"); //player 1 trims the first card (F5)
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("5,6"); //player 1 sets up attack for first stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Dagger, Value: 5| 2. |Name: Sword, Value: 10|"), "Player 1's attack is incorrect for stage 1");

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("Yes"); //player 3 accepts attacking the first stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("1"); //player 3 trims the first card (F5)
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("5,4"); //player 3 sets up attack for the first stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Dagger, Value: 5| 2. |Name: Sword, Value: 10|"), "Player 3's attack is incorrect for stage 1");

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("Yes"); //player 4 accepts attacking the first stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("1"); //player 4 trims the first card (F5)
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("5,8"); //player 4 sets up attack for the first stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Dagger, Value: 5| 2. |Name: Horse, Value: 10|"), "Player 4's attack is incorrect for stage 1");

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 1 succeeds their attack.",
                        "Player 3 succeeds their attack.",
                        "Player 4 succeeds their attack."];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Players' attack resolution not correct for stage 1");

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("Yes"); //player 1 accepts attacking the second stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("6,7"); //player 1 builds attack for second stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Sword, Value: 10| 2. |Name: Horse, Value: 10|"), "Player 1's attack is incorrect for stage 2");

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("Yes"); //player 3 accepts attacking the second stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("9,6"); //player 3 builds attack for second stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Sword, Value: 10| 2. |Name: Battle-Axe, Value: 15|"), "Player 3's attack is incorrect for stage 2");

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("Yes"); //player 4 accepts attacking the second stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("6,7"); //player 4 builds attack for second stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Horse, Value: 10| 2. |Name: Battle-Axe, Value: 15|"), "Player 4's attack is incorrect for stage 2");
        
        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 1 fails their attack.",
            "Player 3 succeeds their attack.",
            "Player 4 succeeds their attack."];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Players' attack resolution not correct for stage 2");

        substrings = ["Player 1 fails their attack.",
                        "Shields: 0	Hand Size: 9", "Hand: 1. |Name: Foe, Value: 5| 2. |Name: Foe, Value: 10| 3. |Name: Foe, Value: 15| 4. |Name: Foe, Value: 15| 5. |Name: Foe, Value: 30| 6. |Name: Horse, Value: 10| 7. |Name: Battle-Axe, Value: 15| 8. |Name: Battle-Axe, Value: 15| 9. |Name: Lance, Value: 20|"];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)),
                                            "Player 1 has 0 shields and has the correct final hand (F5 F10 F15 F15 F30 Horse Axe Axe Lance)");
        
        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("Yes"); //player 3 accepts attacking the third stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("9,6,5"); //player 3 builds attack for third stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);  
        
        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Sword, Value: 10| 2. |Name: Horse, Value: 10| 3. |Name: Lance, Value: 20|"), "Player 3's attack is incorrect for stage 3");

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("Yes"); //player 4 accepts attacking the third stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("7,5,8"); //player 4 builds attack for third stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);  
        
        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Sword, Value: 10| 2. |Name: Battle-Axe, Value: 15| 3. |Name: Lance, Value: 20|"), "Player 4's attack is incorrect for stage 3");

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 3 succeeds their attack.",
                        "Player 4 succeeds their attack."];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Players' attack resolution not correct for stage 3");

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("Yes"); //player 3 accepts attacking the fourth stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("7,6,8"); //player 3 builds attack for fourth stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);  
        
        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Horse, Value: 10| 2. |Name: Battle-Axe, Value: 15| 3. |Name: Lance, Value: 20|"), "Player 3's attack is incorrect for stage 4");

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("Yes"); //player 4 accepts attacking the fourth stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        await playerInput.sendKeys("4,5,6,8"); //player 4 builds attack for fourth stage
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);  
        
        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Dagger, Value: 5| 2. |Name: Sword, Value: 10| 3. |Name: Lance, Value: 20| 4. |Name: Excalibur, Value: 30|"), "Player 4's attack is incorrect for stage 4");

        await playButton.click();
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 3 fails their attack.",
                        "Player 4 succeeds their attack.",
                        "QUEST COMPLETE"];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Players' attack resolution not correct for stage 4 and quest is recognized to be complete");

        substrings = ["Player 3 fails their attack.",
                        "Shields: 0	Hand Size: 5","Hand: 1. |Name: Foe, Value: 5| 2. |Name: Foe, Value: 5| 3. |Name: Foe, Value: 15| 4. |Name: Foe, Value: 30| 5. |Name: Sword, Value: 10|"];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), 
                            "Player 3 has 0 shields and the correct hand (F5 F5 F15 F30 Sword)");

        await playButton.click();
        await driver.sleep(500);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 4 completed the quest and is rewarded 4 shields",
                        "PLAYER: 4	SHIELDS: 4	HAND SIZE: 4",
                        "HAND: 1. |Name: Foe, Value: 15| 2. |Name: Foe, Value: 15| 3. |Name: Foe, Value: 40| 4. |Name: Lance, Value: 20|"];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)),
                            "Player 4 has 4 shields and the correct hand (F15 F15 F40 Lance)");

        await playButton.click();
        await driver.sleep(500);  
        
        await playButton.click();
        await driver.sleep(500);  

        await playerInput.sendKeys("4,5,6,8"); //player 2 trims 4 cards
        await driver.sleep(500);

        await playButton.click();
        await driver.sleep(500);
           
        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("PLAYER: 2	SHIELDS: 0	HAND SIZE: 12"), "Player 2's hand size is 12 after trimming");

    } catch (error) {
        console.error("A1 Scenario Test encountered an error: ", error);
    } finally {
        await driver.quit();
    }
}

runA1Test();