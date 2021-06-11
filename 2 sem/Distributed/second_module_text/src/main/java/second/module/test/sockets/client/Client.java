package second.module.test.sockets.client;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import second.module.test.models.Actor;
import second.module.test.models.Movie;
import second.module.test.ui.Requests;
import second.module.test.ui.UI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Client implements Requests {
    private Socket sock;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String ip, int port) throws IOException {
        sock = new Socket(ip, port);
        in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        out = new PrintWriter(sock.getOutputStream(), true);
    }

    public void disconnect() {
        try {
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createActor(Actor actor) throws RemoteException {
        String query = "ActorInsert#" + (new Gson().toJson(actor));
        out.println(query);
    }

    @Override
    public Actor readActor(Long id) throws RemoteException {
        String query = "ActorFindById#" + id;
        out.println(query);
        try {
            return new Gson().fromJson(in.readLine(), Actor.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateActor(Actor actor) throws RemoteException {
        String query = "ActorUpdate#" + (new Gson().toJson(actor));
        out.println(query);
    }

    @Override
    public void deleteActor(Long id) throws RemoteException {
        String query = "ActorDelete#" + id;
        out.println(query);
    }

    @Override
    public void createMovie(Movie movie) throws RemoteException {
        String query = "MovieInsert#" + (new Gson().toJson(movie));
        out.println(query);
    }

    @Override
    public Movie readMovie(Long id) throws RemoteException {
        String query = "MovieFindById#" + id;
        out.println(query);
        try {
            return new Gson().fromJson(in.readLine(), Movie.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateMovie(Movie movie) throws RemoteException {
        String query = "MovieUpdate#" + (new Gson().toJson(movie));
        out.println(query);
    }

    @Override
    public void deleteMovie(Long id) throws RemoteException {
        String query = "MovieDelete#" + id;
        out.println(query);
    }

    @Override
    public List<Actor> findAllActors() throws RemoteException {
        String query = "ActorAll#";
        out.println(query);

        Type ListType = new TypeToken<ArrayList<Long>>(){}.getType();
        try {
            return new Gson().fromJson(in.readLine(), ListType);
        } catch (JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Actor> finaAllActorsByMovieId(Long id) throws RemoteException {
        String query = "ActorAllByMovieId#" + id;
        out.println(query);

        Type ListType = new TypeToken<ArrayList<Long>>(){}.getType();
        try {
            return new Gson().fromJson(in.readLine(), ListType);
        } catch (JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Movie> findAllMovies() throws RemoteException {
        String query = "MovieAll#";
        out.println(query);

        Type ListType = new TypeToken<ArrayList<Long>>(){}.getType();
        try {
            return new Gson().fromJson(in.readLine(), ListType);
        } catch (JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Movie> findAllMoviesByCurrentAndLastYear() throws RemoteException {
        String query = "MovieAllByCurrentAndLastYear#";
        out.println(query);

        Type ListType = new TypeToken<ArrayList<Long>>(){}.getType();
        try {
            return new Gson().fromJson(in.readLine(), ListType);
        } catch (JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteMoviesByReleaseYearLessThan(Long number) throws RemoteException {
        String query = "DeleteMoviesByReleaseYear#" + number;
        out.println(query);
    }

    public static void main(String[] args) throws IOException, ParseException {
        Client client = new Client("localhost",12345);
        UI ui = new UI(client);
        ui.interactWithUser(args);
    }
}
