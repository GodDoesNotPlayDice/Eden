/**
 *
 * @author Vicente Vasquez.
 */
package Controlador;
import BD.Conexion;
import Modelo.Planta;
import Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Controler {

    public Controler() {
    }
    
    public boolean login(Usuario newuser){
        String query;
        ArrayList<Usuario> users = new ArrayList<>();
        try {
            Conexion coneX=new Conexion();
            Connection cnx=coneX.connection();
            query="SELECT nombre, contrasenna FROM USUARIO";
            PreparedStatement stmt=cnx.prepareStatement(query);
            //defino el elemento dónde recibiré el resultado del SELECT
            ResultSet rs=stmt.executeQuery();
            while (rs.next()){
                Usuario user = new Usuario();
                user.setNombre(rs.getString("nombre"));
                user.setContrasenna(rs.getString("contrasenna"));
                users.add(user);
            }
        }catch(SQLException e){
            System.out.println("Error SQL al listar el usuario: "+e.getMessage());
        }catch(Exception ex){
            System.out.println("Error al encontrar usuario:  "+ex.getMessage());
        }  
        for (Usuario user : users) {
            if (newuser.getNombre().equals(user.getNombre()) && newuser.getContrasenna().equals(user.getContrasenna())){
                System.out.println("Login correcto");
                return true;
            }
        }
        return false;
    }
    
    public boolean ingresarPlantas(Planta planta){
        String query;
        try {
            Conexion coneX=new Conexion();
            Connection cnx=coneX.connection();
            query="INSERT INTO planta (nombre, stock, precio, descripcion, clasificacion) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt=cnx.prepareStatement(query);
            stmt.setString(1, planta.getNombre());
            stmt.setInt(2, planta.getStock());
            stmt.setInt(3, planta.getPrecio());
            stmt.setString(4, planta.getDescripcion());
            stmt.setInt(5, planta.getClasificacion());
            //ahora que tengo lista la QUERY con sus respectivos parámetros...
            stmt.executeUpdate();//Inserto en la Base de Datos
            return true;
            //cerrar TODO
        }catch(SQLException e){
            System.out.println("Error SQL: "+e.getMessage());
        }catch(Exception ex){ 
            System.out.println("Error al encontrar usuario:  "+ex.getMessage());
        }  
        return false;
    }
    
     public ArrayList<Planta> listarTodos(){
        String query;
        ArrayList<Planta> plantas = new ArrayList<>();
        try {
            Conexion coneX=new Conexion();
            Connection cnx=coneX.connection();
            query="SELECT id_planta ,nombre, stock, precio, descripcion, TIPO.nombre_clasi FROM PLANTA join TIPO on PLANTA.clasificacion = TIPO.clasificacion";
            PreparedStatement stmt=cnx.prepareStatement(query);
            //defino el elemento dónde recibiré el resultado del SELECT
            ResultSet rs=stmt.executeQuery();
            while (rs.next()){
                Planta planta = new Planta();
                planta.setId_producto(rs.getInt("id_planta"));
                planta.setNombre(rs.getString("nombre"));
                planta.setPrecio(rs.getInt("precio"));
                planta.setStock(rs.getInt("precio"));
                planta.setDescripcion(rs.getString("descripcion"));
                planta.setNombre_clasi(rs.getString("TIPO.nombre_clasi"));
                plantas.add(planta);
            }
            rs.close();
            stmt.close();
            cnx.close();
        }catch(SQLException e){
            System.out.println("Error SQL al listar el usuario: "+e.getMessage());
        }catch(Exception ex){
            System.out.println("Error al encontrar usuario:  "+ex.getMessage());
        }  
        
        return plantas;
    }
    
    
}