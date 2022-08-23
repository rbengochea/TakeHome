package com.tendo.repository.entries.models

data class Diagnosis(val id: String, val meta: Meta, val status: String, val code: List<Coding>, val appointment:AppointmentVisit)

data class Meta(val lastUpdated: String)

data class Coding(val system: String, val code: String, val name: String)

data class AppointmentVisit(val reference: String)