package com.cinema.repository;

import com.cinema.model.entity.BookingDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetail, Long> {
    Page<BookingDetail> findAllByNameContainingIgnoreCase (String name, Pageable pageable) ;
    Long countByStatus(boolean status);
}
