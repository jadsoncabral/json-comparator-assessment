package br.jad.comparator.domain;

import br.jad.comparator.commons.ComparatorType;

/**
 * Comparator Service interface
 * @see ComparatorServiceImpl for implementation
 */
public interface ComparatorService {

    ComparatorRecord save(Long id, ComparatorType type, String content);

    ComparatorRecord findByIdAndType(Long id, ComparatorType type);

    String getDifference(Long id);
}
