package web.dao.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "insects")
public class InsectModel {
    @Id
    String id;

    @Column(name = "user_id")
    String userId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @ToString.Exclude
    UserModel user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "insects2types",
            joinColumns = @JoinColumn(name = "insect_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id"))
    @EqualsAndHashCode.Exclude
    Set<TypeModel> types;

    String name;
    int size;
}
