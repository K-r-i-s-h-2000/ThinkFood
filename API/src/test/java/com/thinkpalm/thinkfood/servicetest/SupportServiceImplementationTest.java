package com.thinkpalm.thinkfood.servicetest;

import com.thinkpalm.thinkfood.entity.Support;
import com.thinkpalm.thinkfood.exception.EmptyInputException;
import com.thinkpalm.thinkfood.exception.InactiveSupportException;
import com.thinkpalm.thinkfood.exception.SupportNotFoundException;
import com.thinkpalm.thinkfood.model.Support1;

import com.thinkpalm.thinkfood.repository.SupportRepository;
import com.thinkpalm.thinkfood.service.SupportServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupportServiceImplementationTest {

    @Mock
    private SupportRepository supportRepository;

    @InjectMocks
    private SupportServiceImplementation supportService;

    @Test
    void testCreateSupportWithValidData() throws EmptyInputException {
        com.thinkpalm.thinkfood.model.Support supportInput = new com.thinkpalm.thinkfood.model.Support();
        supportInput.setId(1L);
        supportInput.setQuery("query");
        supportInput.setResponse("response message");

        Support expectedSupportEntity = new Support();
        expectedSupportEntity.setId(1L);
        expectedSupportEntity.setQuery(supportInput.getQuery());
        expectedSupportEntity.setResponse(supportInput.getResponse());


        when(supportRepository.save(any(Support.class))).thenReturn(expectedSupportEntity);

        com.thinkpalm.thinkfood.model.Support createdSupport = supportService.createSupport(supportInput);

        assertNotNull(createdSupport);
        //assertNotNull(createdSupport.getId());
        assertEquals(expectedSupportEntity.getQuery(), createdSupport.getQuery());
        assertEquals(expectedSupportEntity.getResponse(), createdSupport.getResponse());
        //assertEquals(expectedSupportEntity.getId(), createdSupport.getId());
    }


    @Test
    void testCreateSupportWithEmptyInput() {
        com.thinkpalm.thinkfood.model.Support supportInput = new com.thinkpalm.thinkfood.model.Support();

        assertThrows(EmptyInputException.class, () -> supportService.createSupport(supportInput));
    }

    @Test
    void testGetAllQueries() {
        Support support1 = new Support();
        support1.setId(1L);
        support1.setQuery("query 1");
        support1.setIsActive(true);

        Support support2 = new Support();
        support2.setId(2L);
        support2.setQuery("query 2");
        support2.setIsActive(true);

        List<Support> supportList = Arrays.asList(support1, support2);

        when(supportRepository.findAll()).thenReturn(supportList);

        Map<Long, String> result = supportService.getAllQueries();

        assertEquals(2, result.size());
        assertTrue(result.containsKey(1L));
        assertTrue(result.containsKey(2L));
        assertEquals("query 1", result.get(1L));
        assertEquals("query 2", result.get(2L));
    }

    @Test
    void testGetSupportById() throws SupportNotFoundException, InactiveSupportException {
        Long supportId = 1L;

        Support supportEntity = new Support();
        supportEntity.setId(supportId);
        supportEntity.setQuery("query");
        supportEntity.setResponse("response message");
        supportEntity.setIsActive(true);

        when(supportRepository.findById(supportId)).thenReturn(Optional.of(supportEntity));

        Support1 result = supportService.getSupportById(supportId);

        assertNotNull(result);
        assertEquals("query", result.getQuery());
        assertEquals("response message", result.getResponse());
    }

    @Test
    void testGetSupportByIdSupportNotFound() {
        Long supportId = 1L;

        when(supportRepository.findById(supportId)).thenReturn(Optional.empty());

        assertThrows(SupportNotFoundException.class, () -> supportService.getSupportById(supportId));
    }

    @Test
    void testGetSupportByIdInactiveSupport() {
        Long supportId = 1L;

        Support supportEntity = new Support();
        supportEntity.setId(supportId);
        supportEntity.setQuery("query");
        supportEntity.setResponse("response message");
        supportEntity.setIsActive(false);

        when(supportRepository.findById(supportId)).thenReturn(Optional.of(supportEntity));

        assertThrows(InactiveSupportException.class, () -> supportService.getSupportById(supportId));
    }

    @Test
    void testDeleteSupportByIdValidId() throws SupportNotFoundException {
        Long supportId = 1L;

        Support supportEntity = new Support();
        supportEntity.setId(supportId);
        supportEntity.setQuery("query");
        supportEntity.setResponse("response message");
        supportEntity.setIsActive(true);

        when(supportRepository.findById(supportId)).thenReturn(Optional.of(supportEntity));
        //when(supportRepository.save(any())).thenReturn(supportEntity);

        com.thinkpalm.thinkfood.model.Support deletedSupport = supportService.deleteSupportById(supportId);

        assertNotNull(deletedSupport);
        assertEquals(supportEntity.getId(), deletedSupport.getId());
        assertEquals(supportEntity.getQuery(), deletedSupport.getQuery());
        assertEquals(supportEntity.getResponse(), deletedSupport.getResponse());
        //assertFalse(deletedSupport.getIsActive());
    }

    @Test
    void testDeleteSupportByIdSupportNotFound() {
        Long supportId = 1L;

        when(supportRepository.findById(supportId)).thenReturn(Optional.empty());

        assertThrows(SupportNotFoundException.class, () -> supportService.deleteSupportById(supportId));
    }

    @Test
    void testSoftDeleteSupportById() throws SupportNotFoundException {
        Long supportId = 1L;

        Support supportEntity = new Support();
        supportEntity.setId(supportId);
        supportEntity.setQuery("query");
        supportEntity.setResponse("response message");
        supportEntity.setIsActive(true);

        when(supportRepository.findById(supportId)).thenReturn(Optional.of(supportEntity));

        String result = supportService.softDeleteSupportById(supportId);

        assertFalse(supportEntity.getIsActive());
        verify(supportRepository, times(1)).save(supportEntity);
        assertEquals("Support With Id " + supportId + " deleted successfully!", result);
    }

    @Test
    void testSoftDeleteSupportByIdSupportNotFound() {
        Long supportId = 1L;

        when(supportRepository.findById(supportId)).thenReturn(Optional.empty());

        assertThrows(SupportNotFoundException.class, () -> supportService.softDeleteSupportById(supportId));
    }
}