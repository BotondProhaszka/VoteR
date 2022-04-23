package hu.bme.aut.voter.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import hu.bme.aut.voter.R
import hu.bme.aut.voter.databinding.ActivityMainBinding
import hu.bme.aut.voter.model.GuestUser
import hu.bme.aut.voter.model.GoogleUser
import hu.bme.aut.voter.model.User
import java.lang.Exception
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val TAG_IS_ANONYMOUS_USER = "TAG_IS_ANONYMOUS_USER"
        const val TAG_DISPLAY_NAME = "TAG_DISPLAY_NAME"
        const val TAG_EMAIL = "TAG_EMAIL"
        const val TAG_PROFILE_PIC_URL = "TAG_PROFILE_PIC_URL"
        lateinit var user: User
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        initUser(navView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    private fun initUser(navView: NavigationView) {
        try {
            val header = navView.getHeaderView(0)
            when (intent.getBooleanExtra(TAG_IS_ANONYMOUS_USER, true)) {
                false -> {
                    val loggedInUser = GoogleUser(
                        intent.getStringExtra(TAG_DISPLAY_NAME).toString(),
                        intent.getStringExtra(TAG_EMAIL).toString(),
                        intent.getStringExtra(TAG_PROFILE_PIC_URL).toString()
                    )


                    downloadImage(header.findViewById(R.id.ivProfile), loggedInUser.picUri)
                    header.findViewById<TextView>(R.id.tvDisplayName).text =
                        loggedInUser.displayName
                    header.findViewById<TextView>(R.id.tvEmail).text = loggedInUser.email
                    header.findViewById<ImageView>(R.id.ivLogout).setOnClickListener { logout() }
                }
                else -> {
                    GuestUser(intent.getStringExtra(TAG_DISPLAY_NAME).toString())

                }
            }
            header.findViewById<ImageView>(R.id.ivLogout).setOnClickListener { logout() }
        } catch (e: Exception) {

        }

    }

    private fun logout() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        Firebase.auth.signOut()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
    }


    private fun downloadImage(imageView: ImageView, imageURL: String) {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap? = null

        executor.execute {
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
                handler.post {
                    imageView.setImageBitmap(image)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}