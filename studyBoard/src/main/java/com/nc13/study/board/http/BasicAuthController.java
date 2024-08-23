package com.nc13.study.board.http;

import com.nc13.study.board.domain.User;
import com.nc13.study.board.security.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//
@RestController
public class BasicAuthController {
    private UserDetail userDetail;

    @RequestMapping(value = "/api/users/signIn", method = RequestMethod.GET)
    public User signIn(@RequestParam String username, @RequestParam String password) {
        User user = userDetail.getUser();
        return user;
    }
}
