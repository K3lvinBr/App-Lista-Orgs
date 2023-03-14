package com.example.orgs.ui.activity

import com.example.orgs.shared.FormataParaMoedaBrasileira.Companion.formataParaMoedaBrasileira
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.orgs.databinding.ActivityDetalhesBinding
import com.example.orgs.extensions.tentaCarregarImagem
import com.example.orgs.model.Produto

class DetalhesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalhesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adicionaDetalhes()
    }

    private fun adicionaDetalhes() {
        val dados = intent.getSerializableExtra("CHAVE_DETALHES") as Produto

        val valorEmMoeda: String = formataParaMoedaBrasileira(dados.valor)

        binding.activityDetalhesImagem.tentaCarregarImagem(dados.imagem)
        binding.activityDetalhesValor.text = valorEmMoeda
        binding.activityDetalhesNome.text = dados.nome
        binding.activityDetalhesDescricao.text = dados.descricao
    }



}