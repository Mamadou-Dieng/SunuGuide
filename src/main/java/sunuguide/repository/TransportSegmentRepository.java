package sunuguide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sunuguide.model.TransportSegment;

@Repository
public interface TransportSegmentRepository extends JpaRepository<TransportSegment, Long> {
}
