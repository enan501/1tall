package safari.com.iltall.Data.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import safari.com.iltall.UI.Network.FeedFragment
import safari.com.iltall.UI.Network.FollowFragment
import safari.com.iltall.UI.Network.RankFragment

class TabAdapter(fm: FragmentManager, val num:Int): FragmentPagerAdapter(fm) {
//    override fun getItemPosition(p0: Any): Int {
//        return PagerAdapter.POSITION_NONE
//    }
    override fun getCount(): Int {
        return num
    }

    override fun getItem(position: Int): Fragment? {
        when(position){
            0->return FeedFragment()
            1->return FollowFragment()
            2->return RankFragment()
        }
        return null
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "피드"
            1 -> "팔로우"
            else -> "랭킹"
        }
    }


}