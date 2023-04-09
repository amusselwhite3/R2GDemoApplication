package com.austin.rooms2godemoapp

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.austin.rooms2godemoapp.data.Email
import com.austin.rooms2godemoapp.data.EmailListNetworkFactory
import com.austin.rooms2godemoapp.data.EmailListService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class SharedEmailViewModel : ViewModel() {
    companion object {
        private const val ERROR_NO_ITEMS = "You do not have any current orders, please try again later."
        private const val GENERIC_ERROR_TEXT = "Sorry, an error occurred, please try again."
        private const val INVALID_EMAIL = "Please enter a valid email address"
    }

    // TODO: Implement the ViewModel
    val isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val success: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(true)
    }

    val errorText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val emailData: MutableLiveData<List<Email>> by lazy {
        MutableLiveData<List<Email>>(listOf())
    }

    fun getEmailData(email: String){
        isLoading.postValue(true)
        val emailApi = EmailListNetworkFactory.getInstance().create(EmailListService::class.java)
        if (!isValidEmail(email)){
            errorText.postValue(INVALID_EMAIL)
            success.postValue(false)
            isLoading.postValue(false)
            return
        }
        viewModelScope.launch(Dispatchers.IO){
            val result = emailApi.getEmail(email)
            isLoading.postValue(false)
            if (result.isSuccessful){
                var emailList = result.body()
                if (emailList?.isEmpty() == true){
                    errorText.postValue(ERROR_NO_ITEMS)
                    success.postValue(false)
                    return@launch
                }
                emailList = emailList?.sortedByDescending {
                    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
                        .parse(it.date.replace("Z", "+0000"))
                    date
                }
                errorText.postValue("")
                emailData.postValue(emailList)
                success.postValue(true)
            } else{
                println("Network error: " + result.errorBody() + result.code())
                errorText.postValue(GENERIC_ERROR_TEXT)
                success.postValue(false)
            }
        }
    }

    fun clearData(){
        emailData.postValue(listOf())
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}