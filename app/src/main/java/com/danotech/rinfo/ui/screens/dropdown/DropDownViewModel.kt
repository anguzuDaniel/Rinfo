package com.danotech.rinfo.ui.screens.dropdown

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.danotech.rinfo.data.LocalOfflineDatabase
import com.danotech.rinfo.model.local.Category
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DropdownViewModel
@Inject
constructor(
    private val localOfflineDatabase: LocalOfflineDatabase,
    logService: LogService
) : RinfoViewModel(logService) {
    // MutableState for the list of visible items
    private val _visibleItems = mutableStateOf<List<Category>>(emptyList())
    val visibleItems: State<List<Category>> = _visibleItems
    // Define other properties and methods as needed...

    private val INITIAL_ITEM_COUNT = 10 // Define INITIAL_ITEM_COUNT with an appropriate value
    private val MORE_ITEM_COUNT = 5 // Define MORE_ITEM_COUNT with an appropriate value


    fun loadInitialItems() {
        viewModelScope.launch {
            try {
                // Load the initial set of items (e.g., the first N items) from your full list.
                // You can implement your own logic here based on your requirements.
                val initialItems =
                    localOfflineDatabase.categoryDao().getAllCategories().firstOrNull()

                // Check if initialItems is not null before updating the _visibleItems state
                initialItems?.let {
                    _visibleItems.value = it.take(INITIAL_ITEM_COUNT)
                }
            } catch (e: Exception) {
                // Handle any exceptions that might occur during the database query or processing
//                logError("Error loading initial items: ${e.message}")
            }
        }
    }

    fun loadMoreItems() {
        viewModelScope.launch {
            try {
                // Load additional items (e.g., the next N items) from your full list.
                // You can implement your own logic here based on your requirements.
                val listItems = localOfflineDatabase.categoryDao().getAllCategories().firstOrNull()

                val nextItems = listItems?.subList(
                    _visibleItems.value.size,
                    _visibleItems.value.size + MORE_ITEM_COUNT
                )

                // Append the next items to the _visibleItems state
                // Check if initialItems is not null before updating the _visibleItems state
                listItems?.let {
                    _visibleItems.value = nextItems!!.toList()
                }
            } catch (e: Exception) {
                // Handle any exceptions that might occur during the database query or processing
//                logError("Error loading initial items: ${e.message}")
            }
        }
    }
}