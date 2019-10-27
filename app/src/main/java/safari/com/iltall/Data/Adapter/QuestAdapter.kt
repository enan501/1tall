package safari.com.iltall.Data.Adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import safari.com.iltall.Data.Dataclass.QuestContent
import safari.com.iltall.UI.Quest.QuestFragment

class QuestAdapter(fm: FragmentManager, val content:ArrayList<QuestContent>): FragmentPagerAdapter(fm) {
//    override fun getItemPosition(p0: Any): Int {
//        return PagerAdapter.POSITION_NONE
//    }
    override fun getCount(): Int {
        return content.size
    }

    override fun getItem(position: Int): Fragment? {
        var qf =  QuestFragment()
        var bundle = Bundle(1)
        bundle.putSerializable("content", content[position])
        qf.arguments = bundle
        return qf
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return position.toString()
    }

}