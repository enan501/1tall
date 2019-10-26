package safari.com.iltall.Data.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import safari.com.iltall.UI.Quest.QuestFragment

class QuestAdapter(fm: FragmentManager, val num:Int, val content:ArrayList<String>): FragmentPagerAdapter(fm) {
//    override fun getItemPosition(p0: Any): Int {
//        return PagerAdapter.POSITION_NONE
//    }
    override fun getCount(): Int {
        return num
    }

    override fun getItem(position: Int): Fragment? {
        for(i in 0..num){
            if(position == i){
                return QuestFragment()
            }
        }
        return null
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return ""
    }

}