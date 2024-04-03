package com.example.catgallery.presentation

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catgallery.domain.model.CatData
import com.example.catgallery.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    private var _catList = mutableStateListOf<CatData>()
    val catList = _catList
    private var _isError = mutableStateOf(false)
    val isError = _isError
    fun fetchCatList(){
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) { repository.fetchCatList() }
                    if (result != null) {
                        _isError.value = false
                        _catList.addAll(result)
                    } else
                        _isError.value = true
            }
            catch (e: Exception){
                _isError.value = true
            }
        }
    }
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            val isNetworkAvailable = activeNetworkInfo != null && activeNetworkInfo.isConnected
            _isError.value = !isNetworkAvailable
            return isNetworkAvailable
        }
        _isError.value = true
        return false
    }
}