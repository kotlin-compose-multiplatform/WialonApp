package com.gs.wialonlocal.features.auth.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val bact: Int,
    val cls: Int,
    val crt: Int,
    val ct: Int,
    val fl: Int,
    val ftp: Ftp,
    val hm: String,
    val id: Int,
    val ld: Int,
    val mappsmax: Int,
    val mu: Int,
    val nm: String,
    val pfl: Int,
    val prp: Prp,
    val uacl: Int
)