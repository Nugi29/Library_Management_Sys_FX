package model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Author {
    private String id;
    private String name;
    private String contact;

}
