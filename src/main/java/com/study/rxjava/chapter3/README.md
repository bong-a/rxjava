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



## 3.2 비동기 처리
RxJava에서는 비동기처리를 수행하는데 필요한 API 제공 -> 기존 구축한 비즈니스 로직에 영향 주지않고 생산자나 소비자의 작업을 비동기로 처리하게 교체할 수 있다.

스레드 관리하는 클래스 제공 -> 직접 스레드 관리 안해도 됨

### 3.2.1 RxJava에서 비동기 처리
RxJava는 구조상 데이터를 통지하는 측(생산자)와 받는 측(소비자)의 처리작업이 논리적으로 분리되어 있음

-> 양측의 처리 작업 자체가 상대방의 작업에 영향을 미치는 일이 없다

->각각의 처리 작업을 서로 다른 스레드에서 실행하기 쉽다.

데이터를 통지하는 측은 데이터를 통지한 뒤 바로 자신의 작업으로 돌아와도 받는 측의 처리 작업에는 논리적으로 영향을 미치지 않으며, 데이터를 통지하는 측의 처리 작업에 의해 데이터를 받는 측의 행동이 바뀌는 일도 발생하지 않는다.

RxJava에서 비동기 처리를 할 때는 생산자가 처리를 실행하는 스레드와 데이터를 받는 측의 스레드를 모두 관리해야함

- subscribeOn : 생산자 처리 작업의 스레드 설정 메소드
- observeOn : 데이터 받는 측의 스레드 종류 설정 메소드

#### 스케줄러

- computation : 연산 처리할 때 사용하는 스케줄러, 논리 프로세서 수만큼 스레드 캐싱, I/O 작업에서 사용 X
- io : I/O 처리 작업할 때 사용하는 스케줄러,스레드 풀에서 스레드를 가져오며 필요에 따라 새로운 스레드 생성
- single : 싱글 스레드에서 처리작업 할 때 사용
- newThread : 매번 새로운 스레드 생성
- from(Executor executor) : 지정한 익스큐터가 생성한 스레드에서 처리작업 수행
- trampoline : 현재 스레드의 큐에 처리 작업을 넣는 스케줄러, 다른 처리 작업이 큐에 있다면 그 작업 처리가 끝난 뒤에 새로 등록한 처리 작업을 수행한다.

서로 다른 스레드가 동시에 접근하는 공유 I/O 처리작업에는 io 스케줄러는 적절치 않다

-> 스레드 안전을 보장하게 구현하거나 single 스케줄러로 설정해 공통 스레드에서만 처리 작업하게 해야함



- timer나 interval 메서드는 스케줄러를 설정하지 않아도 기본으로 별도의 스레드에서 처리작업을 하는 생산자

  -> 시간을 다루는 처리작업에 해당되는 경우

시간을 다루는 처리 작업을 하지 않는 한 생산자가 처리작업을 하는 기본 스레드에서 모든 처리작업 수행



#### subscribeOn 메서드

생산자의 처리작업을 어떤 스케줄러에서 실행할지 설정하는 메서드

최초 1회만 설정 가능하며, 이후에 설정한 값은 무시된다.

interval메서드처럼 스케줄러가 자동으로 지정될 때는 개발자가 subscribeOn메서드로 다른 스케줄러 지정해도 무시된다.

이와 같은 경우 스케줄러를 메서드 인자로 받아 설정 가능하다.

```java
Flowable.interval(100L, TimeUnit.MILLISECONDS,Schedulers.io())
```

(예제 : [L08_ObserveOnSample](./L08_SubscribeOnSample.java))



#### observeOn 메서드

데이터 받는 축의 처리 작업을 어떤 스케줄러에서 실행할지 설정하는 메서드

데이터를 받는 측의 스케줄러를 지정하기 때문에 연산자마다 서로 다른 스케줄러를 지정할 수 있다.

스케줄러만을 인자로 받기도 하며 그외에도 여러 인자를 제공

```java
observeOn(Scheduler scheduler)
observeOn(Scheduler scheduler, Boolean delayError)
observeOn(Scheduler scheduler, Boolean delayError, int bufferSize)
```

세번짜인자 `int bufferSize`는 통지를 기다리는 데이터를 버퍼에 담는 크기로 배압을 적용할 때 이 인자가 중요하다.

버퍼에 담긴 통지 대기 데이터에서 인자 크기만큼 소비자에게 통지할 데이터를 꺼내기 때문이다.

실제로는 자동으로 생산자에게 인자이 지정한 수치를 데이터 개수로 요청하며, 이 요청으로 받은 데이터를 버퍼에 쌓아 둔다.

소비자가 Long.MAX_VALUE로 요청해도 observeOn메서드에 설정된 버퍼사이즈만큼의 데이터만 소비자에게 통지되는 것을 보여준다.

즉, 소비자는 observeOn메서드가 가진 모든 데이터를 받지만, 생산자 측의 데이터를 모두 받을 수 있는 것은 아니다.

(예제 : [L09_ObserveOnSample](./L09_ObserveOnSample.java))



### 3.2.2 연산자 내에서 생성되는 비동기 Flowable/Observable

#### flatMap 메서드

flatMap메서드는 아래와 같이 행동을 한다.

1. 데이터를 받으면
2. 새로운 Flowable/Observable을 생성
3. 이를 실행해 통지되는 데이터를 메서드의 결과물로 통지

데이터가 연속적으로 들어오고 이를 통해  Flowable/Observable이 별도의 스레드에서 실행되 제각각 데이터를 통지하고 이렇데 통지된 데이터는 메서드의 실행 결과로 통지된다. 그러므로 최종으로 통지되는 데이터는 데이터를 받은 순서와 달라질 수 있다.

(예제 : [L10_FlatMapSample](./L10_FlatMapSample.java))

- 실행 순서와 상관없이 처리 성능이 중요할 때는 flatMap메서드를 사용하지만, 데이터 순서가 중요하다면 flatMap 메서드를 사용하지 않는 편이 낫다.

#### concatMap 메서드

concatMap메서드는 다음과 같이 행동을 한다.

1. 데이터를 받으면
2. 메서드 내부에 Flowable/Observable 생성
3. 이를 하나씩 순서대로 실행
4. 통지된 데이터를 그 결과물로 통지

Flowable/Observable은 각각 다른 스레드에서 처리해도 이에 영향을 받지 않고 새로 생성한 Flowable/Observable의 처리 데이터를 받은 순서대로 통지한다.

(예제 : [L11_ConcatMapSample](./L11_ConcatMapSample.java))

- 성능에 관계없이 데이터 순서가 중요할 때는 concatMap메서드를 사용하지만, 성능이 중요할 대는 concatMap메서드를 사용하지 않는 것이 좋다.

  

#### concatMapEager 메서드

concatMapEager 메서드는 다음과 같이 행동을 한다.

1. 데이터를 받으면
2. 새로운 Flowable/Observable을 생성
3. 즉시 동시 실행 (=flatMap)
4. 결과로 받은 데이터를 원본 데이터 순서대로 통지 (=concatMap)

 결과로 통지해야하는 데이터는 Flowable이 생성된 순서이므로 통지 전까지 생성된 데이터를 버퍼에 쌓아둔다.

(예제 : [L13_CounterSample](./L13_CounterSample.java))

- 데이터 순서와 성능 모두 중요하다면 concatMapEager 메서드가 적합하다.
- 하지만, 통지 전까지 데이터를 버퍼에 쌓아야 해서 대량의 데이터가 버퍼에 쌓이면 메모리가 부족해질 위험이 있다.



### 3.2.3 다른 스레드 간 공유되는 객체

여러 Flowable/Observable에서 접근할 수 있는 공유 객체가 있을 때는 이를 병합해 하나의 Flowable/Observable로 만들어 처리 작업을 하지 않으면 정확한 결과를 얻을 수 없으니 주의해야 한다.

(예제 : [L14_CounterWithMergeSample](./L14_CounterWithMergeSample.java))

```java
package com.study.rxjava.chapter3;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class L14_CounterWithMergeSample {
    public static void main(String[] args) throws InterruptedException {
        final Counter counter = new Counter();

        Flowable<Integer> flowable1 = Flowable.range(1, 10000)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation());
        Flowable<Integer> flowable2 = Flowable.range(1, 10000)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation());
        Flowable.merge(flowable1, flowable2)
                .subscribe(
                        data -> counter.increment(),
                        error -> System.out.println("error : " + error),
                        () -> System.out.println("onComplete : counter.get() : " + counter.get())
                );
        Thread.sleep(2000L);
    }

    static class Counter {
        private volatile int count;

        void increment() {
            count++;
        }

        int get() {
            return count;
        }
    }
}

```


