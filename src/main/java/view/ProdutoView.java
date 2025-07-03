package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.ProdutoController;
import model.Produto;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import java.util.List;
import javax.imageio.ImageIO;

public class ProdutoView extends JFrame {
    private JTextField nomeField, precoField, estoqueField, descricaoField;
    private ProdutoController controller;
    private JLabel imagemLabel;
    private File imagemSelecionada;
    private JScrollPane painelScroll;
    private Produto produtoSelecionado = null;

    private final Color PRIMARY_DARK = new Color(40, 44, 52);
    private final Color SECONDARY_DARK = new Color(50, 54, 62);
    private final Color TEXT_LIGHT = new Color(200, 200, 200);
    private final Color TEXT_ACCENT = new Color(102, 217, 239);
    private final Color BUTTON_PRIMARY = new Color(97, 175, 239);
    private final Color BUTTON_SUCCESS = new Color(152, 195, 121);
    private final Color BUTTON_DANGER = new Color(224, 108, 117);
    private final Color BORDER_COLOR_DARK = new Color(70, 74, 82);

    public ProdutoView(ProdutoController controller) {
        this.controller = controller; 
        setTitle("Gerenciar Produtos");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(PRIMARY_DARK);


        JPanel topPanel = new JPanel(new BorderLayout(20, 20));
        topPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        topPanel.setBackground(PRIMARY_DARK);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(PRIMARY_DARK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setFont(labelFont);
        nomeLabel.setForeground(TEXT_LIGHT);
        formPanel.add(nomeLabel, gbc);
        gbc.gridx = 1;
        nomeField = new JTextField(25);
        nomeField.setFont(fieldFont);
        nomeField.setBackground(SECONDARY_DARK);
        nomeField.setForeground(TEXT_LIGHT);
        nomeField.setCaretColor(TEXT_LIGHT);
        nomeField.setBorder(BorderFactory.createLineBorder(BORDER_COLOR_DARK));
        formPanel.add(nomeField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        JLabel precoLabel = new JLabel("Preço:");
        precoLabel.setFont(labelFont);
        precoLabel.setForeground(TEXT_LIGHT);
        formPanel.add(precoLabel, gbc);
        gbc.gridx = 1;
        precoField = new JTextField(25);
        precoField.setFont(fieldFont);
        precoField.setBackground(SECONDARY_DARK);
        precoField.setForeground(TEXT_LIGHT);
        precoField.setCaretColor(TEXT_LIGHT);
        precoField.setBorder(BorderFactory.createLineBorder(BORDER_COLOR_DARK));
        formPanel.add(precoField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        JLabel estoqueLabel = new JLabel("Estoque:");
        estoqueLabel.setFont(labelFont);
        estoqueLabel.setForeground(TEXT_LIGHT);
        formPanel.add(estoqueLabel, gbc);
        gbc.gridx = 1;
        estoqueField = new JTextField(25);
        estoqueField.setFont(fieldFont);
        estoqueField.setBackground(SECONDARY_DARK);
        estoqueField.setForeground(TEXT_LIGHT);
        estoqueField.setCaretColor(TEXT_LIGHT);
        estoqueField.setBorder(BorderFactory.createLineBorder(BORDER_COLOR_DARK));
        formPanel.add(estoqueField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        JLabel descricaoLabel = new JLabel("Descrição:");
        descricaoLabel.setFont(labelFont);
        descricaoLabel.setForeground(TEXT_LIGHT);
        formPanel.add(descricaoLabel, gbc);
        gbc.gridx = 1;
        descricaoField = new JTextField(25);
        descricaoField.setFont(fieldFont);
        descricaoField.setBackground(SECONDARY_DARK);
        descricaoField.setForeground(TEXT_LIGHT);
        descricaoField.setCaretColor(TEXT_LIGHT);
        descricaoField.setBorder(BorderFactory.createLineBorder(BORDER_COLOR_DARK));
        formPanel.add(descricaoField, gbc);
        row++;

        JPanel imageSelectionPanel = new JPanel(new BorderLayout(10, 10));
        imageSelectionPanel.setBackground(PRIMARY_DARK);
        imageSelectionPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_COLOR_DARK), "Imagem do Produto",
                javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP,
                labelFont, TEXT_LIGHT));

        imagemLabel = new JLabel("Nenhuma imagem", SwingConstants.CENTER);
        imagemLabel.setPreferredSize(new Dimension(200, 200));
        imagemLabel.setBorder(BorderFactory.createLineBorder(BORDER_COLOR_DARK, 2));
        imagemLabel.setBackground(SECONDARY_DARK);
        imagemLabel.setOpaque(true);
        imagemLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        imagemLabel.setForeground(TEXT_LIGHT);

        JButton selecionarImagemBtn = new JButton("Selecionar Imagem");
        selecionarImagemBtn.setBackground(BUTTON_PRIMARY);
        selecionarImagemBtn.setForeground(Color.BLACK);
        selecionarImagemBtn.setFocusPainted(false);
        selecionarImagemBtn.setFont(labelFont);

        imageSelectionPanel.add(imagemLabel, BorderLayout.CENTER);
        imageSelectionPanel.add(selecionarImagemBtn, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(PRIMARY_DARK);
        JButton salvarBtn = new JButton("Salvar Produto");
        JButton limparBtn = new JButton("Limpar Formulário");

        salvarBtn.setBackground(BUTTON_SUCCESS);
        salvarBtn.setForeground(Color.BLACK);
        salvarBtn.setFocusPainted(false);
        salvarBtn.setFont(labelFont);

        limparBtn.setBackground(BUTTON_PRIMARY);
        limparBtn.setForeground(Color.BLACK);
        limparBtn.setFocusPainted(false);
        limparBtn.setFont(labelFont);

        buttonPanel.add(salvarBtn);
        buttonPanel.add(limparBtn);

        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(imageSelectionPanel, BorderLayout.EAST);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        selecionarImagemBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Imagens (*.jpg, *.png, *.jpeg)", "jpg", "jpeg", "png"));
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                imagemSelecionada = chooser.getSelectedFile();
                ImageIcon icon = new ImageIcon(imagemSelecionada.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(imagemLabel.getWidth(), imagemLabel.getHeight(), Image.SCALE_SMOOTH);
                imagemLabel.setIcon(new ImageIcon(img));
                imagemLabel.setText("");
            }
        });

        salvarBtn.addActionListener(e -> salvarProduto());
        limparBtn.addActionListener(e -> limparFormulario());

        listarProdutos();
        setVisible(true);
    }

    private void salvarProduto() {
        if (nomeField.getText().trim().isEmpty() || precoField.getText().trim().isEmpty() || estoqueField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome, Preço e Estoque são campos obrigatórios.", "Campos Faltando", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (produtoSelecionado == null) {
            produtoSelecionado = new Produto();
        }

        produtoSelecionado.setNome(nomeField.getText().trim());
        produtoSelecionado.setDescricao(descricaoField.getText().trim());

        try {
            produtoSelecionado.setPreco(Float.parseFloat(precoField.getText().trim()));
            produtoSelecionado.setEstoque(Integer.parseInt(estoqueField.getText().trim()));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preço ou estoque inválido. Por favor, insira valores numéricos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (imagemSelecionada != null) {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                BufferedImage img = ImageIO.read(imagemSelecionada);
                String format = getFileExtension(imagemSelecionada.getName()).toLowerCase();
                if (format.equals("jpg")) format = "jpeg";
                if (!ImageIO.write(img, format, baos)) {
                    JOptionPane.showMessageDialog(this, "Formato de imagem não suportado para salvar: " + format, "Erro de Imagem", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                produtoSelecionado.setImagem(new ImageIcon(baos.toByteArray()));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar ou processar imagem: " + ex.getMessage(), "Erro de Imagem", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else if (produtoSelecionado.getId() == 0) {
            produtoSelecionado.setImagem(null);
        }

        try {
            if (produtoSelecionado.getId() == 0) {
                controller.salvarProduto(produtoSelecionado);
                JOptionPane.showMessageDialog(this, "Produto salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                controller.salvarProduto(produtoSelecionado);
                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
            limparFormulario();
            listarProdutos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar/atualizar produto: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            return "";
        }
        return fileName.substring(dotIndex + 1);
    }

    private void listarProdutos() {
        try {
            List<Produto> produtos = controller.listarProdutos();
            JPanel productsContainerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
            productsContainerPanel.setBackground(PRIMARY_DARK);
            productsContainerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

            if (produtos.isEmpty()) {
                productsContainerPanel.setLayout(new GridBagLayout());
                JLabel noProductsLabel = new JLabel("Nenhum produto cadastrado.");
                noProductsLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
                noProductsLabel.setForeground(TEXT_LIGHT);
                productsContainerPanel.add(noProductsLabel);
            } else {
                for (Produto p : produtos) {
                    JPanel card = new JPanel();
                    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
                    card.setBackground(SECONDARY_DARK);
                    card.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(BORDER_COLOR_DARK, 1),
                            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    card.setPreferredSize(new Dimension(220, 300));
                    card.setMaximumSize(new Dimension(220, 300));

                    JLabel imagem = new JLabel("", SwingConstants.CENTER);
                    imagem.setAlignmentX(Component.CENTER_ALIGNMENT);
                    imagem.setPreferredSize(new Dimension(150, 150));
                    imagem.setMaximumSize(new Dimension(150, 150));
                    imagem.setBorder(BorderFactory.createLineBorder(BORDER_COLOR_DARK));
                    imagem.setBackground(PRIMARY_DARK);
                    imagem.setOpaque(true);

                    if (p.getImagem() != null) {
                        Image img = p.getImagem().getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                        imagem.setIcon(new ImageIcon(img));
                        imagem.setText("");
                    } else {
                        imagem.setText("Sem imagem");
                        imagem.setFont(new Font("Segoe UI", Font.ITALIC, 12));
                        imagem.setForeground(TEXT_LIGHT);
                    }

                    JLabel nome = new JLabel("<html><b>" + p.getNome() + "</b></html>", SwingConstants.CENTER);
                    nome.setAlignmentX(Component.CENTER_ALIGNMENT);
                    nome.setFont(new Font("Segoe UI", Font.BOLD, 16));
                    nome.setForeground(TEXT_LIGHT);
                    nome.setBorder(new EmptyBorder(5, 0, 0, 0));

                    JLabel preco = new JLabel(String.format("R$ %.2f", p.getPreco()), SwingConstants.CENTER);
                    preco.setAlignmentX(Component.CENTER_ALIGNMENT);
                    preco.setFont(new Font("Segoe UI", Font.BOLD, 15));
                    preco.setForeground(TEXT_ACCENT);
                    preco.setBorder(new EmptyBorder(0, 0, 5, 0));

                    JLabel estoque = new JLabel("Estoque: " + p.getEstoque(), SwingConstants.CENTER);
                    estoque.setAlignmentX(Component.CENTER_ALIGNMENT);
                    estoque.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    estoque.setForeground(TEXT_LIGHT);
                    estoque.setBorder(new EmptyBorder(0, 0, 5, 0));

                    JLabel descricao = new JLabel("<html><center>" + p.getDescricao() + "</center></html>", SwingConstants.CENTER);
                    descricao.setAlignmentX(Component.CENTER_ALIGNMENT);
                    descricao.setFont(new Font("Segoe UI", Font.ITALIC, 11));
                    descricao.setForeground(TEXT_LIGHT);
                    descricao.setBorder(new EmptyBorder(0, 0, 10, 0));

                    JButton editar = new JButton("Editar");
                    editar.setAlignmentX(Component.CENTER_ALIGNMENT);
                    editar.setBackground(BUTTON_PRIMARY);
                    editar.setForeground(Color.BLACK);
                    editar.setFocusPainted(false);
                    editar.setFont(new Font("Segoe UI", Font.PLAIN, 12));

                    JButton excluir = new JButton("Excluir");
                    excluir.setAlignmentX(Component.CENTER_ALIGNMENT);
                    excluir.setBackground(BUTTON_DANGER);
                    excluir.setForeground(Color.BLACK);
                    excluir.setFocusPainted(false);
                    excluir.setFont(new Font("Segoe UI", Font.PLAIN, 12));

                    JPanel buttonActionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
                    buttonActionsPanel.setBackground(SECONDARY_DARK);
                    buttonActionsPanel.add(editar);
                    buttonActionsPanel.add(excluir);
                    buttonActionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

                    editar.addActionListener(e -> preencherFormulario(p));
                    excluir.addActionListener(e -> {
                        int opcao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o produto '" + p.getNome() + "'?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (opcao == JOptionPane.YES_OPTION) {
                            try {
                                controller.excluirProduto(p.getId());
                                JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                                listarProdutos();
                                limparFormulario();
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(this, "Erro ao excluir produto: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });

                    card.add(imagem);
                    card.add(Box.createVerticalStrut(5));
                    card.add(nome);
                    card.add(preco);
                    card.add(estoque);
                    if (!p.getDescricao().isEmpty()) {
                        card.add(descricao);
                    }
                    card.add(Box.createVerticalGlue());
                    card.add(buttonActionsPanel);

                    productsContainerPanel.add(card);
                }
            }

            if (painelScroll != null) {
                remove(painelScroll);
            }

            painelScroll = new JScrollPane(productsContainerPanel);
            painelScroll.setBorder(BorderFactory.createEmptyBorder());
            painelScroll.getVerticalScrollBar().setUnitIncrement(16);
            painelScroll.getViewport().setBackground(PRIMARY_DARK);
            add(painelScroll, BorderLayout.CENTER);
            revalidate();
            repaint();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar produtos: " + e.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherFormulario(Produto p) {
        produtoSelecionado = p;
        nomeField.setText(p.getNome());
        precoField.setText(String.valueOf(p.getPreco()));
        estoqueField.setText(String.valueOf(p.getEstoque()));
        descricaoField.setText(p.getDescricao());
        imagemSelecionada = null;

        if (p.getImagem() != null) {
            Image img = p.getImagem().getImage().getScaledInstance(imagemLabel.getWidth(), imagemLabel.getHeight(), Image.SCALE_SMOOTH);
            imagemLabel.setIcon(new ImageIcon(img));
            imagemLabel.setText("");
        } else {
            imagemLabel.setIcon(null);
            imagemLabel.setText("Nenhuma imagem");
            imagemLabel.setForeground(TEXT_LIGHT);
        }
    }

    private void limparFormulario() {
        produtoSelecionado = null;
        nomeField.setText("");
        precoField.setText("");
        estoqueField.setText("");
        descricaoField.setText("");
        imagemSelecionada = null;
        imagemLabel.setText("Nenhuma imagem");
        imagemLabel.setIcon(null);
        imagemLabel.setForeground(TEXT_LIGHT);}
}