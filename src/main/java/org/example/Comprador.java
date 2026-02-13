package org.example;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity
@Table(name = "comprador")
public class Comprador {

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
}
