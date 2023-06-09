package com.example.rickmortyretrofit.screens.startscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.SearchView.OnCloseListener
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmortyretrofit.APP
import com.example.rickmortyretrofit.R
import com.example.rickmortyretrofit.adapter.RcViewAdapter
import com.example.rickmortyretrofit.databinding.FragmentStartBinding
import com.example.rickmortyretrofit.network.WebRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.rickmortyretrofit.model.Result

class StartFragment : Fragment() {
    lateinit var binding: FragmentStartBinding
    private lateinit var recyclerView: RecyclerView
    lateinit var adapter: RcViewAdapter
    lateinit var webRepo: WebRepository
    var number = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        // val viewModel = ViewModelProvider(this)[StartFragmentViewModel::class.java]
        initialization()
        requestAllList()
        recyclerScrollListener()
        setListener()
        searchView()
    }

    private fun initialization() {
        binding.searchView.visibility = View.GONE
        recyclerView = binding.rcViewStart
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        adapter = RcViewAdapter()
        webRepo = WebRepository()
        recyclerView.adapter = adapter

    }

    private fun requestAllList() {
        CoroutineScope(Dispatchers.IO).launch {
            val product = webRepo.retrofit.getCharacter()
            withContext(Dispatchers.Main) {
                adapter.setList(product.results)
            }
        }
    }

    private fun recyclerScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    CoroutineScope(Dispatchers.IO).launch {
                        number++
                        val page = webRepo.retrofit.getPage(number)

                        withContext(Dispatchers.Main) {
                            adapter.addList(page.results)


                        }
                    }
                    binding.searchView.visibility = View.GONE
                    binding.imageSearchButton.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setListener() {

        binding.imageSearchButton.setOnClickListener {
            binding.searchView.visibility = View.VISIBLE
            binding.imageSearchButton.visibility = View.GONE
        }

    }

    fun changeStateButton(){

    }

    private fun searchView() {
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener,
            SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                CoroutineScope(Dispatchers.IO).launch {
                    val product = newText?.let { webRepo.retrofit.getSearchByName(it) }

                    withContext(Dispatchers.Main) {
                        product?.results?.let { adapter.setList(it) }

                    }
                }

                return true
            }

        })
        binding.searchView.setOnCloseListener {
            binding.searchView.visibility = View.GONE
            binding.imageSearchButton.visibility = View.VISIBLE
            true
        }
    }

    companion object {
        fun clickNote(noteModel: Result) {
            val bundle = Bundle()
            bundle.putSerializable("note", noteModel)

            APP.navController.navigate(R.id.action_startFragment_to_infoPersonFragment, bundle)
        }
    }

}


