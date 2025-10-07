# language: es
Característica: Comentarios en anuncios
  Como empleado
  Quiero dar feedback sobre anuncios
  Para aclarar dudas o hacer comentarios

  Escenario: Agregar comentario a un anuncio
    Dado que soy un empleado autenticado
    Y existe un anuncio publicado
    Cuando agrego un comentario "¿A qué hora es la reunión?"
    Entonces el comentario debe aparecer en el anuncio
    Y debe mostrar mi nombre como autor

  Escenario: Ver comentarios de otros empleados
    Dado que soy un empleado autenticado
    Y existe un anuncio con comentarios de otros empleados
    Cuando veo el anuncio
    Entonces debo poder ver todos los comentarios
    Y debe mostrar el nombre del autor de cada comentario