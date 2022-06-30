import java.util.*

/**
 * 커스텀 접근자 사용하기
 *
 * 코틀린 프로퍼티는 변수와 함수의 동작을 한 선언안에 동작할 수 있는 기능이 있다. 이런 기능은 커스텀 접근자를 통해 이뤄진다.
 * 다음 커스텀 게터를 정의하는 코드를 확인해보자.
 */

/**
 * 불변 프로퍼티 접근
 */
class Person(val firstName: String, val familyName: String, age: Int) {
    // 게터는 파라미터가 없으며, 게터의 반환 타입은 프로퍼티의 타입과 동일해야 한다.
    // 아래 처럼 도입한 fullName은 뒷받침 하는 필드(field)가 없이 getter만 있는 프로퍼티 이므로 읽을때마다 다시 계산된다. 해당 프로퍼티는 메모리를 차지하지 않는다.
    val fullName: String
    get() = "$firstName $familyName"


    /**
     * 위 fullName은 불변 프로퍼티(val)에 읽기 접근자(get) 하나뿐이므로 직접 뒷받침하는 필드인 field를 참조하지 않는다.
     * 뒷받침하는 필드에 저장된 값을 사용하지만, 필드 접근을 커스텀해야하는 경우엔 어떻게 해야 할까?
     * (프로퍼티를 읽을 때 마다 Accesing age 라는 로그를 남기고 싶다 등)
     *
     * 그렇다면 다음과 같이 할 수 있다.
     */

    // 이렇게 도입한 fullName2는 뒷받침하는 필드가 생성된다. (field를 상용하는 디폴트 접근자(return field)나 커스텀 접근자가 하나라도 있으면 필드가 생성 된다.)
    val age: Int = age // 주 생성자 파라미터로 선언된 프로퍼티에는 접근자가 생성되지 않는다. 일반적인 프로퍼티가 아닌 생성자 파라미터(val age)를 사용하고 클래스 본문 안에서 프로퍼티에 주 생성자 파라미터를 대입(= age)하면 해결할 수 있다.
    get(): Int { // getter의 프로퍼티 타입 추론 활용
        println("Accessing age")
        return field // 뒷받침하는 필드 참조는 field 키워드로 사용한다. (접근자의 본문에서만 유효)
    }
}

/**
 * 가변 프로퍼티 접근
 *
 * var로 정의하는 가변 프로퍼티에는 값을 읽기 위한 게터와 값을 설정하기 위한 세터라는 두 가지 접근자가 있다.
 * 프로퍼티의 세터 파라미터는 단 1개이며, get과 마찬가지로 프로퍼티의 타입과 동일해야 한다.
 */
class PersonVar(var firstName: String, val familyName: String) {
    /**
     * 가변 프로퍼티에는 두 가지 접근자(get, set)가 있으므로, 두 접근자를 모두 커스텀화하고,
     * 두 접근자가 모두 다 field 키워드를 통해 뒷받침하는 필드를 사용하지 않는 경우를 제외하면 항상 뒷받침하는 필드가 생긴다.
     * 즉, field 키워드를 통해 뒷받침 하는 필드에 대한 참조를 사용하지 않으면 뒷받침 하는 필드 (backing field)는 생기지 않는다.
     */
    var age: Int? = null
    set(value) {
        if (value != null && value <= 0) {
            throw IllegalArgumentException("invalid age: $value")
        }

        field = value
    }
}

/**
 * 프로퍼티 접근자 가시성
 *
 * 프로퍼티가 포함된 클래스 외부에서는 프로퍼티의 값을 변경하지 못하게 해서 바깥 세계에서 볼 때는 실질적으로 객체가 불변인 것 처럼 보이게하는 방법
 */
class Person2(name: String) {
    var lastChanged: Date? = Date()
    private set // lastChanged Person 클래스 밖에서는 변경할 수 없다.

    var name: String = name
    private set

    // 아래와 같은 비공개 프로퍼티는 아무런 접근자 (getter, setter)도 생기지 않는다.
    private var privateProperty: String = name
}

fun main() {
    val person = Person("서", "민규", 29)

    /**
     * 게터는 프로퍼티 정의 끝에 붙으며 이름 대신 get이라는 키워드가 붙은 함수처럼 보인다.
     * 프로퍼티를 읽으면 내부적으로 getter를 호출한다.
     */
    println(person.fullName) // 서 민규
    println(person.age)

    val person2 = Person2("서민규")
    println(person2.name)
    println(person2.lastChanged)
//    person2.name = "서만규" // Error
}