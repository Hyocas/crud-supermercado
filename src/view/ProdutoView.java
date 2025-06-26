package view;

import model.Produto;
import dao.ProdutoDAO;
import util.ConnectionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class ProdutoView extends JFrame {
    private JTextField nomeField, precoField, estoqueField, descricaoField;
    private JTable tabela;
    private ProdutoDAO dao;

    public ProdutoView() {
        setTitle("Gerenciar Produtos");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Connection conexao = ConnectionFactory.getConnection();
        dao = new ProdutoDAO(conexao);

        // Formulário
        JPanel form = new JPanel(new GridLayout(5, 2));
        nomeField = new JTextField();
        precoField = new JTextField();
        estoqueField = new JTextField();
        descricaoField = new JTextField();
        JButton salvarBtn = new JButton("Salvar");

        form.add(new JLabel("Nome:"));
        form.add(nomeField);
        form.add(new JLabel("Preço:"));
        form.add(precoField);
        form.add(new JLabel("Estoque:"));
        form.add(estoqueField);
        form.add(new JLabel("Descrição:"));
        form.add(descricaoField);
        form.add(salvarBtn);

        add(form, BorderLayout.NORTH);

        // Tabela
        tabela = new JTable();
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        salvarBtn.addActionListener(e -> {
            Produto p = new Produto();
            p.setNome(nomeField.getText());
            p.setPreco(Float.parseFloat(precoField.getText()));
            p.setEstoque(Integer.parseInt(estoqueField.getText()));
            p.setDescricao(descricaoField.getText());

            try {
                dao.salvar(p);
                listarProdutos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
            }
        });

        listarProdutos();
        setVisible(true);
    }

    private void listarProdutos() {
        try {
            List<Produto> produtos = dao.listarTodos();
            DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Nome", "Preço", "Estoque", "Descrição"}, 0
            );

            for (Produto p : produtos) {
                model.addRow(new Object[]{
                    p.getId(), p.getNome(), p.getPreco(), p.getEstoque(), p.getDescricao()
                });
            }

            tabela.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar: " + e.getMessage());
        }
    }
}

