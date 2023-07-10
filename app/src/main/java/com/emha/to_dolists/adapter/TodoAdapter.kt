package com.emha.to_dolists.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emha.to_dolists.data.entity.Todo
import com.emha.to_dolists.databinding.TodoItemBinding

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    private lateinit var listTodos: List<Todo>

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class TodoViewHolder(private val binding: TodoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            with(binding) {
                binding.tvTodoName.text = todo.todoName
                binding.root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(todo)
                }
            }
        }
    }

    fun setListTodo(listTodos: List<Todo>) {
        this.listTodos = listTodos
        notifyDataSetChanged()
    }

    fun getTodoAtPosition(position: Int): Todo = listTodos.get(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int = listTodos.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(listTodos[position])
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Todo)
    }
}