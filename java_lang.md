# Java.lang 源码学习

## 以下是Java.lang包中的类
1、Object<br>
2、String<br>
3、AbstractStringBuilder<br>
4、StringBuiler<br>
5、StringBuffer<br>
6、基本类型的包装类<br>
7、Thread<br>
8、ThreadLocal<br>
9、Enum<br>
10、Throwable<br>
11、Error和Exception<br>
12、Class<br>
13、ClassLoader<br>
14、Compiler<br>
15、System<br>
16、Package<br>
17、Void<br>

<hr/>

## Object 类
Object 类比较简单，大部分基本方法是native方法<br>

需要注意的是以下几点:<br>
equals默认以'=='的方式来判断（'=='是值的比较，但是equals方法可以被重写）<br>
getClass\<T\>返回的是运行时的Class对象<br>
wait()不释放锁切抛出interruptedException<br>
notify()唤醒以本对象为锁且等待中的线程(仅让他加入竞争池)<br>
clone()默认仅浅拷贝且调用时检查是否实现Cloneable接口<br>

```Java
public class Object {

	//初始化java方法映射到C
    private static native void registerNatives();
    static {
        registerNatives();
    }

    //返回Class对象,注意返回的是运行时类型的Class对象
    //A a = new B();
    //a.getClass()结果为B
    public final native Class<?> getClass();

    //返回对象hash值
    public native int hashCode();

    //比较
    //默认是比较引用对象地址
    public boolean equals(Object obj) {
        return (this == obj);
    }

    //克隆
    //默认浅拷贝(引用类型的成员不会内存复制)
    //这个方法通常由子类覆盖,先super.clone()浅拷贝,在扩展实现深拷贝
    protected native Object clone() throws CloneNotSupportedException;

    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }

    //唤醒一个在当前对象监视器上等待的线程
    public final native void notify();

    //唤醒所有在当前对象监视器上等待的线程
    public final native void notifyAll();
    
    //无用对象被清理时调用
    //jvm不保证此方法会走完
    protected void finalize() throws Throwable { }

    //将当前线程停止一段时间,不释放锁
    //这个方法会抛出异常
    public final native void wait(long timeout) throws InterruptedException;
    public final void wait(long timeout, int nanos) throws InterruptedException {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }
        if (nanos < 0 || nanos > 999999) {
            throw new IllegalArgumentException(
                                "nanosecond timeout value out of range");
        }
        if (nanos > 0) {
            timeout++;
        }
        wait(timeout);
    }
    public final void wait() throws InterruptedException {
        wait(0);
    }
}
```
## String 类