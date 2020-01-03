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