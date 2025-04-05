package model.reportModels;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BorrowedBooks {

    private String borrow_id;

    private String book_id;

    private String book_name;

    private String member_name;

    private String contact;

    private String borrowed_date;

    private String isReturned;

}
