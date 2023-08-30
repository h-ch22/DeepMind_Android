package com.cj.deepmind.userManagement.helper

import com.cj.deepmind.frameworks.helper.AES256Util
import com.cj.deepmind.userManagement.models.UserInfoModel
import com.cj.deepmind.userManagement.models.UserTypeModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore

class UserManagement {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    companion object{
        var userInfo : UserInfoModel? = null
    }

    fun signIn(email: String, password: String, completion: (Boolean) -> Unit){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                getUserInfo {
                    if(it){
                        completion(true)
                        return@getUserInfo
                    } else{
                        completion(false)
                        return@getUserInfo
                    }
                }
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

    fun signUp(email: String, password: String, name: String, nickName: String, phone: String, birthDay: String, type: UserTypeModel, agency: String?, completion: (Boolean) -> Unit){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                db.collection("Users").document(auth.currentUser?.uid ?: "").set(hashMapOf(
                    "email" to AES256Util.encrypt(email),
                    "name" to AES256Util.encrypt(name),
                    "nickName" to AES256Util.encrypt(nickName),
                    "phone" to AES256Util.encrypt(phone),
                    "birthDay" to AES256Util.encrypt(birthDay),
                    "type" to type.getACode(),
                    "agency" to if(agency != null) AES256Util.encrypt(agency) else null
                )).addOnCompleteListener {
                    if(it.isSuccessful){
                        completion(true)
                        return@addOnCompleteListener
                    } else{
                        try{
                            auth.currentUser?.delete()
                        } catch(e : Exception){
                            e.printStackTrace()
                        } finally {
                            completion(false)
                            return@addOnCompleteListener
                        }
                    }
                }.addOnFailureListener {
                    it.printStackTrace()

                    try{
                        auth.currentUser?.delete()
                    } catch(e : Exception){
                        e.printStackTrace()
                    } finally {
                        completion(false)
                        return@addOnFailureListener
                    }
                }
            }
        }.addOnFailureListener {
            it.printStackTrace()
            completion(false)
            return@addOnFailureListener
        }
    }

    fun uploadFeatures(isChildAbuseAttacker: Boolean, isChildAbuseVictim: Boolean, isDomesticViolenceAttacker: Boolean, isDomesticViolenceVictim: Boolean, isPsychosis: Boolean, completion: (Boolean) -> Unit){
        db.collection("FeatureInformation").document(auth.currentUser?.uid ?: "").set(hashMapOf(
            AES256Util.encrypt("isChildAbuseAttacker") to isChildAbuseAttacker,
            AES256Util.encrypt("isChildAbuseVictim") to isChildAbuseVictim,
            AES256Util.encrypt("isDomesticViolenceAttacker") to isDomesticViolenceAttacker,
            AES256Util.encrypt("isDomesticViolenceVictim") to isDomesticViolenceVictim,
            AES256Util.encrypt("isPsychosis") to isPsychosis
        )).addOnCompleteListener {
            if(it.isSuccessful){
                getUserInfo {
                    if(it){
                        completion(true)
                        return@getUserInfo
                    } else{
                        completion(false)
                        return@getUserInfo
                    }
                }
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

    fun getUserInfo(completion: (Boolean) -> Unit){
        db.collection("Users").document(auth.currentUser?.uid ?: "").get().addOnCompleteListener {
            if(it.isSuccessful){
                val document = it.result

                if(document != null && document.exists()){
                    val UID = auth.currentUser?.uid ?: ""
                    val email = document.get("email") as? String ?: ""
                    val name = document.get("name") as? String ?: ""
                    val nickName = document.get("nickName") as? String ?: ""
                    val phone = document.get("phone") as? String ?: ""
                    val birthDay = document.get("birthDay") as? String ?: ""
                    val type = document.get("type") as? String ?: ""
                    val agency = document.get("agency") as? String ?: ""

                    db.collection("FeatureInformation").document(auth.currentUser?.uid ?: "").get().addOnCompleteListener {
                        if(it.isSuccessful){
                            val featureDoc = it.result

                            if(featureDoc != null && featureDoc.exists()){
                                val isChildAbuseAttacker = featureDoc.get(FieldPath.of(AES256Util.encrypt("isChildAbuseAttacker"))) as? Boolean ?: false
                                val isChildAbuseVictim = featureDoc.get(FieldPath.of(AES256Util.encrypt("isChildAbuseVictim"))) as? Boolean ?: false
                                val isDomesticViolenceAttacker = featureDoc.get(FieldPath.of(AES256Util.encrypt("isDomesticViolenceAttacker"))) as? Boolean ?: false
                                val isDomesticViolenceVictim = featureDoc.get(FieldPath.of(AES256Util.encrypt("isDomesticViolenceVictim"))) as? Boolean ?: false
                                val isPsychosis = featureDoc.get(FieldPath.of(AES256Util.encrypt("isPsychosis"))) as? Boolean ?: false

                                userInfo = UserInfoModel(UID = UID, email = AES256Util.decrypt(email), name = AES256Util.decrypt(name), AES256Util.decrypt(nickName), AES256Util.decrypt(phone), AES256Util.decrypt(birthDay), if(type == "Professional") UserTypeModel.PROFESSIONAL else UserTypeModel.CUSTOMER, if(agency != "") AES256Util.decrypt(agency) else agency, isChildAbuseAttacker, isChildAbuseVictim, isDomesticViolenceAttacker, isDomesticViolenceVictim, isPsychosis)
                                completion(true)
                                return@addOnCompleteListener
                            } else{
                                completion(false)
                                return@addOnCompleteListener
                            }
                        } else{
                            completion(false)
                            return@addOnCompleteListener
                        }
                    }.addOnFailureListener {
                        it.printStackTrace()
                        completion(false)
                        return@addOnFailureListener
                    }
                } else{
                    completion(false)
                    return@addOnCompleteListener
                }
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

    fun signOut(completion: (Boolean) -> Unit){
        try{
            auth.signOut()
            userInfo = null
            completion(true)
            return
        } catch(e: Exception){
            e.printStackTrace()
            completion(false)
            return
        }
    }

    fun secession(completion: (Boolean) -> Unit){
        try{
            auth.currentUser?.delete()
            userInfo = null
            completion(true)
            return
        } catch(e: Exception){
            e.printStackTrace()
            completion(false)
            return
        }
    }
}