package com.example.catgallery.presentation

import android.health.connect.datatypes.DistanceRecord
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    fun fetchCatList(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.fetchCatList()
            if (result != null) {
                withContext(Dispatchers.Main){
                    _catList.addAll(result)
                }
            }
        }
    }
}