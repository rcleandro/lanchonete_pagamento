package br.com.fiap.postech.pagamento.dto

import br.com.fiap.postech.pagamento.model.Status
import lombok.Getter
import lombok.Setter
import java.math.BigDecimal

@Getter
@Setter
data class PagamentoDto(
    val id: Long,
    val valor: BigDecimal,
    val nome: String,
    val numero: String,
    val expiracao: String,
    val codigo: String,
    val status: Status,
    val formaDePagamentoId: Long,
    val pedidoId: Long,
)
