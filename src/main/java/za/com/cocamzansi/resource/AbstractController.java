//package za.com.cocamzansi.resource;
//
////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.ConstraintViolation;
//import javax.validation.ConstraintViolationException;
//import javax.validation.Validator;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.support.DefaultMessageSourceResolvable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.MissingServletRequestParameterException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
//import za.com.cocamzansi.exception.BadRequestException;
//import za.com.cocamzansi.exception.RequestProcessingException;
//import za.com.cocamzansi.exception.ResourceForbiddenException;
//import za.com.cocamzansi.exception.ResourceNotFoundException;
//import za.com.cocamzansi.model.exception.ExceptionResponseType;
//import za.com.cocamzansi.util.localization.MessageByLocaleService;
//
//public abstract class AbstractController {
//    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);
//    @Autowired
//    private MessageByLocaleService localeService;
//    @Resource
//    protected Validator validator;
//
//    public AbstractController() {
//    }
//
//    protected <T> void validate(Class<T> clazz, T obj) {
//        Set<ConstraintViolation<T>> violations = this.validator.validate(obj, new Class[0]);
//        if (violations != null && violations.size() > 0) {
//            throw new ConstraintViolationException(violations);
//        }
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ExceptionResponseType handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        List<String> errors = (List)e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
//        String message = String.join(",", errors);
//        if (message == null) {
//            message = HttpStatus.BAD_REQUEST.getReasonPhrase();
//        }
//
//        return new ExceptionResponseType(String.format("MHG-0000-%s", HttpStatus.BAD_REQUEST), "The request payload is malformed", message);
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ExceptionResponseType handleConstraintViolationException(ConstraintViolationException e) {
//        List<String> errors = (List)e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
//        String message = String.join(",", errors);
//        if (message == null) {
//            message = HttpStatus.BAD_REQUEST.getReasonPhrase();
//        }
//
//        return new ExceptionResponseType(String.format("MHG-0000-%s", HttpStatus.BAD_REQUEST), "A request parameter is not valid", message);
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ExceptionResponseType handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
//        return new ExceptionResponseType(String.format("MHG-0000-%s", HttpStatus.BAD_REQUEST), "A request parameter is not of the expected type", e.getMessage());
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ExceptionResponseType handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
//        return new ExceptionResponseType(String.format("MHG-0000-%s", HttpStatus.BAD_REQUEST), "A required request parameter is not present", e.getMessage());
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ExceptionResponseType handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
//        return new ExceptionResponseType(String.format("MHG-0000-%s", HttpStatus.BAD_REQUEST), "A request parameter is not valid", e.getMessage());
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ExceptionResponseType handleIllegalArgumentException(IllegalArgumentException e) {
//        return new ExceptionResponseType(String.format("MHG-0000-%s", HttpStatus.BAD_REQUEST), "A request parameter is not valid", e.getMessage());
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ExceptionResponseType handleUncaughtThrowable(Throwable e) {
//        LOGGER.error("An unhandled exception has occurred", e);
//        e.printStackTrace();
//        return new ExceptionResponseType(String.format("MHG-0000-%s", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "An unhandled exception has occurred. Please contact MHG technical support for further assistance");
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ExceptionResponseType handleBadRequestException(BadRequestException e) {
//
//        return ExceptionResponseType.valueOf(e);
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public ExceptionResponseType handleUnauthorizedException(ResourceForbiddenException e) {
//        return ExceptionResponseType.valueOf(e);
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ExceptionResponseType handleResourceNotFoundException(ResourceNotFoundException e) {
//        return ExceptionResponseType.valueOf(e);
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ExceptionResponseType handleRequestProcessingException(RequestProcessingException e) {
//        return ExceptionResponseType.valueOf(e);
//    }
//
//    protected Map<String, Object> asListResponse(final List<?> data, String key) {
//        Map<String, Object> response = new LinkedHashMap();
//        response.put(key, data);
//        response.put("meta", new HashMap<String, Object>() {
//            {
//                this.put("size", data != null ? data.size() : 0);
//                this.put("totalPages", data != null && !data.isEmpty() ? 1 : 0);
//                this.put("totalElements", data != null ? data.size() : 0);
//            }
//        });
//        return response;
//    }
//
//    protected Map<String, Object> asListResponse(List<?> data) {
//        return this.asListResponse(data, "data");
//    }
//
//    protected Map<String, Object> asListResponse(final PagedResponseType<?> data, String key) {
//        Map<String, Object> response = new LinkedHashMap();
//        response.put(key, data.getContent());
//        response.put("meta", new HashMap<String, Object>() {
//            {
//                this.put("size", data.getSize());
//                this.put("totalPages", data.getTotalPages());
//                this.put("totalElements", data.getTotalElements());
//            }
//        });
//        return response;
//    }
//
//    protected Map<String, Object> asListResponse(PagedResponseType<?> data) {
//        return this.asListResponse(data, "data");
//    }
//
//    protected HttpServletRequest getRequest() {
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        return ((ServletRequestAttributes)requestAttributes).getRequest();
//    }
//}
//
