package com.example.movee.base

interface ListAdapterItem {
    val id: Long

    override fun equals(other: Any?): Boolean
}