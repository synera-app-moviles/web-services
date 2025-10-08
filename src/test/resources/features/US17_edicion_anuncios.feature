# language: es
Característica: Edición de anuncios
  Como gerente
  Quiero editar anuncios ya publicados
  Para corregir errores o actualizar información

  Escenario: Editar título y contenido de un anuncio
    Dado que soy un gerente autenticado
    Y existe un anuncio publicado con título "Título original"
    Cuando edito el anuncio cambiando el título a "Título actualizado"
    Y cambio el contenido a "Contenido actualizado"
    Entonces los cambios deben guardarse correctamente
    Y el anuncio debe mostrar la información actualizada

  Escenario: Intentar editar anuncio inexistente
    Dado que soy un gerente autenticado
    Cuando intento editar un anuncio que no existe
    Entonces debo recibir un error de "Anuncio no encontrado"