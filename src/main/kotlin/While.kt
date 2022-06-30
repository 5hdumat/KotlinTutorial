/**
 * 코틀린의 루프문의 매력은 수 범위나 컬렉션 등 몇 가지에 대한 용례만 지원하는게 아닌
 * 다양한 값에 대한 루프를 수행할 수 있는 통일된 매커니즘을 제공한다는 점에 있다.
 *
 * 코틀린 타입이 여러 이터레이터(원소 값을 추출할 수 있는 기능 제공을 위한 iterator())를 기본으로 제공하므로
 * 진행, 배열, 문자열 등에 대해 for 루프를 쓸 수 있다.
 */
import kotlin.random.Random

// 숫자 맞추기 게임
fun guess() {
    val num = Random.nextInt(1, 101)
    var guess = 0

    while (guess != num) {
        guess = readLine()!!.toInt()

        if (guess < num) println("Too Big")
        else if (guess > num) println("Too Small")
    }

    println("Right! It's ${num}")
}

// 루프 제어 흐름을 이용한 맞추기 게임
// 루프 중간에 종료 조건이 있어 진입 조건이 불필요 해졌다.
fun guessControl() {
    val num = Random.nextInt(1, 101)
    var guess = 0

    while (true) {
        guess = readLine()!!.toInt()

        if (guess < num) println("Too Big")
        else if (guess > num) println("Too Small")
        else
            break
    }

    println("Right! It's ${num}")
}

/**
 * 코틀린은 for 는 java의 for-each 문과 비슷하다.
 */
fun summery() {
    var sum = 0
    val a = IntArray(10) { it * it } // 0, 1, 4, 9, 16 ...

    for (x in a) {
        sum += x
    }

    println(sum)
}

/**
 * 코틀린은 문자열의 각 문자에 대한 반복이 가능하다.
 * 자바에서는 문자열의 인덱스를 활용해 루프를 돌거나, 문자열을 문자 배열로 만들어야 한다.
 */
fun parseIntNumber(s: String, fallback: Int = -1): Int {
    var num: Int  = 0

    for (c in s) {
        if (c !in '0' .. '1') return fallback
        num = num * 2 + (c - '0')
    }

    return num
}

// 짝수 인덱스만 2배로 변경
fun binaryIndexDouble() {
    val a = IntArray(10) { it * it }

    for (i in a.indices step 2) {
        a[i] *= 2
    }

    println(a.contentToString())
}

fun countLetters(text: String): IntArray {
    val counts = IntArray('z' - 'a' + 1)

    for (char in text) {
        val charLower = char.lowercaseChar()

        if (charLower !in 'a' .. 'z') continue
        counts[charLower - 'a']++
    }

    return counts
}

/**
 * 코틀린은 break와 continue
 *
 * 코틀린의 break는 루프에서만 적용된다. 기본적으로 코틀린은 풀스루를 사용하지 않기 때문에 코틀린 break는 자바 break와 같지 않다.
 * 이로 인한 혼동을 막기 위해 코틀린 1.4 이전에는 when (자바의 switch 문 같은) 내부에서 continue나 break를 사용하는 것을 막는다.
 *
 * 단, 코틀린 1.4부터는 when 내에서 사용하는 break나 continue는 when을 둘러싼 가장 가까운 루프로 제어가 이동한다.
 * 한편, continue는 미래의 어느 시점에 나올 코틀린 버전에서 명시적인 풀스루를 하기 위해 예약돼 있으니 참고하자.
 */

/**
 * 이중 루프와 레이블
 *
 * continue와 break는 구체적으로 루프 앞에 붙은 레이블만 호출 가능하다.
 */
fun indexOf(subarray: IntArray, array: IntArray): Int {
    outerLoop@ for (i in array.indices) {
        for (j in subarray.indices) {
            if (subarray[j] != array[j + i]) continue@outerLoop

        }

        return i
    }

    return -1
}

// 숫자 맞추기 게임 레이블 활용
fun guessLabel() {
    val num = Random.nextInt(1, 101)
    var guess = 0

    loop@ while (true) {
        guess = readLine()!!.toInt()

        when {
            guess < num -> println("Too Big")
            guess > num -> println("Too Small")
            else -> break@loop
        }
    }

    println("Right! It's ${num}")
}

/**
 * 꼬리 재귀 함수(tailrec)
 *
 * 재귀함수는 스택 오버플로를 야기 할 가능성이 있다. 하지만 코틀린에서는 tailrec을 붙이면 컴파일러가
 * 재귀함수를 비재귀적인 코드로 자동 변환한다. 그 결과 양쪽의 장점, 즉 재귀 함수의 간결함과 비재귀 루프의 성능만을 취할 수 있다.
 *
 * 주의점으로는 다음 예제 binIndexOf 와 같이 재귀 호출 다음에 어떤 동작도 하지 말아야한다.
 * sum array[index] + binIndexOf(...) 와 같이 덧셈을 수행하는 꼬리재귀가 아닌 것을 컴파일러가 발견하면 경고를 표시하고,
 * 원래의 재귀 함수로 호출한다.
 */
tailrec fun binIndexOf(x: Int, array: IntArray, from: Int = 0, to: Int = array.size): Int {
    if (from == to) return -1

    val midIndex = (from + to + 1) / 2
    val mid = array[midIndex]

    return when {
        mid < x -> binIndexOf(x, array, midIndex + 1, to)
        mid > x -> binIndexOf(x, array, from, midIndex)
        else -> midIndex
    }
}

fun main() {
//    println(countLetters("love").contentToString())
//    println(indexOf(intArrayOf(1, 2, 3, 4, 5), intArrayOf(0, 1, 2, 3, 4, 5)))
    println(binIndexOf(3, intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)))
}
