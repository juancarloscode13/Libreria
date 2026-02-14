package org.example.modelo;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity
@Table(name = "libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "titulo")
    private String titulo;

    @NonNull
    @Column(name = "descripcion")
    private String descripcion;

    @NonNull
    @Column(name = "precio")
    private double precio;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_vendedor")
    private Administrador administrador;
}
