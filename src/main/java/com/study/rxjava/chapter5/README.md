# 5. Processor와 Subject

## 5.1 Processor/Subject
### 5.1.1 Processor/Subject란

Processor는 생산자를 나타내는 Publish 인터페이스와 소비자를 나타내는 Subscriber인터페이스를 모두 상속하는 인터페이스

- 소비자로서 데이터를 받고 이 데이터를 생산자로서 통지하는 역할
- 생산자와 소비자 사이에서 통지 중개 역할

Processor가 직접 Publisher를 구독하지 않아도 Processor의 onNext메서드에 직접 데이터를 전달하면 구독하는 것과 같은 의미가 돼 Processor를 구독하는 Subscriber에게 해당 데이터가 통지된다.

- 여러 스레드에서 Processor에 데이터를 전달한다면 데이터를 통지하는 onNext 메서드가 동시 호출돼 Reactive Streams 규칙과 Observable 규약을 따르지 않게 되므로 정상적인 통지가 이루어지지 않는다.
- Processor를 다룰 때는 이러한 통지 시 규칙이 깨질 가능성이 크므로 어떻게 통지 메서드를 호출할지 주의 깊게 생각하고 사용해야 한다.

FlowableProcessor = Flowable를 상송받음

Subject = FlowableProcessor - 배압기능, 생산자인 Observable 기능과 소비자인 Observer 기능을 이어받음

- Reactive Stream의 Processor와 다르게 전달받은 데이터의 타입과 통지하는 데이터의 타입이 같다



### 5.1.2 Processor/Subject 종류

PublishProcessor : 데이터를 받은 시점에 데이터 통지

BehaviorProcessor : 마지막으로 받은 데이터를 캐시하고 구독 시점에 캐시한 데이터를 바로 통지. 그 이후부터는 데이터를 받은 시점에 통지

ReplacyProcessor : 모든 데이터를 캐시하고 구독 시점에 캐시한 데이터 바로 통지. 그 이후 데이터는 받은 시점에 통지

AsyncProcessor : 완료 통지를 받았을 때 마지막으로 받은 데이터만 통지

UnicastProcessor : 하나의 소비자만 구독할 수 있음

#### FlowableProcessor/Subject 메서드

```java
boolean hasComplete() // 완료 통지되면 true 반환
boolean hasThrowable() // 에러가 통지되면 true 반환
Throwable getThrowable() // 에러가 통지되면 해당 에러 객체 반환하고 에러가 통지되지 않으면 null 반환
boolean hasSubscribers() 
boolean hasObservers() // 구독 중인 Subscriber/Observer가 있으면 true 반환, 완료나 에러 통지 후에는 false 반환
```



#### toSerialized 메서드

하나의 Processor/Subject는 서로 다른 스레드에서 동시에 통지하는 것을 허용하지 않음

-> 소비자에게 순차적으로 통지가 가지 않을 수 있어서 RxJava의 안정성을 위협함

그래서 Processor/Subject를 스레드에 안전하게 만들기 위해 SerializedProcessor/SerializedSubject 클래스 제공

- 해당 클래스는 패키지 프라이빗한 클래스이므로 직접 접근이 불가능
- Processor/Subject의 toSerialized 메서드 호출해야함

내부적으로 원래 Processor/Subject를 래핑해 통지 처리를 동기화한다.

동기 처리는 비용이 많이 드는 작업 이라서 통지량이 많은 상황이라면 성능에 영향을 줄 수 있음



## 5.2 PublishProcessor/PublishSubject

- 이미 통지한 데이터는 캐시하지 않음

- 구독한 뒤로 받은 데이터만 통지

- 완료 후 구독하면 완료만 에러통지를 소비자가 받을 수 있음

  ![img](https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/PublishProcessor.png)

## 5.3 BehaviorProcessor/BehaviorSubject

- 마지막으로 통지한 데이터를 캐시하고 구독시 캐시된 데이터를 소비자에게 통지

- 소비자는 구독할 때 캐시된 데이터를 먼저 받고 그이후에는 통지 시점에 데이터를 받음

- 처리가 끝나고 구독하면 완료나 에러만 통지

  ![img](https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/S.BehaviorProcessor.png)



## 5.4 ReplayProcessor/ReplaySubject

- 통지한 데이터 모두 또는 지정한 범위까지 캐시

- 구독 시점에 바로 캐시된 데이터를 소비자에게 통지

- 완료한 후 구독하면 캐시된 모든 데이터와 완료나 에러를 소비자에게 통지

- 모든 데이터 캐시하면 통지할 데이터가 증가함에 따라 메모리 사용량이 증가하므로 캐시할 범위를 설정하는 것이 좋음

  ![img](https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/ReplayProcessor.u.png)



## 5.5 AsyncProcessor/AsyncSubject

- 완료할 때까지 아무것도 통지하지 않다가 완료했을 떄 마지막으로 통지한 데이터와 완료만 통지

- 완료 후에 구독한 소비자는 마지막으로 통지된 데이터와 완료를 통지 받음

- 에러가 발생하면 에러만 통지

  ![img](https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/AsyncProcessor.png)



## 5.6 UnicastProcessor/UnicastSubject

- 1개의 소비자만 구독할 수 있음
- 다른 소비자가 구독하면 IllegalStateException에러가 소비자에게 통지
- 통지한 데이터는 캐시되며 소비자가 구독한 시점에 캐시된 데이터가 통지됨
- 완료 후 구독하면 캐시된 데이터와 완료를 통지
- 에러 통지할 때도 캐시한 데이터와 에러를 통지

![img](https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/UnicastProcessor.png)