package com.example.rickmortyretrofit.screens.startscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmortyretrofit.model.Results
import com.example.rickmortyretrofit.network.WebRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StartFragmentViewModel() : ViewModel() {

    private val webRepo = WebRepository()
    var currentPage: Int = 1

    private val _persons: MutableStateFlow<List<Results>> = MutableStateFlow(
        emptyList()
    )
    val persons = _persons.asStateFlow()

    init {
        loadNextPage()

    }

//    fun changeLikeOnPerson(result: com.example.rickmortyretrofit.model.Results) {
//        val oldPersons = persons.value.toList()
//        oldPersons.forEach {
//            if (it.id == result.id)
//                it.isLike = !it.isLike
//        }
//
//        persons.value = oldPersons as MutableList<Results>
//    }


    //    private fun requestAllList() {
//
//        viewModelScope.launch {
//            val product = webRepo.retrofit.getCharacter()
//            persons.value = product.results as MutableList<Results>
//        }
//    }
    private fun requestAllList() {
        viewModelScope.launch {
            val product = webRepo.retrofit.getCharacter()
            _persons.value = product.results
        }


    }

     fun loadNextPage() {
        viewModelScope.launch {
            val newPage: MutableList<Results> = mutableListOf()
            newPage.addAll(_persons.value)

            val product = webRepo.retrofit.getPage(currentPage)
            currentPage++

            newPage.addAll(product.results)
            _persons.value = newPage.toList()
        }


    }
}