package second.module.test.rmi;

import second.module.test.dao.ActorsDAO;
import second.module.test.dao.MovieDAO;
import second.module.test.models.Actor;
import second.module.test.models.Movie;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RMIServerImplementation extends UnicastRemoteObject implements RMIServer {
    protected RMIServerImplementation() throws RemoteException {
        super();
    }

    public static void main(String[] args) throws RemoteException {
        RMIServerImplementation rmiServer = new RMIServerImplementation();
        Registry registry = LocateRegistry.createRegistry(123);
        registry.rebind("Module", rmiServer);
        System.out.println("Server started!");
    }

    @Override
    public void createActor(Actor actor) throws RemoteException {
        ActorsDAO.insert(actor);
    }

    @Override
    public Actor readActor(Long id) throws RemoteException {
        return ActorsDAO.findById(id);
    }

    @Override
    public void updateActor(Actor actor) throws RemoteException {
        ActorsDAO.updateActorByName(actor);
    }

    @Override
    public void deleteActor(Long id) throws RemoteException {
        ActorsDAO.delete(id);
    }

    @Override
    public void createMovie(Movie movie) throws RemoteException {
        MovieDAO.insert(movie);
    }

    @Override
    public Movie readMovie(Long id) throws RemoteException {
        return MovieDAO.findById(id);
    }

    @Override
    public void updateMovie(Movie movie) throws RemoteException {
        MovieDAO.updateMovieByName(movie);
    }

    @Override
    public void deleteMovie(Long id) throws RemoteException {
        MovieDAO.delete(id);
    }

    @Override
    public List<Actor> findAllActors() throws RemoteException {
        return ActorsDAO.findAll();
    }

    @Override
    public List<Actor> finaAllActorsByMovieId(Long id) throws RemoteException {
        return ActorsDAO.finaAllByMovieId(id);
    }

    @Override
    public List<Movie> findAllMovies() throws RemoteException {
        return MovieDAO.findAll();
    }

    @Override
    public List<Movie> findAllMoviesByCurrentAndLastYear() throws RemoteException {
        return MovieDAO.findAllByCurrentAndLastYear();
    }

    @Override
    public void deleteMoviesByReleaseYearLessThan(Long number) throws RemoteException {
        MovieDAO.deleteByReleaseYearLessThan(number);
    }
}
