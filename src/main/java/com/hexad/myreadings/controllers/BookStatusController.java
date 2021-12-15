package com.hexad.myreadings.controllers;

import com.hexad.myreadings.dto.BookStatusDTO;
import com.hexad.myreadings.exception.MyLearningsException;
import com.hexad.myreadings.model.BookStatus;
import com.hexad.myreadings.model.Member;
import com.hexad.myreadings.services.BookStatusService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
public class BookStatusController
{

    @Autowired
    BookStatusService bookStatusService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/add/{bookName}/{count}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public BookStatusDTO addBook(@PathVariable String bookName, @PathVariable int count)
    {
        return convertEntityToDTO(bookStatusService.addBook(bookName, count));
    }

    @GetMapping("/get/{bookName}")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public BookStatusDTO getBookStatus(@PathVariable String bookName) throws MyLearningsException
    {
        return convertEntityToDTO(bookStatusService.findByBookName(bookName));
    }

    @PostMapping("/borrow/{bookName}/{memberName}")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public BookStatusDTO borrowBook(@PathVariable String bookName, @PathVariable String memberName) throws MyLearningsException
    {
        return convertEntityToDTO(bookStatusService.borrowBookByName(bookName, memberName));
    }

    @PostMapping("/return/{bookName}/{memberName}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Set<BookStatusDTO> returnBook(@PathVariable String bookName, @PathVariable String memberName) throws MyLearningsException
    {
        final Set<String> books =  new HashSet<>();
        books.add(bookName);

        final Member member = bookStatusService.returnBooksToLibrary(books, memberName);

        return convertEntitySetToDTOSet(member.getBorrowings());
    }

    private BookStatusDTO convertEntityToDTO(BookStatus bookStatus)
    {
        return modelMapper.map(bookStatus, BookStatusDTO.class);
    }

    private Set<BookStatusDTO> convertEntitySetToDTOSet(Set<BookStatus> bookStatuses)
    {
        return bookStatuses.stream()
                .map(book -> modelMapper.map(book, BookStatusDTO.class))
                .collect(Collectors.toSet());
    }
}
