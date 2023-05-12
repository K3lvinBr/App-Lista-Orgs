package com.example.orgs.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity
data class Produto (
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val nome: String,
    val descricao: String,
    val valor: BigDecimal,
    val imagem: String? = null
) : java.io.Serializable

