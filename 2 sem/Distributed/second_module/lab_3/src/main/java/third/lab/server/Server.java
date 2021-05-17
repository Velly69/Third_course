package third.lab.server;

import third.lab.dto.GenreDTO;
import third.lab.dto.MovieDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    private ServerSocket server = null;
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private static final String separator = "#";

    public void start(int port) throws IOException {
        server = new ServerSocket(port);
        while (true) {
            socket = server.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            while (processQuery()) ;
        }
    }

    private boolean processQuery() {
        String response;
        try {
            String query = in.readLine();
            if (query == null) {
                return false;
            }

            String [] fields = query.split(separator);
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
                        out.println(response);
                        break;
                    case "MovieFindByGenreId":
                        id = Long.parseLong(fields[1]);
                        List<MovieDTO> list = MovieDAO.findByGenreId(id);
                        StringBuilder str = new StringBuilder();
                        assert list != null;
                        moviesToString(str, list);
                        response = str.toString();
                        out.println(response);
                        break;
                    case "MovieFindByName":
                        String name = fields[1];
                        movieDTO = MovieDAO.findByName(name);
                        assert movieDTO != null;
                        response = movieDTO.getMovieId() + separator + movieDTO.getName() + separator + movieDTO.getReleaseYear() + separator + movieDTO.getGenreId();
                        out.println(response);
                        break;
                    case "GenreFindByName":
                        name = fields[1];
                        genreDTO = GenreDAO.findByName(name);
                        assert genreDTO != null;
                        response = genreDTO.getId() + "";
                        out.println(response);
                        break;
                    case "MovieUpdate":
                        id = Long.parseLong(fields[1]);
                        name = fields[2];
                        Integer year = Integer.parseInt(fields[3]);
                        Long genreId = Long.parseLong(fields[4]);
                        movieDTO = new MovieDTO(id, name, year, genreId);
                        if (MovieDAO.update(movieDTO))
                            response = "true";
                        else
                            response = "false";
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "GenreUpdate":
                        id = Long.parseLong(fields[1]);
                        name = fields[2];
                        genreDTO = new GenreDTO(id, name);
                        if (GenreDAO.update(genreDTO)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "MovieInsert":
                        name = fields[1];
                        year = Integer.parseInt(fields[2]);
                        genreId = Long.parseLong(fields[3]);
                        movieDTO = new MovieDTO((long) 0, name, year, genreId);
                        if (MovieDAO.insert(movieDTO)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "GenreInsert":
                        name = fields[1];
                        genreDTO = new GenreDTO();
                        genreDTO.setName(name);
                        if (GenreDAO.insert(genreDTO)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "MovieDelete":
                        id = Long.parseLong(fields[1]);
                        movieDTO = new MovieDTO();
                        movieDTO.setMovieId(id);
                        if (MovieDAO.delete(movieDTO)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "GenreDelete":
                        id = Long.parseLong(fields[1]);
                        genreDTO = new GenreDTO();
                        genreDTO.setId(id);
                        if (GenreDAO.delete(genreDTO)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "MovieAll":
                        List<MovieDTO> moviesList = MovieDAO.findAll();
                        str = new StringBuilder();
                        assert moviesList != null;
                        moviesToString(str, moviesList);
                        response = str.toString();
                        out.println(response);
                        break;
                    case "GenreAll":
                        List<GenreDTO> genresList = GenreDAO.findAll();
                        str = new StringBuilder();
                        for (GenreDTO genre : genresList) {
                            str.append(genre.getId());
                            str.append(separator);
                            str.append(genre.getName());
                            str.append(separator);
                        }
                        response = str.toString();
                        out.println(response);
                        break;
                }
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    private void moviesToString(StringBuilder str, List<MovieDTO> list) {
        for (MovieDTO movie : list) {
            str.append(movie.getMovieId());
            str.append(separator);
            str.append(movie.getName());
            str.append(separator);
            str.append(movie.getReleaseYear());
            str.append(separator);
            str.append(movie.getGenreId());
            str.append(separator);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.start(5433);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
