//package com.nc13.study.board.error;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.util.HashMap;
//import java.util.Map;
//// https://velog.io/@win-luck/Springboot-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%9A%94%EC%B2%AD-%EB%8D%B0%EC%9D%B4%ED%84%B0RequestDto%EC%97%90-%EB%8C%80%ED%95%B4-Valid%EB%A1%9C-%EC%9C%A0%ED%9A%A8%EC%84%B1-%EA%B2%80%EC%82%AC%ED%95%98%EA%B3%A0-%EC%98%88%EC%99%B8%EC%B2%98%EB%A6%AC%ED%95%98%EA%B8%B0
//@RestControllerAdvice
//public class GlobalExceprionHandler {
//    @ExceptionHandler(MethodArgumentNotValidException.class) // 요청의 유효성 검사 실패 시
//    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Bad Request로 응답 반환
//    public ApiResponse<Map<String, String>> handleInValidRequestException(MethodArgumentNotValidException e) {
//        // 에러가 발생한 객체 내 필드와 대응하는 에러 메시지를 map에 저장하여 반환
//        Map<String, String> errors = new HashMap<>();
//        e.getBindingResult().getFieldErrors().forEach(error -> {
//            String fieldName = error.getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return ApiResponse.fail(ResponseCode.BAD_REQUEST, errors);
//    }
//}