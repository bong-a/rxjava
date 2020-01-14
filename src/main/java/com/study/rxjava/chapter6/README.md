# RxJava의 디버깅과 테스트

## 6.1 디버깅과 테스트

RxJava는 디버깅이나 테스트를 쉽게 할 수 있는 여러 메서드와 클래스를 제공

- do로 시작하는 메서드
- blocking으로 시작하는 메서드
- TestSubscriber,TestObserver
- TestScheduler

이런 메서드나 클래스 사용하면 통지 시점에 로그를 출력하거나 실행 결과가 기대한 대로 작동하는지 테스트 할 수 있다.

하지만 비동기로 처리하는 테스트라면 일반적으로 성공하는 처리라도 어무 로직에 관계없는 환경 등의 영향으로 테스트가 실행 할 수도 있다.

subscribe메서드에 전달하는 인자를 익명 클래스로 직접 구현하면 해당 테스트 용도의 메서드나 클래스를 사용할 수 없다.

테스트 용도의 메서드나 클래스를 사용하는것은 구현방식에도 영향을 준다.

- 업무로직에는 사용하지 않는 것이 좋음



## 6.2 do로 시작하는 메서드

통지시점에 미리 지정한 처리를 수행하는 메서드

통지 시에 로그를 출력하는 등의 처리를 할 수 있음

- doOn : 통지할 때 특정한 부가작용이 발생
- doAfter : 통지한 이후에 특정한 부가작용이 발생

### 6.2.1 doOnNext

데이터 통지 시에 지정한 처리 작업을 실행하는 메서드

<img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/doOnNext.png" alt="img" style="zoom:50%;" />



###  6.2.2 doOnComplete

완료 통지시 지정한 처리 작업을 실행하는 메서드

<img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/doOnComplete.png" alt="img" style="zoom:50%;" />



### 6.2.3 doOnError

에러를 통지하면 지정한 처리를 실행하는 메서드

<img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/doOnError.png" alt="img" style="zoom:50%;" />



### 6.2.4 doOnSubscribe

구독 시작시 지정한 처리를 실행하는 메서드

<img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/doOnSubscribe.png" alt="img" style="zoom:50%;" />



### 6.2.5 doOnRequest

데이터 개수를 요청받을 때 인자로 지정한 함수형 인터페이스의 처리를 실행하는 메서드



### 6.2.6 doOnCancel/doOnDispose

구독을 해지하면 지정한 처리를 실행하는 메서드

완료나 에러로 종료되면 실행되지 않음

<img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/doOnUnsubscribe.png" alt="img" style="zoom:50%;" />



## 6.3 blocking으로 시작하는 메서드

blocking으로 시작하는 메서드로 비동기 처리 결과를 받아 테스트

비동기 처리 결과를 현재 실행 중인 스레드에서 받을 수 있는 메서드

테스트를 실행하는 스레드에서 비동기 통지의 결괏값을 받을 수 있어 결괏값을 서로 비교할 수 있다.

### 6.3.1 blockingFirst

Flowable의 첫번째 통지 데이터를 받게 하는 메서드

첫번째 통지 데이터를 결괏값으로 받을 때까지 대기하며 다음 처리를 진행하지 않음

데이터나 완료를 통지하는 Flowable에 사용해야함 -> never 메서드로 생성한 Flowable처럼 아무것도 통지하지 않거나 어떤이유로 결과를 반환하기 전에 구독을 해지하면 통지 데이터를 계속 기다려 스레드가 멈춘 상태가 되므로

### 6.3.2 blockingLast

Flowable이 통지하는 마지막 데이터를 받게 하는 메서드

결과를 반환하는 시점이 완료통지 시점이라서 완료가 통지 될 때까지 다음 처리가 진행되지 않음

<img src="https://github.com/ReactiveX/RxJava/wiki/images/rx-operators/B.last.png" alt="img" style="zoom:50%;" />

### 6.3.3 blockingIterable

Flowable이 통지하는 모든 데이터를 받는 Iterable을 얻게 하는 메서드

subscribe 메서드가 호출되면 처리를 시작하는 Flowable은 Iterable의 iterator메서드를 호출하는 시점부터 처리를 시작함

Flowable의 처리가 종료되지 않더라도 Iterable을 얻을 수 있음

- 데이터를 받으려면 Iterable에서 Iterator를 얻어와 next 메서드 호출
- next 호출할 때 통지된 데이터가 없으면 통지될 때까지 대기
- 통지한 데이터가 있는데 next 호출안한 상태라면 데이터는 버퍼에 보관 -> next 호출시 오래된 순서대로 꺼냄
- 장기간 next호출 안함 -> 버퍼 초과 발생할 수도 있음 -> 이때 next 호출하면 MissingBackpressureException 발생
- 완료 후 next 호출하면 NoSuchElementException 발생 -> 완료했는지 조사하려면 hasNext메서드 활용해야함

데이터나 완료를 통지하는 Flowable에 사용해야함 -> 스레드 멈출 수 도 있으니까

### 6.3.4 blockingSubscribe

해당 메서드 호출한 원본 스레드에서 소비자의 통지데이터를 처리 실행 할 수 있게 하는 메서드



## 6.4 TestSubscriber/TestObserver

Flowable의 통지내용을 테스트

테스트용도로 사용하는 소비자 클래스

생산자로부터 받은 동지를 확인 할 수 있음

### 6.4.1 TestSubscriber/TestObserver 생성

- 생산자의 test메서드
  - 생성과 동시에 해당 생성자가 구독함
- 생성자로 직접 생성
-  TestSubscriber/TestObserver의 create 메서드

#### assert 메서드

받은 통지 데이터를 테스트할 수 있는 메서드

이 때 테스트 대상은 assert메서드를 호출한 시점에 TestSubscriber가 받은 통지이다.

시간 경과에 따라 데이터를 통지하는 Flowable을 테스트할 경우 시간과 그 시간 동안 통지한 내용까지 고려해야 의도한 대로 테스트 할 수 있다.



#### await 메서드

Flowable의 처리는 진행하게 하면서 자신의 처리는 지정한 시간동안 또는 지정한 종료 시각까지 대기하게 하는 메서드



## 6.5 TestScheduler

Flowable 통지 시 걸리는 시간을 다루는 TestScheduler

지정한 시간에 진행할 처리를 실제로 시간을 쓰지 않고도 테스트할 수 있는 스케줄러

TestScheduler는 interval메서드나 timer메서드처럼 RxJava에서 시간과 관련된 데이터를 다룰 때만 사용 가능

실제로 처리할 때 걸리는 시간이 단축되는 것은 아님

### 6.5.1 주요 메서드

- advancedTimeBy : 지정한 시간 동안만 처리
- advanceTimeTo : 지정한 시각까지 처리
- now : 원하는 단위를 지정해 경과된 시간을 long 타입으로 얻을 수 있음