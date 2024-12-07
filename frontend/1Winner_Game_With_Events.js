const { Builder, By, until } = require('selenium-webdriver');

async function runA3Scenario2Test() {
    let driver = await new Builder().forBrowser('chrome').build();

    try {
        await driver.get('http://127.0.0.1:8080');
        await driver.sleep(1000);

        let resetButton = await driver.findElement(By.id("reset-button"));
        await resetButton.click(); //reset game
        await driver.sleep(1000);
        
        let rigButton = await driver.findElement(By.id("rigtest-button3"));
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

        await playerInput.sendKeys("2"); //player 1 sets up the second stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("3"); //player 1 sets up the third stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("4"); //player 1 sets up the fourth stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        let gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        let substrings = ["QUEST: |Stages: 4, Shields: 4|	No. of Stages built: 4/4",
                            "Stage 1: 1. |Name: Foe, Value: 5|",
                                "Value = 5",
                            "Stage 2: 1. |Name: Foe, Value: 10|",
                                "Value = 10",
                            "Stage 3: 1. |Name: Foe, Value: 15|",
                                "Value = 15",
                            "Stage 4: 1. |Name: Foe, Value: 20|",
                                "Value = 20"];
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

        await playerInput.sendKeys("3"); //player 2 sets up attack for first stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Sword, Value: 10|"), "Player 2's attack is incorrect for stage 1");

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 3 accepts attacking the first stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("1"); //player 3 trims the first card (F10)
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("3"); //player 3 sets up attack for the first stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Sword, Value: 10|"), "Player 3's attack is incorrect for stage 1");

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 4 accepts attacking the first stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("1"); //player 4 trims the first card (F5)
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("4"); //player 4 sets up attack for the first stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Sword, Value: 10|", "Player 4's attack is incorrect for stage 1"));

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 2 succeeds their attack.\nShields: 0	Hand Size: 11",
                        "Player 3 succeeds their attack.\nShields: 0	Hand Size: 11",
                        "Player 4 succeeds their attack.\nShields: 0	Hand Size: 11"]
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Players' attack resolution not correct for stage 1");

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

        await playerInput.sendKeys("6"); //player 2 builds attack for second stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Horse, Value: 10|"), "Player 2's attack is incorrect for stage 2");

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 3 accepts attacking the second stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("6"); //player 3 builds attack for second stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Horse, Value: 10|"), "Player 3's attack is incorrect for stage 2");

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
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Horse, Value: 10|"), "Player 4's attack is incorrect for stage 2");

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 2 succeeds their attack.\nShields: 0	Hand Size: 11",
                        "Player 3 succeeds their attack.\nShields: 0	Hand Size: 11",
                        "Player 4 succeeds their attack.\nShields: 0	Hand Size: 11"];
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

        await playerInput.sendKeys("8"); //player 2 builds attack for third stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Battle-Axe, Value: 15|"), "Player 2's attack is incorrect for stage 3");

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 3 accepts attacking the third stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("8"); //player 3 builds attack for third stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Battle-Axe, Value: 15|"), "Player 3's attack is incorrect for stage 3");

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

        await playerInput.sendKeys("9"); //player 4 builds attack for third stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Battle-Axe, Value: 15|"), "Player 4's attack is incorrect for stage 3");

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 2 succeeds their attack.\nShields: 0	Hand Size: 11",
                        "Player 3 succeeds their attack.\nShields: 0	Hand Size: 11",
                        "Player 4 succeeds their attack.\nShields: 0	Hand Size: 11"];
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

        await playerInput.sendKeys("10"); //player 2 builds attack for fourth stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Lance, Value: 20|"), "Player 2's attack is incorrect for stage 4");

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 3 accepts attacking the fourth stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("10"); //player 3 builds attack for fourth stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Lance, Value: 20|"), "Player 3's attack is incorrect for stage 4");

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

        await playerInput.sendKeys("11"); //player 4 builds attack for fourth stage
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Lance, Value: 20|"), "Player 4's attack is incorrect for stage 4");

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 2 succeeds their attack.\nShields: 0	Hand Size: 11",
                        "Player 3 succeeds their attack.\nShields: 0	Hand Size: 11",
                        "Player 4 succeeds their attack.\nShields: 0	Hand Size: 11"];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Players' attack resolution not correct for stage 4");

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 2 completed the quest and is rewarded 4 shields",
                        "PLAYER: 2	SHIELDS: 4	HAND SIZE: 11",
                        "HAND: 1. |Name: Foe, Value: 5| 2. |Name: Foe, Value: 5| 3. |Name: Foe, Value: 15| 4. |Name: Foe, Value: 25| 5. |Name: Foe, Value: 30| 6. |Name: Sword, Value: 10| 7. |Name: Sword, Value: 10| 8. |Name: Horse, Value: 10| 9. |Name: Battle-Axe, Value: 15| 10. |Name: Lance, Value: 20| 11. |Name: Excalibur, Value: 30|",
                        "Player 3 completed the quest and is rewarded 4 shields",
                        "PLAYER: 3	SHIELDS: 4	HAND SIZE: 11",
                        "HAND: 1. |Name: Foe, Value: 5| 2. |Name: Foe, Value: 10| 3. |Name: Foe, Value: 10| 4. |Name: Foe, Value: 25| 5. |Name: Foe, Value: 30| 6. |Name: Sword, Value: 10| 7. |Name: Sword, Value: 10| 8. |Name: Horse, Value: 10| 9. |Name: Battle-Axe, Value: 15| 10. |Name: Lance, Value: 20| 11. |Name: Excalibur, Value: 30|",
                        "Player 4 completed the quest and is rewarded 4 shields",
                        "PLAYER: 4	SHIELDS: 4	HAND SIZE: 11",
                        "HAND: 1. |Name: Foe, Value: 20| 2. |Name: Foe, Value: 20| 3. |Name: Foe, Value: 25| 4. |Name: Foe, Value: 25| 5. |Name: Foe, Value: 30| 6. |Name: Foe, Value: 70| 7. |Name: Sword, Value: 10| 8. |Name: Sword, Value: 10| 9. |Name: Horse, Value: 10| 10. |Name: Battle-Axe, Value: 15| 11. |Name: Lance, Value: 20|"]
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Players' attack resolution not correct for stage 4 and quest 1 is recognized to be complete"); 
       
        await playButton.click();
        await driver.sleep(1000);  
        
        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("1,2,4,5"); //player 1 trims 4 cards
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("PLAYER: 1	SHIELDS: 0	HAND SIZE: 12"), "Player 1 has 12 cards after trimming"); 
       
        await playButton.click();
        await driver.sleep(1000);  
        
        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("PLAYER: 2	SHIELDS: 2	HAND SIZE: 11"), "Player 2 shield count is incorrect after drawing plague (should be 2)"); 

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("1,2"); //player 1 trims 2 cards from prosperity draws
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);
        
        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("1"); //player 2 trims 1 cards from prosperity draws
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("1"); //player 3 trims 1 cards from prosperity draws
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("1"); //player 4 trims 1 cards from prosperity draws
        await driver.sleep(1000);
       
        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("2,6"); //player 4 trims 2 cards from queen's favor draws
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        playerInput = await driver.findElement(By.id("trim-input"));
        await playerInput.sendKeys("Yes"); //player 1 accepts to sponsor of quest 2
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("1"); //player 1 sets up the first stage of quest 2
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("1,8"); //player 1 sets up the second stage of quest 2
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("4,7"); //player 1 sets up the third stage of quest 2
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["QUEST: |Stages: 3, Shields: 3|	No. of Stages built: 3/3",
                            "Stage 1: 1. |Name: Foe, Value: 15|",
                                "Value = 15",
                            "Stage 2: 1. |Name: Foe, Value: 15| 2. |Name: Dagger, Value: 5|",
                                "Value = 20",
                            "Stage 3: 1. |Name: Foe, Value: 20| 2. |Name: Dagger, Value: 5|",
                                "Value = 25"];
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "The second quest was built incorrectly");


        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 2 accepts attacking the first stage for quest 2
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

        await playerInput.sendKeys("9"); //player 2 sets up attack for first stage for quest 2
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Battle-Axe, Value: 15|"), "Player 2's attack is incorrect for stage 1 of quest 2");

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 3 accepts attacking the first stage of quest 2
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("1"); //player 3 trims the first card (F10)
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("9"); //player 3 sets up attack for the first stage of quest 2
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Battle-Axe, Value: 15|"), "Player 3's attack is incorrect for stage 1 of quest 2");

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 4 accepts attacking the first stage of quest 2
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("1"); //player 4 trims the first card (F20)
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("10"); //player 4 sets up attack for the first stage of quest 2
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 2 succeeds their attack.\nShields: 2	Hand Size: 11",
                        "Player 3 succeeds their attack.\nShields: 4	Hand Size: 11",
                        "Player 4 fails their attack.\nShields: 4	Hand Size: 11"]
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Players' attack resolution not correct for stage 1 of quest 2");

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 2 accepts attacking the second stage of quest 2
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("10,9"); //player 2 builds attack for second stage of quest 2
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Horse, Value: 10| 2. |Name: Battle-Axe, Value: 15|"), "Player 2's attack is incorrect for stage 2");

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 3 accepts attacking the second stage of quest 2
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("10,6"); //player 3 builds attack for second stage of quest 2
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Sword, Value: 10| 2. |Name: Battle-Axe, Value: 15|"), "Player 3's attack is incorrect for stage 2 of quest 2");

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 2 succeeds their attack.\nShields: 2	Hand Size: 10",
                        "Player 3 succeeds their attack.\nShields: 4	Hand Size: 10"]
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Players' attack resolution not correct for stage 2 of quest 2");

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 2 accepts attacking the third stage of quest 2
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("10,8"); //player 2 builds attack for third stage of quest 2
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Sword, Value: 10| 2. |Name: Lance, Value: 20|"), "Player 2's attack is incorrect for stage 3 of quest 2");

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("Yes"); //player 3 accepts attacking the third stage of quest 2
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("11"); //player 3 builds attack for third stage of quest 2
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("YOUR ATTACK: 1. |Name: Excalibur, Value: 30|"), "Player 3's attack is incorrect for stage 3 of quest 2");

        await playButton.click();
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 2 succeeds their attack.\nShields: 2	Hand Size: 9",
                        "Player 3 succeeds their attack.\nShields: 4	Hand Size: 10"]
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Players' attack resolution not correct for stage 2 of quest 2");


        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 2 completed the quest and is rewarded 3 shields",
                        "PLAYER: 2	SHIELDS: 5	HAND SIZE: 9",
                        "HAND: 1. |Name: Foe, Value: 15| 2. |Name: Foe, Value: 25| 3. |Name: Foe, Value: 30| 4. |Name: Foe, Value: 40| 5. |Name: Sword, Value: 10| 6. |Name: Sword, Value: 10| 7. |Name: Sword, Value: 10| 8. |Name: Horse, Value: 10| 9. |Name: Excalibur, Value: 30|",
                        "Player 3 completed the quest and is rewarded 3 shields",
                        "PLAYER: 3	SHIELDS: 7	HAND SIZE: 10",
                        "HAND: 1. |Name: Foe, Value: 10| 2. |Name: Foe, Value: 25| 3. |Name: Foe, Value: 30| 4. |Name: Foe, Value: 40| 5. |Name: Foe, Value: 50| 6. |Name: Sword, Value: 10| 7. |Name: Sword, Value: 10| 8. |Name: Horse, Value: 10| 9. |Name: Horse, Value: 10| 10. |Name: Lance, Value: 20|",]
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Players' attack resolution not correct for stage 3 and quest 2 is recognized to be complete"); 
    
        await playButton.click();
        await driver.sleep(1000);  
        
        await playButton.click();
        await driver.sleep(1000);

        await playerInput.sendKeys("1,2,3"); //player 1 trims 3 cards
        await driver.sleep(1000);

        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        await console.assert(gameStatus.includes("PLAYER: 1	SHIELDS: 0	HAND SIZE: 12"), "Player 1 has 12 cards after trimming for sponsoring quest 2"); 
       
        await playButton.click();
        await driver.sleep(1000);

        gameStatus = await driver.findElement(By.id('game-info')).getAttribute("value");
        substrings = ["Player 3 wins!",
                        "PLAYER: 1	SHIELDS: 0	HAND SIZE: 12",
                        "HAND: 1. |Name: Foe, Value: 25| 2. |Name: Foe, Value: 25| 3. |Name: Foe, Value: 35| 4. |Name: Dagger, Value: 5| 5. |Name: Dagger, Value: 5| 6. |Name: Sword, Value: 10| 7. |Name: Sword, Value: 10| 8. |Name: Sword, Value: 10| 9. |Name: Sword, Value: 10| 10. |Name: Horse, Value: 10| 11. |Name: Horse, Value: 10| 12. |Name: Horse, Value: 10|",
                        "PLAYER: 2	SHIELDS: 5	HAND SIZE: 9",
                        "HAND: 1. |Name: Foe, Value: 15| 2. |Name: Foe, Value: 25| 3. |Name: Foe, Value: 30| 4. |Name: Foe, Value: 40| 5. |Name: Sword, Value: 10| 6. |Name: Sword, Value: 10| 7. |Name: Sword, Value: 10| 8. |Name: Horse, Value: 10| 9. |Name: Excalibur, Value: 30|",
                        "PLAYER: 3	SHIELDS: 7	HAND SIZE: 10",
                        "HAND: 1. |Name: Foe, Value: 10| 2. |Name: Foe, Value: 25| 3. |Name: Foe, Value: 30| 4. |Name: Foe, Value: 40| 5. |Name: Foe, Value: 50| 6. |Name: Sword, Value: 10| 7. |Name: Sword, Value: 10| 8. |Name: Horse, Value: 10| 9. |Name: Horse, Value: 10| 10. |Name: Lance, Value: 20|",
                        "PLAYER: 4	SHIELDS: 4	HAND SIZE: 11",
                        "HAND: 1. |Name: Foe, Value: 25| 2. |Name: Foe, Value: 25| 3. |Name: Foe, Value: 30| 4. |Name: Foe, Value: 50| 5. |Name: Foe, Value: 70| 6. |Name: Dagger, Value: 5| 7. |Name: Dagger, Value: 5| 8. |Name: Sword, Value: 10| 9. |Name: Sword, Value: 10| 10. |Name: Battle-Axe, Value: 15| 11. |Name: Lance, Value: 20|"]
        await console.assert(substrings.every(substring => gameStatus.includes(substring)), "Players' attack resolution not correct for stage 3 and quest 2 is recognized to be complete"); 
    
    } catch (error) {
        console.error("A3 Scenario 2 (1winner_game_with_events) Test encountered an error: ", error);
    } finally {
        await driver.quit();
    }
}

runA3Scenario2Test();