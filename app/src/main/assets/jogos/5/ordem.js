// =================================================================
// 1. FUNÇÕES AUXILIARES
// =================================================================

// Verifica se os números gerados são únicos
function areNumbersUnique(numbers) {
    return (new Set(numbers)).size === numbers.length;
}

// Verifica se o array está em ordem crescente
function isAscending(array) {
    for (let i = 0; i < array.length - 1; i++) {
        // Se qualquer elemento for maior ou igual ao próximo, não está em ordem
        if (array[i] >= array[i + 1]) {
            return false;
        }
    }
    return true;
}

// =================================================================
// 2. GERAÇÃO DE DADOS INICIAIS
// =================================================================

let or1, or2, or3, or4, or5, or6;

let or; 

do {
    const MIN = 10;
    const MAX = 300;
    const range = MAX - MIN + 1;

    or1 = Math.floor(Math.random() * range) + MIN;
    or2 = Math.floor(Math.random() * range) + MIN;
    or3 = Math.floor(Math.random() * range) + MIN;
    or4 = Math.floor(Math.random() * range) + MIN;
    or5 = Math.floor(Math.random() * range) + MIN;
    or6 = Math.floor(Math.random() * range) + MIN;
    
    or = [or1, or2, or3, or4, or5, or6];
    
} while (!areNumbersUnique(or)); 

document.getElementById("ord1").innerText = or[0];
document.getElementById("ord2").innerText = or[1];
document.getElementById("ord3").innerText = or[2];
document.getElementById("ord4").innerText = or[3];
document.getElementById("ord5").innerText = or[4];
document.getElementById("ord6").innerText = or[5];

// =================================================================
// 3. LÓGICA DE DRAG-AND-DROP E VALIDAÇÃO
// =================================================================

const colunas = document.querySelectorAll(".coluna");
const ID_COLUNA_ORIGEM = "coluna-origem"; 

document.addEventListener("dragstart", (e) => {
    e.target.classList.add('dragging');
});

document.addEventListener("dragend", (e) => {
    e.target.classList.remove('dragging');
    
    const colunaPai = e.target.parentElement;

    if (colunaPai && colunaPai.classList.contains('coluna')) {
        updateOrderArray(colunaPai);
    }
});

colunas.forEach((item) => {
    item.addEventListener("dragover", (e) => {
        if (item.id === ID_COLUNA_ORIGEM) { // Use ID_COLUNA_ORIGEM
            e.preventDefault();
            return;
        }

        e.preventDefault(); // Necessário para permitir o drop
        
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
    let result = null; // Inicializa com null

    for (let refer_cards of cards) {
        const box = refer_cards.getBoundingClientRect();
        const boxCenterY = box.y + box.height / 2;

        if (posY >= boxCenterY) result = refer_cards;
    }
    return result;
}

// =================================================================
// 4. ATUALIZAÇÃO E VERIFICAÇÃO (UPDATE E CALCULA)
// =================================================================

function updateOrderArray(coluna) {
    const itensNaColuna = coluna.querySelectorAll(".item");
    const novaOrdem = []; 
    
    itensNaColuna.forEach(item => {
        const valor = parseInt(item.innerText); 
        novaOrdem.push(valor);
    });

    or = novaOrdem;

    console.log("Nova ordem do array 'or':", or);
    
    calcula(coluna);
}

function calcula(coluna) {
    if (coluna.id === ID_COLUNA_ORIGEM) return; 
    if (isAscending(or)) {
        if (or.length === 6) {
            alert("PARABÉNS! A ordem está Crescente!");
        } else {
            console.log("Ordem crescente, mas ainda faltam itens.");
        }
    } else {
        alert("A ordem NÃO está crescente.");
    }
}