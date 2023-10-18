package presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.HistoricDao
import data.HistoricItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch

class HistoricListViewModel(
    private val historicDao: HistoricDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    val historicListLiveData: LiveData<List<HistoricItem>> = historicDao.getAll()

    fun deleteById(id: Int) {
        viewModelScope.launch(dispatcher) {
            historicDao.deleteById(id)
        }
    }

    fun  deleteAll() {
        viewModelScope.launch(dispatcher) {
            historicDao.deleteAll()
        }
    }

    companion object {

        fun create(application: Application): HistoricListViewModel {
            val dataBaseInstance = (application as PaceCalculatorApplication).getAppDataBase()
            val dao = dataBaseInstance.HistoricDao()
            return HistoricListViewModel(dao)
        }
    }

}