/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpweb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import simplejdbc.CustomerEntity;
import simplejdbc.DAO;
import simplejdbc.DAOException;

/**
 *
 * @author pedago
 */
public class Methodes extends DAO {

    public Methodes(DataSource dataSource) {
        super(dataSource);
    }
    
    /*Liste des etats*/
    public List<String> listeEtats() throws DAOException {
        List<String> result = new LinkedList<>(); // Liste vIde

        String sql = "SELECT DISTINCT STATE FROM CUSTOMER";
        try (Connection connection = myDataSource.getConnection();
            Statement stmt = connection.createStatement()) {

            try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) { // Tant qu'il y a des enregistrements
                        // On récupère les champs nécessaires de l'enregistrement courant
                        // On crée l'objet entité
                        String state = rs.getString("STATE");
                        // On l'ajoute à la liste des résultats
                        result.add(state);
                    }
            }
        } catch (SQLException ex) {
                Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
                throw new DAOException(ex.getMessage());
        }
        return result;

    }
}
