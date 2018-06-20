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
　　Java.util ┤　　　 └java.util.TimeZone
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
　　