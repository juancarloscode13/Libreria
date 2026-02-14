drop database if exists libreria;
create database libreria character set latin1 collate latin1_spanish_Ci;
use libreria;
-- tabla vendedor
create table administrador(
    id int unsigned primary key not null auto_increment,
    nombre varchar(40) not null,
    contraseña varchar(30) not null,
    permisos_admin boolean
);
-- tabla comprador
create table comprador(
    id int unsigned primary key not null auto_increment,
    nombre varchar(40) not null,
    contraseña varchar(30) not null,
    permisos_admin boolean
);
-- tabla libro
create table libro(
    id int unsigned primary key not null auto_increment,
    titulo varchar(40) not null,
    descripcion varchar(100) not null,
    precio int not null,
    id_vendedor int unsigned not null,
    constraint fk_libro_vendedor foreign key (id_vendedor) references administrador(id) on delete cascade on update cascade
);

-- inserts comprador
INSERT INTO comprador (nombre, contraseña, permisos_admin) VALUES
('comprador', 'comprador', false),
('María López', 'maria456', false),
('Pedro Martínez', 'pedro789', false),
('Ana Fernández', 'ana321', false),
('Luis Sánchez', 'luis654', false),
('Elena Ruiz', 'elena987', false),
('Javier Díaz', 'javier111', false),
('Lucía Torres', 'lucia222', false),
('Andrés Moreno', 'andres333', false),
('Sofía Jiménez', 'sofia444', false);

-- inserts administrador
INSERT INTO administrador (nombre, contraseña, permisos_admin) VALUES
('admin', 'admin', true),
('Laura Gómez', 'lauragomez1', true),
('Carlos Navarro', 'carlosnavarro2', true),
('Elena Romero', 'elenaromero3', true),
('Javier Vargas', 'javiervargas4', true),
('Lucía Castro', 'luciacastro5', true),
('Andrés Ortega', 'andresortega6', true),
('Marina Delgado', 'marinadelgado7', true),
('Sofía Herrera', 'sofiaherrera8', true),
('Pablo Medina', 'pablomedina9', true);

-- inserts libro
INSERT INTO libro (titulo, descripcion, precio, id_vendedor) VALUES
('El Quijote', 'Clásico de la literatura española de Cervantes', 15, 1),
('Cien Años de Soledad', 'Obra maestra de Gabriel García Márquez', 18, 1),
('La Sombra del Viento', 'Novela de Carlos Ruiz Zafón ambientada en Barcelona', 20, 1),
('El Principito', 'Cuento filosófico de Antoine de Saint-Exupéry', 12, 1),
('1984', 'Novela distópica de George Orwell', 14, 1),
('Rayuela', 'Novela experimental de Julio Cortázar', 16, 1),
('Don Juan Tenorio', 'Drama romántico de José Zorrilla', 10, 1),
('La Celestina', 'Tragicomedia de Fernando de Rojas', 11, 1),
('El Lazarillo de Tormes', 'Novela picaresca anónima del siglo XVI', 9, 1),
('Platero y Yo', 'Prosa poética de Juan Ramón Jiménez', 13, 1),

('La Casa de los Espíritus', 'Novela de Isabel Allende con realismo mágico', 17, 2),
('Ficciones', 'Cuentos fantásticos de Jorge Luis Borges', 15, 2),
('Pedro Páramo', 'Novela de Juan Rulfo sobre un pueblo fantasma', 14, 2),
('Como Agua para Chocolate', 'Novela de Laura Esquivel con recetas mexicanas', 16, 2),
('El Aleph', 'Colección de cuentos de Borges', 13, 2),
('La Colmena', 'Novela de Camilo José Cela sobre posguerra', 12, 2),
('Nada', 'Novela de Carmen Laforet sobre Barcelona', 11, 2),
('Los Santos Inocentes', 'Obra de Miguel Delibes sobre la España rural', 10, 2),
('El Camino', 'Novela de Miguel Delibes sobre la infancia', 9, 2),
('San Manuel Bueno, Mártir', 'Nivola de Miguel de Unamuno', 8, 2),

('El Amor en los Tiempos del Cólera', 'Historia de amor de García Márquez', 19, 3),
('La Ciudad y los Perros', 'Primera novela de Mario Vargas Llosa', 16, 3),
('Conversación en La Catedral', 'Novela política de Vargas Llosa', 18, 3),
('Los Detectives Salvajes', 'Novela de Roberto Bolaño sobre poetas', 20, 3),
('2666', 'Obra póstuma monumental de Roberto Bolaño', 25, 3),
('Crónica de una Muerte Anunciada', 'Novela corta de García Márquez', 12, 3),
('El Túnel', 'Novela existencialista de Ernesto Sábato', 11, 3),
('Sobre Héroes y Tumbas', 'Novela compleja de Ernesto Sábato', 15, 3),
('La Tregua', 'Novela de Mario Benedetti sobre la rutina', 10, 3),
('El Astillero', 'Novela de Juan Carlos Onetti', 13, 3),

('El Señor de los Anillos', 'Épica fantasía de J.R.R. Tolkien', 22, 4),
('Harry Potter y la Piedra Filosofal', 'Primera entrega de la saga de J.K. Rowling', 18, 4),
('El Hobbit', 'Aventura fantástica de Tolkien', 15, 4),
('Juego de Tronos', 'Primera novela de Canción de Hielo y Fuego', 20, 4),
('El Nombre del Viento', 'Fantasía épica de Patrick Rothfuss', 19, 4),
('Dune', 'Ciencia ficción clásica de Frank Herbert', 17, 4),
('Fahrenheit 451', 'Distopía de Ray Bradbury sobre libros prohibidos', 13, 4),
('Un Mundo Feliz', 'Distopía de Aldous Huxley', 14, 4),
('Fundación', 'Ciencia ficción de Isaac Asimov', 16, 4),
('Neuromante', 'Novela cyberpunk de William Gibson', 15, 4),

('Crimen y Castigo', 'Novela psicológica de Dostoyevski', 16, 5),
('Guerra y Paz', 'Épica histórica de León Tolstói', 24, 5),
('Anna Karénina', 'Drama social de Tolstói', 20, 5),
('Los Hermanos Karamázov', 'Última novela de Dostoyevski', 22, 5),
('El Idiota', 'Novela de Dostoyevski sobre la bondad', 18, 5),
('Madame Bovary', 'Novela realista de Gustave Flaubert', 15, 5),
('Los Miserables', 'Épica social de Victor Hugo', 23, 5),
('El Conde de Montecristo', 'Aventura de Alejandro Dumas', 19, 5),
('Orgullo y Prejuicio', 'Romance clásico de Jane Austen', 14, 5),
('Jane Eyre', 'Novela gótica de Charlotte Brontë', 15, 5),

('El Gran Gatsby', 'Novela de la era del jazz de Fitzgerald', 14, 6),
('Matar a un Ruiseñor', 'Novela sobre justicia de Harper Lee', 16, 6),
('En el Camino', 'Novela beat de Jack Kerouac', 13, 6),
('El Viejo y el Mar', 'Novela corta de Ernest Hemingway', 11, 6),
('Las Uvas de la Ira', 'Drama social de John Steinbeck', 17, 6),
('Moby Dick', 'Aventura marítima de Herman Melville', 18, 6),
('El Guardián entre el Centeno', 'Novela juvenil de J.D. Salinger', 12, 6),
('Beloved', 'Novela de Toni Morrison sobre la esclavitud', 15, 6),
('La Campana de Cristal', 'Novela autobiográfica de Sylvia Plath', 14, 6),
('Matadero Cinco', 'Sátira antibelicista de Kurt Vonnegut', 13, 6),

('El Perfume', 'Novela sensorial de Patrick Süskind', 16, 7),
('La Metamorfosis', 'Relato kafkiano sobre la transformación', 10, 7),
('El Proceso', 'Novela absurda de Franz Kafka', 14, 7),
('Siddhartha', 'Novela espiritual de Hermann Hesse', 12, 7),
('El Lobo Estepario', 'Novela existencial de Hesse', 13, 7),
('El Tambor de Hojalata', 'Novela de Günter Grass', 17, 7),
('Sin Destino', 'Novela autobiográfica de Imre Kertész', 15, 7),
('El Lector', 'Novela de Bernhard Schlink sobre la culpa', 14, 7),
('Demian', 'Novela de formación de Hermann Hesse', 11, 7),
('Las Tribulaciones del Joven Törless', 'Novela de Robert Musil', 12, 7),

('La Divina Comedia', 'Poema épico de Dante Alighieri', 20, 8),
('Decamerón', 'Colección de relatos de Boccaccio', 18, 8),
('El Nombre de la Rosa', 'Misterio medieval de Umberto Eco', 17, 8),
('Si una Noche de Invierno', 'Novela experimental de Italo Calvino', 15, 8),
('Las Ciudades Invisibles', 'Prosa poética de Italo Calvino', 14, 8),
('El Gatopardo', 'Novela histórica de Lampedusa', 16, 8),
('El Desierto de los Tártaros', 'Novela existencial de Dino Buzzati', 13, 8),
('La Conciencia de Zeno', 'Novela de Italo Svevo', 12, 8),
('Seis Personajes Buscan Autor', 'Obra teatral de Luigi Pirandello', 11, 8),
('El Barón Rampante', 'Novela fantástica de Calvino', 14, 8),

('El Extranjero', 'Novela existencialista de Albert Camus', 13, 9),
('La Peste', 'Novela alegórica de Albert Camus', 15, 9),
('Los Tres Mosqueteros', 'Aventura clásica de Alejandro Dumas', 16, 9),
('Veinte Mil Leguas de Viaje', 'Ciencia ficción de Julio Verne', 14, 9),
('La Vuelta al Mundo en 80 Días', 'Aventura de Julio Verne', 13, 9),
('El Retrato de Dorian Gray', 'Novela gótica de Oscar Wilde', 15, 9),
('Germinal', 'Novela social de Émile Zola', 12, 9),
('Las Flores del Mal', 'Poemario de Charles Baudelaire', 10, 9),
('En Busca del Tiempo Perdido', 'Obra magna de Marcel Proust', 25, 9),
('El Principito (edición lujo)', 'Edición especial ilustrada de Saint-Exupéry', 30, 9),

('Sapiens', 'Historia de la humanidad de Yuval Noah Harari', 22, 10),
('El Arte de la Guerra', 'Tratado militar clásico de Sun Tzu', 10, 10),
('Breves Respuestas a las Grandes', 'Divulgación científica de Stephen Hawking', 18, 10),
('El Gen Egoísta', 'Biología evolutiva de Richard Dawkins', 17, 10),
('Cosmos', 'Divulgación de Carl Sagan sobre el universo', 16, 10),
('Una Breve Historia del Tiempo', 'Física para todos de Stephen Hawking', 19, 10),
('El Poder del Ahora', 'Libro de espiritualidad de Eckhart Tolle', 14, 10),
('Pensar Rápido, Pensar Despacio', 'Psicología de Daniel Kahneman', 20, 10),
('Los Pilares de la Tierra', 'Novela histórica de Ken Follett', 21, 10),
('El Código Da Vinci', 'Thriller de Dan Brown', 15, 10);