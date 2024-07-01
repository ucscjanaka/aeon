package com.library.aeon.service;


import com.library.aeon.model.Borrower;
import com.library.aeon.repository.BorrowerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BorrowerServiceTest {

    @InjectMocks
    private BorrowerService borrowerService;

    @Mock
    private BorrowerRepository borrowerRepository;

    @Test
    void testRegisterBorrower() {
        // Prepare test data
        Borrower borrowerToRegister = new Borrower();
        borrowerToRegister.setName("Janaka");
        borrowerToRegister.setEmail("janaka.com");

        Borrower registeredBorrower = new Borrower();
        registeredBorrower.setId(1L);
        registeredBorrower.setName("Janaka");
        registeredBorrower.setEmail("janaka.com");

        // Mock behavior
        when(borrowerRepository.save(borrowerToRegister)).thenReturn(registeredBorrower);

        // Perform the call
        Borrower result = borrowerService.registerBorrower(borrowerToRegister);

        // Assertions
        assertNotNull(result);
        assertEquals(registeredBorrower.getId(), result.getId());
        assertEquals(registeredBorrower.getName(), result.getName());
        assertEquals(registeredBorrower.getEmail(), result.getEmail());
    }
}

