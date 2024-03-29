package estyle.teabaike.datasource

import android.content.Context
import androidx.room.Room
import estyle.teabaike.TeaBaikeDatabase
import estyle.teabaike.config.Db

object DbDataSource {

    private lateinit var database: TeaBaikeDatabase

    fun init(context: Context) {
        database = Room.databaseBuilder(context, TeaBaikeDatabase::class.java, Db.NAME)
            .build()
    }

    fun collectionDao() = database.collectionDao()
}