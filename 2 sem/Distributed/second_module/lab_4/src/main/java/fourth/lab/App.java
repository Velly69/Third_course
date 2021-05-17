package fourth.lab;

import fourth.lab.dto.GenreDTO;
import fourth.lab.dto.MovieDTO;
import fourth.lab.rmi.Backend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class App extends JFrame {
    private static JFrame frame;

    private static Backend backend = null;

    private static GenreDTO currentGenre = null;
    private static MovieDTO currentMovie = null;

    private static boolean editMode = false;
    private static boolean genreMode = true;

    private static JButton btnAddGenre = new JButton("Add Genre");
    private static JButton btnAddMovie = new JButton("Add Movie");
    private static JButton btnEdit = new JButton("Edit Data");
    private static JButton btnBack = new JButton("Back");
    private static JButton btnSave = new JButton("Save");
    private static JButton btnDelete = new JButton("Delete");

    private static Box menuPanel = Box.createVerticalBox();
    private static Box actionPanel = Box.createVerticalBox();
    private static Box comboPanel = Box.createVerticalBox();
    private static Box moviePanel = Box.createVerticalBox();
    private static Box genrePanel = Box.createVerticalBox();

    private static JComboBox comboGenre = new JComboBox();
    private static JComboBox comboMovie = new JComboBox();

    private static JTextField textGenreName = new JTextField(30);
    private static JTextField textMovieName = new JTextField(30);
    private static JTextField textMovieGenreName = new JTextField(30);
    private static JTextField textMovieYear = new JTextField(30);

    private App() {
        super("Video Shop");
        frame = this;
        frame.setPreferredSize(new Dimension(400, 500));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                frame.dispose();
                System.exit(0);
            }
        });
        Box box = Box.createVerticalBox();
        sizeAllElements();
        frame.setLayout(new FlowLayout());

        // Menu
        menuPanel.add(btnAddGenre);
        menuPanel.add(Box.createVerticalStrut(20));
        btnAddGenre.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = false;
                genreMode = true;
                menuPanel.setVisible(false);
                comboPanel.setVisible(false);
                genrePanel.setVisible(true);
                moviePanel.setVisible(false);
                actionPanel.setVisible(true);
                pack();
            }
        });
        menuPanel.add(btnAddMovie);
        menuPanel.add(Box.createVerticalStrut(20));
        btnAddMovie.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = false;
                genreMode = false;
                menuPanel.setVisible(false);
                comboPanel.setVisible(false);
                genrePanel.setVisible(false);
                moviePanel.setVisible(true);
                actionPanel.setVisible(true);
                pack();
            }
        });
        menuPanel.add(btnEdit);
        menuPanel.add(Box.createVerticalStrut(20));
        btnEdit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = true;
                menuPanel.setVisible(false);
                comboPanel.setVisible(true);
                genrePanel.setVisible(false);
                moviePanel.setVisible(false);
                actionPanel.setVisible(true);
                pack();
            }
        });

        // ComboBoxes
        comboPanel.add(new JLabel("Genre:"));
        comboPanel.add(comboGenre);
        comboPanel.add(Box.createVerticalStrut(20));
        comboGenre.addActionListener(e -> {
            String name = (String) comboGenre.getSelectedItem();
            try {
                currentGenre = backend.genreFindByName(name);
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
            genreMode = true;
            genrePanel.setVisible(true);
            moviePanel.setVisible(false);
            fillGenreFields();
            pack();
        });
        comboPanel.add(new JLabel("Movie:"));
        comboPanel.add(comboMovie);
        comboPanel.add(Box.createVerticalStrut(20));
        comboMovie.addActionListener(e -> {
            String name = (String) comboMovie.getSelectedItem();
            try {
                currentMovie = backend.movieFindByName(name);
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
            genreMode = false;
            genrePanel.setVisible(false);
            moviePanel.setVisible(true);
            try {
                fillMovieFields();
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
            pack();
        });
        try {
            fillComboBoxes();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        comboPanel.setVisible(false);

        moviePanel.add(new JLabel("Name:"));
        moviePanel.add(textMovieName);
        moviePanel.add(Box.createVerticalStrut(20));
        moviePanel.add(new JLabel("Genre Name:"));
        moviePanel.add(textMovieGenreName);
        moviePanel.add(Box.createVerticalStrut(20));
        moviePanel.add(new JLabel("Release year:"));
        moviePanel.add(textMovieYear);
        moviePanel.add(Box.createVerticalStrut(20));
        moviePanel.setVisible(false);

        genrePanel.add(new JLabel("Name:"));
        genrePanel.add(textGenreName);
        genrePanel.add(Box.createVerticalStrut(20));
        genrePanel.setVisible(false);

        actionPanel.add(btnSave);
        actionPanel.add(Box.createVerticalStrut(20));
        btnSave.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                save();
            }
        });
        actionPanel.add(btnDelete);
        actionPanel.add(Box.createVerticalStrut(20));
        btnDelete.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                try {
                    delete();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        actionPanel.add(btnBack);
        actionPanel.add(Box.createVerticalStrut(20));
        btnBack.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                clearFields();
                menuPanel.setVisible(true);
                comboPanel.setVisible(false);
                genrePanel.setVisible(false);
                moviePanel.setVisible(false);
                actionPanel.setVisible(false);
                pack();
            }
        });
        actionPanel.setVisible(false);

        clearFields();
        box.setPreferredSize(new Dimension(300, 500));
        box.add(menuPanel);
        box.add(comboPanel);
        box.add(genrePanel);
        box.add(moviePanel);
        box.add(actionPanel);
        setContentPane(box);
        pack();
    }

    private static void sizeAllElements() {
        Dimension dimension = new Dimension(300, 50);
        btnAddGenre.setMaximumSize(dimension);
        btnAddMovie.setMaximumSize(dimension);
        btnEdit.setMaximumSize(dimension);
        btnBack.setMaximumSize(dimension);
        btnSave.setMaximumSize(dimension);
        btnDelete.setMaximumSize(dimension);

        btnAddGenre.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAddMovie.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSave.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEdit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDelete.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension panelDimension = new Dimension(300, 300);
        menuPanel.setMaximumSize(panelDimension);
        comboPanel.setPreferredSize(panelDimension);
        actionPanel.setPreferredSize(panelDimension);
        moviePanel.setPreferredSize(panelDimension);
        genrePanel.setPreferredSize(panelDimension);

        comboGenre.setPreferredSize(dimension);
        comboMovie.setPreferredSize(dimension);

        textMovieGenreName.setPreferredSize(dimension);
        textMovieName.setPreferredSize(dimension);
        textMovieYear.setPreferredSize(dimension);
        textGenreName.setPreferredSize(dimension);
    }

    private static void save() {
        if (editMode) {
            if (genreMode) {
                currentGenre.setName(textGenreName.getText());
                try {
                    if (backend.genreUpdate(currentGenre)) {
                        JOptionPane.showMessageDialog(null, "Error: update failed!");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                currentMovie.setName(textMovieName.getText());
                currentMovie.setReleaseYear(Integer.parseInt(textMovieYear.getText()));

                GenreDTO genre = null;
                try {
                    genre = backend.genreFindByName(textMovieGenreName.getText());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if (genre == null) {
                    JOptionPane.showMessageDialog(null, "Error: no such genre!");
                    return;
                }
                currentMovie.setGenreId(genre.getId());

                try {
                    if (!backend.movieUpdate(currentMovie)) {
                        JOptionPane.showMessageDialog(null, "Error: update failed!");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (genreMode) {
                GenreDTO genre = new GenreDTO();
                genre.setName(textGenreName.getText());
                try {
                    if (!backend.genreInsert(genre)) {
                        JOptionPane.showMessageDialog(null, "Error: insertion failed!");
                        return;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                comboGenre.addItem(genre.getName());
            } else {
                MovieDTO movie = new MovieDTO();
                movie.setName(textMovieName.getText());
                movie.setReleaseYear(Integer.parseInt(textMovieYear.getText()));

                GenreDTO genre = null;
                try {
                    genre = backend.genreFindByName(textMovieGenreName.getText());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if (genre == null) {
                    JOptionPane.showMessageDialog(null, "Error: no such genre!");
                    return;
                }
                movie.setGenreId(genre.getId());

                try {
                    if (!backend.movieInsert(movie)) {
                        JOptionPane.showMessageDialog(null, "Error: insertion failed!");
                        return;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                comboMovie.addItem(movie.getName());
            }
        }
    }

    private static void delete() throws RemoteException {
        if (editMode) {
            if (genreMode) {
                List<MovieDTO> list = backend.movieFindByGenreId(currentGenre.getId());
                assert list != null;
                for (MovieDTO m : list) {
                    comboMovie.removeItem(m.getName());
                    backend.movieDelete(m);
                }
                backend.genreDelete(currentGenre);
                comboGenre.removeItem(currentGenre.getName());
            } else {
                backend.movieDelete(currentMovie);
                comboMovie.removeItem(currentMovie.getName());
            }
        }
    }

    private void fillComboBoxes() throws RemoteException {
        comboGenre.removeAllItems();
        comboMovie.removeAllItems();
        List<GenreDTO> genres = backend.genreFindAll();
        List<MovieDTO> movies = backend.movieFindAll();
        assert genres != null;
        for (GenreDTO genre : genres) {
            comboGenre.addItem(genre.getName());
        }
        assert movies != null;
        for (MovieDTO movie : movies) {
            comboMovie.addItem(movie.getName());
        }
    }

    private static void clearFields() {
        textGenreName.setText("");
        textMovieName.setText("");
        textMovieGenreName.setText("");
        textMovieYear.setText("");
        currentGenre = null;
        currentMovie = null;
    }

    private static void fillGenreFields() {
        if (currentGenre == null)
            return;
        textGenreName.setText(currentGenre.getName());
    }

    private static void fillMovieFields() throws RemoteException {
        if (currentMovie == null)
            return;
        GenreDTO genre = backend.genreFindById(currentMovie.getGenreId());
        textMovieName.setText(currentMovie.getName());
        assert genre != null;
        textMovieGenreName.setText(genre.getName());
        textMovieYear.setText(String.valueOf(currentMovie.getReleaseYear()));
    }

    public static void main(String[] args) throws IOException, NotBoundException {
        String url = "//localhost:1234/videoshop";
        backend = (Backend) Naming.lookup(url);
        JFrame myWindow = new App();
        myWindow.setVisible(true);
    }
}

