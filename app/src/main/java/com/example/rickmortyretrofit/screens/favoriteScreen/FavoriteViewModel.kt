package com.example.rickmortyretrofit.screens.favoriteScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmortyretrofit.model.Results
import com.example.rickmortyretrofit.network.WebRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel() {

    private val webRepo = WebRepository()
    private val idArray:Array<Int> = arrayOf()
    val idNumber = 123


    private val favoritePersonList: MutableStateFlow<List<Results>> =
        MutableStateFlow(mutableListOf())
    val favPerson = favoritePersonList.asStateFlow()

    init {
        loadFavoriteList()
    }

    private fun loadFavoriteList() {

        viewModelScope.launch {

            val product = webRepo.retrofit.getMultiCharacter(idNumber)
            favoritePersonList.value = product.results


        }




    }
//    fun changeLikeOnPerson(result:Results) {
//        val oldPersons = favPerson.value.toList()
//        oldPersons.forEach {
//            if (it.id == result.id)
//                it.isLike = !it.isLike
//
//        }
//
//        favoritePersonList.value = oldPersons
//    }


//    fun loadNextPage() {
//        viewModelScope.launch {
//            val newPage: MutableList<Results> = mutableListOf()
//            newPage.addAll(_persons.value)
//
//            val product = webRepo.retrofit.getPage(currentPage)
//            currentPage++
//
//            newPage.addAll(product.results)
//            _persons.value = newPage.toList()
//
//
//        }
//
//
//    }


}