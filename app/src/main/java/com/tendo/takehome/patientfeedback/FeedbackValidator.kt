package com.tendo.takehome.patientfeedback

import com.tendo.takehome.patientfeedback.DoctorRating.Bad
import com.tendo.takehome.patientfeedback.DoctorRating.Good
import com.tendo.takehome.patientfeedback.DoctorRating.Mediocre
import com.tendo.takehome.patientfeedback.UnderstoodDoctor.Maybe
import com.tendo.takehome.patientfeedback.UnderstoodDoctor.No
import com.tendo.takehome.patientfeedback.UnderstoodDoctor.Yes


fun String.understandsDiagnosis(): UnderstoodDoctor{
    return when{
        this.lowercase().contains("yes") -> Yes
        this.lowercase().contains("no") -> No
        else -> Maybe
    }
}


fun Int.doctorRating(): DoctorRating {
    return when{
        this < 4 ->  Bad
        this in 4..8 -> Mediocre
        else-> Good
    }
}

enum class UnderstoodDoctor{
    Yes,
    No,
    Maybe
}

enum class DoctorRating {
    Bad,
    Mediocre,
    Good
}