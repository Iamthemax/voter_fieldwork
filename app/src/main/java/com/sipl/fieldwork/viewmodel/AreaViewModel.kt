package com.sipl.fieldwork.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sipl.fieldwork.database.entity.AreaItem
import com.sipl.fieldwork.repository.AreaRepository
import com.sipl.fieldwork.utils.XConst
import kotlinx.coroutines.launch

class AreaViewModel(private val areaRepository: AreaRepository) : ViewModel() {



    suspend fun getAllAreas(): LiveData<List<AreaItem>> {
        return  areaRepository.getAllArea();
    }
    suspend fun getAllTalukas(id:String): LiveData<List<AreaItem>> {
        return areaRepository.getAllTalukas(id);
    }
    suspend fun getAreaByLocationId(id:String): AreaItem {
        return areaRepository.getAreaByLocationId(id);
    }
    fun insertAll(items: List<AreaItem>) {
        viewModelScope.launch {
            areaRepository.insertAll(items)
        }
    }
    fun insertInitialRecords(items: List<AreaItem>) {
        viewModelScope.launch {
            if(areaRepository.getAllArea().value.isNullOrEmpty())
            {
                areaRepository.insertInitialRecords(items)
            }else{
                Log.d(XConst.MYTAG,"Area is not empty")
            }

        }

    }
    suspend fun getAllArea(): LiveData<List<AreaItem>> {
        return areaRepository.getAllArea();
    }

}