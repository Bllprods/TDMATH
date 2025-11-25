let numal = parseInt((Math.random() * (70 - 30) + 30));//Numero principal da operção
let cabo1 = parseInt(Math.random() * (25 - 5)) + 5;
let cabo2 = parseInt(Math.random() * (25 - 5)) + 5;
let cabo3 = parseInt(Math.random() * (25 - 5)) + 5;
let cabo4 = parseInt(Math.random() * (25 - 5)) + 5;
let cabo5 = parseInt(Math.random() * (25 - 5)) + 5;
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
let a1 = numal - cabo1;
let a2 = numal - cabo2;
let a3 = numal - cabo3;
let a4 = numal - cabo4;
let a5 = numal - cabo5;

function Embaralhar(array){
   
    let embara = array.length, ale;
   
    while (embara !== 0){
        ale =  parseInt(Math.random() * embara);
        embara--;
 
        [array[embara], array[ale]] = [
            array[ale], array[embara]];
    }
   
}
 
const vari = [a1, a2, a3, a4, a5];

Embaralhar(vari)




