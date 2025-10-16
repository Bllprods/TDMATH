package etec.sp.gov.br.com.example.tdmath.model;

public class Mapa {
    private int idMapa;
    private String nome;
    private String imgUrlMap;

    public Mapa(int idMapa, String nome, String imgUrlMap) {
        this.idMapa = idMapa;
        this.nome = nome;
        this.imgUrlMap = imgUrlMap;
    }

    public int getIdMapa() { return idMapa; }
    public String getNome() { return nome; }
    public String getImgUrlMap() { return imgUrlMap; }
}
