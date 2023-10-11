package com.emha.to_dolists

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emha.to_dolists.adapter.TodoAdapter
import com.emha.to_dolists.data.entity.Todo
import com.emha.to_dolists.databinding.ActivityMainBinding
import com.emha.to_dolists.model.TodoViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var todoModel: TodoViewModel
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoAdapter = TodoAdapter()
        todoModel = ViewModelProvider(this).get(TodoViewModel::class.java)

        showListTodo()

        binding.fabAdd.setOnClickListener {
            val fragmentManager = supportFragmentManager
            EditorTodoFragment(null).show(
                fragmentManager,
                EditorTodoFragment::class.java.simpleName
            )
        }
    }

    private fun showListTodo() {
        binding.rvTodo.setHasFixedSize(true)
        binding.rvTodo.layoutManager = LinearLayoutManager(this)
        todoModel.getNotes().observe(this) { todo ->
            if (todo.isNotEmpty()) {
                todoAdapter.setListTodo(todo)
                binding.rvTodo.adapter = todoAdapter
                binding.tvTodoEmpty.visibility = View.GONE
            } else {
                binding.tvTodoEmpty.visibility = View.VISIBLE
            }
        }

        todoAdapter.setOnItemClickCallback(object : TodoAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Todo) {
                val fragmentManager = supportFragmentManager
                EditorTodoFragment(data).show(
                    fragmentManager,
                    EditorTodoFragment::class.java.simpleName
                )
            }
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                todoModel.delete(todoAdapter.getTodoAtPosition(viewHolder.layoutPosition))
                Toast.makeText(this@MainActivity, "Deleted successfully", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(binding.rvTodo)
    }

    internal var editorTodoFragment: EditorTodoFragment.OnEditorTodoListener =
        object : EditorTodoFragment.OnEditorTodoListener {
            override fun onTodoValue(id: Int?, todoName: String?) {
                if (todoName != null) {
                    if (id != null) {
                        val todo = Todo(
                            id,
                            todoName
                        )
                        todoModel.update(todo)
                        Toast.makeText(this@MainActivity, "Edited successfully", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        todoModel.store(todoName)
                        Toast.makeText(this@MainActivity, "Added successfully", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
}