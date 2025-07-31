package com.example.pregnancyvitalstracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pregnancyvitalstracker.data.Vital
import com.example.pregnancyvitalstracker.data.VitalDatabase
import com.example.pregnancyvitalstracker.repository.VitalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VitalViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: VitalRepository

    val vitals: StateFlow<List<Vital>> = MutableStateFlow(emptyList())

    init {
        val dao = VitalDatabase.getInstance(application).vitalDao()
        repository = VitalRepository(dao)

        viewModelScope.launch {
            repository.vitals.collect { (vitals as MutableStateFlow).value = it }
        }
    }

    fun addVital(vital: Vital) = viewModelScope.launch {
        repository.insert(vital)
    }
}
