package safari.com.iltall.UI.Quest


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import safari.com.iltall.R

/**
 * A simple [Fragment] subclass.
 */
class QuestFragment() : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quest, container, false)
    }


}
