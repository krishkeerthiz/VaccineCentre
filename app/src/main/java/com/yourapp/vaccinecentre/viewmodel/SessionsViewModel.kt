package com.yourapp.vaccinecentre.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourapp.vaccinecentre.apiservice.SessionsApi
import com.yourapp.vaccinecentre.schemamodel.SessionsModel
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class SessionsViewModel : ViewModel() {

    lateinit var sDate : String

    private val _sessionsModel = MutableLiveData<SessionsModel>()

    val sessionsModel : LiveData<SessionsModel>
        get() = _sessionsModel

    private val _name = MutableLiveData<String>()

    val name : LiveData<String>
        get() = _name

    init{
        //Get current date
        val millis = System.currentTimeMillis()
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis
        sDate = SimpleDateFormat("dd-MM-yyyy").format(calendar.time)
        //getVaccineCentres("600020","11-06-2021")
    }

    fun getVaccineCentres(pincode : String, date : String){
        viewModelScope.launch {
            try{
                _sessionsModel.value = SessionsApi.service.getCentres(pincode, date)
                //_name.value = sessionsModel.sessions?.get(0)?.name ?: "not working"
            }
            catch (e : Exception){
                _name.value = "Error"
            }
        }
    }
}