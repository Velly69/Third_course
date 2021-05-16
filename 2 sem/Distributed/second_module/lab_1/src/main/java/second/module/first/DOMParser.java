package second.module.first;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import second.module.first.models.Genre;
import second.module.first.models.Movie;
import second.module.first.models.VideoShop;

public class DOMParser {
    public static class SimpleErrorHandler implements ErrorHandler {
        public void warning(SAXParseException e) throws SAXException {
            System.out.println("Line " +e.getLineNumber() + ":");
            System.out.println(e.getMessage());
        }
        public void error(SAXParseException e) throws SAXException {
            System.out.println("Line " +e.getLineNumber() + ":");
            System.out.println(e.getMessage());
        }
        public void fatalError(SAXParseException e) throws SAXException {
            System.out.println("Line " +e.getLineNumber() + ":");
            System.out.println(e.getMessage());
        }
    }

    public static VideoShop parse(String path) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new SimpleErrorHandler());
        Document doc = builder.parse(new File(path));
        doc.getDocumentElement().normalize();

        VideoShop shop = new VideoShop();
        NodeList nodes = doc.getElementsByTagName("Genre");

        for(int i = 0; i < nodes.getLength(); ++i) {
            Element n = (Element)nodes.item(i);
            Genre genre = new Genre();
            genre.setGenreId(n.getAttribute("id"));
            genre.setName(n.getAttribute("name"));
            shop.addGenre(genre);

        }

        nodes = doc.getElementsByTagName("Movie");
        for(int j =0; j < nodes.getLength(); ++j) {
            Element e = (Element) nodes.item(j);
            Movie movie = new Movie();
            movie.setMovieId(e.getAttribute("id"));
            movie.setGenreId(e.getAttribute("genreId"));
            movie.setName(e.getAttribute("name"));
            movie.setReleaseYear(Integer.parseInt(e.getAttribute("releaseYear")));
            shop.addMovie(movie);
        }

        return shop;
    }

    public static void write(VideoShop shop, String path) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        Element root = doc.createElement("Shop");
        doc.appendChild(root);

        Map<String, Genre> genres = shop.getGenres();
        for(Map.Entry<String, Genre> entry : genres.entrySet()) {
            Element gnr = doc.createElement("Genre");
            gnr.setAttribute("id", entry.getValue().getGenreId());
            gnr.setAttribute("name", entry.getValue().getName());
            root.appendChild(gnr);

            for(Movie movie: entry.getValue().getMovies()) {
                Element mv = doc.createElement("Movie");
                mv.setAttribute("id", movie.getMovieId());
                mv.setAttribute("genreId", movie.getGenreId());
                mv.setAttribute("name", movie.getName());
                mv.setAttribute("releaseYear", String.valueOf(movie.getReleaseYear()));
                gnr.appendChild(mv);
            }
        }
        Source domSource = new DOMSource(doc);
        Result fileResult = new StreamResult(new File(path));
        TransformerFactory tfactory = TransformerFactory.newInstance();
        Transformer transformer = tfactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "shop.dtd");
        transformer.transform(domSource, fileResult);
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        VideoShop shop = parse("src/main/java/second/module/first/shop.xml");
        write(shop,"src/main/java/second/module/first/shop.xml");
    }
}