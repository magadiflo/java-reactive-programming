# [Mastering Java Reactive Programming [From Scratch]](https://www.udemy.com/course/complete-java-reactive-programming)

Curso tomado de `Udemy` del autor `Vinoth Selvaraj`.

---

## Process, Thread, CUP, RAM, Scheduler

Imaginemos que ha desarrollado una aplicación Java sencilla y la ha empaquetado como un archivo Jar. El archivo Jar se
encuentra ubicado en algún lugar del disco duro. Su programa no es más que un conjunto de instrucciones para ejecutar
en ese momento.

Ahora, cuando ejecutamos nuestra aplicación jar con el comando `java -jar my_app.jar`, nuestra aplicación se cargará en
la memoria creándose un proceso. Así que, `un proceso` es una instancia de un programa informático que tiene su propio
espacio de memoria aislado, que incluirá código, datos, otros recursos asignados por el sistema operativo como el socket
de memoria, etc.

El proceso es pesado, vive en la memoria ram y quien ejecuta las instrucciones es el CPU.

![01.png](assets/section-01/01.png)

### Process

- Una instancia de un programa de computadora
- Incluye código, recursos (alojados por el SO como memoria, sockets, etc.)
- Son de peso pesado.
- Es caro crear y destruir un proceso.
- Es caro, es decir, pesado porque consume mucha memoria.

### Thread

- Parte de un proceso
    * Un proceso puede contener uno o más hilos
- Los hilos dentro de un proceso pueden compartir el espacio de memoria.

### Scheduler

- El scheduler (programador) asignará el hilo a la CPU para su ejecución.
- El scheduler determinará cuánto tiempo puede ejecutarse el hilo.

Supongamos que tenemos un solo procesador, el scheduler dirá, hey hilo, ve y ejecuta durante algún tiempo. Es decir,
si tenemos un proceso (que tiene múltiples hilos) y solo contamos con un procesador (CPU), el scheduler cambiará entre
los múltiples hilos.

![02.png](assets/section-01/02.png)

Si se tienen dos procesadores (CPU), intentará asignar un hilo a cada procesador.

![03.png](assets/section-01/03.png)

Cuando tenemos un único procesador y cuando tienes múltiples programas ejecutándose como Chrome, IntelliJ IDEA, java
app, etc. cuando todos están en marcha y funcionando, cuando tienes múltiples procesos, todos y cada uno de los procesos
tendrán hilos. Así que todos esos hilos (Thread) estarán compitiendo por la CPU.

El scheduler del SO seguirá cambiando entre los hilos (hilos del núcleo o hilos del SO) para la ejecución. A esta acción
lo llamamos cambio de contexto. Así que, cuando se cambia de un hilo a otro, el hilo actual, el punto de ejecución, el
estado tiene que ser almacenado para que pueda ser reanudado más tarde desde el punto en que se detuvo.

Todo lo discutido hasta este punto, no es nada específico de Java. En términos generales todos los procesos funcionan
así.

### Java (Platform) Thread

- `Java Thread` fue introducido hace 25 años.


- Un `Java Thread` es simplemente una envoltura (`wrapper`) sobre un hilo del sistema operativo, por lo tanto:
  `1 Java Thread = 1 OS Thread`. Es decir, cuando creamos un `new Thread()` en Java, internamente se genera un hilo a
  nivel del sistema operativo que se comporta como una `unidad de ejecución independiente`.


- Recuerda: el `OS Thread` (hilo del sistema operativo) es la verdadera unidad de scheduling. En otras palabras, el
  sistema operativo es quien decide cuándo y cómo se ejecutan los hilos, no la JVM directamente.


- La memoria asociada a un hilo (como su pila) se asigna cuando se inicia el proceso o se crea un nuevo hilo.

### ¿Qué es un hilo (Thread)?

**Un `hilo` es la unidad más pequeña de ejecución dentro de un proceso**. Cada hilo tiene su propia secuencia de
instrucciones, su contador de programa y su pila, pero comparte la memoria y los recursos del proceso principal con
otros hilos.

Esto permite que varios hilos trabajen concurrentemente dentro del mismo programa, lo que es útil para hacer múltiples
tareas al mismo tiempo, como leer datos, procesarlos y escribir resultados, todo en paralelo.

En `Java`, cuando hablamos de un `Thread`, nos referimos a un hilo gestionado por el sistema operativo, ya que
`cada Java Thread está directamente asociado a un hilo del sistema operativo (OS Thread)`.

## Inbound/Outbound (Entradas/Salidas)

En programación (y en particular en sistemas de red, I/O y programación reactiva), es importante entender cuatro
conceptos fundamentales:

1. Síncrono (Sync)
2. Asíncrono (Async)
3. No bloqueante (Non-blocking)
4. No bloqueante + Asíncrono (Non-blocking + Async)

![04.png](assets/section-01/04.png)

### 1. Síncrono (Sync)

Una comunicación síncrona y bloqueante es la más simple y tradicional. La aplicación `app1` envía una solicitud a la
aplicación `app2`, y el hilo que hace la llamada queda bloqueado esperando una respuesta.

Ese hilo `no puede hacer nada más` hasta que reciba dicha respuesta.

> 🧠 Es lo que hacemos habitualmente con código como `String result = service.call();`

`Ejemplo real`: Llamas a una compañía de seguros y esperas en la línea hasta que alguien atienda y te responda. Mientras
tanto, no haces nada más.

### 2. Asíncrono (Async)

La `asincronía` significa que una operación se ejecuta `en otro momento`. Por lo general, se delega a otro hilo para que
se encargue de ella, y quien la delega puede continuar trabajando mientras tanto.

`Importante`: Aunque el hilo principal no esté bloqueado, `el hilo delegado sí lo puede estar`.

> 🧠 La asincronía se refiere al tiempo (“haz esto cuando puedas”), no al uso eficiente de hilos.

`Ejemplo real`: Quieres llamar a la compañía de seguros, pero le pides a un amigo que lo haga por ti. Tú puedes seguir
con tus cosas, pero ahora tu amigo está bloqueado esperando en la línea.

Notas adicionales:

- Puede haber asincronía bloqueante, como `Future.get()` (el hilo se detiene esperando el resultado).
- También puede haber asincronía no bloqueante, cuando se usan mecanismos como `CompletableFuture.thenApply()` o `Mono`.

### 3. No bloqueante (Non-blocking)

Una operación `no bloqueante` no detiene el hilo actual, sino que simplemente registra una acción a ejecutar cuando la
operación esté lista (usando callbacks, listeners, o Publishers como en Reactor).

> 🧠 Esto se refiere a `no bloquear el hilo actual`, pero no necesariamente implica asincronía.

`Ejemplo real`: Llamas a la compañía de seguros y te dice una contestadora: "Todos nuestros agentes están ocupados,
deja tu número y te llamaremos". Tú dejas tu número y sigues con tu día.
`Nadie (ni tú ni otro hilo) está esperando activamente`.

### 4. No bloqueante + Asíncrono (Non-blocking + Async)

Esta es la forma `más eficiente y escalable`: no solo el hilo actual no queda bloqueado, sino que otro hilo libre
manejará la respuesta cuando esté lista. Esto permite aprovechar múltiples núcleos de CPU de forma efectiva.

`Ejemplo real`: Llamas a la compañía de seguros, y en lugar de dejar tu número, le das el de tu amigo. Cuando estén
disponibles, lo llaman a él. Ni tú ni tu amigo están bloqueados activamente, pero cuando llegue la respuesta, otro
actor (tu amigo) la maneja.

> 🧠 Es lo que hace Reactor, usando `Schedulers`, `Operators` y `Event Loops`.

### Relación con la Programación Reactiva

La programación reactiva es un modelo diseñado para simplificar la comunicación asíncrona y no bloqueante, permitiendo
construir sistemas más eficientes, escalables y receptivos.

En `Project Reactor`:

- Un `Mono` o un `Flux` es siempre no bloqueante por diseño.
- Sin embargo, pueden ser síncronos o asíncronos, dependiendo de cómo se configure el pipeline:
    - Si usas `subscribeOn` o `publishOn`, cambias el hilo de ejecución y haces la operación asíncrona.
    - Si no cambias de hilo, puede comportarse de forma síncrona pero no bloqueante.

📌 Clave para entenderlo bien:

- `Asincronía es una estrategia de cuándo ejecutar algo`: no lo haces ahora mismo, sino que lo programas para más tarde
  (por ejemplo, en otro hilo o cuando haya disponibilidad).
- `No bloqueante se refiere a cómo se espera el resultado`: en lugar de detener el hilo mientras se completa una
  operación, se continúa con otras tareas y se registra una acción (como un callback) para manejar la respuesta cuando
  esté lista.

➕ Puedes tener:

- `Código asíncrono pero bloqueante`: Por ejemplo, usar `Future.get()` **bloquea el hilo que espera el resultado.**
- `Código no bloqueante pero síncrono`: Como `Mono.just("OK")`, que no bloquea, pero tampoco cambia de hilo (todo
  ocurre en el mismo hilo, inmediatamente).
- `Código asíncrono y no bloqueante`: Lo ideal en programación reactiva. Por ejemplo:
    ````bash
    Mono.fromCallable(() -> llamadaALaAPI())
        .subscribeOn(Schedulers.boundedElastic()) // se ejecuta en otro hilo (asincronía)
        .subscribe(resultado -> System.out.println("Recibido: " + resultado));
    ````
  Aquí, el trabajo se hace en un hilo aparte sin bloquear el principal. Reactor gestiona todo de forma eficiente y
  reactiva.

## Patrones de comunicación

A veces la gente se pregunta si realmente necesitamos la programación reactiva `¿Puedo no utilizar hilos virtuales?`
En realidad, los hilos virtuales son geniales, pero los hilos virtuales podrían no ser suficientes.

Intentamos resolver un problema distinto, voy a explicarlo ahora. Todos hemos estado escribiendo código usando el tipo
de comunicación `request -> response`. Es decir, enviamos una solicitud y esperamos una respuesta. Así es como hemos
estado escribiendo código durante muchos, muchos años.

Si su requisito es simple como este, entonces sí, el hilo virtual es suficiente. No es necesario complicarse con la
programación reactiva. Pero la programación reactiva abre la puerta a tres patrones de comunicación adicionales si se
quiere `request - response`, `request - streaming response`, `streaming-request - response` y
`bidirectional - streaming`. Así que podemos lograr cuatro patrones de comunicación diferentes utilizando la
programación reactiva fácilmente.

- El `request - streaming response`, envías una solicitud para la que obtienes respuesta múltiple. Por ejemplo,
  supongamos que está intentando pedir una pizza, así que envía una solicitud de pedido de pizza. Ahora, enviarán
  actualizaciones en tiempo real, como que la pizza se está preparando. Ahora está listo para su entrega. El conductor
  está a ocho kilómetros. Al cabo de un rato, el conductor está a cuatro millas.

  Así que te darán una respuesta en `streaming` a tu dispositivo móvil diciéndote que de acuerdo, ahora tu comida está
  entregada en tu puerta. Así que envías una petición, pero recibes una respuesta periódica en `streaming`.


- El `streaming-request - response`, enviará una solicitud de streaming al servidor remoto. Por ejemplo, puede que
  lleves
  un Apple Watch, imaginémoslo así, y sigue enviando la frecuencia cardiaca al servidor remoto. O estás trabajando en un
  documento de Google y cuando escribes algo, se guarda en el servidor remoto. Así que aquí recuerde que cuando decimos
  solicitud de streaming no estamos enviando múltiples solicitudes HTTP. Nos limitamos a abrir una única conexión a
  través
  de la cual enviamos múltiples mensajes de streaming al servidor
  remoto.


- El `streaming bidireccional`, permite que dos aplicaciones pueden hablar al igual que un ser humano en realidad pueden
  seguir intercambiando mensajes.

Este tipo de patrones de comunicación son posibles lograr fácilmente con la programación reactiva, mientras que el hilo
virtual, la concurrencia estructurada no resuelven estos problemas.

![05.png](assets/section-01/05.png)

## [Reactive Streams Specification](https://www.reactive-streams.org/)

`Reactive Streams` es una iniciativa que busca proporcionar un estándar para el procesamiento de flujos asíncronos con
contrapresión sin bloqueo. Esto abarca esfuerzos dirigidos a entornos de ejecución(JVM y JavaScript) así como a
protocolos de red.

Palabras claves: `Asíncrono`, `no bloqueante`, `contrapresión`.

![06.png](assets/section-01/06.png)

La especificación `Reactive Streams` define un conjunto estándar de interfaces para procesar flujos de datos de manera
asíncrona y sin bloqueo, con un control adecuado de la contrapresión `(backpressure)`. Las principales interfaces
incluidas en la especificación son las siguientes:

1. `Publisher<T>`:
    - Es el productor de datos. Emite una secuencia de elementos a los suscriptores interesados.
    - Tiene un único método. Este método permite a los suscriptores registrarse para recibir los elementos que el
      `Publisher` emitirá.
   ````java
   void subscribe(Subscriber<? super T> subscriber);
   ````

2. `Subscriber<T>`:
    - Es el consumidor de datos. Recibe los elementos emitidos por un `Publisher`.
    - Los métodos clave que implementa son:
   ````java
   void onSubscribe(Subscription s);
   void onNext(T t);
   void onError(Throwable t);
   void onComplete();
   ````
    - `onSubscribe(Subscription s)`: Se llama cuando el `Subscriber` se suscribe y recibe un `Subscription` para
      gestionar la comunicación con el `Publisher`.
    - `onNext(T t)`: Se invoca cuando hay un nuevo elemento disponible.
    - `onError(Throwable t)`: Se llama cuando ocurre un error durante el procesamiento del flujo.
    - `onComplete()`: Se invoca cuando el `Publisher` ha emitido todos los elementos.


3. `Subscription`:
    - Representa la relación entre un `Subscriber` y un `Publisher`.
    - Los métodos claves son:
   ````java
   void request(long n);
   void cancel();
   ````
    - `request(long n)`: Controla la cantidad de elementos que el `Subscriber` quiere recibir. Es fundamental para la
      gestión de la contrapresión.
    - `cancel()`: Cancela la suscripción, deteniendo la emisión de más elementos.


4. `Processor<T, R> (opcional)`:
    - Combina las funciones de un `Publisher` y un `Subscriber`. Un `Processor` actúa tanto como un suscriptor de un
      flujo de entrada como un publicador de un flujo de salida.
    - Es un intermediario entre productores y consumidores que permite realizar operaciones intermedias en el flujo de
      datos.

## ¿Qué es la Programación Reactiva?

Un paradigma de programación diseñado para procesar flujos de mensajes de manera asincrónica y sin bloqueos, al tiempo
que se controla la contrapresión.

- Se basa en el patrón de diseño Observer.
- Para llamadas Inbound/Outbound.
- `La programación Reactiva complementa la Programación Orientada a Objetos` al proporcionar herramientas y
  abstracciones potentes para manejar llamadas de E/S asíncronas y administrar flujos de datos complejos en aplicaciones
  modernas.

Los pilares de la programación reactiva son:

- Stream of messages
- Non-blocking
- Asynchronous
- Backpressure
