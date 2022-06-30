import util.readIntPair
import kotlin.Int.Companion.MAX_VALUE
import kotlin.Int.Companion.MIN_VALUE
import kotlin.math.PI

/**
 * 함수 파라미터에는 항상 타입을 지정해줘야 한다.
 * 반환 타입도 마찬가지이다. (void가 아니라면)
 */
fun circleArea(radius: Double): Double {
    return PI * radius * radius
}

// 식이 본문인 함수(함수가 단일 식으로만 구성)에서는 반환 값 생략이 가능하다.
// 다만 복잡한 식인 경우 블럭을 사용해 가독성을 높여주는 편이 낫다.
fun circleAreaExpressionBody(radius: Double) = PI * radius * radius

// 식이 본문인 함수에서는 return 문 사용이 불가능하다.
//fun circleAreaLambda(radius: Double) = { PI * radius * radius } // TypeMismatch

fun increment(a: IntArray): Int {
    return ++a[0]
}

fun rectangleArea(width: Double, height: Double): Double {
    println("${width} ${height}")
    return width * height
}

fun swap(s: String, from: Int, to: Int): String {
    val chars = s.toCharArray() // 배열로 변환

    // 배열 원소 교환하기
    val tmp = chars[from]
    chars[from] = chars[to]
    chars[to] = tmp
    return chars.concatToString() // 문자열로 다시 변환
}

fun main() {
    println("Enter radius: ")
    val radius: Double = 5.0
    println("circle area: ${circleArea(radius)}")

    /**
     * 기본적으로 코틀린은 call-by-value를 채택하고 있다.
     * 값에 의한 호출이라는 의미로 함수가 호출된 시점에 값이 인자로 복사된다.
     * 함수 안에서는 값 변경이 기본적으로 불가능하다. (immutable)
     * 단, 배열과 같은 참조 변수는 내부 값이 바뀔 수 있다.
     */
    val a = intArrayOf(1, 2, 3)
    increment(a)
    println(a.contentToString()) // [2, 2, 3]

    /**
     * 코틀린은 위치 기반 인자와 이름 붙은 인자 두 가지 방식을 제공한다.
     */
    val x = 5.0
    val y = 15.0

    println(rectangleArea(x, y)) // 위치 기반으로 인자를 대입한다.
    println(rectangleArea(height = y,  width = x)) // 위치가 다르더라도 인자의 식별자로 구분해 대입시킨다.
    println("hello")
    println(swap("hello", 1, 3))
    println(swap("hello", 3, 0))
    println(swap("hello", 1, 3))

    // 위치 기반 인자와 이름 붙은 인자 혼용하기
    println(swap(s = "hello", 1, 3)) // 1.4 이전 버전에선 컴파일 오류 발생
//    println(swap(s = "hello", to = 1, 3)) // 다만, 원래 인자가 들어가야 할 위치에 이름을 지정해줘야 한다.

    /**
     * 코틀린의 함수 오버로딩
     * 코틀린의 함수 오버로딩은 자바 오버로딩 해소 규칙과 비슷하다.
     * 덜 구체적인 함수를 하나씩 제외하면서 구체적인 시그니쳐가 맞아 떨어지는 함수를 찾는다.
     */
    fun mul(a: Int, b: Int) = a * b // 1
    fun mul(o: Any, n: Int) = Array(n) { o } // 2

    println(mul(1, 2)) // 1 이 실행된다.
    println(mul(1 as Any, 2)) // 2를 실행하고 싶다면 as 문을 활용한다.

    fun readInt(radix: Int = 10): Int {
        return readLine()!!.toInt(radix)
    }

    val decimalInt = readInt(10)
    val hexInt = readInt(16)

    // 디플트 파라미터를 지정하지 않고 이 함수를 호출하려면? 지정해주면 된다.
    // 추가적으로 디폴트 값이 있는 파라미터를 함수 인자 목록에서 뒤쪽에 몰아두는 쪽이 더 좋은 코딩 스타일이다.
    fun restrictToRange(what: Int, from: Int = MIN_VALUE, to: Int = MAX_VALUE): Int = Math.max(from, Math.min(to, what))
    println(restrictToRange(1, 10, 20))

    /**
     * 가변 인자 vararg
     * vararg는 한 함수에 1개의 인자에만 존재할 수 있다.
     * 하지만 콤마로 분리한 여러 인자와 스프레드를 섞어서 전달하는 것은 괜찮다.
     *
     * 오버로딩 시 에도 덜 구체적인 함수로 간주하니 참고하자.
     */

    // items는 배열 인자로 넘어온다.
    fun printSorted(vararg items: Int) {
        items.sort() // 정렬
        println(items.contentToString())
    }

    fun change(vararg items: IntArray) {
        items[0][0] = 123
    }

    printSorted(1, 3, 2) // [1, 2, 3]
    printSorted(1, 2, 3, 4, 5, 6) // [1, 2, 3, 4, 5, 6]

    val testArray = intArrayOf(5, 6, 7)
    val testArray2 = intArrayOf(3, 4, 1)
    printSorted(*testArray) // [5, 6, 7] 스프레드 연산자인 *를 활용하면 배열을 가변 인자 대신 넘길 수 있다. (스프레드는 배열을 얕은 복사한다는 점에 유의)

    change(testArray, testArray2)
    printSorted(*testArray) // [5, 6, 7] -> [6, 7, 123]

    fun printSorted2(vararg items: Int, a: Int){
        items.sort() // 정렬
        println(items.contentToString())
    }

    // vararg 가변인자 다음에 오는 인자는 이름 붙은 인자로만 전달 할 수 있다.
    // 그러므로 vararg와 같은 가변인자는 파라미터의 마지막에 선언하는 것이 좋은 코딩 습관이다.
//    printSorted2(1, 2, 3, 1) // Error
    printSorted2(1, 2, 3, a=1)
    printSorted2(items = *testArray, 1)

    // 디플토 값이 vararg 앞에 있는 경우
    fun printSorted3(a: String = "", vararg items: Int){
        items.sort() // 정렬
        println(a)
        println(items.contentToString())
    }

//    printSorted3(1, 2, 3, 4) // Error integer literal does not conform to the expected type String
    // vararg 인자를 이름 있는 인자로 전달한다.
    printSorted3(items = intArrayOf(2, 3, 4))

    // vararg 뒤에 디폴트 값 인자가 있는 경우
    fun printSorted4(vararg items: Int, a: String = ""){
        items.sort() // 정렬
        println(a)
        println(items.contentToString())
    }

    // 디폴트 값 인자를 이름 있는 인자로 전달한다.
    printSorted4(1, 2, 3, a = "")

    // Util.kt 함수 호출하기
    println(readIntPair())

    /**
     * 지역 변수처럼 함수 내부에 함수를 선언할 수 있으며, 스코프는 지역 함수를 감싸고 있는 블럭에 한정된다.
     * 지역 함수와 변수는 가시성 변경자(private, internal 등)를 붙일 수 없다.
     */
    fun innerFun() {

    }
}
