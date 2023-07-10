package com.emha.to_dolists.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.emha.to_dolists.data.entity.Todo

@Dao
interface TodoDao {

    @Insert
    fun insert(todo: Todo)

    @Query("SELECT * FROM todos ORDER BY id DESC")
    fun getTodos(): LiveData<List<Todo>>

    @Update
    fun update(todo: Todo)

    @Delete
    fun delete(todo: Todo): Int
}