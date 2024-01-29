package br.com.fiap.postech.pagamento.repository

import br.com.fiap.postech.pagamento.model.Pagamento
import br.com.fiap.postech.pagamento.model.Status
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.math.BigDecimal
import java.util.*
import kotlin.random.Random

class PagamentoRepositoryTest {

    @Mock
    private lateinit var pagamentoRepository: PagamentoRepository
    private lateinit var openMock: AutoCloseable

    @BeforeEach
    fun setup() {
        openMock = MockitoAnnotations.openMocks(this)
    }

    @AfterEach
    fun tearDown() = openMock.close()

    @Test
    fun devePermitirCadastrarPagamento() {
        // Arrange
        val pagamento = gerarPagamento()

        `when`(pagamentoRepository.save(any(Pagamento::class.java))).thenReturn(pagamento)

        // Act
        val pagamentoRegistrado = pagamentoRepository.save(pagamento)

        // Assert
        assertThat(pagamentoRegistrado)
            .isInstanceOf(Pagamento::class.java)
            .isNotNull
            .isEqualTo(pagamento)

        verify(pagamentoRepository, times(1))
            .save(any(Pagamento::class.java))
    }

    @Test
    fun devePermitirBuscarPagamentosPorId() {
        // Arrange
        val pagamento = gerarPagamento()

        `when`(pagamentoRepository.findById(pagamento.id)).thenReturn(Optional.of(pagamento))

        // Act
        val pagamentoRecebidoOpcional = pagamento.id.let { pagamentoRepository.findById(it) }

        // Assert
        assertThat(pagamentoRecebidoOpcional).isPresent

        pagamentoRecebidoOpcional.ifPresent { pagamentoRecebido ->
            assertThat(pagamentoRecebido)
                .isInstanceOf(Pagamento::class.java)
                .isEqualTo(pagamento)
        }

        pagamento.id.let {
            verify(pagamentoRepository, times(1))
                .findById(it)
        }
    }

    @Test
    fun devePermitirListarPagamentos() {
        // Arrange
        val pagamento1 = gerarPagamento()
        val pagamento2 = gerarPagamento()
        val listaPagamentos = listOf(pagamento1, pagamento2)

        `when`(pagamentoRepository.findAll()).thenReturn(listaPagamentos)

        // Act
        val pagamentosRecebidoOpcional = pagamentoRepository.findAll()

        // Assert
        assertThat(pagamentosRecebidoOpcional)
            .hasSize(2)
            .containsExactlyInAnyOrder(pagamento1, pagamento2)

        verify(pagamentoRepository, times(1))
            .findAll()
    }

    @Test
    fun devePermitirAtualizarPagamento() {
        // Arrange
        val pagamento = gerarPagamento()

        val novoNome = "Maria"
        pagamento.nome = novoNome

        `when`(pagamentoRepository.findById(pagamento.id)).thenReturn(Optional.of(pagamento))

        // Act
        val pagamentoRecebidoOpcional = pagamento.id.let { pagamentoRepository.findById(it) }

        // Assert
        assertThat(pagamentoRecebidoOpcional).isPresent

        pagamentoRecebidoOpcional.ifPresent { pagamentoRecebido ->
            assertThat(pagamentoRecebido)
                .isInstanceOf(Pagamento::class.java)
                .isEqualTo(pagamento)

            assertThat(pagamentoRecebido.nome)
                .isEqualTo(novoNome)
        }

        pagamento.id.let {
            verify(pagamentoRepository, times(1))
                .findById(it)
        }
    }

    @Test
    fun devePermitirDeletarPagamento() {
        // Arrange
        val pagamento = gerarPagamento()

        // Act
        pagamentoRepository.delete(pagamento)
        val pagamentoRecebidoOpcional = pagamento.id.let { pagamentoRepository.findById(it) }

        // Assert
        assertThat(pagamentoRecebidoOpcional).isEmpty

        verify(pagamentoRepository, times(1))
            .delete(pagamento)
    }

    private fun gerarPagamento(): Pagamento {
        return Pagamento(
            id = Random.nextLong(1000000),
            valor = BigDecimal(1.0),
            nome = "Jose",
            numero = "0123",
            expiracao = "0123",
            codigo = "123",
            status = Status.CRIADO,
            pedidoId = 1L,
            formaDePagamentoId = 1L
        )
    }
}