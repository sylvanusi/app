package com.more.app.repository.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.more.app.entity.product.Party;

public interface PartyRepository extends JpaRepository<Party, Long> {
	@Query("""
			SELECT p FROM Party p
			WHERE lower(p.name) LIKE lower(concat('%', :bicCode, '%'))
			   OR lower(p.bicCode) LIKE lower(concat('%', :name, '%'))
			   OR lower(p.account) LIKE lower(concat('%', :account, '%'))
			""")
	List<Party> search(String bicCode, String name, String account);
	
	List<Party> findByBicCodeStartsWithAndNameStartsWithAndAccountStartsWith(String bicCode, String name, String account);
	
	Optional<Party> findByBicCode(String bicCode);

    Optional<Party> findByIdentifier(String identifier);

    boolean existsByBicCode(String bicCode);

    boolean existsByIdentifier(String identifier);
}
