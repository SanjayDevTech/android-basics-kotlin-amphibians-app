/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.amphibians.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amphibians.network.Amphibian
import com.example.amphibians.network.AmphibianApi
import kotlinx.coroutines.launch

enum class AmphibianApiStatus {LOADING, ERROR, DONE}

class AmphibianViewModel : ViewModel() {

    private val _status: MutableLiveData<AmphibianApiStatus> = MutableLiveData()
    val status: LiveData<AmphibianApiStatus> = _status

    private val _listData: MutableLiveData<List<Amphibian>> = MutableLiveData()
    val listData: LiveData<List<Amphibian>> = _listData

    lateinit var amphibian: Amphibian
    private set

    fun getList() {
        _status.value = AmphibianApiStatus.LOADING
        viewModelScope.launch {
            try {
                _listData.value = AmphibianApi.retrofitService.getList()
                _status.value = AmphibianApiStatus.DONE
            } catch (e: Exception) {
                _status.value = AmphibianApiStatus.ERROR
            }
        }
    }

    fun onAmphibianClicked(amphibian: Amphibian) {
        this.amphibian = amphibian
    }
}
