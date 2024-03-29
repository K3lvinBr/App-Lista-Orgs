package com.example.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.orgs.database.AppDatabase
import com.example.orgs.database.dao.ProdutoDao
import com.example.orgs.databinding.ActivityFormularioProdutoBinding
import com.example.orgs.extensions.tentaCarregarImagem
import com.example.orgs.model.Produto

import com.example.orgs.ui.dialog.FormularioImagemDialog
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }

    private var url: String? = null
    private var idProduto = 0L
    private val produtoDao: ProdutoDao by lazy {
        val db = AppDatabase.instance(this)
        db.produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar produto"
        configuraBotaoSalvar()
        binding.activityFormularioProdutoImagem.setOnClickListener {
            FormularioImagemDialog(this).mostra(url) { imagem ->
                url = imagem
                binding.activityFormularioProdutoImagem.tentaCarregarImagem(url)
            }
        }
        tentaCarregarProduto()
    }

    private fun tentaCarregarProduto() {
        idProduto = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
    }

    override fun onResume() {
        super.onResume()
        tentaBuscarProduto()
    }

    private fun tentaBuscarProduto() {
        produtoDao.buscarPorId(idProduto)?.let {
            title = "Alterar produto"
            preencheCampos(it)
        }
    }

    private fun preencheCampos(produto: Produto): Unit? {
        url = produto.imagem
        binding.activityFormularioProdutoImagem.tentaCarregarImagem(produto.imagem)
        binding.formularioProdutoTextinputlayoutNome.editText?.setText(produto.nome)
        binding.formularioProdutoTextinputlayoutDescricao.editText?.setText(produto.descricao)
        return binding.formularioProdutoTextinputlayoutValor.editText?.setText(produto.valor.toPlainString())
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityFormularioProdutoBotaoSalvar

        botaoSalvar.setOnClickListener {
            val produtoNovo = criaProduto()
//            if(idProduto > 0) {
//                produtoDao.altera(produtoNovo)
//            }
//            else {
//                produtoDao.salva(produtoNovo)
//            }
            produtoDao.salva(produtoNovo)
            finish()
        }
    }

    private fun criaProduto(): Produto {
        val campoNome = binding.activityFormularioProdutoNome
        val nome = campoNome.text.toString()
        val campoDescricao = binding.activityFormularioProdutoDescricao
        val descricao = campoDescricao.text.toString()
        val campoValor = binding.activityFormularioProdutoValor
        val valorEmTexto = campoValor.text.toString()
        val valor = if (valorEmTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorEmTexto)
        }

        return Produto(
            id = idProduto,
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url
        )
    }
}