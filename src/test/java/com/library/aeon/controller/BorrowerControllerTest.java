package com.library.aeon.controller;



import com.library.aeon.model.Borrower;
import com.library.aeon.service.BorrowerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BorrowerControllerTest {

    @InjectMocks
    private BorrowerController borrowerController;

    @Mock
    private BorrowerService borrowerService;

    @Test
    void testRegisterBorrower() {
        // Prepare test data
        Borrower borrowerToRegister = new Borrower();
        borrowerToRegister.setName("Janaka");
        borrowerToRegister.setEmail("Janaka.com");

        Borrower registeredBorrower = new Borrower();
        registeredBorrower.setId(1L);
        registeredBorrower.setName("Janaka");
        registeredBorrower.setEmail("Janaka.com");

        // Mock behavior
        when(borrowerService.registerBorrower(borrowerToRegister)).thenReturn(registeredBorrower);

        // Perform the call
        ResponseEntity<Borrower> responseEntity = borrowerController.registerBorrower(borrowerToRegister);

        // Assertions
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        Borrower result = responseEntity.getBody();
        assertNotNull(result);
        assertEquals(registeredBorrower.getId(), result.getId());
        assertEquals(registeredBorrower.getName(), result.getName());
        assertEquals(registeredBorrower.getEmail(), result.getEmail());
    }
}
