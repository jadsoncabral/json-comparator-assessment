package br.jad.comparator.domain;

import br.jad.comparator.commons.ComparatorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Base64;

/**
 * Rest Controller to be used as API
 */
@Controller
@RequestMapping( "/v1/diff" )
public class ComparatorController {

    /**
     * Spring will Inject the service due its @Service annotation in implementation
     */
    @Autowired
    private ComparatorService comparatorService;

    private static final Logger log = LoggerFactory.getLogger( ComparatorController.class );

    /**
     * This method is used to store one decoded JSON Strings for LEFT type.
     * @param id Numeric unique identifier to the specified JSON
     * @param content Base64 encoded JSON String
     * @return HTTP 200 if stored successfully or HTTP 500 with failure reason
     */
    @RequestMapping( value = "/{id}/left", method = RequestMethod.POST )
    public ResponseEntity persistLeft(@PathVariable String id, @RequestBody String content) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode( content );
            String decodedContent = new String( decodedBytes );

            comparatorService.save( new Long(id), ComparatorType.LEFT, decodedContent);
        } catch (Exception ex) {
            log.error("Error found during save:", ex);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    /**
     * This method is used to store one decoded JSON Strings for RIGHT type.
     * @param id Numeric unique identifier to the specified JSON
     * @param content Base64 encoded JSON String
     * @return HTTP 200 if stored successfully or HTTP 500 with failure reason
     */
    @RequestMapping( value = "/{id}/right", method = RequestMethod.POST )
    public ResponseEntity persistRight(@PathVariable String id, @RequestBody String content) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode( content );
            String decodedContent = new String( decodedBytes );

            comparatorService.save( new Long(id), ComparatorType.RIGHT, decodedContent);
        } catch (Exception ex) {
            log.error("Error found during save:", ex);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    /**
     * This method is used to return the difference of LEFT and RIGHT JSON previously stored
     * @param id Numeric unique identifier find stored JSON
     * @return HTTP 200 with the result of JSON String comparison or HTTP 500 with failure reason
     */
    @RequestMapping( value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity checkDifference(@PathVariable String id) {

        String response;

        try {
            response = comparatorService.getDifference( new Long(id) );
        } catch (Exception ex) {
            log.error("Error found during JSON comparison:", ex);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        }

        return ResponseEntity.ok().body( response );
    }
}
