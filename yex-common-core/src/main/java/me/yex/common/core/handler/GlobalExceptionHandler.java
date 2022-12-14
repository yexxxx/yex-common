

package me.yex.common.core.handler;

import me.yex.common.core.entity.R;
import me.yex.common.core.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

/**
 * <p>
 * 全局异常处理器
 * </p>
 *
 * @author yex
 * @date 2021/10/10
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 处理业务校验过程中碰到的非法参数异常 该异常基本由{@link Assert}抛出
	 * @see Assert#hasLength(String, String)
	 * @param exception 参数校验异常
	 * @return API返回结果对象包装后的错误输出结果
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.OK)
	public R<Object> handleIllegalArgumentException(IllegalArgumentException exception) {
		log.error("非法参数,ex = {}", exception.getMessage(), exception);
		return R.failed(exception.getMessage());
	}

	/**
	 * validation ExceptionResultEnum
	 * @param exception
	 * @return R
	 */
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public R<Object> handleBodyValidException(MethodArgumentNotValidException exception) {
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		FieldError fieldError = fieldErrors.get(0);
		log.warn("参数校验异常,ex = {}", fieldErrors.get(0).getDefaultMessage());
		return R.failed(fieldError.getField() + fieldError.getDefaultMessage());
	}

	/**
	 * validation Exception (以form-data形式传参)
	 * @param exception
	 * @return R
	 */
	@ExceptionHandler({ BindException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public R<Object> bindExceptionHandler(BindException exception) {
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		FieldError fieldError = fieldErrors.get(0);
		log.warn("参数绑定异常,ex = {}", fieldError);
		return R.failed(fieldError.getField() + fieldError.getDefaultMessage());
	}

	/**
	 * 自定义异常.
	 * @param e the e
	 * @return R
	 */
	@ExceptionHandler(GlobalException.class)
	@ResponseStatus(HttpStatus.OK)
	public R<Object> handleGlobalException(GlobalException e) {
		log.error("自定义异常 ex={}", e.getMessage(), e);
		return new R<>(e.getCode(),e.getMessage(),null);
	}

	/**
	 * Controller上一层相关异常
	 *
	 * @param e 异常
	 * @return 异常结果
	 */
	@ExceptionHandler({
			NoHandlerFoundException.class,
			HttpRequestMethodNotSupportedException.class,
			HttpMediaTypeNotSupportedException.class,
			MissingPathVariableException.class,
			MissingServletRequestParameterException.class,
			TypeMismatchException.class,
			HttpMessageNotReadableException.class,
			HttpMessageNotWritableException.class,
			// BindException.class,
			// MethodArgumentNotValidException.class
			HttpMediaTypeNotAcceptableException.class,
			ServletRequestBindingException.class,
			ConversionNotSupportedException.class,
			MissingServletRequestPartException.class,
			AsyncRequestTimeoutException.class
	})
	@ResponseStatus(HttpStatus.OK)
	public R<Object> handleMVCException(Exception e) {
		log.error("web ex={}", e.getMessage(), e);
		return R.failed(e.getLocalizedMessage());
	}



	/**
	 * 全局异常.
	 * @param e the e
	 * @return R
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public R<Object> handleException(Exception e) {
		log.error("全局异常信息 ex={}", e.getMessage(), e);
		return R.failed(e.getLocalizedMessage());
	}
}
