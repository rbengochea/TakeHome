package com.tendo.repository.entries

import com.tendo.repository.entries.models.Appointment
import com.tendo.repository.entries.models.Diagnosis
import com.tendo.repository.entries.models.Doctor
import com.tendo.repository.entries.models.Patient
import com.tendo.repository.entries.models.PatientFeedback

interface EntryEventClient {
    fun getPatient(patientId: String): Patient

    fun getAppointmentsForPatient(patientId:String): List<Appointment>

    fun getDoctorForAppointment(appointmentId:String): Doctor

    fun getDiagnosisForAppointment(appointmentId: String): Diagnosis?

    fun savePatientFeedback(feedback: PatientFeedback): Boolean
}