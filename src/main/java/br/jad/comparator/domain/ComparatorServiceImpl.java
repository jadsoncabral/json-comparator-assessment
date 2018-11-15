package br.jad.comparator.domain;

import br.jad.comparator.commons.ComparatorHelper;
import br.jad.comparator.commons.ComparatorType;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * Comparator Service implementation
 */
@Service
public class ComparatorServiceImpl implements ComparatorService {

    /**
     * Spring will Inject the repository due its @Repository annotation
     */
    @Autowired
    private ComparatorRepository comparatorRepository;

    private static final Logger log = LoggerFactory.getLogger(ComparatorController.class);

    /**
     * This method is used to call repository and store one decoded JSON String.
     * @param id Numeric unique identifier to the specified JSON
     * @param type ComparatorType identifier to the specified JSON
     * @param content Base64 encoded JSON String
     * @return ComparatorRecord
     * @exception IllegalArgumentException on input error.
     */
    @Override
    @Transactional
    public ComparatorRecord save(Long id, ComparatorType type, String content) {
        if (id == null) {
            log.error("ID parameter was not found");
            throw new IllegalArgumentException("ID should not be null");
        }
        if (type == null) {
            log.error("Type parameter was not found");
            throw new IllegalArgumentException("Type should not be null");
        }
        if (StringUtils.isEmpty(content)) {
            log.error("JSON decoded String parameter was not found");
            throw new IllegalArgumentException("Content should not be empty");
        }

        ComparatorRecord comparatorRecord = new ComparatorRecord(
                new ComparatorIdentity(id, type),
                content,
                LocalDateTime.now());

        return comparatorRepository.save( comparatorRecord );
    }

    /**
     * This method is used to find stored decoded JSON String.
     * @param id Numeric unique identifier to the specified JSON
     * @param type ComparatorType identifier to the specified JSON
     * @return ComparatorRecord
     */
    @Override
    public ComparatorRecord findByIdAndType(Long id, ComparatorType type) {
        return comparatorRepository.findOne( new ComparatorIdentity(id, type) );
    }

    /**
     * This method is used to verify and return the difference between two decoded JSON Strings.
     * @param id Numeric unique identifier to find the specified JSON's
     * @return JSON as String with difference between LEFT and RIGHT JSON
     * @exception IllegalArgumentException on input error.
     */
    @Override
    public String getDifference(Long id) {
        ComparatorRecord left = comparatorRepository.findOne( new ComparatorIdentity(id, ComparatorType.LEFT) );
        ComparatorRecord right = comparatorRepository.findOne( new ComparatorIdentity(id, ComparatorType.RIGHT) );

        if (left == null) {
            log.error("No LEFT type was found for ID [" + id + "]");
            throw new IllegalArgumentException("No LEFT type was found for ID [" + id + "]");
        }
        if (right == null) {
            log.error("No RIGHT type was found for ID [" + id + "]");
            throw new IllegalArgumentException("No RIGHT type was found for ID [" + id + "]");
        }

        try {
            return ComparatorHelper.compare(left.getContent(), right.getContent());
        } catch (JSONException e) {
            log.error("Content of ComparatorRecord with ID [\" + id + \"] is not a valid json:", e);
            throw new IllegalArgumentException("Content of ComparatorRecord with ID [" + id + "] is not a valid json.");
        }
    }
}
