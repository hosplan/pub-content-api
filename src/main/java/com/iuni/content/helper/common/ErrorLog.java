package com.iuni.content.helper.common;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorLog {

    public void write(String position, String methodName, Exception e){
        System.out.println("해당위치 " + position + "__" + methodName + "에서 에러 발생 : " + e);
    }

}
