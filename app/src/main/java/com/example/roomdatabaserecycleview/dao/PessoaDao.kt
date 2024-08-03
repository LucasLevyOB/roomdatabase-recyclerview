package com.example.roomdatabaserecycleview.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.roomdatabaserecycleview.models.Pessoa

@Dao
interface PessoaDao {
    @Insert
    fun insertAll(vararg pessoas: Pessoa);

    @Delete
    fun delete(pessoa: Pessoa): Int;

    @Query("SELECT * FROM pessoa")
    fun listAll(): List<Pessoa>;
}