package com.library.aeon.controller;



import com.library.aeon.model.Borrower;
import com.library.aeon.service.BorrowerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Borrower", description = "Library management APIs")
@RestController
@RequestMapping("/api")
public class BorrowerController {

    Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BorrowerService borrowerService;

    /**
     * Registers a new Borrower.
     *
     * @param borrower The Borrower object containing borrower details.
     * @return The registered Borrower object.
     */
    @Operation(summary = "Register a new Borrower", tags = {"borrower", "post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = Borrower.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})

    @PostMapping("/borrower")
    public ResponseEntity<Borrower> registerBorrower(@RequestBody Borrower borrower) {

        try {
            Borrower newBorrower = borrowerService.registerBorrower(borrower);

            return new ResponseEntity<>(newBorrower, HttpStatus.CREATED);

        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
