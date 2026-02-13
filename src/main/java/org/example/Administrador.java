package org.example;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity
@Table(name = "administrador")
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(name = "nombre")
    private String nombre;

    @NonNull
    @Column(name = "contraseña")
    private String contraseña;

    @NonNull
    @Column(name = "permisos_admin")
    private Boolean permisosAdmin;

    @ToString.Exclude
    @OneToMany(mappedBy = "administrador", cascade = CascadeType.ALL)
    private List<Libro> libros;
}
