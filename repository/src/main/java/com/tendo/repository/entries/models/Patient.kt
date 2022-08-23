package com.tendo.repository.entries.models


data class Patient(val id:String,
                   val active:Boolean,
                   val name: List<Name>,
                   val contact: List<Contact>,
                   val gender: String,
                   val birthdate: String,
                   val address: List<Address>)

data class Name(val text:String,
                val family:String,
                val given: List<String>)

data class Contact(val system: String,
                   val value: String,
                   val use: String)

data class Address(val use: String,
                   val line: List<String>)
