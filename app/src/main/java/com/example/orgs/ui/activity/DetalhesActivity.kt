package com.example.orgs.ui.activity

import android.content.Intent
import android.nfc.Tag
import com.example.orgs.shared.FormataParaMoedaBrasileira.Companion.formataParaMoedaBrasileira
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.orgs.R
import com.example.orgs.database.AppDatabase
import com.example.orgs.databinding.ActivityDetalhesBinding
import com.example.orgs.extensions.tentaCarregarImagem
import com.example.orgs.model.Produto

class DetalhesActivity : AppCompatActivity() {
    private var produtoId: Long = 0L
    private var produto: Produto? = null
    private lateinit var binding: ActivityDetalhesBinding
    val produtoDao by lazy {
        AppDatabase.instance(this).produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tentaCarregarProduto()
    }

    override fun onResume() {
        super.onResume()
        buscaProduto()
    }

    private fun buscaProduto() {
        produto = produtoDao.buscarPorId(produtoId)
        produto?.let {
            adicionaDetalhes(it)
        } ?: finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (produto != null) {
            when(item.itemId) {
                R.id.menu_detalhes_produto_editar -> {
                    Intent(this, FormularioProdutoActivity::class.java).apply {
                        putExtra(CHAVE_PRODUTO_ID, produtoId)
                        startActivity(this)
                    }
                }

                R.id.menu_detalhes_produto_remover -> {
                    produto?.let {
                        produtoDao.remove(it)
                    }
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarProduto(){
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
    }

    private fun adicionaDetalhes(dados: Produto) {
        val valorEmMoeda: String = formataParaMoedaBrasileira(dados.valor)

        binding.activityDetalhesImagem.tentaCarregarImagem(dados.imagem)
        binding.activityDetalhesValor.text = valorEmMoeda
        binding.activityDetalhesNome.text = dados.nome
        binding.activityDetalhesDescricao.text = dados.descricao
    }



}