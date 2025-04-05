package model.reportModels;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AvailableBooks {

    private String id;

    private String name;

    private String isbn;

    private String category;

    private String author;

    private String publisher;

}
