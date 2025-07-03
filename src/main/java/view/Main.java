package view;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.ProdutoController;
import dao.ConnectionFactory;
import dao.ProdutoDAO;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        ProdutoDAO dao = new ProdutoDAO(ConnectionFactory.getConnection());
        ProdutoController controller = new ProdutoController(dao);
        new ProdutoView(controller);
    }
}

