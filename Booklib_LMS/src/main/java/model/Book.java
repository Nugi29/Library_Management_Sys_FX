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

    private String publisher;

    private String author;

    private String category;



}
