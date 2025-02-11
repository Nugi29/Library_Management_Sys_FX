package model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Book {

    private String id;

    private String name;

    private String isbn;

    private Boolean availability_status;

    private String category_id;

    private String author_id;

    private String publisher_id;

}
