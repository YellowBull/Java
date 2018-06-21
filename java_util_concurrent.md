# java.util.concurrent

## 阻塞队列BlockingQueue

java.util.concurrent 包里的 BlockingQueue 接口表示一个线程安放入和提取实例的队列。本小节我将给你演示如何使用这个 BlockingQueue。<br/>

本节不会讨论如何在 Java 中实现一个你自己的 BlockingQueue。如果你对那个感兴趣，参考[《Java 并发指南》](http://tutorials.jenkov.com/java-concurrency/index.html)<br/>

### BlockingQueue用法
BlockingQueue 通常用于一个线程生产对象，而另外一个线程消费这些对象的场景。下图是对这个原理的阐述<br/>
![](https://img-blog.csdn.net/20150302184203260?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZGVmb25kcw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)<br/>
一个线程往里边放，另外一个线程从里边取的一个 BlockingQueue。<br/>

一个线程将会持续生产新对象并将其插入到队列之中，直到队列达到它所能容纳的临界点。也就是说，它是有限的。如果该阻塞队列到达了其临界点，负责生产的线程将会在往里边插入新对象时发生阻塞。它会一直处于阻塞之中，直到负责消费的线程从队列中拿走一个对象。<br/>

负责消费的线程将会一直从该阻塞队列中拿出对象。如果消费线程尝试去从一个空的队列中提取对象的话，这个消费线程将会处于阻塞之中，直到一个生产线程把一个对象丢进队列。<br/>

### BlockingQueue的方法
BlockingQueue 具有 4 组不同的方法用于插入、移除以及对队列中的元素进行检查。如果请求的操作不能得到立即执行的话，每个方法的表现也不同。这些方法如下：<br/>

|操作	|抛异常	   |特定值		|阻塞	|超时 						 |
|:-----:|:-----:   |:----------:|:-----:|:--------------------------:|
|插入	|add(o)	   |offer(o)	|put(o)	|offer(o, timeout, timeunit) |
|移除	|remove(o) |poll(o)		|take(o)|poll(timeout, timeunit)	 |
|检查	|element(o)|peek(o)		|不可用	|不可用						 |

四组不同的行为方式解释：<br/>

* 抛异常：如果试图的操作无法立即执行，抛一个异常。<br/>
* 特定值：如果试图的操作无法立即执行，返回一个特定的值(常常是 true / false)。<br/>
* 阻塞：如果试图的操作无法立即执行，该方法调用将会发生阻塞，直到能够执行。<br/>
* 超时：如果试图的操作无法立即执行，该方法调用将会发生阻塞，直到能够执行，但等待时间不会超过给定值。返回一个特定值以告知该操作是否成功(典型的是 true / false)。<br/>
