package dao;

import model.Produto;
import java.sql.*;
import java.util.*;

public class ProdutoDAO {
    private Connection conexao;

    public ProdutoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public List<Produto> listarTodos() throws SQLException {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getFloat("preco"));
                p.setEstoque(rs.getInt("estoque"));
                p.setDescricao(rs.getString("descricao"));
                lista.add(p);
            }
        }
        return lista;
    }

    public void salvar(Produto p) throws SQLException {
        String sql = "INSERT INTO produto (nome, preco, estoque, descricao) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.setFloat(2, p.getPreco());
            stmt.setInt(3, p.getEstoque());
            stmt.setString(4, p.getDescricao());
            stmt.executeUpdate();
        }
    }
}

