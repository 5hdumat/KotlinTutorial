import java.io.File

/**
 * 늦은 초기화
 *
 * 늦은 초기화에 대한 설명이 어려운데, 복잡하게 생각 할 것 없이 말 그대로 객체 초기화를 늦게 하는 것을 말한다.
 */

class Content {
    var text : String? = null

    /**
     * loadFile()은 다른 곳에서 호출되며, 어떤 파일의 내용을 모두 문자열로 읽어온다고 가정하자.
     * 이 예제의 단점은 실제 값이 항상 사용전에 초기화돼야 하는 상황(절대 널이 될 수 없는 값)이라는 사실을 알고 있음에도 불구하고
     * 늘 프로퍼티의 널 가능성을 처리해야 한다는 점이다.
     */
    fun loadFile(file: File) {
        text = file.readText()
    }
}

/**
 * 코틀린은 불필요하게 프로퍼티의 널 가능성을 처리해야 하는 패턴을 지원하는 lateinit 키워드를 제공한다.
 * lateinit 을 붙일 수 있는 조건
 * - 항상 var(가변 프로퍼티) 여야 한다.
 * - 프로퍼티의 타입이 null 혹은 Primitive 타입(Int, Boolean 등)이 아니여야한다. (primitive 타입은 null 값을 허용하지 않는다.)
 * - lateinit 키워드를 사용하면 초기화를 즉시 할 수 없다.
 */

class ContentBetter {
    /**
     * lateinit 프로퍼티는 값을 읽으려고 시도할 때 프로퍼티가 초기화 됐는지 검사한 후 초기화 되지 않은 경우 UninitializedPropertyAccessException 을 던진다.
     * 1.4 버전 이후부터 최상위 프로퍼티에서도 lateinit이 사용 가능하다.
     */
    lateinit var text: String

    fun loadFile(file: File) {
        text = file.readText()
    }
}


