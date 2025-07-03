package dao;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import model.Produto;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
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

                byte[] imagemBytes = rs.getBytes("imagem");
                if (imagemBytes != null) {
                    ImageIcon imagem = new ImageIcon(imagemBytes);
                    p.setImagem(imagem);
                }

                lista.add(p);
            }
        }

        return lista;
    }

    public void salvar(Produto p) throws SQLException {
        String sql = "INSERT INTO produto (nome, preco, estoque, descricao, imagem) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.setFloat(2, p.getPreco());
            stmt.setInt(3, p.getEstoque());
            stmt.setString(4, p.getDescricao());

            if (p.getImagem() != null) {
                BufferedImage bufferedImage = new BufferedImage(
                    p.getImagem().getIconWidth(),
                    p.getImagem().getIconHeight(),
                    BufferedImage.TYPE_INT_RGB
                );
                Graphics2D g2d = bufferedImage.createGraphics();
                g2d.drawImage(p.getImagem().getImage(), 0, 0, null);
                g2d.dispose();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "jpg", baos);
                byte[] imagemBytes = baos.toByteArray();

                stmt.setBinaryStream(5, new ByteArrayInputStream(imagemBytes), imagemBytes.length);
            } else {
                stmt.setNull(5, Types.BLOB);
            }

            stmt.executeUpdate();
        } catch (IOException e) {
            throw new SQLException("Erro ao salvar imagem: " + e.getMessage());
        }
    }

    public void atualizar(Produto p) throws SQLException {
        String sql = "UPDATE produto SET nome = ?, preco = ?, estoque = ?, descricao = ?, imagem = ? WHERE id = ?";
    
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.setFloat(2, p.getPreco());
            stmt.setInt(3, p.getEstoque());
            stmt.setString(4, p.getDescricao());
    
            if (p.getImagem() != null) {
                BufferedImage bufferedImage = new BufferedImage(
                    p.getImagem().getIconWidth(),
                    p.getImagem().getIconHeight(),
                    BufferedImage.TYPE_INT_RGB
                );
                Graphics2D g2d = bufferedImage.createGraphics();
                g2d.drawImage(p.getImagem().getImage(), 0, 0, null);
                g2d.dispose();
    
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "jpg", baos);
                byte[] imagemBytes = baos.toByteArray();
    
                stmt.setBinaryStream(5, new ByteArrayInputStream(imagemBytes), imagemBytes.length);
            } else {
                stmt.setNull(5, Types.BLOB);
            }
    
            stmt.setInt(6, p.getId());
    
            stmt.executeUpdate();
        } catch (IOException e) {
            throw new SQLException("Erro ao atualizar imagem: " + e.getMessage());
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM produto WHERE id = ?";
    
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    
}