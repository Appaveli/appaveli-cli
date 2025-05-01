package com.example.util;

import javax.servlet.http.HttpSession;
import com.example.domain.User;

public class SessionUtils {

    public static boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    public static User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    public static void setLoggedInUser(HttpSession session, User user) {
        session.setAttribute("user", user);
    }

    public static void logout(HttpSession session) {
        session.invalidate();
    }
}