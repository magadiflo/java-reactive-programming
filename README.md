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

