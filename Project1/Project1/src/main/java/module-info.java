module com.example.project {
    requires javafx.controls;
    requires javafx.fxml;
    exports user_interface;
    requires org.kordamp.bootstrapfx.core;
    requires org.seleniumhq.selenium.chrome_driver;
    requires org.seleniumhq.selenium.support;
    requires org.twitter4j.core;
    requires io.github.bonigarcia.webdrivermanager;
    requires org.json;
    requires org.seleniumhq.selenium.edge_driver;

}