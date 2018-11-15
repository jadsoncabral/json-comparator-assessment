package br.jad.comparator.it;

import br.jad.comparator.commons.ComparatorType;
import br.jad.comparator.domain.ComparatorIdentity;
import br.jad.comparator.domain.ComparatorRecord;
import br.jad.comparator.domain.ComparatorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ComparatorRepository integration tests
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ComparatorRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ComparatorRepository repository;

    @Test
    public void findAll_ById_Success() throws Exception {
        ComparatorRecord left = new ComparatorRecord(new ComparatorIdentity(1L, ComparatorType.LEFT), "{ \"code\": \"TEST1\", \"name\": \"Test One\" }", LocalDateTime.now());
        entityManager.persist(left);

        ComparatorRecord right = new ComparatorRecord(new ComparatorIdentity(1L, ComparatorType.RIGHT), "{ \"code\": \"TEST2\", \"name\": \"Test Two\" }", LocalDateTime.now());
        entityManager.persist(right);

        ComparatorRecord foundLeft = repository.findOne(left.getId());
        ComparatorRecord foundRight = repository.findOne(right.getId());

        assertThat(foundLeft.getId()).isEqualTo(left.getId());
        assertThat(foundRight.getId()).isEqualTo(right.getId());
    }
}
