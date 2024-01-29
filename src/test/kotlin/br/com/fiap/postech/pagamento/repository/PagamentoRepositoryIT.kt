package br.com.fiap.postech.pagamento.repository

import br.com.fiap.postech.pagamento.model.Pagamento
import br.com.fiap.postech.pagamento.model.Status
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import kotlin.random.Random

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class PagamentoRepositoryIT {

    @Autowired
    private lateinit var pagamentoRepository: PagamentoRepository

    @Test
    fun devePermitirCriarTabela() {
        val totalDeRegistros = pagamentoRepository.count()
        Assertions.assertThat(totalDeRegistros).isNotNegative()
    }

    @Test
    fun devePermitirCadastrarPagamento() {
        // Arrange
        val pagamento = gerarPagamento()

        // Act
        val pagamentoRegistrado = pagamentoRepository.save(pagamento)

        // Assert
        Assertions.assertThat(pagamentoRegistrado)
            .isInstanceOf(Pagamento::class.java)
            .isNotNull
            .isEqualTo(pagamento)
    }

    @Test
    fun devePermitirBuscarPagamentosPorId() {
        // Arrange
        val pagamento = gerarPagamento()
        pagamentoRepository.save(pagamento)

        // Act
        val pagamentoRecebidoOpcional = pagamentoRepository.findById(pagamento.id)

        // Assert
        Assertions.assertThat(pagamentoRecebidoOpcional).isPresent

        pagamentoRecebidoOpcional.ifPresent { clienteRecebido ->
            Assertions.assertThat(clienteRecebido)
                .isInstanceOf(Pagamento::class.java)
                .isEqualTo(pagamento)
        }
    }

    @Test
    fun devePermitirListarPagamentos() {
        // Arrange
        val pagamento1 = gerarPagamento()
        pagamentoRepository.save(pagamento1)

        val pagamento2 = gerarPagamento()
        pagamentoRepository.save(pagamento2)

        // Act
        val pagamentosRecebidoOpcional = pagamentoRepository.findAll()

        // Assert
        Assertions.assertThat(pagamentosRecebidoOpcional)
            .hasSize(2)
            .containsExactlyInAnyOrder(pagamento1, pagamento2)
    }

    @Test
    fun devePermitirAtualizarPagamento() {
        // Arrange
        val pagamento = gerarPagamento()
        pagamentoRepository.save(pagamento)

        val novoNome = "Maria"
        pagamento.nome = novoNome
        pagamentoRepository.save(pagamento)

        // Act
        val pagamentoRecebidoOpcional = pagamentoRepository.findById(pagamento.id)

        // Assert
        Assertions.assertThat(pagamentoRecebidoOpcional).isPresent

        pagamentoRecebidoOpcional.ifPresent { clienteRecebido ->
            Assertions.assertThat(clienteRecebido)
                .isInstanceOf(Pagamento::class.java)
                .isEqualTo(pagamento)

            Assertions.assertThat(clienteRecebido.nome)
                .isEqualTo(novoNome)
        }
    }

    @Test
    fun devePermitirDeletarPagamento() {
        // Arrange
        val pagamento = gerarPagamento()
        pagamentoRepository.save(pagamento)

        // Act
        pagamentoRepository.delete(pagamento)
        val pagamentoOpcional = pagamentoRepository.findById(pagamento.id)

        // Assert
        Assertions.assertThat(pagamentoOpcional)
            .isEmpty
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