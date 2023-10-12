package deconvoluter.wavfprocessor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WaveFormRepository extends CrudRepository<WaveForm, Long>, PagingAndSortingRepository<WaveForm, Long> {
}
