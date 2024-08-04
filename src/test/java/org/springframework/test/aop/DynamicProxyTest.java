package org.springframework.test.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Before;
import org.junit.Test;
import org.springframework.aop.*;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.framework.CglibAopProxy;
import org.springframework.aop.framework.JdkDynamicAopProxy;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.springframework.test.common.WorldServiceBeforeAdvice;
import org.springframework.test.common.WorldServiceInterceptor;
import org.springframework.test.service.WorldService;
import org.springframework.test.service.WorldServiceImpl;

/**
 * @author derekyi
 * @date 2020/12/6
 */
public class DynamicProxyTest {

	private AdvisedSupport advisedSupport;

	@Before
	public void setup() {
		WorldService worldService = new WorldServiceImpl();

		advisedSupport = new AdvisedSupport();
		TargetSource targetSource = new TargetSource(worldService);
		WorldServiceInterceptor methodInterceptor = new WorldServiceInterceptor();
		MethodMatcher methodMatcher = new AspectJExpressionPointcut("execution(* org.springframework.test.service.WorldService.explode(..))").getMethodMatcher();
		advisedSupport.setTargetSource(targetSource);
		advisedSupport.setMethodInterceptor(methodInterceptor);
		advisedSupport.setMethodMatcher(methodMatcher);
	}

	@Test
	public void testJdkDynamicProxy() throws Exception {
		WorldService proxy = (WorldService) new JdkDynamicAopProxy(advisedSupport).getProxy();
		proxy.explode();
	}

	@Test
	public void testCglibDynamicProxy() throws Exception {
		WorldService proxy = (WorldService) new CglibAopProxy(advisedSupport).getProxy();
		proxy.explode();
	}

	@Test
	public void testProxyFactory(){
		// 使用JDK动态代理
		advisedSupport.setProxyTargetClass(false);
		WorldService proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
		proxy.explode();

		// 使用CGLIB动态代理
		advisedSupport.setProxyTargetClass(true);
		proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
		proxy.explode();

	}

	@Test
	public void testBeforeAdvice(){
		GenericInterceptor genericInterceptor=  new GenericInterceptor();
		genericInterceptor.setBeforeAdvice(new WorldServiceBeforeAdvice());
		advisedSupport.setMethodInterceptor(genericInterceptor);

		WorldService worldService = (WorldService) new ProxyFactory(advisedSupport).getProxy();
		worldService.explode();
	}

	@Test
	public void AspectJExpressionPointcutAdvisor(){
		WorldService worldService = new WorldServiceImpl();

		String expression = "execution(* org.springframework.test.service.WorldService.explode(..))";
		BeforeAdvice beforeAdvice = new WorldServiceBeforeAdvice();
		MethodBeforeAdviceInterceptor interceptor = new MethodBeforeAdviceInterceptor(beforeAdvice);

		AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
		advisor.setExpression(expression);
		advisor.setAdvice(interceptor);

		ClassFilter classFilter = advisor.getPointCut().getClassFilter();
		if(classFilter.matches(worldService.getClass())){
			AdvisedSupport advisedSupport = new AdvisedSupport();
			advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
			advisedSupport.setTargetSource(new TargetSource(worldService));
			advisedSupport.setMethodMatcher(advisor.getPointCut().getMethodMatcher());

			WorldService proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
			proxy.explode();


		}
	}
}
