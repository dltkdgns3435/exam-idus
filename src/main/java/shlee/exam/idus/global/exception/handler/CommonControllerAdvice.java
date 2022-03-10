package shlee.exam.idus.global.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import shlee.exam.idus.global.exception.exceptions.*;

@ControllerAdvice
public class CommonControllerAdvice {
    //이미 로그아웃 되어있는 회원이 요청한 경우 예외처리
    @ExceptionHandler(MemberAlreadyLogoutException.class)
    public ResponseEntity<String> handleMemberAlreadyLogoutException(MemberAlreadyLogoutException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    //회원가입 시 이메일이 중복되는경우 예외처리
    @ExceptionHandler(MemberEmailDuplicateException.class)
    public ResponseEntity<String> handleMemberEmailDuplicateException(MemberEmailDuplicateException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    //사용자가 존재하지 않는경우 예외처리
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> handleMemberNotFoundException(MemberNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    //사용자 패스워드가 일치하지 않는경우 예외처리
    @ExceptionHandler(MemberPasswordNotMatchFountException.class)
    public ResponseEntity<String> handleMemberPasswordNotMatchFountException(MemberPasswordNotMatchFountException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    //파라미터값 검증에 실패한 경우 예외처리
    @ExceptionHandler(RequestValidationException.class)
    public ResponseEntity<String> handleRequestValidationException(RequestValidationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    //파라미터값 검증에 실패한 경우 예외처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
