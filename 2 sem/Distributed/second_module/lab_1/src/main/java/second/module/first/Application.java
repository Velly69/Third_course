package second.module.first;

import org.xml.sax.SAXException;
import second.module.first.models.Genre;
import second.module.first.models.Movie;
import second.module.first.models.VideoShop;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Map;

public class Application extends JFrame {
    private static JFrame frame;

    private static VideoShop shop;
    private static Genre currentGenre = null;
    private static Movie currentMovie = null;

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
    private static Box cityPanel = Box.createVerticalBox();
    private static Box countryPanel = Box.createVerticalBox();

    private static JComboBox comboGenre = new JComboBox();
    private static JComboBox comboMovie = new JComboBox();

    private static JTextField textGenreName = new JTextField(30);
    private static JTextField textMovieName = new JTextField(30);
    private static JTextField textMovieGenreName = new JTextField(30);
    private static JTextField textMovieYear = new JTextField(30);

    private Application() {
        super("Video Shop");
        frame = this;
        frame.setPreferredSize(new Dimension(400, 500));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                frame.dispose();
                try {
                    DOMParser.write(shop, "src/main/java/second/module/first/shop.xml");
                } catch (ParserConfigurationException | TransformerException e) {
                    e.printStackTrace();
                }
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
                countryPanel.setVisible(true);
                cityPanel.setVisible(false);
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
                countryPanel.setVisible(false);
                cityPanel.setVisible(true);
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
                countryPanel.setVisible(false);
                cityPanel.setVisible(false);
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
            currentGenre = shop.getGenre(name);
            genreMode = true;
            countryPanel.setVisible(true);
            cityPanel.setVisible(false);
            fillGenreFields();
            pack();
        });
        comboPanel.add(new JLabel("Movie:"));
        comboPanel.add(comboMovie);
        comboPanel.add(Box.createVerticalStrut(20));
        comboMovie.addActionListener(e -> {
            String name = (String) comboMovie.getSelectedItem();
            currentMovie = shop.getMovie(name);
            genreMode = false;
            countryPanel.setVisible(false);
            cityPanel.setVisible(true);
            fillMovieFields();
            pack();
        });
        fillComboBoxes();
        comboPanel.setVisible(false);

        // City Fields
        cityPanel.add(new JLabel("Name:"));
        cityPanel.add(textMovieName);
        cityPanel.add(Box.createVerticalStrut(20));
        cityPanel.add(new JLabel("Genre Name:"));
        cityPanel.add(textMovieGenreName);
        cityPanel.add(Box.createVerticalStrut(20));
        cityPanel.add(new JLabel("Release year:"));
        cityPanel.add(textMovieYear);
        cityPanel.add(Box.createVerticalStrut(20));
        cityPanel.setVisible(false);

        // Country Fields
        countryPanel.add(new JLabel("Name:"));
        countryPanel.add(textGenreName);
        countryPanel.add(Box.createVerticalStrut(20));
        countryPanel.setVisible(false);

        // Action Bar
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
                countryPanel.setVisible(false);
                cityPanel.setVisible(false);
                actionPanel.setVisible(false);
                pack();
            }
        });
        actionPanel.setVisible(false);

        clearFields();
        box.setPreferredSize(new Dimension(300, 500));
        box.add(menuPanel);
        box.add(comboPanel);
        box.add(countryPanel);
        box.add(cityPanel);
        box.add(actionPanel);
        setContentPane(box);
        //pack();
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
        cityPanel.setPreferredSize(panelDimension);
        countryPanel.setPreferredSize(panelDimension);

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
                String oldName = currentGenre.getName();
                String newName = textGenreName.getText();
                if (changeGenre(currentGenre) && !currentGenre.getName().equals(oldName)) {
                    comboGenre.removeItem(oldName);
                    comboGenre.addItem(newName);
                    comboGenre.setSelectedIndex(comboGenre.getItemCount() - 1);
                }
            } else {
                String oldName = currentMovie.getName();
                String newName = textMovieGenreName.getText();
                if (changeMovie(currentMovie) && !currentMovie.getName().equals(oldName)) {
                    comboMovie.removeItem(oldName);
                    comboMovie.addItem(newName);
                    comboMovie.setSelectedIndex(comboMovie.getItemCount() - 1);
                }
            }
        } else {
            if (genreMode) {
                Genre genre = new Genre();
                shop.createId(genre);
                if (changeGenre(genre)) {
                    shop.addGenre(genre);
                    comboGenre.addItem(genre.getName());
                }
            } else {
                Movie movie = new Movie();
                shop.createId(movie);
                if (changeMovie(movie)) {
                    shop.addMovie(movie);
                    comboMovie.addItem(movie.getName());
                }
            }
        }
    }

    private static boolean changeGenre(Genre genre) {
        String newName = textGenreName.getText();
        if (shop.getGenre(newName) == null) {
            shop.renameGenre(genre, newName);
            return true;
        }
        fillGenreFields();
        JOptionPane.showMessageDialog(null, "Error: this country already exists!");
        return false;
    }

    private static boolean changeMovie(Movie movie) {
        Genre currGenre = shop.getGenre(textMovieGenreName.getText());
        if (currGenre == null) {
            fillMovieFields();
            JOptionPane.showMessageDialog(null, "Error: no such country!");
            return false;
        }
        String newName = textMovieName.getText();
        if (shop.getMovie(newName) == null)
            shop.renameMovie(movie, newName);
        shop.changeMoviePlace(movie, currGenre);
        movie.setReleaseYear(Integer.parseInt(textMovieYear.getText()));
        return true;
    }

    private static void delete() {
        if (editMode) {
            if (genreMode) {
                shop.removeGenre(currentGenre);
                for (Movie m : currentGenre.getMovies())
                    comboMovie.removeItem(m.getName());
                comboGenre.removeItem(currentGenre.getName());
            } else {
                shop.removeMovie(currentMovie);
                comboMovie.removeItem(currentMovie.getName());
            }
        }
    }

    private void fillComboBoxes() {
        comboGenre.removeAllItems();
        comboMovie.removeAllItems();
        Map<String, Genre> genres = shop.getGenres();
        for (Map.Entry<String, Genre> entry : genres.entrySet()) {
            comboGenre.addItem(entry.getValue().getName());
            for (Movie movie : entry.getValue().getMovies()) {
                comboMovie.addItem(movie.getName());
            }
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
        Map<String, Genre> genres = shop.getGenres();
        textMovieName.setText(currentMovie.getName());
        textMovieGenreName.setText(genres.get(currentMovie.getGenreId()).getName());
        textMovieYear.setText(String.valueOf(currentMovie.getReleaseYear()));
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        shop = DOMParser.parse("src/main/java/second/module/first/shop.xml");
        JFrame myWindow = new Application();
        myWindow.setVisible(true);
        Movie movie = shop.getMovie("Halo");
        System.out.println(movie.getName() + ": " + movie.getMovieId());
    }
}