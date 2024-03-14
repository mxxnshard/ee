package web.dao.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserModel {
    @Id
    private String id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "second_name")
    private String secondName;
    private String email;
    private String pass;

    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    List<InsectModel> insects;
}
