package third.lab.client;

import third.lab.dto.GenreDTO;
import third.lab.dto.MovieDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;
    private static final String separator = "#";

    public Client(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public GenreDTO genreFindById(Long id) {
        String query = "GenreFindById" + separator + id.toString();
        out.println(query);
        String response;
        try {
            response = in.readLine();
            return new GenreDTO(id, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public MovieDTO movieFindByName(String name) {
        String query = "MovieFindByName" + separator + name;
        out.println(query);
        String response = "";
        try {
            response = in.readLine();
            String[] fields = response.split(separator);
            Long id = Long.parseLong(fields[0]);
            Integer year = Integer.parseInt(fields[2]);
            Long genreId = Long.parseLong(fields[3]);
            return new MovieDTO(id, name, year, genreId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GenreDTO genreFindByName(String name) {
        String query = "GenreFindByName" + separator + name;
        out.println(query);
        try {
            Long response = Long.parseLong(in.readLine());
            return new GenreDTO(response, name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean movieUpdate(MovieDTO movie) {
        String query = "MovieUpdate" + separator + movie.getMovieId() +
                separator + movie.getName() + separator + movie.getReleaseYear()
                + separator + movie.getGenreId();
        out.println(query);
        try {
            String response = in.readLine();
            return "true".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean genreUpdate(GenreDTO genre) {
        String query = "GenreUpdate" + separator + genre.getId() +
                separator + genre.getName();
        out.println(query);
        try {
            String response = in.readLine();
            return "true".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean movieInsert(MovieDTO movie) {
        String query = "MovieInsert" +
                separator + movie.getName() + separator + movie.getReleaseYear()
                + separator + movie.getGenreId();
        out.println(query);
        try {
            String response = in.readLine();
            return "true".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean genreInsert(GenreDTO genre) {
        String query = "GenreInsert" +
                separator + genre.getName();
        out.println(query);
        try {
            String response = in.readLine();
            return "true".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean genreDelete(GenreDTO genre) {
        String query = "GenreDelete" + separator + genre.getId();
        out.println(query);
        try {
            String response = in.readLine();
            return "true".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean movieDelete(MovieDTO movie) {
        String query = "MovieDelete" + separator + movie.getMovieId();
        out.println(query);
        try {
            String response = in.readLine();
            return "true".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<GenreDTO> genreAll() {
        String query = "GenreAll";
        out.println(query);
        List<GenreDTO> list = new ArrayList<>();
        try {
            String response = in.readLine();
            String[] fields = response.split(separator);
            for (int i = 0; i < fields.length; i += 2) {
                Long id = Long.parseLong(fields[i]);
                String name = fields[i + 1];
                list.add(new GenreDTO(id, name));
            }
            return list;
        } catch (IOException e) {
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
        out.println(query);
        List<MovieDTO> list = new ArrayList<>();
        try {
            String response = in.readLine();
            String[] fields = response.split(separator);
            for (int i = 0; i < fields.length; i += 4) {
                Long id = Long.parseLong(fields[i]);
                String name = fields[i + 1];
                Integer year = Integer.parseInt(fields[i + 2]);
                Long genreId = Long.parseLong(fields[i + 3]);
                list.add(new MovieDTO(id,name, year, genreId));
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
