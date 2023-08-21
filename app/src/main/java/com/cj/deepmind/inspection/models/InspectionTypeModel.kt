package com.cj.deepmind.inspection.models

enum class InspectionTypeModel {
    HOUSE{
        override fun getDescription() = "집"
        override fun getCode() = 0
    },

    TREE{
        override fun getDescription() = "나무"
        override fun getCode() = 1
    },

    PERSON_1{
        override fun getDescription() = "첫번째 사람"
        override fun getCode() = 2
    },

    PERSON_2{
        override fun getDescription() = "두번째 사람"
        override fun getCode() = 3
    };

    abstract fun getDescription() : String
    abstract fun getCode() : Int
}