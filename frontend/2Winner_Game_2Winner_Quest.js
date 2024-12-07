const { Builder, By, until } = require('selenium-webdriver');

async function runA3Scenario1Test() {
    let driver = await new Builder().forBrowser('chrome').build();

    try {
        await driver.get('http://127.0.0.1:8080');
        await driver.sleep(1000);

        let resetButton = await driver.findElement(By.id("reset-button"));
        await resetButton.click(); //reset game
        await driver.sleep(1000);
        
        let rigButton = await driver.findElement(By.id("rigtest-button2"));
        await rigButton.click(); //rig cards for scenario
        await driver.sleep(1000);

        let playButton = await driver.findElement(By.id("continue-button"));
        await playButton.click(); //start game and draw quest
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        let playerInput = await driver.findElement(By.id("trim-input"));
        await playerInput.sendKeys("Yes"); //player 1 accepts to sponsor
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("1"); //player 1 sets up the first stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("1,6"); //player 1 sets up the second stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("1,5"); //player 1 sets up the third stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("1,5"); //player 1 sets up the fourth stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        let gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        let substrings = ["QUEST: |Stages: 4, Shields: 4|	No. of Stages built: 4/4",
                            "Stage 1: 1. |Name: Foe, Value: 5|",
                                "Value = 5",
                            "Stage 2: 1. |Name: Foe, Value: 5| 2. |Name: Dagger, Value: 5|",
                                "Value = 10",
                            "Stage 3: 1. |Name: Foe, Value: 10| 2. |Name: Horse, Value: 10|",
                                "Value = 20",
                            "Stage 4: 1. |Name: Foe, Value: 10| 2. |Name: Battle-Axe, Value: 15|",
                                "Value = 25"];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "The first quest was built incorrectly");

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 2 accepts attacking the first stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("1"); //player 2 trims the first card (F5)
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("6"); //player 2 sets up attack for first stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Horse, Value: 10|"), "Player 2's attack is incorrect for stage 1 of the first quest");

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 3 accepts attacking the first stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("1"); //player 3 trims the first card (F5)
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys(""); //player 3 sets up attack for the first stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK:", "VALUE: 0"), "Player 3's attack is incorrect for stage 1");

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 4 accepts attacking the first stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("1"); //player 4 trims the first card (F10)
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("6"); //player 4 sets up attack for the first stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Horse, Value: 10|"), "Player 4's attack is incorrect for stage 1");

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 2 succeeds their attack.",
                    "Player 3 fails their attack.",
                    "Player 4 succeeds their attack."];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Players' attack resolution not correct for stage 1");

        substrings = ["Player 3 fails their attack.",
                    "Shields: 0	Hand: 1. |Name: Foe, Value: 5| 2. |Name: Foe, Value: 5| 3. |Name: Foe, Value: 5| 4. |Name: Foe, Value: 40| 5. |Name: Dagger, Value: 5| 6. |Name: Dagger, Value: 5| 7. |Name: Dagger, Value: 5| 8. |Name: Horse, Value: 10| 9. |Name: Horse, Value: 10| 10. |Name: Horse, Value: 10| 11. |Name: Horse, Value: 10| 12. |Name: Horse, Value: 10|"];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Player 3 has 0 shields and has the correct hand (3xF5, F40, 3xDagger, 5xHorse)");

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 2 accepts attacking the second stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("4"); //player 2 builds attack for second stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Sword, Value: 10|"), "Player 2's attack is incorrect for stage 2");

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 4 accepts attacking the second stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("4"); //player 4 builds attack for second stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Sword, Value: 10|"), "Player 4's attack is incorrect for stage 2");

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 2 succeeds their attack.",
                    "Player 4 succeeds their attack."];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Players' attack resolution not correct for stage 2");

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 2 accepts attacking the third stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("7,5"); //player 2 builds attack for third stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);  
        
        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Sword, Value: 10| 2. |Name: Horse, Value: 10|"), "Player 2's attack is incorrect for stage 3");

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 4 accepts attacking the third stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("7,6"); //player 4 builds attack for third stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);  
        
        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Sword, Value: 10| 2. |Name: Horse, Value: 10|"), "Player 4's attack is incorrect for stage 3");

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 2 succeeds their attack.",
                    "Player 4 succeeds their attack."];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Players' attack resolution not correct for stage 3");


        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 2 accepts attacking the fourth stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("6,7"); //player 2 builds attack for fourth stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);  
        
        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Sword, Value: 10| 2. |Name: Battle-Axe, Value: 15|"), "Player 2's attack is incorrect for stage 4");

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 4 accepts attacking the fourth stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("6,7"); //player 4 builds attack for fourth stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);  
        
        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Sword, Value: 10| 2. |Name: Battle-Axe, Value: 15|"), "Player 4's attack is incorrect for stage 4");

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 2 succeeds their attack.",
                    "Player 4 succeeds their attack.",
                    "QUEST COMPLETE"];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Players' attack resolution not correct for stage 4 and quest is recognized to be complete");

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 2 completed the quest and is rewarded 4 shields",
                        "PLAYER: 2	SHIELDS: 4	HAND SIZE: 9",
                        "HAND: 1. |Name: Foe, Value: 10| 2. |Name: Foe, Value: 15| 3. |Name: Foe, Value: 30| 4. |Name: Foe, Value: 40| 5. |Name: Foe, Value: 50| 6. |Name: Battle-Axe, Value: 15| 7. |Name: Lance, Value: 20| 8. |Name: Lance, Value: 20| 9. |Name: Excalibur, Value: 30|",
                        "Player 4 completed the quest and is rewarded 4 shields",
                        "PLAYER: 4	SHIELDS: 4	HAND SIZE: 9",
                        "HAND: 1. |Name: Foe, Value: 15| 2. |Name: Foe, Value: 20| 3. |Name: Foe, Value: 30| 4. |Name: Foe, Value: 50| 5. |Name: Foe, Value: 70| 6. |Name: Battle-Axe, Value: 15| 7. |Name: Lance, Value: 20| 8. |Name: Lance, Value: 20| 9. |Name: Excalibur, Value: 30|"]
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Players' attack resolution not correct for stage 4 and quest 1 is recognized to be complete"); 
         
        await playButton.click();
        await driver.sleep(1000);  
        
        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("1,2,3,4"); //player 1 trims 4 cards
        await driver.sleep(1000);  
        
        await playButton.click();
        await driver.sleep(1000);  
        
        await playButton.click();
        await driver.sleep(1000);  
        
        await playButton.click();
        await driver.sleep(1000);  
        
        await playButton.click();
        await driver.sleep(1000);
        
        await playerInput.sendKeys("No"); //player 2 refuses to sponsor new quest
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);  
        
        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 3 accepts to sponsor new quest
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);  
        
        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("1"); //player 3 sets up the first stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("1,4"); //player 3 sets up the second stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("1,5"); //player 3 sets up the third stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("No"); //player 1 declines attacking the first stage of quest 2
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 2 accepts attacking the first stage of quest 2
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("6"); //player 2 builds attack for first stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);  
        
        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Dagger, Value: 5|"), "Player 2's attack is incorrect for stage 1");

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 4 accepts attacking the first stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("6"); //player 4 builds attack for first stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);  
        
        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Dagger, Value: 5|"), "Player 4's attack is incorrect for stage 1");

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 2 succeeds their attack.", "Player 4 succeeds their attack."];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Players' attack resolution not correct for stage 1 quest 2");

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 2 accepts attacking the second stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("7"); //player 2 builds attack for second stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);  
        
        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Battle-Axe, Value: 15|"), "Player 2's attack is incorrect for stage 2");

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 4 accepts attacking the second stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("7"); //player 4 builds attack for second stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);  
        
        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Battle-Axe, Value: 15|"), "Player 4's attack is incorrect for stage 2");

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 2 succeeds their attack.", "Player 4 succeeds their attack."];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Players' attack resolution not correct for stage 2 quest 2");

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 2 accepts attacking the third stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("10"); //player 2 builds attack for third stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);  
        
        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Excalibur, Value: 30|"), "Player 2's attack is incorrect for stage 3");

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 4 accepts attacking the third stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("10"); //player 4 builds attack for third stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);  
        
        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Excalibur, Value: 30|"), "Player 4's attack is incorrect for stage 3");

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 2 succeeds their attack.", "Player 4 succeeds their attack."];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Players' attack resolution not correct for stage 2 quest 2");

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 2 completed the quest and is rewarded 3 shields",
                    "Player 4 completed the quest and is rewarded 3 shields"];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Players' attack resolution not correct for stage 4 and quest 2 is recognized to be complete"); 
        
        await playButton.click();
        await driver.sleep(1000); 

        await playButton.click();
        await driver.sleep(1000); 
        
        await playerInput.sendKeys("1,3,4"); //player 3 trims 4 cards
        await driver.sleep(1000); 
        
        await playButton.click();
        await driver.sleep(1000); 

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 2 wins!",
                                                "Player 4 wins!",
                                                "PLAYER: 1	SHIELDS: 0	HAND SIZE: 12",
                                                "HAND: 1. |Name: Foe, Value: 15| 2. |Name: Foe, Value: 15| 3. |Name: Foe, Value: 20| 4. |Name: Foe, Value: 20| 5. |Name: Foe, Value: 20| 6. |Name: Foe, Value: 20| 7. |Name: Foe, Value: 25| 8. |Name: Foe, Value: 25| 9. |Name: Foe, Value: 30| 10. |Name: Horse, Value: 10| 11. |Name: Battle-Axe, Value: 15| 12. |Name: Lance, Value: 20|",
                                                "PLAYER: 2	SHIELDS: 7	HAND SIZE: 9",
                                                "HAND: 1. |Name: Foe, Value: 10| 2. |Name: Foe, Value: 15| 3. |Name: Foe, Value: 15| 4. |Name: Foe, Value: 25| 5. |Name: Foe, Value: 30| 6. |Name: Foe, Value: 40| 7. |Name: Foe, Value: 50| 8. |Name: Lance, Value: 20| 9. |Name: Lance, Value: 20|",
                                                "PLAYER: 3	SHIELDS: 0	HAND SIZE: 12",
                                                "HAND: 1. |Name: Foe, Value: 20| 2. |Name: Foe, Value: 40| 3. |Name: Dagger, Value: 5| 4. |Name: Dagger, Value: 5| 5. |Name: Sword, Value: 10| 6. |Name: Horse, Value: 10| 7. |Name: Horse, Value: 10| 8. |Name: Horse, Value: 10| 9. |Name: Horse, Value: 10| 10. |Name: Battle-Axe, Value: 15| 11. |Name: Battle-Axe, Value: 15| 12. |Name: Lance, Value: 20|",
                                                "PLAYER: 4	SHIELDS: 7	HAND SIZE: 9",
                                                "HAND: 1. |Name: Foe, Value: 15| 2. |Name: Foe, Value: 15| 3. |Name: Foe, Value: 20| 4. |Name: Foe, Value: 25| 5. |Name: Foe, Value: 30| 6. |Name: Foe, Value: 50| 7. |Name: Foe, Value: 70| 8. |Name: Lance, Value: 20| 9. |Name: Lance, Value: 20|"];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Winners are incorrect or players' hands are incorrect"); 
        

    } catch (error) {
        console.error("A3 Scenario 1 (2winner_game_2winner_quest) Test encountered an error: ", error);
    } finally {
        await driver.quit();
    }
}

runA3Scenario1Test();