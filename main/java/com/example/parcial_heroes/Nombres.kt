package com.example.parcial_heroes

data class Nombres(
    val id: Int,
    val nombre: String,
    val imagenUrl: String,
    val descripcion: String,
    val superPoderes: List<String>,
    val nemesis: Nombres? = null // aca guardo el campo para el villano principal (en este caso quize que sean los nemesis de cada heroe)
)

val listaNombres = listOf(
    Nombres(
        1,
        "Superman",
        "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/lg/644-superman.jpg",
        "Superman es un heroe de Superman Prime One-Million reconocido por ACTION COMICS #1.",
        listOf("Inteligencia superior", "Super fuerza", "Super velocidad", "Alta resistencia"),
        nemesis = Nombres(
            id = 4,
            nombre = "Lex Luthor",
            imagenUrl = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/lg/405-lex-luthor.jpg",
            descripcion = "Archienemigo de Superman, brillante estratega con tecnología avanzada.",
            superPoderes = listOf("Inteligencia de genio", "Traje de combate avanzado")
        )
    ),
    Nombres(
        id = 2,
        nombre = "Batman",
        imagenUrl = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/lg/70-batman.jpg",
        descripcion = "Batman es el protector de Gotham City, un héroe sin superpoderes sobrehumanos reconocido por Detective Comics #27.",
        superPoderes = listOf("Inteligencia de genio", "Artes marciales", "Maestro detective", "Tecnología avanzada"),
        nemesis = Nombres(
            id = 5,
            nombre = "El Joker",
            imagenUrl = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/lg/370-joker.jpg",
            descripcion = "Némesis psicópata de Batman, conocido por su caos impredecible.",
            superPoderes = listOf("Inmunidad a toxinas", "Inteligencia criminal caótica")
        )
    ),
    Nombres(
        id = 3,
        nombre = "Spider-Man",
        imagenUrl = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/lg/620-spider-man.jpg",
        descripcion = "Spider-Man es el héroe arácnido de Nueva York, introducido por primera vez en Amazing Fantasy #15.",
        superPoderes = listOf("Sentido arácnido", "Agilidad sobrehumana", "Super fuerza", "Trepar paredes"),
        nemesis = Nombres(
            id = 6,
            nombre = "Duende Verde",
            imagenUrl = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/lg/280-green-goblin.jpg",
            descripcion = "Norman Osborn, archienemigo de Spider-Man con fuerza y planeador aerodinámico.",
            superPoderes = listOf("Super fuerza", "Planeador aerodinámico")
        )
    ),
)
