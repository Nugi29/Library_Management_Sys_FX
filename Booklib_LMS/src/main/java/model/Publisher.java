package model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Publisher {

    private Integer id;

    private String name;

    private String location;

    private String contact;

}
