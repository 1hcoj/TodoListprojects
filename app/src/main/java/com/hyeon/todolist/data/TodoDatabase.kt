package com.hyeon.todolist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hyeon.todolist.Todo

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase : RoomDatabase(){
    abstract fun todoDao() : TodoDao

    companion object{
        @Volatile
        private var instance : TodoDatabase? = null

        fun getInstance(context : Context) : TodoDatabase = instance?: synchronized(this){
            instance ?: buildDatabase(context).also {instance = it}
        }

        private fun buildDatabase(context : Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java, "Todo.db"
            ).build()
    }
}