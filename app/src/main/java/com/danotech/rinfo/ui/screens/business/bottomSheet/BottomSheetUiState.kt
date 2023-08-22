package com.danotech.rinfo.ui.screens.business.bottomSheet

/**
 * Represents the state of the bottom sheet
 * @param reviewedBusinessId: the id of the business being reviewed
 * @param reviewerUserId: the id of the user who is reviewing
 * @param rating: the rating of the business
 * @param title: the title of the review
 * @param review: the comment of the review
 * @param isPositive: whether the review is positive or not
 * @param submitButtonEnabled: whether the submit button is enabled or not
 * @return the state of the bottom sheet
 */
data class BottomSheetUiState(
    val reviewedBusinessId: String = "",
    val reviewerUserId: String = "",
    val rating: Int = 0,
    val title: String = "",
    val review: String = "",
    val isPositive: Boolean = false,
    var submitButtonEnabled: Boolean = false
)
