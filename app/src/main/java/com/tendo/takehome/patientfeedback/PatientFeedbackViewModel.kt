package com.tendo.takehome.patientfeedback

import androidx.lifecycle.ViewModel
import com.tendo.repository.entries.EntryEventRepository.Companion.entryEventRepository
import com.tendo.repository.entries.models.Diagnosis
import com.tendo.repository.entries.models.Doctor
import com.tendo.repository.entries.models.Patient
import com.tendo.repository.entries.models.PatientFeedback
import com.tendo.takehome.patientfeedback.PatientFeedbackUiState.DiagnosisFeelings
import com.tendo.takehome.patientfeedback.PatientFeedbackUiState.FeedbackConfirmed
import com.tendo.takehome.patientfeedback.PatientFeedbackUiState.Loading
import com.tendo.takehome.patientfeedback.PatientFeedbackUiState.PatientRating
import com.tendo.takehome.patientfeedback.PatientFeedbackUiState.ReviewFeedback
import com.tendo.takehome.patientfeedback.PatientFeedbackUiState.UnderstandDiagnosis
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PatientFeedbackViewModel : ViewModel() {
    private var patientFeedback: PatientFeedback
    private val doctor: String
    private val diagnosis: String?

    private val _uiState = MutableStateFlow<PatientFeedbackUiState>(Loading)
    val uiState = _uiState.asStateFlow()

    init{
        val patient = entryEventRepository.getPatient("patientID").name[0].text
        entryEventRepository.getAppointmentsForPatient("patientID")[0].id.let{ appointmentId ->
            patientFeedback = PatientFeedback(appointmentId)
            doctor = entryEventRepository.getDoctorForAppointment(appointmentId).name[0].family
            diagnosis = entryEventRepository.getDiagnosisForAppointment(appointmentId)?.code?.get(0)?.name
        }
        _uiState.value = PatientRating(patient, doctor)
    }

    fun recordedRating(rating: Int){
        patientFeedback = PatientFeedback(patientFeedback.appointmentId, rating, "", "")

        _uiState.value = UnderstandDiagnosis(diagnosis, doctor)
    }

    fun doneButtonClicked(feedback : String?) {
        feedback ?: return
        _uiState.value = when (uiState.value) {
            is UnderstandDiagnosis -> { patientFeedback = PatientFeedback(patientFeedback.appointmentId, patientFeedback.doctorRating, feedback, patientFeedback.diagnosisFeelings)
                DiagnosisFeelings(diagnosis) }
            is DiagnosisFeelings -> { patientFeedback = PatientFeedback(patientFeedback.appointmentId, patientFeedback.doctorRating, patientFeedback.understandDiagnosis, feedback)
                ReviewFeedback(doctor, patientFeedback.doctorRating, patientFeedback.understandDiagnosis, patientFeedback.diagnosisFeelings) }
            else -> uiState.value
        }
    }

    fun informationConfirmed(){
        entryEventRepository.savePatientFeedback(patientFeedback)
        _uiState.value = FeedbackConfirmed
    }
}

sealed class PatientFeedbackUiState{
    object Loading: PatientFeedbackUiState()
    data class PatientRating(val patientName: String?, val doctorName: String): PatientFeedbackUiState()
    data class UnderstandDiagnosis(val diagnosis: String?, val doctorName: String): PatientFeedbackUiState()
    data class DiagnosisFeelings(val diagnosis: String?): PatientFeedbackUiState()
    data class ReviewFeedback(val doctorName: String, val rating: Int, val understandDiagnosis: String, val diagnosisFeelings: String) : PatientFeedbackUiState()
    object FeedbackConfirmed: PatientFeedbackUiState()
}