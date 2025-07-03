package model;

import javax.swing.ImageIcon;

public class Produto {

    private int id;
    private String nome;
    private float preco;
    private int estoque;
    private String descricao;
    private ImageIcon imagens;

    public ImageIcon getImagem() { return imagens; }
    public void setImagem(ImageIcon imagens) { this.imagens = imagens; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public float getPreco() { return preco; }
    public void setPreco(float preco) { this.preco = preco; }

    public int getEstoque() { return estoque; }
    public void setEstoque(int estoque) { this.estoque = estoque; }

    public String getDescricao() { return descricao != null ? descricao : ""; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

}