package com.mark.proxy.frame;

import com.mark.proxy.threadlocal.Sequence;
import com.mark.proxy.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * 方法拦截助手类
 * Created by lulei on 2017/6/14.
 */
public class AopHelper {

    private static  final Logger logger = LoggerFactory.getLogger(AopHelper.class);

    static {
        try{
            Map<Class<?>,Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>,List<Proxy>> targetMap = createTargetMap(proxyMap);
            for(Map.Entry<Class<?>,List<Proxy>> targetEntry: targetMap.entrySet()){
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass,proxyList);
                BeanHelper.setBean(targetClass,proxy);
            }
        }catch (Exception e){
            logger.error("aop failure",e);
        }
    }

    private static Set<Class<?>> createTargetClassSet(Aspect aspect){
        Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
        Class<? extends Annotation> annotation = aspect.value();
        if(annotation != null && !annotation.equals(Aspect.class)){
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    private static Map<Class<?>,Set<Class<?>>> createProxyMap(){
        Map<Class<?>,Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();

//        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
//        for(Class<?> proxyClass: proxyClassSet){
//            if(proxyClass.isAnnotationPresent(Aspect.class)){
//                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
//                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
//                proxyMap.put(proxyClass,targetClassSet);
//            }
//        }

        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    public static  void addAspectProxy(Map<Class<?>,Set<Class<?>>> proxyMap){
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);

        for(Class<?> proxyClass: proxyClassSet){
            if(proxyClass.isAnnotationPresent(Aspect.class)){
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass,targetClassSet);
            }
        }
    }



    public static  void addTransactionProxy(Map<Class<?>,Set<Class<?>>> proxyMap){
        Set<Class<?>> serviceClassSet= ClassHelper.getClassSetByAnnotation(Transaction.class);
        proxyMap.put(TransactionProxy.class,serviceClassSet);
    }

    private static Map<Class<?>,List<Proxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>,List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();

        for(Map.Entry<Class<?>,Set<Class<?>>> proxyEntry: proxyMap.entrySet()){
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for(Class<?> targetClass: targetClassSet){
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if(targetMap.containsKey(targetClass)){
                    targetMap.get(targetClass).add(proxy);
                }else{
                    List<Proxy> proxyList =new ArrayList<Proxy>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass,proxyList);
                }
            }

        }
        return targetMap;
    }


}
