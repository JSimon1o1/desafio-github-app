package br.com.igorbag.githubsearch.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.igorbag.githubsearch.R
import br.com.igorbag.githubsearch.domain.Repository

class RepositoryAdapter(private val repositories: List<Repository>) :
    RecyclerView.Adapter<RepositoryAdapter.ViewHolder<Any?>>() {

    private var btnShareListener: () -> Unit
    var repoItemListener: () -> Unit
    var carItemLister: (Repository) -> Unit = {}
    var btnShareLister: (Repository) -> Unit = {}

    // Cria uma nova view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<Any?> {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.repository_item, parent, false)
        return ViewHolder(view)
    }

    // Pega o conteudo da view e troca pela informacao de item de uma lista
    override fun onBindViewHolder(holder: ViewHolder<Any?>, position: Int) {
        //@TODO 8 -  Realizar o bind do viewHolder
        //Exemplo de Bind
        //  holder.preco.text = repositories[position].atributo

        // Exemplo de click no item
        //holder.itemView.setOnClickListener {
        // carItemLister(repositores[position])
        //}

        // Exemplo de click no btn Share
        //holder.favorito.setOnClickListener {
        //    btnShareLister(repositores[position])
        //}
    }

    // Pega a quantidade de repositorios da lista
    //@TODO 9 - realizar a contagem da lista
    override fun getItemCount(): Int = 0

    class ViewHolder<Intent>(view: View) : RecyclerView.ViewHolder(view) {
        //@TODO 10 - Implementar o ViewHolder para os repositorios
        //Exemplo:
        //val atributo: TextView

        //init {
        //    view.apply {
        //        atributo = findViewById(R.id.item_view)
        //    }


        // Metodo responsavel por realizar a configuracao do adapter
        fun setupAdapter(list: List<Repository>) {
            /*
                Implementar a configuracao do Adapter , construir o adapter e instancia-lo
                passando a listagem dos repositorios
             */
            val repoAdapter = RepositoryAdapter(list)
            val listaRepositories
            listaRepositories.adapter = repoAdapter
            listaRepositories.layoutManager = LinearLayoutManager(this)

            repoAdapter.repoItemListener = {
                openBrowser(it.htmlUrl)
            }

            repoAdapter.btnShareListener = {
                shareRepositoryLink(it.htmlUrl)
            }
        }

        // Metodo responsavel por compartilhar o link do repositorio selecionado
        // Colocar esse metodo no click do share item do adapter
        fun shareRepositoryLink(urlRepository: String) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, urlRepository)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        // Metodo responsavel por abrir o browser com o link informado do repositorio

        // Colocar esse metodo no click item do adapter
        fun openBrowser(urlRepository: String) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(urlRepository)
                )
            )

        }

        private fun startActivity(intent: Any) {
            TODO("Not yet implemented")
        }

        fun saveSharedPref(nome: String) {
            val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
            with(sharedPref.edit()) {
                putString(getString(R.string.nome_github), nome)
                apply()
            }
        }
        fun getSharedPref(): String? {
            val sharedPref = getPreferences(Context.MODE_PRIVATE)
            return sharedPref.getString(getString(R.string.nome_github), "")
        }

    }
}

class LinearLayoutManager(viewHolder: RepositoryAdapter.ViewHolder<Any?>) {

}


