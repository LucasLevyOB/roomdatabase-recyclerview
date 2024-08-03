package com.example.roomdatabaserecycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabaserecycleview.models.Pessoa

class PessoaItemAdapter(private val listaPessoas: ArrayList<Pessoa>): RecyclerView.Adapter<PessoaItemAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return listaPessoas.size;
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNome: TextView = view.findViewById(R.id.tv_nome);
        val tvCurso: TextView = view.findViewById(R.id.tv_curso);
        val tvIdade: TextView = view.findViewById(R.id.tv_idade);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pessoa_item, parent, false);

        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvNome.setText(listaPessoas[position].nome);
        holder.tvCurso.setText(listaPessoas[position].curso);
        holder.tvIdade.setText(listaPessoas[position].idade.toString());
    }
}