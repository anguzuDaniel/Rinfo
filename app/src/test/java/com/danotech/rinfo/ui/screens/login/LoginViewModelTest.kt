package com.danotech.rinfo.ui.screens.login

import com.danotech.rinfo.model.service.AccountService
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.model.service.impl.DataStoreUtil
import com.danotech.rinfo.model.service.impl.EmailValidator
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.anyString
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    @get:Rule
    var rule = MockitoJUnit.rule()!!

    @Mock
    private lateinit var dataStoreUtil: DataStoreUtil

    @Mock
    private lateinit var accountService: AccountService

    @Mock
    private lateinit var logService: LogService

    @InjectMocks
    private lateinit var viewModel: LoginViewModel

    @Mock
    private lateinit var emailValidator: EmailValidator

    @Before
    fun setUp() {
        viewModel = LoginViewModel(dataStoreUtil, accountService, emailValidator, logService)
    }

    @Test
    fun test_viewModel_initialization() {
        val initialState = viewModel.uiState.value
        assertEquals("", initialState.email)
        assertEquals("", initialState.password)
        assertFalse(initialState.isSignInLoading)
        assertFalse(initialState.isSignInSuccess)
        assertEquals("", initialState.message)
        assertFalse(initialState.hasMessage)
    }

    @Test
    fun test_on_email_change() {
        viewModel.onEmailChange("test@example.com")
        assertEquals("test@example.com", viewModel.uiState.value.email)
    }

    @Test
    fun test_on_password_change() {
        viewModel.onPasswordChange("test")
        assertEquals("test", viewModel.uiState.value.password)
    }

    @Test
    fun test_signIn_click_with_error() = runTest {
        // Mock the email validation function
        `when`(viewModel.isEmailValid("email")).thenReturn(true)

        viewModel.onEmailChange("email")
        viewModel.onPasswordChange("password")

        viewModel.signInClick { _, _ -> }

        assertFalse(viewModel.uiState.value.isSignInLoading)
        assertTrue(viewModel.uiState.value.hasMessage)
        assertFalse(viewModel.uiState.value.isSignInSuccess)
        assertTrue(viewModel.uiState.value.message.isNotEmpty())
    }
}