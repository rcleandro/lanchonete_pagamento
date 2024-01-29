package br.com.fiap.postech.pagamento.repository

import br.com.fiap.postech.pagamento.model.Pagamento
import org.springframework.data.jpa.repository.JpaRepository

interface PagamentoRepository : JpaRepository<Pagamento, Long>