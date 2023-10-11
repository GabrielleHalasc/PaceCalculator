package presentation

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.pacecalculator.HistoricListAdapter
import com.example.pacecalculator.R
import data.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryActivity: AppCompatActivity() {

    private val dataBase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, "Historic-database"
        ).build()
    }
    private val dao by lazy { dataBase.HistoricDao() }
    private lateinit var historicList: RecyclerView
    private val adapter = HistoricListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = ""

        val btnVoltar = findViewById<ImageButton>(R.id.imgbtn_voltar)

        btnVoltar.setOnClickListener {
            val intent = Intent(this, CalculatorActivity::class.java)
            startActivity(intent)
        }

        historicList = findViewById<RecyclerView>(R.id.rv_historic)
        historicList.adapter = adapter

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

    private fun deleteAll() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteAll()
            listFromDataBase()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_delete, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_delete_all -> {
                deleteAll()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}