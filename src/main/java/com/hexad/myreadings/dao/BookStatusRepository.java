package com.hexad.myreadings.dao;

import com.hexad.myreadings.model.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface BookStatusRepository extends JpaRepository<BookStatus, Integer>
{
    @Query("select b from BookStatus b where b.bookStatusId = ?1")
    BookStatus findByBookStatusId(@NonNull int bookStatusId);

    @Query("select b from BookStatus b where upper(b.bookName) = upper(?1)")
    Optional<BookStatus> findByBookName(@NonNull String bookName);

    @Query("select b.availableCount from BookStatus b where upper(b.bookName) = upper(?1)")
    int countByBookName(@NonNull String bookName);

    /*@Query( "select b from BookStatus b where upper(b.bookName) in :upper(?1)" )
    List<BookStatus> findAllByBookName(@NonNull String bookName);*/


    @Query("select b.availableCount from BookStatus b where b.bookStatusId = ?1")
    int countByBookStatusId(int bookStatusId);
}