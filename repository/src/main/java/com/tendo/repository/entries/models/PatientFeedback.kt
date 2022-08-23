package com.tendo.repository.entries.models

data class PatientFeedback(
    val appointmentId: String,
    val doctorRating: Int = -1,
    val understandDiagnosis: String = "",
    val diagnosisFeelings: String = ""
)