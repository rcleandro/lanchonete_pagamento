package br.com.fiap.postech.pagamento.model

enum class Status {
    CRIADO,
    CONFIRMADO,
    CONFIRMADO_SEM_INTEGRACAO,
    CANCELADO
}