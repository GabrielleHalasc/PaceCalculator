package presentation

import android.app.Application
import androidx.room.Room
import data.AppDataBase

class PaceCalculatorApplication :Application(){

    private lateinit var dataBase: AppDataBase

    override fun onCreate() {
        super.onCreate()

         dataBase = Room.databaseBuilder(
                applicationContext,
                AppDataBase::class.java, "Historic-database"
            ).build()

    }
    fun getAppDataBase(): AppDataBase{
        return dataBase
    }
}