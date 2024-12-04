const apiBaseUrl = "http://localhost:8081";

async function continuebtn() {
    var url = window.location.pathname;

    switch (url) {
        case "/":
            start();
            break;
        case "/newGame":
            resetGame()
            break;
        case "/start":
            const gameInfo = document.getElementById("game-info").value;
            if (gameInfo.includes("Quest")) {
                resolveQuest();
            } else {
                resolveEvent();
            }
            break;
        case "/resolveEvent":
            checkTrim();
            break;
        case "/fetchTrimmer":
            trim();
            break;
        case "/trim":
            checkTrim();
            break;
        case "/resolveQuest":
            askToSponsor();
            break;
        case "/askToSponsor":
            resolveSponsorResponse();
            break;
        case "/resolveSponsorChoice":
            initiateStageBuilding();
            break;
        case "/initiateStageBuilding":
            buildStage();
            break;
        case "/questBuilt":
            askAttacker();
            break;
        case "/askAttacker":
            resolveAttackerResponse();
            break;
        case "/setupAttack":
            askForAttackChoices();
            break;
        case "/askForAttackChoices":
            buildAttack();
            break;
        case "/stageComplete":
            resolveAttacks();
            break;
        case "/questAttacksComplete":
            questReward();
            break;
        case "/questReward":
            sponsorDraw();
            break;
        default:
            break;
    }
}

async function resetGame() {
    try {
        const drawOutput = await fetch(`${apiBaseUrl}/resetGame`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        const result = await drawOutput.text();
        console.log("resetGame Response:", result); 
        document.getElementById("game-info").value = result;
        document.getElementById("continue-button").innerText = "Start";
        document.getElementById("input-section").style.display = "none";
        history.pushState({}, '', '/');
        
    } catch (error) {
        console.error("Error in resetGame:", error);
    }
}

async function start() {
    try {
        const drawOutput = await fetch(`${apiBaseUrl}/start`);
        const result = await drawOutput.text();
        console.log("Start Game Response:", result); 
        document.getElementById("game-info").value = result;
        if (result.includes("WINNERS")) {
            document.getElementById("continue-button").innerText = "New Game";
            history.pushState({}, '', '/newGame');
        } else {
            document.getElementById("continue-button").innerText = "Continue";
            history.pushState({}, '', '/start');
        }
    } catch (error) {
        console.error("Error in startGame:", error);
    }
}

async function resolveEvent() {
    try {
        const drawOutput = await fetch(`${apiBaseUrl}/resolveEvent`);
        const result = await drawOutput.text();
        console.log("resolveEvent Response:", result); 
        document.getElementById("game-info").value = result;
        document.getElementById("continue-button").innerText = "Continue";
        history.pushState({}, '', '/resolveEvent');
    } catch (error) {
        console.error("Error in resolveEvent:", error);
    }
}

async function resolveQuest() {
    try {
        const drawOutput = await fetch(`${apiBaseUrl}/resolveQuest`);
        const result = await drawOutput.text();
        console.log("resolveQuest Response:", result); 
        document.getElementById("game-info").value = result;
        if (result.includes("The quest can be sponsored.")) {
            history.pushState({}, '', '/resolveQuest');
        } else {
            history.pushState({}, '', '/');
        }
        document.getElementById("continue-button").innerText = "Continue";
    } catch (error) {
        console.error("Error in resolveQuest:", error);
    }
}

async function askToSponsor() {
    try {
        const drawOutput = await fetch(`${apiBaseUrl}/askToSponsor`);
        const result = await drawOutput.text();
        console.log("askToSponsor Response:", result); 
        document.getElementById("game-info").value = result;
        document.getElementById("continue-button").innerText = "Continue";
        if (result.includes("?")) {
            document.getElementById("input-section").style.display = "block";
            document.getElementById("input-label").innerText = "Enter Yes/No Below";
            document.getElementById("trim-input").value = null;
            document.getElementById("trim-input").placeholder = "Yes";
            history.pushState({}, '', '/askToSponsor');
        } else {
            history.pushState({}, '', '/');
        }
    } catch (error) {
        console.error("Error in askToSponsor:", error);
    }
}

async function resolveSponsorResponse() {
    try {
        let response = document.getElementById("trim-input").value + " ";
        const drawOutput = await fetch(`${apiBaseUrl}/resolveSponsorChoice`, {
            method: "PUT",
            headers: {
                "Content-type": "text/plain",
            },
            body: response,
        });
        const result = await drawOutput.text();
        console.log("resolveSponsorChoice Response:", result); 
        document.getElementById("game-info").value = result;
        document.getElementById("input-section").style.display = "none";
        if (result.includes("Okay") || result.includes("Invalid")) {
            history.pushState({}, '', '/resolveQuest');
        } else {
            history.pushState({}, '', '/resolveSponsorChoice');
        }
    } catch (error) {
        console.error("Error in resolveSponsorChoice:", error);
    }
}

async function initiateStageBuilding() {
    try {
        const drawOutput = await fetch(`${apiBaseUrl}/initiateStageBuilding`);
        const result = await drawOutput.text();
        console.log("initiateStageBuilding Response:", result); 
        document.getElementById("game-info").value = result;
        document.getElementById("continue-button").innerText = "Continue";
        if (result.includes("Quest building complete")) {
            history.pushState({}, '', '/questBuilt');
        } else {
            document.getElementById("input-section").style.display = "block";
            document.getElementById("input-label").innerText = "Enter Card #'s Below";
            document.getElementById("trim-input").value = null;
            document.getElementById("trim-input").placeholder = "e.g. 1,2";
            history.pushState({}, '', '/initiateStageBuilding');
        }
    } catch (error) {
        console.error("Error in initiateStageBuilding:", error);
    }
}

async function buildStage() {
    try {
        let indices = document.getElementById("trim-input").value;
        const drawOutput = await fetch(`${apiBaseUrl}/buildStage`, {
            method: "PUT",
            headers: {
                "Content-type": "text/plain",
            },
            body: indices,
        });
        const result = await drawOutput.text();
        console.log("trim Response:", result); 
        document.getElementById("game-info").value = result;
        document.getElementById("trim-input").value = null;
        document.getElementById("trim-input").placeholder = "e.g. 1,2";
        document.getElementById("input-section").style.display = "none";
        history.pushState({}, '', '/resolveSponsorChoice');
    } catch (error) {
        console.error("Error in buildStage:", error);
    }
}

async function askAttacker() {
    try {
        const drawOutput = await fetch(`${apiBaseUrl}/askAttacker`);
        const result = await drawOutput.text();
        console.log("askAttacker Response:", result); 
        document.getElementById("game-info").value = result;
        document.getElementById("continue-button").innerText = "Continue";
        if (result.includes("All players have responded")) {
            history.pushState({}, '', '/stageComplete');
        } else if (result.includes("No attackers left.")) {
            history.pushState({}, '', '/questAttacksComplete');
        } else {
            document.getElementById("input-section").style.display = "block";
            document.getElementById("input-label").innerText = "Enter Response Below (Yes/No)";
            document.getElementById("trim-input").value = null;
            document.getElementById("trim-input").placeholder = "e.g. yes";
            history.pushState({}, '', '/askAttacker');
        }
    } catch (error) {
        console.error("Error in askAttacker:", error);
    }
}

async function resolveAttackerResponse() {
    try {
        let response = document.getElementById("trim-input").value + " ";
        const drawOutput = await fetch(`${apiBaseUrl}/resolveAttackerResponse`, {
            method: "PUT",
            headers: {
                "Content-type": "text/plain",
            },
            body: response,
        });
        const result = await drawOutput.text();
        console.log("resolveAttackerResponse Response:", result); 
        document.getElementById("game-info").value = result;
        document.getElementById("input-section").style.display = "none";
        if (result.includes("Okay") || result.includes("Invalid")) {
            history.pushState({}, '', '/questBuilt');
        } else {
            history.pushState({}, '', '/trim');
        }
    } catch (error) {
        console.error("Error in resolveAttackerResponse:", error);
    }
}

async function askForAttackChoices() {
    try {
        const drawOutput = await fetch(`${apiBaseUrl}/askForAttackChoices`);
        const result = await drawOutput.text();
        console.log("askForAttackChoices Response:", result); 
        document.getElementById("game-info").value = result;
        document.getElementById("continue-button").innerText = "Continue";
        document.getElementById("input-section").style.display = "block";
        document.getElementById("input-label").innerText = "Enter Card #'s Below";
        document.getElementById("trim-input").value = null;
        document.getElementById("trim-input").placeholder = "e.g. 1,2";
        history.pushState({}, '', '/askForAttackChoices');
    } catch (error) {
        console.error("Error in askForAttackChoices:", error);
    }
}

async function buildAttack() {
    try {
        let response = document.getElementById("trim-input").value + " ";
        const drawOutput = await fetch(`${apiBaseUrl}/buildAttack`, {
            method: "PUT",
            headers: {
                "Content-type": "text/plain",
            },
            body: response,
        });
        const result = await drawOutput.text();
        console.log("buildAttack Response:", result); 
        document.getElementById("game-info").value = result;
        document.getElementById("input-section").style.display = "none";
        if (result.includes("Invalid")) {
            history.pushState({}, '', '/setupAttack');
        } else {
            history.pushState({}, '', '/questBuilt');
        }
    } catch (error) {
        console.error("Error in buildAttack:", error);
    }
}

async function resolveAttacks() {
    try {
        const drawOutput = await fetch(`${apiBaseUrl}/resolveAttacks`);
        const result = await drawOutput.text();
        console.log("resolveAttacks Response:", result); 
        document.getElementById("game-info").value = result;
        document.getElementById("continue-button").innerText = "Continue";
        if (result.includes("QUEST COMPLETE!!")) {
            history.pushState({}, '', '/questAttacksComplete');
        }
        history.pushState({}, '', '/resolveAttacks');
    } catch (error) {
        console.error("Error in askForAttackChoices:", error);
    }
}

async function questReward() {
    try {
        const drawOutput = await fetch(`${apiBaseUrl}/questReward`);
        const result = await drawOutput.text();
        console.log("questReward Response:", result); 
        document.getElementById("game-info").value = result;
        document.getElementById("continue-button").innerText = "Continue";
        history.pushState({}, '', '/questReward');
    } catch (error) {
        console.error("Error in questReward:", error);
    }
}

async function sponsorDraw() {
    try {
        const drawOutput = await fetch(`${apiBaseUrl}/sponsorDraw`);
        const result = await drawOutput.text();
        console.log("sponsorDraw Response:", result); 
        document.getElementById("game-info").value = result;
        document.getElementById("continue-button").innerText = "Continue";
        history.pushState({}, '', '/trim');
    } catch (error) {
        console.error("Error in sponsorDraw:", error);
    }
}

async function checkTrim() {
    let gameInfo = document.getElementById("game-info").value;
    if (gameInfo.includes("and some need to trim.") || gameInfo.includes("and needs to trim.") || gameInfo.includes("needs to trim next.") || gameInfo.includes("Drawing")) {
        try {
            const drawOutput = await fetch(`${apiBaseUrl}/fetchTrimmer`);
            const player = await drawOutput.json();
            const result = `Player: ${player.id}\tShields: ${player.shields}\nHand: ${player.hand.map((Card, index) => `|${index + 1}. Name: ${Card.name}, Value: ${Card.value}|`).join(", ")}\n\nTrim ${player.hand.length - 12} cards, type your choices for trimming below.`
            console.log("checkTrim Response:", result); 
            document.getElementById("game-info").value = result;
            document.getElementById("input-section").style.display = "block";
            document.getElementById("trim-input").value = null;
            history.pushState({}, '', '/fetchTrimmer');
        } catch (error) {
            console.error("Error in fetchTrimmer:", error);
        }
    } else {
        if (gameInfo.includes("EVENT") || gameInfo.includes("Sponsor")) {
            start();
        } else {
            askForAttackChoices();
        }
    }
}

async function trim() {
    try {
        let indices = document.getElementById("trim-input").value;
        const drawOutput = await fetch(`${apiBaseUrl}/trim`, {
            method: "PUT",
            headers: {
                "Content-type": "text/plain",
            },
            body: indices,
        });
        const result = await drawOutput.text();
        console.log("trim Response:", result); 
        document.getElementById("game-info").value = result;
        document.getElementById("trim-input").value = null;
        document.getElementById("trim-input").placeholder = "e.g. 1,2";
        document.getElementById("input-section").style.display = "none";
        history.pushState({}, '', '/trim');
    } catch (error) {
        console.error("Error in trim:", error);
    }
}