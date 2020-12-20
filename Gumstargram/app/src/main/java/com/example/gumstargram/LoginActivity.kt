package com.example.gumstargram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null
    private var googleSignInClient:GoogleSignInClient?=null
    private var GOOGLE_LOGIN_CODE=9001
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        email_login_button.setOnClickListener {
            sigininAndSignup()
        }
        google_sign_in_button.setOnClickListener {
            //First Step
            googleLogin()
        }

        var gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient=GoogleSignIn.getClient(this,gso)
    }
    override fun onStart(){
        super.onStart()
        moveMainPage(auth?.currentUser)

    }
    private fun googleLogin(){
        var signInIntent=googleSignInClient?.signInIntent
        startActivityForResult(signInIntent,GOOGLE_LOGIN_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==GOOGLE_LOGIN_CODE){
            var result= Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if(result!!.isSuccess){
                var account=result.signInAccount
                //Second step
                firebaseAuthWithGoogle(account)
            }
        }
    }
    private fun firebaseAuthWithGoogle(account:GoogleSignInAccount?){
        var credential=GoogleAuthProvider.getCredential((account?.idToken),null)
        auth?.signInWithCredential(credential)
                //third step
            ?.addOnCompleteListener {
                task->
            if(task.isSuccessful){
                //id와 pw가 맞았을때
                moveMainPage((task.result?.user))
            }

            else{//실패했을때
                Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun sigininAndSignup() {
        auth?.createUserWithEmailAndPassword(email_edittext.text.toString(),password_editttext.text.toString())?.addOnCompleteListener {
            task->
            if(task.isSuccessful){
                //아이디가 만들어졌을때
                moveMainPage((task.result?.user))
            }
            else if(!(task.exception?.message.isNullOrEmpty())){
                //로그인 에러가 났을때
                Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
            }
            else{//로그인하는곳으로
                signinEmail()
            }
        }


    }
    private fun signinEmail(){
        auth?.signInWithEmailAndPassword(email_edittext.text.toString(),password_editttext.text.toString())?.addOnCompleteListener {
                task->
            if(task.isSuccessful){
                //id와 pw가 맞았을때
                moveMainPage((task.result?.user))
            }

            else{//실패했을때
                Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun moveMainPage(user:FirebaseUser?){
        if(user!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}