package com.example.fwk.filter;

import com.example.demo.entity.FwkTransactionHst;
import com.example.demo.repo.jpa.TransactionRepo;
import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Aspect
@Component
@Log
public class ControllerAdvice {

    @Autowired
    TransactionRepo repo;


    @Around("PointCutList.allController()")
    public Object around(ProceedingJoinPoint pjp) {
        log.info("3around start");

        Object result = null;

        HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String signatureName = pjp.getSignature().getDeclaringType().getSimpleName() + "." + pjp.getSignature().getName();

        //FwkTransaction init
        FwkTransactionHst newTr = new FwkTransactionHst();
        newTr.setTransactionDate(LocalDate.now());
        newTr.setAppName("muzi");
        newTr.setAppVersion("0.1");
        newTr.setGid(UUID.randomUUID().toString());
        newTr.setMethod(req.getMethod());
        newTr.setPath(req.getRequestURI());
        newTr.setCreateDt(OffsetDateTime.now(ZoneId.of("Asia/Seoul")));

        log.info("4Controller Start : " + signatureName + " by " + req.getRemoteAddr());
        try {
            result = pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            repo.save(newTr);
        }

        log.info("5Controller End : " + signatureName);
        return result;
    }
}
