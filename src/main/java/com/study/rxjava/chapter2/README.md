# 2. RxJava를 사용하는 데 필요한 배경 지식
## 2.1. 람다식

람다식은 자바 8에서 도입한 표현식

람다식을 사용하지 않아도 RxJava를 구현할 수 있지만  RxJava가 람다식으로 구현하는 것을 가정해 개발했기 때문에 람다식 사용하면 개발 효율이 높아진다.

람다식이 자바8부터 도입된거라 이전 자바 버전에서 람다식을 사용할 수 없다.

하지만 Retrolambda 백포트 라이브러리 사용하면 8 이전 버전에서 사용할 수 있다.

- 백포트 : 새로운 버전의 기능으로 이전버전에서도 사용할 수 있게하는 라이브러리

람다식은 함수형 인터페이스 구현에 필요한 최소 정보만을 구현할 수 있게 함 -> 메서드 인자,처리 내용만 필요

### 2.1.1 함수형 인터페이스

함수형 인터페이스 : 구현해야 하는 메서드가 하나뿐인 거. Comparator도 인터페이스

- 인자 유무, 타입
- 반환 타입 유무, 타입

RxJava는 자바 6이사 지원 -> 8 이후에 새로 추가된 함수형 인터페이스는 RxJava에서 사용 할 수 없음

- [ ] 8 이후에 새로 추가된 함수형 인터페이스

RxJava에서 독자적으로 준비한 함수형 인터페이스 제공 (io.reactive.functions 패키지)

많은 메서드가 이 함수형 인터페이스들을 인자로 받음

- Function/Predicate : 인자 O , 반환값 O
- BooleanSupplier : 인자 X, 반환값 O
- Action/Consumer : 반환값 X
- Cancellable : Action과 동일하나 실행 의미가 다른 인터페이스

RxJava에서 제공하는 함수형 인터페이스 메서드들은 예외를 던지게끔 선언되어있다

<-> 자바 표준 함수형 인터페이스는 예외를 못던짐

예외 발생시, 함수형 인터페이스의 호출자에서 잡혀 소비자에게 전달된다.

#### Function/Predicate

인자와 반환값이 있는 함수형 인터페이스

|        | Function | Predicate |
| ------ | -------- | --------- |
| 반환값 | 제한 X   | Boolean   |

Function은 반환값에 제한에 없는 것을 보아 데이터를 전달받아 행당 데이터로 무언가 생성 처리하는 것을 예상할 수 있음

Predicate는 반환값이 Boolean인 것을 보아 데이터를 받아 해당 데이터로 어떤 결정을 한다고 볼 수 있음

##### Function 함수형 인터페이스 목록

- Function<T,R>
- BiFunction<T1,T2,R>
- Function3<T1,T2,T3,R> ~ Function9<T1,T2,...,T9,R>
- intFunction<T>

##### Predicate 함수형 인터페이스 목록

- Predicate<T>
- BiPredicate<T1,T2>

#### BooleanSupplier

인자 없이 boolean 반환하는 메서드

일반적으로 Supplier이름이 붙는 함수형 인터페이스는 인자없이 다양한 타입의 값을 반환하는 메서드가 있지만 RxJava에서는 boolean 값을 반환하는 BooleanSupplier만 제공

- [ ] 다른 Supplier?
- [ ] 그럼 BooleanSupplier역할은...?

#### Action/Consumer

반환값이 없는 메서드. 즉, 어떤 부가 작용이 발생하는 메서드가 있는 함수형 인터페이스

|      | Action | Consumer |
| ---- | ------ | -------- |
| 인자 | 없음   | 있음     |
| 반환 | 없음   | 없음     |

##### Action 함수형 인터페이스 목록

- Action 
  - run()

##### Consumer 함수형 인터페이스 목록

- Consumer<T>
  - accept(T t)
- BiConsumer<T1,T2>
- LongConsumer

#### Cancellable

Action과 마찬가지로 인자와 반환이 없다.

어떤 취소 처리를 구현하는데 사용하는 인터페이스



### 2.1.2 람다식 문법

- 간결하게 구현 할 수 있다 -> 코드가 간결해진다 -> 작성자의 의도가 명확해진다 & 가독성이 좋아진다.

```
# 생략 없는 람다식
(타입 인자,...) -> {
	//실행문
	return 반환값;
}

# 인자가 없을 때
()->{
	return 반환값;
}

# 인자값이 1개일때
data -> {
	return data * 10;
}

# 리턴문만으로 끝나는 경우
data -> data * 10
# 반환값이 없을 경우
data -> System.out.println(data)
```

### 2.1.3 람다식과 익명 클래스의 차이점

둘간의 차이점은 this가 가리키는 대상이다.

익명클래스의 this는 구현한 인터페이스의 인스턴스를 나타내고, 

람다식의 this는 람다식을 구현한 클래스의 인스턴스를 나타낸다.

