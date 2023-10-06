package com.chus.clua.domain.model


enum class Gender(val value: Int) {
    NOT_SET(0), FEMALE(1), MALE(2), NON_BINARY(3);

    companion object {
        fun fromInt(value: Int?) = Gender.values().firstOrNull { it.value == value } ?: NOT_SET
    }
}