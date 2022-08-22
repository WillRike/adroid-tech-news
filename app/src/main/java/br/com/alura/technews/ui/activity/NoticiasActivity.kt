package br.com.alura.technews.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.alura.technews.R
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.ui.fragment.ListaNoticiasFragment
import br.com.alura.technews.ui.fragment.VisualizaNoticiasFragment

private const val TITULO_APPBAR = "Notícias"
private val TAG_FRAGMENT_LISTA_NOTICIAS = "lista-noticias"

class NoticiasActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticias)
        title = TITULO_APPBAR
        val transacao = supportFragmentManager.beginTransaction()

        transacao.add(
            R.id.activity_noticias_container,
            ListaNoticiasFragment()
        )
        transacao.commit()
    }


    private fun abreFormularioModoCriacao() {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        startActivity(intent)
    }

    private fun abreVisualizadorNoticia(noticia: Noticia) {
        val transacao = supportFragmentManager.beginTransaction()
        val fragment = VisualizaNoticiasFragment()
        val dados = Bundle()
        dados.putLong(NOTICIA_ID_CHAVE, noticia.id)
        fragment.arguments = dados
        transacao.replace(R.id.activity_noticias_container, fragment)
        transacao.commit()
    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
        if (fragment is ListaNoticiasFragment) {
            fragment.quandoNoticiaSeleciona = {
                abreVisualizadorNoticia(it)
            }
            fragment.quandoFabSalvaNoticiaClicado = {
                abreFormularioModoCriacao()
            }
        }
        if (fragment is VisualizaNoticiasFragment) {
            fragment.quandoFinalizaTela = { finish() }
            fragment.quandoSelecionaMenuEdicao =
                { noticiaSelecionada -> abreFormularioEdicao(noticiaSelecionada) }
        }
    }

    private fun abreFormularioEdicao(noticia: Noticia) {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        intent.putExtra(NOTICIA_ID_CHAVE, noticia.id)
        startActivity(intent)
    }

}
