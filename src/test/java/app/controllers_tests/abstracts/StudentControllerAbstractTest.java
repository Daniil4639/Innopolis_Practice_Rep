package app.controllers_tests.abstracts;

import app.configs.SpringWebInitializer;
import app.configs.web_config.SessionData;
import app.configs.web_config.WebConfig;
import app.services.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringWebInitializer.class, WebConfig.class})
@WebAppConfiguration
public class StudentControllerAbstractTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @MockitoBean
    protected StudentService service;

    @MockitoBean
    protected SessionData sessionData;

    protected MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @DisplayName("Mock for StudentController configuration test")
    public void givenWac_whenServletContext_thenItProvidesStudentController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        Assertions.assertNotNull(servletContext);
        Assertions.assertInstanceOf(MockServletContext.class, servletContext);
        Assertions.assertNotNull(webApplicationContext.getBean("studentController"));
    }

    protected String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
