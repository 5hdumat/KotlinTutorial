/**
 * 지연 계산 프로퍼티와 위임 객체
 *
 * [코틀린의 위임 객체]
 * 처리에 필요한 데이터를 모아 유지하면서 읽기와 쓰기를 처리하는 위임 객체를 통해 프로퍼티를 구현하게 해주는 위임 프로퍼티 기능의 특별한 경우를 알아보자.
 * 위임 객체는 by 라는 키워드 뒤에 위치하며 lazy {} 는 lazy라는 표준 라이브러리 함수에 람다를 넘기는 식일 뿐임을 참고하자.
 *
 * 코틀린이 기본적으로 제공하는 몇 가지 위임 객체가 있다.
 * - 지연 계산을 활성화 하는 lazy 외에도,
 * - 프로퍼티를 읽거나 쓸 때마다 리스너에게 통지해주는 위임 객체나
 * - 프로퍼티 값을 필드에 저장하는 대신 맵에 저장하는 위임 객체 등이 있다.
 *
 * 추가 적으로 lateinit(늦은 초기화)와 달리 lazy 프로퍼티는 가변 프로퍼티가 아니다. lazy 프로퍼티는 일단 초기화된 다음에는 변경되지 않는다.
 * 하여 가변 프로퍼티로 선언하면 오류가 발생한다.또한 lazy 프로퍼티는 thread-safe 하다. 즉 다중 스레드 환경에서 값을 한 스레드 안에서만 계산하기 때문에
 * lazy 프로퍼티에 접근하는 모든 스레드는 같은 값을 얻는다.
 *
 * 코틀린 1.1 부터는 함수 본문에서 lazy 위임 객체를 통한 지역 변수를 정의할 수 있다.
 *
 * fun main() {
 *   val data by lazy { }
 *   println(data)
 * }
 */
import java.io.File

/**
 * 다음과 같이 프로퍼티를 정의할 경우
   val text = File("data.txt").readText()
 * 프로그램이 시작될 때 바로 파일을 읽는다. 하지만 게터를 사용한 프로퍼티를 사용해 다음과 같이 저장하면
   val text get() = File("data.txt").readText()
 * 매번 필드를 참조할 때 마다 파일을 매번 읽어온다. 필요하면 프로퍼티 타입을 명시할 수 있다.
   val text: String by lazy { File("data.txt").readText() }
 */

// text 프로퍼티를 lazy로 정의했다. lazy 다음에 오는 블록 안에는 프로퍼티를 초기화하는 코드를 지정한다.
// main() 함수에서 사용자가 적절한 명령으로 프로퍼티 값을 읽기 전까지, 프로그램은 lazy 프로퍼티의 값을 계산하지 않는다.
// 초기화 된 이후 프로퍼티의 값은 필드에 저장되고, 그 이후로는 프로퍼티의 값을 읽을 떄 마다 저장된 값을 참조한다.
val text by lazy {
    File("data.txt").readText()
}

fun main() {
    val data by lazy { readLine() }


    if (data != null) {
//        println("length: ${data.length}") // Error 위임 프로퍼티에는 스마트 캐스트를 사용할 수 없다.
    }

    while (true) {
        when (val command = readLine() ?: return) {
            "print data" -> println(text)
            "exit" -> return
        }
    }
}