package synera.centralis.api.bdd.steps;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;

/**
 * Step definitions para funcionalidades de eventos
 * Incluye steps para US26
 */
public class EventSteps {

    // US26 - Creación básica de eventos
    @Cuando("creo un evento con título {string} y fecha {string}")
    public void creoEvento(String titulo, String fecha) {
        // TODO: Implementar creación de evento
        System.out.println("Step: Creando evento: " + titulo + " para " + fecha);
    }

    @Y("establezco la hora como {string}")
    public void establecoHora(String hora) {
        // TODO: Establecer hora del evento
        System.out.println("Step: Estableciendo hora: " + hora);
    }

    @Entonces("el evento debe ser creado exitosamente")
    public void eventoCreado() {
        // TODO: Verificar creación del evento
        System.out.println("Step: Evento creado exitosamente");
    }

    @Y("debe aparecer en el calendario de eventos")
    public void apareceEnCalendario() {
        // TODO: Verificar aparición en calendario
        System.out.println("Step: Evento aparece en calendario");
    }

    @Cuando("intento crear un evento sin especificar fecha")
    public void intentoCrearSinFecha() {
        // TODO: Intentar crear evento sin fecha
        System.out.println("Step: Intentando crear evento sin fecha");
    }

    @Y("el evento no debe ser creado")
    public void eventoNoCreado() {
        // TODO: Verificar que no se creó
        System.out.println("Step: Evento no creado");
    }
}