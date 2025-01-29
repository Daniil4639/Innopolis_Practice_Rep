package app.controllers_tests;

import app.configs.SpringWebInitializer;
import app.configs.WebConfig;
import app.exceptions.IncorrectBodyException;
import app.exceptions.NoDataException;
import app.models.Student;
import app.repositories.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringWebInitializer.class, WebConfig.class})
@WebAppConfiguration
public class StudentControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockitoBean
    private StudentRepository repository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @DisplayName("Mock MVC configuration test")
    public void givenWac_whenServletContext_thenItProvidesStudentController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        Assertions.assertNotNull(servletContext);
        Assertions.assertInstanceOf(MockServletContext.class, servletContext);
        Assertions.assertNotNull(webApplicationContext.getBean("studentController"));
    }

    @Test
    @DisplayName("Students: create request test")
    public void createTest() throws Exception {
        Student student = new Student(
                1, "test_user", "test_email", new Integer[] {1}
        );

        Mockito.when(repository.createStudent(student)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(student))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test_email"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("test_user"));;
    }

    @Test
    @DisplayName("Students: get request test")
    public void getTest() throws Exception {
        Student student = new Student(
                1, "test_user", "test_email", new Integer[] {1}
        );

        Mockito.when(repository.readStudent(1)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.get("/students/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test_email"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("test_user"));;
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Students: update request test")
    public void updateTest() throws Exception {
        Student student = new Student(
                1, "test_user", "new_test_email", new Integer[] {1}
        );

        Student requestStudent = new Student(
                null, null, "new_test_email", null
        );

        Mockito.when(repository.updateStudent(1, requestStudent)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.put("/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(requestStudent))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("new_test_email"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("test_user"));
    }

    @Test
    @DisplayName("Students: add grade request test")
    public void addGradeTest() throws Exception {
        Student student = new Student(
                1, "test_user", "new_test_email", new Integer[] {1, 2}
        );

        Mockito.when(repository.addGrade(1, 2)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.put("/students/1/2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.gradesList[0]").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gradesList[1]").value("2"));
    }

    @Test
    @DisplayName("Students: delete request test")
    public void deleteTest() throws Exception {
        Mockito.when(repository.deleteStudent(1)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/students/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Record has been deleted!"));
    }
}
