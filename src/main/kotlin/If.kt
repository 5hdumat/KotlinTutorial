fun renamePackage(fullName: String, newName: String): String {
    val i = fullName.lastIndexOf('.')
    val prefix = if (i > 0) fullName.substring(0, i + 1) else return newName

    return prefix + newName
}

fun main() {
    fun max(a: Int, b: Int): Int {
        if (a > b) return a else return b // 코틀린 if는 식처럼 사용할 수 있다.

        // 이렇게도 가능
        if (b > a) b
    }

    println(renamePackage("foo.bar.old", "new"))

    /**
     * 정해진 값 사이에 수열을 표현하는 범위 연산자 .. 과 until 활용
     * .. (닫힌 범위): 시작과 끝 값 포함
     * until (반만 닫힌 범위): 시작 값 포함
     */
    println(20 in 1 .. 10) // false
    println(5 in 1 .. 10 step 2) // true
    println(1.3 in 1.0 .. 2.0) // true
    println("def" in "abc" .. "xyz") // true
    println("zz" in "abc" .. "xyz") // false

    println(20 in 1 until 20) // false
    println(1 in 1 until 20) // true

    /**
     * 진행 연산
     */
    println(0 in 10 .. 1) // 끝 값이 시작 값 보다 작으면 빈 범위가 된다. 이럴 떈 다음과 같이 진행 연산을 해주면 된다. (양수만 된다.)
    println(10 in 10 downTo 1) // true
    println(2 in 10 downTo 1 step 2) // true, step은 간격만큼 더해진다. [1, 3, 5, 7, 9]

    /**
     * 범위 연산
     */
    println(IntArray(10) {it * it}.sliceArray(2 .. 5).contentToString()) // [0, 1, 4, 9, 16, 25, 36, 49, 64, 81] -> [4, 9, 16, 25]
    println(IntArray(10) {it * it}.sliceArray(2 until 5).contentToString()) // [0, 1, 4, 9, 16, 25, 36, 49, 64, 81] -> [4, 9, 16]

    /**
     * in 연산 지원 범위
     * 배열이나 문자열 처럼 다른 타입의 원소를 담는 컨테이너 종류의 타입이라면 보통 이 두 연산을 지원한다.
     */
    val numbers = intArrayOf(3, 7, 2, 1)
    val text = "Hello!"

    println(3 in numbers) // true
    println('a' in text) // false
    println('H' in text) // true
}