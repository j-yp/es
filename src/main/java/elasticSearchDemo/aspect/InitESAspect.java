package elasticSearchDemo.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class InitESAspect {
	@Pointcut(value = "@annotation(elasticSearchDemo.aspect.anno.InitES)")
	public static void controllerAspect() {}
	
	// 在这里定义前置切面
    @Before("controllerAspect()")
    public void beforeMethod(JoinPoint joinPoint) {
    	
    }
    
    private HttpServletRequest getRequest() {   
    	return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();   
    }  
}
