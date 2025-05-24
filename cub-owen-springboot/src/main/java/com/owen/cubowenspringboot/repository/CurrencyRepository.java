package com.owen.cubowenspringboot.repository;

import com.owen.cubowenspringboot.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, String> {
}
