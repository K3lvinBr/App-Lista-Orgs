package com.example.orgs.shared

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class FormataParaMoedaBrasileira {
    companion object {
        fun formataParaMoedaBrasileira(valor: BigDecimal): String {
            val formatador: NumberFormat = NumberFormat
                .getCurrencyInstance(Locale("pt", "br"))
            return formatador.format(valor)
        }
    }
}

