package second.module.test.sockets.server;

import com.google.gson.Gson;
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
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int NUMBER_OF_THREADS = 4;
    private ServerSocket server = null;
    private Socket sock = null;
    private PrintWriter out = null;
    private BufferedReader in = null;

    public void start(int port) throws IOException {
        server = new ServerSocket(port);
        sock = server.accept();
        in = new BufferedReader(new InputStreamReader(sock.getInputStream( )));
        out = new PrintWriter(sock.getOutputStream(), true);
        ExecutorService service = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        while (true) {
            String query = in.readLine();
            service.execute(new Process(out, query));
        }
    }

    private static class Process implements Runnable {
        private PrintWriter out;
        private String query;

        public Process(PrintWriter out, String query) {
            this.out = out;
            this.query = query;
        }

        @Override
        public void run() {
            if (query == null)
                return;
            String[] fields = query.split("#");
            if (fields.length == 0)
                return;

            String action = fields[0];

            String response = "";
            switch (action) {
                case "ActorInsert":
                    Actor actor = new Gson().fromJson(fields[1], Actor.class);
                    ActorsDAO.insert(actor);
                    break;
                case "ActorFindById":
                    long id = Long.parseLong(fields[1]);
                    actor = ActorsDAO.findById(id);
                    response = new Gson().toJson(actor);
                    break;
                case "ActorUpdate":
                    actor = new Gson().fromJson(fields[1], Actor.class);
                    ActorsDAO.updateActorByName(actor);
                    break;
                case "ActorDelete":
                    id = Long.parseLong(fields[1]);
                    ActorsDAO.delete(id);
                    break;
                case "MovieInsert":
                    Movie movie = new Gson().fromJson(fields[1], Movie.class);
                    MovieDAO.insert(movie);
                    break;
                case "MovieFindById":
                    id = Long.parseLong(fields[1]);
                    movie = MovieDAO.findById(id);
                    response = new Gson().toJson(movie);
                    break;
                case "MovieUpdate":
                    movie = new Gson().fromJson(fields[1], Movie.class);
                    MovieDAO.updateMovieByName(movie);
                    break;
                case "MovieDelete":
                    id = Long.parseLong(fields[1]);
                    MovieDAO.delete(id);
                    break;

//                case "cC":
//                    var cont = new Gson().fromJson(fields[1], Containing.class);
//                    ContainingDAO.insert(cont);
//                    break;
//                case "rC":
//                    id = Long.parseLong(fields[1]);
//                    var aux = Long.parseLong(fields[2]);
//                    cont = ContainingDAO.findById(id,aux);
//                    response = new Gson().toJson(cont);
//                    break;
//                case "uC":
//                    cont = new Gson().fromJson(fields[1], Containing.class);
//                    ContainingDAO.update(cont);
//                    break;
//                case "dC":
//                    id = Long.parseLong(fields[1]);
//                    aux = Long.parseLong(fields[2]);
//                    ContainingDAO.delete(id,aux);
//                    break;

                case "ActorAll":
                    List<Actor> list = ActorsDAO.findAll();
                    response = new Gson().toJson(list);
                    break;
                case "ActorAllByMovieId":
                    id = Long.parseLong(fields[1]);
                    List<Actor> list1 = ActorsDAO.finaAllByMovieId(id);
                    response = new Gson().toJson(list1);
                    break;
                case "MovieAll":
                    List<Movie> list2 = MovieDAO.findAll();
                    response = new Gson().toJson(list2);
                    break;
                case "MovieAllByCurrentAndLastYear":
                    List<Movie> list3 = MovieDAO.findAllByCurrentAndLastYear();
                    response = new Gson().toJson(list3);
                    break;
                case "DeleteMoviesByReleaseYear":
                    id = Long.parseLong(fields[1]);
                    MovieDAO.deleteByReleaseYearLessThan(id);
                    break;
            }
            out.println(response);
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
