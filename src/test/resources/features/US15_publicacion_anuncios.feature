# language: es
Característica: Publicación básica de anuncios
  Como gerente
  Quiero publicar anuncios en la aplicación móvil
  Para que los empleados estén informados de las novedades de la empresa

  Escenario: Publicar un anuncio básico exitosamente
    Dado que soy un gerente autenticado
    Cuando publico un anuncio con título "Reunión mensual" y contenido "Reunión mensual este viernes"
    Entonces el anuncio debe ser creado exitosamente
    Y debe aparecer en la lista de anuncios

  Escenario: Intentar publicar anuncio sin título
    Dado que soy un gerente autenticado
    Cuando intento publicar un anuncio sin título
    Entonces debo recibir un error de validación
    Y el anuncio no debe ser creado