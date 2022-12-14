

package me.yex.common.client.aspect;

import me.yex.common.client.annotation.HasPermission;
import me.yex.common.client.constant.ResultEnum;
import me.yex.common.core.constant.AuthConstant;
import me.yex.common.core.exception.GlobalException;
import me.yex.common.core.oauth.SessionContextHolder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * HasPermission 校验
 *
 * @author yex
 */
@Aspect
@Slf4j
public class HasPermissionAspect {

    @Around("@annotation(hasPermission)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, HasPermission hasPermission) {
        String strClassName = point.getTarget().getClass().getName();
        String strMethodName = point.getSignature().getName();
        log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);

        List<String> stringList = Arrays.asList(hasPermission.value());
        Collection<String> authInfos = SessionContextHolder.getContext().getUserInfo().getAuthInfos();
        boolean permitted = false;

        if (AuthConstant.PermissionConstant.AND.equals(hasPermission.mode())) {
            if (authInfos == null || authInfos.size() ==0){
                throw new GlobalException(ResultEnum.NO_PERMISSION);
            }
            if (!authInfos.containsAll(stringList)) {
                throw new GlobalException(ResultEnum.NO_PERMISSION);
            }
        } else {
            if (authInfos.stream().noneMatch(stringList::contains)) {
                throw new GlobalException(ResultEnum.NO_PERMISSION);
            }
        }
        return point.proceed();
    }

}
