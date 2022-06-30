import property.*
import java.util.*

fun main(){
    /**
     * val: 불변 변수
     * var: 가변 변수
     */
    val a: Int = readLine()!!.toInt()
    var b: Int = readLine()!!.toInt()

//    a = 10 // 불변 변수는 대입 불가능
    b = 10
    b /= 1 // 복합 대입 연산자 가능

    println(a + b)

    var c = 1;

    println(c++) // 1 출력, c의 값은 2
    println(++c) // 3 출력, c의 값은 3

    // L로 Long Type 추론 가능
    val dw = 10L

    // 각 정수 타입에는 최솟값과 최댓값을 퐇마하는 상수 정의가 들어가 있음.
    println(Short.MAX_VALUE)
    println(Short.MIN_VALUE)
    println(Int.MAX_VALUE + 1) // overflow

    val one: Byte = 1
//    val tooBigForShort: Short = 100000 // too big for Short
    val millionNumber = 1000000 // Int로 타입 추론
//    var zero = 01 // 수 리터럴의 경우 0을 표현하는 정수가 아니라면 맨 앞에 0이 나올 수 없음.

    val pi = 3.14 // 타입 추론: Double
    val piF = 3.14f // Float
    val quarter = .25 // 0.25

    /**
     * 비트연산 (p.69 참고)
     */
    // 0000 1101 1 + 4 + 8 = 13
    // 0011 0100 32 + 16 + 4 = 52
    println(13 shl 2)

    // 비트 반전
    // 0000 1101 1 + 4 + 8 = 13
    // 1111 0010 -14
    println(13.inv())


    /**
     * 문자 타입 Char
     */
    println("\'\r\n\"") // 새줄 문자, 따옴표 등 지원
    println("\u03c0") // π, 16진수 시퀀스를 사용해 임의의 유니코드 문자 사용 가능

    /**
     * 비교와 동등성
     */
    val intNum = 1
    val LongNum = 2L
    println(intNum.toLong() == LongNum) // 같은 타입만 동등성 비교(==, !=) 가능
    println(1.25 >= 1)// 단, 수(number) 타입에 한정해서 >=, <= 와 같은 비교 연산은 타입이 달라도(Int <= Long과 같이) 가능
    println("true < false : " + (true > false)) // boolean의 경우 true가 false보다 크다고 가정, == 연산은 불가

    /**
     * 불 타입 지연 계산 (&&, ||)
     */
    val x = 1
    val y = 1
    println(x == 1 || y/(x - 1) != 1) // x == 1이 true 이면 오른쪽 항은 계산하지 않는다. (오른쪽 항이 계산했다면 zero by division 에러가 발생한다.)
    println(x == 2 && y/(x - 1) != 1) // x == 2가 false 이면 오른쪽 항은 계산하지 않는다.
    // 즉시 계산 or, and는 지연 계산보다 우선수위가 높다.
    // 예를들어 a || b and c or d && e 라면 다음의 우선순위로 계산된다. a || (((b and c) or d) && e)

    /**
     * 문자열
     */
    val name = readLine()
    println("Hello, $name\nToday is ${Date()}") // 템플릿 활용

    val rowString = """
        로우 문자열을 활용해보자. 
            trimIndent()는 여러 줄에 공통된     <- 이 만큼의 최소 들여쓰기를 제거해준다.
    """.trimIndent()
    println(rowString)

    val s1 = "hello"
    val s2 = "hel" + "lo"
    println(s1 == s2) // 자바와 다르게 코틀린은 ==으로 동등성 비교를 할 수있다. 내부적으로 (.equals()를 호출한다.)

    println(s1.substring(1, 3)) // el
    println(s1.indexOf('h')) // 0

    /**
     * 배열
     * Array<Int> 를 사용하는 배여은 제대로 동작하지만 모든 수를 박싱하기 때문에 그다지 실용적이지 않다. 이런 이유로 코틀린은 더 효율적인 ByteArray, IntArray 와 같은 배열 타입을 제공한다.
     */
    val stringArray = emptyArray<String>() // String 타입의 빈 배열
    val whatArray = arrayOf('a', 'b') // whatArray는 whatArray<String>으로 타입 추론된다.

    val size = readLine()!!.toInt()
    val squares = Array(size) {
        (it + 1) * (it + 1)
    }
    println(squares)
    println(squares.size)

    val squaresSub = arrayOf(1, 4, 9, 16)
    println(squares.size)

    val numbers = squaresSub // 참조값 복사
    numbers[0] = 10000
    println(squaresSub[0]) // 같은 참조 값을 가진 squaresSub에 10000 반영

    val numberss = squaresSub.copyOf()
    numberss[1] = 10000
    println(squaresSub[1]) // 깊은 복사를 통해 squaresSub에 10000 반영 X

    // 코틀린은 다음가 같이 상위 타입의 배열에 하위 타입의 배열을 대입할 수 없다.
    // Object[] objects = new String[] { "1", "2", "3" };
    // 이와 같은 로직은 다음과 같은 런타임 오류를 야기시킬 수 있다.
    // objects[0] = new Object();
    // 그렇다보니 코틀린은 Any 배열에 Any의 하위 타입 배열을 대입할 수 없다.

    val compareArray = arrayOf(1, 3, 4)
    val compareArray2 = arrayOf(1, 3, 4)
    println(compareArray.contentEquals(compareArray2)) // 배열 요소 비교는 contentEquals로 한다. == 연산은 참조를 비교한다.

    /**
     * 임포트 디렉티브에서 최상위 프로퍼티를 임포트 할 수 있다.
     */
    println("$prefix")
    println("$prefix2")
}