package second.module.test.second.task.client;

import second.module.test.models.Actor;
import second.module.test.models.Movie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Client {
    private Socket sock;
    private PrintWriter out;
    private BufferedReader in;
    private static final String split = "#";

    public Client(String ip, int port) throws IOException {
        // establish connection
        sock = new Socket(ip, port);
        // get input/output streams
        in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        out = new PrintWriter(sock.getOutputStream(), true);
    }

    public Movie movieFindById(Long id) {
        String query = "MovieFindById" + split + id.toString();
        out.println(query);
        String response;
        try {
            response = in.readLine();
            String[] fields = response.split(split);
            String name = fields[1];
            Integer releaseYear = Integer.parseInt(fields[2]);
            String country = fields[3];
            return new Movie(id, name, releaseYear, country);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Movie movieFindByName(String name) {
        String query = "MovieFindByName" + split + name;
        out.println(query);
        String response;
        try {
            response = in.readLine();
            String[] fields = response.split(split);
            Long id = Long.parseLong(fields[0]);
            Integer releaseYear = Integer.parseInt(fields[2]);
            String country = fields[3];
            return new Movie(id, name, releaseYear, country);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Actor actorFindById(Long id) {
        String query = "ActorFindById" + split + id;
        out.println(query);
        String response;
        try {
            response = in.readLine();
            String[] fields = response.split(split);
            String name = fields[1];
            String surname = fields[2];
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate = formatter.parse(fields[3]);
            Long movieId = Long.parseLong(fields[4]);
            return new Actor(id, name, surname, birthDate, movieId);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean actorUpdate(Actor actor) {
        String query = "ActorUpdate" + split + actor.getId().toString() +
                "#" +  actor.getName()
                + "#" + actor.getSurname() + '#' + actor.getBirthDate() + '#'
                + actor.getMovieId().toString();
        out.println(query);
        try {
            String response = in.readLine();
            if ("true".equals(response))
                return true;
            else
                return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean movieUpdate(Movie movie) {
        String query = "MovieUpdate" + split + movie.getId().toString() +
                "#" + movie.getName() + '#' + movie.getCountry() + '#'
                + movie.getReleaseYear().toString();
        out.println(query);
        try {
            String response = in.readLine();
            if ("true".equals(response))
                return true;
            else
                return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean actorInsert(Actor actor) {
        String query = "ActorInsert" + split + actor.getId().toString() +
                "#" +  actor.getName()
                + "#" + actor.getSurname() + '#' + actor.getBirthDate() + '#'
                + actor.getMovieId().toString();
        out.println(query);
        try {
            String response = in.readLine();
            if ("true".equals(response))
                return true;
            else
                return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean movieInsert(Movie movie) {
        String query = "MovieInsert" + split + movie.getId().toString() +
                "#" + movie.getName() + '#' + movie.getCountry() + '#'
                + movie.getReleaseYear().toString();
        out.println(query);
        try {
            String response = in.readLine();
            if ("true".equals(response))
                return true;
            else
                return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean movieDelete(Movie movie) {
        String query = "MovieDelete" + split + movie.getId().toString();
        out.println(query);
        try {
            String response = in.readLine();
            if ("true".equals(response))
                return true;
            else
                return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean actorDelete(Actor actor) {
        String query = "ActorDelete" + split + actor.getId().toString();
        out.println(query);
        try {
            String response = in.readLine();
            if ("true".equals(response))
                return true;
            else
                return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Movie> movieAll() {
        String query = "MovieAll";
        out.println(query);
        List<Movie> list = new ArrayList<>();
        try {
            String response = in.readLine();
            String[] fields = response.split(split);
            for (int i = 0; i < fields.length; i += 4) {
                Long id = Long.parseLong(fields[i]);
                String name = fields[i + 1];
                Integer releaseYear = Integer.parseInt(fields[i + 2]);
                String country = fields[i + 3];
                list.add(new Movie(id, name, releaseYear, country));
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Actor> actorAll() {
        String query = "ActorAll";
        out.println(query);
        List<Actor> list = new ArrayList<>();
        try {
            String response = in.readLine();
            String[] fields = response.split(split);
            for (int i = 0; i < fields.length; i += 5) {
                Long id = Long.parseLong(fields[0]);
                String name = fields[1];
                String surname = fields[2];
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date birthDate = formatter.parse(fields[3]);
                Long movieId = Long.parseLong(fields[4]);
                list.add(new Actor(id, name, surname, birthDate, movieId));
            }
            return list;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Actor> actorFindByMovieId(Long idc) {
        String query = "ActorFindByMovieId" + split + idc.toString();
        out.println(query);
        List<Actor> list = new ArrayList<>();
        try {
            String response = in.readLine();
            String[] fields = response.split(split);
            for (int i = 0; i < fields.length; i += 5) {
                Long id = Long.parseLong(fields[0]);
                String name = fields[1];
                String surname = fields[2];
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date birthDate = formatter.parse(fields[3]);
                Long movieId = Long.parseLong(fields[4]);
                list.add(new Actor(id, name, surname, birthDate, movieId));
            }
            return list;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void disconnect() {
        try {
            sock.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
