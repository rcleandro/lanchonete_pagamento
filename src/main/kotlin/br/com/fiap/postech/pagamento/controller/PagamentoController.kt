package br.com.fiap.postech.pagamento.controller

import br.com.fiap.postech.pagamento.dto.PagamentoDto
import br.com.fiap.postech.pagamento.service.PagamentoServiceImpl
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/pagamentos")
class PagamentoController {
    @Autowired
    private val service: PagamentoServiceImpl? = null

    @GetMapping
    fun listar(@PageableDefault(size = 10) paginacao: Pageable?): Page<PagamentoDto>? {
        return service?.obterTodos(paginacao)
    }

    @GetMapping("/{id}")
    fun detalhar(@PathVariable @NotNull id: Long?): ResponseEntity<PagamentoDto> {
        val dto: PagamentoDto? = service?.obterPorId(id)

        return ResponseEntity.ok(dto)
    }


    @PostMapping
    fun cadastrar(
        @RequestBody @Valid dto: PagamentoDto?,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<PagamentoDto> {
        val pagamento: PagamentoDto? = service?.criarPagamento(dto)
        val endereco = uriBuilder.path("/pagamentos/{id}").buildAndExpand(pagamento?.id).toUri()

        return ResponseEntity.created(endereco).body(pagamento)
    }

    @PutMapping("/{id}")
    fun atualizar(
        @PathVariable @NotNull id: Long,
        @RequestBody @Valid dto: PagamentoDto?
    ): ResponseEntity<PagamentoDto> {
        val atualizado: PagamentoDto? = service?.atualizarPagamento(id, dto)
        return ResponseEntity.ok(atualizado)
    }

    @DeleteMapping("/{id}")
    fun remover(@PathVariable @NotNull id: Long?): ResponseEntity<PagamentoDto> {
        service?.excluirPagamento(id)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/{id}/confirmar")
    @CircuitBreaker(name = "atualizaPedido", fallbackMethod = "pagamentoAutorizadoComIntegracaoPendente")
    fun confirmarPagamento(@PathVariable @NotNull id: Long?) {
        service?.confirmarPagamento(id)
    }

    fun pagamentoAutorizadoComIntegracaoPendente(id: Long?, e: Exception?) {
        service?.alteraStatus(id)
    }
}
