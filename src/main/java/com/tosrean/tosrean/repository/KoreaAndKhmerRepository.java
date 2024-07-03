package com.tosrean.tosrean.repository;

import com.tosrean.tosrean.entity.KoreanAndKhmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KoreaAndKhmerRepository extends JpaRepository<KoreanAndKhmer, Long> {
}
