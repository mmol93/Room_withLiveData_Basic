package com.example.room_basic_test

import androidx.lifecycle.*
import com.example.room_basic_test.database.SubscribeRepository
import com.example.room_basic_test.database.Subscriber
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// SubscribeRepository에서 정의한 suspend 함수들을 해당 viewModel과 묶어서 실행하게 하기
class SubscribeViewModel(private val repository: SubscribeRepository) : ViewModel() {
    // true: 업데이트 또는 삭제
    // false: 삽입 또는 전체 삭제
    private var isUpdateOrDelete = false
    // 클릭된 아이템에 대한 정보를 가져오기 위해서 필요함
    private lateinit var subscriberToUpdateOrDelete : Subscriber

    // 입력 값에 대한 liveData 설정
    val inputName = MutableLiveData<String?>()
    val inputEmail = MutableLiveData<String?>()

    // 데이터 선택에 따른 버튼 텍스트의 liveData 설정
    val saveUpdateButtonText = MutableLiveData<String>()
    val deleteDeleteAllButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message : LiveData<Event<String>>
        get() = statusMessage

    init {
        saveUpdateButtonText.value = "Save"
        deleteDeleteAllButtonText.value = "Delete All"
    }

    // Room 데이터 베이스에 들어있는 모든 값을 가져온다
    fun saveSubscribers() = liveData {
        // collect: Flow 인터페이스에서 값을 가져오는 함수
        repository.subscriber.collect {
            emit(it)
        }
    }

    // insert 기능 설정
    // viewModelScope를 사용하는 이유
    // viewModelScope의 경우 해당 viewModel Class에 종속되어
    // 해당 viewModel이 삭제되면 viewModelScope도 같이 사라진다
    private fun insert(subscriber: Subscriber){
        viewModelScope.launch {
            repository.insert(subscriber)
            statusMessage.value = Event("Subscriber Insert Successfully")
        }
    }

    fun interOrUpdate(){
        if (isUpdateOrDelete){
            subscriberToUpdateOrDelete.name = inputName.value!!
            subscriberToUpdateOrDelete.email = inputEmail.value!!
            update(subscriberToUpdateOrDelete)
        }else{
            val name = inputName.value!!
            val email = inputEmail.value!!

            insert(Subscriber(0, name, email))
            inputName.value = null
            inputEmail.value = null
        }
    }

    // 내용을 업데이트 한다
    // 입력칸에 있는 텍스트는 모두 삭제한다
    // 매개변수로 받은 객체를 subscriberToUpdateOrDelete에 다시 넣는다
    // 버튼의 텍스트 또한 init과 동일하게 세팅한다 = 원래대로 되돌린다
    fun update(subscriber: Subscriber){
        viewModelScope.launch {
            // 받은 값으로 데이터베이스를 업데이트 한다
            repository.update(subscriber)
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            subscriberToUpdateOrDelete = subscriber
            saveUpdateButtonText.value ="Save"
            deleteDeleteAllButtonText.value = "Delete All"
            statusMessage.value = Event("Subscriber Update Successfully")
        }
    }

    fun deleteOrClear(){
        if (isUpdateOrDelete){
            delete(subscriberToUpdateOrDelete)
        }else{
            deleteAll()
        }
    }

    fun delete(subscriber: Subscriber){
        viewModelScope.launch {
            repository.delete(subscriber)
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            subscriberToUpdateOrDelete = subscriber
            saveUpdateButtonText.value = "Save"
            deleteDeleteAllButtonText.value = "Delete all"
            statusMessage.value = Event("Subscriber Delete Successfully")
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            repository.deleteAll()
            statusMessage.value = Event("Subscriber Clear Successfully")
        }
    }

    // subscriber에서 대항 데이터가 선택되게 한다
    // isUpdateOrDelete 트리거가 on이 되게 한다
    fun initUpdateAndDelete(subscriber: Subscriber){
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        // Adapter에서 받아온 데이터를 지정한다
        subscriberToUpdateOrDelete = subscriber
        saveUpdateButtonText.value = "update"
        deleteDeleteAllButtonText.value = "delete"
    }
}