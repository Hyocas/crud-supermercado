package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dao.ConnectionFactory;
import dao.ProdutoDAO;
import model.Produto;

@ExtendWith(MockitoExtension.class)
class ProdutoControllerTest {

    @Mock
    private ProdutoDAO produtoDAOMock;

    @InjectMocks
    private ProdutoController controller;

    private Produto novoProduto;
    private Produto produtoExistente;
    ProdutoDAO dao = new ProdutoDAO(ConnectionFactory.getConnection());

    @BeforeEach
    void setUp() {
        novoProduto = new Produto();
        novoProduto.setId(0);
        novoProduto.setNome("Suco");
        novoProduto.setPreco(10);
        novoProduto.setEstoque(100);

        produtoExistente = new Produto();
        produtoExistente.setId(5); // ID diferente de zero
        produtoExistente.setNome("Refrigerante");
        produtoExistente.setPreco(7);
        produtoExistente.setEstoque(50);
    }

    @Test
    void testSalvarProdutoNovoChamaSalvarDAO() throws SQLException {
        Produto produto = new Produto();
        produto.setId(0); // Produto novo
        controller.salvarProduto(produto);
        verify(produtoDAOMock).salvar(produto);
        verify(produtoDAOMock, never()).atualizar(any());
    }

    @Test
    void testSalvarProdutoExistenteChamaAtualizarDAO() throws SQLException {
        Produto produto = new Produto();
        produto.setId(10); // Produto já existente
        controller.salvarProduto(produto);
        verify(produtoDAOMock).atualizar(produto);
        verify(produtoDAOMock, never()).salvar(any());
    }

    @Test
    void testExcluirProdutoChamaExcluirDAO() throws SQLException {
        int id = 5;
        controller.excluirProduto(id);
        verify(produtoDAOMock).excluir(id);
    }

    @Test
    void testListarProdutosRetornaListaDoDAO() throws SQLException {
        List<Produto> mockLista = new ArrayList<>();
        Produto produto = new Produto();
        produto.setNome("Café");
        mockLista.add(produto);

        when(produtoDAOMock.listarTodos()).thenReturn(mockLista);

        List<Produto> resultado = controller.listarProdutos();
        assertEquals(1, resultado.size());
        assertEquals("Café", resultado.get(0).getNome());
        verify(produtoDAOMock).listarTodos();
    }

    @Test
    void testSalvarProdutoNoBanco() throws SQLException {
        Produto produto = new Produto();
        produto.setNome("Teste no Banco");
        produto.setPreco(5.0f);
        produto.setEstoque(10);

        dao.salvar(produto);

        // Verifica se o produto aparece na lista
        boolean encontrado = dao.listarTodos().stream()
            .anyMatch(p -> p.getNome().equals("Teste no Banco") && p.getPreco() == 5.0f);

        assertTrue(encontrado, "Produto deveria ter sido salvo no banco de dados");
    }

}
