package com.example.rickmortyretrofit.screens.startscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
import com.example.rickmortyretrofit.R
import com.example.rickmortyretrofit.adapter.RcViewAdapter
import com.example.rickmortyretrofit.databinding.FragmentStartBinding
import com.example.rickmortyretrofit.model.Results
import com.example.rickmortyretrofit.network.WebRepository
import com.example.rickmortyretrofit.screens.InfoPersonFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StartFragment : Fragment() {
    lateinit var binding: FragmentStartBinding
    private lateinit var recyclerView: RecyclerView
    val adapterRc: RcViewAdapter = RcViewAdapter(clickOnItem = ::selectItem)
    lateinit var webRepo: WebRepository
    var number = 1
    private val viewModel: StartFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // binding.rcViewStart.itemAnimator = null
        init()
        binding.rcViewStart.adapter?.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun init() {
        initialization()
        showData()
        recyclerScrollListener()
        setListener()
        searchView()
    }

    private fun showData() {

        viewModel.persons.onEach {
            adapterRc.setList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }


    private fun initialization() {
        binding.searchView.visibility = View.GONE
        recyclerView = binding.rcViewStart
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        webRepo = WebRepository()
        recyclerView.adapter = adapterRc
    }

    private fun selectItem(result: com.example.rickmortyretrofit.model.Results) {
        findNavController().navigate(
            R.id.action_startFragment_to_infoPersonFragment,
            InfoPersonFragment.getBundle(result),
        )
    }


    private fun recyclerScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                if (!recyclerView.canScrollVertically(1)) {
//                    number++
//                    viewModel.loadPage(number)
//                }
//            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == SCROLL_STATE_DRAGGING) {
                    number++
                    Toast.makeText(requireContext(),"Page $number", Toast.LENGTH_SHORT).show()
                    viewModel.loadNextPage()

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

    private fun searchView() {
        binding.searchView.setOnQueryTextListener(object :
            OnQueryTextListener,
            SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                CoroutineScope(Dispatchers.IO).launch {
                    val product = newText?.let { webRepo.retrofit.getSearchByName(it) }

                    withContext(Dispatchers.Main) {
                        product?.results?.let { adapterRc.setList(it as MutableList<Results>) }
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
}
