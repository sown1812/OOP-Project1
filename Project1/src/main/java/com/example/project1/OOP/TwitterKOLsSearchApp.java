package com.example.project1.OOP;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Objects;

public class TwitterKOLsSearchApp extends Application {

    private final ObservableList<KOL> kolData = FXCollections.observableArrayList();
    private final Label statusLabel = new Label("Ready");

    @Override
    public void start(Stage primaryStage) {
        // Tiêu đề
        Label titleLabel = new Label("Twitter KOLs Search");
        titleLabel.getStyleClass().add("title-label");

        // Ô nhập từ khóa
        TextField searchField = new TextField();
        searchField.setPromptText("Enter hashtag or keyword:");
        searchField.getStyleClass().add("search-field");

        // Ô nhập số người theo dõi tối thiểu
        TextField minFollowersField = new TextField();
        minFollowersField.setPromptText("Min Followers:" );
        minFollowersField.getStyleClass().add("follow-field");

        // Nút tìm kiếm
        Button searchButton = new Button("Search ");
        searchButton.getStyleClass().add("search-button");

        // Tạo TableView để hiển thị dữ liệu KOL
        TableView<KOL> kolTable = createKOLTable();

        // Xử lý sự kiện nút tìm kiếm (bao gồm cả lọc)
        searchButton.setOnAction(e -> {
            String keyword = searchField.getText().trim();
            String minFollowersText = minFollowersField.getText().trim();
            if (!keyword.isEmpty()) {
                // Giả lập quá trình tìm kiếm
                simulateSearch(keyword, kolTable);

                // Nếu người dùng nhập giá trị tối thiểu, áp dụng lọc
                if (!minFollowersText.isEmpty()) {
                    try {
                        int minFollowers = Integer.parseInt(minFollowersText);
                        applyFilter(minFollowers, kolTable);
                    } catch (NumberFormatException ex) {
                        showAlert("Please enter a valid number for minimum followers.");
                    }
                }
            } else {
                showAlert("Please enter a keyword or hashtag.");
            }
        });

        // Thanh trạng thái
        HBox statusBar = new HBox();
        statusBar.setPadding(new Insets(5));
        statusBar.getChildren().add(statusLabel);
        statusBar.getStyleClass().add("statusBar");


        // Layout cho nhập liệu và nút
        HBox inputLayout = new HBox(5, searchField, minFollowersField, searchButton);
        inputLayout.setPadding(new Insets(10));

        // Bố cục chính
        VBox mainLayout = new VBox(5, titleLabel, inputLayout, kolTable, statusBar);
        mainLayout.setPadding(new Insets(10));
        mainLayout.getStyleClass().add("mainlayout");

        // Thiết lập icon cho ứng dụng
        String iconPath = Objects.requireNonNull(getClass().getResource("/images.png")).toExternalForm();
        primaryStage.getIcons().add(new Image(iconPath));

        Scene scene = new Scene(mainLayout, 800, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        primaryStage.setTitle("Twitter KOLs Search");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TableView<KOL> createKOLTable() {
        TableView<KOL> kolTable = new TableView<>();

        // Cột Ranking
        TableColumn<KOL, Integer> rankingColumn = new TableColumn<>("Ranking");
        rankingColumn.setCellValueFactory(new PropertyValueFactory<>("ranking"));
        rankingColumn.setPrefWidth(50);

        // Cột Username
        TableColumn<KOL, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        // Cột Followers
        TableColumn<KOL, Integer> followColumn = new TableColumn<>("Followers");
        followColumn.setCellValueFactory(new PropertyValueFactory<>("followers"));
        followColumn.setPrefWidth(50);

        // Cột Link tới trang cá nhân
        TableColumn<KOL, String> linkColumn = new TableColumn<>("Profile Link");
        linkColumn.setCellValueFactory(new PropertyValueFactory<>("link"));
        linkColumn.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Hyperlink hyperlink = new Hyperlink(item);
                    hyperlink.setOnAction(e -> getHostServices().showDocument(item));
                    setGraphic(hyperlink);
                }
            }
        });

        // Thêm các cột vào TableView
        kolTable.getColumns().addAll(rankingColumn, usernameColumn, followColumn, linkColumn);
        kolTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return kolTable;
    }

    private void simulateSearch(String keyword, TableView<KOL> kolTable) {
        // Giả lập dữ liệu tìm kiếm
        statusLabel.setText("Searching for: " + keyword + "...");
        kolData.clear();
        kolData.addAll(
                new KOL("@example1", 12000, "https://twitter.com/example1", 1),
                new KOL("@example2", 8500, "https://twitter.com/example2", 2),
                new KOL("@example3", 4500, "https://twitter.com/example3", 3)
        );

        // Thiết lập dữ liệu cho TableView
        kolTable.setItems(kolData);
        statusLabel.setText("Search complete. Found " + kolData.size() + " results.");
    }

    private void applyFilter(int minFollowers, TableView<KOL> kolTable) {
        ObservableList<KOL> filteredData = FXCollections.observableArrayList();
        for (KOL kol : kolData) {
            if (kol.getFollowers() >= minFollowers) {
                filteredData.add(kol);
            }
        }
        kolTable.setItems(filteredData);
        statusLabel.setText("Filter applied. Showing " + filteredData.size() + " results.");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}