package id.ac.istts.projectmdp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserActivity : AppCompatActivity() {
    lateinit var main: FrameLayout
    lateinit var nav: BottomNavigationView
    lateinit var userHomeFragment: UserHomeFragment
    lateinit var userHistoryFragment: UserHistoryFragment
    lateinit var userProfileFragment: UserProfileFragment
    lateinit var userNotificationFragment: UserNotificationFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        main = findViewById(R.id.user_main)
        nav = findViewById(R.id.user_nav)
        userHomeFragment = UserHomeFragment()
        userHistoryFragment = UserHistoryFragment()
        userProfileFragment = UserProfileFragment()
        userNotificationFragment = UserNotificationFragment()

//        swapFragment(userHomeFragment)
        // TODO: JANGAN LUPA DIHAPUS, HANYA UNTUK COBA MAPS
        val mapsFragment = MapsFragment()
        swapFragment(mapsFragment)

        nav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.user_menuHome -> {
                    swapFragment(userHomeFragment)
                }
                R.id.user_menuHistory -> {
                    swapFragment(userHistoryFragment)
                }
                R.id.user_menuProfile -> {
                    swapFragment(userProfileFragment)
                }
                R.id.user_menuNotification -> {
                    swapFragment(userNotificationFragment)
                }
            }
            return@setOnItemSelectedListener super.onOptionsItemSelected(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun swapFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.user_main, fragment).setReorderingAllowed(true).commit()
    }
}