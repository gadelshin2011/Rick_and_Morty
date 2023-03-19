package com.example.rickmortyretrofit.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.rickmortyretrofit.R
import com.example.rickmortyretrofit.databinding.FragmentInfoPersonBinding
import com.example.rickmortyretrofit.model.Result
import com.squareup.picasso.Picasso

class InfoPersonFragment : Fragment() {
    private lateinit var binding: FragmentInfoPersonBinding
    private lateinit var currentModel: Result
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoPersonBinding.inflate(inflater, container, false)
        currentModel = requireArguments().getParcelable<Result>("note")!!
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setListener()
        transferringDataFromTheModel()

    }

    private fun transferringDataFromTheModel() {
        Picasso.get().load(currentModel.image).into(binding.imageView)
        binding.tvName.text = currentModel.name
        binding.tvGender.text = currentModel.gender
        binding.tvStatus.text = currentModel.status
        binding.tvLocation.text = currentModel.location.name
    }

    private fun setListener() {
        binding.imBtnBack.setOnClickListener {
            findNavController().navigate(R.id.action_infoPersonFragment_to_startFragment)
        }
    }

    companion object {
        fun getBundle(noteModel: Result): Bundle {
            return bundleOf("note" to noteModel)
        }
    }


}