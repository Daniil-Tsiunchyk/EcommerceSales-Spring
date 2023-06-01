package com.example.MonitoringInternetShop.Secure;

import com.example.MonitoringInternetShop.Models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class RoleCheckInterceptor implements HandlerInterceptor {

    private final String expectedRole;

    public RoleCheckInterceptor(String expectedRole) {
        this.expectedRole = expectedRole;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals(User.Role.valueOf(expectedRole))) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }

}
