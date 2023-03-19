package com.example.rickmortyretrofit.screens.startscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmortyretrofit.model.Result
import com.example.rickmortyretrofit.network.WebRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StartFragmentViewModel(): ViewModel() {

    private val webRepo = WebRepository()


    val persons: MutableStateFlow<List<com.example.rickmortyretrofit.model.Result>> = MutableStateFlow(emptyList())
    init {
        requestAllList()
    }

    fun changeLikeOnPerson(result: com.example.rickmortyretrofit.model.Result){
        val oldPersons = persons.value.toList()
        oldPersons.forEach {
            if (it.id == result.id)
                it.isLike = !it.isLike
        }

        persons.value = oldPersons
    }


    private fun requestAllList() {
        viewModelScope.launch {
            val product = webRepo.retrofit.getCharacter()
            persons.value = product.results
        }
    }
}