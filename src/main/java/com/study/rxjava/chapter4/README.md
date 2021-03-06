#  4.  Flowable과 Observable의 연산자

- Flowable/Observable 생성하는 메서드

- 데이터를 변환하거나 선별하는 작업을 거쳐서 새로운 Flowable/Observable을 생성하는 메서드

메서드가 Flowable/Observable을 반환하는 특성을 이용해 순서대로 메서드를 연결해 가면 (메서드 체인)

최종으로 소비자가 처리하기 쉽게 최적화된 형태로 데이터를 통지하는 Flowable/Observable을 생성할 수 있다.



RxJava에서는 데이터를 통지하는 Flowable/Observable을 생성하는 메서드를 연산자(operator)라고 부른다.

## 4.1 Flowable/Observable을 생성하는 연산자

### 4.1.1 just

인자의 데이터를 통지하는 Flowable/Observable 생성

- 인자는 최대 10개까지 지정 가능

- 순차적으로 통지

- 모든 데이터 통지시 완료를 통지

  <img src="http://reactivex.io/documentation/operators/images/just.c.png" alt="Just" style="zoom:50%;" />

### 4.1.2 fromArray/fromIterable

배열이나 Iterable에서 Flowable/Observable 생성

fromArray는 인자로 지정한 배열을, fromIterable은 인자로 지정한 Iterable에 담긴 객체를 순서대로 통지하는 Flowable/Observable을 생성하는 연산자

<img src="http://reactivex.io/documentation/operators/images/from.c.png" alt="From" style="zoom:50%;" />



### 4.1.3 fromCallable

Callable 처리 실행하고 그 결과를 통지하는 Flowable/Observable 생성

- Callable 함수형 인터페이스에서 생성한 데이터를 통지하는 Flowable/Observable 생성하는 연산자

- 인자로 지정한 Callable의 반환값을 데이터로 통지

<img src="https://s3.ap-south-1.amazonaws.com/mindorks-server-uploads/rxjava-fromcallable.png" alt="img" style="zoom:50%;" />



### 4.1.4 range/rangeLong

지정한 숫자부터 지정한 개수만큼 통지하는 Flowable/Observable 생성

- rangeLong은 Long값 데이터를 통지

  <img src="http://reactivex.io/documentation/operators/images/range.c.png" alt="Range" style="zoom:50%;" />

### 4.1.5 interval

지정한 간격마다 숫자를 통지하는 Flowable/Observable 생성

- 별도 설정 없으면 Scheduler.computation()의 스케줄러에서 실행

- 스케줄러 변경하려면 인자로 스케줄러 받는 메서드를 활용

- 최초 통지 데이터인 0을 통지하는 시점은 별도 설정이 없다면 처리 시작 시점이 아닌 지정한 시간만큼 지난 뒤이다.

- initalDelay를 인자로 지정하는 메서드를 활용하면 최초 0을 통지하는 시점만 변경할 수 있다.

- interval로 생성한 Flowable/Observable은 완료를 할 수 없다. -> 완료 통지하려면 take 메서드 등으로 통지할 데이터 개수를 제한하는 작업을  해야한다.

- 인자로 지정한 시간 간격은 CPU 부하 등의 영향으로 어느정도 오차가 발생 할 수 있다.

  ![img](https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/timer.p.png)

### 4.1.6 timer

지정 시간 경과 후 0을 통지하는 Flowable/Observable 생성

- 기본적으로 Schedulers.computation()의 스케줄러에서 실행

- 인자로 스케줄러 받아 기본스케줄러 변경할 수 있는 메서드 활용할 수 있음

- CPU 부하 등의 영향으로 어느정도 오차가 발생 할 수 있다.

  ![img](https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/timer.png)

### 4.1.7 defer

구독될 때마다 Flowable/Observable 생성

just 메서드 등과는 다르게 defer 메서드는 선언한 시점의 데이터를 통지하는 것이 아니라 호출 시점에 데이터 생성이 필요하면 사용한다.

<img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/defer.png" alt="img" style="zoom:50%;" />



### 4.1.8 empty

빈 Flowable/Observable 생성

- 처리 시작하면 바로 완료 통지

- 단독으로 거의 사용하지 않고, flatMap 메서드의 통지 데이터가 null일 때 이를 대신해 empty 메서드에서 생성한  Flowable/Observable로 이후 데이터를 통지 대상에서 제외하는 등의 작업을 할 수 있다.

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/empty.png" alt="img" style="zoom:50%;" />

### 4.1.9 error

에러만 통지하는 Flowable/Observable 생성

-  단독 사용하기 보다는 flatMap 메서드 처리하는 도중 에러 통지하고 싶을 때 사용

<img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/error.png" alt="img" style="zoom:50%;" />



### 4.1.10 never

아무것도 통지하지 않는 Flowable/Observable 생성

- 완료도 통지하지 않음
- 완료 통지하는 empty 메서드와 헷갈리지 말 것

<img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/never.png" alt="img" style="zoom:50%;" />

## 4.2 통지 데이터를 변환하는 연산자

### 4.2.1 map

데이터를 변환 통지

- 한 개의 데이터로 여러 데이터 생성해 통지하거나 데이터 통지를 건너뛸 수 없음(flapMap과 비교해볼것)

- null이 아닌 데이터 하나를 반드시 반환해야함

- null 반환 시 nullPointException 발생

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/map.png" alt="img" style="zoom:50%;" />

### 4.2.2 flatMap

받은 데이터를 Flowable/Observable로 변환하고 이  Flowable/Observable의 데이터를 통지

- map메서드와 달리 여더 데이터가 담긴  Flowable/Observable 반환 -> 여러 데이터 통지 가능

- 빈  Flowable/Observable을 반환해 특정 데이터 통지하지 않거나 에러  Flowable/Observable을 반환해 에러 통지 할 수 있음

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/flatMap.png" alt="img" style="zoom:50%;" />

```java
public final <R> Flowable<R> flatMap(Function<? super T,? extends Publisher<? extends R>> mapper)
```

- mapper : 받은 데이터로 새로운 Flowable/Observable을 생성하는 방법을 정의하는 함수형 인터페이스

```java
public final <U,R> Flowable<R> flatMap(Function<? super T,? extends Publisher<? extends U>> mapper,
        BiFunction<? super T,? super U,? extends R> combiner)
```

- combiner : mapper가 새로 생성한 Flowable/Observable데이터와 원본 데이터를 조합해 새로운 통지 데이터를 생성하는 함수형 인터페이스

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/mergeMap.r.png" alt="img" style="zoom:33%;" />

```java
public final <R> Flowable<R> flatMap(Function<? super T,? extends Publisher<? extends R>> onNextMapper,
        Function<? super Throwable,? extends Publisher<? extends R>> onErrorMapper,
        Callable<? extends Publisher<? extends R>> onCompleteSupplier)
```

- onNextMapper : 받은 데이터로 새로운 Flowable/Observable을 생성하는 방법을 정의하는 함수형 인터페이스

- onErrorMapper : 에러가 통지됐을 때 무엇을 통지할지 정의하는 함수형 인터페이스

- onCompleteSupplier : 완료가 통지됐을 때 무엇을 통지할지 정의하는 함수형 인터페이스 

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/mergeMap.nce.png" alt="img" style="zoom:33%;" />

  

### 4.2.3 concatMap/concatMapDelayError

받은 데이터를 Flowable/Observable로 변환하고 변환한 Flowable/Observable을 하나씩 순서대로 실행해 이 데이터를 통지

- concatMapDelayError 메서드는 생성한 Flowable/Observable에서 에러가 발생해도 다른 데이터로 생성한 Flowable/Observable의 처리가 완료될 때 까지 에러 통지를 미룬다.

<img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/concatMap.png" alt="img" style="zoom:50%;" />

```markdown
## CHAPTER3 중 일부
#### concatMap 메서드

concatMap메서드는 다음과 같이 행동을 한다.

1. 데이터를 받으면
2. 메서드 내부에 Flowable/Observable 생성
3. 이를 하나씩 순서대로 실행
4. 통지된 데이터를 그 결과물로 통지

Flowable/Observable은 각각 다른 스레드에서 처리해도 이에 영향을 받지 않고 새로 생성한 Flowable/Observable의 처리 데이터를 받은 순서대로 통지한다.

(예제 : [L11_ConcatMapSample](./L11_ConcatMapSample.java))

- 성능에 관계없이 데이터 순서가 중요할 때는 concatMap메서드를 사용하지만, 성능이 중요할 대는 concatMap메서드를 사용하지 않는 것이 좋다.
```





### 4.2.4  concatMapEager/concatMapEagerDelayError

받은 데이터를 Flowable/Observable로 변환하자마자 실행, 생성된 Flowable/Observable을 순서대로 데이터를 통지

- concatMapEagerDelayError 메서드는 에러가 발생해도 해당 시점까지 받은 데이터로 생성한 Flowable/Observable이 통지를 마칠 때까지 에러 통지를 미룬다.

```markdown
## CHAPTER3 중 일부
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
```



### 4.2.5 buffer

통지할 데이터를 지정한 범위까지 모아 리스트나 컬렉션으로 통지

- 데이터를 어느 정도 모아서 리스트나 컬렉션에 담아 통지하는 연산자

- 데이터 모으는 단위로는 데이터 개수나 시간 간격을 지정할 수 있다.

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/buffer1.png" alt="img" style="zoom:50%;" />

```markdown
public final <B> Flowable<List<T>> buffer(Publisher<B> boundaryIndicator)
public final <B> Flowable<List<T>> buffer(Callable<? extends Publisher<B>> boundaryIndicatorSupplier)
```

- boundaryIndicator : Flowable/Observable이 데이터를 통지하는 간격 단위로 버퍼에 데이터 쌓음 -> 데이터 여러개 통지

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/buffer8.png" alt="img" style="zoom:33%;" />

- boundaryIndicatorSupplier : 버퍼링 시작시 Flowable/Observable을 생성하고 이  Flowable/Observable이 데이터를 통지하는 시점에 버퍼링을 종료 -> 데이터를 한 개만 통지

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/buffer1.png" alt="img" style="zoom:33%;" />

  
  

```java
public final <TOpening,TClosing> Flowable<List<T>> buffer(Flowable<? extends TOpening> openingIndicator,
        Function<? super TOpening,? extends Publisher<? extends TClosing>> closingIndicator)
```

- openingIndicator : 여러 데이터 통지

  - openingIndicator 의 Flowable/Observable에서 데이터를 통지하는 시점은 버퍼링 시작을 나타냄

- closingIndicator : 한 개의 데이터만 통지

  - closingIndicator의 Flowable/Observable에서 데이터를 통지하는 시점은 버퍼링 종료를 나타냄 -> closingIndicator는 openingIndicator의 Flowable/Observable이 데이터를 통지할 때 호출돼 처리를 시작한다.

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/buffer2.png" alt="img" style="zoom:33%;" />

### 4.2.6 toList

통지할 데이터를 모두 리스트에 담아 통지

-  완료 통지를 받은 시점에 결과 리스트를 통지

- 완료를 사용하지 않는 Flowable/Observable에서 사용할 수 없음

- 버퍼에 쌓이는 데이터가 너무 많아 메모리가 부족하게 될 위험성이 있으므로 주의해야함

- 반환값은 Single (데이터 통지만으로 처리가 끝나며 완료 통지는 하지 않음)

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/toList.png" alt="img" style="zoom:50%;" />

### 4.2.7 toMap

통지할 데이터를 키와 값 한 쌍으로 Map에 담아 통지

- 받은 데이터로 키 생성하고 이 키로 Map에 값을 담는다.

- 완료통지를 받는 시점에 결과 Map 통지

- 완료를 사용하지 않는 Flowable/Observable에서 사용할 수 없음

- 버퍼에 쌓이는 데이터가 너무 많아 메모리가 부족하게 될 위험성이 있으므로 주의해야함

- 반환값은 Single (데이터 통지만으로 처리가 끝나며 완료 통지는 하지 않음)

- 키 중복시에 이전 데이터 쌍을 덮어씀 -> 원본 Flowable/Observable이 통지하는 데이터의 전체 개수보다 적을 수 있음

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/toMap.png" alt="img" style="zoom:50%;" />

### 4.2.7 toMultiMap

통지할 데이터를 키와 컬렉션의 쌍으로 Map에 담아 통지

- 받은 데이터로 키 생성하고 이 키와 연관된 컬렉션에 데이터 값을 감은 Map을 작성

- 완료통지를 받는 시점에 결과 Map 통지

- 완료를 사용하지 않는 Flowable/Observable에서 사용할 수 없음

- 버퍼에 쌓이는 데이터가 너무 많아 메모리가 부족하게 될 위험성이 있으므로 주의해야함

- 반환값은 Single (데이터 통지만으로 처리가 끝나며 완료 통지는 하지 않음)

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/toMultiMap.png" alt="img" style="zoom:50%;" />



## 4.3 통지데이터를 제한하는 연산자

### 4.3.1 filter

지정한 조건에 맞는 데이터만 통지

조건 판정은 인자로 받는 함수형 인터페이스 (predicate)에서 이루어 진다.

<img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/filter.png" alt="img" style="zoom:50%;" />



### 4.3.2 distinct

이미 통지한 데이터와 같은 데이터를 제외하고 통지

이 메서드는 내부에 HashSet이 있어서 데이터가 같은지를 확인한다.

<img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/distinct.png" alt="img" style="zoom:50%;" />

```java
public final <K> Flowable<T> distinct(Function<? super T,K> keySelector)
```

keySelector : 받은 데이터와 비교할 데이터를 생성하는 함수형 인터페이스

- 결과롤 통지할 때는 비교용으로 변환한 데이터가 아닌 받은 데이터 그대로 통지한다. 따라서 가변 객체를 비교하는 도중에 객체의 상태가 변경되면 통지 데이터에도 영향이 있으니 주의해야 한다.

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/distinct.key.png" alt="img" style="zoom:33%;" />



### 4.3.3 distinctUntilChanged

연속된 같은 값의 데이터는 제외하고 통지

<img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/distinctUntilChanged.png" alt="img" style="zoom:50%;" />

```java
public final <K> Flowable<T> distinctUntilChanged(Function<? super T,K> keySelector)
```

keySelector : 받은 데이터와 비교할 데이터를 생성하는 함수형 인터페이스

- 결과롤 통지할 때는 비교용으로 변환한 데이터가 아닌 받은 데이터 그대로 통지한다. 따라서 가변 객체를 비교하는 도중에 객체의 상태가 변경되면 통지 데이터에도 영향이 있으니 주의해야 한다.

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/distinctUntilChanged.key.png" alt="img" style="zoom:33%;" />

```java
public final Flowable<T> distinctUntilChanged(BiPredicate<? super T,? super T> comparer)
```

comparer : 바로 앞서 받은 데이터와 현재 데이터가 같은지를 지정한 방법으로 비교하는 함수형 인터페이스

비교 결과로 true 반환하면 해당데이터는 통지되지 않는다.



### 4.3.4 take

지정한 개수나 기간까지만 데이터를 통지

- 지정한 개수나 기간에 도달하면 완료를 통지해 처리를 종료한다.

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/take.png" alt="img" style="zoom:50%;" />

### 4.3.5 takeUtil

지정한 조건에 도달할 때까지 통지

- 두가지 메서드가 있다.

- 받은 데이터를 지정한 조건으로 판단해 그 결과가 true가 될 때 까지 데이터를 통지하는 메서드

- 인자로 Flowable이 처음으로 데이터를 통지할 때까지 계속해서 데이터를 통지하는 메서드

- 두 메서드들은 지정한 조건이 되면 완료를 통지해 처리를 종료한다.

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/takeUntil.p.png" alt="img" style="zoom:50%;" />



### 4.3.6 takeWhile

지정한 조건에 해당할 때만 데이터를 통지

- 받은 데이터가 지정한 조건에 만족하면 데이터를 통지하고, 아니면 완료를 통지하고 처리를 종료

- 판단 결과가 false가 되기 전에 원본  Flowable에서 완료를 통지하면 그대로 완료 통지하고 처리 종료

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/takeWhile.png" alt="img" style="zoom:50%;" />



### 6.3.7 takeLast

끝에서부터 지정한 조건까지의 데이터 통지

- 원본 Flowable이 완료될 때 마지막 데이터로부터 지정한 개수나 지정한 기간의 데이터만을 세어 통지하는 연산자

- 완료 시점에서 데이터 통지하므로 완료를 통지하지 않는 Flowable은 사용할 수 없다.

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/takeLast.n.png" alt="img" style="zoom:50%;" />

```java
public final Flowable<T> takeLast(long count, long time, TimeUnit unit)
```

- 마지막 통지시점부터 지정한 기간까지의 데이터를 얻고 이중에서 끝에서부터 지정한 개수만큼 데이터를 세어 통지한다.

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/takeLast.tn.png" alt="img" style="zoom:33%;" />



### 4.3.8 skip

앞에서부터 지정된 범위까지의 데이터는 통지 대상에서 제외

<img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/skip.png" alt="img" style="zoom:50%;" />

### 4.3.9 skipUntil

인자 Flowable/Observable이 데이터를 통지할 때까지 데이터 통지를 건너뜀

- 인자로 지정한 Flowable/Observable이 데이터를 통지하면 그때부터 결과 데이터를 통지한다.

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/skipUntil.png" alt="img" style="zoom:50%;" />

### 4.3.10 skipWhile

지정한 조건에 해당할 때는 데이터 통지 제외

- 인자로 지정한 조건이 true일 때는 데이터를 통지하지 않는 연산자

- 한번 통지를 시작하면 또 다시 전송 여부를 판단하지 않는다.

- 판단 결과가 한번도 false되지 않는 채로 원본 생산자의 처리가 완료되면 결과로 완료만 통지하고 처리 종료

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/skipWhile.png" alt="img" style="zoom:50%;" />

### 4.3.11 skipLast

끝에서부터 지정한 범위만큼 데이터 통지 제외

- 원본 Flowable이 통지하는 데이터 중에서 끝에서부터 지정한 범위만큼 데이터를 통지하지 않는 연산자

- 통지는 범위만큼 늦춰 시작하고 원본 통지완료 시점에 결과 통지도 완료한다. -> 마지막 데이터부터 지정한 범위만큼 통지를 건너뛰게 된다.

- 원본 Flowable이 skipLast로 지정한 개수보다 적게 데이터를 통지하거나 지정한 시간보다 짧은 시간에 처리를 완료하면 데이터를 통지하지 않고 완료를 통지한다.

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/skipLast.png" alt="img" style="zoom:50%;" />

### 4.2.12 throttleFirst

데이터 통지후 지정 시간 동안 데이터를 통지하지 않음

- 단기간에 데이터가 대량으로 들어오는 데이터가 모두 필요한 것이 아니라면 이 연산자로 데이터를 쳐낼 수 있다.

- 단, 지정시간내라 하더라도 완료나 에러 통지는 한다

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/throttleFirst.png" alt="img" style="zoom:50%;" />

### 4.3.13 throttleLast/sample

지정한 시간마다 가장 마지막에 통지된 데이터만 통지

<img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/throttleLast.png" alt="img" style="zoom:50%;" />

### 4.3.14 throttleWithTimeout/debounce

데이터를 받은 후 일정 기간 안에 다음 데이터를 받지 못하면 현재 데이터를 통지

- 지정 기간에 다음 데이터를 받으면 새로 데이터를 받은 시점부터 다시 지정기간 안에 다음 데이터가 오는지를 확인

- 지정기간 내라 하더라도 완료,에러 통지는 한다.

- 완료 통지되면 마지막으로 통지된 데이터와 함께 완료를 통지하면 에러가 통지되면 에러만 통지한다.

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/throttleWithTimeout.png" alt="img" style="zoom:50%;" />

### 4.3.15 elementAt / elementAtOrError

지정한 위치의 데이터만 통지

- elementAt / elementAtOrError 둘 다 지정한 위치의 데이터만 통지하는 연산자

- elementAt / elementAtOrError 통지할 데이터가 없을 때는 처리 방식이 다름

- elementAt / elementAtOrError 결과로 생성되는 반환 값도 Flowable이 아닌 Single,Maybe 이다

- elementAt 메서드는 인자에 따라 반환값이 바뀌므로 주의해야한다.

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/elementAt.png" alt="img" style="zoom:50%;" />



## 4.4 Flowable/Observable을 결합하는 연산자

### 4.4.1 merge / mergeDelayError / mergeArray / mergeArrayDelayError / mergeWith

여러개의 Flowable을 하나로 병합하고 동시 실행

-  여러 Flowable에서 받은 데이터를 하나의 Flowable로 통지하는 연산자
-  merge계열 메서드 사용하면 여러 Flowable의 통지를 하나의 Subscriber로 구독할 수 있음
- 에러 통지받으면 받은 시점에서 바로 에러 통지
- 모든 Flowable이 완료될때 완료 통지
- 인자로 최대 네개 받을 수 있다.

<img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/merge.png" alt="img" style="zoom:50%;" />

mergeWith : merge메서드의 인스턴스 메서드처럼 자신의 통지와 인자 Flowable의 통지를 병합

mergeDelayError : 모든 Flowable의 처리가 완료될 때까지 에러통지를 기다린다.

​	<img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/mergeDelayError.png" alt="img" style="zoom:33%;" />

mergeArray : 인자를 배열로 받아 네 개이상의 Flowable 전달 할 수 있음 (merge는 최대 네개)

​	<img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/merge.io.png" alt="img" style="zoom:33%;" />

mergeArrayDelayError  : 인자 수(4개 이상 가능)가 다르며, mergeDelayError 와 마찬가지로 모든 Flowable의 처리가 완료될 때까지 에러통지를 기다린다.



### 4.4.2 concat / concatDelayError / concatArray / concatArrayDelayError / concatWith

여러개의 Flowable을 하나씩 실행

- 여러 개의 Flowable을 전달받아 하나의 Flowable로 결합한 후에 <b>순차적</b>으로 실행하는 연산자

- 순차적으로 하나씩 실행되므로 완료되지 않는 Flowable이 포함되면 다음 Flowable이 실행되지 않는다.

- 에러 통지받으면 받은 시점에서 바로 에러 통지

- 인자로 최대 네개 받을 수 있다.

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/concat.png" alt="img" style="zoom:50%;" />

concatWith : concat메서드의 인스턴스메서드처럼 자신의 통지를 한뒤 인자로 받은 Flowable의 통지를 한다.

concatDelayError: 다른 Flowable의 처리가 완료될 때까지 에러 통지 기다린다.

concatArray : 인자를 배열로 받아 네 개이상의 Flowable 전달 할 수 있음 (concat은 최대 네개)

concatArrayDelayError : 인자 수(4개 이상 가능)가 다르며, concatDelayError와 마찬가지로 모든 Flowable의 처리가 완료될 때까지 에러통지를 기다린다.



### 4.4.3 concatEager / concatArrayEager

여러 개의 Flowable을 결합해 동시 실행하고 한 건씩 통지

- concat 메서드와 달리 모든 Flowable이 동시 실행되지만 통지는 concat 메서드처럼 첫번째 Flowable의 데이터부터 통지되고 끝나면 순차적으로 두번째 Flowable의 데이터가 통지된다.

- 이전 Flowable이 완료되는 시점에 바로 전까지 캐시에 쌓이 데이터를 통지한다.

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/Flowable.concatArrayEager.png" alt="img" style="zoom:80%;" />



### 4.4.4 startWith / startWithArray

인자의 데이터를 통지한 후 자신의 데이터를 통지

- 메서드로 생성한 Flowable은 인자의 모든 데이터를 통지한 후에 자신의 모든 데이터를 통지한다.

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/startWith.png" alt="img" style="zoom:50%;" />

### 4.4.5 zip / zipWith

여러 Flowable의 데이터를 모아 새로운 데이터를 생성 통지

- 새로운 데이터는 모든 Flowable에서 동일한 순서(인덱스)의 데이터를 받아 생성한다.

- 따라서 인자로 전달받은 각 Flowable 통지 시점이 다르면 가장 느리게 처리한 Flowable이 데이터를 통지하는 시점에 새로운 데이터가 생성된다.

- 완료 통지 시점은 통지하는 데이터 개수가 가장 적은 인자 Flowable에 맞춘다.

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/zip.png" alt="img" style="zoom:50%;" />

### 4.4.6 combineLatest / combineLatestDelayError

여러 Flowable에서 데이터를 받을 때마다 새로운 데이터를 생성 통지

- 인자로 받은 여러 Flowable이 데이터를 받는 시점에 각 Flowable이 마지막으로 통지한 데이터를 함수형 인터페이스에 전달하고 이 데이터를 받아 새로 데이터를 생성해 통지하는 연산자

- 처음에는 Flowable이 통지할 데이터를 갖추어질 때까지 기다리지만, 그 이후에는 각 원본 Flowable이 통지할 때마다 새로운 데이터를 생성한다.

- 인자 여러 Flowable 중에서 가장 처리가 느힌 Flowable이 완료를 통지한 시점에 완료를 통지한다.

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/combineLatest.png" alt="img" style="zoom:50%;" />



## 4.5 Flowable/Observable 상태를 통지하는 연산자

### 4.5.1 isEmpty

Flowable이 통지할 데이터가 있는지 판단

- 결과로  Single 반환

- 결과 통보하는 시점은 완료통지를 받거나 데이터를 통지받는 시점이기 때문에 완료를 통지하지 않는 Flowable에는 사용할 수 없다.

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/isEmpty.png" alt="img" style="zoom:50%;" />



### 4.5.2 contains

Flowable이 지정한 데이터를 포함하는지 판단

- 결과로 Single 반환

- 결과 통보하는 시점은 완료통지를 받거나 데이터를 통지받는 시점

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/contains.png" alt="img" style="zoom:50%;" />



### 4.5.3 all

Flowable의 모든 데이터가 조건에 맞는지 판단

- 결과로 Single 반환

- 통지한 데이터가 조건과 하나라도 다르다면 조건에 맞지 않는 데이터를 받은 시점에 false 통지

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/all.png" alt="img" style="zoom:50%;" />



### 4.5.4 sequenceEqual

두 Flowable이 같은 순서로 같은 수의 같은 데이터를 통지하는지 판단

- 결과로 Single 반환

- 데이터만 비교하고 통지 시점은 비교하지 않으므로 interval 메서드로 생성한 Flowable의 통지 간격이 다르더라도 조건에 맞으면 true 통지한다

- 모든 데이터가 동일하면 마지막 Flowable이 완료를 통지한 시점에 true를 통지한다.

- 하나라도 데이터가 다르다면 false 통지하며, 조건에 맞지 않는 데이터를 받은 시점에 false를 통지한다.

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/sequenceEqual.png" alt="img" style="zoom:50%;" />



### 4.5.5 count

Flowable의 데이터 개수 통지

- 결과로 Single 반환

- 데이터 개수 타입은 Long

- 데이터 개수 통지하는 시점은 완료 통지를 받은 시점

  <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/longCount.png" alt="img" style="zoom:50%;" />

  





## 4.6 Flowable/Observable 데이터를 집계하는 연산자

### 4.6.1 reduce/reduceWith

Flowable의 데이터를 계산하고 최종 집계 결과만 통지

- reduceWith : reduce가 인자로 초깃값 데이터를 받는 것과 달리 reduceWith메서드는 인자로 초깃값을 생성하는 함수형 인터페이스를 받는다.

```java
	Maybe<T> reduce(BiFunction<T,T,T> reducer)
	<R> Single<R> reduce(R seed, BiFunction<R,? super T,R> reducer)
```

 <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/reduce.png" alt="img" style="zoom:50%;" /> 

 <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/reduceSeed.png" alt="img" style="zoom:50%;" /> 



### 4.6.2 scan

Flowable의 데이터를 계산하고 각 계산 결과를 통지

- 원본 Flowable이 통지한 데이터를 인자의 함수형 인터페이스를 사용해 집계하는 연산자

- 계산할 때마다 생성되는 결괏값을 통지한다.

- 중간결과를 통지하지 않고 최종 집계 결과만 통지 : reduce

- 중간 결과를 통지 : scan

   <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/scan.png" alt="img" style="zoom:50%;" /> 



## 4.7 유틸리티 연산자

### 4.7.1 repeat

데이터 통지를 처음부터 반복

원본 Flowable이 처리를 완료하면 데이터 통지를 처음부터 반복하게 하는 연산자

- 인자가 없으면 완료하지 않고 데이터를 반복 통지

- 인자가 있으면 인자로 지정한 숫자만큼 데이터를 반복 통지

   <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/repeat.o.png" alt="img" style="zoom:50%;" /> 

### 4.7.2 repeatUntil

지정한 조건이 될 때까지 데이터 통지 반복

- 조건은 인자 없이 boolean 값을 반환하는 메서드를 가지는 함수형 인터페이스로 지정
- 조건을 판단하는 시점은 원본 Flowable이 완료를 통지하는 시점
- 판단 결과가 false면 통지를 반복하고 true면 완료를 통지하고 처리를 종료한다.

 

### 4.7.2 repeatWhen

반복 처리 정의 및 통지 반복

- 지정한 시점까지 원본 Flowable의 통지를 반복하는 연산자

- 반복여부는 인자인 함수형 인터페이스에서 판단

- 함수형 인터페이스는 완료를 통지하는 원본 Flowable을 인자로 받고 이를 변환해 반한한다.

- 이때 변환된 Flowable이 데이터를 통지하면 원본 Flowable의 데이터통지를 반복하고, 변환된 Flowable이 완료를 통지하면 결과로 완료를 통지하고 처리를 끝낸다.

- 함수형 인터페이스가 빈 Flowable을 반환하면 데이터 통지 없이 완료 통지하고 종료한다.

- 반복시점이 변환된 Flowable이 데이터를 통지한 시점이므로 delay메서드로 통지를 지연하면 반복 통지 시작을 늦출 수 있다.

- 반한된 Flowable이 원본 Flowable과 다른 스레드에서 작동하면 반복 도중에 완료를 통지할 수 있으므로 주의해야한다.

   <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/repeatWhen.f.png" alt="img" style="zoom:50%;" /> 

### 4.7.4 delay

데이터 통지 시점 지연

 

<img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/delay.png" alt="img" style="zoom:50%;" />

### 4.7.5 delaySubscription

처리 시작 지연

 <img src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/delaySubscription.png" alt="img" style="zoom:50%;" /> 