package com.example.test_app.data_store

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val language: Language = Language.ENGLISH,
    val knownLocations: PersistentList<Location> = persistentListOf()
)

@Serializable
data class Location(
    val latitude: Double,
    val longitude: Double
)

enum class Language {
    ENGLISH, KOREAN, SPANISH
}
