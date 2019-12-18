# 3. RxJava의 메커니즘

## 3.1 RxJava와 디자인 패턴

RxJava는 옵저버 패턴과 이터레이터 패턴 영향을 받음

### 3.1.1 옵저버 패턴

 ![img](https://gmlwjd9405.github.io/images/design-pattern-observer/observer-pattern.png) 

<b>한 객체의 상태 변화에 따라 다른 객체의 상태도 연동되도록 일대다 객체 의존관계를 구성하는 패턴</b>

GoF 디자인 패턴, 관찰 대상이 되는 Subject에 이를 관찰하는  Observer를 등록한다. 그리고 <b> Subject의 상태가 변하면 Subject로부터 등록된 모든 Observer에 변화가 발생한 사실을 통지</b>한다. Observer는 Subject로 부터 통지를 받은 후 변화에 따른 적절한 처리를 한다.

#### 특징

- 관찰대상인 Subject에 상태 변화가 발생했을 때, Subject 스스로 변화가 생겼다고 Observer에게 통지한다는 것
- 관찰대상인 Subject와 관찰하는 Observer가 분리됐다는 점

##### 옵저버패턴을 사용하지 않고 객체를 관찰하려면?

- 주기적으로 객체 상태가 변했는지 확인해야한다.

- Subject 상태가 오랫동안 바뀌지 않는다면 상태 확인하는 처리 작업을 계속 반복해서 수행해야함

- 그렇다고 상태 확인하는 주기를 길게 잡으면 Subject에 상태가 바뀌더라도 한참 뒤에야 상태값이 바뀐걸 확인할 수 있음

  

생산자 - 소비자 관계가 옵저버 패턴 적용한 것

옵저버 패턴의 Subject가 생산자로, Observer가 소비자로 볼 수 있다.



#### 행위 패턴

- 객체나 클래스 사이의 알고리즘이나 책임 분배에 관련된 패턴
- 한 객체가 혼자 수행할 수 없는 작업을 여러 개의 객체로 어떻게 분배하는지, 또 그렇게 하면서도 객체 사이의 결합도를 최소화하는 것에 중점을 둔다

![img](https://gmlwjd9405.github.io/images/design-pattern/types-of-designpattern.png) 

예제 1 : 차량 연료량 클래스는 연료량이 부족한 경우, 연료량에게 관심을 가지는 구체적인 클래스에게 직접 의존하지 않는 방식으로 연료량의 변화를 통보해야한다. 

- 구체적인 클래스 :  연료량 부족 경고 클래스, 주행 가능 거리 출력 클래스 등

예제 2 : [여러 가지 방식으로 성적 출력하기](observer/Client,java)

 ![img](https://gmlwjd9405.github.io/images/design-pattern-observer/observer-solution.png) 

 



### 3.1.2 이터레이터 패턴

![img](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=http%3A%2F%2Fcfile4.uf.tistory.com%2Fimage%2F99C8A03D5A992031198354)

<b>컬렉션 구현 방법을 노출시키지 않으면서도 그 집합체 안에 들어있는 모든 항목에 접근할 수 있는 방법을 제공한다.</b>

RxJava의 실제 구현은 이터레이터 패턴과 완전히 다른 구조를 가지고 있으나 개념에는 이터레이터 패턴이 많은 영향을 주었음

GoF 디자인 패턴 중 하나로, 데이터 집합체에서 순서대로 데이터를 꺼내기 위한 패턴

집합체에서 데이터를 꺼내는 Iterator를 생성하고 Iterator로 데이터를 하나씩 순서대로 얻을 수있다. 

Iterator는 데이터 집합체가 어떤 형태로 데이터를 가지고있는지 알 필요가 없다.

가져갈 데이터가 있는지 hasNext 메서드로 판단하고 데이터가 있으면 next메서드로 데이터를 얻는다.





RxJava에서는 생산자 자체가 데이터 집합체이므로 데이터를 순서대로 공급(Iterator)하는 역할을 하기도 한다.

단, RxJava는 이터레이터 패턴처럼 소비자가 데이터를 가져가는 형태(Pull)가 아니라 소비자에게 데이터를 통지하는 형태(Push)

이러한 차이가 있지만, <b>데이터를 하나씩 순서대로 처리하는 메커니즘이라는 점</b>에서 공통점이 있다.

순서대로 통지 받는다 -> 소비자 내부에서 상태 다루기 쉽다



일반적인 이터레이터 패턴에서는 데이터가 모두 모일때까지 처리를 시작할 수 없지만, RxJava에서는 모든 데이터가 만들어지지 않은 상태에서도 생성된 데이터를 순서대로 처리 할 수 있다.



예제 : [두개 다른 식당이 있고 각각의 식당에서 메뉴를 구현한다.](iterator/MenuTestDrive.java)

출처
- https://gmlwjd9405.github.io/2018/07/08/observer-pattern.html
- https://jusungpark.tistory.com/25


