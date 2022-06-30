import java.util.concurrent.locks.Condition

/**
 * 람다식
 *
 * 함수형 타입의 구체적인 값을 어떻게 만들 수 있을까?
 * 여러가지 방법 중 한가지 방법은 함수를 묘사하되 이름을 지정하지 않는 람다식을 사용하는 것이다.
 */

fun aggregate(numbers: IntArray, op: (Int, Int) -> Int): Int {
    var result = numbers.firstOrNull() ?: throw IllegalArgumentException("Empty Array")

    for (i in 1 .. numbers.lastIndex) {
        result = op(result, numbers[i])
    }

    return result
}

/**
 * 하단의 { result, number -> result + number }와 같은 식을 람다식이라고 부른다.
 * 람다식의 정의는 함수와 비슷한 요소로 이루어지는데
 *
 * 파라미터 목록: result, number (람다의 파라미터 목록은 괄호로 둘러싸지 않는다는 걸 유의하자.)
 * 람다식의 본문이 되는 식이나 문: result + number
 *
 * 로 외우면 된다.
 *
 * 함수 본문에 반환 타입을 설정할 필요가 없으며, 반환 타입이 자동으로 추론된다. (중괄호에 마우스를 호버하면 확인할 수 있다.)
 *
 * 또한 람다가 함수의 마지막 파라미터로 오는 경우, 함수를 호출할 때 인자를 둘러싸는 괄호 밖에 이 람다를 위치시킬 수 있으니 참고하자.
 */
fun sum(numbers: IntArray) = aggregate(numbers) { result, number -> result + number }

fun max(numbers: IntArray) = aggregate(numbers) { num1, num2 -> if (num1 > num2) num1 else num2}

/**
 * 인자가 없는 람다
 *
 * 람다에 인자가 없으면 호출부에서 -> 는 생략 가능하다.
 */

fun measureTime(action: () -> Unit): Long {
    val start = System.nanoTime()

    action()

    return System.nanoTime() - start
}

/**
 * 인자가 하나밖에 없는 람다는 특별히 단순화할 수 있는 문법이 있다.
 *
 * 인자가 하나인 경우 파라미터 목록과 화살표 기호를 생략할 수 있고 유일한 파라미터는 미리 정해진 it라는 이름을 상요해 가리킬 수 있다.
 */
fun check(s: String, condition: (Char) -> Boolean): Boolean {
    for (c in s) {
        if (!condition(c)) return false
    }

    return true
}

fun main() {
    println(sum(intArrayOf(1, 2, 3, 4)))
    println(max(intArrayOf(1, 2, 3, 4)))

    println(measureTime {
        for (i in 1..1000) {
            println(i)
        }
    })

    println(check("abcdefg") { c -> c.isLetter() })
    println(check("abcdefg") { it.isLetter() })
}