fun hexDigit(n: Int): Char {
    /**
     * 코틀린의 when 문은 자바 switch와 비슷하다.
     * 하지만 코틀린의 when은 풀스루하지 않는다.
     * 자바의 switch는 조건이 참이더라도 명시적으로 break를 만날 떄 까지 풀스루한다.
     *
     * 코틀린에서 break와 continue를 사용하면 오류가 발생한다. (코틀린은 루프에서 사용한다)
     */
    when {
        n in 0 .. 9 -> return '0' + n
        n in 10 .. 15 -> return 'A' + n - 10
        else -> return '?' // else 는 필수적으로 포함시켜야 한다.
    }
}

/**
 * 대상이 n 하나이므로 이와같이 개선할 수 있다.
 */
fun hexDigitBetter(n: Int): Char {
    when (n) {
        in 0 .. 9 -> return '0' + n
        in 10 .. 15 -> return 'A' + n - 10
        else -> return '?'
    } // else 는 필수적으로 포함시켜야 한다.
}

fun main() {

}