package com.example.room_basic_test

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
// 여기 클래스는 그냥 정해진 양식을 인터넷에서 가져온거임
// Event Wrapper를 사용하고 싶다면 이걸 그냥 복사해서 어디던 사용하면됨
// Event Wrapper 사용 이유: 불필요하게 liveData를 여러번 구독(observe)하는 것을 막기 위해 사용
// 필요 없는 부분에서 lifecycle 변화로 인해(화면 회전 or 스낵바 등) 그 때마다 observe를 하게 된다면
// 의미 없이 view의 모든 데이터를 다시 읽어오거나 하는 행동을 하게 된다
// Event Wrapper: 명시적으로 선언하여 어느 상황에서 observe를 할지 지정할 수 있다
// 참조: https://woovictory.github.io/2020/07/08/Android-SingleLiveEventToEventWrapper/
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    // 중복적인 인벤트 처리를 방지할 수 있다
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    // 이벤트 처리 여부에 상관없이 값을 반환
    fun peekContent(): T = content
}
