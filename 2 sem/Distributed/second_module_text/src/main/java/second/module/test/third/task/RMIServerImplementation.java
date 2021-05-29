package second.module.test.third.task;

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

    @Override
    public Actor actorById(Long id) throws RemoteException {
        return ActorsDAO.findById(id);
    }

    @Override
    public Actor actorByName(String name) throws RemoteException {
        return ActorsDAO.findByName(name);
    }

    @Override
    public boolean updateActorByName(Actor actor) throws RemoteException {
        return ActorsDAO.updateActorByName(actor);
    }

    @Override
    public boolean actorInsert(Actor actor) throws RemoteException {
        return ActorsDAO.insert(actor);
    }

    @Override
    public boolean actorDelete(Actor actor) throws RemoteException {
        return ActorsDAO.delete(actor);
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
    public Movie movieById(Long id) throws RemoteException {
        return MovieDAO.findById(id);
    }

    @Override
    public Movie movieByName(String name) throws RemoteException {
        return MovieDAO.findByName(name);
    }

    @Override
    public boolean updateMovieByName(Movie movie) throws RemoteException {
        return MovieDAO.updateMovieByName(movie);
    }

    @Override
    public boolean movieInsert(Movie movie) throws RemoteException {
        return MovieDAO.insert(movie);
    }

    @Override
    public boolean movieDelete(Movie movie) throws RemoteException {
        return MovieDAO.delete(movie);
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
    public boolean deleteMoviesByReleaseYearLessThan(Long number) throws RemoteException {
        return MovieDAO.deleteByReleaseYearLessThan(number);
    }

    public static void main(String[] args) throws RemoteException {
        RMIServerImplementation rmiServer = new RMIServerImplementation();
        Registry registry = LocateRegistry.createRegistry(123);
        registry.rebind("Module", rmiServer);
        System.out.println("Server started!");
    }
}
