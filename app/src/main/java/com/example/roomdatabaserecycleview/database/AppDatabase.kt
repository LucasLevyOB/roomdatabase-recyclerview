package com.example.roomdatabaserecycleview.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomdatabaserecycleview.dao.PessoaDao
import com.example.roomdatabaserecycleview.models.Pessoa

@Database(entities = [Pessoa::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pessoaDao(): PessoaDao;
}