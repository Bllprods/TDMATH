let NumP = parseInt((Math.random() * (200 - 30) + 30));//Numero principal da operção (ALVO)
let Mostra = parseInt((Math.random() * (500 - 80) + 80));; // O alvo é o NumP inicial
let Sub = parseInt(Math.random() * (29 - 5)) + 5;//Numero da subtração
let Mult = parseInt(Math.random() * (6 - 2)) + 2;//Numero da multiplicação
let Soma = parseInt(Math.random() * (29 - 5)) + 5;//Numero da soma

// Variável para armazenar o valor inicial embaralhado que o jogador começará
let ValorInicialEmbaralhado; 

window.addEventListener("load", function Embaralhar(){
    let Pontua = document.getElementById('Pontua');
    let Nesc = document.getElementById('fn');
    // Exibe o número ALVO
    Nesc.innerText = Mostra;
    
    let Embaralhador = parseInt(Math.random() * (8 - 1)) + 1;
    
    // --------------------------------------------------------------------------
    // LÓGICA CHAVE: Calcule o VALOR INICIAL a partir do ALVO (Mostra) 
    //               usando as operações INVERSAS.
    // O jogador deve aplicar as operações normais (Soma, Sub, Mult) para reverter.
    // --------------------------------------------------------------------------
    
    if(Embaralhador == 1){
        // Inverso de: N = N / Mult + Sub - Soma
        // Ação para reverter: (N + Soma - Sub) * Mult
        // O valor inicial será (Mostra * Mult - Sub + Soma) para ser inteiro.
        ValorInicialEmbaralhado = Mostra * Mult - Sub + Soma;
        
    }else if(Embaralhador == 2){
        // Inverso de: N = N + (Sub - Soma) / Mult
        // Ação para reverter: N * Mult - (Sub - Soma)
        ValorInicialEmbaralhado = Mostra * Mult - (Sub - Soma);
        
    }else if(Embaralhador == 3 || Embaralhador == 4 || Embaralhador == 5 || Embaralhador == 6 || Embaralhador == 8){
        // Inverso de: N = N + Sub - Soma
        // Ação para reverter: N - Sub + Soma
        ValorInicialEmbaralhado = Mostra - Sub + Soma;
        
    }else if(Embaralhador == 7){
        // Inverso de: N = (N + Sub) - Soma
        // Ação para reverter: N - Sub + Soma
        ValorInicialEmbaralhado = Mostra - Sub + Soma;
    }
    
    // 1. Atualiza o valor de NumP com o valor inicial embaralhado SOLUCIONÁVEL.
    NumP = ValorInicialEmbaralhado;
    
    // 2. Exibe o valor inicial para o jogador.
    Pontua.innerText = NumP;

    let btnsoma = document.getElementById("N1");
    let btnsub = document.getElementById("N2");
    let btnmult = document.getElementById("N3");


    btnsoma.innerText = "+" + Soma;
    btnsub.innerText = "-" + Sub;
    btnmult.innerText = "x" + Mult;
})


function Somar(){
    let Pontua = document.getElementById('Pontua'); 
    NumP = NumP + Soma;
    Pontua.innerText = NumP;
    verifica();
}

function Subtrair(){
    let Pontua = document.getElementById('Pontua');
    NumP = NumP - Sub;
    Pontua.innerText = NumP;
    verifica();
}

function Multiplicar(){
    let Pontua = document.getElementById('Pontua');
    NumP = NumP * Mult;
    Pontua.innerText = NumP;
    verifica();
}

function verifica(){
    if(Mostra < NumP){
    alert("Você passou do limite");

    document.body.classList.add("tremer");

    setTimeout(() => {
    document.body.classList.remove("tremer");
    }, 300); // dura 0.3s

}else if(NumP == Mostra){
    // Alerta de vitória ajustado
    alert("Você ganhou"); 
}else{};
};

function moverPara(id, x, y) {
    const obj = document.getElementById(id);
    obj.style.left = x + "px";  
    obj.style.top = y + "px";   
}

// Quando a página carregar
window.addEventListener("load", function() {
    moverPara("centro", 160, 350);  
    moverPara("esquerda", 10, 450); 
    moverPara("direita", 310, 450); 
});