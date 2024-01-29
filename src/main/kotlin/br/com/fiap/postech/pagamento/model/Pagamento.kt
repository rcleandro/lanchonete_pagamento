package br.com.fiap.postech.pagamento.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.math.BigDecimal

@Entity
@Table(name = "pagamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
data class Pagamento(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @NotNull
    @Positive
    var valor: BigDecimal,

    @NotBlank
    @Size(max = 100)
    var nome: String,

    @NotBlank
    @Size(max = 19)
    var numero: String,

    @NotBlank
    @Size(max = 7)
    var expiracao: String,

    @NotBlank
    @Size(min = 3, max = 3)
    var codigo: String,

    @NotNull
    @Enumerated(EnumType.STRING)
    var status: Status = Status.CRIADO,

    @NotNull
    var pedidoId: Long,

    @NotNull
    var formaDePagamentoId: Long
)
