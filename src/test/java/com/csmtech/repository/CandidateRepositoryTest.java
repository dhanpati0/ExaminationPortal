package com.csmtech.repository;

import com.csmtech.model.Candidate;
import com.csmtech.model.SubTestTaker;
import com.csmtech.model.TestTaker;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CandidateRepositoryTest {

    @Autowired
    CandidateRepository candidateRepository;
    
    @DisplayName("save Candidate")
    @Test
    @Order(1)
    public void givenCandidateObject_WhenSave_thenReturnCandidate() {
    	
    	TestTaker testTaker = new TestTaker();
    	testTaker.setTestTakerId(1);
    	testTaker.setTestTakerName("hig");
    	testTaker.setPhoneNumber("7898328973");
    	testTaker.setOfficerEmail("ki23@gmail.com");
    	testTaker.setPlacementOfficer("pk singh");
    	testTaker.setIsDeleted(null);
    	testTaker.setCollegeAddress("bbsr");
    	
    	SubTestTaker subTestTaker = new SubTestTaker();
    	subTestTaker.setSubTestTakerId(1);
    	subTestTaker.setSubTestTakerName("batch-1");
    	subTestTaker.setTestTaker(testTaker);
    	
    	
    	Candidate cand = new Candidate();
    	cand.setCandid(1);
    	cand.setCandFirstname("Megha");
    	cand.setCandLastname("Singh");
    	cand.setCandidateemail("mk234@gmail.com");
    	cand.setCandMobile("9048779343");
    	cand.setCandCollegeName("GIET Khordha");
    	cand.setCandpassword("Megha@#");
    	cand.setPause(null);
    	cand.setCandStartTime(null);
    	cand.setCandEndTime(null);
    	cand.setCandLoginTime(null);
    	cand.setMarkAppear(null);
    	cand.setProgress(null);
    	cand.setResultStatus(null);
    	cand.setStatus("inactive");
    	cand.setTotalMark(null);
    	cand.setIsdelete("No");
    	cand.setSubTestTaker(subTestTaker);
    	
    	Candidate saveCandidate = candidateRepository.save(cand);
    	
    	assertThat(saveCandidate).isNotNull();
    	assertThat(saveCandidate.getCandid()).isGreaterThan(0);
    	
    }

    @Order(2)
    @DisplayName("Test for finding Candidate by candidate Id")
    @ParameterizedTest
    @ValueSource(ints = { 1 })
    public void givenCandId_WhenFoundCand_ThenReturnCand(int id){

        //Given candidate Id as ValueSource

        //When candidate available with given id
        Candidate candidate = candidateRepository.findDetailsById(id);

        //Then validate
        assertNotNull(candidate);
        assertEquals(1,candidate.getCandid());
        assertEquals("No",candidate.getIsdelete());
    }

    @Order(3)
    @DisplayName("Test to find all Candidates by sub test taker id")
    @ParameterizedTest
    @ValueSource(ints = { 1 })
    public void givenSubTestTakerId_WhenFound_ThenReturnCandList(int id){
        //Given candidate Id as ValueSource

        //When found candidates
        List<Candidate> candidates = candidateRepository.findAllCandidates(id);

        //Then validate
        assertNotNull(candidates);
        assertFalse(candidates.isEmpty());
        assertEquals(1,candidates.get(0).getSubTestTaker().getSubTestTakerId());

    }

    @Order(4)
    @DisplayName("Test for finding Candidate by name and passw")
    @ParameterizedTest
    @MethodSource("provideStringsNamePassword")
    public void givenNameNPassw_WhenFindCand_ThenReturnCand(String name,String passw){

        //given username and password as method source

        //When find candidate
        Candidate candidate = candidateRepository
                .findCandidateByCandnameAndPassword("mk234@gmail.com","Megha@#");
        //Then verify
        assertNotNull(candidate);
        assertFalse(candidate.getCandidateemail().isEmpty());
        assertFalse(candidate.getCandFirstname().isEmpty());
        assertEquals("mk234@gmail.com",candidate.getCandidateemail());
        assertEquals("Megha",candidate.getCandFirstname());
    }

    private static Stream<Arguments> provideStringsNamePassword() {
        return Stream.of(
                Arguments.of("mk234@gmail.com","Megha@#"));
    }
    

    @Order(5)
    @DisplayName("Test to find all not deleted")
    @Test
    public void givenNotDeleted_WhenFindCandList_ThenReturnList(){
        //Given not deleted

        //When find records not deleted
        List<Candidate> candidates = candidateRepository.findAllNotDeleted();

        //Then validate
        assertNotNull(candidates);
        assertFalse(candidates.isEmpty());
    }

    @Order(6)
    @DisplayName("Test to find Candidates by email")
    @ParameterizedTest
    @ValueSource(strings = { "mk234@gmail.com" })
    public void givenEmail_WhenFound_ThenReturnCand(String email){
        //Given candidate Id as ValueSource

        //When found candidates
        Candidate candidates = candidateRepository.getCandidateByEmail(email);

        //Then validate
        assertNotNull(candidates);
        assertFalse(candidates.getCandidateemail().isEmpty());
        assertEquals("mk234@gmail.com",candidates.getCandidateemail());

    }

    @Order(7)
    @DisplayName("Test to find all Candidates by sub test taker id")
    @ParameterizedTest
    @MethodSource("provideStringsStatusNTakerId")
    public void givenStatusNSubTestTakerId_WhenFound_ThenReturnCandList(String status,int subTestTakerId){
        //Given candidate Id as ValueSource

        //When found candidates
        List<Candidate> candidates = candidateRepository
                .findAllCandidateListByStatusAndSubTestTaker("inactive",1);

        //Then validate
        assertNotNull(candidates);
        assertFalse(candidates.isEmpty());
        assertEquals(1,candidates.get(0).getSubTestTaker().getSubTestTakerId());

    }

    private static Stream<Arguments> provideStringsStatusNTakerId() {
        return Stream.of(
                Arguments.of("inactive",1));
    }

    @Order(8)
    @DisplayName("Test to find all active Candidates")
    @ParameterizedTest
    @MethodSource("provideStringsStatusNTakerId")
    public void givenActiveStatus_WhenFound_ReturnActiveOnly(){

        //Given status as active

        //when found status as active
        List<Candidate> candidates = candidateRepository
                .findAllCandidateWithActiveStatus();

        //Then validate status
        assertNotNull(candidates);
        assertFalse(candidates.stream()
                .anyMatch(cand -> cand.getStatus().equals("inactive")));
    }
}
