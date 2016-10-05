package voliy.samples.dog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@RestController
public class SessionController {
    private static int i = 1;

    @RequestMapping(method = RequestMethod.GET, path = "/session")
    public SessionInfo sessionInfo() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        String userKey = "user";
        if (session.getAttribute(userKey) == null) {
            session.setAttribute(userKey, "test-" + (i++));
        }

        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.user = (String) session.getAttribute(userKey);
        return sessionInfo;
    }

    private static class SessionInfo {
        private String user;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }
    }
}
