package com.danotech.rinfo.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.ui.ThemeViewModel
import com.danotech.rinfo.ui.components.BusinessCard
import com.danotech.rinfo.ui.components.BusinessCardShimmer
import com.danotech.rinfo.ui.components.FilterRow
import com.danotech.rinfo.ui.components.SectionHeading

/**
 * HomePageContent
 * @param modifier Modifier
 *
 * shows the home page content
 *
 * contains all the main content of the home page
 * at the top is the search bar
 * then the category options
 * then the show options
 * then the filter row
 * then the reviews
 * @see BusinessCard
 */
@Composable
fun HomeContent(
    viewModel: HomesScreenViewModel,
    themeViewModel: ThemeViewModel,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
    onReviewCardClicked: (Business) -> Unit = {},
    onCategoryClicked: () -> Unit = {},
) {
    val layout = themeViewModel.cardLayoutState.value
    val index = layout.tabTypeIndex

    var tabIndex by remember {
        mutableStateOf(index)
    }
    viewModel.showReviews()
    val businesses = viewModel.businessFlow.collectAsState(initial = emptyList()).value
    val listState = rememberLazyListState()

    Surface(
        modifier = Modifier.nestedScroll(rememberNestedScrollInteropConnection()),
    ) {
        LazyColumn(
            modifier = modifier
                .height(LocalConfiguration.current.screenHeightDp.dp)
                .padding(bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(top = innerPadding.calculateTopPadding()),
            state = listState,
        ) {
            item {
                FilterBusinessRow(
                    paddingHorizontal = R.dimen.body_padding_small,
                    paddingStart = R.dimen.body_padding
                )
            }

            item {
                ChangeLayoutAction(
                    onClick = {
                        if (tabIndex >= 2) {
                            tabIndex = 0
                            themeViewModel.saveCardLayout(tabIndex)
                        } else {
                            tabIndex += 1
                            themeViewModel.saveCardLayout(tabIndex)
                        }
                    }
                )
            }

            item {
                SectionHeading(
                    text = R.string.recommendations,
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.body_padding))
                )
            }

            item {
                BusinessSnippetRow(
                    businessList = businesses,
                    paddingHorizontal = R.dimen.body_padding_small,
                    paddingStart = R.dimen.body_padding
                )
            }

            item {
                FilterRow(paddingHorizontal = R.dimen.body_padding)
            }

            when (tabIndex) {
                0 -> {
                    items(businesses) { business ->
                        BusinessCardShimmer(
                            isLoading = viewModel.uiState.value.isLoading
                        ) {
                            BusinessCard(
                                business = business,
                                onReviewCardClicked = onReviewCardClicked,
                                paddingHorizontal = R.dimen.body_padding,
                            )
                        }
                    }
                }

                1 -> {
                    gridItems(
                        data = businesses,
                        columnCount = 2,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) { business ->
                        BusinessGridCard(
                            business = business,
                            imageLoading = viewModel.uiState.value.imageLoading,
                            onReviewCardClicked = { onReviewCardClicked(business) }
                        )
                    }
                }

                else -> {
                    items(businesses) { business ->
                        BusinessCardFullScreenDisplay(
                            business = business,
                            imageLoading = viewModel.uiState.value.imageLoading,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            onReviewCardClicked = { onReviewCardClicked(business) }
                        )
                    }
                }
            }

            if (tabIndex == 2) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}