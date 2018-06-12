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
<hr/>

## String 类
1、String类是不可变类，持有一个不可变的字符数组的引用来保存整个字符串<br>
2、equals()方法比较数组长度和每个元素<br>
3、compareTo(anotherString)方法先比较共同长度部分，返回第一个不同字符的asci码减法结果，如果前面都相同则返回数组长度相减的结果<br>
4、trim()方法去掉字符串两端Unicode编码小于等于32(\u0020)的所有字符(包括空格)<br>
5、intern()方法用equals()去常量池中查找本字符串，如果找不到则加入方法区的常量池<br>
```Java
public final class String implements java.io.Serializable, Comparable<String>, CharSequence {

	//不可变字符数组
    private final char value[];

    //保存hash值,默认0
    private int hash;

    //构造器：无参数、或者调用Arrays工具类阶段字符数组或者byte数组
    public String() {
        this.value = "".value;
    }

    //返回字符数组长度
    public int length() {
        return value.length;
    }
    
    //比较是否相等
    //挨个比较字符数组中的每个元素和数组长度
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof String) {
            String anotherString = (String)anObject;
            int n = value.length;
            if (n == anotherString.value.length) {
                char v1[] = value;
                char v2[] = anotherString.value;
                int i = 0;
                while (n-- != 0) {
                    if (v1[i] != v2[i])
                        return false;
                    i++;
                }
                return true;
            }
        }
        return false;
    }


    //与equals()类似,但是这里要求比较对象是CharSequence子类
    public boolean contentEquals(CharSequence cs) {
        if (cs instanceof AbstractStringBuilder) {
            if (cs instanceof StringBuffer) {
                synchronized(cs) {
                   return nonSyncContentEquals((AbstractStringBuilder)cs);
                }
            } else {
                return nonSyncContentEquals((AbstractStringBuilder)cs);
            }
        }
        if (cs instanceof String) {
            return equals(cs);
        }
        char v1[] = value;
        int n = v1.length;
        if (n != cs.length()) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            if (v1[i] != cs.charAt(i)) {
                return false;
            }
        }
        return true;
    }
	
	//忽略大小写比较是否相等
    public boolean equalsIgnoreCase(String anotherString) {
        return (this == anotherString) ? true
                : (anotherString != null)
                && (anotherString.value.length == value.length)
                && regionMatches(true, 0, anotherString, 0, value.length);
    }

    //比较大小
    public int compareTo(String anotherString) {
        int len1 = value.length;
        int len2 = anotherString.value.length;
        //取长度最小值lim
        int lim = Math.min(len1, len2);
        char v1[] = value;
        char v2[] = anotherString.value;

        int k = 0;
        //从0到lim有不同的字符
        while (k < lim) {
            char c1 = v1[k];
            char c2 = v2[k];
            //ASCI码相减作为返回结果
            if (c1 != c2) {
                return c1 - c2;
            }
            k++;
        }
        //从0到lim字符均相同
        //长度相减并返回
        return len1 - len2;
    }

 
    //计算hash值
    public int hashCode() {
        int h = hash;
        //hash值默认为0,等于0说明还没计算过
        if (h == 0 && value.length > 0) {
            char val[] = value;

            for (int i = 0; i < value.length; i++) {
            	//计算过程
                //s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
                h = 31 * h + val[i];
            }
            hash = h;
        }
        return h;
    }

    //按指定字符串分割为数组
    public String[] split(String regex, int limit) {
        char ch = 0;
        //regex指定正则表达式
        if (((regex.value.length == 1 &&
             ".$|()[{^?*+\\".indexOf(ch = regex.charAt(0)) == -1) ||
             (regex.length() == 2 &&
              regex.charAt(0) == '\\' &&
              (((ch = regex.charAt(1))-'0')|('9'-ch)) < 0 &&
              ((ch-'a')|('z'-ch)) < 0 &&
              ((ch-'A')|('Z'-ch)) < 0)) &&
            (ch < Character.MIN_HIGH_SURROGATE ||
             ch > Character.MAX_LOW_SURROGATE))
        {
            int off = 0;
            int next = 0;
            boolean limited = limit > 0;
            //先按指定字符分割后加入ArrayList中
            ArrayList<String> list = new ArrayList<>();
            while ((next = indexOf(ch, off)) != -1) {
                if (!limited || list.size() < limit - 1) {
                    list.add(substring(off, next));
                    off = next + 1;
                } else {    // last one
                    //assert (list.size() == limit - 1);
                    list.add(substring(off, value.length));
                    off = value.length;
                    break;
                }
            }
            if (off == 0)
                return new String[]{this};
            if (!limited || list.size() < limit)
                list.add(substring(off, value.length));

            //从这里开始构造结果
            int resultSize = list.size();
            if (limit == 0) {
            	//找到n个ArrayList中长度为0的元素,则size-n
                while (resultSize > 0 && list.get(resultSize - 1).length() == 0) {
                    resultSize--;
                }
            }
            String[] result = new String[resultSize];
            //截取最终长度的子集,所有这里去掉了尾部的长度为0的元素
            return list.subList(0, resultSize).toArray(result);
        }
        return Pattern.compile(regex).split(this, limit);
    }

 
    //去掉字符串两端Unicode编码小于等于32（\u0020）的所有字符(包括空格)
    public String trim() {
        int len = value.length;
        int st = 0;
        char[] val = value;    /* avoid getfield opcode */

        while ((st < len) && (val[st] <= ' ')) {
            st++;
        }
        while ((st < len) && (val[len - 1] <= ' ')) {
            len--;
        }
        return ((st > 0) || (len < value.length)) ? substring(st, len) : this;
    }


    //将该字符串加入方法区的常量池
    //加入的条件是equasl()池中已有字符串均返回false
    public native String intern();
}
```
<hr/>

## AbstractStringBuilder
为什么字符串不能修改,为什么字符串拼接会产生新的字符串对象：<br>

字符串拼接、修改使得字符数组长度改变,比如数组扩容需要创建新的更长的字符数组，由于String的字符数组value域是final的,其字符数组不允许扩容成新的长数组,所以一个字符串对象所表示的字符串就不能修改。<br>

而字符构建器的字符数组引用不是final的,允许指向新的长数组。所以字符串拼接通常是利用字符构建器拼接成新的字符数组(当然会先到方法区常量池中找一下),再toString返回一个新的字符串,这也是编译器对字符串加操作的执行过程，原来的字符串引用指向上一步得到的新字符串。<br>
