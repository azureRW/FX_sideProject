package aop;

import model.forWebsocket.message;
import model.dao.jpaEntranceForUsers;
import model.deep.semiPersistence;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Base64;
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
     @Autowired
     semiPersistence semi;
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
//    @Pointcut("execution(* controller.websocket.do*(..))")
//    private void pt3(){};
//    @Around("pt3()")
//    public Object printProperty00(ProceedingJoinPoint point) throws Throwable {
//        System.out.println("aop property run");
//        Object o[]=point.getArgs();
//        Object re = point.proceed();
//        String uuid = ((Principal)o[1]).getName().trim();
//        template.convertAndSend("/topic/propertyInfo/"+uuid,
//                                new message(Double.toString(entrance.findByUserAccount(((message)o[0]).getContent()).getUserProperty()))
//        );
//
//        return re;
//    }
    @Pointcut("execution(* model.service.userBehavior.userX*(..))")
    private void pt4() {}
    @Around("pt4()")
    public Object printPropertyForHTTP(ProceedingJoinPoint point) throws Throwable {
        Object o[]=point.getArgs();
        String account = (String) o[0];
        String id= semi.account2Uuid(account);
        Object re = point.proceed();
        double property = entrance.findByUserAccount(account).getUserProperty();
//        System.out.println("================================");
//        System.out.println("id="+id);
        template.convertAndSend("/topic/propertyInfo/"+id,new message(Double.toString(property)));
        return re;
    }
    @Pointcut("execution(String controller.registerAndLogin.login(..))")
    private void pt5(){}
    @Around("pt5()")
    public Object encodeResponseMs(ProceedingJoinPoint point) throws Throwable {
        Object re = point.proceed();
        String loginMes = Base64.getEncoder().encodeToString(((String)re).getBytes("utf-8"));
        return loginMes;
    }


}
