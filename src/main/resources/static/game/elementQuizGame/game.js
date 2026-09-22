// Initialize Telegram Web App
Telegram.WebApp.ready();
Telegram.WebApp.expand();

const elements = [
    { symbol: "H", name: "Hydrogen" },
    { symbol: "He", name: "Helium" },
    { symbol: "Li", name: "Lithium" },
    { symbol: "C", name: "Carbon" },
    { symbol: "N", name: "Nitrogen" },
    { symbol: "O", name: "Oxygen" },
    { symbol: "Na", name: "Sodium" },
    { symbol: "Fe", name: "Iron" },
    { symbol: "Au", name: "Gold" }
];

let score = 0;
let currentElement = {};

function nextQuestion() {
    currentElement = elements[Math.floor(Math.random() * elements.length)];
    document.getElementById("element-symbol").innerText = currentElement.symbol;

    // Generate choices
    let choices = [currentElement.name];
    while (choices.length < 3) {
        let randomEl = elements[Math.floor(Math.random() * elements.length)].name;
        if (!choices.includes(randomEl)) choices.push(randomEl);
    }
    choices.sort(() => Math.random() - 0.5);

    const container = document.getElementById("options-container");
    container.innerHTML = "";
    choices.forEach(choice => {
        const btn = document.createElement("button");
        btn.className = "btn";
        btn.innerText = choice;
        btn.onclick = () => checkAnswer(choice);
        container.appendChild(btn);
    });
}

function checkAnswer(selected) {
    if (selected === currentElement.name) {
        score += 10;
        document.getElementById("score").innerText = score;
        Telegram.WebApp.HapticFeedback.notificationOccurred('success');
    } else {
        Telegram.WebApp.HapticFeedback.notificationOccurred('error');
    }
    nextQuestion();
}

nextQuestion();