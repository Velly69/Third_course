package fifth.lab;

import fifth.lab.dto.GenreDTO;
import fifth.lab.dto.MovieDTO;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private Connection connection;
    private Session session;
    private MessageProducer producer;
    private MessageConsumer consumer;
    private static final String separator = "#";

    public Client() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        try {
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination queueOut = session.createQueue("fromClient");
            Destination queueIn = session.createQueue("toClient");

            producer = session.createProducer(queueOut);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            consumer = session.createConsumer(queueIn);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private String handleMessage(String query, int timeout) throws JMSException {
        TextMessage message = session.createTextMessage(query);
        producer.send(message);
        Message mes = consumer.receive(timeout);
        if (mes == null) {
            return null;
        }

        if (mes instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) mes;
            return textMessage.getText();
        }

        return "";
    }

    public GenreDTO genreFindById(Long id) {
        String query = "GenreFindById" + separator + id.toString();
        try {
            String response = handleMessage(query, 15000);
            return new GenreDTO(id, response);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }

    public MovieDTO movieFindByName(String name) {
        String query = "MovieFindByName" + separator + name;
        try {
            String response = handleMessage(query, 15000);
            String[] fields = response.split(separator);
            Long id = Long.parseLong(fields[0]);
            Integer year = Integer.parseInt(fields[2]);
            Long genreId = Long.parseLong(fields[3]);
            return new MovieDTO(id, name, year, genreId);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GenreDTO genreFindByName(String name) {
        String query = "GenreFindByName" + separator + name;
        try {
            String response = handleMessage(query, 15000);
            Long responseId = Long.parseLong(response);
            return new GenreDTO(responseId, name);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean movieUpdate(MovieDTO movie) {
        String query = "MovieUpdate" + separator + movie.getMovieId() +
                separator + movie.getName() + separator + movie.getReleaseYear()
                + separator + movie.getGenreId();
        try {
            String response = handleMessage(query, 15000);
            return "true".equals(response);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean genreUpdate(GenreDTO genre) {
        String query = "GenreUpdate" + separator + genre.getId() +
                separator + genre.getName();
        try {
            String response = handleMessage(query, 15000);
            return "true".equals(response);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean movieInsert(MovieDTO movie) {
        String query = "MovieInsert" +
                separator + movie.getName() + separator + movie.getReleaseYear()
                + separator + movie.getGenreId();
        try {
            String response = handleMessage(query, 15000);
            return "true".equals(response);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean genreInsert(GenreDTO genre) {
        String query = "GenreInsert" +
                separator + genre.getName();
        try {
            String response = handleMessage(query, 15000);
            return "true".equals(response);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean genreDelete(GenreDTO genre) {
        String query = "GenreDelete" + separator + genre.getId();
        try {
            String response = handleMessage(query, 15000);
            return "true".equals(response);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean movieDelete(MovieDTO movie) {
        String query = "MovieDelete" + separator + movie.getMovieId();
        try {
            String response = handleMessage(query, 15000);
            return "true".equals(response);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<GenreDTO> genreAll() {
        String query = "GenreAll";
        List<GenreDTO> list = new ArrayList<>();
        try {
            String response = handleMessage(query, 15000);
            String[] fields = response.split(separator);
            for (int i = 0; i < fields.length; i += 2) {
                Long id = Long.parseLong(fields[i]);
                String name = fields[i + 1];
                list.add(new GenreDTO(id, name));
            }
            return list;
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<MovieDTO> movieAll() {
        String query = "MovieAll";
        return getMovieDTOS(query);
    }

    public List<MovieDTO> movieFindByGenreId(Long genreId) {
        String query = "MovieFindByGenreId" + separator + genreId.toString();
        return getMovieDTOS(query);
    }

    private List<MovieDTO> getMovieDTOS(String query) {
        List<MovieDTO> list = new ArrayList<>();
        try {
            String response = handleMessage(query, 15000);
            String[] fields = response.split(separator);
            for (int i = 0; i < fields.length; i += 4) {
                Long id = Long.parseLong(fields[i]);
                String name = fields[i + 1];
                Integer year = Integer.parseInt(fields[i + 2]);
                Long genreId = Long.parseLong(fields[i + 3]);
                list.add(new MovieDTO(id,name, year, genreId));
            }
            return list;
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cleanMessages() {
        try {
            Message message = consumer.receiveNoWait();
            while (message!=null) {
                message = consumer.receiveNoWait();
            }
        } catch (JMSException e) {
            e.printStackTrace();
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
}
