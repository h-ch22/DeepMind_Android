package com.cj.deepmind.community.helper

import android.net.Uri
import com.cj.deepmind.community.models.CommunityArticleDataModel
import com.cj.deepmind.community.models.CommunityCommentDataModel
import com.cj.deepmind.frameworks.helper.AES256Util
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.Date

class CommunityHelper {
    companion object{
        private var articleList: ArrayList<CommunityArticleDataModel> = arrayListOf()
        private var boardList: HashMap<String, String> = hashMapOf("자유 게시판" to "Free",
                                                            "질문 게시판" to "Question",
                                                            "맛집 게시판" to "Restaurant",
                                                            "병원 추천" to "Hospital",
                                                            "문화/생활" to "Culture",
                                                            "이벤트" to "Event",
                                                            "자녀방" to "Child",
                                                            "구인/구직" to "JobSearch",
                                                            "판매" to "Market")
    }

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val dateFormatter = SimpleDateFormat("yyyy. MM. dd. kk:mm:ss.SSSS")

    fun getAllArticles(completion: (Boolean) -> Unit) {
        db.collection("Community").get().addOnSuccessListener { querySnapshot ->
            if(querySnapshot != null){
                for(document in querySnapshot.documents){
                    val title = document.get("title") as? String ?: ""
                    val contents = document.get("contents") as? String ?: ""
                    val imageIndex = document.get("imageIndex") as? Int ?: 0
                    val author = document.get("author") as? String ?: ""
                    val nickName = document.get("nickName") as? String ?: ""
                    val createDate = document.get("createDate") as? String ?: ""
                    val views = document.get("views") as? Int ?: 0
                    val board = document.get("board") as? String ?: ""

                    this.getCommentCount(document.id){ commentCount ->
                        articleList.add(
                            CommunityArticleDataModel(
                                id = document.id,
                                title = AES256Util.decrypt(title),
                                contents = AES256Util.decrypt(contents),
                                imageIndex = imageIndex,
                                author = author,
                                nickName = AES256Util.decrypt(nickName),
                                createDate = createDate,
                                views = views,
                                commentCount = commentCount,
                                board = boardList.filterValues {it == AES256Util.decrypt(board)}.keys.first())
                        )

                        if(querySnapshot.documents.size == articleList.size){
                            completion(true)
                        }
                    }
                }
            } else{
                completion(false)
                return@addOnSuccessListener
            }
        }.addOnFailureListener {
            it.printStackTrace()
            completion(false)
            return@addOnFailureListener
        }
    }

    fun getAllArticles(): ArrayList<CommunityArticleDataModel>{
        return articleList
    }

    fun downloadImages(id: String, imageIndex: Int, completion: (ArrayList<Uri>?) -> Void) {
        val urlList : ArrayList<Uri> = arrayListOf()

        for(i in 0 until imageIndex){
            storage.reference.child("Community/${id}/${i}.png").downloadUrl.addOnSuccessListener {
                urlList.add(it)
            }
        }

        completion(urlList)
        return
    }

    fun getComments(id: String, completion: (ArrayList<CommunityCommentDataModel>) -> Unit){
        val commentList: ArrayList<CommunityCommentDataModel> = arrayListOf()

        db.collection("Community").document(id).collection("Comments").get().addOnSuccessListener {
            if(it != null){
                for(document in it.documents){
                    val author = document.get("author") as? String ?: ""
                    val nickName = document.get("nickName") as? String ?: ""
                    val contents = document.get("contents") as? String ?: ""
                    val uploadDate = document.get("uploadDate") as? String ?: ""

                    commentList.add(
                        CommunityCommentDataModel(author = author, nickName = AES256Util.decrypt(nickName), contents = AES256Util.decrypt(contents), uploadDate = uploadDate)
                    )
                }
            }

            completion(commentList)
            return@addOnSuccessListener
        }.addOnFailureListener {
            it.printStackTrace()
            completion(commentList)
            return@addOnFailureListener
        }
    }

    private fun getCommentCount(id: String, completion: (Int) -> Unit){
        db.collection("Community").document(id).collection("Comments").get().addOnSuccessListener {
            completion(it?.documents?.size ?: 0)
        }.addOnFailureListener {
            it.printStackTrace()
            completion(0)
        }
    }

    fun removeArticle(id: String, completion: (Boolean) -> Unit){
        val commentsRef = db.collection("Community").document(id).collection("Comments")
        commentsRef.get().addOnSuccessListener {
            if(it != null){
                for(document in it.documents){
                    commentsRef.document(document.id).delete()
                }
            }
        }

        db.collection("Community").document(id).delete().addOnCompleteListener {
            if(it.isSuccessful){
                completion(true)
                return@addOnCompleteListener
            } else{
                completion(false)
                return@addOnCompleteListener
            }
        }.addOnFailureListener {
            it.printStackTrace()
            completion(false)
            return@addOnFailureListener
        }
    }

    fun removeComment(id: String, commentId: String, completion: (Boolean) -> Unit){
        db.collection("Community").document(id).collection("Comments").document(commentId).delete().addOnCompleteListener {
            if(it.isSuccessful){
                completion(true)
                return@addOnCompleteListener
            } else{
                completion(false)
                return@addOnCompleteListener
            }
        }.addOnFailureListener {
            it.printStackTrace()
            completion(false)
            return@addOnFailureListener
        }
    }

    fun uploadArticle(title: String, contents: String, author: String, nickName: String, board: String, imgs: ArrayList<Uri>, completion: (Boolean) -> Unit){
        val docRef = db.collection("Community").document()

        docRef.set(
            hashMapOf(
                "title" to AES256Util.encrypt(title),
                "contents" to AES256Util.encrypt(contents),
                "imageIndex" to imgs.size,
                "author" to author,
                "nickName" to nickName,
                "createDate" to dateFormatter.format(Date()),
                "views" to 0,
                "board" to AES256Util.encrypt(boardList.get(board))
            )
        ).addOnCompleteListener {
            if(it.isSuccessful){
                if(imgs.size > 0){
                    for(i in 0 until imgs.size){
                        val storageRef = storage.reference.child("Community/${docRef.id}/${i}.png")
                        storageRef.putFile(imgs[i]).addOnFailureListener {
                            it.printStackTrace()
                            completion(false)
                            return@addOnFailureListener
                        }
                    }
                }

                completion(true)
                return@addOnCompleteListener
            } else{
                completion(false)
                return@addOnCompleteListener
            }
        }.addOnFailureListener {
            it.printStackTrace()
            completion(false)
            return@addOnFailureListener
        }
    }

    fun uploadComments(id: String, contents: String, nickName: String, author: String, completion: (Boolean) -> Unit){
        db.collection("Community").document(id).collection("Comments").add(
            hashMapOf(
                "author" to author,
                "nickName" to nickName,
                "contents" to AES256Util.encrypt(contents),
                "uploadDate" to dateFormatter.format(Date())
            )
        ).addOnCompleteListener {
            if(it.isSuccessful){
                completion(true)
                return@addOnCompleteListener
            } else{
                completion(false)
                return@addOnCompleteListener
            }
        }.addOnFailureListener {
            it.printStackTrace()
            completion(false)
            return@addOnFailureListener
        }
    }
}