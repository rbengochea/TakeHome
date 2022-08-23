package com.tendo.repository.entries

import com.tendo.repository.entries.models.Actor
import com.tendo.repository.entries.models.Appointment
import com.tendo.repository.entries.models.AppointmentVisit
import com.tendo.repository.entries.models.Coding
import com.tendo.repository.entries.models.Diagnosis
import com.tendo.repository.entries.models.Doctor
import com.tendo.repository.entries.models.Meta
import com.tendo.repository.entries.models.Name
import com.tendo.repository.entries.models.Patient
import com.tendo.repository.entries.models.PatientFeedback
import com.tendo.repository.entries.models.Period
import com.tendo.repository.entries.models.Subject

class LocalEntryEventClient: EntryEventClient {
    private val patients: MutableList<Patient> = mutableListOf(Patient(id="patientID",active= true, name= listOf(Name("Tendo Tenderson", "Tenderson", listOf("Tendo"))), contact = listOf(), gender = "gender", birthdate = "birthdateString", address = listOf()))
    private val appointments: MutableList<Appointment> = mutableListOf(Appointment("appointmentId", "inProgress", listOf(), subject = Subject("patientID"), actor = Actor("doctorId"), Period("start", "end")))
    private val doctors: MutableList<Doctor> = mutableListOf( Doctor("doctorId", listOf(Name("null", "Careful", listOf()))))
    private val diagnosis: MutableList<Diagnosis> = mutableListOf(Diagnosis("diagnosis", Meta("meta meta meta data"), status = "final", listOf(
        Coding("system", "code", "Diabetes without complications")
    ), AppointmentVisit("appointmentId")))
    private val feedbackRecords: MutableList<PatientFeedback> = mutableListOf()


    override fun getPatient(patientId: String): Patient = patients.firstOrNull { it.id == patientId } ?: throw PatientDoesntExistException()

    override fun getAppointmentsForPatient(patientId:String): List<Appointment> = appointments.filter { it.subject.reference.contains(patientId) }

    override fun getDoctorForAppointment(appointmentId:String): Doctor {
        val appointment = appointments.firstOrNull { it.id == appointmentId } ?: throw DoctorDoesntExistException()
        return doctors.firstOrNull { appointment.actor.reference.contains(it.id) } ?: throw DoctorDoesntExistException()
    }

    override fun getDiagnosisForAppointment(appointmentId: String): Diagnosis? {
        val appointment = appointments.firstOrNull { it.id == appointmentId } ?: return null
        return diagnosis.firstOrNull { it.appointment.reference.contains(appointment.id) }
    }

    override fun savePatientFeedback(feedback: PatientFeedback): Boolean = feedbackRecords.add(feedback)
}

class PatientDoesntExistException: Exception()
class DoctorDoesntExistException: Exception()