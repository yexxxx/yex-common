

package me.yex.common.client.annotation;

import me.yex.common.core.constant.AuthConstant;

import java.lang.annotation.*;

/**
 * @author yex
 * @date 2021/10/10 操作日志注解
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HasPermission {

	/**
	 * 需要校验的权限码
	 * @return 需要校验的权限码
	 */
	String [] value() default {};

	/**
	 * 验证模式：and | or，默认and
	 * @return 验证模式
	 */
	String mode() default AuthConstant.PermissionConstant.AND;

	/**
	 * 多账号体系下所属的账号体系标识
	 * @return see note
	 */
	String type() default "";

	/**
	 * 在权限认证不通过时的次要选择，两者只要其一认证成功即可通过校验
	 *
	 * <p>
	 * 	例1：@HasPermission(value="user-add", orRole="admin")，
	 * 	代表本次请求只要具有 user-add权限 或 admin角色 其一即可通过校验
	 * </p>
	 *
	 * <p>
	 * 	例2： orRole = {"admin", "manager", "staff"}，具有三个角色其一即可 <br>
	 * 	例3： orRole = {"admin, manager, staff"}，必须三个角色同时具备
	 * </p>
	 *
	 * @return /
	 */
	String[] orRole() default {};

}
