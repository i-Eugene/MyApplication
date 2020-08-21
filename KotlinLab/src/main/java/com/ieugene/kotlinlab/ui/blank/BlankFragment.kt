package com.ieugene.kotlinlab.ui.blank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ieugene.kotlinlab.R
import com.ieugene.kotlinlab.databinding.BlankFragmentBinding
import timber.log.Timber

class BlankFragment : Fragment() {

    companion object {
        fun newInstance() = BlankFragment()
    }

    private lateinit var viewModel: BlankViewModel
    private lateinit var binding: BlankFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.blank_fragment, container, false)
        viewModel.score.observe(viewLifecycleOwner, Observer {
            binding.textView.text = it.toString()
            Toast.makeText(context,"observer data $it",Toast.LENGTH_SHORT).show()
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.textView.setOnClickListener {
//            view.findNavController().navigate(R.id.homeFragment)
            viewModel.score.value?.inc()
            viewModel.onSkip()
            binding.apply {

//                invalidateAll()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BlankViewModel::class.java)
        Timber.i("Called ViewModelProvider.get${viewModel}")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}