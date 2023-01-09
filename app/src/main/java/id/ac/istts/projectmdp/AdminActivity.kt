package id.ac.istts.projectmdp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminActivity : AppCompatActivity() {
    lateinit var main: FrameLayout
    lateinit var nav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        main = findViewById(R.id.puskesmas_main)
        nav = findViewById(R.id.puskesmas_nav)

        nav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.admin_menuHome -> {
                    // TODO: Buat fragment trs nti swapFragment(variabel_fragment_yang_dibuat)
                }
            }
            return@setOnItemSelectedListener true
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
        supportFragmentManager.beginTransaction().replace(R.id.puskesmas_main, fragment).setReorderingAllowed(true).commit()
    }
}