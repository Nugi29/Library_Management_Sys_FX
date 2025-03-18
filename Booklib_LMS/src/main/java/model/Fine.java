package model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Fine {
    private Integer id;
    private Double amount;
    private String date_count;
    private String bookBorrowId;
    private Integer adminId;
}