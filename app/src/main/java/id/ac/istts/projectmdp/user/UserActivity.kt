package id.ac.istts.projectmdp.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.ac.istts.projectmdp.R
import id.ac.istts.projectmdp.user.Adapter.PagerAdapter
import id.ac.istts.projectmdp.user.Fragment.UserHistoryFragment
import id.ac.istts.projectmdp.user.Fragment.UserHomeFragment
import id.ac.istts.projectmdp.user.Fragment.UserNotificationFragment
import id.ac.istts.projectmdp.user.Fragment.UserProfileFragment

class UserActivity : AppCompatActivity() {
//    lateinit var main: FrameLayout
//    lateinit var nav: BottomNavigationView
//    lateinit var userHomeFragment: UserHomeFragment
//    lateinit var userHistoryFragment: UserHistoryFragment
//    lateinit var userProfileFragment: UserProfileFragment
//    lateinit var userNotificationFragment: UserNotificationFragment
    private lateinit var mViewPager: ViewPager
    private lateinit var homeBtn:ImageButton
    private lateinit var historyBtn:ImageButton
    private lateinit var profileBtn:ImageButton
    private lateinit var notifBtn:ImageButton
    private lateinit var mPagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        mViewPager = findViewById(R.id.mViewPager)

        homeBtn = findViewById(R.id.homeBtn)
        historyBtn = findViewById(R.id.historyBtn)
        profileBtn = findViewById(R.id.profileBtn)
        notifBtn = findViewById(R.id.notifBtn)


        homeBtn.setOnClickListener {
            mViewPager.currentItem = 0
        }


        historyBtn.setOnClickListener {
            mViewPager.currentItem = 1
        }


        profileBtn.setOnClickListener {
            mViewPager.currentItem = 2
        }


        notifBtn.setOnClickListener {
            mViewPager.currentItem = 3
        }


        mPagerAdapter = PagerAdapter(supportFragmentManager)
        mViewPager.adapter = mPagerAdapter
        mViewPager.offscreenPageLimit = 4

        mViewPager.addOnAdapterChangeListener(object : ViewPager.OnPageChangeListener,
            ViewPager.OnAdapterChangeListener {

            override fun onPageSelected(position: Int) {
                changeTabs(position)
            }
            
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                TODO("Not yet implemented")
            }

            override fun onPageScrollStateChanged(state: Int) {
                TODO("Not yet implemented")
            }


            override fun onAdapterChanged(
                viewPager: ViewPager,
                oldAdapter: androidx.viewpager.widget.PagerAdapter?,
                newAdapter: androidx.viewpager.widget.PagerAdapter?
            ) {
                TODO("Not yet implemented")
            }


        })
        mViewPager.currentItem = 0
//        homeBtn.setImageResource(R.drawable.ic_baseline_home_24_focused)
    }
    private fun changeTabs(position: Int) {


        if (position == 0) {
            homeBtn.setImageResource(R.drawable.ic_baseline_home_24_focused)
            historyBtn.setImageResource(R.drawable.ic_baseline_history_24)
            profileBtn.setImageResource(R.drawable.ic_baseline_person_24)
            notifBtn.setImageResource(R.drawable.ic_baseline_notifications_24)
        }
        if (position == 1) {
            Toast.makeText(this@UserActivity, "cok", Toast.LENGTH_SHORT).show()
            homeBtn.setImageResource(R.drawable.ic_baseline_home_24)
            historyBtn.setImageResource(R.drawable.ic_baseline_history_24_focused)
            profileBtn.setImageResource(R.drawable.ic_baseline_person_24)
            notifBtn.setImageResource(R.drawable.ic_baseline_notifications_24)
        }
        if (position == 2) {
            homeBtn.setImageResource(R.drawable.ic_baseline_home_24)
            historyBtn.setImageResource(R.drawable.ic_baseline_history_24)
            profileBtn.setImageResource(R.drawable.ic_baseline_person_focused)
            notifBtn.setImageResource(R.drawable.ic_baseline_notifications_24)
        }
        if (position == 3) {
            homeBtn.setImageResource(R.drawable.ic_baseline_home_24)
            historyBtn.setImageResource(R.drawable.ic_baseline_history_24)
            profileBtn.setImageResource(R.drawable.ic_baseline_person_24)
            notifBtn.setImageResource(R.drawable.ic_baseline_notifications_24_focused)
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
}