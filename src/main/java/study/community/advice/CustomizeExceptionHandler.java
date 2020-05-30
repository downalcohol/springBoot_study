package study.community.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import study.community.dto.ResultDTO;
import study.community.exception.CustomizeErrorCode;
import study.community.exception.CustomizeException;

import javax.servlet.http.HttpServletRequest;

@ResponseBody
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    Object handleControllerException(Throwable e,
                                           Model model,
                                           HttpServletRequest request) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)){
            //返回JSON
            if (e instanceof CustomizeException){
                return ResultDTO.errorOf((CustomizeException) e);
            }
            else {
                return ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
        }else {
            if (e instanceof CustomizeException){
                model.addAttribute("errorMessage",((CustomizeException) e).getMessage());
            }else {
                model.addAttribute("errorMessage",CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
