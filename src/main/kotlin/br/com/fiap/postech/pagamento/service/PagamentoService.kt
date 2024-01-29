package br.com.fiap.postech.pagamento.service

import br.com.fiap.postech.pagamento.dto.PagamentoDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PagamentoService {

    fun obterTodos(paginacao: Pageable?): Page<PagamentoDto>?

    fun obterPorId(id: Long?): PagamentoDto

    fun criarPagamento(dto: PagamentoDto?): PagamentoDto

    fun atualizarPagamento(id: Long, dto: PagamentoDto?): PagamentoDto

    fun excluirPagamento(id: Long?)

    fun confirmarPagamento(id: Long?)

    fun alteraStatus(id: Long?)
}