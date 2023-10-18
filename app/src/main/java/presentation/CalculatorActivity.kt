package presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.pacecalculator.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import data.AppDataBase
import data.HistoricItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CalculatorActivity : AppCompatActivity() {

    private val dataBase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, "Historic-database"
        ).build()
    }

    private val dao by lazy {
        dataBase.HistoricDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        val tietDistancia = findViewById<TextInputEditText>(R.id.tiet_distancia)
        val tilDistancia = findViewById<TextInputLayout>(R.id.til_distancia)
        val tietTempo = findViewById<TextInputEditText>(R.id.tiet_tempo)
        val tilTempo = findViewById<TextInputLayout>(R.id.til_tempo)
        val btnCalcular = findViewById<Button>(R.id.btn_calcular)
        val btnHistorico = findViewById<Button>(R.id.btn_historico)
        val txtRitmo = findViewById<TextView>(R.id.txt_result)

        val viewModel: CalculatorViewModel by viewModels()


        btnHistorico.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
        btnCalcular.setOnClickListener {


            val distanciaString = tietDistancia.text.toString()
            val tempoString = tietTempo.text.toString()


            if (distanciaString.isEmpty()) {
                tilDistancia.error = "*Altura obrigatória"
            } else {
                tilDistancia.error = null
                tilDistancia.isErrorEnabled = false
            }
            if (tempoString.isEmpty()) {
                tilTempo.error = "*Peso obrigatório"
            } else {
                tilTempo.error = null
                tilTempo.isErrorEnabled = false
            }


            if (distanciaString.isNotEmpty() && tempoString.isNotEmpty()) {
                val distancia: Float = tietDistancia.text.toString().toFloat()
                val tempo: Float = tietTempo.text.toString().toFloat()
                val ritmo:Float = viewModel.resultRitm(distancia,tempo)

                txtRitmo.text = ritmo.toString()

                val item = HistoricItem(distancia = distancia, tempo = tempo, ritmo = ritmo)
                CoroutineScope(Dispatchers.IO).launch {
                    dao.insert(item)
                }
            }

        }

    }
}