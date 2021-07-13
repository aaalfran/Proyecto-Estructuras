package circularfortune;

import Elements.vistaJuego;
import static Elements.vistaJuego.cirExterno;
import static Elements.vistaJuego.cirInterno;
import TDAList.DoubleCircularList;
import static circularfortune.CircularFortune.musicaInicio;
import static circularfortune.CircularFortune.musicaJuego;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VentanaJuegoController implements Initializable{
    //Ventana Principal
    
    @FXML
    private AnchorPane anchor;
    
    @FXML
    private Button rotIzq;

    @FXML
    private Button eliminar;

    @FXML
    private Button rotDer;

    @FXML
    private Label score;

    @FXML
    private Label apuesta;
 
    //Acciones de la ventana principal del juego

    @FXML
    void clickDer(ActionEvent event) {
        playSound("derecha");
        
        
        DoubleCircularList.moveRigth(cirExterno);
        DoubleCircularList.moveRigth(cirInterno);
        vistaJuego.actualizarValoresCirculos();

    }

    @FXML
    void clickElim(ActionEvent event) {
        playSound("eliminar");
    }

    @FXML
    void clickIzq(ActionEvent event) {
        playSound("izquierda");
        DoubleCircularList.moveLeft(cirExterno);
        DoubleCircularList.moveLeft(cirInterno);
        vistaJuego.actualizarValoresCirculos();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        musicaInicio.stop();
        musicaJuego.play();
        //Se fija la apuesta inicial
        String apuestaIni = String.valueOf(SettingsController.apuestaIni);
        apuesta.setText(apuestaIni);
        
     
        //Se agregan los circulos de base
       anchor.getChildren().add(vistaJuego.getCirculoExt());
       anchor.getChildren().add(vistaJuego.getCirculoInt());
        
       //Se establece el valor del score inicial
       vistaJuego.fijarCirculos(anchor);
       Integer tot = DoubleCircularList.suma(vistaJuego.cirExterno,vistaJuego.cirInterno);
       score.setText(tot.toString());
       System.out.println(cirExterno.size());

    }
    @FXML
    void clickExitbtn(ActionEvent event) throws IOException{
        musicaInicio.play();
        musicaJuego.stop();
        playSound("click");
        Stage st = (Stage) anchor.getScene().getWindow();
        Alert.AlertType tipoAlerta = Alert.AlertType.CONFIRMATION;
        Alert alerta =  new Alert(tipoAlerta,"");
        alerta.initModality(Modality.APPLICATION_MODAL);
        alerta.initOwner(st);
        alerta.getDialogPane().setContentText("¿Esta seguro que desea salir del juego?");
        alerta.getDialogPane().setHeaderText("Si sale perdera todo su progeso en el juego ");
        Optional<ButtonType> resultado = alerta.showAndWait();
            if(resultado.get() == ButtonType.OK){
                playSound("click");
                Parent root = FXMLLoader.load(getClass().getResource("menuinicio.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }else{
                playSound("click");
                
            }
    }
    public void playSound(String s){
        switch (s) {
            case "click":
                {
                    AudioClip note = new AudioClip(this.getClass().getResource("/resources/clickBoton.wav").toString());
                    note.play();
                    break;
                }
            case "derecha":
                {
                    AudioClip note = new AudioClip(this.getClass().getResource("/resources/der.wav").toString());
                    note.play();
                    break;
                }
            case "izquierda":
                {
                    AudioClip note = new AudioClip(this.getClass().getResource("/resources/izq.wav").toString());
                    note.play();
                    break;
                }
            case "eliminar":
                {
                    AudioClip note = new AudioClip(this.getClass().getResource("/resources/elim.wav").toString());
                    note.play();
                    break;
                }
            default:
                break;
        }
    }
   
}

