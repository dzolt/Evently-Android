package com.apusart.evently_android.logged.create_event
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apusart.evently_android.Category
import com.apusart.evently_android.R
import kotlinx.android.synthetic.main.add_event_category.*
import kotlinx.android.synthetic.main.pick_category_list_item.view.*

class EventPickCategoryFragment: Fragment(R.layout.add_event_category) {
    private val viewModel: CreateEventViewModel by activityViewModels()
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryAdapter = CategoryAdapter()

        add_event_category_categories_list.apply {
            adapter = categoryAdapter
        }
        categoryAdapter.submitList(viewModel.categories)
        categoryAdapter.notifyDataSetChanged()
    }

}

class CategoryAdapter: ListAdapter<Category, CategoryViewHolder>(diffUtil) {

    object diffUtil: DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.name == newItem.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val container = LayoutInflater.from(parent.context)
            .inflate(R.layout.pick_category_list_item, parent, false)

        return CategoryViewHolder(container)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CategoryViewHolder(container: View): RecyclerView.ViewHolder(container) {
    fun bind(category: Category) {
        itemView.apply {

            pick_category_list_item_checkbox.setOnCheckedChangeListener { _, isChecked ->
                category.isChecked = isChecked
            }

            itemView.setOnClickListener {
                pick_category_list_item_checkbox.isChecked = !category.isChecked
            }

            pick_category_list_item_checkbox.isChecked = category.isChecked
            pick_category_list_item_text.text = category.name
        }
    }
}