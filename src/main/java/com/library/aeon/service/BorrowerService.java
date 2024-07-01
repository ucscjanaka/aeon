package com.library.aeon.service;


import com.library.aeon.model.Borrower;
import com.library.aeon.repository.BorrowerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing borrowers.
 */
@Service
public class BorrowerService {

    Logger logger = LoggerFactory.getLogger(BorrowerService.class);

    @Autowired
    private BorrowerRepository borrowerRepository;

    /**
     * Registers a new borrower.
     *
     * @param borrower The Borrower object containing borrower details to register.
     * @return The registered Borrower object.
     */
    public Borrower registerBorrower(Borrower borrower) {
        return borrowerRepository.save(borrower);
    }
}
