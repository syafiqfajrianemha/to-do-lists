package com.emha.to_dolists.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "todo_name") @NonNull val todoName: String
)
