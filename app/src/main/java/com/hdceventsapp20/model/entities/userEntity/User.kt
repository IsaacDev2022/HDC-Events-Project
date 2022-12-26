package com.hdceventsapp20.model.entities.userEntity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val idUser: Long = 0,
    val nameUser: String = "",
    val cpfUser: String = "",
    val ageUser: String = "",
    val email: String = "",
    val password: String = ""
) : Parcelable
