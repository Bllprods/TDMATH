let or1 = parseInt(Math.random() * (300 - 5)) + 5;
let or2 = parseInt(Math.random() * (300 - 6)) + 6;
let or3 = parseInt(Math.random() * (300 - 7)) + 7;
let or4 = parseInt(Math.random() * (300 - 8)) + 8;
let or5 = parseInt(Math.random() * (300 - 9)) + 9;
let or6 = parseInt(Math.random() * (300 - 10)) + 10;

let o1 = document.getElementById("ord1");
let o2 = document.getElementById("ord2");
let o3 = document.getElementById("ord3");
let o4 = document.getElementById("ord4");
let o5 = document.getElementById("ord5");
let o6 = document.getElementById("ord6");

o1.innerText = or1;
o2.innerText = or2;
o3.innerText = or3;
o4.innerText = or4;
o5.innerText = or5;
o6.innerText = or6;

const colunas = document.querySelectorAll(".coluna");

document.addEventListener("dragstart", (e) => {
    e.target.classList.add(dragging);
});

document.addEventListener("dragend", (e) => {
    e.target.classList.remove(dragging);
});

colunas.forEach((item) => {
    item.addEventListener("dragover", (e) => {
        const dragging = document.querySelector(".dragging");
        const applyAfter = getNewPosition(item, e.clientY);

        if(applyAfter){
            applyAfter.insertAdjacentElement("afterend", dragging);
        } else {
            item.prepend(dragging);
        }
    });
});

function getNewPosition(coluna, posY) {
    const cards = coluna.querySelectorAll(".item:not(.dragging)");
    let result;

    for (let refer_cards of cards) {
        const box = refer_cards.getBoundingClientRect();
        const boxCenterY = box.y + box.height / 2;

        if (posY >= boxCenterY) result = refer_cards;
    }
    return result;
}