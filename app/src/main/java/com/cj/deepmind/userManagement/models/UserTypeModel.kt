package com.cj.deepmind.userManagement.models

enum class UserTypeModel {
    CUSTOMER{
        override fun getDescription(): String {
            return "사용자"
        }

        override fun getACode(): String {
            return "Customer"
        }
    },

    PROFESSIONAL{
        override fun getDescription(): String {
            return "전문가"
        }

        override fun getACode(): String {
            return "Professional"
        }
    };

    abstract fun getDescription() : String
    abstract fun getACode() : String
}