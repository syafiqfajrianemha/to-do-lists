package com.emha.to_dolists

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emha.to_dolists.data.entity.Todo
import com.emha.to_dolists.databinding.FragmentEditorTodoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditorTodoFragment(var todo: Todo?) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEditorTodoBinding

    private var editorTodoDialogListener: OnEditorTodoListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditorTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etTodoName.addTextChangedListener(textWatcher)

        if (todo != null) {
            val editable = Editable.Factory.getInstance()
            binding.etTodoName.text = editable.newEditable(todo?.todoName)
        }

        binding.btnCreateTodo.setOnClickListener {
            val etTodoName = binding.etTodoName.text.toString().trim()

            if (todo != null) {
                editorTodoDialogListener?.onTodoValue(todo?.id, etTodoName)
            } else {
                editorTodoDialogListener?.onTodoValue(null, etTodoName)
            }

            dialog?.dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        this.editorTodoDialogListener = (activity as MainActivity).editorTodoFragment
    }

    override fun onDetach() {
        super.onDetach()
        this.editorTodoDialogListener = null
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val etTodoNameValue = binding.etTodoName.text.toString().trim()

            binding.btnCreateTodo.isEnabled = etTodoNameValue.isNotEmpty()
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }

    interface OnEditorTodoListener {
        fun onTodoValue(id: Int? = null, todoName: String?)
    }
}