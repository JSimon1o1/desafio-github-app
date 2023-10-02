package br.com.igorbag.githubsearch.ui

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.igorbag.githubsearch.R
import br.com.igorbag.githubsearch.data.GitHubService
import br.com.igorbag.githubsearch.domain.Repository
import br.com.igorbag.githubsearch.ui.adapter.RepositoryAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var nomeUsuario: EditText
    lateinit var btnConfirmar: Button
    lateinit var listaRepositories: RecyclerView
    lateinit var githubApi: GitHubService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        showUserName()
        setupRetrofit()
        getAllReposByUserName()
    }


        //@TODO 1 - Recuperar os Id's da tela para a Activity com o findViewById
    }

    // Metodo responsavel por realizar o setup da view e recuperar os Ids do layout
    fun setupView() {
        //Recuperar os Id's da tela para a Activity com o findViewById
        var nomeUsuario = findViewById(R.id.et_nome_usuario)
        var btnConfirmar = findViewById(R.id.btn_confirmar)
        var listaRepositories = findViewById(R.id.rv_lista_repositories)
    }

//fun findViewById(etNomeUsuario: Int): Any {
//    TODO("Not yet implemented")
//}

//metodo responsavel por configurar os listeners click da tela
    private fun setupListeners() {
    fun getAllReposByUserName() {
        TODO("Not yet implemented")
    }

    fun saveUserLocal() {
        TODO("Not yet implemented")
    }

    //@TODO 2 - colocar a acao de click do botao confirmar
        private fun setupListeners() {
        val btnConfirmar // *
        val btnConfirmar = btnConfirmar.setOnClickListener {
                saveUserLocal()
                getAllReposByUserName()
        }

}


    fun saveSharedPref(userlogin: Any) {
        TODO("Not yet implemented")
    }

    // salvar o usuario preenchido no EditText utilizando uma SharedPreferences
    private fun saveUserLocal() {
        //@TODO 3 - Persistir o usuario preenchido na editText com a SharedPref no listener do botao salvar
        val nomeUsuario
        if(nomeUsuario.text != null) {
            var userlogin = nomeUsuario.text.toString()
            saveSharedPref(userlogin)
                Log.i(TAG, "$userlogin gravado com sucesso!")
            }else{
                Log.i(TAG, "Preencha corretamente o Nome do usuário.")
            }
        }

    }

    private fun showUserName() {
        //@TODO 4- depois de persistir o usuario exibir sempre as informacoes no EditText  se a sharedpref possuir algum valor, exibir no proprio editText o valor salvo
        val nomeUsuario
        nomeUsuario.setText(getSharedPref())
    }

    //Metodo responsavel por fazer a configuracao base do Retrofit
    fun setupRetrofit() {
        /*
           @TODO 5 -  realizar a Configuracao base do retrofit
           Documentacao oficial do retrofit - https://square.github.io/retrofit/
           URL_BASE da API do  GitHub= https://api.github.com/
           lembre-se de utilizar o GsonConverterFactory mostrado no curso
        */
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var gitHubAPI = retrofit.create(GitHubService::class.java)

        Log.i(TAG, "Retrofit configurado com sucesso! ")
    }

}

class LinearLayoutManager(any: Any) {

}

//Metodo responsavel por buscar todos os repositorios do usuario fornecido
    fun getAllReposByUserName() {
        // TODO 6 - realizar a implementacao do callback do retrofit e chamar o metodo setupAdapter se retornar os dados com sucesso
        fun getAllReposByUserName() {
            val gitHubAPI
            gitHubAPI.getAllRepositoriesByUser(getSharedPref()!!).enqueue(object :
                Callback<List<Repository>> {
                override fun onResponse(call: Call<List<Repository>>, response: Response<List<Repository>>) {
                    if(response.isSuccessful) {
                        var message = ""
                        val repoList = response.body()
                        repoList?.let {
                            setupAdapter(it)
                            repoList.forEach {
                                message += "${it.name} - ${it.htmlUrl}\n"
                            }
                            Log.d(TAG, message)
                        }
                    }else {
                        val applicationContext
                        Toast.makeText(applicationContext, R.string.app_error, Toast.LENGTH_LONG).show()
                    }
                }

                private fun setupAdapter(it: List<Repository>) {

                }

                override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                    val applicationContext
                    Toast.makeText(applicationContext, R.string.app_error, Toast.LENGTH_LONG).show()
                }

            }
    }

    // Metodo responsavel por realizar a configuracao do adapter
    fun setupAdapter(list: List<Repository>) {
        /*
            @TODO 7 - Implementar a configuracao do Adapter , construir o adapter e instancia-lo
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
    }


    // Metodo responsavel por compartilhar o link do repositorio selecionado
    // @Todo 11 - Colocar esse metodo no click do share item do adapter
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

    // @Todo 12 - Colocar esse metodo no click item do adapter
    fun openBrowser(urlRepository: String) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(urlRepository)
            )
        )

    }

}