package com.hexad.myreadings.constants;

public class Constants
{

    public static final String BOOK_ID = "bookId";
    public static final String BOOK_NAME = "name";

    public static final String MEMBER_ID = "member_id";
    public static final String MEMBER_NAME = "member_name";

    // Messages

    public static final String ERR_CODE_ADD_BOOK = "1001";
    public static final String ERR_MESSAGE_ADD_BOOK = "Unable to add book status";

    public static final String ERR_CODE_BOOK_NOT_EXISTS = "1002";
    public static final String ERR_MESSAGE_BOOK_NOT_EXISTS = "Book does not exists";

    public static final String ERR_CODE_ALREADY_BORROWED = "1003";
    public static final String ERR_MESSAGE_ALREADY_BORROWED = "Member has already borrowed same book";

    public static final String ERR_CODE_MEMBER_NOT_FOUND = "1101";
    public static final String ERR_MESSAGE_MEMBER_NOT_FOUND = "Member not found";
}
