package com.cj.deepmind.userManagement.models

data class UserInfoModel(
    val UID : String,
    val email : String,
    val name : String,
    val nickName : String,
    val phone : String,
    val birthDay : String,
    val type: UserTypeModel,
    val agency: String,
    val isChildAbuseAttacker: Boolean,
    val isChildAbuseVictim: Boolean,
    val isDomesticViolenceAttacker : Boolean,
    val isDomesticViolenceVictim : Boolean,
    val isPsychosis : Boolean
)