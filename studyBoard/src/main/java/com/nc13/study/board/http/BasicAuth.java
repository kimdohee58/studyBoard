package com.nc13.study.board.http;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// notion에 잇는 링크
public class BasicAuth {
    private static final Logger logger = LoggerFactory.getLogger(String.valueOf(BasicAuth.class));
    public static void main(String[] args) throws URISyntaxException, IOException,InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                .authenticator(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("admin", "admin".toCharArray());
                    }
                })
                .build();

        // request 보낼 때, header의 "Authentication"에 인증을 위한 정보를 암호화하여 넣는다.
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI("http://localhost:8080/users/signIn"))
                .header("Authorization", getBasicAuthentictionHeader("admin", "admin"))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        logger.info(response.body());
    }

    // 인증 관련 정보(credential) 암호화하고 전송 형식에 맞게 반환
    private static final String getBasicAuthentictionHeader(String username, String password) {
        String valueToEncode = username + ":" + password; // credential 만들기
        return "Basic "+ Base64.getEncoder().encodeToString(valueToEncode.getBytes()); // credential -> Base64 인코딩하고 앞에 Basic 붙이기
    }
}
