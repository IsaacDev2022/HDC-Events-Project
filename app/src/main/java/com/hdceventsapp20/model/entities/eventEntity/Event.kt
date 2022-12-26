package com.hdceventsapp20.model.entities.eventEntity

import android.os.Parcelable
import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "event_tb")
data class Event(
    @PrimaryKey(autoGenerate = true) val idEvent: Long = 0,
    val nameEvent: String = "",
    val dateEvent: String = "",
    val timeEvent: String = "",
    val city: String = "",
    val type: String = "",
    val privateOrPublic: String = "",
    val description: String = ""
) : Parcelable
