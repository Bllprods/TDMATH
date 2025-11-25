let q1 = parseInt(Math.random() * (12 - 2)) + 2;
let nua1 = document.getElementById('n1');
let nua2 = document.getElementById('n2');
let nua3 = document.getElementById('n3');
let nua4 = document.getElementById('n4');
nua1.innerText = q1;
nua2.innerText = q1;
nua3.innerText = q1;
nua4.innerText = q1;

resultadoperimetroverdadeiro = q1 + q1 + q1 + q1;
resultadoareaverdadeiro = q1 * q1;

document.querySelectorAll("#numpe1, #numpe2, #numpe3, #numpe4").forEach(input => {
    input.addEventListener("input", () => {
        const n1 = Number(document.getElementById("numpe1").value);
        const n2 = Number(document.getElementById("numpe2").value);
        const n3 = Number(document.getElementById("numpe3").value);
        const n4 = Number(document.getElementById("numpe4").value);

        resultadope = n1 + n2 + n3 + n4;

        let respe = document.getElementById("resultadoperimetro");
        respe.innerText = resultadope;
    })
})

document.querySelectorAll("#numar1, #numar2").forEach(input => {
    input.addEventListener("input", () => {
        const n1 = Number(document.getElementById("numar1").value);
        const n2 = Number(document.getElementById("numar2").value);
     

        resultadoar = n1 * n2;

        let resar = document.getElementById("resultadoarea");
        resar.innerText = resultadoar;
    });
});

function Veri(){
    if(resultadoar == resultadoareaverdadeiro && resultadope == resultadoperimetroverdadeiro){
        alert("Está certo, parabéns")
    }
    else if (resultadoar == resultadoareaverdadeiro && resultadope !== resultadoperimetroverdadeiro) {
        alert("Está certo na área, mas errou no perímetro, tente novamente no perímetro")
    }
    else if(resultadoar !== resultadoareaverdadeiro && resultadope == resultadoperimetroverdadeiro){
        alert("Está certo no perímetro, mas errou na área, tente novamente na área")
    }
    else(
        alert("Está errado, tente novamente")
    );
};