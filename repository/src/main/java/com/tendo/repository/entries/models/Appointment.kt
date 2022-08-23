package com.tendo.repository.entries.models

data class Appointment(val id: String, val status: String, val type: List<Visit>, val subject: Subject, val actor: Actor, val period: Period)

data class Visit(val text: String)

data class Subject(val reference: String) //patient

data class Actor(val reference: String) //doctor

data class Period(val start:String, val end: String)