package com.example.roomdatabaserecycleview

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.roomdatabaserecycleview.dao.PessoaDao
import com.example.roomdatabaserecycleview.database.AppDatabase
import com.example.roomdatabaserecycleview.models.Pessoa
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    var listaPessoas: ArrayList<Pessoa> = ArrayList();
    lateinit var appDatabase: AppDatabase;
    lateinit var pessoasAdapter: PessoaItemAdapter;
    lateinit var btnAdicionar: Button;
    lateinit var pessoaDao: PessoaDao;

    fun adicionaPessoa() {
        val edtNome = findViewById<EditText>(R.id.edt_nome);
        val edtCurso = findViewById<EditText>(R.id.edt_curso);
        val ednIdade = findViewById<EditText>(R.id.edn_idade);

        if (edtNome.text.isNullOrEmpty() || edtCurso.text.isNullOrEmpty() || ednIdade.text.isNullOrEmpty()) {
            Snackbar.make(findViewById(R.id.rv_lista_pessoas), "Preencha todos os campos", Snackbar.LENGTH_LONG).show();
            return;
        }

        try {
            val nome: String = edtNome.text.toString();
            val curso: String = edtCurso.text.toString();
            val idade: Int = ednIdade.text.toString().toInt();
            val pessoa = Pessoa(nome, curso, idade);

            pessoaDao.insertAll(pessoa);

            listaPessoas.add(pessoa);
            pessoasAdapter.notifyDataSetChanged();

            Snackbar.make(findViewById(R.id.rv_lista_pessoas), "Pessoa Adicionada", Snackbar.LENGTH_LONG).show();
        } catch (e: Exception) {
            Snackbar.make(findViewById(R.id.rv_lista_pessoas), "Erro ao Adicionar Pessoa", Snackbar.LENGTH_LONG).show();
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAdicionar = findViewById(R.id.btn_adicionar);

        appDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "main-database")
            .enableMultiInstanceInvalidation()
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build();

        val recyclerView = findViewById<RecyclerView>(R.id.rv_lista_pessoas);

        recyclerView.layoutManager = LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        pessoasAdapter = PessoaItemAdapter(listaPessoas);
        recyclerView.adapter = pessoasAdapter;

        pessoaDao = appDatabase.pessoaDao();

        val pessoasDoBd: List<Pessoa> = pessoaDao.listAll();
        for (pessoa in pessoasDoBd) {
            listaPessoas.add(pessoa);
        }

        pessoasAdapter.notifyDataSetChanged();


        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false;
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedPessoa: Pessoa = listaPessoas.get(viewHolder.adapterPosition);

                val sucesso = pessoaDao.delete(deletedPessoa);

                if (sucesso == 1) {
                    val position = viewHolder.adapterPosition;

                    listaPessoas.removeAt(viewHolder.adapterPosition);

                    pessoasAdapter.notifyItemRemoved(viewHolder.adapterPosition);

                    Snackbar.make(recyclerView, "Deletou " + deletedPessoa.nome, Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(recyclerView, "Desculpe, ocorreu um erro ao deletar - " + deletedPessoa.nome, Snackbar.LENGTH_LONG).show();
                }

//                    .setAction(
//                        "Desfazer",
//                        View.OnClickListener {
//                            // adding on click listener to our action of snack bar.
//                            // below line is to add our item to array list with a position.
//                            listaPessoas.add(position, deletedPessoa)
//
//                            // below line is to notify item is
//                            // added to our adapter class.
//                            pessoasAdapter.notifyItemInserted(position)
//                        })
            }
        }).attachToRecyclerView(recyclerView);

        btnAdicionar.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                adicionaPessoa();
            }
        })
    }
}