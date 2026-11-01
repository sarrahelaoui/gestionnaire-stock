package DAOController;

import DAOController.ArticleDAO;
import config.CRUD;
import gestionnaire_stock.Article;
import gestionnaire_stock.MVT;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class MvtDAO implements DAO<MVT> {
    private CRUD crud = new CRUD();
    private ArticleDAO articleDAO = new ArticleDAO() {};

    public boolean insert(MVT m) {
        String sql = "INSERT INTO mvt(id,date_time,note,type,qty,article_id) VALUES('"
                + m.getId() + "','" + CRUD.esc(m.getDateTime()) + "','" + CRUD.esc(m.getNote()) + "','"
                + m.getType() + "'," + m.getQty() + ",'" + m.getArticle().getId() + "')";
        return crud.executeInsert(sql);
    }

    @Override
    public boolean update(MVT m, UUID id) {
        String sql = "UPDATE mvt SET date_time='" + CRUD.esc(m.getDateTime())
                + "', note='" + CRUD.esc(m.getNote())
                + "', type='" + m.getType()
                + "', qty=" + m.getQty()
                + " WHERE id='" + id + "'";
        return crud.executeUpdate(sql);
    }

    @Override
    public boolean delete(UUID id) {
        return crud.executeDelete("DELETE FROM mvt WHERE id='" + id + "'");
    }

    @Override
    public ArrayList<MVT> select() {
        return load("SELECT * FROM mvt");
    }

    // tous les mouvements d'un article
    public ArrayList<MVT> selectByArticle(Article a) {
        return load("SELECT * FROM mvt WHERE article_id='" + a.getId() + "'");
    }

    private ArrayList<MVT> load(String sql) {
        ArrayList<MVT> list = new ArrayList<>();
        ResultSet rs = crud.executeSelect(sql);
        try {
            while (rs != null && rs.next()) {
                Article a = articleDAO.findById(UUID.fromString(rs.getString("article_id")));
                list.add(new MVT(UUID.fromString(rs.getString("id")), a,
                        MVT.Type.valueOf(rs.getString("type")), rs.getDouble("qty"),
                        rs.getString("note"), rs.getString("date_time")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}