# language: es
Característica: Envío de mensajes
  Como empleado
  Quiero enviar mensajes
  Para mantenerme comunicado con mi equipo

  Escenario: Enviar mensaje en chat grupal
    Dado que soy un empleado autenticado
    Y pertenezco a un chat grupal "Equipo de Desarrollo"
    Cuando envío el mensaje "¿Alguien puede revisar el código?"
    Entonces el mensaje debe aparecer en el chat
    Y todos los miembros deben poder verlo

  Escenario: Enviar mensaje vacío
    Dado que soy un empleado autenticado
    Y estoy en un chat grupal
    Cuando intento enviar un mensaje vacío
    Entonces el mensaje no debe ser enviado
    Y debo ver una indicación de error