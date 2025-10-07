# language: es
Característica: Visualizar imágenes en anuncios
  Como empleado
  Quiero poder ver las imágenes adjuntas en los anuncios
  Para comprender mejor la información publicada

  Escenario: Ver anuncio con imagen adjunta
    Dado que soy un empleado autenticado
    Y existe un anuncio con una imagen adjunta
    Cuando veo el anuncio
    Entonces debo poder visualizar la imagen correctamente
    Y la imagen debe cargarse en el tamaño apropiado

  Escenario: Ver anuncio sin imágenes
    Dado que soy un empleado autenticado
    Y existe un anuncio sin imágenes adjuntas
    Cuando veo el anuncio
    Entonces solo debo ver el texto del anuncio
    Y no debe aparecer ningún placeholder de imagen