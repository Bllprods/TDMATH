// ---- LISTA DE FORMAS ----
const formas = {
  1: { nome: "Quadrado", arquivo: "asQ.png" },
  2: { nome: "Triângulo ", arquivo: "asT.png" },
  3: { nome: "Pentágono", arquivo: "asPen.png" },
  4: { nome: "Hexágono", arquivo: "asH.png" },
  5: { nome: "Losango", arquivo: "asL.png" },
  6: { nome: "Paralelogramo", arquivo: "asPa.png" },
  7: { nome: "Círculo", arquivo: "asC.png" },
  8: { nome: "Retangulo", arquivo: "asP.png" }
};

// ------------------ GERAR VERDADEIRO ------------------
let verdade = Math.floor(Math.random() * 8) + 1; // 1 a 8
let erros = 0;

let usados = new Set();
usados.add(verdade);

// ------------------ GERAR FALSOS ------------------
function gerarFalso(usados) {
  let n;
  do {
    n = Math.floor(Math.random() * 8) + 1;
  } while (usados.has(n));
  usados.add(n);
  return n;
}

let falso1 = gerarFalso(usados);
let falso2 = gerarFalso(usados);
let falso3 = gerarFalso(usados);
let falso4 = gerarFalso(usados);
let falso5  = gerarFalso(usados);
let falso6  = gerarFalso(usados);


// ------------------ MOSTRAR IMAGEM DA FORMA CORRETA ------------------
document.getElementById("dentro").innerHTML = `
<img src="imagem/${formas[verdade].arquivo}" style="width:250px; margin-top:20px;">

`;

// ------------------ COLOCAR OS NOMES NAS PEÇAS ------------------
document.getElementById("verdade").innerText = formas[verdade].nome;
document.getElementById("falso1").innerText = formas[falso1].nome;
document.getElementById("falso2").innerText = formas[falso2].nome;
document.getElementById("falso3").innerText = formas[falso3].nome;
document.getElementById("falso4").innerText = formas[falso4].nome;
document.getElementById("falso5").innerText = formas[falso5].nome;
document.getElementById("falso6").innerText = formas[falso6].nome;

// ------------------ SISTEMA DE ARRASTAR ------------------
const draggables = document.querySelectorAll('.draggable');

draggables.forEach(el => {
  let startX = 0, startY = 0;
  let elStartLeft = 0, elStartTop = 0;
  let dragging = false;

  function onPointerDown(e) {
    if (e.button && e.button !== 0) return;

    el.setPointerCapture(e.pointerId);
    dragging = true;

    startX = e.clientX;
    startY = e.clientY;

    elStartLeft = parseInt(getComputedStyle(el).left) || 0;
    elStartTop  = parseInt(getComputedStyle(el).top) || 0;

    el.style.zIndex = 1000;
  }

  function onPointerMove(e) {
    if (!dragging) return;

    let dx = e.clientX - startX;
    let dy = e.clientY - startY;

    el.style.left = elStartLeft + dx + "px";
    el.style.top  = elStartTop + dy + "px";
  }

  function onPointerUp(e) {
    dragging = false;

    try { el.releasePointerCapture(e.pointerId); } catch {}
    el.style.zIndex = "";

    const dentro = document.getElementById("final");
    const dropArea = dentro.getBoundingClientRect();
    const pieceArea = el.getBoundingClientRect();

    const overlap = !(
      pieceArea.right < dropArea.left ||
      pieceArea.left > dropArea.right ||
      pieceArea.bottom < dropArea.top ||
      pieceArea.top > dropArea.bottom
    );

    if (overlap) {

      //❌ ERRADO
      if (el.id !== "verdade") {
        erros++;
        alert(`Errou! Tentativas: ${erros}/3`);

        el.style.left = elStartLeft + "px";
        el.style.top  = elStartTop + "px";

        if (erros >= 3) {
          alert("Você errou 3 vezes! Reiniciando...");
          location.reload();
        }
        return;
      }

      //✔ CORRETO
      el.style.position = "static";
      el.style.left = "";
      el.style.top = "";
      dentro.appendChild(el);

      // bloqueia tudo
      draggables.forEach(p => {
        const clone = p.cloneNode(true);
        p.replaceWith(clone);
      });

      alert("Parabéns! Você acertou!");
    }
  }

  el.addEventListener("pointerdown", onPointerDown);
  el.addEventListener("pointermove", onPointerMove);
  el.addEventListener("pointerup", onPointerUp);
});

// ------------------ EMBARALHAR ------------------
function Embaralhar(array) {
  let i = array.length, r;
  while (i !== 0) {
    r = Math.floor(Math.random() * i);
    i--;
    [array[i], array[r]] = [array[r], array[i]];
  }
  return array;
}

// ------------------ POSICIONAR PEÇAS ------------------
const board = document.getElementById("board");
const pecas = Array.from(document.querySelectorAll(".draggable"));

Embaralhar(pecas).forEach((p, i) => {
  p.style.top = 20 + (i * 100) + "px";
  p.style.left = "50px";
  board.appendChild(p);
});
