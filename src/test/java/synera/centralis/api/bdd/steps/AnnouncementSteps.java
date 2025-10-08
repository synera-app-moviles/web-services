package synera.centralis.api.bdd.steps;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;

/**
 * Step definitions para funcionalidades de anuncios
 * Incluye steps para US15, US16, US17, US23, US25
 */
public class AnnouncementSteps {

    // US15 - Publicación básica de anuncios
    @Dado("que soy un gerente autenticado")
    public void soyGerenteAutenticado() {
        // TODO: Implementar autenticación de gerente
        System.out.println("Step: Gerente autenticado");
    }

    @Cuando("publico un anuncio con título {string} y contenido {string}")
    public void publicoAnuncio(String titulo, String contenido) {
        // TODO: Implementar creación de anuncio
        System.out.println("Step: Publicando anuncio - " + titulo);
    }

    @Entonces("el anuncio debe ser creado exitosamente")
    public void anuncioCreado() {
        // TODO: Verificar creación del anuncio
        System.out.println("Step: Anuncio creado exitosamente");
    }

    @Y("debe aparecer en la lista de anuncios")
    public void apareceEnLista() {
        // TODO: Verificar que aparece en la lista
        System.out.println("Step: Anuncio aparece en lista");
    }

    @Cuando("intento publicar un anuncio sin título")
    public void intentoPublicarSinTitulo() {
        // TODO: Intentar crear anuncio sin título
        System.out.println("Step: Intentando publicar sin título");
    }

    @Entonces("debo recibir un error de validación")
    public void reciboErrorValidacion() {
        // TODO: Verificar error de validación
        System.out.println("Step: Error de validación recibido");
    }

    @Y("el anuncio no debe ser creado")
    public void anuncioNoCreado() {
        // TODO: Verificar que no se creó
        System.out.println("Step: Anuncio no creado");
    }

    // US16 - Priorización de anuncios
    @Y("existe un anuncio publicado")
    public void existeAnuncioPublicado() {
        // TODO: Crear anuncio de prueba
        System.out.println("Step: Anuncio de prueba creado");
    }

    @Cuando("marco el anuncio como prioritario")
    public void marcoComoPrioritario() {
        // TODO: Marcar como prioritario
        System.out.println("Step: Marcando como prioritario");
    }

    @Entonces("el anuncio debe aparecer al inicio de la lista")
    public void apareceAlInicio() {
        // TODO: Verificar posición en lista
        System.out.println("Step: Anuncio al inicio de lista");
    }

    @Y("debe tener una etiqueta de {string}")
    public void tieneEtiqueta(String etiqueta) {
        // TODO: Verificar etiqueta
        System.out.println("Step: Etiqueta '" + etiqueta + "' presente");
    }

    // US17 - Edición de anuncios
    @Y("existe un anuncio publicado con título {string}")
    public void existeAnuncioConTitulo(String titulo) {
        // TODO: Crear anuncio con título específico
        System.out.println("Step: Anuncio creado con título: " + titulo);
    }

    @Cuando("edito el anuncio cambiando el título a {string}")
    public void editoTitulo(String nuevoTitulo) {
        // TODO: Editar título del anuncio
        System.out.println("Step: Editando título a: " + nuevoTitulo);
    }

    @Y("cambio el contenido a {string}")
    public void cambioContenido(String nuevoContenido) {
        // TODO: Editar contenido del anuncio
        System.out.println("Step: Editando contenido a: " + nuevoContenido);
    }

    @Entonces("los cambios deben guardarse correctamente")
    public void cambiosGuardados() {
        // TODO: Verificar que los cambios se guardaron
        System.out.println("Step: Cambios guardados");
    }

    @Y("el anuncio debe mostrar la información actualizada")
    public void muestraInformacionActualizada() {
        // TODO: Verificar información actualizada
        System.out.println("Step: Información actualizada mostrada");
    }

    // US23 - Comentarios en anuncios
    @Dado("que soy un empleado autenticado")
    public void soyEmpleadoAutenticado() {
        // TODO: Implementar autenticación de empleado
        System.out.println("Step: Empleado autenticado");
    }

    @Cuando("agrego un comentario {string}")
    public void agregoComentario(String comentario) {
        // TODO: Agregar comentario al anuncio
        System.out.println("Step: Agregando comentario: " + comentario);
    }

    @Entonces("el comentario debe aparecer en el anuncio")
    public void comentarioApareceEnAnuncio() {
        // TODO: Verificar que el comentario aparece
        System.out.println("Step: Comentario aparece en anuncio");
    }

    @Y("debe mostrar mi nombre como autor")
    public void muestraMiNombre() {
        // TODO: Verificar nombre del autor
        System.out.println("Step: Nombre del autor mostrado");
    }

    // US25 - Visualizar imágenes
    @Y("existe un anuncio con una imagen adjunta")
    public void anuncioConImagen() {
        // TODO: Crear anuncio con imagen
        System.out.println("Step: Anuncio con imagen creado");
    }

    @Cuando("veo el anuncio")
    public void veoAnuncio() {
        // TODO: Visualizar anuncio
        System.out.println("Step: Visualizando anuncio");
    }

    @Entonces("debo poder visualizar la imagen correctamente")
    public void visualizoImagen() {
        // TODO: Verificar visualización de imagen
        System.out.println("Step: Imagen visualizada correctamente");
    }
}