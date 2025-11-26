let numal = parseInt((Math.random() * (70 - 30) + 30)); //Numero principal da operção

// Criando as variaveis com dado aleatorio 

function gerarNumerosUnicos(qtd, min, max) {
    const numeros = new Set();

    while (numeros.size < qtd) {
        const num = Math.floor(Math.random() * (max - min + 1)) + min;
        numeros.add(num); // Set só adiciona se ainda não existir
    }

    return Array.from(numeros);
}

// gera 5 números únicos entre 5 e 25
const [cabo1, cabo2, cabo3, cabo4, cabo5] = gerarNumerosUnicos(5, 5, 25);
let a1 = numal - cabo1;
let a2 = numal - cabo2;
let a3 = numal - cabo3;
let a4 = numal - cabo4;
let a5 = numal - cabo5;
// Insere elas nas divs do html

let numaal = document.getElementById('numal');
let c1 = document.getElementById('cabo1');
let c2 = document.getElementById('cabo2');
let c3 = document.getElementById('cabo3');
let c4 = document.getElementById('cabo4');
let c5 = document.getElementById('cabo5');
c1.innerText = cabo1;
c2.innerText = cabo2;
c3.innerText = cabo3;
c4.innerText = cabo4;
c5.innerText = cabo5;
numaal.innerText = numal;


let jun1 = document.getElementById('jun1');
let jun2 = document.getElementById('jun2');
let jun3 = document.getElementById('jun3');
let jun4 = document.getElementById('jun4');
let jun5 = document.getElementById('jun5');

jun1.innerText = a1;
jun2.innerText = a2;
jun3.innerText = a3;
jun4.innerText = a4;
jun5.innerText = a5;

function Embaralhar(array){
    
    let embara = array.length, ale;
    
    while (embara !== 0){
        ale =  parseInt(Math.random() * embara);
        embara--;
 
        [array[embara], array[ale]] = [
            array[ale], array[embara]];
    }
   return array;
};

const container = document.getElementById('con');
const aa = Array.from(document.querySelectorAll('.a'));

//embaralha a ordem da variaveis
const embaralhados = Embaralhar(aa);

// reinsere no HTML a nova ordem
embaralhados.forEach(el => container.appendChild(el));

const canvas = document.createElement("canvas");
canvas.id = "cordas";
document.body.appendChild(canvas);
const ctx = canvas.getContext("2d");

canvas.width = window.innerWidth;
canvas.height = window.innerHeight;

let start = null;
let caboAtivo = null;
let mouse = { x: 0, y: 0 };
let drawing = false;

const cabos = document.querySelectorAll('[id^="cabo"]');
const juns = document.querySelectorAll('[id^="jun"]');
const conexoes = [];
// NOVA VARIÁVEL: Para rastrear cabos já conectados
const cabosConectados = new Set(); 

// pega o centro de um elemento
function centro(el) {
  const rect = el.getBoundingClientRect();
  return {
    x: rect.left + rect.width / 2,
    y: rect.top + rect.height / 2
  };
}

// desenha tudo
function desenharTudo() {
  ctx.clearRect(0, 0, canvas.width, canvas.height);

  // linhas fixas
  conexoes.forEach(({ x1, y1, x2, y2, cor }) => {
    ctx.beginPath();
    ctx.moveTo(x1, y1);
    ctx.lineTo(x2, y2);
    ctx.strokeStyle = cor;
    ctx.lineWidth = 4;
    ctx.stroke();
  });

  // linha atual
  if (drawing && start) {
    ctx.beginPath();
    ctx.moveTo(start.x, start.y);
    ctx.lineTo(mouse.x, mouse.y);
    ctx.strokeStyle = "yellow";
    ctx.lineWidth = 4;
    ctx.stroke();
  }
}

// atualiza posição do mouse
window.addEventListener("mousemove", (e) => {
  mouse.x = e.clientX;
  mouse.y = e.clientY;
  desenharTudo();
});

// soltar o mouse (final da conexão)
window.addEventListener("mouseup", () => {
  if (!drawing || !caboAtivo) return;

  // verifica se soltou sobre o jun correto
  const numCabo = caboAtivo.id.replace("cabo", ""); // ex: "1"
  const junAlvo = document.getElementById("jun" + numCabo);

  const rect = junAlvo.getBoundingClientRect();
  const dentro =
    mouse.x >= rect.left &&
    mouse.x <= rect.right &&
    mouse.y >= rect.top &&
    mouse.y <= rect.bottom;

  if (dentro) {
    // 1. Conecta corretamente
    const end = centro(junAlvo);
    conexoes.push({
      x1: start.x,
      y1: start.y,
      x2: end.x,
      y2: end.y,
      cor: "yellow"
    });
    // Adiciona o cabo à lista de conectados para bloqueá-lo
    cabosConectados.add(caboAtivo.id); 

    // 2. Verifica se todos os cabos estão conectados (5 acertos)
    if (conexoes.length === 5) {
        alert("Você acertou!"); // 1. Mostra o alerta
        window.location.href = "nova_pagina.html"; // 2. Redireciona para nova página
        // Certifique-se de ter um arquivo 'nova_pagina.html' no mesmo diretório
    }

  }

  // limpa variáveis temporárias
  drawing = false;
  start = null;
  caboAtivo = null;
  desenharTudo();
});

// iniciar o arraste (clique no cabo)
cabos.forEach(cabo => {
  cabo.addEventListener("mousedown", () => {
    // 4. IMPEDE NOVA CORDA EM CABO CONECTADO
    if (cabosConectados.has(cabo.id)) { 
        return; // Não faz nada se o cabo já estiver conectado
    }
    drawing = true;
    caboAtivo = cabo;
    start = centro(cabo);
  });
});

// 3. IMPEDE SELEÇÃO DE TEXTO DO NAVEGADOR
// Adicionado ao canvas para impedir a seleção enquanto desenha a corda
canvas.addEventListener('mousedown', (e) => e.preventDefault());