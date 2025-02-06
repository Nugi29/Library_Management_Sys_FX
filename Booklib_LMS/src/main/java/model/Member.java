package model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member {
    private String id;
    private String name;
    private String address;
    private String email;
    private String contact;


}
