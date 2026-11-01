package DAOController;

import config.CRUD;
import gestionnaire_stock.Supplier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class SupplierDAO implements DAO<Supplier> {
    private CRUD crud = new CRUD();

    @Override
    public boolean insert(Supplier s) {
        String sql = "INSERT INTO supplier(id,fullname,email,tel,vat) VALUES('"
                + s.getId() + "','" + CRUD.esc(s.getFullname()) + "','" + CRUD.esc(s.getEmail()) + "','"
                + CRUD.esc(s.getTel()) + "','" + CRUD.esc(s.getVat()) + "')";
        return crud.executeInsert(sql);
    }

    @Override
    public boolean update(Supplier s, UUID id) {
        String sql = "UPDATE supplier SET fullname='" + CRUD.esc(s.getFullname())
                + "', email='" + CRUD.esc(s.getEmail())
                + "', tel='" + CRUD.esc(s.getTel())
                + "', vat='" + CRUD.esc(s.getVat())
                + "' WHERE id='" + id + "'";
        return crud.executeUpdate(sql);
    }

    @Override
    public boolean delete(UUID id) {
        return crud.executeDelete("DELETE FROM supplier WHERE id='" + id + "'");
    }

    @Override
    public ArrayList<Supplier> select() {
        ArrayList<Supplier> list = new ArrayList<>();
        ResultSet rs = crud.executeSelect("SELECT * FROM supplier");
        try {
            while (rs != null && rs.next()) {
                list.add(build(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public Supplier build(ResultSet rs) throws SQLException {
        return new Supplier(UUID.fromString(rs.getString("id")),
                rs.getString("fullname"), rs.getString("email"),
                rs.getString("tel"), rs.getString("vat"));
    }
}