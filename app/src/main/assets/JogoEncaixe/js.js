// --- lógica dos números ---
let result = Math.floor(Math.random() * (25 - 3) + 3);
let soma = Math.floor(Math.random() * (result - 2) + 2);
let resulfinal = `${soma} + ? = ${result}`;
let verdade = result - soma;
// contador de erros
let erros = 0;

// conjunto para evitar repetição
let usados = new Set();

function gerarFalso(verdade, usados) {
  let n;
  do {
    n = verdade + Math.floor(Math.random() * 7) - 3; 
  } while (n === verdade || usados.has(n));
  usados.add(n);
  return n;
}
// gerar falsos únicos
let falso1 = gerarFalso(verdade, usados);
let falso2 = gerarFalso(verdade, usados);
let falso3 = gerarFalso(verdade, usados);
let falso4 = gerarFalso(verdade, usados);

 
const valores = [verdade, falso1, falso2, falso3, falso4];

// ------------------ IMPRIMIR NO HTML ------------------
document.getElementById('resu').innerText = result;
document.getElementById('numero').innerText = soma;
document.getElementById('final').innerText = resulfinal;
document.getElementById('verdade').innerText = verdade;
document.getElementById('falso1').innerText = falso1;
document.getElementById('falso2').innerText = falso2;
document.getElementById('falso3').innerText = falso3;
document.getElementById('falso4').innerText = falso4;

// ------------------ SISTEMA DE ARRASTAR ------------------
const draggables = document.querySelectorAll('.draggable');

draggables.forEach(el => {
  let startX = 0, startY = 0;
  let elStartLeft = 0, elStartTop = 0;
  let dragging = false;

  function onPointerDown(e) {
    // garantir que somente botões principais iniciem o drag 
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
    el.style.top  = elStartTop  + dy + "px";
  }

  function onPointerUp(e) {
    dragging = false;
    try { el.releasePointerCapture(e.pointerId); } catch {}
    el.style.zIndex = "";

    const dentro = document.getElementById("final"); // área de destino
    const dropArea = dentro.getBoundingClientRect();
    const pieceArea = el.getBoundingClientRect();

    const overlap = !(
      pieceArea.right < dropArea.left ||
      pieceArea.left > dropArea.right ||
      pieceArea.bottom < dropArea.top ||
      pieceArea.top > dropArea.bottom
    );

    // Se soltou dentro da área de destino
    if (overlap) { 

      // Se NÃO é a peça verdadeira
      if (el.id !== "verdade") {

        erros++; // conta erro
        alert(`Errou! Tentativas: ${erros}/3`);

        // voltar peça ao lugar inicial
        el.style.left = elStartLeft + "px";
        el.style.top  = elStartTop  + "px";

        // se atingiu 3 erros, reinicia
        if (erros >= 3) {
          alert("Você errou 3 vezes! Reiniciando...");
          location.reload();
        }
        return; // sai para não executar o resto
      }

      // Se for a peça correta, encaixa e trava
      el.style.position = "static";
      el.style.left = "";
      el.style.top = "";
      dentro.appendChild(el);

    //  impede mover qualquer peça depois de acertar
    draggables.forEach(p => {
    const clone = p.cloneNode(true);
   p.replaceWith(clone);
    });

alert("Parabéns! Você acertou!");
      // remove eventos para não mover mais
      const cloned = el.cloneNode(true);
      el.replaceWith(cloned);
    }
  }

  // registrar os listeners (IMPORTANTE)
  el.addEventListener("pointerdown", onPointerDown);
  el.addEventListener("pointermove", onPointerMove);
  el.addEventListener("pointerup", onPointerUp);
}); // <-- fechamento do forEach (muito importante)

// ------------------ FUNÇÃO EMBARALHAR ------------------
function Embaralhar(array) {
  let i = array.length, r;
  while (i !== 0) {
    r = Math.floor(Math.random() * i);
    i--;
    [array[i], array[r]] = [array[r], array[i]];
  }
  return array;
}

// ------------------ EMBARALHAR AS PEÇAS NO BOARD ------------------
const board = document.getElementById("board");
const pecas = Array.from(board.querySelectorAll('.draggable'));

const pecasEmbaralhadas = Embaralhar(pecas);

pecasEmbaralhadas.forEach((p, i) => {
  p.style.top = 20 + (i * 120) + "px";
  p.style.left = "50px";
  board.appendChild(p);
});
