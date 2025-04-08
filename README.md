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
- El `Java Thread` es simplemente una envoltura alrededor del hilo del `SO`, por lo que `1 Java Thread = 1 OS Thread`.
- Recuerda: El OS Thread es la unidad del Scheduling.
- La memoria se determina cuando se inicia el proceso o se crea un hilo.

## Inbound/Outbound (entradas/salidas)

1. Sync
2. Async
3. non-blocking
4. non-blocking + async

![04.png](assets/section-01/04.png)

1. `Sync`, la primera es muy simple, una comunicación de bloqueo síncrono directo, que todos hemos estado haciendo. La
   aplicación envía una solicitud a otra aplicación. El hilo permanecerá inactivo hasta que reciba la respuesta, no
   puede hacer otra cosa. Se trata pues, de una `comunicación síncrona de bloqueo`.


2. `Async`, un hilo puede crear otro hilo para delegar la tarea y hacer las cosas de forma asíncrona. Pero, quien quiera
   que esté realizando la tarea desde su perspectiva, estará bloqueado de todos modos. Por ejemplo. Supongamos que
   quiero llamar a una compañía de seguros para hacerle ciertas preguntas, así que le digo a un amigo que lo haga por
   mí, mientras que yo realizo otras cosas. Mi amigo, realiza la llamada, así que es él quién va a tener que esperar a
   que le contesten el teléfono y le respondan las preguntas. En este caso, yo no estoy bloqueado, pero mi amigo sí.


3. `Non-blocking`, supongamos que vuelvo a llamar a la compañía de seguros para hacerle unas preguntas, pero la
   contestadora me indica que nadie está disponible en ese momento para atenderme, pero que deje mi número de teléfono
   para que apenas alguien esté disponible me llame. Mientras eso sucede, yo puedo seguir haciendo mis cosas con
   normalidad.<br><br>
   En este caso, envié una solicitud y no estoy bloqueado, pero después de algún tiempo se me notifica para indicar que
   ya están disponibles.<br><br>
   En una aplicación `no-bloqueante`, la aplicación envía la solicitud a otra aplicación, base de datos, etc. una vez
   enviada la petición, el hilo no se bloqueará, será libre de hacer lo que quiera, cualquier otra tarea. Si está
   disponible, el sistema operativo notificará al hilo diciéndole, oye, tenemos la respuesta.


4. `no-blocking + async`, es una combinación de `non-blocking + async`. Si tienes varias CPUs, ¿por qué un hilo tiene
   que hacer todo el trabajo?, también podemos tener más hilos.<br><br>
   Supongamos que llamo a la compañía de seguros y me dicen, dame tu número, te llamaremos. Ahora, en lugar de dar mi
   número, le doy el de mi amigo para que le devuelvan la llamada. Así que en este caso no estoy bloqueado, mi amigo
   tampoco está bloqueado, pero recibiría la llamada cuando la compañía de seguros esté disponible.<br><br>
   Entonces, aquí un hilo envía una petición a otra aplicación. La respuesta puede tardar un poco, llevará algún tiempo.
   Hasta entonces el hilo no está bloqueado. Cuando la respuesta vuelve, el SO notificará a un hilo diferente que maneje
   la respuesta para hacer uso de múltiples CPUs.

> La `programación reactiva` es un modelo de programación para simplificar la comunicación `asíncrona no bloqueante`.

