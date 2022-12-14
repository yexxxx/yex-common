package me.yex.common.core.controller;

import me.yex.common.core.exception.GlobalException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yex
 * @description /error的错误处理
 */
@Hidden
@RestController
public class HttpErrorController implements ErrorController {

    private final static String ERROR_PATH = "/error";


    @RequestMapping(ERROR_PATH)
    public void errorPathHandler(HttpServletResponse response) {
        //抛出ErrorPageException异常，方便被ExceptionHandlerConfig处理
        HttpStatus httpStatus;
        switch (response.getStatus()) {
            case 404:
                httpStatus = HttpStatus.NOT_FOUND;
                break;
            case 403:
                httpStatus = HttpStatus.FORBIDDEN;
                break;
            case 401:
                httpStatus = HttpStatus.UNAUTHORIZED;
                break;
            case 400:
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            default:
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
        }
        throw new GlobalException(httpStatus);
    }

    /**
     * filter异常处理
     */
    @RequestMapping("/error/exception")
    public void rethrow(HttpServletRequest request) throws Exception {
        throw ((Exception) request.getAttribute("filter.error"));
    }
}
