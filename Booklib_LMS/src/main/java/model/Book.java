package model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Book {

    private Integer id;

    private String name;

    private String isbn;

    private Boolean availability_status;

    private Publisher publisher;


}
