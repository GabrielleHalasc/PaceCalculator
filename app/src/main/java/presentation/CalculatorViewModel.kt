package presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

class CalculatorViewModel : ViewModel() {

    fun resultRitm(distancia: Float,tempo: Float): Float {
            val ritmo: Float = distancia / tempo
            return ritmo
        }

}
