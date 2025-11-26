package etec.sp.gov.br.com.example.tdmath.model;

public class nivel {
    private int idNivel;
    private int pontuacao;
    private int fkIdMap;

    public nivel(int idNivel, int pontuacao, int fkIdMap) {
        this.idNivel = idNivel;
        this.pontuacao = pontuacao;
        this.fkIdMap = fkIdMap;
    }

    public int getIdNivel() { return idNivel; }
    public int getPontuacao() { return pontuacao; }
    public int getFkIdMap() { return fkIdMap; }

    public void setFkIdMap(int fkIdMap) {
        this.fkIdMap = fkIdMap;
    }

    public void setIdNivel(int idNivel) {
        this.idNivel = idNivel;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }
}
