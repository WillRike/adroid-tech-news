package br.com.alura.technews.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.alura.technews.R
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.ui.activity.extensions.transacaoFragment
import br.com.alura.technews.ui.fragment.ListaNoticiasFragment
import br.com.alura.technews.ui.fragment.VisualizaNoticiasFragment


private val TAG_FRAGMENT_LISTA_NOTICIAS = "lista-noticias"

class NoticiasActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticias)
        if (savedInstanceState == null) {
            abreListaNoticias()
        }

    }

    private fun abreListaNoticias() {
        transacaoFragment {
            replace(R.id.activity_noticias_container, ListaNoticiasFragment())
        }
    }


    private fun abreFormularioModoCriacao() {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        startActivity(intent)
    }

    private fun abreVisualizadorNoticia(noticia: Noticia) {
        val fragment = VisualizaNoticiasFragment()
        val dados = Bundle()
        dados.putLong(NOTICIA_ID_CHAVE, noticia.id)
        fragment.arguments = dados
        transacaoFragment {
            addToBackStack(null)
            replace(R.id.activity_noticias_container, fragment)
        }
    }


    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
        when (fragment) {
            is ListaNoticiasFragment -> {
                configuraListaNoticias(fragment)
            }
            is VisualizaNoticiasFragment -> {
                configuraVisualizacaoNoticia(fragment)
            }
        }
    }

    private fun configuraVisualizacaoNoticia(fragment: VisualizaNoticiasFragment) {
        fragment.quandoFinalizaTela = { finish() }
        fragment.quandoSelecionaMenuEdicao =
            { noticiaSelecionada -> abreFormularioEdicao(noticiaSelecionada) }
    }

    private fun configuraListaNoticias(fragment: ListaNoticiasFragment) {
        fragment.quandoNoticiaSeleciona = {
            abreVisualizadorNoticia(it)
        }
        fragment.quandoFabSalvaNoticiaClicado = {
            abreFormularioModoCriacao()
        }
    }

    private fun abreFormularioEdicao(noticia: Noticia) {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        intent.putExtra(NOTICIA_ID_CHAVE, noticia.id)
        startActivity(intent)
    }

}
