import java.lang.Exception

/**
 * 코틀린의 throws 식도 break, continue와 같은 Nothing 타입이다.
 */
fun parseIntNumber(s: String): Int {
    var num = 0

    if (s.length !in 1..31) throw NumberFormatException("Not a number: $s")

    for (c in s) {
        if (c !in '0' .. '1') throw NumberFormatException("Not a number: $s")
        num = num * 2 + (c - '0')
    }

    return num
}

/**
 * catch 블록은 선언된 순서대로 예외 타입을 검사한다.
 * 상위 타입을 처리할 수 있는 catch 블록보다 하위 타입(상세한 에러 타입)이 위에 있어야한다.
 * 그렇게 하지 않으면 상위 타입 에러가 하위 타입 에러까지 다 잡아버린다.
 */

fun readInt(default: Int): Int {
    try {
        val num = readLine()!!.toInt()
    } catch (e: Exception) {
        return 0
    } catch (e: NumberFormatException) {
        return default
    }

    return 0
}

/**
 * 자바에서는 도착할 수 없는 코드를 금지하기 때문에 아래와 같은 코드는 금지된다.
 * 자바와 달른 코틀린 try의 가장 큰 차이점은 코틀린 try가 식이라는 것이다.
 * 아래 예제는 예외가 발생하지 않은 경우 try 블록의 값이거나 예외르 처리한 catch 블록이 값이 된다.
 */
fun readInt2(default: Int) = try {
        readLine()!!.toInt()
    } catch (e: NumberFormatException) {
        default
    }

/**
 * finally 스코프에서는 try 문의 스코프에 영향을 미치지 못한다.
 */
fun readInt3(default: Int) = try {
    readLine()!!.toInt()
} finally {
    println("Error")
}

fun main() {
    println(readInt3(1))
}