# language: es
Característica: Enviar imágenes en chats grupales
  Como empleado
  Quiero poder enviar imágenes en los chats grupales
  Para compartir información visual con mi equipo

  Escenario: Enviar imagen en chat grupal
    Dado que soy un empleado autenticado
    Y estoy en un chat grupal "Equipo de Diseño"
    Cuando envío una imagen con el mensaje "¿Qué opinan de este diseño?"
    Entonces la imagen debe aparecer en el chat
    Y todos los miembros deben poder verla

  Escenario: Enviar imagen de formato no soportado
    Dado que soy un empleado autenticado
    Y estoy en un chat grupal
    Cuando intento enviar una imagen en formato no soportado
    Entonces debo recibir un mensaje de error
    Y la imagen no debe ser enviada