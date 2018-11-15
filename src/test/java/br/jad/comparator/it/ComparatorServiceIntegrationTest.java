package br.jad.comparator.it;

import br.jad.comparator.commons.ComparatorType;
import br.jad.comparator.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * ComparatorService integration tests
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ComparatorServiceIntegrationTest {

    @TestConfiguration
    static class ComparatorServiceContextConfigTest {
        @Bean
        public ComparatorService comparatorService() {
            return new ComparatorServiceImpl();
        }
    }

    @Autowired
    private ComparatorService comparatorService;

    @MockBean
    private ComparatorRepository comparatorRepository;

    @Test
    public void findOne_NotFound_Success() {
        when(comparatorRepository.findOne(any(ComparatorIdentity.class))).thenReturn(null);

        ComparatorRecord found = comparatorService.findByIdAndType(1L, ComparatorType.LEFT);

        assertThat(found).isEqualTo(null);
    }

    @Test
    public void findAll_ById_Success() {
        ComparatorRecord left = new ComparatorRecord(
                new ComparatorIdentity(1L, ComparatorType.LEFT), "{ \"code\": \"TEST1\", \"name\": \"Test One\" }", LocalDateTime.now());

        when(comparatorRepository.findOne(new ComparatorIdentity(1L, ComparatorType.LEFT))).thenReturn(left);

        ComparatorRecord right = new ComparatorRecord(
                new ComparatorIdentity(1L, ComparatorType.RIGHT), "{ \"code\": \"TEST2\", \"name\": \"Test Two\" }", LocalDateTime.now());

        when(comparatorRepository.findOne(new ComparatorIdentity(1L, ComparatorType.RIGHT))).thenReturn(right);

        ComparatorRecord foundLeft = comparatorService.findByIdAndType(1L, ComparatorType.LEFT);
        ComparatorRecord foundRight = comparatorService.findByIdAndType(1L, ComparatorType.RIGHT);

        assertThat(foundLeft.getId()).isEqualTo(left.getId());
        assertThat(foundRight.getId()).isEqualTo(right.getId());
    }

    @Test
    public void save_LeftType_Success() {
        String json = "{ \"code\": \"TEST1\", \"name\": \"Test One\" }";

        ComparatorRecord comparatorRecord = new ComparatorRecord(
                new ComparatorIdentity(1L, ComparatorType.LEFT), json, LocalDateTime.now());

        when(comparatorRepository.save(any(ComparatorRecord.class))).thenReturn(comparatorRecord);

        ComparatorRecord newComparatorRecord = comparatorService.save(1L, ComparatorType.LEFT, json);

        assertThat(newComparatorRecord.getId()).isEqualTo(comparatorRecord.getId());
        assertThat(newComparatorRecord.getContent()).isEqualTo(comparatorRecord.getContent());
    }

    @Test
    public void save_RightType_Success() {
        String json = "{ \"code\": \"TEST2\", \"name\": \"Test Two\" }";

        ComparatorRecord comparatorRecord = new ComparatorRecord(
                new ComparatorIdentity(1L, ComparatorType.RIGHT), json, LocalDateTime.now());

        when(comparatorRepository.save(any(ComparatorRecord.class))).thenReturn(comparatorRecord);

        ComparatorRecord newComparatorRecord = comparatorService.save(1L, ComparatorType.RIGHT, json);

        assertThat(newComparatorRecord.getId()).isEqualTo(comparatorRecord.getId());
        assertThat(newComparatorRecord.getContent()).isEqualTo(comparatorRecord.getContent());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getDifference_NotFoundJson_ExceptionThrown() {
        when(comparatorRepository.findOne(any(ComparatorIdentity.class))).thenReturn(null);

        comparatorService.getDifference(1L);
    }
}
