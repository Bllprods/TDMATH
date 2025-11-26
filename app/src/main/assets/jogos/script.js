fetch("file:///android_asset/niveis.json")
  .then(res => res.json())
  .then(niveis => {
    niveis.forEach(item => {
      console.log("idNivel:", item.idNivel);
      console.log("operacao:", item.operacao);
    });
  })
  .catch(err => console.error("Erro ao carregar JSON:", err));
