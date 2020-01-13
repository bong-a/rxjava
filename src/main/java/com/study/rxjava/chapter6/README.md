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