package controller;

import java.sql.SQLException;
import java.util.List;

import dao.ProdutoDAO;
import model.Produto;

public class ProdutoController {
    private ProdutoDAO dao;

    public ProdutoController(ProdutoDAO dao) {
        this.dao = dao;
    }

    public void salvarProduto(Produto produto) throws SQLException {
        if (produto.getId() == 0) {
            dao.salvar(produto);
        } else {
            dao.atualizar(produto);
        }
    }

    public void excluirProduto(int id) throws SQLException {
        dao.excluir(id);
    }

    public List<Produto> listarProdutos() throws SQLException {
        return dao.listarTodos();
    }
}

