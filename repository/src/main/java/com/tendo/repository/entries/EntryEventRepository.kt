package com.tendo.repository.entries

import com.tendo.repository.entries.models.Appointment
import com.tendo.repository.entries.models.Diagnosis
import com.tendo.repository.entries.models.Doctor
import com.tendo.repository.entries.models.Patient
import com.tendo.repository.entries.models.PatientFeedback

class EntryEventRepository(private val entryEventClient: EntryEventClient = LocalEntryEventClient()) {
    fun getPatient(patientId: String): Patient = entryEventClient.getPatient(patientId)

    fun getAppointmentsForPatient(patientId:String): List<Appointment> = entryEventClient.getAppointmentsForPatient(patientId)

    fun getDoctorForAppointment(appointmentId:String): Doctor = entryEventClient.getDoctorForAppointment(appointmentId)

    fun getDiagnosisForAppointment(appointmentId: String): Diagnosis? = entryEventClient.getDiagnosisForAppointment(appointmentId)

    fun savePatientFeedback(feedback: PatientFeedback): Boolean = entryEventClient.savePatientFeedback(feedback)

    companion object{
        val entryEventRepository: EntryEventRepository = EntryEventRepository()
    }
}