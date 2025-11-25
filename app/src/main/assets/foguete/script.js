let   NumP = parseInt((Math.random() * (100 - 30) + 30));//Numero principal da operção
let   Mostra = NumP;
let   Sub = parseInt(Math.random() * (29 - 5)) + 5;//Numero da subtração
let   Mult = parseInt(Math.random() * (6 - 2)) + 2;//Numero da multiplicação
let   Soma = parseInt(Math.random() * (29 - 5)) + 5;//Numero da soma


    window.onload = function Embaralhar(){
    let Pontua = document.getElementById('Pontua');
    let Nesc = document.getElementById('fn');
    Nesc.innerText = Mostra;
    
    let Embaralhador = parseInt(Math.random() * (8 - 1)) + 1;
    if(Embaralhador == 1){
        NumP = NumP / Mult + Sub - Soma;
        Pontua.innerText = NumP;

    }else if(Embaralhador == 2){
        NumP = NumP + (Sub - Soma) / Mult;
        Pontua.innerText = NumP;
        
    }else if(Embaralhador == 3){
        NumP = NumP + (Sub - Soma);
        Pontua.innerText = NumP;
        
    }else if(Embaralhador == 4){
        NumP = NumP + Sub - Soma;
        Pontua.innerText = NumP;
        
    }else if(Embaralhador == 5){
        NumP = NumP + Sub - Soma;
        Pontua.innerText = NumP;
       
    }else if(Embaralhador == 6){
        NumP = NumP + Sub - Soma;
        Pontua.innerText = NumP;

    }else if(Embaralhador == 7){
        NumP = (NumP + Sub) - Soma;
        Pontua.innerText = NumP;

    }else{
        NumP = NumP + Sub - Soma;
        Pontua.innerText = NumP;
    }

let btnsoma = document.getElementById("N1");
let btnsub = document.getElementById("N2");
let btnmult = document.getElementById("N3");


btnsoma.textContent=Soma;
btnsub.textContent=Sub;
btnmult.textContent=Mult;
}


    function Somar(){
        NumP = NumP + Soma;
        Pontua.innerText = NumP;
        verifica();
    }

    function Subtrair(){
        NumP = NumP - Sub;
        Pontua.innerText = NumP;
        verifica();
    }

    function Multiplicar(){
        NumP = NumP * Mult;
        Pontua.innerText = NumP;
        verifica();
    }

function verifica(){
    if(Mostra < NumP){
    alert("Você passou do limite");

}else if(NumP == Mostra){
    alert("Você passou");
}else{};
}