package second.module.test.rmi;

import second.module.test.models.Actor;
import second.module.test.models.Movie;
import second.module.test.ui.Requests;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMIServer extends Remote, Requests {
    void createActor(Actor actor) throws RemoteException;
    Actor readActor(Long id) throws RemoteException;
    void updateActor(Actor actor) throws RemoteException;
    void deleteActor(Long id) throws RemoteException;

    void createMovie(Movie movie) throws RemoteException;
    Movie readMovie(Long id) throws RemoteException;
    void updateMovie(Movie movie) throws RemoteException;
    void deleteMovie(Long id) throws RemoteException;

    List<Actor> findAllActors() throws RemoteException;
    List<Actor> finaAllActorsByMovieId(Long id) throws RemoteException;
    List<Movie> findAllMovies() throws RemoteException;
    List<Movie> findAllMoviesByCurrentAndLastYear() throws RemoteException;
    void deleteMoviesByReleaseYearLessThan(Long number) throws RemoteException;
}
