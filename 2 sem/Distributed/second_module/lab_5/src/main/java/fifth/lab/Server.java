package fifth.lab;

import fifth.lab.dao.GenreDAO;
import fifth.lab.dao.MovieDAO;
import fifth.lab.dto.GenreDTO;
import fifth.lab.dto.MovieDTO;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.List;

public class Server {
    private Connection connection;
    private Session session;
    private MessageProducer producer;
    private MessageConsumer consumer;

    private static final String SEPARATOR = "#";

    public void start() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        try {
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination queueTo = session.createQueue("toClient");
            Destination queueFrom = session.createQueue("fromClient");

            producer = session.createProducer(queueTo);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            consumer = session.createConsumer(queueFrom);

            while (processQuery()) {

            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private boolean processQuery() {
        String response = "";
        String query = "";
        try {
            Message request = consumer.receive(500);
            if (request == null) {
                return true;
            }

            if (request instanceof TextMessage) {
                TextMessage message = (TextMessage) request;
                query = message.getText();
            } else { return true; }

            String [] fields = query.split(SEPARATOR);
            if (fields.length == 0) {
                return true;
            } else {
                String action = fields[0];
                GenreDTO genreDTO;
                MovieDTO movieDTO;

                switch (action) {
                    case "GenreFindById":
                        Long id = Long.parseLong(fields[1]);
                        genreDTO = GenreDAO.findById(id);
                        response = genreDTO.getName();
                        TextMessage message = session.createTextMessage(response);
                        producer.send(message);
                        break;
                    case "MovieFindByGenreId":
                        id = Long.parseLong(fields[1]);
                        List<MovieDTO> list = MovieDAO.findByGenreId(id);
                        StringBuilder str = new StringBuilder();
                        assert list != null;
                        moviesToString(str, list);
                        response = str.toString();
                        message = session.createTextMessage(response);
                        producer.send(message);
                        break;
                    case "MovieFindByName":
                        String name = fields[1];
                        movieDTO = MovieDAO.findByName(name);
                        assert movieDTO != null;
                        response = movieDTO.getMovieId() + SEPARATOR + movieDTO.getName() + SEPARATOR + movieDTO.getReleaseYear() + SEPARATOR + movieDTO.getGenreId();
                        message = session.createTextMessage(response);
                        producer.send(message);
                        break;
                    case "GenreFindByName":
                        name = fields[1];
                        genreDTO = GenreDAO.findByName(name);
                        assert genreDTO != null;
                        response = genreDTO.getId() + "";
                        message = session.createTextMessage(response);
                        producer.send(message);
                        break;
                    case "MovieUpdate":
                        id = Long.parseLong(fields[1]);
                        name = fields[2];
                        Integer year = Integer.parseInt(fields[3]);
                        Long genreId = Long.parseLong(fields[4]);
                        movieDTO = new MovieDTO(id, name, year, genreId);
                        response = MovieDAO.update(movieDTO) ? "true" : "false";
                        System.out.println(response);
                        message = session.createTextMessage(response);
                        producer.send(message);
                        break;
                    case "GenreUpdate":
                        id = Long.parseLong(fields[1]);
                        name = fields[2];
                        genreDTO = new GenreDTO(id, name);
                        response = GenreDAO.update(genreDTO) ? "true" : "false";
                        message = session.createTextMessage(response);
                        producer.send(message);
                        break;
                    case "MovieInsert":
                        name = fields[1];
                        year = Integer.parseInt(fields[2]);
                        genreId = Long.parseLong(fields[3]);
                        movieDTO = new MovieDTO((long) 0, name, year, genreId);
                        response = MovieDAO.insert(movieDTO) ? "true" : "false";
                        message = session.createTextMessage(response);
                        producer.send(message);
                        break;
                    case "GenreInsert":
                        name = fields[1];
                        genreDTO = new GenreDTO();
                        genreDTO.setName(name);
                        response = GenreDAO.insert(genreDTO) ? "true" : "false";
                        message = session.createTextMessage(response);
                        producer.send(message);
                        break;
                    case "MovieDelete":
                        id = Long.parseLong(fields[1]);
                        movieDTO = new MovieDTO();
                        movieDTO.setMovieId(id);
                        response = MovieDAO.delete(movieDTO) ? "true" : "false";
                        message = session.createTextMessage(response);
                        producer.send(message);
                        break;
                    case "GenreDelete":
                        id = Long.parseLong(fields[1]);
                        genreDTO = new GenreDTO();
                        genreDTO.setId(id);
                        response = GenreDAO.delete(genreDTO) ? "true" : "false";
                        message = session.createTextMessage(response);
                        producer.send(message);
                        break;
                    case "MovieAll":
                        List<MovieDTO> moviesList = MovieDAO.findAll();
                        str = new StringBuilder();
                        assert moviesList != null;
                        moviesToString(str, moviesList);
                        response = str.toString();
                        message = session.createTextMessage(response);
                        producer.send(message);
                        break;
                    case "GenreAll":
                        List<GenreDTO> genresList = GenreDAO.findAll();
                        str = new StringBuilder();
                        assert genresList != null;
                        for (GenreDTO genre : genresList) {
                            str.append(genre.getId());
                            str.append(SEPARATOR);
                            str.append(genre.getName());
                            str.append(SEPARATOR);
                        }
                        response = str.toString();
                        message = session.createTextMessage(response);
                        producer.send(message);
                        break;
                }
            }
            return true;
        } catch (JMSException ex) {
            return false;
        }
    }

    private void moviesToString(StringBuilder str, List<MovieDTO> list) {
        for (MovieDTO movie : list) {
            str.append(movie.getMovieId());
            str.append(SEPARATOR);
            str.append(movie.getName());
            str.append(SEPARATOR);
            str.append(movie.getReleaseYear());
            str.append(SEPARATOR);
            str.append(movie.getGenreId());
            str.append(SEPARATOR);
        }
    }

    public void disconnect() {
        try {
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
