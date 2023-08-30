package com.danotech.rinfo.ui.screens.category

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.danotech.rinfo.data.LocalOfflineDatabase
import com.danotech.rinfo.model.local.Category
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val localOfflineDatabase: LocalOfflineDatabase,
    logService: LogService
) : RinfoViewModel(logService) {
    val uiState = mutableStateOf(CategoryUiState())

    val categoriesSearched: StateFlow<CategoryUiState> = localOfflineDatabase
        .categoryDao()
        .getCategoryByName(uiState.value.searchedCategory)
        .filterNotNull()
        .map {
            CategoryUiState(categories = it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = CategoryUiState()
        )

    val allCategories: StateFlow<CategoryUiState> = localOfflineDatabase
        .categoryDao()
        .getAllCategories()
        .filterNotNull()
        .map {
            CategoryUiState(categories = it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = CategoryUiState()
        )

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        uiState.value = CategoryUiState()
    }

    fun onAddCategories() {
        val categories = listOf(
            Category(name = "Restaurants"),
            Category(name = "Cafes"),
            Category(name = "Hotels"),
            Category(name = "Bars"),
            Category(name = "Grocery Stores"),
            Category(name = "Clothing Stores"),
            Category(name = "Shoe Stores"),
            Category(name = "Electronics Stores"),
            Category(name = "Bookstores"),
            Category(name = "Pharmacies"),
            Category(name = "Bakeries"),
            Category(name = "Fitness Centers"),
            Category(name = "Beauty Salons"),
            Category(name = "Spas"),
            Category(name = "Car Dealerships"),
            Category(name = "Gas Stations"),
            Category(name = "Auto Repair Shops"),
            Category(name = "Banks"),
            Category(name = "ATMs"),
            Category(name = "Hospitals"),
            Category(name = "Clinics"),
            Category(name = "Dentists"),
            Category(name = "Optometrists"),
            Category(name = "Veterinarians"),
            Category(name = "Pet Stores"),
            Category(name = "Home Improvement Stores"),
            Category(name = "Furniture Stores"),
            Category(name = "Jewelry Stores"),
            Category(name = "Toy Stores"),
            Category(name = "Art Galleries"),
            Category(name = "Museums"),
            Category(name = "Movie Theaters"),
            Category(name = "Parks"),
            Category(name = "Zoos"),
            Category(name = "Libraries"),
            Category(name = "Schools"),
            Category(name = "Universities"),
            Category(name = "Stadiums"),
            Category(name = "Music Venues"),
            Category(name = "Nightclubs"),
            Category(name = "Airports"),
            Category(name = "Train Stations"),
            Category(name = "Bus Stops"),
            Category(name = "Taxi Services"),
            Category(name = "Car Rentals"),
            Category(name = "African Cuisine"),
            Category(name = "Art and Craft"),
            Category(name = "Fashion and Apparel"),
            Category(name = "Jewelry and Accessories"),
            Category(name = "Beauty and Cosmetics"),
            Category(name = "Textiles and Fabrics"),
            Category(name = "Furniture and Home Decor"),
            Category(name = "Music and Entertainment"),
            Category(name = "Handmade Products"),
            Category(name = "Spices and Herbs"),
            Category(name = "Traditional Medicine"),
            Category(name = "Tourism and Safaris"),
            Category(name = "Technology and Innovation"),
            Category(name = "Education and Training"),
            Category(name = "Agriculture and Farming"),
            Category(name = "Construction and Real Estate"),
            Category(name = "Transportation Services"),
            Category(name = "Language and Cultural Services"),
            Category(name = "Sports and Recreation"),
            Category(name = "Health and Wellness"),
            Category(name = "Financial Services"),
            Category(name = "Media and Communication"),
            Category(name = "Automotive Services")
            // Add more categories as needed
        )

        runBlocking {
            launch(Dispatchers.IO) {
                uiState.value = uiState.value.copy(isLoading = true)
                for (category in categories) {
                    localOfflineDatabase.categoryDao().insertCategory(category)
                }
            }.invokeOnCompletion {
                uiState.value = uiState.value.copy(isLoading = false)
            }
        }
    }

    fun getAllCategories() = localOfflineDatabase.categoryDao().getAllCategories()

    fun getCategoryByName() =
        localOfflineDatabase.categoryDao().getCategoryByName(uiState.value.searchedCategory)

    fun onSearchInput(newValue: String) {
        uiState.value = uiState.value.copy(searchedCategory = newValue)
    }

    fun onSearch(searchQuery: String) {
        uiState.value = uiState.value.copy(searchedCategory = searchQuery)
    }

    fun onClose() {
        uiState.value = uiState.value.copy(searchedCategory = "")
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

