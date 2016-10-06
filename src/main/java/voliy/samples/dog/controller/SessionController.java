package voliy.samples.dog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

import static io.qala.datagen.RandomShortApi.alphanumeric;

@RestController
public class SessionController {
    private static int i = 1;

    @RequestMapping(method = RequestMethod.GET, path = "/session")
    public SessionInfo sessionInfo() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        String userKey = "user";
        if (session.getAttribute(userKey) == null) {
            String user = String.format("User-%s-%s", i++, alphanumeric(5));
            session.setAttribute(userKey, user);
        }

        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setId(session.getId());
        sessionInfo.setUser((String) session.getAttribute(userKey));
        return sessionInfo;
    }

    private static class SessionInfo {
        private String id;
        private String user;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }
    }
}
