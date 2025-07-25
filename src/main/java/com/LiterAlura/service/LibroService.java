package com.LiterAlura.service;

import com.LiterAlura.dto.LibroDTO;
import com.LiterAlura.model.*;
import com.LiterAlura.repository.AutorRepository;
import com.LiterAlura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorService autorService;

    @Autowired
    private ConsumoAPI consumoApi;

    @Autowired
    private ConvierteDatos conversor;

    private static final String GUTENDEX_URL_BASE = "https://gutendex.com/books/";

    public void buscarYGuardarLibro(String busqueda) {
        DatosLibro datosLibro = buscarLibroEnApi(busqueda);
        if (datosLibro == null) {
            System.out.println("No se encontraron libros.");
            return;
        }
        Autor autor = autorService.buscarOCrearAutor(datosLibro.autores().get(0));
        guardarLibroSiNoExiste(datosLibro, autor);
    }

    private DatosLibro buscarLibroEnApi(String busqueda) {
        String url = GUTENDEX_URL_BASE + "?search=" + busqueda.replace(" ", "+");
        String json = consumoApi.obtenerDatos(url);
        DatosCatalogoLibros catalogo = conversor.obtenerDatos(json, DatosCatalogoLibros.class);
        return catalogo.libros().isEmpty() ? null : catalogo.libros().get(0);
    }

    private void guardarLibroSiNoExiste(DatosLibro datosLibro, Autor autor) {
        if (libroRepository.existsById(datosLibro.id())) {
            System.out.println("⚠️  Ese libro ya está registrado en tu catálogo.");
        } else {
            Libro libro = new Libro();
            libro.setId(datosLibro.id());
            libro.setTitulo(datosLibro.titulo());
            libro.setIdioma(datosLibro.idiomas() != null && !datosLibro.idiomas().isEmpty() ? datosLibro.idiomas().get(0) : null);
            libro.setDescargas(datosLibro.descargas());
            libro.setAutor(autor);
            libroRepository.save(libro);
            System.out.println("\u001B[33m**********************************************\u001B[0m");
            System.out.println("📚 Libro registrado: ");
            System.out.println("\u001B[33m**********************************************\u001B[0m");
            System.out.println(toDTO(libro));
        }
    }

    private LibroDTO toDTO(Libro libro) {
        return new LibroDTO(
                libro.getId(),
                libro.getTitulo(),
                libro.getAutor() != null ? libro.getAutor().getNombre() : null,
                libro.getIdioma(),
                libro.getDescargas()
        );
    }

    public List<LibroDTO> listarTodos() {
        return libroRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<LibroDTO> listarPorIdioma(String idioma) {
        return libroRepository.findAll().stream()
                .filter(libro -> libro.getIdioma() != null && libro.getIdioma().equalsIgnoreCase(idioma))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void mostrarEstadisticasIdiomas() {
        long totalLibros = libroRepository.count();
        if (totalLibros == 0) {
            System.out.println("📚 No hay libros registrados en el catálogo todavía.");
            return;
        }
        long cantidadEs = libroRepository.countByIdiomaIgnoreCase("es");
        long cantidadEn = libroRepository.countByIdiomaIgnoreCase("en");
        System.out.println("\u001B[34m**********************************************\u001B[0m");
        System.out.printf("📚 Total de libros registrados: %d%n", totalLibros);
        System.out.println("\u001B[34m**********************************************\u001B[0m");
        System.out.println("📊 Estadísticas de libros por idioma en el catálogo:");
        System.out.printf(" - Libros en español (es): %d%n", cantidadEs);
        System.out.printf(" - Libros en inglés (en): %d%n", cantidadEn);
        System.out.println("\u001B[34m**********************************************\u001B[0m");
    }

    public void mostrarEstadisticasDescargas() {
        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("📚 No hay libros registrados, por lo tanto no hay estadísticas de descargas.");
            return;
        }

        DoubleSummaryStatistics stats = libros.stream()
                .mapToDouble(libro -> libro.getDescargas() != null ? libro.getDescargas() : 0)
                .summaryStatistics();

        System.out.println("\u001B[34m**********************************************\u001B[0m");
        System.out.println("📈 Estadísticas de descargas en el catálogo:");
        System.out.println("\u001B[34m**********************************************\u001B[0m");
        System.out.printf(" - Total descargas: %.0f%n", stats.getSum());
        System.out.printf(" - Promedio descargas: %.2f%n", stats.getAverage());
        System.out.printf(" - Máx descargas: %.0f%n", stats.getMax());
        System.out.printf(" - Mín descargas: %.0f%n", stats.getMin());
        System.out.println("\u001B[34m**********************************************\u001B[0m");
    }

    public void mostrarTop10MasDescragados(){
        List<Libro> top10 = libroRepository.findTop10ByOrderByDescargasDesc();
        if (top10.isEmpty()) {
            System.out.println("📚 No hay libros registrados para mostrar el ranking.");
            return;
        }

        System.out.println("\u001B[35m**********************************************\u001B[0m");
        System.out.println("🏆 Top 10 libros más descargados:");
        System.out.println("\u001B[35m**********************************************\u001B[0m");

        top10.stream()
                .map(this::toDTO)
                .forEach(System.out::println);

    }

}
