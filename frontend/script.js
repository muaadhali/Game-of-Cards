const apiBaseUrl = "http://localhost:8081";

async function continuebtn() {
    var url = window.location.pathname;

    switch (url) {
        case "/":
            start();
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
        default:
            break;
    }
}

async function start() {
    try {
        const drawOutput = await fetch(`${apiBaseUrl}/start`);
        const result = await drawOutput.text();
        console.log("Start Game Response:", result); 
        document.getElementById("game-info").value = result;
        document.getElementById("continue-button").innerText = "Continue";
        history.pushState({}, '', '/start');
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
        document.getElementById("continue-button").innerText = "Continue";
        history.pushState({}, '', '/resolveQuest');
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
        }
        history.pushState({}, '', '/askToSponsor');
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
        console.error("Error in trim:", error);
    }
}

async function checkTrim() {
    let gameInfo = document.getElementById("game-info").value;
    if (gameInfo.includes("and some need to trim.") || gameInfo.includes("and needs to trim.") || gameInfo.includes("needs to trim next.")) {
        try {
            const drawOutput = await fetch(`${apiBaseUrl}/fetchTrimmer`);
            const player = await drawOutput.json();
            const result = `Player: ${player.id}\tShields: ${player.shields}\nHand: ${player.hand.map((Card, index) => `|${index + 1}. Name: ${Card.name}, Value: ${Card.value}|`).join(", ")}\n\nTrim ${player.hand.length - 12} cards, type your choices for trimming below.`
            console.log("checkTrim Response:", result); 
            document.getElementById("game-info").value = result;
            document.getElementById("input-section").style.display = "block";
            history.pushState({}, '', '/fetchTrimmer');
        } catch (error) {
            console.error("Error in fetchTrimmer:", error);
        }
    } else {
        start();
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