package uoggmk.college;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class AuthSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        authorities.forEach(authority -> {
            try {
                if (authority.getAuthority().equals("STUDENT")) {
                    redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/student");
                } else if (authority.getAuthority().equals("TEACHER")) {
                    redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/teacher");
                } else if (authority.getAuthority().equals("ADMIN")) {
                    redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/admin");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}
