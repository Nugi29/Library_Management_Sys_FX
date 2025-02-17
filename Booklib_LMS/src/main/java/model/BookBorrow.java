package model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class BookBorrow {

    private String id;

    private Book book;

    private Member member;

    private String borrowDate;

    private Boolean isReturned;

    private String returnDate;

    private String returnedDate;


}
