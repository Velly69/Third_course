package second.module.test.second.task.server;

import second.module.test.dao.ActorsDAO;
import second.module.test.dao.MovieDAO;
import second.module.test.models.Actor;
import second.module.test.models.Movie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Server {
    private ServerSocket server = null;
    private Socket sock = null;
    private PrintWriter out = null;
    private BufferedReader in = null;

    public void start(int port) throws IOException {
        server = new ServerSocket(port);
        while (true) {
            sock = server.accept();
            in = new BufferedReader(new InputStreamReader(sock.getInputStream( )));
            out = new PrintWriter(sock.getOutputStream(), true);
            while (processQuery());
        }
    }

    private boolean processQuery() {
        int result = 0;
        String response;
        try {
            String query = in.readLine();
            if (query==null)
                return false;

            String[] fields = query.split("#");
            if (fields.length == 0){
                return true;
            } else {
                String action = fields[0];
                Movie movie;
                Actor actor;

                System.out.println(action);

                switch(action) {
                    case "MovieFindById":
                        Long id = Long.parseLong(fields[1]);
                        movie = MovieDAO.findById(id);
                        response = movie.getId().toString()+"#"+movie.getName()+"#"+movie.getCountry()+"#"+movie.getReleaseYear().toString();
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "ActorFindByMovieId":
                        id = Long.parseLong(fields[1]);
                        List<Actor> list= ActorsDAO.finaAllByMovieId(id);
                        StringBuilder str = new StringBuilder();
                        for(Actor actorInList: list) {
                            str.append(actorInList.getId());
                            str.append("#");
                            str.append(actorInList.getName());
                            str.append("#");
                            str.append(actorInList.getSurname());
                            str.append("#");
                            str.append(actorInList.getBirthDate());
                            str.append("#");
                            str.append(actorInList.getBirthDate());
                            str.append("#");
                            str.append(actorInList.getMovieId());
                            str.append("#");
                        }
                        response = str.toString();
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "MovieFindByName":
                        String name = fields[1];
                        movie = MovieDAO.findByName(name);
                        response = movie.getId().toString()+"#"+movie.getName()+"#"
                                +movie.getCountry()+"#"+movie.getReleaseYear().toString();
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "ActorFindById":
                        name = fields[1];
                        actor = ActorsDAO.findByName(name);
                        response = actor.getId().toString()+"#"+actor.getName()+"#" +actor.getSurname()
                                +"#"+actor.getBirthDate().toString() + '#' + actor.getMovieId().toString();
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "ActorUpdate":
                        id = Long.parseLong(fields[1]);
                        name = fields[2];
                        String surname = fields[3];
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        Date birthDate = formatter.parse(fields[4]);
                        Long movieId = Long.parseLong(fields[5]);
                        actor = new Actor(id, name, surname, birthDate, movieId);
                        if(ActorsDAO.updateActorByName(actor))
                            response = "true";
                        else
                            response = "false";
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "MovieUpdate":
                        id = Long.parseLong(fields[1]);
                        name = fields[2];
                        Integer releaseYear = Integer.parseInt(fields[3]);
                        String country = fields[4];
                        movie = new Movie(id, name, releaseYear, country);
                        if (MovieDAO.updateMovieByName(movie))
                            response = "true";
                        else
                            response = "false";
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "MovieInsert":
                        movieId = Long.parseLong(fields[1]);
                        name = fields[2];
                        releaseYear = Integer.parseInt(fields[3]);
                        country = fields[4];
                        movie = new Movie(movieId, name, releaseYear, country);
                        if(MovieDAO.insert(movie))
                            response = "true";
                        else
                            response = "false";
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "ActorInsert":
                        Long actorId = Long.parseLong(fields[1]);
                        name = fields[2];
                        surname = fields[3];
                        formatter = new SimpleDateFormat("yyyy-MM-dd");
                        birthDate = formatter.parse(fields[4]);
                        movieId = Long.parseLong(fields[5]);
                        actor = new Actor(actorId, name, surname, birthDate, movieId);
                        if(ActorsDAO.insert(actor))
                            response = "true";
                        else
                            response = "false";
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "ActorDelete":
                        id = Long.parseLong(fields[1]);
                        actor = new Actor();
                        actor.setId(id);
                        if(ActorsDAO.delete(actor))
                            response = "true";
                        else
                            response = "false";
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "MovieDelete":
                        id = Long.parseLong(fields[1]);
                        movie = new Movie();
                        movie.setId(id);
                        if(MovieDAO.delete(movie))
                            response = "true";
                        else
                            response = "false";
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "ActorAll":
                        List<Actor> list1 = ActorsDAO.findAll();
                        str = new StringBuilder();
                        for(Actor a: list1) {
                            str.append(a.getId());
                            str.append("#");
                            str.append(a.getName());
                            str.append("#");
                            str.append(a.getSurname());
                            str.append("#");
                            str.append(a.getBirthDate());
                            str.append("#");
                        }
                        response = str.toString();
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "MovieAll":
                        List<Movie> list2 = MovieDAO.findAll();
                        str = new StringBuilder();
                        for(Movie m: list2) {
                            str.append(m.getId());
                            str.append("#");
                            str.append(m.getName());
                            str.append("#");
                            str.append(m.getCountry());
                            str.append("#");
                            str.append(m.getReleaseYear());
                            str.append("#");
                        }
                        response = str.toString();
                        System.out.println(response);
                        out.println(response);
                        break;
                }
            }

            return true;
        }
        catch(IOException | ParseException e){
            return false;
        }
    }
    public static void main(String[] args) {
        try {
            Server srv = new Server();
            srv.start(12345);
        } catch(IOException e) {
            System.out.println("Error happened :c");
        }
    }
}
