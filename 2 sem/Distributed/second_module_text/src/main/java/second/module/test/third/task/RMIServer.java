package second.module.test.third.task;

import second.module.test.models.Actor;
import second.module.test.models.Movie;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMIServer extends Remote {
    public Actor actorById(Long id) throws RemoteException;
    public Actor actorByName(String name) throws RemoteException;
    public boolean updateActorByName(Actor actor) throws RemoteException;
    public boolean actorInsert(Actor actor) throws RemoteException;
    public boolean actorDelete(Actor actor) throws RemoteException;
    public List<Actor> findAllActors() throws RemoteException;
    public List<Actor> finaAllActorsByMovieId(Long id) throws RemoteException;
    public Movie movieById(Long id) throws RemoteException;
    public Movie movieByName(String name) throws RemoteException;
    public boolean updateMovieByName(Movie movie) throws RemoteException;
    public boolean movieInsert(Movie movie) throws RemoteException;
    public boolean movieDelete(Movie movie) throws RemoteException;
    public List<Movie> findAllMovies() throws RemoteException;
    public List<Movie> findAllMoviesByCurrentAndLastYear() throws RemoteException;
    public boolean deleteMoviesByReleaseYearLessThan(Long number) throws RemoteException;
}
