package com.csmtech.service;

import com.csmtech.model.Configure;
import com.csmtech.model.SubTestTaker;
import com.csmtech.repository.ConfigureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ConfigureServiceImplTest {

    @Mock
    ConfigureRepository configureRepository;

    @InjectMocks
    ConfigureServiceImpl configureService;

    @BeforeEach
    void setMockOutput() {
        Configure configure = createConfigure(2,2);
        when(configureRepository.findConfigureBySubTestTakerId(1)).thenReturn(configure);
        when(configureRepository.save(Mockito.any(Configure.class)))
                .thenAnswer(i -> i.getArguments()[0]);
    }

    @DisplayName("Test to save Configure")
    @Test
    void givenConfigure_WhenSave_ThenReturnSaved() {
        Configure configure = createConfigure(1,1);
        Configure savedConfigure = configureService.saveConfigure(configure);
        assertNotNull(savedConfigure);
        assertEquals(1,savedConfigure.getConfigId());
    }

    @DisplayName("Test to find Configure by subTest Taker id")
    @Test
    void givenSubTestTaker_WhenFound_ThenReturn() {
        Configure configure = configureService.findConfigureBySubTestTakerId(1);
        assertNotNull(configure);
        assertEquals(2,configure.getConfigId());
        assertEquals(2,configure.getSubTestTaker().getSubTestTakerId());
    }

    private Configure createConfigure(int id,int subTestTakerId) {
        SubTestTaker subTestTaker = new SubTestTaker();
        subTestTaker.setSubTestTakerId(subTestTakerId);

        Configure configure = new Configure();
        configure.setConfigId(id);
        configure.setSubTestTaker(subTestTaker);
        return configure;
    }
}
