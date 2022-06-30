package classTutorial

/**
 * 코틀린은 공개 클래스 이름과 소스 파일의 이름을 똑같게 만들 필요가 없다.
 * 단지 취향의 문제라고 생각한다.
 */
class Person {
    var firstName: String = ""
    var familyName: String = ""
    var age: Int = 0

    fun fullName() = "${this.firstName} $familyName" // this는 java와 동일하게 수신 객체를 참조한다. (생략 가능)

    fun showMe() {
        println("${fullName()} $age")
    }
}

/**
 * 클래스 프로퍼티도 불변일 수 있다. (val 선언)
 * 이럴경우 프로퍼티의 값을 지정할 수단이 있어야 한다. 그렇지 않으면 모든 인스턴스가 동일한 firstName을 써야한다.
 * 커스텀 생성자를 사용하면 다양한 값으로 초기화 가능하다.
 */
class PersonCustomConstructor2 {
    val firstName: String = "John"
}

/**
 * 클래스 이름뒤에 덧붙인 파라미터 목록을 살펴보자.
 * 이 파라미터는 클래스의 인스턴스를 생성할 때 전달되는 인자다. (주 생성자 파라미터라고 한다.)
 * 이 파라미터를 이용해 프로퍼티를 초기화 하고, 인스턴스를 생성한다.
 *
 */
class PersonCustomConstructor(firstName: String, familyName: String) {
    val fullName = "$firstName $familyName"

    /**
     * - 클래스 헤더의 파라미터 목록을 주 생성자 선언이라고 하는데, 주 생성자는 함수와 달리 본문이 하나가 아니다.
     * 무슨 의미냐면 클래스 헤더의 주 생성자는 클래스 정의 내에서 프로퍼티 초기화와 초기화 블록이 등장하는 순서대로 여러개 구성된다.
     * - 초기화 블록이란 init 키워드가 앞에 붙은 블록이다.
     * - 주 생성자 인자를 프로퍼티 초기화나 init 블록 밖에서 사용할 수 없다.
     *   멤버 함수 등에서 주 생성자 파라미터를 쓸 수 없다는 의미이므로 이와 같은 케이스는 멤버 프로퍼티를 따로 만들어 생성자 파라미터를 할당해주면 된다.
     */
    init {
//        if (firstName.isEmpty() && familyName.isEmpty()) return // 생성자에는 return 이 들어갈 수 없다. 'return' is not allowed here
        println("Created new Person instance: $fullName")
    }
}

/**
 * 주 생성자 인자를 프로퍼티 초기화나 init 블록 밖에서 사용할 수 없다.
 * 그러므로 멤버 함수 등에서 주 생성자 파라미터를 쓸 수 없다는 의미이므로 이와 같은 케이스는 멤버 프로퍼티를 따로 만들어 생성자 파라미터를 할당해주면 된다.
 * 간단히 생성자 파라미터 앞에 val, var 키워드를 덧 붙여주면 자동으로 해당 생성자 파라미터로 초기화 되는 프로퍼티를 정의한다.
 *
 * 생성자 블럭에서 해당 파라미터를 참조하면 생성자 파라미터로 참조되고, 다른 위치에서 참조하면 프로퍼티를 가리키게 된다.
 *
 * 팁) var / var 생성자 파라미터를 사용해 본문이 없어지면 {} 를 생략할 수 있다.
 */
class PrimaryConstructor(val firstName: String, val familyName: String)

class PersonInit(fullName: String) {
    val firstName: String
    val familyName: String

    init {
        val names = fullName.split(" ")
        if (names.size != 2) {
            throw IllegalArgumentException("Invalid name: $names")
        }

        /**
         * 컴파일러는 모든 프로퍼티가 확실히 초기화 되는지 확인한다.
         * 주 생성자의 모든 실행 경로에서 멤버 프로퍼티를 초기화하거나 예외를 발생시키는지 확인할 수 없다면
         * 프로퍼티에 property must be initialized or be abstract 오류를 발생시킨다.
         */
//        if (names.size == 2) {
//            firstName = names[0]
//            familyName = names[1]
//        }

        firstName = names[0]
        familyName = names[1]
    }
}

/**
 * vararg와 default value도 주 생성자 파라미터에서 사용할 수 있다.
 */
class RoomPerson (val firstName: String, val familyName: String = "") {
    fun fullName() = "$firstName $familyName"
}

class Room (vararg val persons: RoomPerson) {
    fun showNames() {
        for (person in persons) println(person.fullName())
    }
}

/**
 * 경우에 따라서 여러 생성자를 사용해 서로 다른 방법으로 인스턴스를 초기화 하고 싶을때가 있을것이다.
 * 디폴트 파라미터를 사용하는 주 생성자로 해결하지 못하는 케이스는 부 생성자를 사용해야한다.
 * 부 생성자는 constructor라는 키워드를 사용한다.
 *
 * - 부 생성자는 기본적으로 Unit 타입 값을 반환하는 함수라고 생각하면된다.
 *   init 과는 달리 return 을 사용할 수 있다.
 * - 부생성자는 본인의 본문을 실행하기 전 프로퍼티 초기화와 init 블록을 실행한다. (공통 초기화 코드가 정확히 한번만 실행되게 보장할 수 있다.)
 */
class PersonSubConstructor {
    val firstName: String
    val familyName: String

    constructor(firstName: String, familyName: String) {
        this.firstName = firstName
        this.familyName = familyName
    }

    constructor(fullName: String) {
        val names = fullName.split(" ")

        if (names.size != 2) {
            throw IllegalArgumentException("invalid name: $fullName")
        }

        firstName = names[0]
        familyName = names[1]
    }
}

/**
 * - 부 생성자가 생성자 위임 호출을 사용해 다른 부생성자 혹은 주 생성자를 호출할 수 있다.
 * - 생상자 목록뒤에 콜론(:)을 넣고 일반 함수를 호출하듯이 코드를 작성하되, this를 사용해 객체 자신을 가리키면된다.
 * - 부 생성자 파라미터 목록에는 val, val 키워드 사용이 불가능하다.
 */
// 주 생성자 위임 호출
class PersonConstructor2 (fullName: String) {
    constructor(firstName: String, familyName: String):
            this("$firstName $familyName")
}

// 부 생성자 위임 호출
class PersonConstructor3 {
    val fullName: String

    constructor(firstName: String, familyName: String):
        this("$firstName $familyName")

    constructor(fullName: String) {
        this.fullName = fullName
    }
}

/**
 * 멤버 가시성
 *
 * - private로 선언된 주 생성자 파라미터는 main()에서 호출했을 때 (당연하게도) 볼 수 없다.
 * - 멤버 함수, 프로퍼티, 주 생성자, 부 생성자에 대한 가시성도 지원한다.
 */
class PersonVisibility(private val firstName: String, private val familyName: String) {
    fun fullName() = println("$firstName $familyName")
}

// 주 생성자 가시성 제한 (이제 이 클래스는 클래스 본문 외부에서 인스턴스화 할 수 없다.)
class Empty private constructor() {
    fun showMe() = println("Empty")
}

/**
 * 내포된 클래스
 *
 * 자바와 가장 큰 차이점은 자바의 경우 내부 클래스가 외부 클래스와 연관되길 원하지 않으면 명시적으로 static을 붙여야한다.
 * 코틀린은 inner가 붙지 않은 내부 클래스는 자동적으로 외부 클래스와 연관되지 않는다.
 */
class PersonInner (private val id: Id, val age: Int) {
    /**
     * 코틀린은 바깥쪽 클래스가 내포된 글래스의 비공개 멤버에 접근할 수 없다.
     */
//    class Id(private val fristName: String, private val familyName: String)
    class Id(val firstName: String, val familyName: String)

    fun showMe() = println("${id.firstName} ${id.familyName}")
}

// inner 키워드
class PersonInnerKeyword(val firstName: String, val familyName: String) {
    inner class Possession(val description: String) {
        fun showOwner() {
            println(fullName())

            // 내부 클래스에서 this는 가장 내부의 클래스 인스턴스를 가리킨다.
            // 내부 클래스에서 외부 클래스를 가리키려면 한정시킨 this를 사용해야한다.
            this@PersonInnerKeyword.myWallet
        }
    }

    val myWallet = Possession("Wallet")

    private fun fullName() = "$firstName $familyName"
}

fun main() {
    val person = Person() // 코틀린은 인스턴스를 생성할 때 new 키워드를 사용하지 않는다.

    person.firstName = "Seo"
    person.familyName = "Mingyu"
    person.age = 25

    person.showMe()

    val person2 = PersonCustomConstructor("John", "Hops")
    val person3 = PersonCustomConstructor("Daniel", "Caesar")

    println(PersonInit(person2.fullName).firstName)
    println(PersonInit(person3.fullName).familyName)

    val person4 = PrimaryConstructor("mingyu", "mingyu")
    println(person4.firstName)

    val room = Room(RoomPerson("안", "시현"), RoomPerson("서", "민규"))
    room.showNames()

    val personVisibility = PersonVisibility("서", "만규")
    personVisibility.fullName()

    val id = PersonInner.Id("서", "민규")
    val personInner = PersonInner(id, 29)
    personInner.showMe()

    val personInnerKeyword = PersonInnerKeyword("아", "이유")
    val wallet = personInnerKeyword.Possession("Wallet")
    wallet.showOwner()


    /**
     * 지역 클래스
     *
     * 자바에서 처럼 함수 본문에서 클래스를 정의할 수 있다.
     * 이렇게 정의된 클래스는 함수 블럭 안에서만 유요하다. (그러므로 가시성 변경자를 붙일 수 없다.)
     */
    class Point(val x: Int, val y: Int) {

        // 지역 클래스의 멤버 클래스는 항상 inner 클래스여야 한다.
        inner class Test {

        }
        // 지역 함수와 비슷하게 코틀린 지역 클래스도 자신을 둘러싼 코드의 선언에 접근할 수 있으며,
        // 보인이 접근할 수 있는 값을 포획할 수 있고, 값을 변경할 수도 있다. (코틀린과 달리 자바에서는 포획한 변수의 값을 변경할 수 없다.)
        fun shift(dx: Int, dy: Int): Point = Point(x + dx, y + dy)
        override fun toString() = "($x, $y)"
    }

    val point = Point(10, 10)
    println(point.shift(-1, 3)) // 9, 13
}