package com.csmtech.service;

import com.csmtech.model.Candidate;
import com.csmtech.repository.CandidateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CandidateServiceImplTest {

    @Mock
    CandidateRepository candidateRepository;

    @InjectMocks
    CandidateServiceImpl candidateService;

    Candidate candidate = createCandidate(1,"santhosh","pass",
            "asd@qwe.com","12321231");

    List<Candidate> candidateList = Stream.of(
                    createCandidate(1,"cand1","pass","cand1@mail.com","1231231"),
                    createCandidate(2,"cand2","pass","cand2@mail.com","1231232")
                    ).collect(Collectors.toList());

    @BeforeEach
    void setMockOutput() {
        //candidate.setSubTestTaker(subTestTaker);
        when(candidateRepository.findCandidateByCandnameAndPassword(candidate.getCandidateemail(),candidate.getCandpassword()))
                .thenReturn(candidate);
        when(candidateRepository.findAllNotDeleted()).thenReturn(candidateList);
        when(candidateRepository.findCandidateBySubTestTakerId(1)).thenReturn(candidateList);
        when(candidateRepository.findCandidateByTestTakerId(1)).thenReturn(candidateList);
        when(candidateRepository.findBytestTakerIdAndsubTetTakerId(1,1))
                .thenReturn(candidateList);
        when(candidateRepository.findDetailsById(candidate.getCandid())).thenReturn(candidate);

        when(candidateRepository.save(Mockito.any(Candidate.class)))
                .thenAnswer(i -> i.getArguments()[0]);
    }

    @DisplayName("Test to save Candidate")
    @Test
    void givenCandidate_WhenSave_ThenReturnSaved() {
        Candidate candidate1 = createCandidate(2,"Sahoo",
                "pass","sds@sdas.com","2312312");
        Candidate savedCand = candidateService.saveCandidate(candidate1);
        assertNotNull(savedCand);
        assertEquals(candidate1.getCandid(),savedCand.getCandid());
        assertEquals(candidate1.getCandidateemail(),savedCand.getCandidateemail());
        assertEquals(candidate1.getCandMobile(),savedCand.getCandMobile());
        assertEquals(candidate1.getCandpassword(),savedCand.getCandpassword());
    }

    @DisplayName("Test to find Candidate by name and pass")
    @Test
    void givenCandidateNamePass_WhenFind_ThenReturn() {

        Candidate findCand = candidateService.findCandidateByCandnameAndPassword("asd@qwe.com","pass");
        assertNotNull(findCand);
        assertEquals("asd@qwe.com",findCand.getCandidateemail());
        assertEquals("pass",findCand.getCandpassword());
    }

    @DisplayName("Test to find Candidate by id")
    @Test
    void givenCandidateId_WhenFind_ThenReturn() {

        Candidate findCand = candidateService.findDetailsById(1);
        assertNotNull(findCand);
        assertEquals(1,findCand.getCandid());
    }

    @DisplayName("Test to find all Candidates")
    @Test
    void givenCandidate_WhenFind_ThenReturnList() {

        List<Candidate> findCandList = candidateService.findAllCandidate();
        assertNotNull(findCandList);
        assertFalse(findCandList.isEmpty());
        assertEquals(2,findCandList.size());
    }

    @DisplayName("Test to find Candidates by subtest taker")
    @Test
    void givenSubTestTaker_WhenFind_ThenReturnList() {

        List<Candidate> findCandList = candidateService.findCandidateBySubTestTakerId(1);
        assertNotNull(findCandList);
        assertFalse(findCandList.isEmpty());
        assertEquals(2,findCandList.size());
    }

    @DisplayName("Test to find Candidates by test taker id")
    @Test
    void givenTestTakerId_WhenFind_ThenReturnList() {

        List<Candidate> findCandList = candidateService.findCandidateByTestTakerId(1);
        assertNotNull(findCandList);
        assertFalse(findCandList.isEmpty());
        assertEquals(2,findCandList.size());
    }

    @DisplayName("Test to find Candidates by test taker id and test taker id")
    @Test
    void givenTestTakerIdAndSubTestTakerId_WhenFind_ThenReturnList() {

        List<Candidate> findCandList = candidateService.findBytestTakerIdAndsubTetTakerId(1,1);
        assertNotNull(findCandList);
        assertFalse(findCandList.isEmpty());
        assertEquals(2,findCandList.size());
    }

    private Candidate createCandidate(int id, String name, String pass, String email, String number) {
        Candidate candidate1 = new Candidate();
        candidate1.setCandid(id);
        candidate1.setCandFirstname(name);
        candidate1.setCandpassword(pass);
        candidate1.setCandidateemail(email);
        candidate1.setCandMobile(number);

        return candidate1;
    }
}
