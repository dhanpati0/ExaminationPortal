package com.csmtech.controller;

import com.csmtech.model.CommunicationMaster;
import com.csmtech.model.TestTaker;
import com.csmtech.service.CommunicationService;
import com.csmtech.service.TestTakerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CollegeControllerTest {

    @InjectMocks
    private CollegeController collegeController;

    @Mock
    private TestTakerService testTakerService;

    @Mock
    private CommunicationService communicationService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private HttpServletResponse httpServletResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addCollegeTest() {

        /*TestTaker testTaker = new TestTaker();
        testTaker.setTestTakerId(5);
        model.addAttribute(testTaker);*/
        String view = collegeController.addCollege(model);
        assertEquals("admin/addCollege", view);
        verify(model).addAttribute(eq("collegeList"), anyList());
    }

    @Test
    void addTestTakerTest() {
        when(testTakerService.getAll()).thenReturn(Collections.emptyList());

        String view = collegeController.addTestTaker(null, "Test College",
                "Officer", "email", "123",
                "Address", redirectAttributes, model);

        assertEquals("redirect:./addCollege", view);
        verify(testTakerService).save(any(TestTaker.class));
        verify(redirectAttributes).addFlashAttribute(eq("savecollege"),
                eq("College saved successfully"));
    }

    @Test
    void deleteCollegeTest() {
        TestTaker testTaker = new TestTaker();
        testTaker.setTestTakerId(5);
        testTaker.setIsDeleted("No");
        model.addAttribute(testTaker);
        when(testTakerService.findById(anyInt())).thenReturn(testTaker);

        String view = collegeController.deleteCollege(1, redirectAttributes);

        assertEquals("redirect:./addCollege", view);
        verify(testTakerService).findById(eq(1));
        verify(testTakerService).save(any(TestTaker.class));
        //verify(redirectAttributes).addFlashAttribute(eq("delete"), anyString());
    }

    @Test
    void updateCollegeTest() {
        when(testTakerService.findById(anyInt())).thenReturn(new TestTaker());

        String view = collegeController.updateCollege(1, model, redirectAttributes);

        assertEquals("redirect:./addCollege", view);
        verify(testTakerService).findById(eq(1));
        verify(redirectAttributes).addFlashAttribute(eq("updateCollege"), any(TestTaker.class));
    }

    @Test
    void getEmailByCollegeTest() throws IOException {
        when(testTakerService.getEmailIdByTestTakerId(anyInt())).thenReturn("email");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(httpServletResponse.getWriter()).thenReturn(writer);

        collegeController.getEmailByCollege(1, httpServletResponse);

        verify(httpServletResponse).getWriter();
        assertEquals("email", stringWriter.toString());
    }

    @Test
    void senEmailToOfficerTest() throws IOException {
        MultipartFile[] attachments = new MultipartFile[0];
        TestTaker testTaker = new TestTaker();
        testTaker.setTestTakerId(5);
        testTaker.setIsDeleted("No");
        testTaker.setOfficerEmail("qwqe@wee.com");
        when(communicationService.saveAllDetails(any())).thenReturn(null);

        String view = collegeController.senEmailToOfficer(testTaker,
                "message", attachments, model, redirectAttributes);

        assertEquals("redirect:./addCollege", view);
        verify(communicationService).saveAllDetails(any());
        verify(redirectAttributes).addFlashAttribute(eq("saveDetail"), eq("yes"));
    }

    @Test
    void getReportsByLimitTest() throws IOException {
        CommunicationMaster communicationMaster = new CommunicationMaster();
        List<CommunicationMaster> communicationMasterList
                = new ArrayList<CommunicationMaster>(Collections.singletonList(communicationMaster));
        when(communicationService.findall()).thenReturn(communicationMasterList);
        when(communicationService.getReportsByLimit(anyInt())).thenReturn(Collections.emptyList());
        when(httpServletResponse.getWriter()).thenReturn(mock(PrintWriter.class));

        collegeController.getReportsByLimit(0, httpServletResponse);

        verify(httpServletResponse).setContentType(eq("application/json"));
    }
}


