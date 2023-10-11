package presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.pacecalculator.R
import com.example.pacecalculator.TaskListAdapter
import data.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskActivity:AppCompatActivity() {

    private val dataBase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, "Task-database"
        ).build()
    }
    private val dao by lazy { dataBase.HistoricDao() }
    private lateinit var taskList: RecyclerView
    private val adapter = TaskListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        setSupportActionBar(findViewById(R.id.toolbar2))
        supportActionBar?.title = ""

        val taskList =findViewById<RecyclerView>(R.id.rv_planejamento)
        taskList.adapter = adapter
        listFromDataBase()

        adapter.setOnDeleteClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                dao.deleteById(it.id)
                listFromDataBase()
            }
        }
    }
    private fun listFromDataBase() {

        CoroutineScope(Dispatchers.IO).launch {

            val list = dao.getAll()
            lifecycleScope.launch {
                adapter.submitList(list)
            }

        }

    }
}