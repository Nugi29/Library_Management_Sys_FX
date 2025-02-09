package model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Publisher {

    private String id;

    private String name;

    private String location;

    private String contact;

}
