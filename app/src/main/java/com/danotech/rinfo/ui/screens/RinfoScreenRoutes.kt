package com.danotech.rinfo.ui.screens

import androidx.annotation.StringRes
import com.danotech.rinfo.R

/**
 * Has all the possible screens in the app.
 * @param title the title of the screen
 * @param route the route of the screen
 * @param icon the icon of the screen
 * @param isBottomBarVisible whether the bottom bar should be visible or not
 */
enum class RInfoScreen(@StringRes val title: Int) {
    // the start page is the home page
    Home(title = R.string.home),
    Favourites(title = R.string.favorites),
    Notification(title = R.string.notifications),
    Settings(title = R.string.settings),
    Account(title = R.string.account),
    BusinessAccount(title = R.string.business),
    EditAccount(title = R.string.edit_account),
    Business(title = R.string.review),
    Reviews(title = R.string.reviews),
    Search(title = R.string.search),
    Login(title = R.string.login),
    Register(title = R.string.create_account),
    Map(title = R.string.direction),
    ReviewForm(title = R.string.review),
    SelectedCategory(title = R.string.categories),
    Categories(title = R.string.more_categories),
}
