package third.lab.client;

import third.lab.dto.GenreDTO;
import third.lab.dto.MovieDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class App extends JFrame {
    private static JFrame frame;
    private static Client client;

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
                client.disconnect();
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
            currentGenre = client.genreFindByName(name);
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
            currentMovie = client.movieFindByName(name);
            genreMode = false;
            genrePanel.setVisible(false);
            moviePanel.setVisible(true);
            fillMovieFields();
            pack();
        });
        fillComboBoxes();
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
                delete();
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
                if (client.genreUpdate(currentGenre)) {
                    JOptionPane.showMessageDialog(null, "Error: update failed!");
                }
            } else {
                currentMovie.setName(textMovieName.getText());
                currentMovie.setReleaseYear(Integer.parseInt(textMovieYear.getText()));

                GenreDTO genre = client.genreFindByName(textMovieGenreName.getText());
                if (genre == null) {
                    JOptionPane.showMessageDialog(null, "Error: no such genre!");
                    return;
                }
                currentMovie.setGenreId(genre.getId());

                if (!client.movieUpdate(currentMovie)) {
                    JOptionPane.showMessageDialog(null, "Error: update failed!");
                }
            }
        } else {
            if (genreMode) {
                GenreDTO genre = new GenreDTO();
                genre.setName(textGenreName.getText());
                if (!client.genreInsert(genre)) {
                    JOptionPane.showMessageDialog(null, "Error: insertion failed!");
                    return;
                }
                comboGenre.addItem(genre.getName());
            } else {
                MovieDTO movie = new MovieDTO();
                movie.setName(textMovieName.getText());
                movie.setReleaseYear(Integer.parseInt(textMovieYear.getText()));

                GenreDTO genre = client.genreFindByName(textMovieGenreName.getText());
                if (genre == null) {
                    JOptionPane.showMessageDialog(null, "Error: no such genre!");
                    return;
                }
                movie.setGenreId(genre.getId());

                if (!client.movieInsert(movie)) {
                    JOptionPane.showMessageDialog(null, "Error: insertion failed!");
                    return;
                }
                comboMovie.addItem(movie.getName());
            }
        }
    }

    private static void delete() {
        if (editMode) {
            if (genreMode) {
                List<MovieDTO> list = client.movieFindByGenreId(currentGenre.getId());
                assert list != null;
                for (MovieDTO m : list) {
                    comboMovie.removeItem(m.getName());
                    client.movieDelete(m);
                }
                client.genreDelete(currentGenre);
                comboGenre.removeItem(currentGenre.getName());
            } else {
                client.movieDelete(currentMovie);
                comboMovie.removeItem(currentMovie.getName());
            }
        }
    }

    private void fillComboBoxes() {
        comboGenre.removeAllItems();
        comboMovie.removeAllItems();
        List<GenreDTO> genres = client.genreAll();
        List<MovieDTO> movies = client.movieAll();
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

    private static void fillMovieFields() {
        if (currentMovie == null)
            return;
        GenreDTO genre = client.genreFindById(currentMovie.getGenreId());
        textMovieName.setText(currentMovie.getName());
        assert genre != null;
        textMovieGenreName.setText(genre.getName());
        textMovieYear.setText(String.valueOf(currentMovie.getReleaseYear()));
    }

    public static void main(String[] args) throws IOException {
        client = new Client("localhost", 5433);
        JFrame myWindow = new App();
        myWindow.setVisible(true);
    }
}
