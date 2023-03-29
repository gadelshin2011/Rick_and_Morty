package com.example.rickmortyretrofit.screens.favoriteScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmortyretrofit.R
import com.example.rickmortyretrofit.adapter.RcViewAdapter
import com.example.rickmortyretrofit.adapter.RcViewFavAdapter
import com.example.rickmortyretrofit.databinding.FragmentFavoriteBinding
import com.example.rickmortyretrofit.model.Results
import com.example.rickmortyretrofit.network.WebRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FavoriteFragment : Fragment() {
    lateinit var binding: FragmentFavoriteBinding
    private lateinit var recyclerView: RecyclerView
    lateinit var webRepo: WebRepository
    private val adapterRcF: RcViewFavAdapter = RcViewFavAdapter()
    private val viewModel: FavoriteViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container,false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListener()
    }

    private fun init() {
        recyclerView = binding.rcViewStart
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        webRepo = WebRepository()
        recyclerView.adapter = adapterRcF


        viewModel.favPerson.onEach {
            adapterRcF.setFavList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }
    private fun setListener(){
        binding.imBtnFavBack.setOnClickListener {
           findNavController().popBackStack()

        }
    }


    private fun selectItem(results: Results) {

    }

    private fun selectItemF(results: Results) {

    }






}