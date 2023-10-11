package com.example.pacecalculator


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import data.HistoricItem
import data.TaskItem

class TaskListAdapter: RecyclerView.Adapter<TaskListViewHolder>() {

    private var taskList: List<TaskItem> = listOf()
    private lateinit var onClickListener: (TaskItem) -> Unit

    fun submitList(list: List<HistoricItem>){
        this.taskList = list
        notifyDataSetChanged()
    }
    fun setOnDeleteClickListener(onClick: (TaskItem) -> Unit) {
        this.onClickListener = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskListViewHolder(view)

    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val item = taskList[position]
        holder.bind(item)
    }
}

class TaskListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val tvTitleTask = view.findViewById<TextView>(R.id.txt_title_task)
    val tvDescriptionTask = view.findViewById<TextView>(R.id.txt_description_task)


    fun bind(
        item: TaskItem
    ) {

        tvTitleTask.text = item.title
        tvDescriptionTask.text = item.description
    }
}