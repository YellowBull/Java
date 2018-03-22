# SpringMVC 基础知识总结
![](https://images2017.cnblogs.com/blog/411512/201707/411512-20170729103203243-1447701989.png)<br/>
## 核心架构的具体流程：
首先用户发送请求——>DispatcherServlet，前端控制器收到请求后自己不进行处理，而是委托给其他的解析器进行处理，作为统一访问点，进行全局的流程控制；<br/>
DispatcherServlet——>HandlerMapping， HandlerMapping将会把请求映射为HandlerExecutionChain对象（包含一个Handler处理器（页面控制器）对象、多个HandlerInterceptor拦截器）对象，通过这种策略模式，很容易添加新的映射策略；<br/>
DispatcherServlet——>HandlerAdapter，HandlerAdapter将会把处理器包装为适配器，从而支持多种类型的处理器，即适配器 设计模式 的应用，从而很容易支持很多类型的处理器；<br/>
HandlerAdapter——>处理器功能处理方法的调用，HandlerAdapter将会根据适配的结果调用真正的处理器的功能处理方法，完成功能处理；并返回一个ModelAndView对象（包含模型数据、逻辑视图名）；<br/>
ModelAndView的逻辑视图名——> ViewResolver， ViewResolver将把逻辑视图名解析为具体的View，通过这种策略模式，很容易更换其他视图技术；<br/>
View——>渲染，View会根据传进来的Model模型数据进行渲染，此处的Model实际是一个Map数据结构，因此很容易支持其他视图技术；<br/>
返回控制权给DispatcherServlet，由DispatcherServlet返回响应给用户，到此一个流程结束。<br/>
#### [SpringMVC入门](http://www.cnblogs.com/fangjian0423/p/springMVC-introduction.html)
#### [SpringMVC核心分发器DispatcherServlet解析](http://www.cnblogs.com/fangjian0423/p/springMVC-dispatcherServlet.html)
#### [SpringMVC请求时如何找到正确的Controller源码解析](http://www.cnblogs.com/fangjian0423/p/springMVC-request-mapping.html)
#### [SpringMVC中Controller的方法中参数的工作原理](http://www.cnblogs.com/fangjian0423/p/springMVC-request-param-analysis.html)
#### [SpringMVC关于json、xml自动转换的原理研究](http://www.cnblogs.com/fangjian0423/p/springMVC-xml-json-convert.html)
#### [SpringMVC类型转换、数据绑定详解](http://www.cnblogs.com/fangjian0423/p/springMVC-databind-typeconvert.html)
#### [SpringMVC拦截器详解](http://www.cnblogs.com/fangjian0423/p/springMVC-interceptor.html)
#### [SpringMVC视图机制详解](http://www.cnblogs.com/fangjian0423/p/springMVC-view-viewResolver.html)
#### [SpringMVC异常处理机制详解](http://www.cnblogs.com/fangjian0423/p/springMVC-exception-analysis.html)


