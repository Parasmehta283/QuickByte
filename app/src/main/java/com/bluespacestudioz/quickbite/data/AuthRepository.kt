package com.bluespacestudioz.quickbite.data

import com.bluespacestudioz.quickbite.model.User
import com.bluespacestudioz.quickbite.model.UserRole
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class AuthRepository(private val firebaseAuth: FirebaseAuth,private val  fireStore: FirebaseFirestore) {


    fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    suspend fun getUserRole() : String?
    {
        val uid = firebaseAuth.currentUser?.uid
        val getUser = uid?.let { fireStore.collection("users").document(it) }?.get()?.await()
        val user = getUser?.toObject(User::class.java)
         return user?.role


    }

    suspend fun signup(name : String,email: String,password: String) : Result<Unit>
    {
        return try {

            val result = firebaseAuth.createUserWithEmailAndPassword(email,password).await()
            val uid = result.user?.uid ?: throw Exception("Failed to get user ID.")

            val userProfile = User(
                userId = uid,
                name = name,
                email = email
            )

            fireStore.collection("users").document(uid).set(userProfile).await()
            firebaseAuth.signOut()
            Result.success(Unit)
        }catch (e : Exception)
        {
            Result.failure(e)
        }

    }
    fun signOut()
    {
        firebaseAuth.signOut()
    }
    suspend fun loginIn(email : String, password : String) : Result<Unit>
    {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email,password).await()
            Result.success(Unit)
        } catch (e : Exception)
        {
            Result.failure(e)
        }
    }

   suspend fun passwordResetLink(email: String) : Result<Unit>
    {
       return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception)
        {
            Result.failure(e)
        }
    }
    fun logout()
    {
        firebaseAuth.signOut()
    }
}