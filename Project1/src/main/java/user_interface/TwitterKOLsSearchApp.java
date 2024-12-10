package user_interface;

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

import java.io.*;
import java.util.Objects;

public class TwitterKOLsSearchApp extends Application {

    private final ObservableList<KOL> kolData1 = FXCollections.observableArrayList();
    private final ObservableList<KOL> kolData2 = FXCollections.observableArrayList();
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
        minFollowersField.setPromptText("Min Followers:");
        minFollowersField.getStyleClass().add("follow-field");

        // Nút tìm kiếm
        Button searchButton = new Button("Search");
        searchButton.getStyleClass().add("search-button");

        // Tạo TabPane
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);  // Không cho đóng tab

        // Tab 1 chứa bảng đọc từ KOL1.txt
        Tab tab1 = new Tab("KOL Ranking 1");
        tab1.setClosable(false);
        TableView<KOL> kolTable1 = createKOLTable(kolData1);
        loadKOLData("C:\\Users\\Administrator\\IdeaProjects\\Project1\\src\\main\\resources\\KOL1.txt", kolData1);
        VBox tab1Layout = new VBox(kolTable1);
        VBox.setVgrow(kolTable1, Priority.ALWAYS);  // Cho phép bảng co giãn theo chiều dọc
        tab1.setContent(tab1Layout);

        // Tab 2 chứa bảng đọc từ KOL2.txt
        Tab tab2 = new Tab("KOL Ranking 2");
        tab2.setClosable(false);
        TableView<KOL> kolTable2 = createKOLTable(kolData2);
        loadKOLData("C:\\Users\\Administrator\\IdeaProjects\\Project1\\src\\main\\resources\\KOL2.txt", kolData2);
        VBox tab2Layout = new VBox(kolTable2);
        VBox.setVgrow(kolTable2, Priority.ALWAYS);  // Cho phép bảng co giãn theo chiều dọc
        tab2.setContent(tab2Layout);

        // Thêm các tab vào TabPane
        tabPane.getTabs().addAll(tab1, tab2);

        // Layout cho nhập liệu và nút
        HBox inputLayout = new HBox(5, searchField, minFollowersField, searchButton);
        inputLayout.setPadding(new Insets(10));

        // Bố cục chính (AnchorPane để bảng có thể thay đổi kích thước)
        AnchorPane mainLayout = new AnchorPane(titleLabel, inputLayout, tabPane, statusLabel);
        mainLayout.setPadding(new Insets(10));

        // Thiết lập Anchor cho các phần tử con trong AnchorPane
        AnchorPane.setTopAnchor(titleLabel, 10.0);
        AnchorPane.setLeftAnchor(titleLabel, 10.0);
        AnchorPane.setTopAnchor(inputLayout, 50.0);
        AnchorPane.setLeftAnchor(inputLayout, 10.0);
        AnchorPane.setTopAnchor(tabPane, 100.0);
        AnchorPane.setLeftAnchor(tabPane, 10.0);
        AnchorPane.setRightAnchor(tabPane, 10.0);
        AnchorPane.setBottomAnchor(tabPane, 50.0);
        AnchorPane.setLeftAnchor(statusLabel, 10.0);
        AnchorPane.setBottomAnchor(statusLabel, 10.0);

        // Bọc bố cục trong ScrollPane để tự động thay đổi kích thước
        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true); // Tự động điều chỉnh chiều rộng
        scrollPane.setFitToHeight(true); // Tự động điều chỉnh chiều cao
        scrollPane.getStyleClass().add("scroll-pane");

        // Thiết lập icon cho ứng dụng
        String iconPath = Objects.requireNonNull(getClass().getResource("/images.png")).toExternalForm();
        primaryStage.getIcons().add(new Image(iconPath));

        // Thiết lập scene và hiển thị cửa sổ
        Scene scene = new Scene(scrollPane, 800, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        primaryStage.setTitle("Twitter KOLs Search");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadKOLData(String fileName, ObservableList<KOL> kolData) {
        kolData.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int ranking = 1;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 3) {
                    String username = parts[0];
                    int followers = Integer.parseInt(parts[1]);
                    String link = parts[2];
                    kolData.add(new KOL(username, followers, link, ranking++));
                }
            }
            statusLabel.setText("Loaded " + kolData.size() + " KOLs from " + fileName);
        } catch (IOException | NumberFormatException e) {
            showAlert("Error loading KOL data from " + fileName + ": " + e.getMessage());
        }
    }

    private TableView<KOL> createKOLTable(ObservableList<KOL> kolData) {
        TableView<KOL> kolTable = new TableView<>();

        // Cột Ranking
        TableColumn<KOL, Integer> rankingColumn = new TableColumn<>("Ranking");
        rankingColumn.setCellValueFactory(new PropertyValueFactory<>("ranking"));
        rankingColumn.setPrefWidth(40);
        rankingColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                    setStyle("-fx-alignment: CENTER;"); // Căn giữa
                }
            }
        });

        // Cột Username
        TableColumn<KOL, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        // Cột Followers
        TableColumn<KOL, Integer> followColumn = new TableColumn<>("Followers");
        followColumn.setCellValueFactory(new PropertyValueFactory<>("followers"));

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
        kolTable.setItems(kolData);

        // Cho phép các cột tự động thay đổi kích thước khi thay đổi kích thước bảng
        kolTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return kolTable;
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
