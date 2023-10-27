package deconvoluter.wavfprocessor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface WaveFormRepository extends CrudRepository<WaveForm, Long>, PagingAndSortingRepository<WaveForm, Long> {
  //TODO implement custom DFT matching algorithm
  //TODO move to PostgreSQL + MySQL
  WaveForm findByIdAndOwner(Long id, String owner);
  Page<WaveForm> findByOwner(String owner, PageRequest amount);
}
