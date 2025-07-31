package com.example.pregnancyvitalstracker.repository

import com.example.pregnancyvitalstracker.data.Vital
import com.example.pregnancyvitalstracker.data.VitalDao

class VitalRepository(private val dao: VitalDao) {
    val vitals = dao.getAllVitals()

    suspend fun insert(vital: Vital) {
        dao.insertVital(vital)
    }
}
