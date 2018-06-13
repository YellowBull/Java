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
```Java
//给出了一系列的字符数组的操作方法用于字符串构建
abstract class AbstractStringBuilder implements Appendable, CharSequence {

    char[] value;//存储字符串(可变的)
    
    int count;//记录存储了多少个字符
   
    //下面两方法用于判断数组扩容
    public void ensureCapacity(int minimumCapacity) {
        if (minimumCapacity > 0)
            ensureCapacityInternal(minimumCapacity);
    }
    private void ensureCapacityInternal(int minimumCapacity) {
        if (minimumCapacity - value.length > 0)
            expandCapacity(minimumCapacity);
    }
    //数组扩容
    void expandCapacity(int minimumCapacity) {
    	//2倍+2
        int newCapacity = value.length * 2 + 2;
        if (newCapacity - minimumCapacity < 0)
            newCapacity = minimumCapacity;
        if (newCapacity < 0) {
            if (minimumCapacity < 0)
                throw new OutOfMemoryError();
            newCapacity = Integer.MAX_VALUE;
        }
        //创建新数组
        value = Arrays.copyOf(value, newCapacity);
    }
    //去掉数组尾部未使用的容量
    public void trimToSize() {
        if (count < value.length) {
            value = Arrays.copyOf(value, count);
        }
    }
    //强制增大count
    //会触发数组扩容函数
    //比原来count多出来的部分用null来初始化
    public void setLength(int newLength) {
        if (newLength < 0)
            throw new StringIndexOutOfBoundsException(newLength);
        ensureCapacityInternal(newLength);
        //从count~newCount用null来初始化数组元素
        if (count < newLength) {
            Arrays.fill(value, count, newLength, '\0');
        }
        count = newLength;
    }
    //将字符数组value倒叙
    public AbstractStringBuilder reverse() {
        boolean hasSurrogates = false;
        int n = count - 1;
        for (int j = (n-1) >> 1; j >= 0; j--) {
            int k = n - j;
            char cj = value[j];
            char ck = value[k];
            value[j] = ck;
            value[k] = cj;
            if (Character.isSurrogate(cj) ||
                Character.isSurrogate(ck)) {
                hasSurrogates = true;
            }
        }
        if (hasSurrogates) {
            reverseAllValidSurrogatePairs();
        }
        return this;
    }
    //倒叙帮助方法
    private void reverseAllValidSurrogatePairs() {
        for (int i = 0; i < count - 1; i++) {
            char c2 = value[i];
            if (Character.isLowSurrogate(c2)) {
                char c1 = value[i + 1];
                if (Character.isHighSurrogate(c1)) {
                    value[i++] = c1;
                    value[i] = c2;
                }
            }
        }
    }
    //将子字符数组复制到另外一个字符数组的dstBegin位置
    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)
    {
        if (srcBegin < 0)
            throw new StringIndexOutOfBoundsException(srcBegin);
        if ((srcEnd < 0) || (srcEnd > count))
            throw new StringIndexOutOfBoundsException(srcEnd);
        if (srcBegin > srcEnd)
            throw new StringIndexOutOfBoundsException("srcBegin > srcEnd");
        System.arraycopy(value, srcBegin, dst, dstBegin, srcEnd - srcBegin);
    }
    //指定下标范围的子字符数组,用另一个String替换
    public AbstractStringBuilder replace(int start, int end, String str) {
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        if (start > count)
            throw new StringIndexOutOfBoundsException("start > length()");
        if (start > end)
            throw new StringIndexOutOfBoundsException("start > end");
        if (end > count)
            end = count;
        int len = str.length();
        int newCount = count + len - (end - start);
        ensureCapacityInternal(newCount);
        //end开始的后半部分后移,腾出空位
        System.arraycopy(value, end, value, start + len, count - end);
        //将str的字符数组复制到另一个字符数组value中,且第一个字符保存到value的start位置
        //其实就是将str的字符数组填充到value在上一步腾出的空位上
        str.getChars(value, start);
        count = newCount;
        return this;
    }
    //获得指定下标范围的子字符数组
    @Override
    public CharSequence subSequence(int start, int end) {
        return substring(start, end);
    }
    public String substring(int start) {
        return substring(start, count);
    }
    public String substring(int start, int end) {
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        if (end > count)
            throw new StringIndexOutOfBoundsException(end);
        if (start > end)
            throw new StringIndexOutOfBoundsException(end - start);
        return new String(value, start, end - start);
    } 
    //删除指定下标范围的子数组
    public AbstractStringBuilder delete(int start, int end) {
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        if (end > count)
            end = count;
        if (start > end)
            throw new StringIndexOutOfBoundsException();
        int len = end - start;
        if (len > 0) {
        	//删除,后半部分前移
            System.arraycopy(value, start+len, value, start, count-end);
            count -= len;
        }
        return this;
    }   
    //下面是一系列字符串拼接,
    //提供多种重载,但是底层还是操作其字符数组,实现方式比较简单,其他省略
    public AbstractStringBuilder append(StringBuffer sb) {
        if (sb == null)
            return appendNull();
        int len = sb.length();
        ensureCapacityInternal(count + len);
        sb.getChars(0, len, value, count);
        count += len;
        return this;
    }
    //下面是一系列的插入子字符数组的方法
    //提供多种重载,但是底层还是操作其字符数组,实现方式比较简单,其他省略
    public AbstractStringBuilder insert(int index, char[] str, int offset,
                                        int len)
    {
        if ((index < 0) || (index > length()))
            throw new StringIndexOutOfBoundsException(index);
        if ((offset < 0) || (len < 0) || (offset > str.length - len))
            throw new StringIndexOutOfBoundsException(
                "offset " + offset + ", len " + len + ", str.length "
                + str.length);
        ensureCapacityInternal(count + len);
        System.arraycopy(value, index, value, index + len, count - index);
        System.arraycopy(str, offset, value, index, len);
        count += len;
        return this;
    }
    //下面是子字符数组下标获取相关方法,实现比较简单,省略
}
```
<hr/>

## StringBuilder 类

StringBuilder 覆盖了父类AbstractStringBuilder的一些字符数组操作的常用方法，基本上都依赖于父类实现。<br/>
值的注意的就是toString()方法将字符数组返回一个新的String对象，且方法均非线程安全。<br/>
```Java
public final class StringBuilder extends AbstractStringBuilder implements java.io.Serializable, CharSequence
{
	//构造器
    public StringBuilder() {
        super(16);
    }
    public StringBuilder(int capacity) {
        super(capacity);
    }
    public StringBuilder(String str) {
        super(str.length() + 16);
        append(str);
    }
    public StringBuilder(CharSequence seq) {
        this(seq.length() + 16);
        append(seq);
    }
    
    //拼接字符串
    //有多个重载,均是调用父类AbstractStringBuilder的实现,省略
    @Override
    public StringBuilder append(Object obj) {
        return append(String.valueOf(obj));
    }
    
    @Override
    public StringBuilder appendCodePoint(int codePoint) {
        super.appendCodePoint(codePoint);
        return this;
    }
    
    //删除字符
    @Override
    public StringBuilder deleteCharAt(int index) {
        super.deleteCharAt(index);
        return this;
    }

    //替换字符
    @Override
    public StringBuilder replace(int start, int end, String str) {
        super.replace(start, end, str);
        return this;
    }

    //插入字符
    //有多个重载,均是调用父类AbstractStringBuilder的实现,省略
    @Override
    public StringBuilder insert(int index, char[] str, int offset,
                                int len)
    {
        super.insert(index, str, offset, len);
        return this;
    }

    //下标相关
    //有多个重载,均是调用父类AbstractStringBuilder的实现,省略
    @Override
    public int indexOf(String str) {
        return super.indexOf(str);
    }

    //字符数组反序
    @Override
    public StringBuilder reverse() {
        super.reverse();
        return this;
    }

    //返回修改后的字符串对象
    @Override
    public String toString() {
        //这里创建了新的字符串
        return new String(value, 0, count);
    }

    private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException {
        s.defaultWriteObject();
        s.writeInt(count);
        s.writeObject(value);
    }
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
        s.defaultReadObject();
        count = s.readInt();
        value = (char[]) s.readObject();
    }
}
```
<hr/>

## StringBuffer 类
StringBuffer 如同StringBuilder 一样，覆盖了父类AbstractStringBuilder一系列字符数组的相关操作方法，底层也都是调用父类实现但是外层方法加了同步关键字保证线程安全。<br/>
```Java
public final class StringBuffer extends AbstractStringBuilder implements java.io.Serializable, CharSequence
{
	//字符数组,可变,不可序列化
	private transient char[] toStringCache;
	
	//构造器
	public StringBuffer() {
	    super(16);
	}
	public StringBuffer(int capacity) {
	    super(capacity);
	}
	public StringBuffer(String str) {
	    super(str.length() + 16);
	    append(str);
	}
	public StringBuffer(CharSequence seq) {
	    this(seq.length() + 16);
	    append(seq);
	}

	
	//拼接字符串
	//有一系列重载这里省略,均是利用父类实现,仅仅是添加了同步关键字
	@Override
	public synchronized StringBuffer append(Object obj) {
	    toStringCache = null;
	    super.append(String.valueOf(obj));
	    return this;
	}
	
	//获取字符
	@Override
	public synchronized void getChars(int srcBegin, int srcEnd, char[] dst,
	                                  int dstBegin)
	{
	    super.getChars(srcBegin, srcEnd, dst, dstBegin);
	}
	
	//修改字符
	@Override
	public synchronized void setCharAt(int index, char ch) {
	    if ((index < 0) || (index >= count))
	        throw new StringIndexOutOfBoundsException(index);
	    toStringCache = null;
	    value[index] = ch;
	}
	//删除字符
	@Override
	public synchronized StringBuffer deleteCharAt(int index) {
	    toStringCache = null;
	    super.deleteCharAt(index);
	    return this;
	}
	//替换字符
	@Override
	public synchronized StringBuffer replace(int start, int end, String str) {
	    toStringCache = null;
	    super.replace(start, end, str);
	    return this;
	}	
	//返回子字符数组得到的字符串
	@Override
	public synchronized String substring(int start) {
	    return substring(start, count);
	}
	//插入字符串
	//有一系列重载这里省略,均是利用父类实现,仅仅是添加了同步关键字
	@Override
	public synchronized StringBuffer insert(int index, char[] str, int offset,
	                                        int len)
	{
	    toStringCache = null;
	    super.insert(index, str, offset, len);
	    return this;
	}
	//非线程安全的insert方法
	//包含多个重载
	//由insert(int, String)调用实现同步
	@Override
	public StringBuffer insert(int offset, long l) {
	    super.insert(offset, l);
	    return this;
	}
	
	//数组下标相关
	//有一系列重载这里省略,均是利用父类实现,仅仅是添加了同步关键字
	@Override
	public int indexOf(String str) {
	    return super.indexOf(str);
	}

	//字符数组反序
	@Override
	public synchronized StringBuffer reverse() {
	    toStringCache = null;
	    super.reverse();
	    return this;
	}
	
	//用字符数组返回字符串
	@Override
	public synchronized String toString() {
	    if (toStringCache == null) {
	        toStringCache = Arrays.copyOfRange(value, 0, count);
	    }
	    //这里创建了新的字符串
	    return new String(toStringCache, true);
	}
	
	//序列化相关
	private static final java.io.ObjectStreamField[] serialPersistentFields =
	{
	    new java.io.ObjectStreamField("value", char[].class),
	    new java.io.ObjectStreamField("count", Integer.TYPE),
	    new java.io.ObjectStreamField("shared", Boolean.TYPE),
	};
	private synchronized void writeObject(java.io.ObjectOutputStream s)
	    throws java.io.IOException {
	    java.io.ObjectOutputStream.PutField fields = s.putFields();
	    fields.put("value", value);
	    fields.put("count", count);
	    fields.put("shared", false);
	    s.writeFields();
	}
	private void readObject(java.io.ObjectInputStream s)
	    throws java.io.IOException, ClassNotFoundException {
	    java.io.ObjectInputStream.GetField fields = s.readFields();
	    value = (char[])fields.get("value", null);
	    count = fields.get("count", 0);
	}
}
```
<hr/>

## 基本类型包装类
基本类型(byte char short int long float double boolean)的包装类均继承了Number接口。<br/>
用于将基本类型到引用类型的转换，下面给出部分分析。<br/>
### Byte：

不可变成员value保存实际值,在-128\~127之间。<br/>
hashCode()返回的就是value。equals()比较的就是value。<br/>
n转为无符号的int值或者long值时,与0xff做&操作,n为正返回n,n为负返回256+n。<br/>
申明静态成员内部类BateCache,使用数组缓存-128\~127的Byte对象，自动装箱会调用valueOf(),从数组缓存中找到并返回缓存对象，如果使用构造函数创建则不会用到缓存对象。<br/>
```Java
public final class Byte extends Number implements Comparable<Byte> {
	
    private final byte value;//保存byte值
	
    public static final byte   MIN_VALUE = -128;//-128~127
    public static final byte   MAX_VALUE = 127;
    
    @SuppressWarnings("unchecked") //返回其泛型化的Class对象
    public static final Class<Byte>  TYPE = (Class<Byte>) Class.getPrimitiveClass("byte");

    //hashCode
    @Override
    public int hashCode() {
        return Byte.hashCode(value);
    }
    public static int hashCode(byte value) {
    	//哈希值就是int类型的value值
        return (int)value;
    }
    
    //equals
    public boolean equals(Object obj) {
        if (obj instanceof Byte) {
        	//比较的是value,即基本类型的实际值
            return value == ((Byte)obj).byteValue();
        }
        return false;
    }

    //转为无符号int
    public static int toUnsignedInt(byte x) {
    	//0xff就是11111111
    	//x为正,结果为x
    	//x为负,结果为256+n
        return ((int) x) & 0xff;
    }
    //转为无符号long
    public static long toUnsignedLong(byte x) {
    	//0xffL就是11111111
        return ((long) x) & 0xffL;
    }
    
    //缓存静态成员内部类
    private static class ByteCache {
        private ByteCache(){}
        //类加载时将-128~127的Byte对象缓存到数组中,整个数组放在方法区的常量池
        static final Byte cache[] = new Byte[-(-128) + 127 + 1];
        static {
            for(int i = 0; i < cache.length; i++)
                cache[i] = new Byte((byte)(i - 128));
        }
    }
    //自动装箱会调用这个方法,下面两者在-128~127之间等价
    //Byte a = 20;
    //Byte a = Byte.valueOf(20)
    public static Byte valueOf(byte b) {
        final int offset = 128;
        //从缓存中获取
        //下面两个引用在-128~127之间==返回true
        //Byte a = 20;
        //Byte b = 20;
        return ByteCache.cache[(int)b + offset];
    }
}
```
Integer也类似，用不可变私有域value保存值，值的范围在-(2^31)到(2^32)-1之间。<br>
同样申明静态成员内部类使用数组缓存-128到127之间的Integer对象，用这个范围之间的基本数值使用自动装箱创建Integer对象会先调用valueOf从缓存中查找。<br>
<hr/>

## Thread 类

Thread实现了Runnable接口用来操作线程。<br>
提供了一系列基本native用来控制线程状态，然后封装了导出方法给客户端使用，源码较长且部分并没有看懂，以后再改。<br>
Thread类构造方法均调用了init()用来创建线程，线程优先级1~10默认5，使用枚举申明六个线程状态。<br>
持有一个ThreadLocal.ThreadLocalMap对象引用用来保存线程本地变量(这个Map每个Thread对象都有一个，使用ThreadLocal的弱引用作为key来保存变量值)<br>
重要的公共导出方法包括：<br>
yield()放弃cpu资源<br>
sleep()使线程休眠但是不释放锁<br>
start()调用native方法start0()开始运行线程<br>
run()执行Runnable对象的run()也就是执行任务方法<br>
interrupt()设置中断标志注意此方法与线程状态无关仅设置标志<br>
interrupted()检查是否设置过标志且可以清除标志<br>
setPriority()更改优先级<br>
join()等待子线程终止<br>
setDaemon()设置为守护线程<br>
holdsLock()检查是否持有指定锁。<br>
上述公有方法均是封装了一系列私有或者native方法，已废弃方法省略<br>
```Java
//函数式接口：允许使用lambda表达式(而非匿名类)方式给出方法实现
@FunctionalInterface
public interface Runnable {
    public abstract void run();
}
```
```Java
public class Thread implements Runnable {
	
    //确保此方法最先调用,做一些准备工作
    private static native void registerNatives();
    static {
        registerNatives();
    }
    //name
    private volatile char  name[];
    //优先级
    private int            priority;
    //是否守护线程
    private boolean     daemon = false;
    //虚拟机状态
    private boolean     stillborn = false;
    //线程实际任务
    private Runnable target;
    //当前线程所属线程组
    private ThreadGroup group;
    //线程类加载器
    private ClassLoader contextClassLoader;
    //断阻塞器：当线程发生IO中断时，需要在线程被设置为中断状态后调用该对象的interrupt方法
    volatile Object parkBlocker;   
    //阻塞器对象,如果线程使用LockSupport的park进行挂起(而不是wait挂起)
    //这个对象用来表示挂起原因,一般用来dump文件在出问题时方便分析
    private volatile Interruptible blocker;    
    //interrupt()使用它做锁对象
    private final Object blockerLock = new Object();    
    //保存线程本地变量的一个Map
    ThreadLocal.ThreadLocalMap threadLocals = null;
    //保存可继承的线程本地变量的一个Map
    ThreadLocal.ThreadLocalMap inheritableThreadLocals = null;
    //指定线程栈大小,一般默认为0并由jvm指定,且某些jvm不支持此属性
    private long stackSize;
    //线程id
    private long tid;
    //线程状态
    private volatile int threadStatus = 0;
    //线程状态枚举类
    public enum State {
        NEW,
        RUNNABLE,
        BLOCKED,
        WAITING,
        TIMED_WAITING,
        TERMINATED;
    }
    //线程优先级1~10,默认5
    public final static int MIN_PRIORITY = 1;
    public final static int NORM_PRIORITY = 5;
    public final static int MAX_PRIORITY = 10;
    
    //构造器
    //利用init()进行一系列重载,这里省略
    public Thread(ThreadGroup group, Runnable target, String name,long stackSize) {
    	// 分配新的 Thread 对象，以便将 target 作为其运行对象，将指定的 name 作为其名称
    	// 作为 group 所引用的线程组的一员，并具有指定的堆栈大小。
        init(group, target, name, stackSize);
    }
    //init
    //用于创建线程,构造器会用到
    private void init(ThreadGroup g, Runnable target, String name, long stackSize, AccessControlContext acc) {
        if (name == null) {
            throw new NullPointerException("name cannot be null");
        }
        this.name = name.toCharArray();
        Thread parent = currentThread();
        SecurityManager security = System.getSecurityManager();
        if (g == null) {
            if (security != null) {
                g = security.getThreadGroup();
            }
            if (g == null) {
                g = parent.getThreadGroup();
            }
        }
        g.checkAccess();
        if (security != null) {
            if (isCCLOverridden(getClass())) {
                security.checkPermission(SUBCLASS_IMPLEMENTATION_PERMISSION);
            }
        }
        g.addUnstarted();
        this.group = g;
        this.daemon = parent.isDaemon();
        this.priority = parent.getPriority();
        if (security == null || isCCLOverridden(parent.getClass()))
            this.contextClassLoader = parent.getContextClassLoader();
        else
            this.contextClassLoader = parent.contextClassLoader;
        this.inheritedAccessControlContext =
                acc != null ? acc : AccessController.getContext();
        this.target = target;
        setPriority(priority);
        if (parent.inheritableThreadLocals != null)
            this.inheritableThreadLocals =
                ThreadLocal.createInheritedMap(parent.inheritableThreadLocals);
        this.stackSize = stackSize;
        tid = nextThreadID();
    }
    //返回对当前正在执行的线程对象的引用。
    public static native Thread currentThread();
    //暂停当前正在执行的线程对象，并执行其他线程。
    public static native void yield();
    //线程休眠
    public static native void sleep(long millis) throws InterruptedException;
    //克隆
    protected Object clone() throws CloneNotSupportedException {
    	//直接抛出异常
        throw new CloneNotSupportedException();
    } 
    //开始运行线程
    public synchronized void start() {
        if (threadStatus != 0)
            throw new IllegalThreadStateException();
        group.add(this);
        boolean started = false;
        try {
            //调用start0(),由jvm调用其run()
            start0();
            started = true;
        } finally {
            try {
                if (!started) {
                    group.threadStartFailed(this);
                }
            } catch (Throwable ignore) {
            }
        }
    }
    private native void start0();
    //任务方法
    public void run() {
        if (target != null) {
            target.run();
        }
    }
    //退出
    private void exit() {
        if (group != null) {
            group.threadTerminated(this);
            group = null;
        }
        target = null;
        threadLocals = null;
        inheritableThreadLocals = null;
        inheritedAccessControlContext = null;
        blocker = null;
        uncaughtExceptionHandler = null;
    }
    //通知线程可以退出了,设置标志位,注意此方法与线程状态无关系,仅仅设置标志位
    public void interrupt() {
        if (this != Thread.currentThread())
            checkAccess();
        synchronized (blockerLock) {
            Interruptible b = blocker;
            if (b != null) {
                interrupt0(); 
                b.interrupt(this);
                return;
            }
        }
        interrupt0();
    }
    private native void interrupt0();
    //interrupted()：返回是否被通知退出过,此方法调用后会清除标志位,即消除通知,通常结合while实现线程的安全中断
    public static boolean interrupted() {
        return currentThread().isInterrupted(true);
    }
    public boolean isInterrupted() {
        return isInterrupted(false);
    }
    void blockedOn(Interruptible b) {
        synchronized (blockerLock) {
            blocker = b;
        }
    }
    private native boolean isInterrupted(boolean ClearInterrupted);
    //测试线程是否处于活动状态
    public final native boolean isAlive();
    //更改线程的优先级
    public final void setPriority(int newPriority) {
        ThreadGroup g;
        checkAccess();
        if (newPriority > MAX_PRIORITY || newPriority < MIN_PRIORITY) {
            throw new IllegalArgumentException();
        }
        if((g = getThreadGroup()) != null) {
            if (newPriority > g.getMaxPriority()) {
                newPriority = g.getMaxPriority();
            }
            setPriority0(priority = newPriority);
        }
    }
    //等待该线程终止
    public final synchronized void join(long millis) throws InterruptedException {
        long base = System.currentTimeMillis();
        long now = 0;
        if (millis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }
        if (millis == 0) {
            while (isAlive()) {
                wait(0);
            }
        } else {
            while (isAlive()) {
                long delay = millis - now;
                if (delay <= 0) {
                    break;
                }
                wait(delay);
                now = System.currentTimeMillis() - base;
            }
        }
    }
    public final synchronized void join(long millis, int nanos)
    throws InterruptedException {
        if (millis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }
        if (nanos < 0 || nanos > 999999) {
            throw new IllegalArgumentException(
                                "nanosecond timeout value out of range");
        }
        if (nanos >= 500000 || (nanos != 0 && millis == 0)) {
            millis++;
        }
        join(millis);
    }
    public final void join() throws InterruptedException {
        join(0);
    }
    //标记为守护线程或用户线程
    public final void setDaemon(boolean on) {
        checkAccess();
        if (isAlive()) {
            throw new IllegalThreadStateException();
        }
        daemon = on;
    }
    //检查可访问性
    public final void checkAccess() {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkAccess(this);
        }
    }
    //是否持锁：obj为指定的监视器对象
    public static native boolean holdsLock(Object obj);
    //返回该线程的状态
    public State getState() {
        // get current thread state
        return sun.misc.VM.toThreadState(threadStatus);
    }
    
    
    
    //以下代码还不知道是干嘛的
    private long           eetop;
    private boolean     single_step;
    private AccessControlContext inheritedAccessControlContext;
    private static int threadInitNumber;
    
    private static synchronized int nextThreadNum() {
        return threadInitNumber++;
    }
    private static long threadSeqNumber;
    private static synchronized long nextThreadID() {
        return ++threadSeqNumber;
    }
    private long nativeParkEventPointer;
    private static final RuntimePermission SUBCLASS_IMPLEMENTATION_PERMISSION =
            new RuntimePermission("enableContextClassLoaderOverride");


    private static class Caches {
        static final ConcurrentMap<WeakClassKey,Boolean> subclassAudits =
            new ConcurrentHashMap<>();
        static final ReferenceQueue<Class<?>> subclassAuditsQueue =
            new ReferenceQueue<>();
    }

    private static boolean isCCLOverridden(Class<?> cl) {
        if (cl == Thread.class)
            return false;
        processQueue(Caches.subclassAuditsQueue, Caches.subclassAudits);
        WeakClassKey key = new WeakClassKey(cl, Caches.subclassAuditsQueue);
        Boolean result = Caches.subclassAudits.get(key);
        if (result == null) {
            result = Boolean.valueOf(auditSubclass(cl));
            Caches.subclassAudits.putIfAbsent(key, result);
        }
        return result.booleanValue();
    }

    private static boolean auditSubclass(final Class<?> subcl) {
        Boolean result = AccessController.doPrivileged(
            new PrivilegedAction<Boolean>() {
                public Boolean run() {
                    for (Class<?> cl = subcl;
                         cl != Thread.class;
                         cl = cl.getSuperclass())
                    {
                        try {
                            cl.getDeclaredMethod("getContextClassLoader", new Class<?>[0]);
                            return Boolean.TRUE;
                        } catch (NoSuchMethodException ex) {
                        }
                        try {
                            Class<?>[] params = {ClassLoader.class};
                            cl.getDeclaredMethod("setContextClassLoader", params);
                            return Boolean.TRUE;
                        } catch (NoSuchMethodException ex) {
                        }
                    }
                    return Boolean.FALSE;
                }
            }
        );
        return result.booleanValue();
    } 

    static void processQueue(ReferenceQueue<Class<?>> queue,ConcurrentMap<? extends WeakReference<Class<?>>, ?> map)
    {
        Reference<? extends Class<?>> ref;
        while((ref = queue.poll()) != null) {
            map.remove(ref);
        }
    }

    static class WeakClassKey extends WeakReference<Class<?>> {

        private final int hash;

        WeakClassKey(Class<?> cl, ReferenceQueue<Class<?>> refQueue) {
            super(cl, refQueue);
            hash = System.identityHashCode(cl);
        }
        @Override
        public int hashCode() {
            return hash;
        }
        @Override
        public boolean equals(Object obj) {
            if (obj == this)
                return true;
            if (obj instanceof WeakClassKey) {
                Object referent = get();
                return (referent != null) &&
                       (referent == ((WeakClassKey) obj).get());
            } else {
                return false;
            }
        }
    }
    
    /** The current seed for a ThreadLocalRandom */
    @sun.misc.Contended("tlr")
    long threadLocalRandomSeed;

    /** Probe hash value; nonzero if threadLocalRandomSeed initialized */
    @sun.misc.Contended("tlr")
    int threadLocalRandomProbe;

    /** Secondary seed isolated from public ThreadLocalRandom sequence */
    @sun.misc.Contended("tlr")
    int threadLocalRandomSecondarySeed;
}
```
<hr/>

## ThreadLocal 类
线程隔离变量实现方式：Thread持有一个ThreadLocalMap的引用，key是申明变量时的ThreadLocal对象，value是要保存的数据对象。所以真正保存的数据在线程对象里。<br/>

ThreadLocalMap：<br/>
是ThreadLocal的静态成员内部类，实现了一个map，嵌套实现了一个Entry节点类(key为ThreadLocal对象的弱引用，value保存数据对象)。这个map使用Entry循环数组组成一张table，首先计算key的hash值找到数组下标，然后利用线性探测法寻找临近的空位。数组容量为2的幂次方(方便与hash值求模得到均匀的数组下标)，扩容阈值为长度的三分之二。提供了系列操作节点的方法。<br/>

弱引用：<br/>
Entry节点类继承自WeakReference<T>，接口方法get()返回T对象的引用，这里T类型ThreadLocal的对象引用变为弱引用。因为ThreadLocal对象如果使用强引用会造成生命周期与线程绑定，而现在线程通常需要复用会造成ThreadLocal对象一直可达而不被GC。ThreadLocal对象使用弱引用的话，在没有强引用它时通常其对象会在下一次GC被清理掉而使得key为null，我们可以手动设置value为null断开数据对象的强引用。<br/>

ThreadLocal：<br/>
除实现了静态成员内部类ThreadLocalMap之外，仅包含几个方法用于操作Thread的ThreadLocalMap成员来保存数据对象。值的注意的是他计算hash从id累加魔数而来，这样得到的hash值可以与数组长度求模，均匀映射到长度为2的幂次方的数组下标上。<br/>

ThreadLocalMap 源码<br/>

```Java
//Map：环形的键值对数组
static class ThreadLocalMap { 
	
	//初始容量
    private static final int INITIAL_CAPACITY = 16;
    //节点数组
    private Entry[] table;
    //容量实际使用值
    private int size = 0;
    //扩容阈值,容量的2/3
    private int threshold;
  
	//Entry节点类
    //通常线程需要复用而生命周期长,Entry键值对与线程之间的引用关系使得Entry难以被回收
    //对象强引用链不可达通常活不过下一次GC,实现这个接口的对象就复合这种要求
	static class Entry extends WeakReference<ThreadLocal<?>> {
        //需要保存的值
        Object value;
        //key：ThreadLocal对象的引用,value：需要保存的值
        Entry(ThreadLocal<?> k, Object v) {
            super(k);
            value = v;
        }
    }
    
    //构造器
    ThreadLocalMap(ThreadLocal<?> firstKey, Object firstValue) {
    	//创建节点数组ThreadLocal引用作为键,hash值来计算数组下标
        table = new Entry[INITIAL_CAPACITY];
        //计算数组下标
        int i = firstKey.threadLocalHashCode & (INITIAL_CAPACITY - 1);
        //添加元素
        table[i] = new Entry(firstKey, firstValue);
        //改变size
        size = 1;
        //计算扩容阈值
        setThreshold(INITIAL_CAPACITY);
    }
    private ThreadLocalMap(ThreadLocalMap parentMap) {
        Entry[] parentTable = parentMap.table;
        int len = parentTable.length;
        setThreshold(len);
        table = new Entry[len];
        for (int j = 0; j < len; j++) {
            Entry e = parentTable[j];
            if (e != null) {
                @SuppressWarnings("unchecked")
                ThreadLocal<Object> key = (ThreadLocal<Object>) e.get();
                if (key != null) {
                    Object value = key.childValue(e.value);
                    Entry c = new Entry(key, value);
                    int h = key.threadLocalHashCode & (len - 1);
                    while (table[h] != null)
                        h = nextIndex(h, len);
                    table[h] = c;
                    size++;
                }
            }
        }
    }
	
    //计算扩容阈值
    private void setThreshold(int len) {
        threshold = len * 2 / 3;
    }
    //环形数组下标移动：用于线性探测法寻找临近的空位
    private static int nextIndex(int i, int len) {
        return ((i + 1 < len) ? i + 1 : 0);
    }
    private static int prevIndex(int i, int len) {
        return ((i - 1 >= 0) ? i - 1 : len - 1);
    }
    //重新计算hash值
    private void rehash() {
    	//清理数组上无效key引用映射的value
        expungeStaleEntries();
        //可能使得容量实际使用小很多,所以将扩容阈值调低,然后在判断是否需要扩容
        if (size >= threshold - threshold / 4)
        	//扩容
            resize();
    }
    //数组扩容
    private void resize() {
        Entry[] oldTab = table;
        int oldLen = oldTab.length;
        int newLen = oldLen * 2;
        Entry[] newTab = new Entry[newLen];
        int count = 0;

        for (int j = 0; j < oldLen; ++j) {
            Entry e = oldTab[j];
            if (e != null) {
                ThreadLocal<?> k = e.get();
                if (k == null) {
                    e.value = null;
                } else {
                    int h = k.threadLocalHashCode & (newLen - 1);
                    while (newTab[h] != null)
                        h = nextIndex(h, newLen);
                    newTab[h] = e;
                    count++;
                }
            }
        }
        setThreshold(newLen);
        size = count;
        table = newTab;
    }

    //获取节点
    //会被ThreadLocal的get方法直接调用,用于获取map中某个ThreadLocal存放的值
    private Entry getEntry(ThreadLocal<?> key) {
    	//ThreadLocal为键
    	//使用键的hash值计算数组下标
        int i = key.threadLocalHashCode & (table.length - 1);
        Entry e = table[i];
        //因为是线性探测法,一个key的hash值会映射到多个数组下标,首先正向映射找到节点,再反向映射判断是否是我们查找的节点
        if (e != null && e.get() == key)
            return e;
        else
        	//未直接命中,向后寻找
            return getEntryAfterMiss(key, i, e);
    }
    //未直接命中,向后寻找
    private Entry getEntryAfterMiss(ThreadLocal<?> key, int i, Entry e) {
        Entry[] tab = table;
        int len = tab.length;
        while (e != null) {
            ThreadLocal<?> k = e.get();
            if (k == key)
                return e;
            if (k == null)
                expungeStaleEntry(i);
            else
                i = nextIndex(i, len);
            e = tab[i];
        }
        return null;
    }

    //保存变量
    private void set(ThreadLocal<?> key, Object value) {
        Entry[] tab = table;
        int len = tab.length;
        //ThreadLocal为key,通过key的hash值找到数组下标
        int i = key.threadLocalHashCode & (len-1);
        for (Entry e = tab[i];
             e != null;
        	 //该位置不为空,看下一个位置是否为空
             e = tab[i = nextIndex(i, len)]) {
            ThreadLocal<?> k = e.get();
            if (k == key) {
            	//覆盖原有value
                e.value = value;
                return;
            }
            if (k == null) {
                replaceStaleEntry(key, value, i);
                return;
            }
        }
        tab[i] = new Entry(key, value);
        int sz = ++size;
        if (!cleanSomeSlots(i, sz) && sz >= threshold)
            rehash();
    }
    //删除变量
    private void remove(ThreadLocal<?> key) {
        Entry[] tab = table;
        int len = tab.length;
        int i = key.threadLocalHashCode & (len-1);
        //同样,没找到继续向后找
        for (Entry e = tab[i];
             e != null;
             e = tab[i = nextIndex(i, len)]) {
            if (e.get() == key) {
                e.clear();
                expungeStaleEntry(i);
                return;
            }
        }
    }
 
    //清理
    private boolean cleanSomeSlots(int i, int n) {
        boolean removed = false;
        Entry[] tab = table;
        int len = tab.length;
        do {//挨个向后
            i = nextIndex(i, len);
            Entry e = tab[i];
            //找到某个有效Entry(Enter不为空或者threadLocal也还没被GC掉)
            if (e != null && e.get() == null) {
                n = len;
                removed = true;
                //从这个位置开始清理一个连续段
                i = expungeStaleEntry(i);
            }
        } while ( (n >>>= 1) != 0);
        return removed;
    }

    //清理节点
    //key是弱引用被GC掉,那么value由我们显示断开强引用
    private int expungeStaleEntry(int staleSlot) {
        Entry[] tab = table;
        int len = tab.length;
        //显示断开value的强引用
        tab[staleSlot].value = null;
        tab[staleSlot] = null;
        size--;
        Entry e;
        int i;
        //向后遍历,遍历连续段的有Entry对象的数组位置
        for (i = nextIndex(staleSlot, len);(e = tab[i]) != null;i = nextIndex(i, len)) {
            ThreadLocal<?> k = e.get();
            //Entry不为空,但是key(ThreadLocal)为空(已经被GC掉),则将value置空
            if (k == null) {
                e.value = null;
                tab[i] = null;
                size--;
            } else {
                int h = k.threadLocalHashCode & (len - 1);
                if (h != i) {
                    tab[i] = null;
                    while (tab[h] != null)
                        h = nextIndex(h, len);
                    tab[h] = e;
                }
            }
        }
        return i;
    }
    //清理数组
    private void expungeStaleEntries() {
        Entry[] tab = table;
        int len = tab.length;
        for (int j = 0; j < len; j++) {
            Entry e = tab[j];
            if (e != null && e.get() == null)
                expungeStaleEntry(j);
        }
    }
}
```

ThreadLocal 源码<br/>

```Java
public class ThreadLocal<T> {

	//hash值：这个值与2的幂次方取模(数组长度)可以得到一个均匀的结果
    private final int threadLocalHashCode = nextHashCode();
    private static int nextHashCode() {
    	//id累加魔数,得到一个hash值
        return nextHashCode.getAndAdd(HASH_INCREMENT);
    }
    //id
    private static AtomicInteger nextHashCode = new AtomicInteger();
    //魔数
    private static final int HASH_INCREMENT = 0x61c88647;
    //构造器
    public ThreadLocal() {
    }
    protected T initialValue() {
        return null;
    }
    public static <S> ThreadLocal<S> withInitial(Supplier<? extends S> supplier) {
        return new SuppliedThreadLocal<>(supplier);
    }
    
    //get
    public T get() {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null) {
            ThreadLocalMap.Entry e = map.getEntry(this);
            if (e != null) {
                @SuppressWarnings("unchecked")
                T result = (T)e.value;
                return result;
            }
        }
        return setInitialValue();
    }
    //set
    public void set(T value) {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
    }
    private T setInitialValue() {
        T value = initialValue();
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
        return value;
    }
     //remove
     public void remove() {
         ThreadLocalMap m = getMap(Thread.currentThread());
         if (m != null)
             m.remove(this);
     } 
    //返回Map
    ThreadLocalMap getMap(Thread t) {
        return t.threadLocals;
    }
    //创建Map
    //第一次保存数据的时候调用			
    void createMap(Thread t, T firstValue) {
        t.threadLocals = new ThreadLocalMap(this, firstValue);
    }
    
    static ThreadLocalMap createInheritedMap(ThreadLocalMap parentMap) {
        return new ThreadLocalMap(parentMap);
    }
    T childValue(T parentValue) {
        throw new UnsupportedOperationException();
    }
    static final class SuppliedThreadLocal<T> extends ThreadLocal<T> {
        private final Supplier<? extends T> supplier;
        SuppliedThreadLocal(Supplier<? extends T> supplier) {
            this.supplier = Objects.requireNonNull(supplier);
        }
        @Override
        protected T initialValue() {
            return supplier.get();
        }
    }
}
```