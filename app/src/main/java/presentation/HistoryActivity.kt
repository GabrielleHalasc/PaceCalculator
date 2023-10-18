package presentation

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.pacecalculator.R
import data.HistoricItem

class HistoryActivity : AppCompatActivity() {

    private val viewModel: HistoricListViewModel by lazy {
        HistoricListViewModel.create(application)
    }
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
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        historicList = findViewById<RecyclerView>(R.id.rv_historic)
        historicList.adapter = adapter

        listFromDataBase()

        adapter.setOnDeleteClickListener {
            viewModel.deleteById(it.id)
        }
    }


    override fun onStart() {
        super.onStart()
        listFromDataBase()
    }

    private fun listFromDataBase() {

        //Observer (sozinho n faz nada, precisa atrelar ao life data)
        val listObserver = Observer<List<HistoricItem>> {
            adapter.submitList(it)
        }
        //Live Data
        viewModel.historicListLiveData.observe(this@HistoryActivity, listObserver)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_delete, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_delete_all -> {
                viewModel.deleteAll()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
