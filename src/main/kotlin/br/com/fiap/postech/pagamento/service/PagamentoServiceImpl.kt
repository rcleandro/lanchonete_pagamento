package br.com.fiap.postech.pagamento.service

import br.com.fiap.postech.pagamento.dto.PagamentoDto
import br.com.fiap.postech.pagamento.http.PedidoClient
import br.com.fiap.postech.pagamento.model.Pagamento
import br.com.fiap.postech.pagamento.model.Status
import br.com.fiap.postech.pagamento.repository.PagamentoRepository
import jakarta.persistence.EntityNotFoundException
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PagamentoServiceImpl: PagamentoService {
    @Autowired
    private val repository: PagamentoRepository? = null

    @Autowired
    private val modelMapper: ModelMapper? = null

    @Autowired
    private val pedido: PedidoClient? = null


    override fun obterTodos(paginacao: Pageable?): Page<PagamentoDto>? {
        return paginacao?.let {
            repository
                ?.findAll(it)
                ?.map { p -> modelMapper!!.map(p, PagamentoDto::class.java) }
        }
    }

    override fun obterPorId(id: Long?): PagamentoDto {
        val pagamento: Pagamento? = id?.let {
            repository?.findById(it)
                ?.orElseThrow { EntityNotFoundException() }
        }

        return modelMapper?.let { mapper ->
            pagamento?.let { mapper.map(it, PagamentoDto::class.java) }
                ?: throw EntityNotFoundException("Pagamento não encontrado para o ID: $id")
        } ?: throw EntityNotFoundException("modelMapper não deve ser nulo aqui")
    }

    override fun criarPagamento(dto: PagamentoDto?): PagamentoDto {
        val pagamento: Pagamento = modelMapper!!.map(dto, Pagamento::class.java)
        repository?.save(pagamento)

        return modelMapper.map(pagamento, PagamentoDto::class.java)
    }

    override fun atualizarPagamento(id: Long, dto: PagamentoDto?): PagamentoDto {
        var pagamento: Pagamento = modelMapper!!.map(dto, Pagamento::class.java)
        pagamento.id = id
        repository?.save(pagamento)?.let { pagamento = it }
        return modelMapper.map(pagamento, PagamentoDto::class.java)
    }

    override fun excluirPagamento(id: Long?) {
        id?.let { repository?.deleteById(it) }
    }

    override fun confirmarPagamento(id: Long?) {
        val pagamento = id?.let { repository?.findById(it) }

        if (pagamento?.isPresent != true) {
            throw EntityNotFoundException()
        }

        pagamento.get().status = Status.CONFIRMADO
        repository?.save(pagamento.get())
        pedido?.atualizaPagamento(pagamento.get().pedidoId)
    }


    override fun alteraStatus(id: Long?) {
        val pagamento = id?.let { repository?.findById(it) }

        if (pagamento?.isPresent != true) {
            throw EntityNotFoundException()
        }

        pagamento.get().status = Status.CONFIRMADO_SEM_INTEGRACAO
        repository?.save(pagamento.get())
    }
}
