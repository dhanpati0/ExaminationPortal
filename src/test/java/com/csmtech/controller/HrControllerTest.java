package com.csmtech.controller;

import com.csmtech.model.Role;
import com.csmtech.model.User;
import com.csmtech.service.CandidateService;
import com.csmtech.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class HrControllerTest {
    @InjectMocks
    private HrController hrController;

    @Mock
    private UserService userService;

    @Mock
    private CandidateService candidateService;

    @Mock
    private HttpSession httpSession;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getHrDashboardTest() {
        String view = hrController.getHrDashboard();
        assertEquals("hr/hrDashboard", view);
    }

    @Test
    void manageHrProfileTest() {
        String view = hrController.manageHrProfile(model);
        assertEquals("hr/manageProfile", view);
        verify(model).addAttribute(eq("allCandidate"), anyList());
    }

    @Test
    void editHrTest() {
        Role role = new Role();
        role.setRoleName("HR");
        role.setRoleId(6);

        User user = new User();
        user.setUserId(1);
        user.setUsername("username");
        user.setPassword("password");
        user.setRole(role);

        when(userService.findUserDetailsById(anyInt())).thenReturn(new User());

        String view = hrController.editHr(user, 1, model);

        assertEquals("hr/hrDashboard", view);
        //verify(userService).findUserDetailsById(eq(1));
        //verify(userService).saveDetailsOfUser(any(User.class));
        verify(model).addAttribute(eq("msg"), eq("plzz.... fill the form"));
    }

    @Test
    void forgotPasswordTest() {
        User user = new User();
        user.setName("Santhosh");
        when(httpSession.getAttribute("sessionData")).thenReturn(user);
        String view = hrController.forgotPassword(model);
        assertEquals("hr/hrResetPassword", view);
        verify(model).addAttribute(eq("username"), anyString());
    }

    @Test
    void savePasswordTest() {
        User user = new User();
        user.setUserId(1);

        when(httpSession.getAttribute("sessionData")).thenReturn(user);

        String view = hrController.savePassword(1, "newPassword", "password", model);

        assertEquals("redirect:./forgotPassword", view);
        verify(httpSession).getAttribute(eq("sessionData"));
        verify(model).addAttribute(eq("msg"), eq("Entered password is wrong..!! "));
    }

    @Test
    void viewResultTest() {
        String view = hrController.viewResult(model);
        assertEquals("hr/viewResult", view);
        verify(model).addAttribute(eq("result"), anyList());
    }
}
