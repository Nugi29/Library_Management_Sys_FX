package model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;


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
