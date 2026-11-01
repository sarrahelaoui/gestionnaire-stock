package DAOController;

import config.CRUD;
import gestionnaire_stock.Category;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class CategoryDAO implements DAO<Category> {
    private CRUD crud = new CRUD();

    private String parentSql(Category c) {
        return c.getParent() == null ? "NULL" : "'" + c.getParent().getId() + "'";
    }

    @Override
    public boolean insert(Category c) {
        String sql = "INSERT INTO category(id,name,description,parent_id) VALUES('"
                + c.getId() + "','" + CRUD.esc(c.getName()) + "','" + CRUD.esc(c.getDescription()) + "',"
                + parentSql(c) + ")";
        return crud.executeInsert(sql);
    }

    @Override
    public boolean update(Category c, UUID id) {
        String sql = "UPDATE category SET name='" + CRUD.esc(c.getName())
                + "', description='" + CRUD.esc(c.getDescription())
                + "', parent_id=" + parentSql(c)
                + " WHERE id='" + id + "'";
        return crud.executeUpdate(sql);
    }

    @Override
    public boolean delete(UUID id) {
        return crud.executeDelete("DELETE FROM category WHERE id='" + id + "'");
    }

    @Override
    public ArrayList<Category> select() {
        ArrayList<Category> list = new ArrayList<>();
        ResultSet rs = crud.executeSelect("SELECT * FROM category");
        try {
            while (rs != null && rs.next()) {
                list.add(build(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public Category findById(UUID id) {
        ResultSet rs = crud.executeSelect("SELECT * FROM category WHERE id='" + id + "'");
        try {
            if (rs != null && rs.next()) return build(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Category build(ResultSet rs) throws SQLException {
        Category c = new Category(UUID.fromString(rs.getString("id")),
                rs.getString("name"), rs.getString("description"));
        String parentId = rs.getString("parent_id");
        if (parentId != null) {
            c.setParent(findById(UUID.fromString(parentId)));
        }
        return c;
    }
}