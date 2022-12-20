package id.ac.istts.projectmdp.hospital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.ac.istts.projectmdp.R

class PuskesmasActivity : AppCompatActivity() {
    lateinit var main: FrameLayout
    lateinit var nav: BottomNavigationView
    lateinit var puskesmasHomeFragment: PuskesmasHomeFragment
    lateinit var puskesmasHistoryFragment: PuskesmasHistoryFragment
    lateinit var puskesmasProfileFragment: PuskesmasProfileFragment
    lateinit var puskesmasMapsFragment: PuskesmasMapsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puskesmas)

        main = findViewById(R.id.puskesmas_main)
        nav = findViewById(R.id.puskesmas_nav)
        puskesmasHomeFragment = PuskesmasHomeFragment()
        puskesmasHistoryFragment = PuskesmasHistoryFragment()
        puskesmasProfileFragment = PuskesmasProfileFragment()
        puskesmasMapsFragment = PuskesmasMapsFragment()

        swapFragment(puskesmasHomeFragment)

        nav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.puskesmas_menuHome -> {
                    swapFragment(puskesmasHomeFragment)
                }
                R.id.puskesmas_menuHistory -> {
                    swapFragment(puskesmasHistoryFragment)
                }
                R.id.puskesmas_menuProfile -> {
                    swapFragment(puskesmasProfileFragment)
                }
                R.id.puskesmas_menuMap -> {
                    swapFragment(puskesmasMapsFragment)
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

    private fun swapFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.puskesmas_main, fragment).setReorderingAllowed(true).commit()
    }
}