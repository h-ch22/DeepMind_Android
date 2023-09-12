package com.cj.deepmind.community.models

data class CommunityArticleDataModel(
    var id: String = "",
    var title: String = "",
    var contents: String = "",
    var imageIndex: Int = 0,
    var author: String = "",
    var nickName: String = "",
    var createDate: String = "",
    var views: Int = 0,
    var commentCount: Int = 0,
    var board: String = ""
)
