package com.hernanbosqued.db_unit_tests

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.hernanbosqued.db_unit_tests.databinding.FragmentMainBinding
import com.hernanbosqued.db_unit_tests.db.AppDatabase

class MainFragment : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

    private val database by lazy { AppDatabase.getDatabase(requireContext()) }
    private val viewModel by lazy { MainViewModelFactory(ParameterStorageImpl(database)).create(MainViewModel::class.java) }

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObservers()
        bindListeners()
        setupUI()

        viewModel.start()
    }

    private fun setupUI() {
        binding.list.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }

    private fun bindListeners() {
        binding.add.setOnClickListener { viewModel.add() }
        binding.subtract.setOnClickListener { viewModel.subtract() }
    }

    private fun registerObservers() {
        viewModel.data().observe(viewLifecycleOwner) { data ->
            binding.list.adapter = ListAdapter(data)
            binding.list.scrollToPosition(data.lastIndex)
        }
    }
}

class ListAdapter(private val list: List<Int>) : RecyclerView.Adapter<TextViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        return TextViewHolder.make(parent)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class TextViewHolder(private val view: TextElementView) : RecyclerView.ViewHolder(view) {
    companion object {
        fun make(parent: ViewGroup) = TextViewHolder(TextElementView(parent.context))
    }

    fun bind(value: Int) {
        view.apply {
            text = value.toString()
        }
    }
}

class TextElementView(context: Context) : AppCompatTextView(context) {

    init {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        gravity = Gravity.CENTER_HORIZONTAL
        textSize = resources.getDimension(R.dimen.item_text_size)
    }
}