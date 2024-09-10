package com.csmtech.repository;

import com.csmtech.model.CommunicationMaster;
import com.csmtech.model.Items;
import com.csmtech.model.TestTaker;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class CommunicationRepositoryTest {

    @Autowired
    CommunicationRepository communicationRepository;
    
    @Test
    @Order(1)
    @DisplayName("for save communication")
    public void givenItemObject_whenSaveItem_ThenReturnItem() {
    	
    	TestTaker testTaker = new TestTaker();
    	testTaker.setTestTakerId(1);
    	testTaker.setTestTakerName("Gita Campus");
    	testTaker.setPhoneNumber("7898328973");
    	testTaker.setOfficerEmail("ki23@gmail.com");
    	testTaker.setPlacementOfficer("pk singh");
    	testTaker.setIsDeleted(null);
    	testTaker.setCollegeAddress("bbsr");
    	
    	CommunicationMaster communication= new CommunicationMaster();
    	communication.setCommunicationId(1);
    	communication.setMessage("let it be go there");
    	communication.setTestTaker(testTaker);
    	communication.setFilePaths(null);
    	
    	CommunicationMaster savecommunication = communicationRepository.save(communication);
    	
    	assertThat(savecommunication).isNotNull();
    	assertThat(savecommunication.getCommunicationId()).isGreaterThan(0);
    	
    }

    @Order(2)
    @Disabled("No records in Table ")
    @DisplayName("Test to get All Saved Records")
    @Test
    public void givenCommunicationMaster_WhenFound_ReturnList(){
        //given Communication Master

        //When found return list
        List<CommunicationMaster> communicationMasters = communicationRepository.findAllRecords();

        //Then verify
        assertNotNull(communicationMasters);
        assertFalse(communicationMasters.isEmpty());

    }

    @Order(3)
    @Disabled("No records in Table ")
    @DisplayName("Test to find Record by Id")
    @ParameterizedTest
    @ValueSource(ints = { 1 })
    public void givenCommunicationMasterId_WhenFound_Return(int id){

        //given Communication Master Id

        //When found return
        CommunicationMaster communicationMaster = communicationRepository
                .getReferenceById(id);

        //Then verify
        assertNotNull(communicationMaster);
        assertEquals(1,communicationMaster.getCommunicationId());
    }
}
