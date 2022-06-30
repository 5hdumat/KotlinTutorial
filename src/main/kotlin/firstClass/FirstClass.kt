package firstClass

/**
 * 고차 함수
 */

val squares = IntArray(5) { n -> n * n}

/**
 * 어떤 정수 배열의 원소의 합을 계산하는 단순한 함수
 */
fun sumLegacy(numbers: IntArray): Int {
    var result = numbers.firstOrNull() ?: throw IllegalArgumentException("Empty array")

    for (i in 1..numbers.lastIndex) {
        result += numbers[i]
    }

    return result
}

/**
 * 위 함수를 좀 더 일반화 해서 곱셈이나 최댓값/최솟값과 같은 다양한 집계 함수를 사용하게 하려면 어떻게 해야 할까?
 * 함수 자체의 기본적인 루프 로직은 그대로 두되 중간 값들을 함수의 파라미터로 추출한 다음 일반화 함수(aggregate)를 호출할 때 이 파라미터에 적당한 연산을 제공하면 된다.
 *
 * 아래 함수의 파라미터 op:(Int, Int) -> Int 중 op 는 '함수값' (Int, Int) -> Int '함수 타입'이라고 한다.
 * 괄호로 둘러싸인 파라미터 타입 목록 ((Int, Int)) 은 함숫값에 전달될 데이터의 종류와 수를 정의한다.
 * 반환 타입(-> Int)은 함수 타입의 함숫값을 호출하면 돌려받게 되는 값의 타입이다. (반환값이 없는 함수라도 함수 타입에서는 반환 타입을 반드시 명시해야 한다. Unit을 활용하자.)
 *
 * 함수 정의와 달리 함수 타입 표기에서는 인자 타입 목록과 반환 타입 사이를 :이 아닌 ->로 구분한다.
 */
fun aggregate(numbers: IntArray, op: (Int, Int) -> Int): Int {
    var result = numbers.firstOrNull() ?: throw IllegalArgumentException("Empty array")
    var result2 = 0

    for (i in 1..numbers.lastIndex) {
        result = op(result, numbers[i]) // 함수값을 일반 함수처럼 호출 할 수 있다.
        result2 = op.invoke(result, numbers[i]) // invoke를 사용해 호출 할 수도 있다.
    }

    return result
}

/**
 * 함숫 값을 표현하는 인자식을 aggregate 함수를 호출하면서 넘긴다.
 * 람다식은 기본적으로 단순한 형태의 문법을 사용해 정의하는 '이름 없는 지역 함수'다.
 *
 * { result, number -> result + number }
 *
 * result와 number는 함수 파라미터 역할을 하며 -> 다음에 오는 식은 결과를 계산하는 식이다.
 * 람다는 명시적인 return이 불필요 하기 때문에 기입하지 않아도 된다.
 */
fun sum(numbers: IntArray) = aggregate(numbers) { result, number -> result + number }

fun mul(numbers: IntArray) = aggregate(numbers) { result, number -> result * number }

fun max(numbers: IntArray) = aggregate(numbers) { result, number -> if (number > result) number else result }

/**
 * 코틀린 에서는 interface 키워드 앞에 fun 을 붙이면 자바의 단일 추상 메서드(SAM)으로 취급한다.
 * 이 기능을 사용하면 자바의 SAM 인터페이스 처럼 코틀린 인터페이스를 람다로 인스턴스화 할 수 있다.
 *
 * 인터페이스를 람다로 인스턴스화 별도의 클래스 선언이 불피요하고 코드부에서 바로 구현할 수 있어 클래스 선언 비용을 줄이고 코드를 간결하게 유지할 수 있다.
 */
fun interface StringConsumer {
    fun accept(s: String)
}

/**
 * 함수가 인자를 받지 않는 경우 함수 타입의 파라미터 목록에 빈 괄호를 사용한다.
 * 또한 함수 타입의 파라미터 타입을 둘러싼 괄호도 필수이므로 빼먹지 않도록 하자.
 *
 * 추가적으로 함수 타입이 널이 될 수 있는 타입으로 지정되었다면 함수 타입을 괄호로 감싸준 후 물음표를 붙여주자.
 */
fun measureTime(action: (() -> Unit)?): Long {
    val start = System.nanoTime()

    action?.invoke()

    return System.nanoTime() - start
}

fun main() {
    /**
     * 파라미터 타입을 둘러싼 괄호도 필수이므로 빼먹지 않도록 해야 하며,
     * 함수 타입의 값을 함수의 파라미터 안에서만 사용할 수 있는 것도 아니다. 함숫 값을 변수에 저장 할수도 있다.
     */
    val inc: (Int) -> Int = { n -> n + 1}
    val dec: (Int) -> Int = { n -> n - 1}
    val lessThan: (Int, Int) -> Boolean = { a, b -> a < b }
    println(inc(5)) // 6
    println(dec(5)) // 4
    println(lessThan(3, 5)) // true

    /**
     * 변수 타입에 함수 타입을 생략하면 컴파일러가 람다 파라미터의 타입을 추론할 수 없다.
     * 함수 타입을 생략하고 싶다면 파라미터에 타입을 명시하면 된다.
     */
//    val lessThanError = {a, b -> a < b} // Error
    val lessThanGood = { a: Int, b: Int -> a < b }

    var consume = StringConsumer { s-> println(s) }

    println(squares)
    println(sum(intArrayOf(1, 2, 3)))
    println(max(intArrayOf(1, 2, 3)))
    println(mul(intArrayOf(1, 2, 3)))


    /**
     * 고차 함수
     *
     * 합수 타입을 다른 함수 타입 안에 내포시켜 고차 함수의 타입을 정의할 수 있다.
     *
     * ->는 오른쪽 결합을 의미하며, 하단 shifter 는 Int를 인자로 받아서 다른 함수를 반환하며, 반환 된 함수는 Int를 받아서 다른 Int를 내놓는다.
     */
    val shifter: (Int) -> ((Int) -> Int) = { n -> { i -> i + n }}

    val inc2 = shifter(1)
    val dec2 = shifter(-1)

    println(inc2)
    println(dec2)

    println(inc2(10)) // 11
    println(dec2(10)) // 9

    /**
     * Int를 받아서 함수가 아닌 Int 값을 내놓는 함수를 인자로 받아서 Int를 결과로 돌려주려면 다음과 같이 하면 된다.
     * 또한 함수 타입에 파라미터 명을 포함시킬 수 있으며, 이는 단지 가독성을 위한 것이다. 타입이 표현하는 함숫값에는 전혀 영향을 끼치지 않는다.
     * (하단 호출부에서 cmd + p 를 누르면 함수 시그니처에 대한 힌트를 얻을 수 있다.)
     */
    val evalAtZero: (func: (num: Int) -> Int) -> Int = { func -> func(0) }
//    val evalAtZero: ((Int) -> Int) -> Int = { func -> func(0) }

    println(evalAtZero { n -> n + 2 }) // 2
    println(evalAtZero { n -> n + 1 }) // 1
}

/**
 * [깨알 정보]
 * 보통 function value 나 functional value는 보통 함수가 반환하는 값(return value)이 아닌 함수인 값 자체를 뜻한다.
 * 보통 이런 함수 역할을 하는 값을 함숫 값이라고 지칭하는데, 함숫 값은 실제 런타임에 힙 메모리상에 존재하는 함수처럼 호출할 수 있는 객체이다.
 * 이런 점에서 일반 함수와 약간 다르다. (함수는 힙이 아닌 스택 영역이라서 인가?) 다만 함숫 값과 일반 함수 모두 호출 할 수 있고, 호출 시 결과 값을 돌려준다는 건 동일하다.
 */