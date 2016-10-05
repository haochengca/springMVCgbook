package com.licyun.util;

import com.licyun.model.User;
import com.licyun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


/**
 * Created by 李呈云
 * Description:
 * 2016/9/27.
 */
@Component
public class AdminLoginValid implements Validator {

    @Autowired
    private UserService userService;

    public boolean supports(Class<?> klass) {
        return User.class.isAssignableFrom(klass);
    }

    public void validate(Object target, Errors errors) {
        User user = (User) target;
        User sqlUser = userService.findByEmail(user.getEmail());
        ValidationUtils.rejectIfEmpty(errors, "email", "useremail.required");
        ValidationUtils.rejectIfEmpty(errors, "passwd", "userpasswd.required");

        if(sqlUser != null){
            System.out.println("is null ");
            if(sqlUser.getPasswd().equals(user.getPasswd())){
                System.out.println("password"+ user.getPasswd());
                if(sqlUser.getType() == 1){

                }else{
                    errors.rejectValue("email", "useremail.notexist");
                }
            }else{
                errors.rejectValue("passwd", "userpasswd.error");
            }
        }else{
            errors.rejectValue("email", "useremail.notexist");
        }

    }
}