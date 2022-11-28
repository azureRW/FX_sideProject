package aop;

import mappingObj.forWebsocket.message;
import mappingObj.jpaEntranceForUsers;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Aspect
@Component
public class advice {
     @Autowired
     SimpMessagingTemplate template;
     @Autowired
     jpaEntranceForUsers entrance;
    @Pointcut( "execution(java.util.Map<String,Float> model.deep.subServiceOfSemi.subService*(..))")
    private void pt1(){};
    @Around("pt1()")
    public Object broadcastPrice(ProceedingJoinPoint point) throws Throwable {

        Map<String,Float> map= (Map<String, Float>) point.proceed();
//        System.out.println("aop is working");
        Map<String,String> newmap = new HashMap<>();
        newmap.put("ask",map.get("ask").toString());
        newmap.put("bid",map.get("bid").toString());
        newmap.put("time",new Date().toString());
        template.convertAndSend("/topic/price",newmap);
        return map;
    }
//    @Pointcut("execution(void model.deep.semiPersistence.mainService() )")
//    private void pt2(){}
//    @Before("pt2()")
//    public void test(){
//        System.out.println("aop test");
//    }
    @Pointcut("execution(* controller.websocket.*(..))")
    private void pt3(){};
    @Around("pt3()")
    public Object printProperty00(ProceedingJoinPoint point) throws Throwable {
        System.out.println("aop property run");
        Object o[]=point.getArgs();
        Object re = point.proceed();
        template.convertAndSend("/topic/propertyInfo/"+((Principal)o[1]).getName().trim(),
                                new message(Double.toString(entrance.findByUserAccount(((message)o[0]).getContent()).getUserProperty()))
        );

        return re;
    }

}
