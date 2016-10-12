package voliy.samples.dog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static io.qala.datagen.RandomShortApi.alphanumeric;

@RestController
public class SessionController {
    private static int i = 1;

    @RequestMapping(method = RequestMethod.GET, path = "/session")
    public SessionInfo sessionInfo(@RequestParam(required = false) Integer timeout,
            @RequestParam(required = false) String value, HttpServletRequest request) {
        HttpSession session = request.getSession();
        updateSession(session, request.getServerPort(), timeout, value);
        return sessionInfo(session);
    }

    private void updateSession(HttpSession session, int port, Integer timeout, String value) {
        if (session.getAttribute("user") == null) {
            String user = String.format("Tomcat-%s___User-%s-%s", port, i++, alphanumeric(5));
            session.setAttribute("user", user);
        }
        if (timeout != null) {
            session.setMaxInactiveInterval(timeout);
        }
        if (value != null) {
            session.setAttribute("value", value);
        }
    }

    private SessionInfo sessionInfo(HttpSession session) {
        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setId(session.getId());
        sessionInfo.setUser((String) session.getAttribute("user"));
        sessionInfo.setTimeout(session.getMaxInactiveInterval());
        sessionInfo.setValue((String) session.getAttribute("value"));
        return sessionInfo;
    }

    private static class SessionInfo {
        private String id;
        private String user;
        private int timeout;
        private String value;

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

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
