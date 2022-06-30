/**
 * 코틀린은 기본적으로 null이 될 수 없는 타입을 디폴트로 한다.
 * (String과 같은 타입에 null 을 대입하면 오류가 발생한다.)
 *
 * String?과 같이 ?를 붙이면 null이 될 수 있는 타입(nullable type)으로 변경할 수 있다.
 * 단, nullable한 타입이 되는 순간 해당 타입의 메서드를 사용할 수 없다.
 */
fun isLetterString(s: String): Boolean {
//    if (s == null) return false

    if (s.isEmpty()) return false

    for (ch in s) {
        if (!ch.isLetter()) return false
    }

    return true
}

fun isBooleanString(s: String?) = s == "false" || s == "true"

/**
 * 스마트 캐스트
 * null에 대한 동등성 검사를 직접적으로 수행함으로써
 * 컴파일러가 값 타입을 세분화 함으로써 널이 될 수 있는 값을 널이 될 수 없는 값으로 캐스트 한다.
 *
 * 다양하게 활용 가능하다.
 */
fun describeNumber(n: Int?) = when (n) {
    null -> "null"

    // 위에서 널에 대한 동등성 검사를 직접적으로 체크했기에 밑에 있는 가지의 n은 null이 될 수 없다.
    in 0 .. 10 -> "0 .. 10"
    in 11 .. 100 -> "11 .. 100"
    in 101 .. 200 -> "101 .. 200"
    else -> "out of range"
}

// 스마트캐스트를 사용하려면 값을 검사하는 지점과 사용하는 지점 변하지 않는다고 컴파일러가 확신할 수 있어야 한다.
fun smartCastTest() {
    var s = readLine()

    if (s != null) {
        s = readLine()

//        println(s.length) // Error
    }
}

// s가 null이 아니면 && 뒤의 s는 String으로 캐스팅된다.
fun isSingleChar(s: String?) = s != null && s.length == 1

/**
 * 널 아님 단언 연산자 (!!)
 *
 * 널 아님 단언 연산자는 자바 프로그램의 널 관련 동작 즉 널 값을 역 참조 (메모리에 적재된 값에 접근하는 동작) 예외를 부활 시킨다.
 * 일반적으로 널이 될 수 있는 값을 사용하려면 그냥 예외를 던지는 방식보다 더 타당한 응답을 제공해야 하기 때문에 이 동작을 사용하지 말아야 한다.
 *
 * 하지만 널 아님 단언 연산자 사용을 정당화 할 수 있는 경우가 있다.
 */
fun notNullable() {
    var name: String? = null

    fun initialize() {
        name = "John"
    }

    fun sayHello() {
        // 컴파일러는 name이 null safe 하다는 걸 인지 할 수 없다. (변수 타입을 String으로 세분화 하지 못하므로)
        // 그러므로 널 아님 단언 연산자를 사용해 컴파일 오류를 우회할 수 있다.
        // !! 하지만 컴파일러가 스마트 캐스트를 적용할 수 있게 사용하는 편을 더 권장한다 !!
        println(name!!.uppercase())
    }

    initialize()
    sayHello()
}

/**
 * 안전한 호출 연산자
 *
 * 널이 될 수 있는 타입의 값에 대해서는 그에 상응하는 널이 될 수 없는 타입의 메서드를 호출 할 수 없다.
 * 하지만 안전한 호출 연산자(safe call)을 사용하면 제약을 피할 수 있다.
 */
fun safeCallOperator() {
    fun readInt() = readLine()!!.toInt() // 잘 작동하지만 KotlinNullPointException을 발생시킬 수 있다.

    /**
     * 아래와 같이 안전 호출 연산자를 사용하자.
     *
     * 아래의 코드는
     *
     * fun readStringLength(): String? {
     *   val tmp = readLine()
     *
     *   return if (tmp != null) tmp.length else null
     * }
     *
     * 와 같다.
     *
     * 즉, 왼쪽 피연산자가 null이 아닐 경우 일반적인 함수를 호출해주지만, 수신 객체가 널이면 안전 호출 연산자는 호출을 수행하지 않고 null을 돌려준다.
     * 정리하자면, 수신 객체가 널이 아닌 경우 의미 있는 일을 하고, 수신 객체가 널인 경우 널을 반환하라.의 패턴이다.
     * (실무에서 굉장히 자주 보이는 패턴이다. 안전 호출 연산자는 if 식과 임시 변수의 사용을 줄여준다.)
     */
    fun readStringLength() = readLine()?.length

    /**
     * 안전 호출 연산자를 응용해 연쇄식으로 사용할 수 있다.
     */
    println(readLine()?.toInt()?.toString(16))

    /**
     * 안전한 호출 연산자가 널을 반환할 수 있기에 이런 연산이 반환하는 값의 타입은 널이 될 수 있는 타입이 된다.
     * 하단의 test 함수 처럼 호출하는 쪽에서도 이런 타입 변화를 염두해야 한다.
     */
    fun readInt2() = readLine()?.toInt()
    fun test() {
        val n = readInt2()

        if (n != null) println(n + 1)
        else print("No value")
    }
}

/**
 * 엘비스 연산자 (?:)
 *
 * 앨비스 연산자를 사용하면 null 대신 default 값 설정 가능하다.
 */
fun sayHello(name: String?) {
    println("Hello, " + (name ?: "Unknown"))
}

fun sayInt() {
    // ?. 연산자가 null 체크는 되는데 blank 체크는 안돼서 (일단) 아래처럼 구현 함..
    var number = readLine()
    if (number.isNullOrBlank()) {
        number = "0"
    }
    println("Hello, " + number.toInt())
}

fun main() {
    println(isLetterString("abc")) // OK
//    println(isLetterString(null)) // Error

    /**
     * 코틀린에서 String? 같은 타입은 널이 될 수 있는 타입이라고 불린다.
     * 타입 시스템 용어에서 모든 널이 될 수 있는 타입은 원래 타입(?가 붙지 않은 타입)의 상위 타입(부모)이며,
     * 원래 타입에 속하는 모든 값으로 이뤄진 집합을 null로 확장한 집합이 값의 집합이 된다.
     *
     * 널이 될 수 없는 타입은 하위(자식) 타입이며, 이는 nullable 타입에 nullable 하지 않은 타입을 대입할 수 있다는 의미이며,
     * 반대로 nullable한 타입을 nullable하지 않은 타입에는 대입할 수 없다는 의미이다. (자식은 부모를 품을 수 없다.)
     */
    println(isBooleanString(null)) // false
    val s: String? = "abc"
//    val ss: String = s // Type mismatch

    /**
     * Int 나 Boolean 같은 원시 타입도 nullable 할 수 있다.
     * 다만 nullable 해지는 순간 박싱 타입이 된다.
     */
    val intNotNull: Int = 100
    val intNull: Int? = 100

    println(intNull === intNotNull) // false

    sayHello("서민규")
    sayHello(null)
    sayInt()
}