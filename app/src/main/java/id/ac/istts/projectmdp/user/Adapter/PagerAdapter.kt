package id.ac.istts.projectmdp.user.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import id.ac.istts.projectmdp.user.Fragment.*

internal class PagerAdapter (fm:FragmentManager?):
    FragmentPagerAdapter(fm!!){
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                UserHomeFragment()
            }
            1 -> {
                UserHistoryFragment()
            }
            2 -> {
                UserProfileFragment()
            }
            3 -> {
                UserNotificationFragment()
            }
            4 -> {
                UserProfilePuskesmasFragment()
            }
            else-> UserHomeFragment()
        }
    }

    override fun getCount(): Int {
        return 5
    }
}