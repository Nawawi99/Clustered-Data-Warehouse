package dev.awn.datawarehouse.core.deal.repository;

import dev.awn.datawarehouse.core.deal.document.Deal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DealRepository extends MongoRepository<Deal, String> {
}
