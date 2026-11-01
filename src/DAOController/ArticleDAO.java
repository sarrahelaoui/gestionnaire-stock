package DAOController;

import config.CRUD;
import gestionnaire_stock.Article;
import gestionnaire_stock.Supplier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class ArticleDAO implements DAO<Article> {
    private CRUD crud = new CRUD();
    private CategoryDAO categoryDAO = new CategoryDAO();
    private SupplierDAO supplierDAO = new SupplierDAO();

    @Override
    public boolean insert(Article a) {
        if (a.getCategory() == null) {
            System.err.println("Article sans catégorie !");
            return false;
        }
        String sql = "INSERT INTO article(id,label,description,qty,prix_achat,prix_vente,category_id) VALUES('"
                + a.getId() + "','" + CRUD.esc(a.getLabel()) + "','" + CRUD.esc(a.getDescription()) + "',"
                + a.getQty() + "," + a.getPrixAchat() + "," + a.getPrixVente() + ",'"
                + a.getCategory().getId() + "')";
        boolean ok = crud.executeInsert(sql);
        if (ok) saveSuppliers(a);
        return ok;
    }

    @Override
    public boolean update(Article a, UUID id) {
        String sql = "UPDATE article SET label='" + CRUD.esc(a.getLabel())
                + "', description='" + CRUD.esc(a.getDescription())
                + "', qty=" + a.getQty()
                + ", prix_achat=" + a.getPrixAchat()
                + ", prix_vente=" + a.getPrixVente()
                + ", category_id='" + a.getCategory().getId()
                + "' WHERE id='" + id + "'";
        boolean ok = crud.executeUpdate(sql);
        if (ok) {
            crud.executeDelete("DELETE FROM article_supplier WHERE article_id='" + id + "'");
            saveSuppliers(a);
        }
        return ok;
    }

    @Override
    public boolean delete(UUID id) {
        // article_supplier et mvt sont supprimés automatiquement (ON DELETE CASCADE)
        return crud.executeDelete("DELETE FROM article WHERE id='" + id + "'");
    }

    @Override
    public ArrayList<Article> select() {
        ArrayList<Article> list = new ArrayList<>();
        ResultSet rs = crud.executeSelect("SELECT * FROM article");
        try {
            while (rs != null && rs.next()) {
                list.add(build(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public Article findById(UUID id) {
        ResultSet rs = crud.executeSelect("SELECT * FROM article WHERE id='" + id + "'");
        try {
            if (rs != null && rs.next()) return build(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // enregistre la relation Article <-> Supplier (table article_supplier)
    private void saveSuppliers(Article a) {
        for (Supplier s : a.getSuppliers()) {
            crud.executeInsert("INSERT INTO article_supplier(article_id,supplier_id) VALUES('"
                    + a.getId() + "','" + s.getId() + "')");
        }
    }

    private Article build(ResultSet rs) throws SQLException {
        Article a = new Article(UUID.fromString(rs.getString("id")),
                rs.getString("label"), rs.getString("description"),
                rs.getDouble("qty"), rs.getDouble("prix_achat"), rs.getDouble("prix_vente"),
                categoryDAO.findById(UUID.fromString(rs.getString("category_id"))));
        // charger les fournisseurs
        ResultSet rs2 = crud.executeSelect("SELECT s.* FROM supplier s JOIN article_supplier x "
                + "ON s.id = x.supplier_id WHERE x.article_id='" + a.getId() + "'");
        while (rs2 != null && rs2.next()) {
            a.addSupplier(supplierDAO.build(rs2));
        }
        return a;
    }
}