package pers.cxd.commonmodule.activities.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pers.cxd.commonmodule.R
import pers.cxd.commonmodule.databinding.FragmentFirstBinding
import pers.cxd.corelibrary.Singleton
import pers.cxd.corelibrary.base.UIComponent
import pers.cxd.corelibrary.base.UIComponentPlugins

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), UIComponent<FragmentFirstBinding> {

    companion object {
        private const val TAG = "CXD-FirstFragment"
    }

    private val mBindingSingleton = object : Singleton<FragmentFirstBinding>(){
        override fun create(): FragmentFirstBinding {
            return FragmentFirstBinding.inflate(layoutInflater)
        }
    }

    override val mViewBinding: FragmentFirstBinding
        get() = mBindingSingleton.getInstance()

    override fun getContext(): Context {
        val context = super.getContext()
        return context!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        UIComponentPlugins.initUIComponent(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return mViewBinding.root
    }

    override fun setup(savedInstanceState: Bundle?) {
        mViewBinding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        mViewBinding.textviewFirst.text = "hello! go next"
    }


}