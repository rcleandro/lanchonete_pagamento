package br.com.fiap.postech.pagamento.http

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient("pedidos-ms")
interface PedidoClient {
    @RequestMapping(method = [RequestMethod.PUT], value = ["/pedidos/{id}/pago"])
    fun atualizaPagamento(@PathVariable id: Long?)
}
