package com.beem.schoolmessageapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.beem.schoolmessageapp.repository.Repository

class BalanceViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
          return BalanceViewModel(repository) as T
    }
}