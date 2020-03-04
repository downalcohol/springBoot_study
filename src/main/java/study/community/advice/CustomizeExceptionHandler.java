package study.community.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import study.community.exception.CustomizeException;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(Throwable e, Model model) {
        if (e instanceof CustomizeException){
            model.addAttribute("errorMessage",((CustomizeException) e).getErrorMessage());
        }else {
            model.addAttribute("errorMessage","发生了未知的错误，目前无法杀妈");
        }
        return new ModelAndView("error");
    }
}
