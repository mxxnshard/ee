    package web.dao.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "types")
public class TypeModel {
    @Id
    String id;
    String name;

    @ManyToMany(mappedBy = "types")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Set<InsectModel> insects;
}
