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
	
	//保存byte值
    private final byte value;

	//-128~127
    public static final byte   MIN_VALUE = -128;
    public static final byte   MAX_VALUE = 127;
    
    //返回其泛型化的Class对象
    @SuppressWarnings("unchecked")
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