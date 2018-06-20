# Java.util 学习

### java.util包的基本层次结构图

```Java
	  ┌java.util.BitSet
　　　　　　　　　　　│java.util.Calendar
　　　　　　　　　　　│　　　　　　└java.util.GregorianCalendar
　　　　　　　　　　　│java.util.Date
　　　　　　　　　　　│java.util.Dictionary
　　　　　　　　　　　│　　　　　　└java.util.Hashtable
　　　　　　　　　　　│　　　　　　　　　　　　　└java.util.Properties
　　　　　　　　　　　│java.util.EventObject
　　　　　　　　　　　│java.util.ResourceBundle
　　　　　　　┌普通类 ┤　　　　　　├java.util.ListResourceBundle
　　　　　　　│　　　 │　　　　　　└java.util.PropertyResourceBundle
　　　　　　　│　　　 │java.util.Local
　　　　　　　│　　　 │java.util.Observable
　　　　　　　│　　　 │java.util.Random
　　　　　　　│　　　 │java.util.StringTokenizer
　　　　　　　│　　　 │java.util.Vector
　　　　　　　│　　　 │　　　　　　└java.util.Stack
　 Java.util ┤　　　  └java.util.TimeZone
　　　　　　　│　　　　　　　　　　└java.util.SimpleTimeZone
　　　　　　　│　　　 ┌java.util.Enumeration
　　　　　　　├接　口 ┤java.util.EventListener
　　　　　　　│　　　 └java.util.Observer
　　　　　　　│　　　 ┌java.util.EmptyStackException
　　　　　　　└异常类 ┤java.util.MissingResourceException
　　　　　　　　　　　│java.util.NoSuchElementException
　　　　　　　　　　　└java.util.TooManyListenersException
```

## 日期类Date

　　Java在日期类中封装了有关日期和时间的信息，用户可以通过调用相应的方法来获取系统时间或设置日期和时间。Date类中有很多方法在JDK1.0公布后已经过时了，在8.3中我们将介绍JDK1.0中新加的用于替代Date的功能的其它类。<br/>
　　在日期类中共定义了六种构造函数。<br/>
　　(1)public Date()<br/>
　　创建的日期类对象的日期时间被设置成创建时刻相对应的日期时间。<br/>
　　例 Date today=new Date()；//today被设置成创建时刻相对应的日期时间。<br/>
　　(2)public Date (long date)<br/>
　　long 型的参数date可以通过调用Date类中的static方法parse(String s)来获得。<br/>
　　例 long l=Date.parse("Mon 6 Jan 1997 13:3:00");<br/>
　　　　Date day=new Date(l);<br/>
　　//day中时间为1997年 1月6号星期一，13:3:00。<br/>
　　(3)public Date(String s)<br/>
　　按字符串s产生一日期对象。s的格式与方法parse中字符串参数的模式相同。<br/>
　　例 Date day=new Date("Mon 6 Jan 1997 13:3:00");<br/>
　　//day 中时间为1997年1月6号星期一，13:3:00.<br/>
　　(4)public Date(int year,int month,int date)<br/>
　　(5)public Date(int year,int month,int date,int hrs,int min)<br/>
　　(6)public Date(int year,int month,int date,int hrs,int min,int sec)<br/>
　　按给定的参数创建一日期对象。<br/>
　　参数说明：<br/>
　　year的值为：需设定的年份-1900。例如需设定的年份是1997则year的值应为97，即1997-1900的结果。所以Date中可设定的年份最小为1900；<br/>
　　month的值域为0～11，0代表1月，11表代表12月；<br/>
　　date的值域在1～31之间；<br/>
　　hrs的值域在0～23之间。从午夜到次日凌晨1点间hrs=0，从中午到下午1点间hrs=12；<br/>
　　min和sec的值域在0～59之间。<br/>
　　例 Date day=new Date(11,3,4);<br/>
　　//day中的时间为：04-Apr-11 12:00:00 AM <br/>
另外，还可以给出不正确的参数。<br/>
　　例　设定时间为1910年2月30日，它将被解释成3月2日。<br/>
　　Date day=new Date(10,1,30,10,12,34);<br/>
　　System.out.println("Day's date is:"+day);<br/>
　　//打印结果为：Day's date is:Web Mar 02 10:13:34 GMT+08:00 1910<br/>
　　下面我们给出一些Date类中常用方法。<br/>
　　(1)public static long UTC(int year,int month,int date,int hrs. int min,int sec)<br/>
　　该方法将利用给定参数计算UTC值。UTC是一种计时体制，与GMT(格林威治时间)的计时体系略有差别。UTC计时体系是基于原子时钟的，而GTMT计时体系是基于天文学观测的。计算中使用的一般为GMT计时体系。<br/>
　　(2)public static long parse(String s)<br/>
　　该方法将字符串s转换成一个long型的日期。在介绍构造方法Date(long date)时曾使用过这个方法。<br/>
　　字符串s有一定的格式，一般为：<br/>
　　(星期 日 年 时间GMT+时区)<br/>
　　若不注明时区，则为本地时区。<br/>
　　(3)public void setMonth(int month)<br/>
　　(4)public int getMonth()<br/>
　　这两个方法分别为设定和获取月份值。<br/>
　　获取的月份的值域为0～11，0代表1月，11代表12月。<br/>
　　(5)public String toString()<br/>
　　(6)public String toLocalString()<br/>
　　(7)public String toGMTString()<br/>
　　将给定日期对象转换成不同格式的字符串。它们对应的具体的格式可参看例子8.1。<br/>
　　(8)public int getTimezoneOffset()<br/>
　　该方法用于获取日期对象的时区偏移量。<br/>
<hr/>

## 日历类Calendar

　　在早期的JDK版本中，日期(Date)类附有两大功能：(1)允许用年、月、日、时、分、秒来解释日期：(2)允许对表示日期的字符串进行格式化和句法分析。在JDK1.1中提供了类Calendar来完成第一种功能，类DateFormat来完成第二项功能。dateFormat是java.text包中的一个类。与Date类有所不同的是，DateFormat类接受用各种语言和不同习惯表示的日期字符串。本节将介绍java.util包中的类Calendar及其它新增加的相关的类。<br/>
　　类Calendar是一个抽象类，它完成日期(Date)类和普通日期表示法(即用一组整型域如YEAR，MONTH，DAY，HOUR表示日期)之间的转换。<br/>
　　由于所使用的规则不同，不同的日历系统对同一个日期的解释有所不同。在JDK1.1中提供了Calendar类一个子类GregorianCalendar??它实现了世界上普遍使用的公历系统。当然用户也可以通过继承Calendar类，并增加所需规则，以实现不同的日历系统。<br/>
　　第GregorianCalendar继承了Calendar类。本节将在介绍类GregorianCalendar的同时顺带介绍Calendar类中的相关方法。<br/>
　　类GregorianCalendar提供了七种构造函数：<br/>
　　(1)public GregorianCalendar()<br/>
　　创建的对象中的相关值被设置成指定时区，缺省地点的当前时间，即程序运行时所处的时区、地点的当前时间。<br/>
　　(2)public GregorianCalendar(TimeZone zone)<br/>
　　创建的对象中的相关值被设置成指定时区zone，缺省地点的当前时间。<br/>
　　(3)public GregorianCalendar(Locale aLocale)<br/>
　　创建的对象中的相关值被设置成缺省时区，指定地点aLocale的当前时间。<br/>
　　(4)public GregorianCalendar(TimeZone zone,Local aLocale)<br/>
　　创建的对象中的相关值被设置成指定时区，指定地点的当前时间。<br/>
　　上面使用到的类TimeZone的性质如下：<br/>
　　TimeZone是java.util包中的一个类，其中封装了有关时区的信息。每一个时区对应一组ID。类TimeZone提供了一些方法完成时区与对应ID两者之间的转换。<br/>
　　(Ⅰ)已知某个特定的ID，可以调用方法<br/>
　　public static synchronized TimeZone getTimeZone(String ID)<br/>
来获取对应的时区对象。<br/>
　　例 太平洋时区的ID为PST，用下面的方法可获取对应于太平洋时区的时区对象：<br/>
　　TimeZone tz=TimeZone.getTimeZone("PST");<br/>
　　调用方法getDefault()可以获取主机所处时区的对象。<br/>
　　TimeZone tz=TimeZone.getDefault();<br/>
　　(Ⅱ)调用以下方法可以获取时区的ID<br/>
　　■public static synchronized String[] getavailableIDs(int rawOffset)<br/>
　　根据给定时区偏移值获取ID数组。同一时区的不同地区的ID可能不同，这是由于不同地区对是否实施夏时制意见不统一而造成的。<br/>
　　例String s[]=TimeZone.getAvailableIDs(-7*60*60*1000);<br/>
　　打印s，结果为s[0]=PNT，s[1]=MST<br/>
　　■public static synchronized String[] getAvailableIDs()<br/>
　　获取提供的所有支持的ID。<br/>
　　■public String getID()<br/>
　　获取特定时区对象的ID。<br/>
　　例 TimeZone tz=TimeZone.getDefault();<br/>
　　String s=tz.getID();<br/>
　　打印s，结果为s=CTT。<br/>
　　上面使用类的对象代表了一个特定的地理、政治或文化区域。Locale只是一种机制，它用来标识一类对象，Local本身并不包含此类对象。<br/>
　　要获取一个Locale的对象有两种方法：<br/>
　　(Ⅰ)调用Locale类的构造方法<br/>
　　Locale(String language,String country)<br/>
　　Locale(String language,String country,String variant)<br/>
　　参数说明：language??在ISO-639中定义的代码，由两个小写字母组成。<br/>
　　　　　　　country??在ISO-3166中定义的代码，由两个大写字母组成。<br/>
　　　　　　　variant??售货商以及特定浏览器的代码，例如使用WIN代表Windows。<br/>
　　(Ⅱ)调用Locale类中定义的常量<br/>
　　Local类提供了大量的常量供用户创建Locale对象。<br/>
　　例 Locale.CHINA<br/>
　　　　为中国创建一个Locale的对象。<br/>
　　类TimeZone和类Locale中的其它方法，读者可查阅API。<br/>
　　(5)public GregorianCalendar(int year,int month,int date)<br/>
　　(6)public GregorianCalendar(int year,int month,int date,int hour,int minute)<br/>
　　(7)public GregorianCalendar(int year,int month,int date,int hour,int minute,int second)<br/>
　　用给定的日期和时间创建一个GregorianCalendar的对象。
　　参数说明：<br/>
　　year-设定日历对象的变量YEAR；month-设定日历对象的变量MONTH；<br/>
　　date-设定日历对象的变量DATE；hour-设定日历对象的变量HOUR_OF_DAY；<br/>
　　minute-设定日历对象的变量MINUTE；second-设定日历对象的变量SECOND。<br/>
　　与Date类中不同的是year的值没有1900这个下限，而且year的值代表实际的年份。month的含义与Date类相同，0代表1月，11代表12月。<br/>
　　例 GregorianCalendar cal=new GregorianCalendar(1991,2,4)<br/>
　　cal的日期为1991年3月4号。<br/>
　　除了与Date中类似的方法外，Calendar类还提供了有关方法对日历进行滚动计算和数学计算。计算规则由给定的日历系统决定。进行日期计算时，有时会遇到信息不足或信息不实等特殊情况。Calendar采取了相应的方法解决这些问题。当信息不足时将采用缺省设置，在GregorianCalendar类中缺省设置一般为YEAR=1970,MONTH=JANUARY,DATE=1。<br/>
　　当信息不实时，Calendar将按下面的次序优先选择相应的Calendar的变量组合，并将其它有冲突的信息丢弃。<br/>
　　MONTH+DAY_OF_MONTH<br/>
　　MONTH+WEEK_OF_MONTH+DAY_OF_WEEK<br/>
　　MONTH+DAY_OF_WEEK_OF_MONTH+DAY_OF_WEEK<br/>
　　DAY_OF+YEAR<br/>
　　DAY_OF_WEEK_WEEK_OF_YEAR<br/>
　　HOUR_OF_DAY<br/>
<hr/>

## 随机数类Random

　　Java实用工具类库中的类java.util.Random提供了产生各种类型随机数的方法。它可以产生int、long、float、double以及Goussian等类型的随机数。这也是它与java.lang.Math中的方法Random()最大的不同之处，后者只产生double型的随机数。<br/>
　　类Random中的方法十分简单，它只有两个构造方法和六个普通方法。<br/>
　　构造方法：<br/>
　　(1)public Random()<br/>
　　(2)public Random(long seed)<br/>
　　Java产生随机数需要有一个基值seed，在第一种方法中基值缺省，则将系统时间作为seed。<br/>
　　普通方法：<br/>
　　(1)public synonronized void setSeed(long seed)<br/>
　　该方法是设定基值seed。<br/>
　　(2)public int nextInt()<br/>
　　该方法是产生一个整型随机数。<br/>
　　(3)public long nextLong()<br/>
　　该方法是产生一个long型随机数。<br/>
　　(4)public float nextFloat()<br/>
　　该方法是产生一个Float型随机数。<br/>
　　(5)public double nextDouble()<br/>
　　该方法是产生一个Double型随机数。<br/>
　　(6)public synchronized double nextGoussian()<br/>
　　该方法是产生一个double型的Goussian随机数。<br/>
<hr/>

## 向量类Vector

　　Java.util.Vector提供了向量(Vector)类以实现类似动态数组的功能。在Java语言中。正如在一开始就提到过，是没有指针概念的，但如果能正确灵活地使用指针又确实可以大大提高程序的质量，比如在C、C++中所谓"动态数组"一般都由指针来实现。为了弥补这点缺陷，Java提供了丰富的类库来方便编程者使用，Vector类便是其中之一。事实上，灵活使用数组也可完成向量类的功能，但向量类中提供的大量方法大大方便了用户的使用。<br/>
　　创建了一个向量类的对象后，可以往其中随意地插入不同的类的对象，既不需顾及类型也不需预先选定向量的容量，并可方便地进行查找。对于预先不知或不愿预先定义数组大小，并需频繁进行查找、插入和删除工作的情况，可以考虑使用向量类。<br/>
　　向量类提供了三种构造方法：<br/>
　　public vector()<br/>
　　public vector(int initialcapacity,int capacityIncrement)<br/>
　　public vector(int initialcapacity)<br/>
　　使用第一种方法，系统会自动对向量对象进行管理。若使用后两种方法，则系统将根据参数initialcapacity设定向量对象的容量(即向量对象可存储数据的大小)，当真正存放的数据个数超过容量时，系统会扩充向量对象的存储容量。参数capacityIncrement给定了每次扩充的扩充值。当capacityIncrement为0时，则每次扩充一倍。利用这个功能可以优化存储。<br/>
　　在Vector类中提供了各种方法方便用户使用：<br/>
　　■插入功能<br/>
　　(1)public final synchronized void addElement(Object obj)<br/>
　　将obj插入向量的尾部。obj可以是任何类的对象。对同一个向量对象，可在其中插入不同类的对象。但插入的应是对象而不是数值，所以插入数值时要注意将数值转换成相应的对象。<br/>
　　例 要插入一个整数1时，不要直接调用v1.addElement(1)，正确的方法为：<br/>
　　Vector v1=new Vector();<br/>
　　Integer integer1=new Integer(1);<br/>
　　v1.addElement(integer1);<br/>
　　(2)public final synchronized void setElementAt(object obj,int index)<br/>
　　将index处的对象设成obj，原来的对象将被覆盖。<br/>
　　(3)public final synchronized void insertElementAt(Object obj,int index)<br/>
　　在index指定的位置插入obj，原来对象以及此后的对象依次往后顺延。<br/>
　　■删除功能<br/>
　　(1)public final synchronized void removeElement(Object obj)<br/>
　　从向量中删除obj。若有多个存在，则从向量头开始试，删除找到的第一个与obj相同的向量成员。<br/>
　　(2)public final synchronized void removeAllElement()<br/>
　　删除向量中所有的对象。<br/>
　　(3)public final synchronized void removeElementlAt(int index)<br/>
　　删除index所指的地方的对象。<br/>
　　■查询搜索功能<br/>
　　(1)public final int indexOf(Object obj)<br/>
　　从向量头开始搜索obj ,返回所遇到的第一个obj对应的下标，若不存在此obj，返回-1。<br/>
　　(2)public final synchronized int indexOf(Object obj,int index)<br/>
　　从index所表示的下标处开始搜索obj。<br/>
　　(3)public final int lastIndexOf(Object obj)<br/>
　　从向量尾部开始逆向搜索obj。<br/>
　　(4)public final synchronized int lastIndexOf(Object obj,int index)<br/>
　　从index所表示的下标处由尾至头逆向搜索obj。<br/>
　　(5)public final synchronized Object firstElement()<br/>
　　获取向量对象中的首个obj。<br/>
　　(6)public final synchronized Object lastelement()<br/>
　　获取向量对象中的最后一个obj。<br/>
　　了解了向量的最基本的方法后，我们来看一下例8.3VectorApp.java。<br/>
<hr/>

## 栈类Stack

　　Stack类是Vector类的子类。它向用户提供了堆栈这种高级的数据结构。栈的基本特性就是先进后出。即先放入栈中的元素将后被推出。Stack类中提供了相应方法完成栈的有关操作。<br/>
　　基本方法：<br/>
　　public Object push(Object Hem)<br/>
　　将Hem压入栈中，Hem可以是任何类的对象。<br/>
　　public Object pop()<br/>
　　弹出一个对象。<br/>
　　public Object peek()<br/>
　　返回栈顶元素，但不弹出此元素。<br/>
　　public int search(Object obj)<br/>
　　搜索对象obj,返回它所处的位置。<br/>
　　public boolean empty()<br/>
　　判别栈是否为空。<br/>
<hr/>

## 哈希表类Hashtable

　　哈希表是一种重要的存储方式，也是一种常见的检索方法。其基本思想是将关系码的值作为自变量，通过一定的函数关系计算出对应的函数值，把这个数值解释为结点的存储地址，将结点存入计算得到存储地址所对应的存储单元。检索时采用检索关键码的方法。现在哈希表有一套完整的算法来进行插入、删除和解决冲突。在Java中哈希表用于存储对象，实现快速检索。
　　Java.util.Hashtable提供了种方法让用户使用哈希表，而不需要考虑其哈希表真正如何工作。<br/>
　　哈希表类中提供了三种构造方法，分别是：<br/>
　　public Hashtable()<br/>
　　public Hashtable(int initialcapacity)<br/>
　　public Hashtable(int initialCapacity,float loadFactor)<br/>
　　参数initialCapacity是Hashtable的初始容量，它的值应大于0。loadFactor又称装载因子，是一个0.0到0.1之间的float型的浮点数。它是一个百分比，表明了哈希表何时需要扩充，例如，有一哈希表，容量为100，而装载因子为0.9，那么当哈希表90%的容量已被使用时，此哈希表会自动扩充成一个更大的哈希表。如果用户不赋这些参数，系统会自动进行处理，而不需要用户操心。<br/>
　　Hashtable提供了基本的插入、检索等方法。<br/>
　　■插入<br/>
　　public synchronized void put(Object key,Object value)<br/>
给对象value设定一关键字key,并将其加到Hashtable中。若此关键字已经存在，则将此关键字对应的旧对象更新为新的对象Value。这表明在哈希表中相同的关键字不可能对应不同的对象(从哈希表的基本思想来看，这也是显而易见的)。<br/>
　　■检索<br/>
　　public synchronized Object get(Object key)<br/>
　　根据给定关键字key获取相对应的对象。<br/>
　　public synchronized boolean containsKey(Object key)<br/>
　　判断哈希表中是否包含关键字key。<br/>
　　public synchronized boolean contains(Object value)<br/>
　　判断value是否是哈希表中的一个元素。<br/>
　　■删除<br/>
　　public synchronized object remove(object key)<br/>
　　从哈希表中删除关键字key所对应的对象。<br/>
　　public synchronized void clear()<br/>
　　清除哈希表<br/>
　　另外，Hashtalbe还提供方法获取相对应的枚举集合：<br/>
　　public synchronized Enumeration keys()<br/>
　　返回关键字对应的枚举对象。<br/>
　　public synchronized Enumeration elements()<br/>
　　返回元素对应的枚举对象。<br/>

```Java
Collection
├List
│├LinkedList
│├ArrayList
│└Vector
│　└Stack
└Set
Map
├Hashtable
├HashMap
└WeakHashMap
```
<hr/>

## Collection接口
　　Collection是最基本的集合接口，一个Collection代表一组Object，即Collection的元素（Elements）。一些Collection允许相同的元素而另一些不行。一些能排序而另一些不行。Java SDK不提供直接继承自Collection的类，Java SDK提供的类都是继承自Collection的“子接口”如List和Set。<br/>
　　所有实现Collection接口的类都必须提供两个标准的构造函数：无参数的构造函数用于创建一个空的Collection，有一个Collection参数的构造函数用于创建一个新的Collection，这个新的Collection与传入的Collection有相同的元素。后一个构造函数允许用户复制一个Collection。<br/>
　　如何遍历Collection中的每一个元素？不论Collection的实际类型如何，它都支持一个iterator()的方法，该方法返回一个迭代子，使用该迭代子即可逐一访问Collection中每一个元素。典型的用法如下：<br/>
```Java
　　　　Iterator it = collection.iterator(); // 获得一个迭代子
　　　　while(it.hasNext()) {
　　　　　　Object obj = it.next(); // 得到下一个元素
　　　　}
　　由Collection接口派生的两个接口是List和Set。
```
<hr/>

## List接口
　　List是有序的Collection，使用此接口能够精确的控制每个元素插入的位置。用户能够使用索引（元素在List中的位置，类似于数组下标）来访问List中的元素，这类似于Java的数组。<br/>
和下面要提到的Set不同，List允许有相同的元素。<br/>
　　除了具有Collection接口必备的iterator()方法外，List还提供一个listIterator()方法，返回一个ListIterator接口，和标准的Iterator接口相比，ListIterator多了一些add()之类的方法，允许添加，删除，设定元素，还能向前或向后遍历。<br/>
　　实现List接口的常用类有LinkedList，ArrayList，Vector和Stack。<br/>
<hr/>

## LinkedList类
　　LinkedList实现了List接口，允许null元素。此外LinkedList提供额外的get，remove，insert方法在LinkedList的首部或尾部。这些操作使LinkedList可被用作堆栈（stack），队列（queue）或双向队列（deque）。<br/>
　　注意LinkedList没有同步方法。如果多个线程同时访问一个List，则必须自己实现访问同步。一种解决方法是在创建List时构造一个同步的List：<br/>
```Java
　　　　List list = Collections.synchronizedList(new LinkedList(...));
```
<hr/>

## ArrayList类
　　ArrayList实现了可变大小的数组。它允许所有元素，包括null。ArrayList没有同步。<br/>
size，isEmpty，get，set方法运行时间为常数。但是add方法开销为分摊的常数，添加n个元素需要O(n)的时间。其他的方法运行时间为线性。<br/>
　　每个ArrayList实例都有一个容量（Capacity），即用于存储元素的数组的大小。这个容量可随着不断添加新元素而自动增加，但是增长算法并没有定义。当需要插入大量元素时，在插入前可以调用ensureCapacity方法来增加ArrayList的容量以提高插入效率。<br/>
　　和LinkedList一样，ArrayList也是非同步的（unsynchronized）。<br/>
<hr/>

## Vector类
　　Vector非常类似ArrayList，但是Vector是同步的。由Vector创建的Iterator，虽然和ArrayList创建的Iterator是同一接口，但是，因为Vector是同步的，当一个Iterator被创建而且正在被使用，另一个线程改变了Vector的状态（例如，添加或删除了一些元素），这时调用Iterator的方法时将抛出ConcurrentModificationException，因此必须捕获该异常。<br/>
<hr/>

## Stack 类
　　Stack继承自Vector，实现一个后进先出的堆栈。Stack提供5个额外的方法使得Vector得以被当作堆栈使用。基本的push和pop方法，还有peek方法得到栈顶的元素，empty方法测试堆栈是否为空，search方法检测一个元素在堆栈中的位置。Stack刚创建后是空栈。<br/>
<hr/>

## Set接口
　　Set是一种不包含重复的元素的Collection，即任意的两个元素e1和e2都有e1.equals(e2)=false，Set最多有一个null元素。<br/>
　　很明显，Set的构造函数有一个约束条件，传入的Collection参数不能包含重复的元素。<br/>
　　请注意：必须小心操作可变对象（Mutable Object）。如果一个Set中的可变元素改变了自身状态导致Object.equals(Object)=true将导致一些问题。<br/>
<hr/>

## Map接口
　　请注意，Map没有继承Collection接口，Map提供key到value的映射。一个Map中不能包含相同的key，每个key只能映射一个value。Map接口提供3种集合的视图，Map的内容可以被当作一组key集合，一组value集合，或者一组key-value映射。<br/>
<hr/>

## Hashtable类
　　Hashtable继承Map接口，实现一个key-value映射的哈希表。任何非空（non-null）的对象都可作为key或者value。<br/>
　　添加数据使用put(key, value)，取出数据使用get(key)，这两个基本操作的时间开销为常数。<br/>
Hashtable通过initial capacity和load factor两个参数调整性能。通常缺省的load factor 0.75较好地实现了时间和空间的均衡。增大load factor可以节省空间但相应的查找时间将增大，这会影响像get和put这样的操作。<br/>
使用Hashtable的简单示例如下，将1，2，3放到Hashtable中，他们的key分别是”one”，”two”，”three”：<br/>
```Java
　　　　Hashtable numbers = new Hashtable();<br/>
　　　　numbers.put(“one”, new Integer(1));<br/>
　　　　numbers.put(“two”, new Integer(2));<br/>
　　　　numbers.put(“three”, new Integer(3));<br/>
```
　　要取出一个数，比如2，用相应的key：<br/>
```Java
　　　　Integer n = (Integer)numbers.get(“two”);<br/>
　　　　System.out.println(“two = ” + n);<br/>
```
　　由于作为key的对象将通过计算其散列函数来确定与之对应的value的位置，因此任何作为key的对象都必须实现hashCode和equals方法。hashCode和equals方法继承自根类Object，如果你用自定义的类当作key的话，要相当小心，按照散列函数的定义，如果两个对象相同，即obj1.equals(obj2)=true，则它们的hashCode必须相同，但如果两个对象不同，则它们的hashCode不一定不同，如果两个不同对象的hashCode相同，这种现象称为冲突，冲突会导致操作哈希表的时间开销增大，所以尽量定义好的hashCode()方法，能加快哈希表的操作。<br/>
　　如果相同的对象有不同的hashCode，对哈希表的操作会出现意想不到的结果（期待的get方法返回null），要避免这种问题，只需要牢记一条：要同时复写equals方法和hashCode方法，而不要只写其中一个。<br/>
　　Hashtable是同步的。<br/>
<hr/>

## HashMap类
　　HashMap和Hashtable类似，不同之处在于HashMap是非同步的，并且允许null，即null value和null key。，但是将HashMap视为Collection时（values()方法可返回Collection），其迭代子操作时间开销和HashMap的容量成比例。因此，如果迭代操作的性能相当重要的话，不要将HashMap的初始化容量设得过高，或者load factor过低。<br/>
<hr/>

## WeakHashMap类
　　WeakHashMap是一种改进的HashMap，它对key实行“弱引用”，如果一个key不再被外部所引用，那么该key可以被GC回收。<br/>
<hr/>

## 总结
　　如果涉及到堆栈，队列等操作，应该考虑用List，对于需要快速插入，删除元素，应该使用LinkedList，如果需要快速随机访问元素，应该使用ArrayList。
　　如果程序在单线程环境中，或者访问仅仅在一个线程中进行，考虑非同步的类，其效率较高，如果多个线程可能同时操作一个类，应该使用同步的类。
　　要特别注意对哈希表的操作，作为key的对象要正确复写equals和hashCode方法。
　　尽量返回接口而非实际的类型，如返回List而非ArrayList，这样如果以后需要将ArrayList换成LinkedList时，客户端代码不用改变。这就是针对抽象编程。