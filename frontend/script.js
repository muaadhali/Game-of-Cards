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
        console.log("Start Game Response:", result); 
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
        console.log("Start Game Response:", result); 
        document.getElementById("game-info").value = result;
        document.getElementById("continue-button").innerText = "Continue";
        if (result.includes("?")) {
            document.getElementById("input-section").style.display = "block";
            document.getElementById("input-label").innerText = "Enter Yes/No Below";
            document.getElementById("trim-input").placeholder = "Yes";
        }
        history.pushState({}, '', '/resolveQuest');
    } catch (error) {
        console.error("Error in resolveQuest:", error);
    }
}

async function checkTrim() {
    let gameInfo = document.getElementById("game-info").value;
    if (gameInfo.includes("and some need to trim.") || gameInfo.includes("and needs to trim.") || gameInfo.includes("needs to trim next.")) {
        try {
            const drawOutput = await fetch(`${apiBaseUrl}/fetchTrimmer`);
            const player = await drawOutput.json();
            const result = `Player: ${player.id}\tShields: ${player.shields}\nHand: ${player.hand.map((Card, index) => `|${index + 1}. Name: ${Card.name}, Value: ${Card.value}|`).join(", ")}\n\nTrim ${player.hand.length - 12} cards, type your choices for trimming below.`
            console.log("Start Game Response:", result); 
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
        console.log("Start Game Response:", result); 
        document.getElementById("game-info").value = result;
        document.getElementById("trim-input").value = null;
        document.getElementById("trim-input").placeholder = "e.g. 1,2";
        document.getElementById("input-section").style.display = "none";
        history.pushState({}, '', '/trim');
    } catch (error) {
        console.error("Error in trim:", error);
    }
}