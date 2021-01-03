package ipvc.estg.trabalhofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreenActivity : AppCompatActivity() {

    private val splashTime = 3000L
    private lateinit var myHandler : Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        myHandler = Handler()
        myHandler.postDelayed({
            goToMainActivity()
        },splashTime)
    }

    private fun goToMainActivity(){
        startActivity(Intent(applicationContext,MainActivity::class.java))
        finish()
    }
}