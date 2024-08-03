package com.example.roomdatabaserecycleview.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pessoa")
class Pessoa {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0;
    @ColumnInfo(name = "pes_nome")
    lateinit var nome: String;
    @ColumnInfo(name = "pes_curso")
    lateinit var curso: String;
    @ColumnInfo(name = "pes_idade")
    var idade: Int = 0;

    constructor(nome: String, curso: String, idade: Int) {
        this.nome = nome;
        this.curso = curso;
        this.idade = idade;
    }
}