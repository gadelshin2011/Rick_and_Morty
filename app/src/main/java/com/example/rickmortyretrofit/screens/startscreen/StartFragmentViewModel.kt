package com.example.rickmortyretrofit.screens.startscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmortyretrofit.APP
import com.example.rickmortyretrofit.R
import com.example.rickmortyretrofit.adapter.RcViewAdapter
import com.example.rickmortyretrofit.databinding.FragmentStartBinding
import com.example.rickmortyretrofit.network.InterfaceApi
import com.example.rickmortyretrofit.network.WebRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StartFragmentViewModel(application: Application): AndroidViewModel(application) {


//
//
//    fun requestAll(){
//
//        CoroutineScope(Dispatchers.IO).launch {
//            val product = webRepo.retrofit.getCharacter()
//
//            withContext(Dispatchers.Main) {
//                adapter.setList(product.results)
//
//
//            }
//        }
//    }
//
//    fun requestRcScroll(number:Int){
//        var number2 = 0
//        CoroutineScope(Dispatchers.IO).launch {
//            number2 = number + 1
//            val page = webRepo.retrofit.getPage(number2)
//
//            withContext(Dispatchers.Main) {
//                adapter.addList(page.results)
//
//
//            }
//        }
//
//    }

}